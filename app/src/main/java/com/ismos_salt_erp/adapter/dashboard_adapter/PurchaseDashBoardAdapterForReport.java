package com.ismos_salt_erp.adapter.dashboard_adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.LayoutForDashReportBinding;
import com.ismos_salt_erp.serverResponseModel.TodaysSaleOrPurchaseList;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;

import java.util.List;

public class PurchaseDashBoardAdapterForReport extends RecyclerView.Adapter<PurchaseDashBoardAdapterForReport.ViewHolder> {
    FragmentActivity context;
    List<TodaysSaleOrPurchaseList> lists;
    String type;

    public PurchaseDashBoardAdapterForReport(FragmentActivity context, List<TodaysSaleOrPurchaseList> lists, String type) {
        this.context = context;
        this.lists = lists;
        this.type = type;
    }

    @NonNull
    @Override
    public PurchaseDashBoardAdapterForReport.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutForDashReportBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R
                .layout.layout_for_dash_report, parent, false);
        return new PurchaseDashBoardAdapterForReport.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseDashBoardAdapterForReport.ViewHolder holder, int position) {
        TodaysSaleOrPurchaseList customerList = lists.get(position);
         if (type.equals("1")){
             holder.binding.qtyTv.setText("Purchase Qty");
             holder.binding.enterPriseTv.setText("Supplier Name");
         }if (type.equals("2")){
            holder.binding.enterPriseTv.setText("Customer Name");
            holder.binding.qtyTv.setText("Sale Qty");
         }
        try {
            holder.binding.enterprise.setText(":  " + customerList.getCompanyName());
            holder.binding.qty.setText(":  " +customerList.getTotalQuantity() + " " + MtUtils.kg);
            holder.binding.amount.setText(":  " + DataModify.addFourDigit(customerList.getGrandTotal()) + " " + MtUtils.priceUnit);
        } catch (Exception e) {
        }

    }


    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutForDashReportBinding binding;

        public ViewHolder(@NonNull LayoutForDashReportBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

