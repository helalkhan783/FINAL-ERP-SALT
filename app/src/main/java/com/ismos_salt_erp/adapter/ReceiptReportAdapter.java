package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.ReceiptLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.account_report_response.ReceiptList;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReceiptReportAdapter extends RecyclerView.Adapter<ReceiptReportAdapter.viewHolder> {
    private FragmentActivity context;
    List<ReceiptList> saleReports;

    public ReceiptReportAdapter(FragmentActivity context, List<ReceiptList> saleReports) {
        this.context = context;
        this.saleReports = saleReports;
    }

    @NonNull
    @NotNull
    @Override
    public ReceiptReportAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ReceiptLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.receipt_layout, parent, false);
        return new ReceiptReportAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReceiptReportAdapter.viewHolder holder, int position) {
        ReceiptList currentitem = saleReports.get(position);
        position += 1;
        holder.itembinding.sl.setText(":  " + position);
        holder.itembinding.date.setText(":  " + currentitem.getPaymentDate());
        holder.itembinding.companyName.setText(":  " + currentitem.getCompanyName() +"@"+currentitem.getCustomerName());
        holder.itembinding.transactionType.setText(":  " + currentitem.getTransactionType());
        holder.itembinding.amount.setText(":  " + currentitem.getReceiptAmout()  + MtUtils.priceUnit);
    }

    @Override
    public int getItemCount() {
        return saleReports.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private ReceiptLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull ReceiptLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }}
