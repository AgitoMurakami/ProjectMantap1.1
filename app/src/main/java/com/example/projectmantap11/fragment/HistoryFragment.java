package com.example.projectmantap11.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
import com.example.projectmantap11.adapter.reimburse.ItemViewModel;
import com.example.projectmantap11.adapter.reimburse.PagedRecyclerAdapter;
import com.example.projectmantap11.adapter.reimburse.ReimburseRecyclerAdapter;
import com.example.projectmantap11.models.Reimbursement;
import com.example.projectmantap11.services.ApiService;
import com.example.projectmantap11.services.RetrofitResoClientInstance;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HistoryFragment extends Fragment implements PagedRecyclerAdapter.ListItemClickListener {

    private RecyclerView historyRecycler;
    private ArrayList<Reimbursement> reimbursementList;
    private boolean isLoading = false;

    private int page = 0;

    private ShimmerFrameLayout mShimmerViewContainer;

    ItemViewModel itemViewModel;
    PagedRecyclerAdapter adapter;


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

        historyRecycler = view.findViewById(R.id.recycler_history);
        historyRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        historyRecycler.setHasFixedSize(true);

        //getting our ItemViewModel
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

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

    }
}