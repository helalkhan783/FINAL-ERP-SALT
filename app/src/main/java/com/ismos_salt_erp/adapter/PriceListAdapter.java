package com.ismos_salt_erp.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.EditPriceListLayoutBinding;
import com.ismos_salt_erp.dialog.EditPrice;
import com.ismos_salt_erp.serverResponseModel.PriceDetail;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;

import java.util.List;

public class PriceListAdapter extends RecyclerView.Adapter<PriceListAdapter.MyHolder> {
    FragmentActivity activity;
    List<PriceDetail> priceDetails;
    String productId;
    LifecycleOwner lifecycleOwner;
     public PriceListAdapter(FragmentActivity activity, List<PriceDetail> priceDetails, String productId, LifecycleOwner lifecycleOwner) {
    this.activity =activity;
    this.priceDetails =priceDetails;
    this.productId =productId;
    this.lifecycleOwner =lifecycleOwner;
     }

    @NonNull
    @Override
    public PriceListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EditPriceListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.edit_price_list_layout, parent, false);

        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull PriceListAdapter.MyHolder holder, int position) {
        PriceDetail currentItem = priceDetails.get(position);


        holder.binding.itemName.setText(":  "+currentItem.getProductTitle());
        holder.binding.sellingPrice.setText(":  "+ DataModify.addFourDigit(currentItem.getSellingPrice()) + MtUtils.priceUnit);
        try {
            if (currentItem.getStatus().equals("0")|| currentItem.getStatus() == null){
                holder.binding.edit.setVisibility(View.GONE);
                holder.binding.status.setTextColor(Color.parseColor("#F14136"));
                holder.binding.status.setText(":  "+"Old Price");
            }
            if (currentItem.getStatus().equals("1")){
                holder.binding.status.setText(":  "+"Current Price");
            }
        }catch (Exception e){
            Log.d("ERROR",e.getMessage());
        }

        /**
         *
         */
        String sellingPrice =   currentItem.getSellingPrice();
        holder.binding.edit.setOnClickListener(v -> {
        EditPrice editPrice = new EditPrice(activity,sellingPrice,"edit",productId,currentItem.getProductPriceID(),lifecycleOwner);
        editPrice.addPrice();

        });
    }

    @Override
    public int getItemCount() {
        return priceDetails.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        EditPriceListLayoutBinding binding;
        public MyHolder(@NonNull EditPriceListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
 /*   FragmentActivity activity;

    public PriceListAdapter(FragmentActivity activity, List<PriceDetail> priceDetails) {


    }*/
}
