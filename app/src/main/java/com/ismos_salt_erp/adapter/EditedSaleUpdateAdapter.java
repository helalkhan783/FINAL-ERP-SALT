package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.serverResponseModel.EditedOrderDetail;
import com.ismos_salt_erp.serverResponseModel.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditedSaleUpdateAdapter extends RecyclerView.Adapter<EditedSaleUpdateAdapter.MyHolder>{

    FragmentActivity context;
    List<EditedOrderDetail> editedOrderDetailsList;
    List<Item> previousOrderList;

    public EditedSaleUpdateAdapter(FragmentActivity context, List<EditedOrderDetail> editedOrderDetailsList, List<Item> previousOrderList) {
        this.context = context;
        this.editedOrderDetailsList = editedOrderDetailsList;
        this.previousOrderList = previousOrderList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.edited_purchase_product_model, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        EditedOrderDetail currentItem = editedOrderDetailsList.get(position);

        /**
         * now set color on updated property like (price,quantity)
         */
        for (int i = 0; i < previousOrderList.size(); i++) {
            double previousBuyingPrice = Double.parseDouble(previousOrderList.get(i).getSellingPrice());
            double currentBuyingPrice = Double.parseDouble(currentItem.getSellingPrice());

            //price
            if (currentBuyingPrice != previousBuyingPrice) {
                holder.price.setText(":  "+currentItem.getSellingPrice());
                holder.price.setTextColor(context.getResources().getColor(R.color.successColor));
            } else {
                holder.price.setTextColor(context.getResources().getColor(R.color.gray));
                holder.price.setText(":  "+currentItem.getSellingPrice());
            }

            double previousQuantity = Double.parseDouble(previousOrderList.get(position).getQuantity());
            double currentQuantity = Double.parseDouble(currentItem.getQuantity());
            //quantity
            if (previousQuantity != currentQuantity) {
                holder.quantity.setTextColor(context.getResources().getColor(R.color.successColor));
                holder.quantity.setText(":  "+currentItem.getQuantity() + "  "+currentItem.getUnit());
            } else {
                holder.quantity.setTextColor(context.getResources().getColor(R.color.gray));
                holder.quantity.setText(":  "+currentItem.getQuantity());
            }
        }

        holder.itemName.setText(":  "+currentItem.getProductTitle());
        holder.unit.setText(":  "+currentItem.getUnit());
        double total = Double.parseDouble(currentItem.getBuyingPrice()) * Double.parseDouble(currentItem.getQuantity());
        holder.total.setText(":  "+String.valueOf(total));
    }

    @Override
    public int getItemCount() {
        return editedOrderDetailsList.size();
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
        @BindView(R.id.total)
        TextView total;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
