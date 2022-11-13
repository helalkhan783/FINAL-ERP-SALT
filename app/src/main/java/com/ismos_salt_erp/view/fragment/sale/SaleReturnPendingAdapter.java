package com.ismos_salt_erp.view.fragment.sale;

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
import com.ismos_salt_erp.databinding.SalePendingListLayoutBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.SaleReturnPendingList;
import com.ismos_salt_erp.utils.ImageBaseUrl;
import com.ismos_salt_erp.utils.PermissionUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SaleReturnPendingAdapter extends RecyclerView.Adapter<SaleReturnPendingAdapter.viewHolder> {
    private Context context;
    List<SaleReturnPendingList> lists;
    public static String serialId, VendorId;


    @NonNull
    @NotNull
    @Override
    public SaleReturnPendingAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        SalePendingListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.sale_pending_list_layout, parent, false);
        return new SaleReturnPendingAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SaleReturnPendingAdapter.viewHolder holder, int position) {
        SaleReturnPendingList currentList = lists.get(position);
        holder.itembinding.edit.setVisibility(View.GONE);
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1534)) {
            holder.itembinding.view.setVisibility(View.GONE);
        }

        if (currentList.getDate() == null) {
            holder.itembinding.date.setText(":");

        } else {
            holder.itembinding.date.setText(":  " + currentList.getDate());

        }
        if (currentList.getEnterpriseName() == null) {
            holder.itembinding.enterpriseName.setText(":");

        } else {
            holder.itembinding.enterpriseName.setText(":  " + currentList.getEnterpriseName());

        }
        if (currentList.getCompanyName() == null) {
            holder.itembinding.companyName.setText(":  ");

        } else {
            holder.itembinding.companyName.setText(":  " + currentList.getCompanyName());

        }
        if (currentList.getCustomerFname() == null) {
            holder.itembinding.ownerName.setText(":  ");

        } else {
            holder.itembinding.ownerName.setText(":  " + currentList.getCustomerFname());

        }

        holder.itembinding.processdBy.setText(currentList.getFullName());


        if (currentList.getOrderSerial() == null) {
            holder.itembinding.orderSerial.setText(":");
        } else {
            holder.itembinding.orderSerial.setText(":  " + currentList.getOrderSerial());
        }
        VendorId = String.valueOf(currentList.getId());


        try {
            Glide.with(context).load(ImageBaseUrl.image_base_url + currentList.getProfilePhoto()).centerCrop().
                    error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).
                    into(holder.itembinding.saleImage);

        } catch (NullPointerException e) {
            Log.d("ERROR", e.getMessage());
            Glide.with(context).load(R.mipmap.ic_launcher).into(holder.itembinding.saleImage);
        }

        /**
         * now handle onlClick
         */

        holder.itembinding.view.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("RefOrderId",lists.get(holder.getAdapterPosition()).getId());
            bundle.putString("portion", "SALES_RETURNS_DETAILS");
            bundle.putString("orderVendorId", lists.get(holder.getAdapterPosition()).getOrderVendorID());
            // bundle.putString("portion", "PENDING_SALE");
           // bundle.putString("portion", "Sale_Return_Pending");
            bundle.putString("pageName", "Sale Return Pending Details");
            bundle.putString("status", "2");
            SaleAllListFragment.pageNumber = 1;
            Navigation.findNavController(holder.itembinding.getRoot()).navigate(R.id.action_saleAllListFragment_to_purchaseReturnPendingDetailsFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private SalePendingListLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull SalePendingListLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}