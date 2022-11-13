package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.serverResponseModel.ExpenseOrdersResponse;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.utils.replace.ReplaceCommaFromString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpenseDueOrdersAdapter extends RecyclerView.Adapter<ExpenseDueOrdersAdapter.MyHolder> {
    public static Set<String> selectedOrderList = new HashSet<>();//store the selected order list for send server

    FragmentActivity context;
    List<ExpenseOrdersResponse> ordersList;

    public ExpenseDueOrdersAdapter(FragmentActivity context, List<ExpenseOrdersResponse> ordersList) {
        this.context = context;
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public ExpenseDueOrdersAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.receive_due_rv_model, parent, false);
        return new ExpenseDueOrdersAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseDueOrdersAdapter.MyHolder holder, int position) {
        holder.selectedOrder.setChecked(true);
        ExpenseOrdersResponse currentOrder = ordersList.get(position);
        holder.date.setText("Date: "+currentOrder.getOrderDate());
        holder.orderId.setText("#" + currentOrder.getOrderID());
        holder.totalPaid.setText(DataModify.addFourDigit(ReplaceCommaFromString.replaceComma(String.valueOf(currentOrder.getPaidAmount()))) + MtUtils.priceUnit);
        holder.totalAmount.setText(DataModify.addFourDigit(ReplaceCommaFromString.replaceComma(currentOrder.getTotalAmount())) + MtUtils.priceUnit);
        holder.orderDue.setText(DataModify.addFourDigit(ReplaceCommaFromString.replaceComma(String.valueOf(currentOrder.getDue()))) + MtUtils.priceUnit);

        selectedOrderList.add(ordersList.get(position).getOrderID());
        holder.selectedOrder.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                selectedOrderList.add(String.valueOf(ordersList.get(position).getOrderID()));
            } else {
                selectedOrderList.remove(String.valueOf(ordersList.get(position).getOrderID()));
            }

       /*     if (isChecked) {
                selectedOrderList.add(String.valueOf(ordersList.get(position).getOrderID()));
            } else {
                if (selectedOrderList.isEmpty()){
                    return;
                }else {
                    if (selectedOrderList.contains(String.valueOf(ordersList.get(position).getOrderID()))) {
                       try {
                           selectedOrderList.remove(position);
                       }catch (Exception e){
                           Log.d("EXCEPTION",e.getLocalizedMessage());
                       }
                    }
                }
            }*/
        });
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.orderIdTv)
        TextView orderId;
        @BindView(R.id.totalPaidTv)
        TextView totalPaid;
        @BindView(R.id.totalAmountTv)
        TextView totalAmount;
        @BindView(R.id.orderDueTv)
        TextView orderDue;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.selectedOrder)
        CheckBox selectedOrder;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
