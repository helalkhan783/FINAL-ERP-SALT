package com.ismos_salt_erp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.LayoutForPurchaseReturnBinding;
import com.ismos_salt_erp.serverResponseModel.PurchaseOrderDetailList;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PurchaseReturnListAdapter extends RecyclerView.Adapter<PurchaseReturnListAdapter.ViewHolder> {
    private FragmentActivity context;
    private List<PurchaseOrderDetailList> lists;
    private String enterprise;

    @NonNull
    @Override
    public PurchaseReturnListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutForPurchaseReturnBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_for_purchase_return, parent, false);


        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseReturnListAdapter.ViewHolder holder, int position) {
        PurchaseOrderDetailList list = lists.get(position);
        try {
            holder.binding.ItemNameTv.setText(": " + list.getProductTitle());
            holder.binding.quantityTv.setText(": " + list.getQuantity());
            holder.binding.enterPrisenameTv.setText(": " + enterprise);
            holder.binding.storeTv.setText(": " + list.getSoldFromStoreName());
            holder.binding.discount.setText(": " + list.getDiscount());
            holder.binding.price.setText(": " + list.getBuyingPrice());
            holder.binding.subtotal.setText(": " +  getSubTotal(list.getQuantity(),list.getBuyingPrice()));

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    private String getSubTotal(String quantity, String buyingPrice) {
        Double total=0.0;
        total =Double.parseDouble(quantity)* Double.parseDouble(buyingPrice);
        return String.valueOf(total);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutForPurchaseReturnBinding binding;

        public ViewHolder(@NonNull LayoutForPurchaseReturnBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
