package com.ismos_salt_erp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.PackagingListLayoutBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.PackagingList;
import com.ismos_salt_erp.utils.PermissionUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PackagingListAdapter extends RecyclerView.Adapter<PackagingListAdapter.Myholder> {
    private Context context;
    private List<PackagingList> lists;

    @NonNull
    @NotNull
    @Override
    public PackagingListAdapter.Myholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        PackagingListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater
                .from(parent.getContext()), R.layout.packaging_list_layout, parent, false);
        return  new Myholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PackagingListAdapter.Myholder holder, int position) {

        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1561)) {
            holder.binding.view.setVisibility(View.GONE);
        }
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1455)) {
            holder.binding.edit.setVisibility(View.GONE);
        }
        PackagingList currentiIem = lists.get(position);
        if (currentiIem.getEntryDateTime() == null){
        holder.binding.date.setText(":");
        }else {
            holder.binding.date.setText(":  "+currentiIem.getEntryDateTime());

        }
        if (currentiIem.getProductTitle() == null){
            holder.binding.itemName.setText(":");
        }else {
            holder.binding.itemName.setText(":  "+currentiIem.getProductTitle());

        }
        if (currentiIem.getPackedName() == null){
            holder.binding.packateName.setText(":");
        }else {
            holder.binding.packateName.setText(":  "+currentiIem.getPackedName() );

        }
        if (currentiIem.getProductDimensions()== null){
            holder.binding.weight.setText(":");
        }else {
            holder.binding.weight.setText(":  "+currentiIem.getProductDimensions());

        }
        if (currentiIem.getOriginItemQty() == null){
            holder.binding.totalQuantity.setText(":");
        }else {
            holder.binding.totalQuantity.setText(":  "+currentiIem.getOriginItemQty());

        }
        if (currentiIem.getUnit() == null){
            holder.binding.totalWeight.setText(":");
        }else {
            holder.binding.totalWeight.setText(":  "+currentiIem.getUnit());

        }
        if (currentiIem.getStore() == null) {
            holder.binding.store.setText(":");

        }   else {
            holder.binding.store.setText(":  "+currentiIem.getStore());

        }
        if (currentiIem.getEnterprise() == null){
            holder.binding.enterpriseName.setText(":");
        }else {
            holder.binding.enterpriseName.setText(":  "+currentiIem.getEnterprise());

        }

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        private  PackagingListLayoutBinding binding;
        public Myholder(@NonNull @NotNull PackagingListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
