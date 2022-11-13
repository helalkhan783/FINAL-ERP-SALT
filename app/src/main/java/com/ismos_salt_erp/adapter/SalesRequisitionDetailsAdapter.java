package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.ismos_salt_erp.R;
import com.ismos_salt_erp.serverResponseModel.Item;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SalesRequisitionDetailsAdapter extends RecyclerView.Adapter<SalesRequisitionDetailsAdapter.MyHolder>{
    FragmentActivity context;
    List<Item> itemList;

    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pending_sales_requisition_model,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        Item currentItem = itemList.get(position);

        holder.itemName.setText(currentItem.getItem());
        holder.price.setText(currentItem.getSellingPrice());
        holder.quantity.setText(currentItem.getQuantity());
        holder.unit.setText(currentItem.getUnit());
        holder.totalAmount.setText(String.valueOf(currentItem.getTotalAmount()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.itemName)
        TextView itemName;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.quantity)
        TextView quantity;
        @BindView(R.id.unit)
        TextView unit;
        @BindView(R.id.totalAmount)
        TextView totalAmount;


        public MyHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
