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
import com.ismos_salt_erp.databinding.SelectedNewConfirmSaleModelBinding;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ConfirmNewSaleSelectedProductListAdapter extends RecyclerView.Adapter<ConfirmNewSaleSelectedProductListAdapter.MyHolder> {
    private FragmentActivity context;
    private List<SalesRequisitionItems> list;

    public ConfirmNewSaleSelectedProductListAdapter(FragmentActivity context, List<SalesRequisitionItems> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        SelectedNewConfirmSaleModelBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.selected_new_confirm_sale_model, parent, false);
        return new ConfirmNewSaleSelectedProductListAdapter.MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {

        SalesRequisitionItems currentItem = list.get(position);
        holder.binding.productName.setText(":  "+currentItem.getProductTitle());
        try {
            if (currentItem.getPrice() == null ){
                holder.binding.forSalePurchaseLayout.setVisibility(View.GONE);

            }

            holder.binding.quantity.setText(":  "+currentItem.getQuantity() +"  "+String.valueOf(currentItem.getUnit_name()));
            holder.binding.perPrice.setText(":  "+currentItem.getPrice());
            holder.binding.discount.setText(":  "+currentItem.getDiscount());
            double total = Double.parseDouble(currentItem.getQuantity())*Double.parseDouble(currentItem.getPrice());


            holder.binding.totalPrice.setText(":  "+total);

        } catch (Exception e) {
            Log.d("ERROR", "ERROR");
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyHolder extends RecyclerView.ViewHolder {
        private SelectedNewConfirmSaleModelBinding binding;

        public MyHolder(SelectedNewConfirmSaleModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
