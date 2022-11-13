package com.ismos_salt_erp.adapter.dashboard_adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.LayoutForDashReportBinding;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.TopTenSupplierAndCustomerList;
import com.ismos_salt_erp.utils.MtUtils;

import java.util.List;

public class TopTenCustomerAdapterForReport extends RecyclerView.Adapter<TopTenCustomerAdapterForReport.ViewHolder> {
    FragmentActivity context;
    List<TopTenSupplierAndCustomerList> lists;
    String type;

    public TopTenCustomerAdapterForReport(FragmentActivity context, List<TopTenSupplierAndCustomerList> lists,String type) {
        this.context = context;
        this.lists = lists;
        this.type = type;
    }

    @NonNull
    @Override
    public TopTenCustomerAdapterForReport.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutForDashReportBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R
                .layout.layout_for_dash_report, parent, false);
        return new TopTenCustomerAdapterForReport.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TopTenCustomerAdapterForReport.ViewHolder holder, int position) {
        TopTenSupplierAndCustomerList customerList =  lists.get(position);
        int po=position+1;
        if (type.equals("1")){
            holder.binding.enterPriseTv.setText("Supplier Name");

        } if (type.equals("2,5")){
            holder.binding.enterPriseTv.setText("Customer Name");

        }
         holder.binding.qtyTv.setText("Total Qty");
         try {
            holder.binding.enterprise.setText(":  "+customerList.getCompanyName() + " @ "+customerList.getCustomerFname());
            holder.binding.qty.setText(":  "+ customerList.getTotalQuantity() +" "+ MtUtils.kg);
            holder.binding.amount.setText(":  "+customerList.getGrandTotal() + MtUtils.priceUnit);


        }catch (Exception e){}
    }
    public Double kgToTon(Double purchaseCm) {
        double kilogram = purchaseCm;
        return (double) (kilogram * 0.001);

    }
    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutForDashReportBinding binding;
        public ViewHolder(@NonNull LayoutForDashReportBinding binding) {
            super(binding.getRoot());
            this.binding =binding;
        }
    }
}
