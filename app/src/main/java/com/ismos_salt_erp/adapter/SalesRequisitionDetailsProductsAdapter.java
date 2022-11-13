package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.ProductListRvBinding;
import com.ismos_salt_erp.databinding.PurchaseHistoryListLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.SalesItemResponse;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * for show single salesRequisition product list
 */
public class SalesRequisitionDetailsProductsAdapter extends RecyclerView.Adapter<SalesRequisitionDetailsProductsAdapter.MyHolder> {
    FragmentActivity context;
    List<SalesItemResponse> itemList;

    public SalesRequisitionDetailsProductsAdapter(FragmentActivity context, List<SalesItemResponse> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductListRvBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.product_list_rv, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        SalesItemResponse currentProduct = itemList.get(position);

        holder.binding.item.setText(":  "+currentProduct.getItem());
        holder.binding.price.setText(":  "+currentProduct.getSellingPrice());
        holder.binding.qty.setText(":  "+currentProduct.getQuantity());
        if (currentProduct.getDiscount() !=null){
            holder.binding.discount.setText(":  "+currentProduct.getDiscount());

        }
        holder.binding.total.setText(":  "+ DataModify.addFourDigit(String.valueOf(currentProduct.getTotalAmount())) + MtUtils.priceUnit);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ProductListRvBinding binding;
        public MyHolder(@NonNull ProductListRvBinding itemView) {
            super(itemView.getRoot());
            this.binding =itemView;

         }
    }
}
