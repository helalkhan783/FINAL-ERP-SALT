package com.ismos_salt_erp.adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionListResponse;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.viewModel.SalesRequisitionListViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * for show all sales requisition list
 */
public class RequisitionListAdapter extends RecyclerView.Adapter<RequisitionListAdapter.MyHolder> {
    private SalesRequisitionListViewModel salesRequisitionListViewModel;
    FragmentActivity context;
    List<SalesRequisitionListResponse> salesRequisitionList;

    public RequisitionListAdapter(FragmentActivity context, List<SalesRequisitionListResponse> salesRequisitionList) {
        this.context = context;
        this.salesRequisitionList = salesRequisitionList;
        salesRequisitionListViewModel = ViewModelProviders.of(context).get(SalesRequisitionListViewModel.class);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.requisition_list, parent, false);
        return new MyHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        SalesRequisitionListResponse currentRequisition = salesRequisitionList.get(position);
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1807)) {
            holder.view.setVisibility(View.GONE);

        }

        holder.id.setText(": #"+currentRequisition.getId());
        holder.ownerName.setText(":  " + currentRequisition.getCustomerFname());
        holder.companyName.setText(":  " + currentRequisition.getCompanyName());
        holder.enterPriseName.setText(":  " + currentRequisition.getEnterpriseName());
        holder.processBy.setText(":  " + currentRequisition.getFullName());
        holder.startDate.setText(":  " + currentRequisition.getDate());
        holder.endDate.setText(":  " + currentRequisition.getEndDate());
        holder.totalAmount.setText(":  " + currentRequisition.getGrandTotal() + " " + MtUtils.priceUnit);

        /***
         * For get single requisition details
         */
        holder.itemView.setOnClickListener(v -> {
            goToDetails(currentRequisition.getId(), v);
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDetails(currentRequisition.getId(), v);
            }
        });


    }


    private void goToDetails(String id, View v) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        Navigation.createNavigateOnClickListener(R.id.action_salesRequisitionList_to_singleSalesRequisitionDetails, bundle).onClick(v);
        return;
    }

    @Override
    public int getItemCount() {
        return salesRequisitionList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ownerNameEt)
        TextView ownerName;
        @BindView(R.id.companyName)
        TextView companyName;
        @BindView(R.id.enterPriseName)
        TextView enterPriseName;
        @BindView(R.id.processBy)
        TextView processBy;
        @BindView(R.id.startDate)
        TextView startDate;
        @BindView(R.id.endDate)
        TextView endDate;
        @BindView(R.id.totalAmount)
        TextView totalAmount;
        @BindView(R.id.id)
        TextView id;

        @BindView(R.id.view)
        ImageButton view;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
