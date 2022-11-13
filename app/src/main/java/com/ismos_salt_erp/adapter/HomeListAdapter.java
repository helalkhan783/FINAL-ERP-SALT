package com.ismos_salt_erp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.HomepageListLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.HomepageList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder> {
    FragmentActivity context;
    List<HomepageList> lists;
    String portion;

    @NonNull
    @NotNull
    @Override
    public HomeListAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        HomepageListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.homepage_list_layout, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HomeListAdapter.ViewHolder holder, int position) {
        HomepageList currentPosition = lists.get(position);
        try {
            if (portion.equals("PurchaseRawSalt")) {
                holder.binding.typeTv.setText("P.O ID");
                holder.binding.customerTv.setText("Supplier");

            }

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }

        if (currentPosition.getEntryDate() != null) {
            holder.binding.enroledDate.setText(":  " + currentPosition.getEntryDate());
        }

        if (currentPosition.getOrderID() != null) {
            holder.binding.id.setText(":  " + currentPosition.getOrderID());
        }

        if (currentPosition.getEnterpriseName() != null) {
            holder.binding.enterprise.setText(":  " + currentPosition.getEnterpriseName());
        }
        if (currentPosition.getQuantity() != null) {
            holder.binding.quantity.setText(":  " + currentPosition.getQuantity() + " " + currentPosition.getUnitName());
        }

        if (currentPosition.getCustomerName() != null) {
            holder.binding.customer.setText(":  " + currentPosition.getCustomerName());

        }

        if (currentPosition.getEnterpriseName() != null) {
            holder.binding.customer.setText(":  " + currentPosition.getEnterpriseName());
        }
        if (currentPosition.getProcessedBy() != null) {
            holder.binding.processedBy.setText(":  " + currentPosition.getProcessedBy());
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        HomepageListLayoutBinding binding;

        public ViewHolder(@NonNull @NotNull HomepageListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
