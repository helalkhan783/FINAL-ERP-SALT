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
import com.ismos_salt_erp.clickHandle.IodizationHistoryAdapterClick;
import com.ismos_salt_erp.databinding.IodizationHistoryListLayoutBinding;
import com.ismos_salt_erp.localDatabase.MyWashingCrushingHelper;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.IodizationHistoryList;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.production.ProductionAllListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;


public class IodizationHistoryAdapter extends RecyclerView.Adapter<IodizationHistoryAdapter.viewHolder> {
    FragmentActivity activity;
    List<IodizationHistoryList> lists;
    private MyWashingCrushingHelper myWashingCrushingHelper;

    public IodizationHistoryAdapter(FragmentActivity activity, List<IodizationHistoryList> lists) {
        this.activity = activity;
        this.lists = lists;
        myWashingCrushingHelper = new MyWashingCrushingHelper(activity);
    }

    @NonNull
    @NotNull
    @Override
    public IodizationHistoryAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        IodizationHistoryListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R
                .layout.iodization_history_list_layout, parent, false);
        return new IodizationHistoryAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull IodizationHistoryAdapter.viewHolder holder, int position) {
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(activity).getUserCredentials().getPermissions()).contains(1560)) {
            holder.itembinding.details.setVisibility(View.GONE);
        }
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(activity).getUserCredentials().getPermissions()).contains(1331)) {
            holder.itembinding.edit.setVisibility(View.GONE);
        }

        try {
            /**
             * delete all data before go edit iodization history
             */
            myWashingCrushingHelper.deleteAllData();
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getLocalizedMessage());
        }
        IodizationHistoryList currentList = lists.get(position);
        holder.itembinding.date.setText(":  " + currentList.getEntryDate());
        holder.itembinding.itemName.setText(":  " + currentList.getProductTitle());
        holder.itembinding.totalQuantity.setText(":  " + String.valueOf(currentList.getQuantity())  + MtUtils.kg);
        holder.itembinding.store.setText(":  " + currentList.getStoreName());
        holder.itembinding.enterpriseName.setText(":  " + currentList.getEnterpriseName());


        holder.itembinding.setClickHandle(new IodizationHistoryAdapterClick() {
            @Override
            public void view() {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putString("portion", " Iodization Details");
                    bundle.putString("pageName", "Iodization Details");
                    bundle.putString("RefOrderId", lists.get(holder.getAdapterPosition()).getOrderID());
                    bundle.putString("vendorId", lists.get(holder.getAdapterPosition()).getOrder_vendorID());
                    ProductionAllListFragment.pageNumber = 1;
                    Navigation.findNavController(holder.itembinding.getRoot()).navigate(R.id.action_productionAllListFragment_to_pending_iodizationDetailsFragment, bundle);
                } catch (Exception e) {
                    Log.d("ERROR", e.getMessage());
                }
            }

            @Override
            public void edit() {

                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(activity).getUserCredentials().getPermissions()).contains(1331)  ) {
                    Bundle bundle = new Bundle();
                    bundle.putString("sid", lists.get(holder.getAdapterPosition()).getSerialID());
                    bundle.putString("orderId", lists.get(holder.getAdapterPosition()).getOrderID());
                    ProductionAllListFragment.pageNumber = 1;
                    Navigation.findNavController(holder.itembinding.getRoot()).navigate(R.id.action_productionAllListFragment_to_editIodization, bundle);
                    return;
                } else {
                    Toasty.info(activity, "You don't have permission for access this portion", Toasty.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private IodizationHistoryListLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull IodizationHistoryListLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}

