package com.ismos_salt_erp.view.fragment.sale.salesReurn;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.PurchaseReturnAdapter;
import com.ismos_salt_erp.databinding.PurchaseReturnBinding;
import com.ismos_salt_erp.serverResponseModel.PurchaseItems;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SalesReturnAdapter extends RecyclerView.Adapter<SalesReturnAdapter.MyHolder> {
    private FragmentActivity activity;
    private List<PurchaseItems> items;
    private SalesReturnItemClick itemClick;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PurchaseReturnBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.purchase_return, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        PurchaseItems currentItem = items.get(position);
        try {
            holder.binding.itemName.setText("" + currentItem.getItem());
            holder.binding.quantity.setText("" + currentItem.getQuantity());
            holder.binding.price.setText("" + currentItem.getSellingPrice());
            holder.binding.total.setText("" + Double.parseDouble(currentItem.getSellingPrice()) * Double.parseDouble(currentItem.getQuantity()));
        } catch (Exception e) {
        }
        holder.binding.returnQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String quantity = holder.binding.returnQuantity.getText().toString();
                findSubTotal(holder, quantity);
                if (!quantity.isEmpty()) {

                     itemClick.insertQuantity(holder.getAdapterPosition(), quantity);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {

                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }
            }
        });
    }
    private void findSubTotal(SalesReturnAdapter.MyHolder holder, String quantity) {
        try {
            double price=0,subTotal=0;
            price = Double.parseDouble(holder.binding.price.getText().toString());
            subTotal = price*Double.parseDouble(quantity);
            holder.binding.subTotal.setText(""+subTotal);
        } catch (Exception e){}
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        PurchaseReturnBinding binding;

        public MyHolder(PurchaseReturnBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
