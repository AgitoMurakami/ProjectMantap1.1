package com.example.projectmantap11.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectmantap11.R;
import com.example.projectmantap11.activity.ReimbursementDetailActivity;
import com.example.projectmantap11.activity.VirtualCardDetailsActivity;
import com.example.projectmantap11.adapter.reimburse.ItemViewModel;
import com.example.projectmantap11.adapter.reimburse.PagedRecyclerAdapter;
import com.example.projectmantap11.adapter.reimburse.ReimburseRecyclerAdapter;
import com.example.projectmantap11.models.Reimbursement;
import com.example.projectmantap11.models.ReimbursementServerResponse;
import com.example.projectmantap11.services.ApiService;
import com.example.projectmantap11.services.RetrofitResoClientInstance;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HistoryFragment extends Fragment implements ReimburseRecyclerAdapter.ListItemClickListener {

    private RecyclerView historyRecycler;
    private ArrayList<Reimbursement> reimbursementList;
    private boolean isLoading = false;

    private int page = 0;
    private int maxPage = 0;

    private ShimmerFrameLayout mShimmerViewContainer;

    //normal RV
    ReimburseRecyclerAdapter mAdapter;

    //view model
    ItemViewModel itemViewModel;
    PagedRecyclerAdapter adapter;

    //your key here
    String key = "your key here";
    int maxItem = 0;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        //TAKE KEY
        SharedPreferences prefs = getActivity().getSharedPreferences("CORONA_PROJECT_MANTAP", Context.MODE_PRIVATE);
        key = prefs.getString("key", "empty");
        Log.d("KEYREIMBURSE", "loadInitial: " + key);

        historyRecycler = view.findViewById(R.id.recycler_history);
        historyRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        historyRecycler.setHasFixedSize(true);

        reimbursementList = new ArrayList<Reimbursement>();
        callEnqueAPI();

        mAdapter = new ReimburseRecyclerAdapter(getActivity(),this );

        mAdapter.setMenuList(reimbursementList);
        historyRecycler.setAdapter(mAdapter);

        initScrollListener();



        ///////////////////YOUR VIEW MODEL

        /*
        //getting our ItemViewModel
        //itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        //creating the Adapter
        adapter = new PagedRecyclerAdapter(this, getActivity());


        //observing the itemPagedList from view model
        itemViewModel.itemPagedList.observe(getViewLifecycleOwner(), new Observer<PagedList<Reimbursement>>() {
            @Override
            public void onChanged(@Nullable PagedList<Reimbursement> items) {

                Log.d("Reimburse ", "onChanged: TEST");
                //in case of any changes
                //submitting the items to adapter
                adapter.submitList(items);
                adapter.notifyDataSetChanged();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        });

        historyRecycler.setAdapter(adapter);

        */
        //////////////////////////////////////////////

        mShimmerViewContainer = view.findViewById(R.id.shimmer_history_container);
        mShimmerViewContainer.startShimmer();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onListItemClicked(Reimbursement t) {

        String name = t.getName();
        String reimburseID = t.getId();
        String invoiceID = t.getInvoiceID();
        String category = t.getCategories();
        String amount = t.getUang();
        String note = t.getNote();
        String description = t.getDescription();
        String filepath = t.getImagePath();

        Intent intent = new Intent(getActivity(), ReimbursementDetailActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("reimburseID", reimburseID);
        intent.putExtra("invoiceID", invoiceID);
        intent.putExtra("category", category);
        intent.putExtra("amount", amount);
        intent.putExtra("note", note);
        intent.putExtra("description", description);
        intent.putExtra("filepath", filepath);

        startActivity(intent);
    }

    private void callEnqueAPI() {


        RetrofitResoClientInstance
                .getRetrofit()
                .create(ApiService.class)
                .daftarAllReimbursement("Bearer " + key, 0, page)
                .enqueue(new Callback<ReimbursementServerResponse>() {
                    @Override
                    public void onResponse(Call<ReimbursementServerResponse> call, Response<ReimbursementServerResponse> response) {
                        Log.d("REIMBURSERESPONSE", "onResponse: " + response.raw().toString());

                        if (response.body() != null) {

                            try {
                                Log.d("REIMBURSETRY", "onResponse: GET DATA : "+
                                        response.body().content);
                                reimbursementList.addAll(response.body().content);
                                mAdapter.notifyDataSetChanged();
                                mShimmerViewContainer.setVisibility(View.GONE);
                                maxPage = response.body().total_pages;
                                maxItem = response.body().total_elements;
                                Log.d("REIMBURSETRY", "onResponse: MAX ITEM = "+
                                        maxItem);

                                if (page < maxPage - 1) {
                                    page = page + 1;
                                }
                            }catch (Exception e) {
                                Log.d("REIMBURSECATCH", "onResponse: DATA FAILURE" +
                                        e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ReimbursementServerResponse> call, Throwable t) {
                        Log.d("REIMBURSEOnFailure", "onFailure: " + t);

                    }
                });
    }

    private void initScrollListener() {
        historyRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == reimbursementList.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });


    }

    private void loadMore() {
        reimbursementList.add(null);
        mAdapter.notifyItemInserted(reimbursementList.size() - 1);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reimbursementList.remove(reimbursementList.size() - 1);
                int scrollPosition = reimbursementList.size();
                mAdapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 10;

                if (maxItem < nextLimit) {
                    nextLimit = maxItem;
                }

                //addmore

                if(currentSize < nextLimit) {
                    callEnqueAPI();
                }

                mAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 0);


    }
}