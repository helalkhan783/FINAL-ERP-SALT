package com.ismos_salt_erp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
 import com.ismos_salt_erp.databinding.MonitorDetailsItemListLayoutBinding;


import org.jetbrains.annotations.NotNull;

 import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MonitoringDetailsAdapter extends RecyclerView.Adapter<MonitoringDetailsAdapter.viewHolder> {
    private Context context;
    /*List<Miller> miller;*/
    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        MonitorDetailsItemListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.monitor_details_item_list_layout, parent, false);
        return new viewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {
      /*  Miller currentList = miller.get(position);
        holder.itembinding.monitoriD.setText(currentList.getMillID());
        holder.itembinding.MonitorDate.setText(currentList.getEntryTime());
        holder.itembinding.monitorBy.setText(currentList.getReviewBy());
        holder.itembinding.publishedDate.setText(currentList.);
        holder.itembinding.document.setText(currentList.getDocument());
        holder.itembinding.enterTime.setText(currentList.getEntryDateTime());
        holder.itembinding.entryuserID.setText(currentList.getEntryuserID());
        holder.itembinding.reviewTime.setText(currentList.getReviewTime()); */

    }

    @Override
    public int getItemCount() {

        return  0;
      /*  return monitoringDetails.;*/
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private MonitorDetailsItemListLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull MonitorDetailsItemListLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding; }
    }
}
