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
import com.ismos_salt_erp.serverResponseModel.Order;
import com.ismos_salt_erp.utils.DateFormatRight;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.utils.replace.ReplaceCommaFromString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerOrderAdapter extends RecyclerView.Adapter<CustomerOrderAdapter.MyHolder> {
    public static Set<String> selectedOrderList; //store the selected order list for send server

    FragmentActivity context;
    List<Order> ordersList;
    String from;

    public CustomerOrderAdapter(FragmentActivity activity, List<Order> orders, String from) {
        this.context = activity;
        this.ordersList = orders;
        this.from = from;
        selectedOrderList = new HashSet<>();
        selectedOrderList.clear();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.receive_due_rv_model, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Order currentOrder = ordersList.get(position);
        if (from.equals("payment")) {
            holder.invoiceTvLevel.setText("PO ID");
        }
        holder.selectedOrder.setChecked(true);
        holder.orderId.setText( /*"#"+ currentOrder.getOrderSerial()*/  "" + currentOrder.getParticular() );
        holder.totalPaid.setText(""+currentOrder.getPaidAmount() + MtUtils.priceUnit);
        holder.totalAmount.setText(currentOrder.getTotalAmount() + MtUtils.priceUnit);
        holder.orderDue.setText(currentOrder.getDue() + MtUtils.priceUnit);
        if (currentOrder.getOrderDate() !=null){
            holder.date.setText("Date:  " +currentOrder.getOrderDate() );
        }

//          holder.totalPaid.setText(DataModify.addFourDigit(ReplaceCommaFromString.replaceComma(currentOrder.getPaidAmount())) + MtUtils.priceUnit);
//        holder.totalAmount.setText(DataModify.addFourDigit(ReplaceCommaFromString.replaceComma(currentOrder.getTotalAmount())) + MtUtils.priceUnit);
//        holder.orderDue.setText(DataModify.addFourDigit(ReplaceCommaFromString.replaceComma(currentOrder.getDue())) + MtUtils.priceUnit);
//        if (currentOrder.getOrderDate() != null) {
//            holder.date.setText("Date:  " + new DateFormatRight(context,currentOrder.getOrderDate()).onlyDayMonthYear() );
//
//        }
        selectedOrderList.add(String.valueOf(ordersList.get(position).getOrderID()));
        holder.selectedOrder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedOrderList.add(String.valueOf(ordersList.get(position).getOrderID()));
            } else {
                selectedOrderList.remove(String.valueOf(ordersList.get(position).getOrderID()));
            }
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
        @BindView(R.id.selectedOrder)
        CheckBox selectedOrder;
        @BindView(R.id.invoiceTvLevel)
        TextView invoiceTvLevel;
        @BindView(R.id.date)
        TextView date;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
