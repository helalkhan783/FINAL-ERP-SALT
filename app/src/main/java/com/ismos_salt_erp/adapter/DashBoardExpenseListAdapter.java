package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.serverResponseModel.DashBoardExpenseListResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DashBoardExpenseListAdapter extends RecyclerView.Adapter<DashBoardExpenseListAdapter.MyHolder> {
    FragmentActivity context;
    List<DashBoardExpenseListResponse> lists;


    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_expense_model, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        DashBoardExpenseListResponse currentItem = lists.get(position);

        holder.date.setText(currentItem.getIssuedDateTime());
        holder.enterpriseName.setText(currentItem.getEnterpriseName());
        holder.expenseHead.setText(currentItem.getExpenseHead());
        holder.expenseGroup.setText(currentItem.getExpenseGroup());
        holder.vendorName.setText(currentItem.getCustomerFname());
        holder.amount.setText(currentItem.getExpense_amount());
        holder.paidAmount.setText(currentItem.getPaid());
        holder.paymentType.setText(currentItem.getPaymentType());


        holder.viewBtn.setOnClickListener(v -> {
            Toasty.info(context, "will", Toasty.LENGTH_LONG).show();
        });

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.enterpriseName)
        TextView enterpriseName;
        @BindView(R.id.expenseHead)
        TextView expenseHead;
        @BindView(R.id.expenseGroup)
        TextView expenseGroup;
        @BindView(R.id.vendorName)
        TextView vendorName;
        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.paidAmount)
        TextView paidAmount;
        @BindView(R.id.paymentType)
        TextView paymentType;
        @BindView(R.id.viewBtn)
        LinearLayout viewBtn;

        public MyHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
