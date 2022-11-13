package com.ismos_salt_erp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.ItemListLayoutBinding;
import com.ismos_salt_erp.databinding.VarientListLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.VarientList;
import com.ismos_salt_erp.view.fragment.VarientlistFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VarientlistAdapter extends RecyclerView.Adapter<VarientlistAdapter.ViewHolder> {
    private Context context;
    private List<VarientList> products;
     VarientlistFragment click;

    public VarientlistAdapter(Context context, List<VarientList> products, VarientlistFragment click) {
        this.context = context;
        this.products = products;
        this.click = click;
    }

    @NonNull
    @NotNull
    @Override
    public VarientlistAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        VarientListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.varient_list_layout, parent, false);
        return new VarientlistAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VarientlistAdapter.ViewHolder holder, int position) {
        VarientList currentProduct = products.get(position);
      try{
          int po = position+1;
          holder.binding.sl.setText(":  "+po);
          holder.binding.name.setText(":  "+currentProduct.getProductTitle());
          holder.binding.date.setText(":  "+currentProduct.getRecordDateTime());
          holder.binding.weight.setText(":  "+currentProduct.getProductDimensions() + "  "+currentProduct.getName());


      }catch (Exception e){}
    }



    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final VarientListLayoutBinding binding;

        public ViewHolder(final VarientListLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}

