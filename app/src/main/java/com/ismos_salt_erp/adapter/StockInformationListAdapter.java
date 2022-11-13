package com.ismos_salt_erp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.StockListXmlBinding;
import com.ismos_salt_erp.serverResponseModel.StockList;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.store.StoreListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StockInformationListAdapter extends RecyclerView.Adapter<StockInformationListAdapter.viewHolder> {
    private Context context;
    List<StockList> lists;
    View view;

     public StockInformationListAdapter(Context context, List<StockList> lists, View view) {
         this.context = context;
         this.lists = lists;
         this.view = view;
     }

     @NonNull
    @NotNull
    @Override
    public StockInformationListAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        StockListXmlBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.stock_list_xml, parent, false);
        return new StockInformationListAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StockInformationListAdapter.viewHolder holder, int position) {
        StockList currentList = lists.get(position);
        try {
             holder.itembinding.itemName.setText(":  " + currentList.getProductTitle());
             if (currentList.getBrandName() != null){
                 holder.itembinding.brand.setText(":  " + currentList.getBrandName());
             }
            holder.itembinding.category.setText(":  " + currentList.getCategory());
            holder.itembinding.quality.setText(":  " + currentList.getQuantity()+ MtUtils.kg);
            holder.itembinding.lbp.setText(":  " + DataModify.addFourDigit(String.valueOf(currentList.getUnitBuyingPrice()))  + MtUtils.priceUnit);
            holder.itembinding.lsp.setText(":  " + DataModify.addFourDigit(String.valueOf(currentList.getAvgPrice())) + MtUtils.priceUnit);
        } catch (Exception e) {
            Log.e("ERROR", "" + e.getMessage());
        }

        holder.itembinding.view.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("productId",currentList.getProductID());
            bundle.putString("porson","stockDetails");
            bundle.putString("pageName",  "Stock Details Of "+ currentList.getProductTitle());
            if (StoreListFragment.endScroll){
                StoreListFragment.manage = 1;
            }
            StoreListFragment.pageNumber =+1;
            Navigation.findNavController(view).navigate(R.id.action_storeListFragment_self,bundle);
        });


    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private StockListXmlBinding itembinding;
        public viewHolder(@NonNull @NotNull StockListXmlBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}
