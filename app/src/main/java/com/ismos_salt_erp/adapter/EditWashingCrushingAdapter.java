package com.ismos_salt_erp.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.clickHandle.NewSaleProductListClickHandle;
import com.ismos_salt_erp.databinding.NewSaleProductListModelBinding;
import com.ismos_salt_erp.databinding.ProductionOrderModelBinding;
import com.ismos_salt_erp.serverResponseModel.WashingCrushingModel;
import com.ismos_salt_erp.view.fragment.production.washingAndCrushing.edit.EditWashingCrushing;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EditWashingCrushingAdapter extends RecyclerView.Adapter<EditWashingCrushingAdapter.MyHolder> {
    private FragmentActivity context;
    private List<WashingCrushingModel> list;
    private EditWashingCrushing itemClick;

    public EditWashingCrushingAdapter(FragmentActivity context, List<WashingCrushingModel> itemsList, EditWashingCrushing editSaleData) {
        this.context = context;
        this.list = itemsList;
        this.itemClick = editSaleData;
    }

    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ProductionOrderModelBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.production_order_model, parent, false);

        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        WashingCrushingModel currentItem = list.get(holder.getAdapterPosition());
        holder.binding.delete.setVisibility(View.GONE);
        holder.binding.productName.setText(currentItem.getProductTitle());
        holder.binding.unit.setText(currentItem.getUnit_name());
        holder.binding.quantityEt.setText(currentItem.getQuantity());

        holder.binding.stoclLayout.setVisibility(View.GONE);
        if (currentItem.getQuantity() != null) {
            if (Double.parseDouble(currentItem.getQuantity()) > 0) {
                try {
                    holder.binding.quantityEt.setVisibility(View.VISIBLE);
                    holder.binding.unit.setVisibility(View.VISIBLE);
                    holder.binding.removeBtn.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                }
            } else {
                holder.binding.quantityEt.setVisibility(View.VISIBLE);
                holder.binding.unit.setVisibility(View.VISIBLE);
                holder.binding.removeBtn.setVisibility(View.GONE);
            }
        }
        /**
         * now manage loading time total quantity
         */
        double totalQuantity = 0;
        for (int i = 0; i < list.size(); i++) {
            String currentQuantity = list.get(i).getQuantity();
            if (currentQuantity == null) {
                currentQuantity = "0";
            }
            totalQuantity += Double.parseDouble(currentQuantity);
        }
        EditWashingCrushing.binding.totalQuantity.setText("Total Quantity: " + String.valueOf(totalQuantity));


        holder.binding.setClickHandle(new NewSaleProductListClickHandle() {
            @Override
            public void addQuantity() {
                double quantity = 0;
                if (holder.binding.quantityEt.getText().toString().isEmpty()) {
                    quantity = 0;
                } else {
                    quantity = Double.parseDouble(holder.binding.quantityEt.getText().toString());
                }
               /* if (productStockResponse != null) {
                    if (productStockResponse.get(holder.getAdapterPosition()).getStockQty() == quantity) {//for handle stock Available or not
                        Toasty.info(context, "Stock Out", Toasty.LENGTH_LONG).show();
                        return;
                    }
                }*/
                quantity += 1;
                if (quantity >= 0) {
                    holder.binding.quantityEt.setVisibility(View.VISIBLE);
                    holder.binding.unit.setVisibility(View.VISIBLE);
                    holder.binding.removeBtn.setVisibility(View.VISIBLE);
                }
                holder.binding.quantityEt.setText("" + quantity);
                itemClick.
                        insertQuantity(holder.getAdapterPosition(), String.valueOf(quantity), list.get(holder.getAdapterPosition()));


                List<String> quantityList = EditWashingCrushing.getAllQuantity();
                if (quantityList.isEmpty()) {
                    return;
                }
                double total = 0;
                for (int i = 0; i < quantityList.size(); i++) {
                    if (quantityList.get(i).isEmpty()) {
                    } else {
                        total += Double.parseDouble(quantityList.get(i));
                    }

                }
                try {
                    EditWashingCrushing.binding.totalQuantity.setText("Total Quantity: " + String.valueOf(total));
                } catch (Exception e) {

                }
            }

            @Override
            public void removeQuantity() {
                double quantity = 0;
                if (holder.binding.quantityEt.getText().toString().isEmpty()) {
                    quantity = 0;
                } else {
                    quantity = Double.parseDouble(holder.binding.quantityEt.getText().toString());
                }
                if (quantity == 0) {
                    holder.binding.quantityEt.setVisibility(View.GONE);
                    holder.binding.removeBtn.setVisibility(View.GONE);
                    holder.binding.unit.setVisibility(View.GONE);
                    return;
                }
                quantity -= 1;
                holder.binding.quantityEt.setText(String.valueOf(quantity));
                itemClick.minusQuantity(holder.getAdapterPosition(), String.valueOf(quantity), list.get(holder.getAdapterPosition()));
                List<String> quantityList = EditWashingCrushing.getAllQuantity();
                if (quantityList.isEmpty()) {
                    return;
                }
                double total = 0;
                for (int i = 0; i < quantityList.size(); i++) {
                    if (quantityList.get(i).isEmpty()) {
                    } else {
                        total += Double.parseDouble(quantityList.get(i));
                    }

                }
                EditWashingCrushing.binding.totalQuantity.setText("Total Quantity: " + String.valueOf(total));
            }

            @Override
            public void delete() {
                itemClick.removeBtn(holder.getAdapterPosition());
            }
        });
        /**
         * for check real time
         */
        holder.binding.quantityEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                double quantity = 0;
                if (holder.binding.quantityEt.getText().toString().isEmpty()) {

                } else {
                    try {
                        quantity = Double.parseDouble(holder.binding.quantityEt.getText().toString());
                    } catch (Exception e) {
                        Log.d("ERROR", e.getLocalizedMessage());
                    }
                }
                itemClick.insertQuantity(holder.getAdapterPosition(), String.valueOf(quantity), list.get(holder.getAdapterPosition()));


                List<String> quantityList = EditWashingCrushing.getAllQuantity();
                if (quantityList.isEmpty()) {
                    return;
                }
                double total = 0.0;
                for (int i = 0; i < quantityList.size(); i++) {
                    if (quantityList.get(i).isEmpty()) {

                    } else {
                        total += Double.parseDouble(quantityList.get(i));
                    }
                }
                EditWashingCrushing.binding.totalQuantity.setText("Total Quantity: " + String.valueOf(total));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        private ProductionOrderModelBinding binding;

        public MyHolder(ProductionOrderModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
