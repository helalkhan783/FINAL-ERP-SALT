package com.ismos_salt_erp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.serverResponseModel.ItemsResponse;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationPendingPurchaseAdapter extends RecyclerView.Adapter<NotificationPendingPurchaseAdapter.MyHolder> {
    FragmentActivity context;
    List<ItemsResponse> itemsResponseList;
    String enterPriseName;
    public static double totalAmount = 0;
    public static double collectedAmount = 0;


    public NotificationPendingPurchaseAdapter(FragmentActivity context, List<ItemsResponse> itemsResponseList, String enterPriseName) {
        this.context = context;
        this.itemsResponseList = itemsResponseList;
        this.enterPriseName = enterPriseName;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_pending_product_model, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        ItemsResponse currentItems = itemsResponseList.get(position);
        try {

            if (currentItems.getProductTitle() != null) {
                holder.itemNameTv.setText(":  " + currentItems.getProductTitle());

            }
            if (currentItems.getBuyingPrice() != null) {
                holder.purchasingPrice.setText(":  " + DataModify.addFourDigit(currentItems.getBuyingPrice()) +MtUtils.priceUnit);

            }
          /*  if (currentItems.getDiscount() != null) {
                holder.discount.setText(":  " + currentItems.getDiscount());
            }*/

            String unit = currentItems.getUnit();
            if (currentItems.getQuantity() != null) {
                if (unit != null) {
                    holder.quantityTv.setText(":  " + currentItems.getQuantity() + " " + unit);
                }
                holder.quantityTv.setText(":  " + currentItems.getQuantity()+ MtUtils.kg);
            }
            if (enterPriseName != null) {
                holder.enterPrisenameTv.setText(":  " + enterPriseName);
            }
            if (currentItems.getSoldFrom() != null) {
                holder.storeTv.setText(":  " + currentItems.getSoldFrom());
            }

            double buyingPrice=0,quantity=0,subtotal;
            buyingPrice = Double.parseDouble(currentItems.getBuyingPrice());
            quantity = Double.parseDouble(currentItems.getQuantity());
            // discount = Double.parseDouble(currentItems.getDiscount());
            subtotal = buyingPrice * quantity;
            holder.subtotal.setText(":  " + DataModify.addFourDigit(String.valueOf(subtotal)) + MtUtils.priceUnit);

        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return itemsResponseList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ItemNameTv)
        TextView itemNameTv;
        @BindView(R.id.purchasingPrice)
        TextView purchasingPrice;
        @BindView(R.id.discount)
        TextView discount;
        @BindView(R.id.subtotal)
        TextView subtotal;
        @BindView(R.id.quantityTv)
        TextView quantityTv;
        @BindView(R.id.enterPrisenameTv)
        TextView enterPrisenameTv;
        @BindView(R.id.storeTv)
        TextView storeTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
