package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.serverResponseModel.EditedItemsResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WashingCrushingEditedItemAdapter extends RecyclerView.Adapter<WashingCrushingEditedItemAdapter.MyHolder> {
    FragmentActivity context;
    List<EditedItemsResponse> currentItems;


    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.washing_crushing_edited_model, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        EditedItemsResponse currentItem = currentItems.get(position);
        holder.itemNameModel.setText(currentItem.getItem());
        holder.itemQuantityModel.setText(currentItem.getQuantity());
        holder.itemUnitModel.setText(currentItem.getUnit());
    }

    @Override
    public int getItemCount() {
        return currentItems.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemNameModel)
        TextView itemNameModel;
        @BindView(R.id.itemQuantityModel)
        TextView itemQuantityModel;
        @BindView(R.id.itemUnitModel)
        TextView itemUnitModel;

        public MyHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
