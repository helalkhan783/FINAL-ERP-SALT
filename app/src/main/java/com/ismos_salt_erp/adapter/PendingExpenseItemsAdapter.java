package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.serverResponseModel.ExpenseListResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.AllArgsConstructor;

/**
 * for get pending expense details items
 */
@AllArgsConstructor
public class PendingExpenseItemsAdapter extends RecyclerView.Adapter<PendingExpenseItemsAdapter.MyHolder> {
    FragmentActivity context;
    List<ExpenseListResponse> expenseLists;

    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pending_expense_items_model, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        ExpenseListResponse currentItem = expenseLists.get(position);

        holder.particularsModel.setText(currentItem.getTitle());
        holder.unitPriceModel.setText(currentItem.getExpenseAmount());
        holder.remarksModel.setText(currentItem.getRemarks());
    }

    @Override
    public int getItemCount() {
        return expenseLists.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.particularsModel)
        TextView particularsModel;
        @BindView(R.id.unitPriceModel)
        TextView unitPriceModel;
        @BindView(R.id.remarksModel)
        TextView remarksModel;

        public MyHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
