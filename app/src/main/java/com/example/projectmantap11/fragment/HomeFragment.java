package com.example.projectmantap11.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projectmantap11.R;
import com.example.projectmantap11.activity.HomeActivity;
import com.example.projectmantap11.activity.LoginActivity;
import com.example.projectmantap11.activity.VirtualCardDetailsActivity;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.text.SimpleDateFormat;

public class HomeFragment extends Fragment{

    private ShimmerFrameLayout mShimmerViewContainer;
    private LinearLayout vcardtrue, vcardfalse;
    private ImageView newReimbursement, historyReimbursement, vCardDetail;

    private TextView viewname, dateTV;

    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        vcardtrue = view.findViewById(R.id.vcardtrue);
        vcardfalse = view.findViewById(R.id.nocardview);
        dateTV = view.findViewById(R.id.dateHome);
        vCardDetail = view.findViewById(R.id.carddetailactivity);
        viewname = view.findViewById(R.id.userHome);

        //get and set name
        SharedPreferences sharedPref = getActivity().getSharedPreferences("CORONA_PROJECT_MANTAP", Context.MODE_PRIVATE);
        String yourname = sharedPref.getString("yourname", "empty");
        viewname.setText("Hi, " + yourname);

        //stop shimmer + hide no card
        mShimmerViewContainer.stopShimmer();
        mShimmerViewContainer.hideShimmer();
        //mShimmerViewContainer.setVisibility(View.GONE);
        vcardfalse.setVisibility(View.GONE);

        //set date
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy");
        String dateString = sdf.format(date);
        dateTV.setText(dateString);

        //set reimbursement menu
        newReimbursement = view.findViewById(R.id.addnewreimbursement);
        historyReimbursement = view.findViewById(R.id.showreimbursementhistory);

        newReimbursement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BottomSheetFragment().show(getActivity().getSupportFragmentManager(), "Dialog");
            }
        });

        historyReimbursement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navView.setSelectedItemId(R.id.navigation_dashboard);
            }
        });

        vCardDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VirtualCardDetailsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }



}