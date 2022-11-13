package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.ReceiptDetailsListLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.ReceiptDetailsBatchList;
import com.ismos_salt_erp.utils.AccountsUtil;
import com.ismos_salt_erp.utils.MtUtils;

import java.util.List;

public class ReceiptDetailsListAdapter extends RecyclerView.Adapter<ReceiptDetailsListAdapter.MyHolder> {
    FragmentActivity context;
    List<ReceiptDetailsBatchList> lists;
    String portion;

    public ReceiptDetailsListAdapter(FragmentActivity context, List<ReceiptDetailsBatchList> lists, String portion) {
        this.context = context;
        this.lists = lists;
        this.portion = portion;
    }

    @NonNull
    @Override
    public ReceiptDetailsListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ReceiptDetailsListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.receipt_details_list_layout, parent, false);
        return new ReceiptDetailsListAdapter.MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptDetailsListAdapter.MyHolder holder, int position) {
        ReceiptDetailsBatchList currentPosition = lists.get(position);

        if (portion.equals(AccountsUtil.paymentList) || portion.equals(AccountsUtil.paymentPendingList) || portion.equals(AccountsUtil.paymentDeclinedList)){
            holder.binding.receiptTvLevel.setText("Payment Amount");
            holder.binding.soidLevelTv.setText("Particular");
        }

        if (portion.equals(AccountsUtil.vendorPaymentList) || portion.equals(AccountsUtil.declinedVendorPayments)|| portion.equals(AccountsUtil.pendingVendorPayment) ){
            holder.binding.receiptTvLevel.setText("Payment Amount");
            holder.binding.soidLevelTv.setText("Invoice No");
        }
        holder.binding.date.setText(":  " + currentPosition.getPaymentDate());
        holder.binding.inVoiceNo.setText(":  " +currentPosition.getParticular());
        holder.binding.receiptAmount.setText(":  " + currentPosition.getPaidAmount() + MtUtils.priceUnit);
        holder.binding.transactionType.setText(":  " + currentPosition.getPaymentTypeName());
    }
 
    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ReceiptDetailsListLayoutBinding binding;

        public MyHolder(@NonNull ReceiptDetailsListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}