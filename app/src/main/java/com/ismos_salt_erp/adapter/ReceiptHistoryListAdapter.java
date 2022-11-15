package com.ismos_salt_erp.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.ReceiptHistoryListLayoutBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.ReceiptPaymentHistoryList;
import com.ismos_salt_erp.utils.AccountsUtil;
import com.ismos_salt_erp.utils.ImageBaseUrl;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.utils.replace.ReplaceCommaFromString;

import java.util.List;


public class ReceiptHistoryListAdapter extends RecyclerView.Adapter<ReceiptHistoryListAdapter.MyHolder> {
    FragmentActivity context;
    List<ReceiptPaymentHistoryList> lists;
    String portion;
    String whoseFor;

    public ReceiptHistoryListAdapter(FragmentActivity context, List<ReceiptPaymentHistoryList> lists, String portion, String whoseFor) {
        this.context = context;
        this.lists = lists;
        this.portion = portion;
        this.whoseFor = whoseFor;
    }

    @NonNull
    @Override
    public ReceiptHistoryListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ReceiptHistoryListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.receipt_history_list_layout, parent, false);
        return new ReceiptHistoryListAdapter.MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptHistoryListAdapter.MyHolder holder, int position) {
        ReceiptPaymentHistoryList currentPosition = lists.get(position);
        if (portion.equals(AccountsUtil.paymentList) || portion.equals(AccountsUtil.vendorPaymentList)) {
            holder.binding.receiptAmountTvLevel.setText("Payment Amount");
        }
        if (whoseFor.equals("receipt")) {
            if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1813)) {
                holder.binding.view.setVisibility(View.GONE);
            }
        }
        if (whoseFor.equals("paymentList")) {//eta change hobe
            if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1678)) {
                holder.binding.view.setVisibility(View.GONE);
            }
        }
        if (whoseFor.equals("expenseList")) {//eta change hobe
            if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1653)) {//Vendor Payment Due List (1653)
                holder.binding.view.setVisibility(View.GONE);
            }
        }

        holder.binding.date.setText(":  " + currentPosition.getPaymentDate());
        String companyName = "";
        if (currentPosition.getCompanyName() != null) {
            companyName = currentPosition.getCompanyName();
        }
        holder.binding.companyName.setText(":  " + companyName + "@" + currentPosition.getCustomerFname());
        holder.binding.receiptAmount.setText(":  " + DataModify.addFourDigit(ReplaceCommaFromString.replaceComma(currentPosition.getPaidAmount())) + MtUtils.priceUnit);
        holder.binding.transactionType.setText(":  " + currentPosition.getPaymentTypeName());
        holder.binding.processdBy.setText(currentPosition.getFullName());
        try {
            Glide.with(context).load(ImageBaseUrl.image_base_url + currentPosition.getProfilePhoto()).centerCrop().
                    error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).
                    into(holder.binding.purchaseImage);

        } catch (NullPointerException e) {
            Log.d("ERROR", e.getMessage());
            Glide.with(context).load(R.mipmap.ic_launcher).into(holder.binding.purchaseImage);
        }


        holder.binding.view.setOnClickListener(v -> {

            if (portion.equals(AccountsUtil.receiptList)) {
                gotoDetails(holder, position, "Receipt Details", "1", currentPosition.getBatchNo());
                return;
            }
            if (portion.equals(AccountsUtil.receiptPendingList)) {
                gotoDetails(holder, position, "Receipt Pending Details", "1", currentPosition.getBatchNo());
                return;
            }
            if (portion.equals(AccountsUtil.receiptDeclinedList)) {
                gotoDetails(holder, position, "Receipt Declined Details", "1", currentPosition.getBatchNo());
                return;
            }
            if (portion.equals(AccountsUtil.paymentList)) {
                gotoDetails(holder, position, "Payment Details", "2", currentPosition.getBatchNo());
                return;
            }
            if (portion.equals(AccountsUtil.paymentPendingList)) {
                gotoDetails(holder, position, "Payment Pending Details", "2", currentPosition.getBatchNo());
                return;
            }
            if (portion.equals(AccountsUtil.paymentDeclinedList)) {
                gotoDetails(holder, position, "Payment Declined Details", "2", currentPosition.getBatchNo());
                return;
            }

            if (portion.equals(AccountsUtil.vendorPaymentList)) {
                gotoDetails(holder, position, AccountsUtil.vendorPaymentList + " Details", "3", currentPosition.getBatchNo());
                return;
            }  if (portion.equals(AccountsUtil.pendingVendorPayment)) {
                gotoDetails(holder, position, AccountsUtil.vendorPaymentList + " Details", "3", currentPosition.getBatchNo());
                return;
            }  if (portion.equals(AccountsUtil.declinedVendorPayments)) {
                gotoDetails(holder, position, AccountsUtil.vendorPaymentList + " Details", "3", currentPosition.getBatchNo());
                return;
            }


        });

    }

    private void gotoDetails(MyHolder holder, int position, String receipt_details, String type, String batch) {
        Bundle bundle = new Bundle();
        bundle.putString("batchNo", batch);
        bundle.putString("portion", portion);
        bundle.putString("pageName", receipt_details);
        bundle.putString("type", type);//here one means expense
        Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_accountsListFragment_to_receiptHistoryDetailsFragment, bundle);

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ReceiptHistoryListLayoutBinding binding;

        public MyHolder(@NonNull ReceiptHistoryListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
