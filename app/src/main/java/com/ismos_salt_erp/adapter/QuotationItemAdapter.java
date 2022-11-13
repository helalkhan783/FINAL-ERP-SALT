package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.serverResponseModel.QuotationDetail;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QuotationItemAdapter extends RecyclerView.Adapter<QuotationItemAdapter.MyHolder> {
    FragmentActivity context;
    List<QuotationDetail> quotationDetails;
    String totalAmount;

    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.qoutation_item_model, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        QuotationDetail currentItem = quotationDetails.get(position);
        holder.itemName.setText(currentItem.getProductTitle());
        holder.quantity.setText(currentItem.getQuantity());
        holder.price.setText(currentItem.getUnitPrice());
        holder.discount.setText(currentItem.getDiscount());
        holder.subtotal.setText(totalAmount);
    }

    @Override
    public int getItemCount() {
        return quotationDetails.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemName)
        TextView itemName;
        @BindView(R.id.quantity)
        TextView quantity;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.discount)
        TextView discount;
        @BindView(R.id.subtotal)
        TextView subtotal;

        public MyHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
