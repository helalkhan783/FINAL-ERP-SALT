package com.ismos_salt_erp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.BrandListLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.BrandList;
import com.ismos_salt_erp.utils.ImageBaseUrl;
import com.ismos_salt_erp.view.fragment.items.ItemListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;


public class BrandListAdapter extends RecyclerView.Adapter<BrandListAdapter.MyHolder> {
    private FragmentActivity context;
    private List<BrandList> brandLists;
    ItemListFragment click;

    public BrandListAdapter(FragmentActivity context, List<BrandList> brandLists, ItemListFragment click) {
        this.context = context;
        this.brandLists = brandLists;
        this.click = click;
    }

    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        BrandListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.brand_list_layout, parent, false);


        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        BrandList currentPossition = brandLists.get(position);
        holder.binding.brandName.setText(":  " + currentPossition.getBrandName());
        try {
            Glide.with(context).load(ImageBaseUrl.image_base_url + currentPossition.getImage())

                    .into(holder.binding.brandImageView);

        } catch (NullPointerException e) {
            Log.d("ERROR", e.getMessage());
        }
        /** click handle*/
        /** click handle*/
        holder.binding.edit.setOnClickListener(v -> {
            click.getData(holder.getAdapterPosition(), currentPossition.getBrandID(), currentPossition.getBrandName(), currentPossition.getImage());
        });

        holder.binding.delete.setOnClickListener(v -> click.delete(holder.getAdapterPosition(), currentPossition.getBrandID()));

    }

    @Override
    public int getItemCount() {
        return brandLists.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        private final BrandListLayoutBinding binding;

        public MyHolder(@NonNull @NotNull BrandListLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
