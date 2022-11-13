package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.PermissionListBinding;
import com.ismos_salt_erp.serverResponseModel.SetIodineStoreList;
import com.ismos_salt_erp.view.fragment.set_iodine.IodinesetFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.NonNull;

public class IodineSetStoreAdapter extends RecyclerView.Adapter<IodineSetStoreAdapter.MyHolder> {
    FragmentActivity context;
    List<SetIodineStoreList> setIodineStoreLists;
    View view;
    IodinesetFragment click;

    public IodineSetStoreAdapter(FragmentActivity context, List<SetIodineStoreList> setIodineStoreLists, View view, IodinesetFragment click) {
        this.context = context;
        this.setIodineStoreLists = setIodineStoreLists;
        this.view = view;
        this.click = click;
    }

    @NonNull
    @NotNull
    @Override
    public IodineSetStoreAdapter.MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        PermissionListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.permission_list, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull IodineSetStoreAdapter.MyHolder holder, int position) {
        SetIodineStoreList currentImage = setIodineStoreLists.get(position);
        holder.binding.checkboxItem.setText("" +currentImage.getStoreName() /*+" @ "+ currentImage.getFullName()*/);
        if (currentImage.getIodineStore().equals("1")) {
            holder.binding.checkboxItem.setChecked(true);
            holder.binding.checkboxItem.setSelected(true);
        }
        holder.binding.checkboxItem.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                click.getData(holder.getAdapterPosition(), currentImage.getStoreID());
            }
        });

    }

    @Override
    public int getItemCount() {
        return setIodineStoreLists.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        private PermissionListBinding binding;

        public MyHolder(PermissionListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}

