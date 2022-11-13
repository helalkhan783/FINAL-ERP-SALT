package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.AddQuantityModelLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.StoreList;

import java.util.List;

public class AddQuantityAdapter extends RecyclerView.Adapter<AddQuantityAdapter.MyHolder> {

    private FragmentActivity activity;
    private List<StoreList> store;

    public AddQuantityAdapter(FragmentActivity activity, List<StoreList> store) {
        this.activity = activity;
        this.store = store;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AddQuantityModelLayoutBinding binding = DataBindingUtil.inflate( LayoutInflater.from(parent.getContext()), R.layout.add_quantity_model_layout,parent,false);
        return new AddQuantityAdapter.MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        StoreList currentStore= store.get(position);
        holder.binding.itemName.setText(currentStore.getStoreName() + "(" + currentStore.getEnterpriseName() + ")");

    }

    @Override
    public int getItemCount() {
        return store.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private  AddQuantityModelLayoutBinding binding;
        public MyHolder(@NonNull AddQuantityModelLayoutBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
