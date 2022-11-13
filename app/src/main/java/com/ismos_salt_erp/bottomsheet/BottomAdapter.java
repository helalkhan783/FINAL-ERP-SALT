package com.ismos_salt_erp.bottomsheet;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.ItemListAdapter;
import com.ismos_salt_erp.clickHandle.ItemListClickHandle;
import com.ismos_salt_erp.databinding.BottomAdapterLayoutBinding;
import com.ismos_salt_erp.databinding.ItemListLayoutBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.AddNewItemBrand;
import com.ismos_salt_erp.serverResponseModel.AddNewItemCategory;
import com.ismos_salt_erp.serverResponseModel.AddNewItemUnit;
import com.ismos_salt_erp.serverResponseModel.Product;
import com.ismos_salt_erp.utils.BottomUtil;
import com.ismos_salt_erp.utils.ImageBaseUrl;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.items.ItemListFragment;
import com.ismos_salt_erp.view.fragment.items.addNew.AddNewItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class BottomAdapter extends RecyclerView.Adapter<BottomAdapter.ViewHolder> {
    String whoseFor;
    private List<AddNewItemUnit> unitList;
    private boolean click = false;
    BottomSheetBehavior<View> bottomSheetBehavior;
    AddNewItem clickHandle;
    private List<AddNewItemBrand> brandResponseList;
    private List<AddNewItemCategory> categoryResponseList;

    public BottomAdapter(AddNewItem clickHandle, String whoseFor, BottomSheetBehavior<View> bottomSheetBehavior, List<AddNewItemUnit> products, List<AddNewItemBrand> brandResponseList,
                         List<AddNewItemCategory> categoryResponseList) {
        this.clickHandle = clickHandle;
        this.unitList = products;
        this.whoseFor = whoseFor;
        this.bottomSheetBehavior = bottomSheetBehavior;
        this.brandResponseList = brandResponseList;
        this.categoryResponseList = categoryResponseList;

    }

    @NonNull
    @NotNull
    @Override
    public BottomAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        BottomAdapterLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.bottom_adapter_layout, parent, false);
        return new BottomAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BottomAdapter.ViewHolder holder, int position) {
        AddNewItemUnit unit = null;
        AddNewItemBrand brand = null;
        AddNewItemCategory category = null;
        if (whoseFor.equals(BottomUtil.selectUnit)) {
            holder.binding.imageView.setVisibility(View.GONE);
            unit = unitList.get(position);
            holder.binding.nameTv.setText("" + unit.getName());

        } else if (whoseFor.equals(BottomUtil.selectBrand)) {
            holder.binding.imageView.setVisibility(View.VISIBLE);
            brand = brandResponseList.get(position);
            holder.binding.nameTv.setText("" + brand.getBrandName());

        } else if (whoseFor.equals(BottomUtil.selectCategory)) {
            category = categoryResponseList.get(position);
            holder.binding.imageView.setVisibility(View.VISIBLE);
            holder.binding.nameTv.setText("" + category.getCategory());


        }

        AddNewItemUnit finalUnit = unit;
        AddNewItemBrand finalBrand = brand;
        AddNewItemCategory finalCategory = category;
        holder.itemView.setOnClickListener(v -> {
            if (whoseFor.equals("Select Unit")) {
                clickHandle.fetchIdAndName(finalUnit.getName(), finalUnit.getiD(), whoseFor);

            } else if (whoseFor.equals("Select Brand")) {
                clickHandle.fetchIdAndName(finalBrand.getBrandName(), finalBrand.getBrandID(), whoseFor);

            } else if (whoseFor.equals("Select Category")) {
                clickHandle.fetchIdAndName(finalCategory.getCategory(), finalCategory.getCategoryID(), whoseFor);
            }
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        });


    }


    @Override
    public int getItemCount() {

        if (whoseFor.equals(BottomUtil.selectUnit)) {
            return unitList.size();
        } else if (whoseFor.equals(BottomUtil.selectBrand)) {
            return brandResponseList.size();
        } else if (whoseFor.equals(BottomUtil.selectCategory)) {
            return brandResponseList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final BottomAdapterLayoutBinding binding;

        public ViewHolder(final BottomAdapterLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
