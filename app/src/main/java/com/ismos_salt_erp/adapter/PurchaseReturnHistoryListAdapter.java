package com.ismos_salt_erp.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.PurchaseHistoryListLayoutBinding;
import com.ismos_salt_erp.databinding.PurchaseReturnHistoryListLayoutBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.PurchaseHistoryList;
import com.ismos_salt_erp.serverResponseModel.PurchaseReturnHistoryList;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.purchase.AllPurchaseListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;


public class PurchaseReturnHistoryListAdapter extends RecyclerView.Adapter<PurchaseReturnHistoryListAdapter.viewHolder> {
    FragmentActivity activity;
    List<PurchaseHistoryList> lists;
    String datee;

    public PurchaseReturnHistoryListAdapter(FragmentActivity activity, List<PurchaseHistoryList> lists) {
        this.activity = activity;
        this.lists = lists;
    }

    @NonNull
    @NotNull
    @Override
    public PurchaseReturnHistoryListAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        PurchaseHistoryListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.purchase_history_list_layout, parent, false);
        return new PurchaseReturnHistoryListAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PurchaseReturnHistoryListAdapter.viewHolder holder, int position) {
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(activity).getUserCredentials().getPermissions()).contains(1501)) {
            holder.itembinding.view.setVisibility(View.GONE);
        }
        holder.itembinding.idTv.setText("ID");
        holder.itembinding.edit.setVisibility(View.GONE);
        PurchaseHistoryList currentList = lists.get(position);
        holder.itembinding.date.setText(":  " + currentList.getDate());
        datee = currentList.getDate();

        holder.itembinding.date.setText(":  " + currentList.getDate());
        holder.itembinding.enterpriseName.setText(":  " + currentList.getEnterpriseName());
        holder.itembinding.companyName.setText(":  " + currentList.getCompanyName());
        holder.itembinding.ownerName.setText(":  " + currentList.getCustomerFname());
        holder.itembinding.processdBy.setText(currentList.getFullName());
        holder.itembinding.orderSerial.setText(":  " + currentList.getId());
        if (currentList.getGrandTotal() != null) {
            holder.itembinding.totalAmount.setText(":  " + currentList.getGrandTotal() + MtUtils.priceUnit);
        }
        if (currentList.getOrder_type().equals("4")){
            holder.itembinding.purchaseOrderId.setText(":  #PORTN" + currentList.getOrder_serial());

        }  if (currentList.getOrder_type().equals("504")){
            holder.itembinding.purchaseOrderId.setText(":  #POCANC" + currentList.getOrder_serial());

        }

        holder.itembinding.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("RefOrderId", currentList.getId()/*currentList.getOrderID()*/);
                bundle.putString("orderVendorId", currentList.getOrder_vendorID());
                bundle.putString("portion", "PurchaseReturnDetails");
                bundle.putString("pageName", "Purchase Return History Details");
                bundle.putString("enterprise", currentList.getEnterpriseName()/*currentList.getStoreName()*/);
                AllPurchaseListFragment.pageNumber = 1;
                Navigation.findNavController(holder.itembinding.getRoot()).navigate(R.id.action_allPurchaseListFragment_to_purchaseAndSaleReturnDetailsFragment2, bundle);

            }
        });


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