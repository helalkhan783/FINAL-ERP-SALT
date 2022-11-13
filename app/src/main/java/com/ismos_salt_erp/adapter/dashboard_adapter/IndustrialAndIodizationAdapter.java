package com.ismos_salt_erp.adapter.dashboard_adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.IndustrialIodizationLayBinding;
import com.ismos_salt_erp.databinding.TodaySalePurchaseLayBinding;
import com.ismos_salt_erp.serverResponseModel.ProductionList;

import java.util.List;

public class IndustrialAndIodizationAdapter extends RecyclerView.Adapter<IndustrialAndIodizationAdapter.ViewHolder> {
    FragmentActivity context;
    List<ProductionList> lists;

    public IndustrialAndIodizationAdapter(FragmentActivity context, List<ProductionList> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public IndustrialAndIodizationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IndustrialIodizationLayBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R
                .layout.industrial_iodization_lay, parent, false);
        return new IndustrialAndIodizationAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IndustrialAndIodizationAdapter.ViewHolder holder, int position) {
        ProductionList customerList = lists.get(position);
        int po = position + 1;
        holder.binding.sl.setText("" + po);
        holder.binding.enterprise.setText("" + customerList.getEnterpriseName());
        holder.binding.industrial.setText("" + customerList.getIndustrial());
        holder.binding.iodization.setText("" + customerList.getProduction());

    }


    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        IndustrialIodizationLayBinding binding;

        public ViewHolder(@NonNull IndustrialIodizationLayBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

