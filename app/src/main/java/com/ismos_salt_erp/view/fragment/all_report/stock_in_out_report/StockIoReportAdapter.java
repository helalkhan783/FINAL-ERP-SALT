package com.ismos_salt_erp.view.fragment.all_report.stock_in_out_report;

import android.view.LayoutInflater;
import android.view.View;
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
import com.ismos_salt_erp.view.fragment.all_report.stock_in_out_report.list_response.StockIOReportList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StockIoReportAdapter extends RecyclerView.Adapter<StockIoReportAdapter.viewHolder> {
    private FragmentActivity context;
    List<StockIOReportList> profuctList;

    @NonNull
    @NotNull
    @Override
    public StockIoReportAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        SaleReturnReportListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.sale_return_report_list_layout, parent, false);
        return new StockIoReportAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StockIoReportAdapter.viewHolder holder, int position) {
        StockIOReportList currentItem = profuctList.get(position);
        holder.itembinding.typeLayout.setVisibility(View.VISIBLE);
        try {

            holder.itembinding.name.setText(":  " + currentItem.getCategory());
            holder.itembinding.date.setText(":  " + new DateFormatRight(context, currentItem.getEntryDate()).onlyDayMonthYear());
            holder.itembinding.product.setText(":  " + currentItem.getProductTitle());
            if (currentItem.getBrandName() !=null){
                holder.itembinding.brand.setText(":  " + currentItem.getBrandName());
            }
            holder.itembinding.miller.setText(":  " + currentItem.getEnterprizeName());
            holder.itembinding.store.setText(":  " + currentItem.getStoreName());
            holder.itembinding.supplier.setText(":  " + currentItem.getCustomerFname());
            holder.itembinding.quantity.setText(":  " + currentItem.getQuantity() + MtUtils.kg);
            if (currentItem.getTransferType().equals("6")) {//here 6 means selling price
                setData(holder, currentItem.getSellingPrice(), currentItem.getQuantity(), "Selling Price", "Out");
            }

            if (currentItem.getTransferType().equals("1")) {//here 1 means buying price
                setData(holder, currentItem.getBuyingPrice(), currentItem.getQuantity(), "Buying Price", "In");
            }


        } catch (Exception e) {

        }

    }

    private void setData(viewHolder holder, String price, String quantity, String typePrice, String type) {
        double total = Double.parseDouble(price) * Double.parseDouble(quantity);
        holder.itembinding.subTotal.setText(":  " + DataModify.addFourDigit(String.valueOf(total)) + MtUtils.priceUnit);
        holder.itembinding.price.setText(":  " + DataModify.addFourDigit(price) + MtUtils.priceUnit);
        holder.itembinding.leveTvForInOut.setText(typePrice);//change level text
        holder.itembinding.type.setText(":  " + type);//change level text

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