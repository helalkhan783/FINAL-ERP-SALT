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
import com.ismos_salt_erp.serverResponseModel.ProductStockListResponse;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.view.fragment.sale.newSale.ItemClick;
import com.ismos_salt_erp.view.fragment.sale.newSale.AddNewSale;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class NewSaleProductListAdapter extends RecyclerView.Adapter<NewSaleProductListAdapter.MyHolder> {
    private FragmentActivity context;
    private List<SalesRequisitionItems> list;
    private List<ProductStockListResponse> productStockResponse;
    private ItemClick itemClick;
    private int quantity;
    int totalPrice, discount, price;
    List<ProductStockListResponse> stockListResponses;

    public NewSaleProductListAdapter(FragmentActivity context,
                                     List<SalesRequisitionItems> list,
                                     List<ProductStockListResponse> productStockResponse,
                                     ItemClick itemClick,List<ProductStockListResponse> stockListResponses) {
        this.context = context;
        this.list = list;
        this.productStockResponse = productStockResponse;
        this.itemClick = itemClick;
        this.stockListResponses = stockListResponses;
    }

    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        NewSaleProductListModelBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.new_sale_product_list_model, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        holder.binding.priceLayout.setVisibility(View.VISIBLE);
        SalesRequisitionItems currentItem = list.get(holder.getAdapterPosition());
        ProductStockListResponse stockQty =   stockListResponses.get(position);


        try {
            holder.binding.productName.setText(currentItem.getProductTitle());
            holder.binding.unit.setText("Unit  : "+currentItem.getUnit_name());
            holder.binding.quantityEt.setText(currentItem.getQuantity());
            holder.binding.priceEt.setText(currentItem.getPrice());
            holder.binding.discountEt.setText(currentItem.getDiscount());
            holder.binding.totalEt.setText(currentItem.getTotalPrice());
            holder.binding.stock.setText(""+stockQty.getStockQty());


            if (Integer.parseInt(currentItem.getQuantity()) > 0) {
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
            int totalQuantity = 0;
            for (int i = 0; i < list.size(); i++) {
                totalQuantity += Integer.parseInt(list.get(i).getQuantity());
                quantity = Integer.parseInt(list.get(i).getQuantity());
                price = Integer.parseInt(list.get(i).getPrice());
                discount = Integer.parseInt(list.get(i).getDiscount());
                totalPrice = Integer.parseInt(list.get(i).getTotalPrice());
            }
            AddNewSale.binding.totalQuantity.setText("Total Quantity: " + String.valueOf(totalQuantity));


            if (productStockResponse != null) {
                for (int i = 0; i < productStockResponse.size(); i++) {
                    if (currentItem.getProductID().equals(productStockResponse.get(i).getProductID())) {
                        holder.binding.stock.setVisibility(View.VISIBLE);
                        holder.binding.stock.setText("Stock Available:" + productStockResponse.get(i).getStockQty());
                    }
                }
            }

        }catch (Exception e){}

        holder.binding.setClickHandle(new NewSaleProductListClickHandle() {
            @Override
            public void addQuantity() {
                if (holder.binding.quantityEt.getText().toString().isEmpty()) {
                    return;
                }
                quantity = Integer.parseInt(holder.binding.quantityEt.getText().toString());
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


                //helal

                insertAllDataToLocal(holder);

                //  itemClick.insertQuantity(holder.getAdapterPosition(), String.valueOf(quantity), list.get(holder.getAdapterPosition()));


                List<String> quantityList = AddNewSale.getAllQuantity();
                if (quantityList.isEmpty()) {
                    return;
                }
                int total = 0;
                for (int i = 0; i < quantityList.size(); i++) {
                    if (quantityList.get(i).isEmpty()) {
                    } else {
                        total += Integer.parseInt(quantityList.get(i));
                    }

                }
                try {
                    AddNewSale.binding.totalQuantity.setText("Total Quantity: " + String.valueOf(total));
                } catch (Exception e) {

                }
            }

            @Override
            public void removeQuantity() {
                if (holder.binding.quantityEt.getText().toString().isEmpty()) {
                    return;
                }
                quantity = Integer.parseInt(holder.binding.quantityEt.getText().toString());
                if (quantity == 0) {
                    holder.binding.quantityEt.setVisibility(View.GONE);
                    holder.binding.removeBtn.setVisibility(View.GONE);
                    holder.binding.unit.setVisibility(View.GONE);
                    return;
                }
                quantity -= 1;
                holder.binding.quantityEt.setText(String.valueOf(quantity));

                insertAllDataToLocal(holder);
                // itemClick.minusQuantity(holder.getAdapterPosition(), String.valueOf(quantity), list.get(holder.getAdapterPosition()));
                List<String> quantityList = AddNewSale.getAllQuantity();
                if (quantityList.isEmpty()) {
                    return;
                }
                int total = 0;
                for (int i = 0; i < quantityList.size(); i++) {
                    if (quantityList.get(i).isEmpty()) {
                    } else {
                        total += Integer.parseInt(quantityList.get(i));
                    }

                }
                AddNewSale.binding.totalQuantity.setText("Total Quantity: " + String.valueOf(total));
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

                if (holder.binding.quantityEt.getText().toString().isEmpty()) {
                    quantity = 0;
                } else {
                    try {
                        quantity = Integer.parseInt(holder.binding.quantityEt.getText().toString());
                    } catch (Exception e) {
                        Log.d("ERROR", e.getLocalizedMessage());
                    }
                }

                insertAllDataToLocal(holder);

                //  itemClick.insertQuantity(holder.getAdapterPosition(), String.valueOf(quantity), list.get(holder.getAdapterPosition()));


                List<String> quantityList = AddNewSale.getAllQuantity();
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
                AddNewSale.binding.totalQuantity.setText("Total Quantity: " + String.valueOf(total));


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.binding.discountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (quantity <= 0) {
                    Toasty.info(context, "Add Quantity", Toasty.LENGTH_LONG).show();
                    return;
                }
                if (holder.binding.discountEt.getText().toString().matches("-?\\d+")) {
                    discount = Integer.parseInt(holder.binding.discountEt.getText().toString());
                }
                insertAllDataToLocal(holder);
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

                if (holder.binding.priceEt.getText().toString() == null) {
                    price = 0;
                }
                if (quantity <= 0) {
                    Toasty.success(context, "At first add quantity", Toasty.LENGTH_LONG).show();
                    return;
                }
                if (holder.binding.priceEt.getText().toString().matches("-?\\d+")) {
                    price = Integer.parseInt(holder.binding.priceEt.getText().toString());

                }

                insertAllDataToLocal(holder);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void insertAllDataToLocal(MyHolder holder) {
        if (quantity > 0) {

            if (holder.binding.priceEt.getText().toString().matches("-?\\d+")) {
                price = Integer.parseInt(holder.binding.priceEt.getText().toString());
            }
            if (holder.binding.discountEt.getText().toString().matches("-?\\d+")) {
                discount = Integer.parseInt(holder.binding.discountEt.getText().toString());

            }
            if (holder.binding.quantityEt.getText().toString().matches("-?\\d+")) {
                quantity = Integer.parseInt(holder.binding.quantityEt.getText().toString());

            }
        }

        int totalPrice1 = quantity * price;
        if (totalPrice1 > 0) {
            if (discount > 0) {
                totalPrice1 = totalPrice1 - discount;
            }
            holder.binding.totalEt.setText("" + totalPrice1);
        }

        if (holder.binding.totalEt.getText().toString().matches("-?\\d+")) {
            totalPrice = Integer.parseInt(holder.binding.totalEt.getText().toString());

        }

        itemClick.insertQuantity(holder.getAdapterPosition(), String.valueOf(quantity), list.get(holder.getAdapterPosition()), String.valueOf(price), String.valueOf(discount), String.valueOf(totalPrice));

    }

    private void countTotalQuantity() {
        List<String> quantityList = AddNewSale.getAllQuantity();
        if (quantityList.isEmpty()) {
            return;
        }
        int total = 0;
        for (int i = 0; i < quantityList.size(); i++) {
            if (quantityList.get(i).isEmpty()) {
            } else {
                total += Integer.parseInt(quantityList.get(i));
            }

        }
        AddNewSale.binding.totalQuantity.setText("Total Quantity: " + String.valueOf(total));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        NewSaleProductListModelBinding binding;

        public MyHolder(NewSaleProductListModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}