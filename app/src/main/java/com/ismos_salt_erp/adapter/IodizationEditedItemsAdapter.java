package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.ismos_salt_erp.R;
import com.ismos_salt_erp.serverResponseModel.IodizationItemRespons;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IodizationEditedItemsAdapter extends RecyclerView.Adapter<IodizationEditedItemsAdapter.MyViewHolder> {
    FragmentActivity context;
    List<IodizationItemRespons> itemList;

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.edited_iodizationitem_model, parent, false);
        return new IodizationEditedItemsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        IodizationItemRespons currentItem = itemList.get(position);
        holder.itemName.setText(currentItem.getItem());
        holder.quantity.setText(currentItem.getQuantity());
        holder.unit.setText(currentItem.getUnit());
        holder.store.setText(currentItem.getStore());
        double total = Double.parseDouble(currentItem.getQuantity())*1;//will implement
        holder.total.setText(String.valueOf(total));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemNameModel)
        TextView itemName;
        @BindView(R.id.quantity)
        TextView quantity;
        @BindView(R.id.unit)
        TextView unit;
        @BindView(R.id.store)
        TextView store;
        @BindView(R.id.total)
        TextView total;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
