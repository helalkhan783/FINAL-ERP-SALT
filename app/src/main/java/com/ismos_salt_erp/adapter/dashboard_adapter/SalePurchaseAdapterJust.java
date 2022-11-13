package com.ismos_salt_erp.adapter.dashboard_adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.SalePurchaseJustLayBinding;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.SalePurchaseListJust;
import com.ismos_salt_erp.utils.replace.DataModify;

import java.util.List;

public class SalePurchaseAdapterJust extends RecyclerView.Adapter<SalePurchaseAdapterJust.ViewHolder> {
    FragmentActivity context;
    List<SalePurchaseListJust> lists;

    public SalePurchaseAdapterJust(FragmentActivity context, List<SalePurchaseListJust> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public SalePurchaseAdapterJust.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SalePurchaseJustLayBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R
                .layout.sale_purchase_just_lay, parent, false);
        return new SalePurchaseAdapterJust.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SalePurchaseAdapterJust.ViewHolder holder, int position) {
        SalePurchaseListJust customerList = lists.get(position);
        int po = position + 1;
      try {
          holder.binding.sl.setText("" + po);
          holder.binding.enterprise.setText("" + customerList.getEnterpriseName());
          holder.binding.amount.setText("" + customerList.getGrandTotal() );
          holder.binding.qty.setText("" + DataModify.kgToTon(customerList.getQuantity()) );

      }catch (Exception e){}
    }
    public Double kgToTon(Double purchaseCm) {

        return purchaseCm/1000;/*(double) (kilogram * 0.001);*/
    }


    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SalePurchaseJustLayBinding binding;

        public ViewHolder(@NonNull SalePurchaseJustLayBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

