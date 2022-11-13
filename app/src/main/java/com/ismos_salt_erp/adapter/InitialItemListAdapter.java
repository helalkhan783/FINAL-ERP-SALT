package com.ismos_salt_erp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.CustomItemLayoutBinding;
import com.ismos_salt_erp.databinding.InitialItemLayoutBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.InitiaItemList;
import com.ismos_salt_erp.serverResponseModel.Product;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.items.ItemListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class InitialItemListAdapter extends RecyclerView.Adapter<InitialItemListAdapter.ViewHolder> {
    private Context context;
    private List<InitiaItemList> products;


    public InitialItemListAdapter(Context context, List<InitiaItemList> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @NotNull
    @Override
    public InitialItemListAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        InitialItemLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.initial_item_layout, parent, false);
        return new InitialItemListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull InitialItemListAdapter.ViewHolder holder, int position) {
        InitiaItemList data = products.get(position);
        holder.binding.categoryName.setText(":  " + data.getCategory());
        holder.binding.itemName.setText(":  " + data.getProductTitle());
        holder.binding.enterpriseName.setText(":  " + data.getEnterprise_name());
        holder.binding.storeName.setText(":  " + data.getStoreName());
        holder.binding.code.setText(":  " + data.getPcode());

        if (data.getBrandName() !=null){
            holder.binding.brandName.setText(":  " + data.getBrandName());
        }

        holder.binding.unit.setText(":  " + data.getUnitName());
        holder.binding.weight.setText(":  " + data.getProductDimensions());
        holder.binding.qty.setText(":  " + data.getQuantity());
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final InitialItemLayoutBinding binding;

        public ViewHolder(final InitialItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}

