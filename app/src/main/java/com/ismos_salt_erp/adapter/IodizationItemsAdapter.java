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
public class IodizationItemsAdapter extends RecyclerView.Adapter<IodizationItemsAdapter.MyHolder>{
    FragmentActivity context;
    List<Item> itemList;



    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.washing_crushing_edited_model, parent, false);
        return new IodizationItemsAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        Item currentItem = itemList.get(position);

        holder.itemName.setText(currentItem.getItem());
        holder.quantity.setText(currentItem.getQuantity());
        holder.unit.setText(currentItem.getQuantity());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.itemNameModel)
        TextView itemName;
        @BindView(R.id.itemQuantityModel)
        TextView quantity;
        @BindView(R.id.itemUnitModel)
        TextView unit;


        public MyHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
