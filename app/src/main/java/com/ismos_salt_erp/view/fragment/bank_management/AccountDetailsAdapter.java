package com.ismos_salt_erp.view.fragment.bank_management;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.AccountListLayBinding;
import com.ismos_salt_erp.databinding.PurchaseReturnHistoryListLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.bankresponse.AccountList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AccountDetailsAdapter extends RecyclerView.Adapter<AccountDetailsAdapter.viewHolder> {
    FragmentActivity activity;
    List<AccountList> lists;

    public AccountDetailsAdapter(FragmentActivity activity, List<AccountList> lists) {
        this.activity = activity;
        this.lists = lists;
    }

    @NonNull
    @NotNull
    @Override
    public AccountDetailsAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        AccountListLayBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.account_list_lay, parent, false);
        return new AccountDetailsAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AccountDetailsAdapter.viewHolder holder, int position) {

        AccountList currentList = lists.get(position);
        try {
            holder.itembinding.date.setText(":  " + currentList.getTransactionDateTime());
            holder.itembinding.processedBy.setText(":  " + currentList.getEntryUserName());
            holder.itembinding.in.setText(":  " + currentList.getIn());
            holder.itembinding.out.setText(":  " + currentList.getOut());
            if (currentList.getReferenceID() !=null){
                holder.itembinding.refId.setText(":  " + currentList.getReferenceID());
            }
            if (currentList.getParticular() !=null){
                holder.itembinding.partyName.setText(":  " + currentList.getParticular());
            }
        } catch (Exception e) {
        }


    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private AccountListLayBinding itembinding;

        public viewHolder(@NonNull @NotNull AccountListLayBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}