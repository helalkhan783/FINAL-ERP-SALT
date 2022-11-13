package com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.purchase_report;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.VendorLedgerLayoutBinding;
import com.ismos_salt_erp.databinding.VendorReportListLayoutBinding;
import com.ismos_salt_erp.retrofit.VendorReportList;
import com.ismos_salt_erp.utils.DateFormatRight;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;

import java.util.List;

public class VendorReportListAdapter extends RecyclerView.Adapter<VendorReportListAdapter.MyHolder> {
    FragmentActivity activity;
    List<VendorReportList> list;

    public VendorReportListAdapter(FragmentActivity activity, List<VendorReportList> list) {
        this.activity = activity;
        this.list = list;
    }
    @NonNull
    @Override
    public VendorReportListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VendorLedgerLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.vendor_ledger_layout, parent, false);
        return new VendorReportListAdapter.MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorReportListAdapter.MyHolder holder, int position) {
        VendorReportList currentList = list.get(position);

        holder.binding.date.setText(":  "+currentList.getDate());
        holder.binding.refNo.setText(":  "+currentList.getReferenceNo());
        holder.binding.expenseAmount.setText(":  "+currentList.getExpense() +MtUtils.priceUnit);
        holder.binding.receipt.setText(":  "+DataModify.addFourDigit(currentList.getReceipt())+MtUtils.priceUnit);
        holder.binding.payment.setText(":  "+DataModify.addFourDigit(currentList.getPayment())+MtUtils.priceUnit);
       if (currentList.getRemarks() !=null){
           holder.binding.remarks.setText(":  "+currentList.getDate());
       }

        holder.binding.balance.setText(":  "+ DataModify.addFourDigit(String.valueOf(currentList.getBalance()))+MtUtils.priceUnit);
     }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        VendorLedgerLayoutBinding binding;
        public MyHolder(@NonNull VendorLedgerLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
