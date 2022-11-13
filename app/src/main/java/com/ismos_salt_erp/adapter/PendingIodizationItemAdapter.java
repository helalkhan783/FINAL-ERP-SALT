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
import com.ismos_salt_erp.utils.MtUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PendingIodizationItemAdapter extends RecyclerView.Adapter<PendingIodizationItemAdapter.MyIodizationHolder> {
    FragmentActivity context;
    List<Item> items;

    @NonNull
    @NotNull
    @Override
    public MyIodizationHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.iodization_item_model, parent, false);
        return new MyIodizationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyIodizationHolder holder, int position) {
        Item currentItem = items.get(position);
        holder.iodizationItemName.setText(":  "+currentItem.getItem());
        holder.iodizationQuantity.setText(":  "+currentItem.getQuantity() + MtUtils.kg);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MyIodizationHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iodizationItemName)
        TextView iodizationItemName;
        @BindView(R.id.iodizationQuantity)
        TextView iodizationQuantity;

        public MyIodizationHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

