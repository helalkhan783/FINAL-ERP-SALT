package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.serverResponseModel.ExpensePaymentDueList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExpenseDuePaymentApprovalAdapter extends RecyclerView.Adapter<ExpenseDuePaymentApprovalAdapter.MyHolder> {
    FragmentActivity context;
    List<ExpensePaymentDueList> lists;


    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.expense_pending_due_payment_approval_model, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        ExpensePaymentDueList currentDue = lists.get(position);
        holder.date.setText(currentDue.getPayment_date());
        holder.invoiceNo.setText(currentDue.getOrderID());
        holder.paidAmount.setText(currentDue.getPaidAmount());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.invoiceNo)
        TextView invoiceNo;
        @BindView(R.id.paidAmount)
        TextView paidAmount;

        public MyHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
