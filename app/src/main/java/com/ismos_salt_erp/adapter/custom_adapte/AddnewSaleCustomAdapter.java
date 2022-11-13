package com.ismos_salt_erp.adapter.custom_adapte;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.animation.CircleAnimationUtil;
import com.ismos_salt_erp.databinding.ProductListLayoutBinding;
import com.ismos_salt_erp.localDatabase.MyDatabaseHelper;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItemsResponse;
import com.ismos_salt_erp.view.fragment.sale.newSale.AddNewSale;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AddnewSaleCustomAdapter extends RecyclerView.Adapter<AddnewSaleCustomAdapter.ViewHolder> {
    private Context context;
    private List<SalesRequisitionItemsResponse> products;
    AddNewSale click;
    MyDatabaseHelper myDatabaseHelper;



    public static boolean isOk = true;


    public AddnewSaleCustomAdapter(Context context, List<SalesRequisitionItemsResponse> products, AddNewSale click) {
        this.context = context;
        this.products = products;
        this.click = click;
        myDatabaseHelper = new MyDatabaseHelper(context);

    }


    @NotNull
    @Override
    public AddnewSaleCustomAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ProductListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.product_list_layout, parent, false);
        return new AddnewSaleCustomAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AddnewSaleCustomAdapter.ViewHolder holder, int position) {
        SalesRequisitionItemsResponse currentProduct = products.get(position);

        holder.binding.productName.setText(currentProduct.getProductTitle());

        holder.binding.priceTv.setText("100");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //click.insertQuantity(holder.getAdapterPosition(), productCount(),);
                //   addToCart(currentProduct,holder);
                addToCart(currentProduct, holder);
                new CircleAnimationUtil().attachActivity((Activity) context).setTargetView(holder.binding.profileImage).setMoveDuration(500).setDestView(AddNewSale.binding.toolbar.totalQtyTv).
                        setAnimationListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {

                             }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        }).startAnimation();

            }
        });

    }

    private void addToCart(SalesRequisitionItemsResponse currentProduct, ViewHolder holder) {

        try {
            if (AddNewSale.isOk) {//this code for update product quantity
                for (int i = 0; i < AddNewSale.list.size(); i++) {
                    if (currentProduct.getProductID().equals(AddNewSale.list.get(i).getProductID())) {
                        double totalQty = 0;
                        totalQty = totalQty + Double.parseDouble(AddNewSale.list.get(i).getQuantity());
                        Update(totalQty, currentProduct.getProductID(), holder, currentProduct);
                        return;
                    }
                }

            }
        } catch (Exception e) {
        }

        // Inser product in
        productCount(0, currentProduct.getProductID(), holder, currentProduct);



    }

    private void Update(double totalQty, String productID, ViewHolder holder, SalesRequisitionItemsResponse currentProduct) {

        totalQty = totalQty + 1;
        click.update(holder.getAdapterPosition(), String.valueOf(totalQty), products.get(holder.getAdapterPosition()), "100", "0", "0", currentProduct.getProductID());

    }


    private void productCount(double totalQty, String productID, ViewHolder holder, SalesRequisitionItemsResponse currentProduct) {
        totalQty = totalQty + 1;
        click.insertQuantity(holder.getAdapterPosition(), String.valueOf(totalQty), products.get(holder.getAdapterPosition()), "100", "0", "0", currentProduct.getProductID());
    }





    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ProductListLayoutBinding binding;

        public ViewHolder(final ProductListLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}

