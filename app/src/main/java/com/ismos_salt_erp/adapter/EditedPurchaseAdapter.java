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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditedPurchaseAdapter extends RecyclerView.Adapter<EditedPurchaseAdapter.MyHolder> {
    FragmentActivity context;
    List<Item> itemList;

    public EditedPurchaseAdapter(FragmentActivity context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.edited_purchase_product_model, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Item currentItem = itemList.get(position);
        holder.itemName.setText("  :"+currentItem.getItem() + "  " + currentItem.getQuantity());
        holder.price.setText("  :"+currentItem.getBuyingPrice());
        holder.quantity.setText("  :"+currentItem.getQuantity());

        double total = Double.parseDouble(currentItem.getBuyingPrice()) * Double.parseDouble(currentItem.getQuantity());
        holder.total.setText("  :"+String.valueOf(total));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemName)
        TextView itemName;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.quantity)
        TextView quantity;
        @BindView(R.id.unit)
        TextView unit;
        @BindView(R.id.total)
        TextView total;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
