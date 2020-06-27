package com.example.projectmantap11.adapter.reimburse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmantap11.R;
import com.example.projectmantap11.models.Reimbursement;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ReimburseRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private List<Reimbursement> dataList = new ArrayList<>();


    private final ListItemClickListener<Reimbursement> mOnclickListener;

    public interface ListItemClickListener<T> {
        void onListItemClicked(Reimbursement t);
    }

    public ReimburseRecyclerAdapter(Context context, ListItemClickListener onClickListener) {
        this.context = context;
        this.mOnclickListener = onClickListener;
    }

    public void setMenuList (List<Reimbursement> menuList) {
        dataList = new ArrayList<>();
        dataList = menuList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.recycler_item_reimburse, parent, false);
            return new MenuViewHolder(view);
        } else {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.recycler_item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof  MenuViewHolder) {
            populateItemRows((MenuViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return dataList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView dates;
        private TextView duit;
        private TextView status;
        private FrameLayout statusBlock;
        private View itemView;

        public MenuViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            duit = (TextView) itemView.findViewById(R.id.reimburse_value);
            title = (TextView) itemView.findViewById(R.id.reimburse_title);
            dates = (TextView) itemView.findViewById(R.id.reimburse_date);
            status = (TextView) itemView.findViewById(R.id.reimburse_status);
            statusBlock = (FrameLayout) itemView.findViewById(R.id.statustextblock);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    private void populateItemRows(MenuViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnclickListener.onListItemClicked(dataList.get(position));
            }
        });
        holder.title.setId(position);
        final int i = holder.title.getId();
        holder.title.setText(dataList.get(position).getNote());

        String[] arrOfStr = dataList.get(position).getCreatedDate().split("T");
        holder.dates.setText(arrOfStr[0]);

        double amount = Double.parseDouble(dataList.get(position).getUang());
        DecimalFormat formatter = new DecimalFormat("#,###.00");

        holder.duit.setText("Rp " + formatter.format(amount));
        holder.status.setText(dataList.get(position).getStatus());

        if (dataList.get(position).getStatus().equals("ACCEPTED")) {
            holder.status.setText("accepted");
            holder.statusBlock.setBackgroundResource(R.drawable.background_statustext_green);
        } else if (dataList.get(position).getStatus().equals("PENDING")) {
            holder.status.setText("pending");
            holder.statusBlock.setBackgroundResource(R.drawable.background_statustext_yellow);
        } else if (dataList.get(position).getStatus().equals("REJECTED")) {
            holder.status.setText("rejected");
            holder.statusBlock.setBackgroundResource(R.drawable.background_statustext_red);
        } else {
            holder.status.setText("submitted");
            holder.statusBlock.setBackgroundResource(R.drawable.background_statustext_yellow);
        }
    }

}
