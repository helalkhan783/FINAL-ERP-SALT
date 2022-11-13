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
import com.ismos_salt_erp.serverResponseModel.PendingTransferItem;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransferDetailsItemAdapter extends RecyclerView.Adapter<TransferDetailsItemAdapter.MyHolder> {
    FragmentActivity context;
    List<PendingTransferItem> itemList;
    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transfer_details_item_model, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        PendingTransferItem currentItem = itemList.get(position);
        try {
            holder.itemName.setText(":  " + currentItem.getProductTitle());
            holder.price.setText(":  "+ DataModify.addFourDigit(currentItem.getBuyingPrice()) + MtUtils.priceUnit);
            holder.quantity.setText(":  " + currentItem.getQuantity() + " " + currentItem.getName());
         double total = Double.parseDouble(currentItem.getBuyingPrice()) * Double.parseDouble(currentItem.getQuantity());
        holder.total.setText(":  "+ DataModify.addFourDigit(String.valueOf(total)) + MtUtils.priceUnit);
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemNameModel)
        TextView itemName;
        @BindView(R.id.quantity)
        TextView quantity;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.total)
        TextView total;

        public MyHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
