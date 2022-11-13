package com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.purchase_report;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.RcustomerReportListLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.CustomerReportList;
import com.ismos_salt_erp.serverResponseModel.customer_report.CustomerReportList1;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.utils.replace.ReplaceCommaFromString;

import java.util.List;

public class CustomerReportListAdapter extends RecyclerView.Adapter<CustomerReportListAdapter.MyHolder> {
    FragmentActivity activity;
    List<CustomerReportList1> list;


    public CustomerReportListAdapter(FragmentActivity activity, List<CustomerReportList1> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RcustomerReportListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.rcustomer_report_list_layout, parent, false);

        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        CustomerReportList1 currentList = list.get(position);
        holder.binding.date.setText(":  "+currentList.getPaymentDate());
        holder.binding.referenceNumber.setText(":  "+currentList.getParticular());
        holder.binding.saleAmount.setText(":  "+currentList.getSaleAmount());
        holder.binding.returnAmount.setText(":  "+currentList.getReturnAmount());
        holder.binding.receipt.setText(":  "+currentList.getReceiptAmount());
        holder.binding.payment.setText(":  "+currentList.getPaymentAmount());
        if (currentList.getTrx_type() !=null){
            holder.binding.transactionType.setText(":  "+currentList.getTrx_type());
        }
        holder.binding.balance.setText(":  "+currentList.getBalance());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        RcustomerReportListLayoutBinding binding;
        public MyHolder(@NonNull RcustomerReportListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
