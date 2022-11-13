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
import com.ismos_salt_erp.serverResponseModel.PurchaseReturnPendingOrderDetail;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PurchaseReturnOrderDetailsAdapter extends RecyclerView.Adapter<PurchaseReturnOrderDetailsAdapter.ViewHolder> {
    private FragmentActivity context;
    private List<PurchaseReturnPendingOrderDetail> lists;


    @NonNull
    @Override
    public PurchaseReturnOrderDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutForPurchaseReturnBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_for_purchase_return, parent, false);
        return new PurchaseReturnOrderDetailsAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseReturnOrderDetailsAdapter.ViewHolder holder, int position) {
        PurchaseReturnPendingOrderDetail list = lists.get(position);
        try {
            holder.binding.ItemNameTv.setText(": " + list.getProductTitle());
            holder.binding.quantityTv.setText(": " + list.getQuantity());
            holder.binding.enterPrisenameTv.setText(": " + list.getStore_name());
            holder.binding.storeTv.setText(": " + list.getSoldFromStoreName());

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
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
