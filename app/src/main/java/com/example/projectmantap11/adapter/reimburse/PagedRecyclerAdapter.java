package com.example.projectmantap11.adapter.reimburse;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmantap11.R;
import com.example.projectmantap11.models.Reimbursement;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PagedRecyclerAdapter extends PagedListAdapter<Reimbursement, PagedRecyclerAdapter.PagedViewHolder> {


    private List<Reimbursement> dataList = new ArrayList<>();
    private final ListItemClickListener<Reimbursement> mOnclickListener;
    private Context mCtx;

    public PagedRecyclerAdapter(ListItemClickListener<Reimbursement> mOnclickListener, Context mCtx) {
        super(DIFF_CALLBACK);
        this.mOnclickListener = mOnclickListener;
        this.mCtx = mCtx;
    }

    public interface ListItemClickListener<T> {
        void onListItemClicked(Reimbursement t);
    }

    @NonNull
    @Override
    public PagedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recycler_item_reimburse, parent, false);
        return new PagedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PagedViewHolder holder, int position) {
        Reimbursement item = getItem(position);

        if (item != null) {

        }else{
            Toast.makeText(mCtx, "Item is null", Toast.LENGTH_LONG).show();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnclickListener.onListItemClicked(dataList.get(position));
            }
        });
        holder.title.setId(position);
        final int i = holder.title.getId();
        holder.title.setText(dataList.get(position).getNote());
        Log.d("GETTITLE", "onBindViewHolder: " + dataList.get(position).getNote());

        String[] arrOfStr = dataList.get(position).getCreatedDate().split("T");
        holder.dates.setText(arrOfStr[0]);

        double amount = Double.parseDouble(dataList.get(position).getUang());
        DecimalFormat formatter = new DecimalFormat("#,###");

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

    private static DiffUtil.ItemCallback<Reimbursement> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Reimbursement>() {
                @Override
                public boolean areItemsTheSame(Reimbursement oldItem, Reimbursement newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Reimbursement oldItem, @NonNull Reimbursement newItem) {
                    return false;
                }
            };

    class PagedViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView dates;
        private TextView duit;
        private TextView status;
        private FrameLayout statusBlock;
        private View itemView;

        public PagedViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            duit = (TextView) itemView.findViewById(R.id.reimburse_value);
            title = (TextView) itemView.findViewById(R.id.reimburse_title);
            dates = (TextView) itemView.findViewById(R.id.reimburse_date);
            status = (TextView) itemView.findViewById(R.id.reimburse_status);
            statusBlock = (FrameLayout) itemView.findViewById(R.id.statustextblock);
        }
    }
}