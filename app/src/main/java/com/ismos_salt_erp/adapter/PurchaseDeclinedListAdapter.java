package com.ismos_salt_erp.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.PurchaseReturnHistoryListLayoutBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.PurchaseDeclineList;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.purchase.AllPurchaseListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PurchaseDeclinedListAdapter extends RecyclerView.Adapter<PurchaseDeclinedListAdapter.viewHolder> {
    FragmentActivity activity;
    List<PurchaseDeclineList> lists;


    @NonNull
    @NotNull
    @Override
    public PurchaseDeclinedListAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        PurchaseReturnHistoryListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.purchase_return_history_list_layout, parent, false);
        return new PurchaseDeclinedListAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PurchaseDeclinedListAdapter.viewHolder holder, int position) {
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(activity).getUserCredentials().getPermissions()).contains(1501)) {
            holder.itembinding.view.setVisibility(View.GONE);
        }

        PurchaseDeclineList currentList = lists.get(position);
        holder.itembinding.date.setText(":  " + currentList.getDate());
        holder.itembinding.enterpriseName.setText(":  " + currentList.getEnterpriseName());
        holder.itembinding.companyName.setText(":  " + currentList.getCompanyName());
        holder.itembinding.ownerName.setText(":  " + currentList.getCustomerFname());
        holder.itembinding.orderSerial.setText(":  " + currentList.getId());
        holder.itembinding.uniqueOrderID.setText(":  " + currentList.getOrderSerial());
        if (currentList.getGrandTotal() != null){
            holder.itembinding.totalAmount.setText(":  "+currentList.getGrandTotal() +" Tk");
        }

        holder.itembinding.view.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("RefOrderId", currentList.getId());
            bundle.putString("orderVendorId", currentList.getOrderVendorID());
            bundle.putString("portion", "PENDING_PURCHASE");
            bundle.putString("pageName", "Decline Purchase Details");
            bundle.putString("porson", "PurchaseHistoryDetails");
            AllPurchaseListFragment.pageNumber = 1;
            Navigation.findNavController(holder.itembinding.getRoot()).navigate(R.id.action_allPurchaseListFragment_to_pendingPurchaseDetailsFragment, bundle);

        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private PurchaseReturnHistoryListLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull PurchaseReturnHistoryListLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}