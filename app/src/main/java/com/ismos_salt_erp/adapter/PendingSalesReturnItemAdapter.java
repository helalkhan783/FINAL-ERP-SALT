package com.ismos_salt_erp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.ismos_salt_erp.R;
import com.ismos_salt_erp.serverResponseModel.PendingSalesReturnOrderDetails;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PendingSalesReturnItemAdapter extends RecyclerView.Adapter<PendingSalesReturnItemAdapter.myHolder> {
    FragmentActivity context;
    List<PendingSalesReturnOrderDetails> orderDetails;


    @NonNull
    @NotNull
    @Override
    public myHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pending_sales_return_item, parent, false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull myHolder holder, int position) {
        try {
            PendingSalesReturnOrderDetails current = orderDetails.get(position);

            holder.itemNameModel.setText(current.getProductTitle());
            //holder.priceModel.setText(current.getSellingPrice());


            if (current.getSalesTypeID().equals("404")) {
                holder.returned.setVisibility(View.VISIBLE);
            }
            if (current.getSalesTypeID().equals("402")) {
                holder.returned.setVisibility(View.GONE);
            }
            holder.quantity.setText(DataModify.addThreeDigit(current.getQuantity()) + MtUtils.kg);

//            double total = Double.parseDouble(current.getSellingPrice()) * Double.parseDouble(current.getQuantity());
//            holder.total.setText(String.valueOf(total));
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    class myHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemNameModel)
        TextView itemNameModel;
        //        @BindView(R.id.priceModel)
//        TextView priceModel;
        @BindView(R.id.quantity)
        TextView quantity;
        //        @BindView(R.id.total)
//        TextView total;
        @BindView(R.id.returned)
        LinearLayout returned;


        public myHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
