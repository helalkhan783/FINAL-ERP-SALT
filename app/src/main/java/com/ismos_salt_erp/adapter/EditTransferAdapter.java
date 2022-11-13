package com.ismos_salt_erp.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.clickHandle.NewSaleProductListClickHandle;
import com.ismos_salt_erp.databinding.NewSaleProductListModelBinding;
import com.ismos_salt_erp.serverResponseModel.ProductStockListResponse;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.view.fragment.transfer.edit.EditTransferData;
import com.ismos_salt_erp.view.fragment.transfer.edit.EditTransferItemClick;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class EditTransferAdapter extends RecyclerView.Adapter<EditTransferAdapter.MyHolder> {
    private FragmentActivity context;
    private List<SalesRequisitionItems> list;
    private List<ProductStockListResponse> productStockResponse;
    private EditTransferItemClick itemClick;
    double totalPrice, discount, price,quantity;

    public EditTransferAdapter(FragmentActivity context, List<SalesRequisitionItems> list, List<ProductStockListResponse> productStockResponse, EditTransferItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.productStockResponse = productStockResponse;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewSaleProductListModelBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.new_sale_product_list_model, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.binding.priceLayout.setVisibility(View.VISIBLE);
        holder.binding.delete.setVisibility(View.GONE);
        SalesRequisitionItems currentItem = list.get(holder.getAdapterPosition());
        holder.binding.productName.setText(currentItem.getProductTitle());
        holder.binding.unit.setText(currentItem.getUnit_name());
        holder.binding.quantityEt.setText(currentItem.getQuantity());
        holder.binding.priceEt.setText(currentItem.getPrice());
        holder.binding.discountEt.setText(currentItem.getDiscount());
        holder.binding.totalEt.setText(currentItem.getTotalPrice());

        if (Double.parseDouble(currentItem.getQuantity()) > 0) {
            holder.binding.quantityEt.setVisibility(View.VISIBLE);
            holder.binding.unit.setVisibility(View.VISIBLE);
            holder.binding.removeBtn.setVisibility(View.VISIBLE);
        } else {
            holder.binding.quantityEt.setVisibility(View.VISIBLE);
            holder.binding.unit.setVisibility(View.VISIBLE);
            holder.binding.removeBtn.setVisibility(View.GONE);
        }

        /**
         * now manage loading time total quantity
         */
        double totalQuantity = 0;
        for (int i = 0; i < list.size(); i++) {
            totalQuantity +=Double.parseDouble(list.get(i).getQuantity());

           try {
               if (list.get(i).getPrice().matches("-?\\d+")) {
                   price = Double.parseDouble(list.get(i).getPrice());
               }
               if (list.get(i).getQuantity().matches("-?\\d+")) {
                   quantity = Double.parseDouble(list.get(i).getQuantity());
               }

               if (list.get(i).getDiscount().matches("-?\\d+")) {
                   discount = Double.parseDouble(list.get(i).getDiscount());
               }
               if (list.get(i).getTotalPrice().matches("-?\\d+")) {
                   totalPrice = Double.parseDouble(list.get(i).getTotalPrice());
               }
           }
           catch (Exception e){
               Log.d("Error",e.getMessage());
           }

        }
        EditTransferData.binding.totalQuantity.setText("Total Quantity: " + String.valueOf(totalQuantity));


        if (productStockResponse != null) {
            for (int i = 0; i < productStockResponse.size(); i++) {
                if (currentItem.getProductID().equals(productStockResponse.get(i).getProductID())) {
                    holder.binding.stock.setVisibility(View.VISIBLE);
                    holder.binding.stock.setText("Stock Available:" + productStockResponse.get(i).getStockQty());
                }
            }
        }


        holder.binding.setClickHandle(new NewSaleProductListClickHandle() {
            @Override
            public void addQuantity() {
                if (holder.binding.quantityEt.getText().toString().isEmpty()) {
                    return;
                }
                quantity = Double.parseDouble(holder.binding.quantityEt.getText().toString());
                if (productStockResponse != null) {
                    if (productStockResponse.get(holder.getAdapterPosition()).getStockQty() == quantity) {//for handle stock Available or not
                        Toasty.info(context, "Stock Out", Toasty.LENGTH_LONG).show();
                        return;
                    }
                }
                quantity += 1;
                if (quantity > 0) {
                    holder.binding.quantityEt.setVisibility(View.VISIBLE);
                    holder.binding.unit.setVisibility(View.VISIBLE);
                    holder.binding.removeBtn.setVisibility(View.VISIBLE);
                }
                holder.binding.quantityEt.setText("" + quantity);

                insertDataToLocal(holder);

                // itemClick.insertQuantity(holder.getAdapterPosition(), String.valueOf(quantity), list.get(holder.getAdapterPosition()));


                List<String> quantityList = EditTransferData.getAllQuantity();
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
                    EditTransferData.binding.totalQuantity.setText("Total Quantity: " + String.valueOf(total));
                } catch (Exception e) {

                }
            }

            @Override
            public void removeQuantity() {
                if (holder.binding.quantityEt.getText().toString().isEmpty()) {
                    return;
                }
                quantity = Double.parseDouble(holder.binding.quantityEt.getText().toString());
                if (quantity == 0) {
                    holder.binding.quantityEt.setVisibility(View.GONE);
                    holder.binding.removeBtn.setVisibility(View.GONE);
                    holder.binding.unit.setVisibility(View.GONE);
                    return;
                }
                quantity -= 1;
                holder.binding.quantityEt.setText(String.valueOf(quantity));

                insertDataToLocal(holder);
                // itemClick.minusQuantity(holder.getAdapterPosition(), String.valueOf(quantity), list.get(holder.getAdapterPosition()));
                List<String> quantityList = EditTransferData.getAllQuantity();
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
                EditTransferData.binding.totalQuantity.setText("Total Quantity: " + String.valueOf(total));
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
                insertDataToLocal(holder);
                //  itemClick.insertQuantity(holder.getAdapterPosition(), String.valueOf(quantity), list.get(holder.getAdapterPosition()));


                List<String> quantityList = EditTransferData.getAllQuantity();
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
                EditTransferData.binding.totalQuantity.setText("Total Quantity: " + String.valueOf(total));


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        /**
         * for current data set in price
         */
        holder.binding.priceEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (quantity <= 0) {
                    Toast.makeText(context, "Add Quantity", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (holder.binding.priceEt.getText().toString().matches("-?\\d+")) {
                    price = Double.parseDouble(holder.binding.priceEt.getText().toString());

                }
                insertDataToLocal(holder);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }

    private void insertDataToLocal(MyHolder holder) {
        if (quantity > 0) {


            price = Double.parseDouble(holder.binding.priceEt.getText().toString());

            if (holder.binding.discountEt.getText().toString().matches("-?\\d+")) {
                discount = Double.parseDouble(holder.binding.discountEt.getText().toString());

            }
            if (holder.binding.quantityEt.getText().toString().matches("-?\\d+")) {
                quantity = Double.parseDouble(holder.binding.quantityEt.getText().toString());

            }
        }

        double totalPrice1 = quantity * price;
        if (totalPrice1 > 0) {
            if (discount > 0) {
                totalPrice1 = totalPrice1 - discount;
            }
            holder.binding.totalEt.setText("" + totalPrice1);
        }

        totalPrice = Double.parseDouble(holder.binding.totalEt.getText().toString());
        itemClick.insertQuantity(holder.getAdapterPosition(), String.valueOf(quantity), list.get(holder.getAdapterPosition()), String.valueOf(price), String.valueOf(discount), String.valueOf(totalPrice));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private NewSaleProductListModelBinding binding;

        public MyHolder(NewSaleProductListModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
