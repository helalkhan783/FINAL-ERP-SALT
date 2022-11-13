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
import com.ismos_salt_erp.clickHandle.SaleHistoryClickHandle;
import com.ismos_salt_erp.databinding.SaleHistoryListLayoutBinding;
import com.ismos_salt_erp.localDatabase.MyDatabaseHelper;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.SaleHistoryList;
import com.ismos_salt_erp.utils.ImageBaseUrl;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.sale.SaleAllListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;


public class SaleHistoryListAdapter extends RecyclerView.Adapter<SaleHistoryListAdapter.viewHolder> {
    private Context context;
    List<SaleHistoryList> lists;
    private MyDatabaseHelper myDatabaseHelper;

    public SaleHistoryListAdapter(Context context, List<SaleHistoryList> lists) {
        this.context = context;
        this.lists = lists;
        myDatabaseHelper = new MyDatabaseHelper(context);
    }

    @NonNull
    @NotNull
    @Override
    public SaleHistoryListAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        SaleHistoryListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.sale_history_list_layout, parent, false);
        return new SaleHistoryListAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SaleHistoryListAdapter.viewHolder holder, int position) {
        SaleHistoryList currentList = lists.get(position);
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1534)) {
            holder.itembinding.view.setVisibility(View.GONE);
        }
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1533)) {
            holder.itembinding.delete.setVisibility(View.GONE);
        }
        holder.itembinding.amountLayout.setVisibility(View.VISIBLE);

        if(currentList.getGrandTotal() != null){
                   holder.itembinding.totalAmount.setText(":  "+ DataModify.addFourDigit(currentList.getGrandTotal()) + MtUtils.priceUnit);
                }
        if (currentList.getDate() == null) {
            holder.itembinding.date.setText(":  ");
        } else {
            holder.itembinding.date.setText(":  " + currentList.getDate());
        }
        if (currentList.getEnterpriseName() == null) {
            holder.itembinding.enterpriseName.setText(":  ");
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

        if (currentList.getCustomerFname() == null) {
            holder.itembinding.processdBy.setText("");
        } else {
            holder.itembinding.processdBy.setText(" " + currentList.getFullName());

        }
        if (currentList.getOrderSerial() == null) {

            holder.itembinding.orderSerial.setText(":");
        } else {
            holder.itembinding.orderSerial.setText(":  " + currentList.getId());

        }
        holder.itembinding.saleOrderId.setText(":  " + currentList.getOrderSerial());

        try {
            Glide.with(context).load(ImageBaseUrl.image_base_url + currentList.getProfilePhoto()).centerCrop().
                    error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).
                    into(holder.itembinding.purchaseImage);

        } catch (NullPointerException e) {
            Log.d("ERROR", e.getMessage());
            Glide.with(context).load(R.mipmap.ic_launcher).into(holder.itembinding.purchaseImage);
        }

        holder.itembinding.view.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("RefOrderId", currentList.getId());
            bundle.putString("orderVendorId", currentList.getOrderVendorID());
            bundle.putString("portion", "PENDING_SALE");
            bundle.putString("porson", "SaleHistoryDetails");
            bundle.putString("grandTotal", currentList.getGrandTotal());
            SaleAllListFragment.pageNumber = 1;

            Navigation.findNavController(holder.itembinding.getRoot()).navigate(R.id.action_saleAllListFragment_to_pendingPurchaseDetailsFragment, bundle);

        });
        holder.itembinding.setClickHandle(new SaleHistoryClickHandle() {
            @Override
            public void edit() {
                try {
                    myDatabaseHelper.deleteAllData();
                } catch (Exception e) {
                    Log.d("ERROR", e.getMessage());
                }

                String currentProfileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
                if (currentProfileTypeId != null) {
                    if (!currentProfileTypeId.equals("7")) {
                        Toasty.info(context, "You don't have permission for access this portion", Toasty.LENGTH_LONG).show();
                        return;
                    }
                }

                Bundle bundle = new Bundle();
                bundle.putString("sid", lists.get(holder.getAdapterPosition()).getSerialID());
                SaleAllListFragment.pageNumber = 1;
                Navigation.findNavController(holder.itembinding.getRoot()).navigate(R.id.action_saleAllListFragment_to_editSaleData, bundle);
            }

            @Override
            public void view() {

            }
        });

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private SaleHistoryListLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull SaleHistoryListLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}
