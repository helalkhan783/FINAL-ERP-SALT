package com.ismos_salt_erp.adapter.bank_list_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.BankAccountListLayoutBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.bankresponse.BankAccountList;
import com.ismos_salt_erp.utils.PermissionUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BankAccountListAdapter extends RecyclerView.Adapter<BankAccountListAdapter.viewHolder> {
    private Context context;
    List<BankAccountList> lists;

    public BankAccountListAdapter(Context context, List<BankAccountList> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @NotNull
    @Override
    public BankAccountListAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        BankAccountListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.bank_account_list_layout, parent, false);
        return new BankAccountListAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BankAccountListAdapter.viewHolder holder, int position) {
        BankAccountList currentList = lists.get(position);

        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1500)) {
            holder.itembinding.edit.setVisibility(View.GONE);
        }
        try {
            holder.itembinding.bankName.setText(":  " + currentList.getBankName());
            holder.itembinding.accountType.setText(":  " + currentList.getAccountType());
            holder.itembinding.accountName.setText(":  " + currentList.getAccountantName());
            holder.itembinding.accountNumber.setText(":  " + currentList.getAccountNumber());
            holder.itembinding.routingNo.setText(":  " + currentList.getRoutingNo());
            holder.itembinding.branch.setText(":  " + currentList.getBankBranch());
            holder.itembinding.keyPerson.setText(":  " + currentList.getKeypersonName());
            holder.itembinding.phone.setText(":  " + currentList.getKeypersonContact());
            holder.itembinding.bankAddress.setText(":  " + currentList.getBankAddress());


        } catch (Exception e) {
        }

    }


    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private BankAccountListLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull BankAccountListLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}