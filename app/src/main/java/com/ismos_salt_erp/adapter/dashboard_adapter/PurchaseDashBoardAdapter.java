package com.ismos_salt_erp.adapter.dashboard_adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.TodaySalePurchaseLayBinding;
import com.ismos_salt_erp.serverResponseModel.TodaysSaleOrPurchaseList;
import com.ismos_salt_erp.utils.replace.DataModify;

import java.util.List;

public class PurchaseDashBoardAdapter extends RecyclerView.Adapter<PurchaseDashBoardAdapter.ViewHolder> {
    FragmentActivity context;
    List<TodaysSaleOrPurchaseList> lists;

    public PurchaseDashBoardAdapter(FragmentActivity context, List<TodaysSaleOrPurchaseList> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public PurchaseDashBoardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TodaySalePurchaseLayBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R
                .layout.today_sale_purchase_lay, parent, false);
        return new PurchaseDashBoardAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseDashBoardAdapter.ViewHolder holder, int position) {
        TodaysSaleOrPurchaseList customerList = lists.get(position);
        int po = position + 1;
        holder.binding.sl.setText("" + po);
      try{  holder.binding.enterprise.setText("" + customerList.getCompanyName() +"@"+customerList.getCustomerFname());
          holder.binding.qty.setText("" + DataModify.kgToTon(customerList.getTotalQuantity()) );
          holder.binding.amount.setText("" + DataModify.addFourDigit(customerList.getGrandTotal()));
      }catch (Exception e){}

    }

    public Double kgToTon(Double purchaseCm) {
        double kilogram = purchaseCm;
        return (double) (kilogram * 0.001);
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
