package com.ismos_salt_erp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.PurchaseReportByDateLayoutBinding;
import com.ismos_salt_erp.permission.DateFormatRight;
import com.ismos_salt_erp.serverResponseModel.PurchaseReportProductList;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.purchase_report.PurchaseReturnListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PurchaseReportAdapterByDate extends RecyclerView.Adapter<PurchaseReportAdapterByDate.viewHolder> {
    private FragmentActivity context;
    List<PurchaseReportProductList> lists;

    PurchaseReturnListFragment access;

    public PurchaseReportAdapterByDate(FragmentActivity context, List<PurchaseReportProductList> lists, PurchaseReturnListFragment access) {
        this.context = context;
        this.lists = lists;
        this.access = access;
    }

    @NonNull
    @NotNull
    @Override
    public PurchaseReportAdapterByDate.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        PurchaseReportByDateLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.purchase_report_by_date_layout, parent, false);
        return new PurchaseReportAdapterByDate.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PurchaseReportAdapterByDate.viewHolder holder, int position) {
        try {
            PurchaseReportProductList currentitem = lists.get(position);
            holder.itembinding.discountLayout.setVisibility(View.GONE);
            holder.itembinding.invoiceId.setText(":  " + currentitem.getInvoiceId());
            holder.itembinding.date.setText(":  " + currentitem.getDate());
            holder.itembinding.companyName.setText(":  " + currentitem.getCompany());
            holder.itembinding.supplier.setText(":  " + currentitem.getSupplier());
            holder.itembinding.product.setText(":  " + currentitem.getProduct());
            holder.itembinding.category.setText(":  " + currentitem.getCategory());
            if (currentitem.getBrand() !=null){
                holder.itembinding.brand.setText(":  " + currentitem.getBrand());
            }
            holder.itembinding.quantity.setText(":  " + currentitem.getQuantity());
            holder.itembinding.price.setText(":  " + currentitem.getPrice() +MtUtils.priceUnit);
            holder.itembinding.subTotal.setText(":  " + currentitem.getSubTotal()+MtUtils.priceUnit);
            holder.itembinding.enterprise.setText(":  " + currentitem.getEnterprise());
            holder.itembinding.store.setText(":  " + currentitem.getStore());
            //  holder.itembinding.subTotal.setText(":  "
//                            +access.calculation(currentitem.getDiscount(),currentitem.getQuantity(),currentitem.getBuyingPrice()) + "Tk");
//


        } catch (Exception e) {
            Log.d(this.getClass().getSimpleName(), e.getLocalizedMessage());
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private PurchaseReportByDateLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull PurchaseReportByDateLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}