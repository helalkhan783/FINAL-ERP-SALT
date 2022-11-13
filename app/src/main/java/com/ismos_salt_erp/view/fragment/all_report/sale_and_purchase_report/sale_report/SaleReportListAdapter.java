package com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_report;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.SaleReportListLayoutBinding;
import com.ismos_salt_erp.utils.DateFormatRight;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_report.sale_response.SaleReportProDuctList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SaleReportListAdapter extends RecyclerView.Adapter<SaleReportListAdapter.viewHolder> {
    private FragmentActivity context;
    List<SaleReportProDuctList> profuctList;

    @NonNull
    @NotNull
    @Override
    public SaleReportListAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        SaleReportListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.sale_report_list_layout, parent, false);
        return new SaleReportListAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SaleReportListAdapter.viewHolder holder, int position) {
        SaleReportProDuctList currentitem = profuctList.get(position);
        holder.itembinding.supplierTv.setText("Customer");

        try {
            holder.itembinding.price.setText(":  " + DataModify.addFourDigit(currentitem.getSellingPrice()) + MtUtils.priceUnit);
            double total = Double.parseDouble(currentitem.getQuantity()) * Double.parseDouble(currentitem.getSellingPrice());
            holder.itembinding.subTotal.setText(":  " + DataModify.addFourDigit(String.valueOf(total)) + MtUtils.priceUnit);
            holder.itembinding.name.setText(":  " + currentitem.getCategory());
            holder.itembinding.date.setText(":  " + new DateFormatRight(context, currentitem.getEntryDate()).onlyDayMonthYear());
            holder.itembinding.product.setText(":  " + currentitem.getProductTitle());
            if (currentitem.getBrandName() !=null){
                holder.itembinding.brand.setText(String.valueOf(":  " + currentitem.getBrandName()));
            }
            holder.itembinding.miller.setText(":  " + currentitem.getEnterprizeName());
            holder.itembinding.store.setText(":  " + currentitem.getStoreName());
            if (currentitem.getCustomerFname() == null) {
                holder.itembinding.supplier.setText(":  ");
            } else {
                holder.itembinding.supplier.setText(":  " + currentitem.getCustomerFname());

            }
            if (currentitem.getName() != null) {
                holder.itembinding.quantity.setText(":  " + currentitem.getQuantity() + " " + currentitem.getName());
                return;
            }
            holder.itembinding.quantity.setText(":  " + currentitem.getQuantity());


        } catch (Exception e) {
            Log.d(this.getClass().getSimpleName(), e.getLocalizedMessage());
        }


    }

    @Override
    public int getItemCount() {
        return profuctList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private SaleReportListLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull SaleReportListLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}