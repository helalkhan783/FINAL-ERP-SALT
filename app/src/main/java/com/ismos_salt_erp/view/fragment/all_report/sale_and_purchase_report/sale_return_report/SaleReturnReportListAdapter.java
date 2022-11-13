package com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_return_report;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.SaleReturnReportListLayoutBinding;
import com.ismos_salt_erp.utils.DateFormatRight;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_return_report.sale_return_report_list.SaleReturnReportProfuctList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SaleReturnReportListAdapter extends RecyclerView.Adapter<SaleReturnReportListAdapter.viewHolder> {
    private FragmentActivity context;
    List<SaleReturnReportProfuctList> profuctList;

    @NonNull
    @NotNull
    @Override
    public SaleReturnReportListAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        SaleReturnReportListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.sale_return_report_list_layout, parent, false);
        return new SaleReturnReportListAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SaleReturnReportListAdapter.viewHolder holder, int position) {
        SaleReturnReportProfuctList currentitem = profuctList.get(position);
        holder.itembinding.name.setText(":  " + currentitem.getCategory());
        holder.itembinding.date.setText(":  " + new DateFormatRight(context,currentitem.getEntryDate()).onlyDayMonthYear());
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
        holder.itembinding.quantity.setText(":  " + DataModify.addThreeDigit(currentitem.getQuantity()) + MtUtils.kg);
        holder.itembinding.price.setText(":  "+ DataModify.addFourDigit(currentitem.getSellingPrice()) +MtUtils.priceUnit);
       try {
           holder.itembinding.subTotal.setText(":  "+ DataModify.addFourDigit(String.valueOf(Double.parseDouble(currentitem.getSellingPrice())
                   *Double.parseDouble(currentitem.getQuantity())))+MtUtils.priceUnit);
       }catch (Exception e){}
    }

    @Override
    public int getItemCount() {
        return profuctList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private SaleReturnReportListLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull SaleReturnReportListLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}