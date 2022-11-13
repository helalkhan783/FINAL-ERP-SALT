package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.ConfirmNewItemModelBinding;
import com.ismos_salt_erp.serverResponseModel.StoreList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConfirmAddNewItemAdapter extends RecyclerView.Adapter<ConfirmAddNewItemAdapter.MyHolder> {
    private FragmentActivity activity;
    private List<StoreList> store;

    public ConfirmAddNewItemAdapter(FragmentActivity activity, List<StoreList> store) {
        this.activity = activity;
        this.store = store;
    }

    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ConfirmNewItemModelBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.confirm_new_item_model, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        StoreList currentStore = store.get(position);
        holder.binding.name.setText(currentStore.getStoreName() + "(" + currentStore.getEnterpriseName() + ")");
    }

    @Override
    public int getItemCount() {
        return store.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private ConfirmNewItemModelBinding binding;

        public MyHolder(ConfirmNewItemModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
