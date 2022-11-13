package com.ismos_salt_erp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.DistrictWiseSaleReportListLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.DistrictWiseSaleReport;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DistrictWiseSaleReportAdapter extends RecyclerView.Adapter<DistrictWiseSaleReportAdapter.viewHolder> {
    private Context context;
    List<DistrictWiseSaleReport> saleReports;

    public DistrictWiseSaleReportAdapter(Context context, List<DistrictWiseSaleReport> saleReports) {
        this.context = context;
        this.saleReports = saleReports;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        DistrictWiseSaleReportListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.district_wise_sale_report_list_layout, parent, false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {
        DistrictWiseSaleReport currentitem = saleReports.get(position);
        position += 1;
        holder.itembinding.sl.setText(":  " + position);
        holder.itembinding.disName.setText(":  " + currentitem.getName());

        String  qty = "0";
        if (currentitem.getQuantity() != null){
             qty = String.valueOf(currentitem.getQuantity());

        }
        holder.itembinding.qty.setText(":  " + DataModify.addThreeDigit(qty) + MtUtils.kg);

    }

    @Override
    public int getItemCount() {
        return saleReports.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private DistrictWiseSaleReportListLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull DistrictWiseSaleReportListLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }}
