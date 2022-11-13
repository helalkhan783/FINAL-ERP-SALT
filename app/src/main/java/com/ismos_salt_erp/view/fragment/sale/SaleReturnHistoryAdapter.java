package com.ismos_salt_erp.view.fragment.sale;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.SaleHistoryListLayoutBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.SaleHistoryList;
import com.ismos_salt_erp.serverResponseModel.SaleReturnHistoryList;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.utils.replace.DataModify;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SaleReturnHistoryAdapter extends RecyclerView.Adapter<SaleReturnHistoryAdapter.viewHolder> {
    private Context context;
    List<SaleHistoryList> lists;
    //  private MyDatabaseHelper myDatabaseHelper;

    public SaleReturnHistoryAdapter(Context context, List<SaleHistoryList> lists) {
        this.context = context;
        this.lists = lists;

    }

    @NonNull
    @NotNull
    @Override
    public SaleReturnHistoryAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        SaleHistoryListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.sale_history_list_layout, parent, false);
        return new SaleReturnHistoryAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SaleReturnHistoryAdapter.viewHolder holder, int position) {
        SaleHistoryList currentList = lists.get(position);
        holder.itembinding.layout.setVisibility(View.GONE);
        holder.itembinding.delete.setVisibility(View.GONE);

        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1534)) {
            holder.itembinding.view.setVisibility(View.GONE);
        }

        holder.itembinding.idTv.setText("ID");

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

        if (currentList.getOrder_type().equals("3")){
            holder.itembinding.saleOrderId.setText(":  #SORTN" + currentList.getOrderSerial());

        } if (currentList.getOrder_type().equals("503")){
            holder.itembinding.saleOrderId.setText(":  #SOCANC" + currentList.getOrderSerial());

        }



        if (currentList.getOrderSerial() == null) {
            holder.itembinding.orderSerial.setText(":");
        } else {
            holder.itembinding.orderSerial.setText(":  " + currentList.getOrderSerial());

        }

        if (currentList.getGrandTotal() != null) {
            holder.itembinding.totalAmount.setText(":  " + DataModify.addFourDigit(currentList.getGrandTotal()) + MtUtils.priceUnit);
        }
        holder.itembinding.view.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("TypeKey", String.valueOf(currentList.getOrder_type()));
            bundle.putString("RefOrderId", currentList.getId());
            bundle.putString("orderVendorId", currentList.getOrderVendorID());
            bundle.putString("portion", "SALES_RETURNS_DETAILS");
            bundle.putString("pageName", "Sales Return Details");
            Navigation.findNavController(holder.itembinding.getRoot()).navigate(R.id.action_saleAllListFragment_to_pendingSalesReturnDetails, bundle);

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
