package com.ismos_salt_erp.view.fragment.stock.list_adapter;

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
import com.ismos_salt_erp.databinding.StockTransferListLayoutBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.utils.DateFormatRight;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.stock.StockAllListFragment;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockTransferHistoryList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StockTransferHistoryListAdapter extends RecyclerView.Adapter<StockTransferHistoryListAdapter.viewHolder> {
    private FragmentActivity activity;
    private List<StockTransferHistoryList> list;
    private boolean isHistoryIn;
    String type;


    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        StockTransferListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.stock_transfer_list_layout, parent, false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {
        StockTransferHistoryList currentList = list.get(position);
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(activity).getUserCredentials().getPermissions()).contains(1460)) {
            holder.itembinding.edit.setVisibility(View.GONE);
        }
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(activity).getUserCredentials().getPermissions()).contains(1538)) {
            holder.itembinding.view.setVisibility(View.GONE);
        }

        double totalAmount = Double.parseDouble(currentList.getBuyingPrice()) * Double.parseDouble(currentList.getQuantity());

        try {
            holder.itembinding.date.setText(":  " + new DateFormatRight(activity, currentList.getEntryDate()).onlyDayMonthYear());
            //  holder.itembinding.fromEnterprise.setText(":  " + currentList.getFromEnterpriseName());
            //  holder.itembinding.toEnterPrise.setText(":  " + currentList.getToEnterpriseName());
            holder.itembinding.transferFrom.setText(":  " + currentList.getFromEnterpriseName() + "@" + currentList.getFromStoreName());
            holder.itembinding.transferTo.setText(":  "+currentList.getToEnterpriseName() + "@" + currentList.getToStoreName());
            holder.itembinding.itemName.setText(":  " + currentList.getProductTitle());
            holder.itembinding.quantity.setText(":  " + currentList.getQuantity() + " " + currentList.getName());
            holder.itembinding.price.setText(":  " + DataModify.addFourDigit(currentList.getBuyingPrice()) + MtUtils.priceUnit);

            holder.itembinding.totalAmount.setText(":  " + DataModify.addFourDigit(String.valueOf(totalAmount)) + MtUtils.priceUnit);

            holder.itembinding.view.setOnClickListener(v -> {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putString("RefOrderId", list.get(holder.getAdapterPosition()).getOrderID());
                    bundle.putString("vendorId", list.get(holder.getAdapterPosition()).getOrder_vendorID());
                    bundle.putString("pageName", "Transfer Details");
                    StockAllListFragment.pageNumber = 1;
                    StockAllListFragment.isFirstLoad = 0;
                    Navigation.findNavController(holder.itembinding.getRoot()).navigate(R.id.action_stockAllListFragment_to_transferDetailsFragment, bundle);
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }
            });

            holder.itembinding.edit.setOnClickListener(v -> {
                try {

                    Bundle bundle = new Bundle();
                    bundle.putString("id", list.get(holder.getAdapterPosition()).getSerialID());
                    bundle.putString("type", type);
                    StockAllListFragment.pageNumber = 1;
                    StockAllListFragment.isFirstLoad = 0;
                    Navigation.findNavController(holder.itembinding.getRoot()).navigate(R.id.action_stockAllListFragment_to_EditTransferDate, bundle);
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private StockTransferListLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull StockTransferListLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}
