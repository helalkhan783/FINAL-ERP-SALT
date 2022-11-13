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
import com.ismos_salt_erp.databinding.StockListLayoutBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.stock.StockAllListFragment;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockPendingReconciliationList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReconciliationPendingListAdapter extends RecyclerView.Adapter<ReconciliationPendingListAdapter.viewHolder> {
    private FragmentActivity activity;
    private List<StockPendingReconciliationList> list;


    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        StockListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.stock_list_layout, parent, false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {
        StockPendingReconciliationList currentList = list.get(position);
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(activity).getUserCredentials().getPermissions()).contains(1437)) {
            holder.itembinding.edit.setVisibility(View.GONE);
        }
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(activity).getUserCredentials().getPermissions()).contains(1545)) {
            holder.itembinding.view.setVisibility(View.GONE);
        }
        try {
            if (currentList.getEnterpriseName() == null) {
                holder.itembinding.enterpriseName.setText(":  ");
            } else {
                holder.itembinding.enterpriseName.setText(":  " + currentList.getEnterpriseName());

            }
            if (currentList.getStoreName() == null) {
                holder.itembinding.store.setText(":  ");
            } else {
                holder.itembinding.store.setText(":  " + currentList.getStoreName());

            }

            if (currentList.getReconciliationType() == null) {
                holder.itembinding.type.setText(":  ");
            } else {
                holder.itembinding.type.setText(":  " + currentList.getReconciliationType());

            }
            if (currentList.getProductTitle() == null) {
                holder.itembinding.itemName.setText(":  ");
            } else {
                holder.itembinding.itemName.setText(":  " + currentList.getProductTitle());

            }
            if (currentList.getQuantity() == null) {
                holder.itembinding.quantity.setText(":  ");
            } else {
                holder.itembinding.quantity.setText(":  " + currentList.getQuantity() + " " + currentList.getUnitName());
            }
            holder.itembinding.view.setOnClickListener(v -> {
                try {

                    Bundle bundle = new Bundle();
                    bundle.putString("RefOrderId", list.get(holder.getAdapterPosition()).getOrderID());
                    bundle.putString("vendorId", list.get(holder.getAdapterPosition()).getOrder_vendorID());
                    bundle.putString("pageName", "Reconciliation Details");
                    bundle.putString("portion", "PENDING_RECONCILIATION_DETAILS");
                    bundle.putString("status", "2");//for detect this is pending Reconciliation list details
                    StockAllListFragment.pageNumber = 1;

                    StockAllListFragment.isFirstLoad = 0;
                    Navigation.createNavigateOnClickListener(R.id.action_stockAllListFragment_to_reconciliationDetailsFragment, bundle).onClick(v);
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }
            });

        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }
        /**
         * For edit Transfer Details
         */
        holder.itembinding.edit.setOnClickListener(v -> {
            try {

                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(activity).getUserCredentials().getPermissions()).contains(1437)  ) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", list.get(holder.getAdapterPosition()).getSerialID());
                    StockAllListFragment.pageNumber = 1;
                    StockAllListFragment.isFirstLoad = 0;
                    Navigation.findNavController(holder.itembinding.getRoot()).navigate(R.id.action_stockAllListFragment_to_editReconciliation, bundle);
                    return;
                } else {
                    Toasty.info(activity, "You don't have permission for access this portion", Toasty.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.d("ERROR", "" + e.getMessage());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private StockListLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull StockListLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}
