package com.ismos_salt_erp.adapter;

import android.animation.Animator;
import android.app.Activity;
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
import com.ismos_salt_erp.animation.CircleAnimationUtil;
import com.ismos_salt_erp.clickHandle.NewSaleProductListClickHandle;
import com.ismos_salt_erp.databinding.NewSaleProductListModelBinding;
import com.ismos_salt_erp.databinding.ProductListLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.ProductStockListResponse;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItemsResponse;
import com.ismos_salt_erp.view.fragment.purchase.newPurchase.AddNewPurchase;
import com.ismos_salt_erp.view.fragment.purchase.newPurchase.PurchaseRecyclerItemClick;
import com.ismos_salt_erp.view.fragment.sale.newSale.AddNewSale;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class NewPurchaseProductListAdapter extends RecyclerView.Adapter<NewPurchaseProductListAdapter.MyHolder> {
    private FragmentActivity context;
    private List<SalesRequisitionItemsResponse> list;
    private List<ProductStockListResponse> productStockResponse;
    private AddNewPurchase click;
    private int quantity;
    int totalPrice, discount, price;
    List<ProductStockListResponse> stockListResponses;

    public NewPurchaseProductListAdapter(FragmentActivity context,
                                         List<SalesRequisitionItemsResponse> list,
                                         AddNewPurchase itemClick ) {
        this.context = context;
        this.list = list;
        this.productStockResponse = productStockResponse;
        this.click = itemClick;
        this.stockListResponses = stockListResponses;
    }

    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ProductListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.product_list_layout, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        SalesRequisitionItemsResponse currentProduct = list.get(position);
        holder.binding.productName.setText(currentProduct.getProductTitle());
        holder.binding.priceTv.setText("100");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //click.insertQuantity(holder.getAdapterPosition(), productCount(),);
                //   addToCart(currentProduct,holder);
                addToCart(currentProduct, holder);
                new CircleAnimationUtil().attachActivity((Activity) context).setTargetView(holder.binding.profileImage).setMoveDuration(500).setDestView(AddNewPurchase.binding.toolbar.totalQtyTv).
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

    private void addToCart(SalesRequisitionItemsResponse currentProduct, MyHolder holder) {

        try {
            if (AddNewPurchase.isOk) {//this code for update product quantity
                for (int i = 0; i < AddNewPurchase.list.size(); i++) {
                    if (currentProduct.getProductID().equals(AddNewPurchase.list.get(i).getProductID())) {
                        double totalQty = 0;
                        totalQty = totalQty + Double.parseDouble(AddNewPurchase.list.get(i).getQuantity());
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

    private void Update(double totalQty, String productID, MyHolder holder, SalesRequisitionItemsResponse currentProduct) {

        totalQty = totalQty + 1;
        click.update(holder.getAdapterPosition(), String.valueOf(totalQty), list.get(holder.getAdapterPosition()), "100", "0", "0", currentProduct.getProductID());

    }

    private void productCount(int totalQty, String productID, MyHolder holder, SalesRequisitionItemsResponse currentProduct) {
        totalQty = totalQty + 1;
        click.insertQuantity(holder.getAdapterPosition(), String.valueOf(totalQty), list.get(holder.getAdapterPosition()), "100", "0", "0", currentProduct.getProductID());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ProductListLayoutBinding binding;

        public MyHolder(ProductListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}