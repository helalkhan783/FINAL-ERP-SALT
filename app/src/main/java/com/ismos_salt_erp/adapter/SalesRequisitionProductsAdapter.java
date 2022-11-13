package com.ismos_salt_erp.adapter;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItemsResponse;
import com.ismos_salt_erp.view.fragment.salesRequisition.NewSaleFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class SalesRequisitionProductsAdapter extends RecyclerView.Adapter<SalesRequisitionProductsAdapter.MyHolder> {
    private boolean isDataFetching = false; // variable to detect whether server data fetch is loading or not
    FragmentActivity context;
    public static List<SalesRequisitionItemsResponse> selectedProductsList;
    public static List<SalesRequisitionItems> salesRequisitionItemsList;
    private int quantity;
    int totalPrice, discount, price;
    public SalesRequisitionProductsAdapter(FragmentActivity context, List<SalesRequisitionItemsResponse> selectedProductsList, List<SalesRequisitionItems> salesRequisitionItemsList) {
        this.context = context;
        this.selectedProductsList = selectedProductsList;
        this.salesRequisitionItemsList = salesRequisitionItemsList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sales_requisition_search_item_model, parent, false);
        return new MyHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.quantityEt.setText("0.0");
        NewSaleFragment.totalSubmitBtn.setText("0.0");
        if (selectedProductsList != null) {
            holder.productName.setText(selectedProductsList.get(position).getProductTitle());
        } else {
            holder.productName.setText(salesRequisitionItemsList.get(position).getProductTitle());
        }
        holder.priceEt.setHint("Total" + " " + "0.0");
        holder.priceEt.setEnabled(false);
        holder.priceEt.setHintTextColor(R.color.black);

        holder.addBtn.setOnClickListener(v -> {


            double currentQuantity = Double.parseDouble(holder.quantityEt.getText().toString());

            holder.priceEditCancel.setVisibility(View.GONE);
            String currentPrice = holder.priceEt.getText().toString();
            if (currentPrice.isEmpty()) {
                Toasty.info(context, "Please Enter Your Amount", Toasty.LENGTH_LONG).show();
                holder.priceEditBtn.setVisibility(View.VISIBLE);
                return;
            }
            double currentAPrice = Double.parseDouble(holder.priceEt.getText().toString());

            holder.removeBtn.setVisibility(View.VISIBLE);
            holder.priceEditBtn.setVisibility(View.VISIBLE);
            holder.quantityEt.setVisibility(View.VISIBLE);

            currentQuantity += 1;
            holder.quantityEt.setText(String.valueOf(currentQuantity));

            /**
             * if user don't give any amount
             */
            if (currentAPrice == 0) {
                Toasty.info(context, "Please Enter Your Amount", Toasty.LENGTH_LONG).show();
                currentQuantity = 0;
                holder.quantityEt.setText(String.valueOf(currentQuantity));
                holder.removeBtn.setVisibility(View.GONE);
                return;
            }
            /**
             * now set the total based on quantity and cost
             */
            if (currentQuantity != 0 && Double.parseDouble(holder.priceEt.getText().toString()) != 0) {

                NewSaleFragment.totalSubmitBtn.setText("Total" + " " + NewSaleFragment.getTotalPrice());

            }
        });
        holder.removeBtn.setOnClickListener(v -> {

            double currentQuantity = Double.parseDouble(holder.quantityEt.getText().toString());


            if (currentQuantity != 0) {
                currentQuantity -= 1;

                holder.quantityEt.setText(String.valueOf(currentQuantity));

                if (Double.parseDouble(holder.priceEt.getText().toString()) != 0) {
/*

                    double currentTotal = 1 * Double.parseDouble(holder.priceEt.getText().toString());
                    double mainTotal = Double.parseDouble(NewSaleFragment.totalSubmitBtn.getText().toString());

                    double sendTotal = mainTotal - currentTotal;*/
                    // NewSaleFragment.totalSubmitBtn.setText(String.valueOf(sendTotal));
                    NewSaleFragment.totalSubmitBtn.setText("Total" + " " + NewSaleFragment.getTotalPrice());
                }
                if (currentQuantity == 0) {
                    holder.removeBtn.setVisibility(View.GONE);
                    holder.priceEditBtn.setVisibility(View.GONE);
                    holder.removeBtn.setVisibility(View.GONE);
                    holder.priceEt.setEnabled(false);
                    holder.priceEt.setClickable(false);
                }
                return;
            }
            holder.removeBtn.setVisibility(View.GONE);
            holder.priceEditBtn.setVisibility(View.GONE);
        });
        holder.priceEditBtn.setOnClickListener(v -> {
            holder.priceEt.setClickable(true);
            holder.priceEt.setEnabled(true);
            holder.priceEditBtn.setVisibility(View.GONE);
            holder.priceEditCancel.setVisibility(View.VISIBLE);
        });
        holder.priceEditCancel.setOnClickListener(v -> {
            holder.priceEditCancel.setVisibility(View.GONE);
            holder.priceEditBtn.setVisibility(View.VISIBLE);
            holder.priceEt.setClickable(false);
            holder.priceEt.setEnabled(false);
        });

        holder.priceEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (holder.priceEt.isPerformingCompletion()) {
                    return;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (!charSequence.toString().trim().isEmpty() && !isDataFetching) {
                    if (!holder.priceEt.getText().toString().isEmpty() || Double.parseDouble(holder.priceEt.getText().toString()) != 0) {
                        //double currentQuantity = Double.parseDouble(holder.quantityEt.getText().toString());
                        NewSaleFragment.totalSubmitBtn.setText("Total" + " " + NewSaleFragment.getTotalPrice());
                        isDataFetching = false;
                        return;
                    }
                    NewSaleFragment.totalSubmitBtn.setText("Total" + " " + NewSaleFragment.getTotalPrice());
                    isDataFetching = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.quantityEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (holder.quantityEt.isPerformingCompletion()) {
                    return;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (!charSequence.toString().trim().isEmpty() && !isDataFetching) {

                    if (!holder.quantityEt.getText().toString().isEmpty() || Double.parseDouble(holder.quantityEt.getText().toString()) != 0) {
                        NewSaleFragment.totalSubmitBtn.setText("Total" + " " + NewSaleFragment.getTotalPrice());
                        isDataFetching = false;
                        return;
                    }
                    NewSaleFragment.totalSubmitBtn.setText("Total" + " " + NewSaleFragment.getTotalPrice());
                    isDataFetching = false;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void insertAllDataToLocal(NewSaleProductListAdapter.MyHolder holder) {
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

        //  itemClick.insertQuantity(holder.getAdapterPosition(), String.valueOf(quantity), list.get(holder.getAdapterPosition()), String.valueOf(price), String.valueOf(discount), String.valueOf(totalPrice));

    }


    @Override
    public int getItemCount() {
        if (selectedProductsList != null) {
            return selectedProductsList.size();
        }
        return salesRequisitionItemsList.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.productNameTv)
        TextView productName;
        @BindView(R.id.priceEt)
        AutoCompleteTextView priceEt;
        @BindView(R.id.priceEditBtn)
        ImageButton priceEditBtn;
        @BindView(R.id.editCancelBtn)
        ImageButton priceEditCancel;
        @BindView(R.id.removeBtn)
        ImageButton removeBtn;
        @BindView(R.id.quantityEt)
        AutoCompleteTextView quantityEt;
        @BindView(R.id.addBtn)
        ImageButton addBtn;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
