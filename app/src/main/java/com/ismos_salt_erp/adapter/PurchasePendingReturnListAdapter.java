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

import com.bumptech.glide.Glide;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.PurchaseHistoryListLayoutBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.PurchaseReturnPendingList;
import com.ismos_salt_erp.utils.ImageBaseUrl;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.purchase.AllPurchaseListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PurchasePendingReturnListAdapter extends RecyclerView.Adapter<PurchasePendingReturnListAdapter.viewHolder> {
    FragmentActivity activity;
    List<PurchaseReturnPendingList> lists;

    @NonNull
    @NotNull
    @Override
    public PurchasePendingReturnListAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        PurchaseHistoryListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.purchase_history_list_layout, parent, false);
        return new PurchasePendingReturnListAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PurchasePendingReturnListAdapter.viewHolder holder, int position) {
        holder.itembinding.edit.setVisibility(View.GONE);
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(activity).getUserCredentials().getPermissions()).contains(1501)) {
            holder.itembinding.view.setVisibility(View.GONE);
        }
        PurchaseReturnPendingList currentList = lists.get(position);
        holder.itembinding.date.setText(":  " + currentList.getDate());
        holder.itembinding.enterpriseName.setText(":  " + currentList.getEnterpriseName());
        if ( currentList.getCompanyName() !=null){
            holder.itembinding.companyName.setText(":  " + currentList.getCompanyName());
        }
        holder.itembinding.ownerName.setText(":  " + currentList.getCustomerFname());
        holder.itembinding.processdBy.setText(currentList.getFullName());
        holder.itembinding.orderSerial.setText(":  " +  currentList.getId());
        holder.itembinding.purchaseOrderId.setText(":  " + currentList.getOrder_serial());
        if (currentList.getGrandTotal() != null){
            holder.itembinding.totalAmount.setText(":  "+ DataModify.addFourDigit(currentList.getGrandTotal()) + MtUtils.priceUnit);
        }

        holder.itembinding.view.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("RefOrderId", currentList.getId());
            bundle.putString("orderVendorId", lists.get(holder.getAdapterPosition()).getOrder_vendorID());
            bundle.putString("portion", "PENDING_PURCHASE");
            bundle.putString("pageName", "Pending Purchase Return Details");
            bundle.putString("status", "2");
            AllPurchaseListFragment.pageNumber = 1;
            Navigation.findNavController(holder.itembinding.getRoot()).navigate(R.id.action_allPurchaseListFragment_to_purchaseReturnPendingDetailsFragment, bundle);
        });
        try {
            Glide.with(activity).load(ImageBaseUrl.image_base_url + currentList.getProfilePhoto()).centerCrop().
                    error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).
                    into(holder.itembinding.purchaseImage);

        } catch (NullPointerException e) {
            Log.d("ERROR", e.getMessage());
            Glide.with(activity).load(R.mipmap.ic_launcher).into(holder.itembinding.purchaseImage);
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private PurchaseHistoryListLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull PurchaseHistoryListLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}