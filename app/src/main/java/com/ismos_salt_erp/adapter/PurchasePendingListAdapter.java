package com.ismos_salt_erp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.PurchasePendingListLayoutBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.PurchasePendingList;
import com.ismos_salt_erp.utils.ImageBaseUrl;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.purchase.AllPurchaseListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PurchasePendingListAdapter extends RecyclerView.Adapter<PurchasePendingListAdapter.viewHolder> {
    private Context context;
    List<PurchasePendingList> lists;

    @NonNull
    @NotNull
    @Override
    public PurchasePendingListAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        PurchasePendingListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.purchase_pending_list_layout, parent, false);
        return new PurchasePendingListAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PurchasePendingListAdapter.viewHolder holder, int position) {
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1500)) {
            holder.itemBinding.edit.setVisibility(View.GONE);
        }
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1501)) {
            holder.itemBinding.view.setVisibility(View.GONE);
        }
        PurchasePendingList currentList = lists.get(position);
        try {
            holder.itemBinding.date.setText(":  " + currentList.getDate());
            holder.itemBinding.enterpriseName.setText(":  " + currentList.getEnterpriseName());
            holder.itemBinding.companyName.setText(":  " + currentList.getCompanyName());
            holder.itemBinding.ownerName.setText(":  " + currentList.getCustomerFname());
            holder.itemBinding.subbmittedBy.setText(currentList.getFullName());
            holder.itemBinding.orderSerial.setText(":  " + currentList.getId());
            if (currentList.getGrandTotal() != null){
                holder.itemBinding.totalAmount.setText(":  "+currentList.getGrandTotal() +" Tk");
            }
            holder.itemBinding.purchaseOrderId.setText(":  " + currentList.getOrderSerial());
            try {
                Glide.with(context).load(ImageBaseUrl.image_base_url + currentList.getProfilePhoto()).centerCrop().
                        error(R.drawable.owner_image).placeholder(R.drawable.owner_image).
                        into(holder.itemBinding.submittedByImnage);

            } catch (NullPointerException e) {
                Log.d("ERROR", e.getMessage());
                Glide.with(context).load(R.drawable.owner_image).into(holder.itemBinding.submittedByImnage);
            }
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
        holder.itemBinding.view.setOnClickListener(v -> {
            try {
                Bundle bundle = new Bundle();
                bundle.putString("RefOrderId", currentList.getId());
                bundle.putString("OrderSerialId", currentList.getOrderSerial());
                bundle.putString("orderVendorId", currentList.getOrderVendorID());
                bundle.putString("portion", "PENDING_PURCHASE");
                bundle.putString("pageName", "Pending Purchase Details");
                bundle.putString("status", "2");
                AllPurchaseListFragment.pageNumber = 1;
                Navigation.findNavController(holder.itemBinding.getRoot()).navigate(R.id.action_allPurchaseListFragment_to_pendingPurchaseDetailsFragment, bundle);

            } catch (Exception e) {
                Log.d("Error", e.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private PurchasePendingListLayoutBinding itemBinding;
        public viewHolder(@NonNull @NotNull PurchasePendingListLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}