package com.ismos_salt_erp.adapter.custom_confirm_sale_adapter;

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
import com.ismos_salt_erp.databinding.LayoutOneBinding;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.purchase.newPurchase.ConfirmPurchase;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConfirmPurchaseAdapterOne extends RecyclerView.Adapter<ConfirmPurchaseAdapterOne.MyHolder> {
    private FragmentActivity context;
    private List<SalesRequisitionItems> list;
    SalesRequisitionItems currentItem;
    ConfirmPurchase click;

    public ConfirmPurchaseAdapterOne(FragmentActivity context, List<SalesRequisitionItems> list, ConfirmPurchase click) {
        this.context = context;
        this.list = list;
        this.click = click;
    }

    @NonNull
    @NotNull
    @Override
    public ConfirmPurchaseAdapterOne.MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutOneBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.layout_one, parent, false);
        return new ConfirmPurchaseAdapterOne.MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ConfirmPurchaseAdapterOne.MyHolder holder, int position) {

        currentItem = list.get(position);
        holder.binding.productName.setText("" + currentItem.getProductTitle());


        holder.binding.qtyLayout.setVisibility(View.VISIBLE);

        holder.binding.qtyEt.setText("" + currentItem.getQuantity());
        holder.binding.priceEt.setText("" + currentItem.getPrice());
        //  holder.binding.priceTv.setText(":  "+currentItem.getPrice());
        //  holder.binding.discount.setText(":  "+currentItem.getDiscount());
        double total = Double.parseDouble(currentItem.getQuantity()) * Double.parseDouble(currentItem.getPrice());


        holder.binding.priceTv.setText("" + total);


        holder.binding.qtyEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (holder.binding.qtyEt.getText().toString().isEmpty()) {

                } else {
                    try {
                        double quantity = Double.parseDouble(holder.binding.qtyEt.getText().toString());
                        double price = Double.parseDouble(holder.binding.priceEt.getText().toString());

                        Update(quantity, price, currentItem.getProductID(), holder, currentItem);

                    } catch (Exception e) {
                        Log.d("ERROR", e.getLocalizedMessage());
                    }
                }


                //  itemClick.insertQuantity(holder.getAdapterPosition(), String.valueOf(quantity), list.get(holder.getAdapterPosition()));


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        holder.binding.priceEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (holder.binding.priceEt.getText().toString().isEmpty()) {

                } else {
                    try {
                        double quantity = Double.parseDouble(holder.binding.qtyEt.getText().toString());
                        double price = Double.parseDouble(holder.binding.priceEt.getText().toString());

                        Update(quantity, price,currentItem.getProductID(), holder, currentItem);

                    } catch (Exception e) {
                        Log.d("ERROR", e.getLocalizedMessage());
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        holder.binding.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.removeBtn(holder.getAdapterPosition());

            }
        });
    }

    private void Update(double totalQty, double price, String productID, ConfirmPurchaseAdapterOne.MyHolder
            holder, SalesRequisitionItems currentProduct) {
        double totalPrice = totalQty * price;
        holder.binding.priceTv.setText("" + DataModify.addFourDigit(String.valueOf(totalPrice)));
        click.updateConfirm(holder.getAdapterPosition(), String.valueOf(totalQty), list.get(holder.getAdapterPosition()), String.valueOf(price), "0", "0", currentProduct.getProductID());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private LayoutOneBinding binding;

        public MyHolder(LayoutOneBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

