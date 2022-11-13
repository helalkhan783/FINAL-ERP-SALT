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
import com.ismos_salt_erp.databinding.StockOkListLayoutBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.stock.StockAllListFragment;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockPendingTransferredList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StockPendingTransferredLisAdapter extends RecyclerView.Adapter<StockPendingTransferredLisAdapter.viewHolder> {
    private FragmentActivity activity;
    List<StockPendingTransferredList> list;


    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        StockOkListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.stock_ok_list_layout, parent, false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {
        StockPendingTransferredList currentList = list.get(position);
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(activity).getUserCredentials().getPermissions()).contains(1460)) {
            holder.itembinding.edit.setVisibility(View.GONE);
        }
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(activity).getUserCredentials().getPermissions()).contains(1538)) {
            holder.itembinding.view.setVisibility(View.GONE);
        }
        try {
            double totalAmount  = Double.parseDouble(currentList.getBuyingPrice()) * Double.parseDouble(currentList.getQuantity());

            holder.itembinding.date.setText(":  " + currentList.getEntryDate());
            holder.itembinding.fromEnterprise.setText(":  " + currentList.getFromEnterpriseName());
            holder.itembinding.toEnterPrise.setText(":  " + currentList.getToEnterpriseName());
            holder.itembinding.transferTo.setText(":  " + currentList.getToStoreName());
            holder.itembinding.transferFrom.setText(":  " + currentList.getFromStoreName());
            holder.itembinding.itemName.setText(":  " + currentList.getProductTitle());
            holder.itembinding.quantity.setText(":  " + currentList.getQuantity() + " " +currentList.getName());
            holder.itembinding.price.setText(":  "+currentList.getBuyingPrice() +"Tk");

            holder.itembinding.totalAmount.setText(":  "+ totalAmount + "Tk");
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }


        holder.itembinding.view.setOnClickListener(v -> {
            try {
                Bundle bundle = new Bundle();
                bundle.putString("RefOrderId", list.get(holder.getAdapterPosition()).getOrderID());
                bundle.putString("vendorId", list.get(holder.getAdapterPosition()).getOrder_vendorID());
                bundle.putString("pageName", "Transfer Details");
                bundle.putString("status", "2");
                StockAllListFragment.pageNumber = 1;
                StockAllListFragment.isFirstLoad = 0;
                Navigation.findNavController(holder.itembinding.getRoot()).navigate(R.id.action_stockAllListFragment_to_transferDetailsFragment, bundle);
            } catch (Exception e) {
                Log.d("ERROR", "" + e.getMessage());
            }
        });
        holder.itembinding.edit.setOnClickListener(v -> {
            try {
                String currentProfileTypeId = PreferenceManager.getInstance(activity).getUserCredentials().getProfileTypeId();
                if (!currentProfileTypeId.equals("7")) {
                    Toasty.info(activity, "you don't  have permission for access this portion", Toasty.LENGTH_LONG).show();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("id", list.get(holder.getAdapterPosition()).getSerialID());
                StockAllListFragment.pageNumber = 1;
                StockAllListFragment.isFirstLoad = 0;
                Navigation.findNavController(holder.itembinding.getRoot()).navigate(R.id.action_stockAllListFragment_to_EditTransferDate, bundle);
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
        private StockOkListLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull StockOkListLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}

