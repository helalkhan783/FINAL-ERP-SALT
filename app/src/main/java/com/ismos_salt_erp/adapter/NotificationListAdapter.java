package com.ismos_salt_erp.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.serverResponseModel.NotificationListResponse;
import com.ismos_salt_erp.view.fragment.NotificationFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.AllArgsConstructor;


public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyHolder> {
    private FragmentActivity context;
    private  List<NotificationListResponse> notificationListResponseList;
    private  NotificationFragment accessInterface;

    public NotificationListAdapter(FragmentActivity context, List<NotificationListResponse> notificationListResponseList, NotificationFragment accessInterface) {
        this.context = context;
        this.notificationListResponseList = notificationListResponseList;
        this.accessInterface = accessInterface;
    }

    @NonNull
    @Override
    public NotificationListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_item_model2, parent, false);
        return new NotificationListAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationListAdapter.MyHolder holder, int position) {
        NotificationListResponse current = notificationListResponseList.get(position);
        holder.dateTextView.setText(notificationListResponseList.get(position).getEntryDate());
        holder.referenceTextView.setText(notificationListResponseList.get(position).getRemarks() + " #" + notificationListResponseList.get(position).getRefOrderID());
        holder.noteTextView.setText(notificationListResponseList.get(position).getNote());


        if (notificationListResponseList.get(position).getOrderApproval().equals("2")) {
            holder.approvedTextView.setVisibility(View.GONE);
            holder.approved_date.setVisibility(View.GONE);
            holder.approved_date_tv.setVisibility(View.GONE);
            holder.pendingTextView.setVisibility(View.VISIBLE);
            holder.pendingTextView.setText("Pending");
        } else if (notificationListResponseList.get(position).getOrderApproval().equals("1")) {
            holder.approvedTextView.setVisibility(View.VISIBLE);
            holder.approved_date.setVisibility(View.VISIBLE);
            holder.approved_date_tv.setVisibility(View.VISIBLE);
            holder.pendingTextView.setVisibility(View.GONE);
            holder.approvedTextView.setText("Approved by " + notificationListResponseList.get(position).getProcessedBy().getFullName());
            holder.approved_date_tv.setText(notificationListResponseList.get(position).getApprovedDateTime());
        } else if (notificationListResponseList.get(position).getStatus().equals("0")) {
            holder.approvedTextView.setVisibility(View.GONE);
            holder.approved_date.setVisibility(View.GONE);
            holder.approved_date_tv.setVisibility(View.GONE);
            holder.pendingTextView.setVisibility(View.VISIBLE);
            holder.pendingTextView.setText("Old");
        } else {
            holder.approvedTextView.setVisibility(View.GONE);
            holder.approved_date.setVisibility(View.GONE);
            holder.approved_date_tv.setVisibility(View.GONE);
            holder.pendingTextView.setVisibility(View.VISIBLE);
            holder.pendingTextView.setText("Declined");
        }

        holder.detailsBtn.setOnClickListener(v -> {

            Log.d("TYPE", String.valueOf(current.getType()));

            accessInterface.goToDetails(current.getType(),current.getCustomerID(),current.getStatus(),current.getOrderApproval(),current.getBatch());


        });
    }


    @Override
    public int getItemCount() {
        return notificationListResponseList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.date_tv)
        TextView dateTextView;
        @BindView(R.id.reference_tv)
        TextView referenceTextView;
        @BindView(R.id.note_tv)
        TextView noteTextView;
        @BindView(R.id.approved_tv)
        TextView approvedTextView;
        @BindView(R.id.pending_tv)
        TextView pendingTextView;
        @BindView(R.id.approved_date)
        TextView approved_date;
        @BindView(R.id.approved_date_tv)
        TextView approved_date_tv;
        @BindView(R.id.detailsBtn)
        ImageButton detailsBtn;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
