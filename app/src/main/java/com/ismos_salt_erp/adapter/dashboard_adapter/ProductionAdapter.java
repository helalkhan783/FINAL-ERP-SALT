package com.ismos_salt_erp.adapter.dashboard_adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.TodaySalePurchaseLayBinding;
import com.ismos_salt_erp.serverResponseModel.ProductionList;

import java.util.List;

public class ProductionAdapter extends RecyclerView.Adapter<ProductionAdapter.ViewHolder> {
    FragmentActivity context;
    List<ProductionList> lists;

    public ProductionAdapter(FragmentActivity context, List<ProductionList> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public ProductionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TodaySalePurchaseLayBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R
                .layout.production_layout, parent, false);
        return new ProductionAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductionAdapter.ViewHolder holder, int position) {
        ProductionList customerList = lists.get(position);
        int po = position + 1;
        holder.binding.sl.setText("" + po);
       // holder.binding.enterprise.setText("" + customerList.getGrandTotal());
       // holder.binding.qty.setText("" + kgToTon(Integer.parseInt(customerList.getTotalQuantity())) +" "+ MtUtils.metricTon);
       // holder.binding.amount.setText("" + customerList.getGrandTotal() + " " + MtUtils.priceUnit);
    }

    public Float kgToTon(Integer purchaseCm) {
        float kilogram = purchaseCm;
        return (float) (kilogram * 0.001);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TodaySalePurchaseLayBinding binding;

        public ViewHolder(@NonNull TodaySalePurchaseLayBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
