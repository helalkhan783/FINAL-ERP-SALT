package com.ismos_salt_erp.adapter.account_report;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.CreditVoucherListLayoutBinding;
import com.ismos_salt_erp.databinding.ReceiptHistoryListLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.DebitAndCreditVoucherList;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.accounts.AccountsListFragment;

import java.util.List;

public class DebitAndCreditVoucherListAdapter extends RecyclerView.Adapter<DebitAndCreditVoucherListAdapter.MyHolder> {
    FragmentActivity context;
    List<DebitAndCreditVoucherList> lists;
    AccountsListFragment click;
    String voucherType;

    public DebitAndCreditVoucherListAdapter(FragmentActivity context, List<DebitAndCreditVoucherList> lists, AccountsListFragment click,String voucherType) {
        this.context = context;
        this.lists = lists;
        this.click = click;
        this.voucherType = voucherType;
    }

    @NonNull
    @Override
    public DebitAndCreditVoucherListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CreditVoucherListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.credit_voucher_list_layout, parent, false);
        return new DebitAndCreditVoucherListAdapter.MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DebitAndCreditVoucherListAdapter.MyHolder holder, int position) {
        DebitAndCreditVoucherList currentPosition = lists.get(position);
        holder.binding.edit.setVisibility(View.GONE);
        holder.binding.date.setText(":  " + currentPosition.getPaymentDateTime());
        holder.binding.companyName.setText(":  " + currentPosition.getCompanyName() + "@" + currentPosition.getCustomerFname());
        holder.binding.refNo.setText(":  " + currentPosition.getPaymentID());
        holder.binding.amount.setText(":  " + DataModify.addFourDigit(currentPosition.getTotalAmount()) + MtUtils.priceUnit);
        if (currentPosition.getPaymentType() != null) {
            holder.binding.transactionType.setText(": " + currentPosition.getPaymentTypeName());

        }
        holder.binding.processBy.setText(":  " + currentPosition.getEntryUser());

        holder.binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        click.updateDebitAndCreditVoucher(holder.getAdapterPosition(),currentPosition.paymentID,currentPosition.getTotalAmount(),currentPosition.getPaymentDateTime(),voucherType,currentPosition.getPaymentType(),currentPosition.getOrderID());
            }
        });


    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        CreditVoucherListLayoutBinding binding;

        public MyHolder(@NonNull CreditVoucherListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
