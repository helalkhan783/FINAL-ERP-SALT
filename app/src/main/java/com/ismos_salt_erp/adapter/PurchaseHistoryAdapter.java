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
import com.ismos_salt_erp.clickHandle.PurchaseEditClickHandle;
import com.ismos_salt_erp.databinding.PurchaseHistoryListLayoutBinding;
import com.ismos_salt_erp.localDatabase.MyDatabaseHelper;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.PurchaseHistoryList;
import com.ismos_salt_erp.utils.ImageBaseUrl;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.purchase.AllPurchaseListFragment;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PurchaseHistoryAdapter extends RecyclerView.Adapter<PurchaseHistoryAdapter.viewHolder> {
    private Context context;
    List<PurchaseHistoryList> lists;
    private MyDatabaseHelper myDatabaseHelper;
    private String datee;

    public PurchaseHistoryAdapter(Context context, List<PurchaseHistoryList> lists) {
        this.context = context;
        this.lists = lists;
        myDatabaseHelper = new MyDatabaseHelper(context);
    }

    @NonNull
    @NotNull
    @Override
    public PurchaseHistoryAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        PurchaseHistoryListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.purchase_history_list_layout, parent, false);
        return new PurchaseHistoryAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PurchaseHistoryAdapter.viewHolder holder, int position) {

        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1500)) {
            holder.itembinding.edit.setVisibility(View.GONE);

        }
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1501)) {
            holder.itembinding.view.setVisibility(View.GONE);
        }

        PurchaseHistoryList currentList = lists.get(position);
        holder.itembinding.date.setText(":  " + currentList.getDate());
        datee = currentList.getDate();

      holder.itembinding.date.setText(":  "+currentList.getDate());
        holder.itembinding.enterpriseName.setText(":  " + currentList.getEnterpriseName());
        holder.itembinding.companyName.setText(":  " + currentList.getCompanyName());
        holder.itembinding.ownerName.setText(":  " + currentList.getCustomerFname());
        holder.itembinding.processdBy.setText(currentList.getFullName());
        holder.itembinding.orderSerial.setText(":  " + currentList.getId());
        if (currentList.getGrandTotal() != null){
            holder.itembinding.totalAmount.setText(":  "+ currentList.getGrandTotal()+ MtUtils.priceUnit);
        }
        holder.itembinding.purchaseOrderId.setText(":  " + currentList.getOrder_serial());

        holder.itembinding.setClickHandle(new PurchaseEditClickHandle() {
            @Override
            public void view() {
                /**
                 * will add view
                 */
            }

            @Override
            public void edit() {
                try {
                    myDatabaseHelper.deleteAllData();
                } catch (Exception e) {
                    Log.d("ERROR", e.getLocalizedMessage());
                }

                Bundle bundle = new Bundle();
                bundle.putString("sid", lists.get(holder.getAdapterPosition()).getSerialID());
                AllPurchaseListFragment.pageNumber = 1;
                Navigation.findNavController(holder.itembinding.getRoot()).navigate(R.id.action_allPurchaseListFragment_to_editPurchaseData, bundle);
            }
        });
        holder.itembinding.view.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("RefOrderId", currentList.getId());
            bundle.putString("orderVendorId", currentList.getOrder_vendorID());
            bundle.putString("portion", "PENDING_PURCHASE");
            bundle.putString("porson", "PurchaseHistoryDetails");
            bundle.putString("pageName", "Purchase History Details");
            AllPurchaseListFragment.pageNumber = 1;
            Navigation.findNavController(holder.itembinding.getRoot()).navigate(R.id.action_allPurchaseListFragment_to_pendingPurchaseDetailsFragment, bundle);

        });
/*
        holder.itembinding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.createNavigateOnClickListener(R.id.action_saleAllListFragment_to_millerAllListFragment, bundle).onClick(v);
            }
        });
*/

        try {
            Glide.with(context).load(ImageBaseUrl.image_base_url + currentList.getProfilePhoto()).centerCrop().
                    error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).
                    into(holder.itembinding.purchaseImage);

        } catch (NullPointerException e) {
            Log.d("ERROR", e.getMessage());
            Glide.with(context).load(R.mipmap.ic_launcher).into(holder.itembinding.purchaseImage);
        }
    }

    private String setDate() {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd-MMM-yyyy h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date ;
        String str = null;

        try {
            date = inputFormat.parse(datee);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
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