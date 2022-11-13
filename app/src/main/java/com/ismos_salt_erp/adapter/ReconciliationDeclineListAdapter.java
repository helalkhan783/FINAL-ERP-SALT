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

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.ReconciliationDeclineListLayoutBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.stock.StockAllListFragment;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockDeclineReconciliationList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReconciliationDeclineListAdapter extends RecyclerView.Adapter<ReconciliationDeclineListAdapter.viewHolder> {
    private FragmentActivity activity;
    List<StockDeclineReconciliationList> list;


    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ReconciliationDeclineListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.reconciliation_decline_list_layout, parent, false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {
        StockDeclineReconciliationList currentList = list.get(position);
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(activity).getUserCredentials().getPermissions()).contains(1545)) {
            holder.itembinding.view.setVisibility(View.GONE);
        }

        try {
            if (currentList.getEnterpriseName() != null){
            holder.itembinding.enterpriseName.setText(":  " + currentList.getEnterpriseName());
            }

            holder.itembinding.store.setText(":  " + currentList.getStoreName());

            if (currentList.getProductTitle() != null) {
                holder.itembinding.itemName.setText(":  " + currentList.getProductTitle());
            }
            if (currentList.getReconciliationType() != null){
                holder.itembinding.type.setText(":  "+currentList.getReconciliationType());
            }

            holder.itembinding.quantity.setText(":  " + currentList.getQuantity() + " "+currentList.getUnitName());

            holder.itembinding.view.setOnClickListener(v -> {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putString("RefOrderId", list.get(holder.getAdapterPosition()).getOrderID());
                    bundle.putString("vendorId", list.get(holder.getAdapterPosition()).getOrderVendorID());
                    bundle.putString("pageName", "Reconciliation Details");
                    bundle.putString("portion", "PENDING_RECONCILIATION_DETAILS");
                    StockAllListFragment.pageNumber = 1;
                    StockAllListFragment.isFirstLoad = 0;
                    Navigation.createNavigateOnClickListener(R.id.action_stockAllListFragment_to_reconciliationDetailsFragment, bundle).onClick(v);
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }
            });

/*
            holder.itembinding.edit.setOnClickListener(v -> {
                try {
                    String currentProfileTypeId = PreferenceManager.getInstance(activity).getUserCredentials().getProfileTypeId();
                    if (!currentProfileTypeId.equals("7")) {
                        Toasty.info(activity, "you don't have permission for access this portion", Toasty.LENGTH_LONG).show();
                        return;
                    }
//                    Bundle bundle = new Bundle();
//                    bundle.putString("id", list.get(holder.getAdapterPosition()).getSerialID());
//                    Navigation.findNavController(holder.itembinding.getRoot()).navigate(R.id.action_stockAllListFragment_to_editReconciliation, bundle);
//
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }
            });
*/


        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private ReconciliationDeclineListLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull ReconciliationDeclineListLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}
