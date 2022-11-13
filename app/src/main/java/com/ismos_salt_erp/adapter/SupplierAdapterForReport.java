package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.RcustomerReportListLayoutBinding;
import com.ismos_salt_erp.databinding.SupplierLedgerLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.customer_report.CustomerReportList1;
import com.ismos_salt_erp.serverResponseModel.customer_report.SupplierRepostList1;

import java.util.List;

public class SupplierAdapterForReport extends RecyclerView.Adapter<SupplierAdapterForReport.MyHolder> {
    FragmentActivity activity;
    List<SupplierRepostList1> list;


    public SupplierAdapterForReport(FragmentActivity activity, List<SupplierRepostList1> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public SupplierAdapterForReport.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SupplierLedgerLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.supplier_ledger_layout, parent, false);
        return new SupplierAdapterForReport.MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SupplierAdapterForReport.MyHolder holder, int position) {
        SupplierRepostList1 currentList = list.get(position);
        holder.binding.date.setText(":  "+currentList.getPaymentDate());
        holder.binding.referenceNumber.setText(":  "+currentList.getParticular());
        holder.binding.purchaseAmount.setText(":  "+currentList.getPurchaseAmount());
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
        SupplierLedgerLayoutBinding binding;
        public MyHolder(@NonNull SupplierLedgerLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
