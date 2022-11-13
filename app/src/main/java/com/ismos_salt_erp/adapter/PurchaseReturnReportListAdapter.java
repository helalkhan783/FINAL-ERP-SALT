package com.ismos_salt_erp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.PurchaseReturnReportLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.PurchaseReturnReportList;
import com.ismos_salt_erp.utils.DateFormatRight;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PurchaseReturnReportListAdapter extends RecyclerView.Adapter<PurchaseReturnReportListAdapter.viewHolder> {
    private FragmentActivity context;
    List<PurchaseReturnReportList> profuctList;

    @NonNull
    @NotNull
    @Override
    public PurchaseReturnReportListAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        PurchaseReturnReportLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.purchase_return_report_layout, parent, false);
        return new PurchaseReturnReportListAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PurchaseReturnReportListAdapter.viewHolder holder, int position) {
        try {
            PurchaseReturnReportList currentitem = profuctList.get(position);
            holder.itemBinding.name.setText(":  " + currentitem.getCategory());
            holder.itemBinding.date.setText(":  " + new DateFormatRight(context,currentitem.getEntryDate()).onlyDayMonthYear());
            holder.itemBinding.product.setText(":  " + currentitem.getProductTitle());
            if (currentitem.getBrandName() == null) {
                holder.itemBinding.brand.setText(":  ");
            } else {
                holder.itemBinding.brand.setText(":  " + currentitem.getBrandName());

            }
            holder.itemBinding.miller.setText(":  " + currentitem.getEnterprizeName());
            holder.itemBinding.store.setText(":  " + currentitem.getStoreName());
            if (currentitem.getCustomerFname() != null) {
                holder.itemBinding.supplier.setText(":  " + currentitem.getCustomerFname());
            }
            holder.itemBinding.quantity.setText(":  " + currentitem.getQuantity() + MtUtils.kg);
            holder.itemBinding.price.setText(":  " + DataModify.addFourDigit(currentitem.getBuyingPrice())  +MtUtils.priceUnit);
            double total =  Double.parseDouble(currentitem.getBuyingPrice()) * Double.parseDouble(currentitem.getQuantity());

            holder.itemBinding.subTotal.setText(":  " + DataModify.addFourDigit(String.valueOf(total))+ MtUtils.priceUnit);
        } catch (Exception e) {
            Log.d(this.getClass().getSimpleName(), e.getLocalizedMessage());
        }
    }

    @Override
    public int getItemCount() {
        return profuctList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private PurchaseReturnReportLayoutBinding itemBinding;

        public viewHolder(@NonNull @NotNull PurchaseReturnReportLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}