package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.serverResponseModel.ReconciliationListResponse;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReconciliationAdapter extends RecyclerView.Adapter<ReconciliationAdapter.MyHolder> {

    FragmentActivity context;
    List<ReconciliationListResponse> details;

    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reconciliation_model, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        ReconciliationListResponse current = details.get(position);

        double totalAmount = Double.parseDouble(current.getSellingPrice())*Double.parseDouble(current.getQuantity());
        holder.itemName.setText(":  "+current.getProductTitle()  );
        holder.quantity.setText(":  "+current.getQuantity() + " " + current.getName());
        holder.type.setText(":  "+current.getReconciliation_type());
        holder.store.setText(":  "+current.getStore_name());
        //holder.price.setText(":  "+current.getBuyingPrice());
        holder.price.setText(":  "+ DataModify.addFourDigit(current.getSellingPrice()) + MtUtils.priceUnit);
        holder.total.setText(":  "+ DataModify.addFourDigit(String.valueOf(totalAmount)) +MtUtils.priceUnit);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemName)
        TextView itemName;
        @BindView(R.id.quantity)
        TextView quantity;
        @BindView(R.id.type)
        TextView type;
        @BindView(R.id.store)
        TextView store;
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
