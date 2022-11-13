package com.ismos_salt_erp.adapter.dashboard_adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.ProductionJustLayBinding;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.ProductionListJust;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;

import java.util.List;

public class ProductionAdapterForReport extends RecyclerView.Adapter<ProductionAdapterForReport.ViewHolder> {
    FragmentActivity context;
    List<ProductionListJust> lists;

    public ProductionAdapterForReport(FragmentActivity context, List<ProductionListJust> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public ProductionAdapterForReport.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductionJustLayBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.production_just_lay, parent, false);
        return new ProductionAdapterForReport.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductionAdapterForReport.ViewHolder holder, int position) {

        try {
            if (position == 0){
                holder.binding.headLayout.setVisibility(View.VISIBLE);
            }
            ProductionListJust customerList = lists.get(position);
            int po = position + 1;
            holder.binding.sl.setText(""+po);

            holder.binding.enterprise.setText(""+customerList.getEnterpriseName());

            holder.binding.enterprise.setText("" + customerList.getEnterpriseName());
            holder.binding.washingType.setText("Washing & Crushing");
            holder.binding.iodizationtype.setText("Iodization");

            holder.binding.washingQty.setText("" + customerList.getIndustrialQuantity() + MtUtils.metricTon);
            holder.binding.iodizationQty.setText("" + customerList.getIodizedQuantity() + MtUtils.metricTon); } catch (Exception e) {
            Log.d("Helal",e.getMessage());
        }

    }



    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ProductionJustLayBinding binding;

        public ViewHolder(@NonNull ProductionJustLayBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

