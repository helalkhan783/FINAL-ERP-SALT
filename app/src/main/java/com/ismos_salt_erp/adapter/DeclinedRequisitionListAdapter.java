package com.ismos_salt_erp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.serverResponseModel.DeclinedRequisitionListResponse;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.viewModel.DeclinedRequisitionListViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * for show all declined requisition list
 */
public class DeclinedRequisitionListAdapter extends RecyclerView.Adapter<DeclinedRequisitionListAdapter.MyHolder> {
    private final DeclinedRequisitionListViewModel declinedRequisitionListViewModel;
    FragmentActivity context;
    List<DeclinedRequisitionListResponse> declinedRequisitionListResponseList;

    public DeclinedRequisitionListAdapter(FragmentActivity context, List<DeclinedRequisitionListResponse> declinedRequisitionListResponseList) {
        this.context = context;
        this.declinedRequisitionListResponseList = declinedRequisitionListResponseList;
        declinedRequisitionListViewModel = ViewModelProviders.of(context).get(DeclinedRequisitionListViewModel.class);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.declined_requisition_list_item, parent, false);
        return new MyHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        DeclinedRequisitionListResponse currentRequisition = declinedRequisitionListResponseList.get(position);
        holder.orderId.setText(":  #" + currentRequisition.getOrderID());

        holder.companyName.setText(":  " + currentRequisition.getCompanyName() +"@" +currentRequisition.getOwnerName());
        holder.enterPriseName.setText(":  " + currentRequisition.getEnterpriseName());
        holder.processBy.setText(":  " + currentRequisition.getFullName());
        holder.startDate.setText(":  " + currentRequisition.getRequisitionDate());
        holder.endDate.setText(":  " + currentRequisition.getRequisitionEndDate());
        holder.totalAmount.setText(":  " + DataModify.addFourDigit(currentRequisition.getOrderAmount()) + MtUtils.priceUnit);
    }

    @Override
    public int getItemCount() {
        return declinedRequisitionListResponseList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.orderId)
        TextView orderId;

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

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
