package com.ismos_salt_erp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.PacketingListLayoutBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.PacketingProductionListList;
import com.ismos_salt_erp.utils.PermissionUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class   PacketingListAdapter extends RecyclerView.Adapter<PacketingListAdapter.Myholder> {
    private Context context;
    private List<PacketingProductionListList> lists;
            ;

    @NonNull
    @NotNull
    @Override
    public PacketingListAdapter.Myholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        PacketingListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.packeting_list_layout, parent, false);
        return  new PacketingListAdapter.Myholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PacketingListAdapter.Myholder holder, int position) {
      /*    if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1802)) {
            holder.itembinding.details.setVisibility(View.GONE);
        }
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1331)) {
            holder.itembinding.edit.setVisibility(View.GONE);
        }
*/
        PacketingProductionListList currentiIem = lists.get(position);
        if (currentiIem.getEntryDate() == null){
            holder.binding.date.setText(":");
        }else {
            holder.binding.date.setText(":  "+currentiIem.getEntryDate());

        }
        if (currentiIem.getProductTitle() == null){
            holder.binding.itemName.setText(":");
        }else {
            holder.binding.itemName.setText(":  "+currentiIem.getProductTitle());

        }

        if (currentiIem.getQuantity() == null){
            holder.binding.totalQuantity.setText(":");
        }else {
            holder.binding.totalQuantity.setText(":  "+currentiIem.getQuantity());

        }

        if (currentiIem.getStoreName() == null) {
            holder.binding.store.setText(":");

        }   else {
            holder.binding.store.setText(":  "+currentiIem.getStoreName());

        }
        if (currentiIem.getEnterpriseName() == null){
            holder.binding.enterpriseName.setText(":");
        }else {
            holder.binding.enterpriseName.setText(":  "+currentiIem.getEnterpriseName());

        }

        holder.binding.referrerName.setText(":  "+currentiIem.getCustomerFname());

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        private  PacketingListLayoutBinding binding;
        public Myholder(@NonNull @NotNull PacketingListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}