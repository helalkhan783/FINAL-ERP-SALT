package com.ismos_salt_erp.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.EnterPriseLayoutStoreBinding;
import com.ismos_salt_erp.serverResponseModel.Enterprize;
import com.ismos_salt_erp.view.fragment.store.StoreListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EnterPriseAdapterForStore extends RecyclerView.Adapter<EnterPriseAdapterForStore.viewHolder> {
    private FragmentActivity context;
    List<Enterprize> lists;
    private StoreListFragment click;

    public EnterPriseAdapterForStore(FragmentActivity context, List<Enterprize> lists, StoreListFragment click) {
        this.context = context;
        this.lists = lists;
        this.click = click;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        EnterPriseLayoutStoreBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.enter_prise_layout_store, parent, false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {
        holder.itembinding.edit.setVisibility(View.GONE);
        Enterprize currentList = lists.get(position);
         holder.itembinding.shortName.setText(":  " + currentList.getStoreName());
        holder.itembinding.enterpriseName.setText(":  " + currentList.getFullName());
        if (currentList.getStoreAddress() != null) {
            holder.itembinding.address.setText(":  " + currentList.getStoreAddress());
        }
        if (currentList.getCreatedAt() != null) {
            holder.itembinding.date.setText(":  " + currentList.getCreatedAt());
        }


        holder.itembinding.userSwitch.setChecked(false);
        holder.itembinding.userSwitch.setSelected(false);
        if (currentList.getStatus().equals("1"))  {
            holder.itembinding.userSwitch.setChecked(true);
            holder.itembinding.userSwitch.setSelected(true);
        }

        holder.itembinding.userSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               click.statusManage(holder.getAdapterPosition(), currentList.getStoreID());
            }
        });

        holder.itembinding.edit.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", currentList.getStoreID());
            bundle.putString("storeNo", currentList.getStoreNo());
            bundle.putString("fullName", currentList.getFullName());
            bundle.putString("shortName", currentList.getStoreName());
            bundle.putString("address", currentList.getStoreAddress());
            bundle.putString("contact", currentList.getContact());
            bundle.putString("email", currentList.getEmail());
            bundle.putString("companyLogo", String.valueOf(currentList.getCompanyLogo()));
            bundle.putString("enterPriseLogo", currentList.getStoreLogo());
         //   Navigation.findNavController(holder.itembinding.getRoot()).navigate(R.id.action_storeListFragment_to_enterPriseEditFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private EnterPriseLayoutStoreBinding itembinding;

        public viewHolder(@NonNull @NotNull EnterPriseLayoutStoreBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}
