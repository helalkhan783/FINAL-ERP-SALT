package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.serverResponseModel.PendingWashingCrushingItem;
import com.ismos_salt_erp.utils.MtUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PendingWashingAndCrushingAdapter extends RecyclerView.Adapter<PendingWashingAndCrushingAdapter.MyHolder> {
    FragmentActivity context;
    List<PendingWashingCrushingItem> itemList;


    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.pending_washing_crushing_model, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        PendingWashingCrushingItem currentItem = itemList.get(position);
        holder.itemName.setText(":  "+currentItem.getItem());
        holder.quantity.setText(":  "+ currentItem.getQuantity() + MtUtils.kg);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemNameTV)
        TextView itemName;
        @BindView(R.id.quantityTV)
        TextView quantity;

        public MyHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
