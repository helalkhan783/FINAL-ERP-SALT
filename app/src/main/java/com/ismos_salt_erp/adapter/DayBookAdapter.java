package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.CashBookModelBinding;
import com.ismos_salt_erp.date_time_picker.CurrentDatePick;
import com.ismos_salt_erp.serverResponseModel.CashBookList;
import com.ismos_salt_erp.serverResponseModel.DayBookList;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.monitoring.AccountsListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.AllArgsConstructor;


public class DayBookAdapter extends RecyclerView.Adapter<DayBookAdapter.MyHolder> {

    FragmentActivity context;
    List<DayBookList> list;


    public DayBookAdapter(FragmentActivity context, List<DayBookList> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        CashBookModelBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cash_book_model, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        holder.binding.viewBtn.setVisibility(View.GONE);
        DayBookList currentItem = list.get(position);
        holder.binding.date.setText(":  " + currentItem.getTransactionDate());
        holder.binding.enterprise.setText(":  " + currentItem.getEnterprise());
        holder.binding.transactionId.setText(":  " + currentItem.getTransactionId());

        if (currentItem.getReference() != null) {
            holder.binding.reference.setText(":  " + currentItem.getReference());
        }

        holder.binding.company.setText(":  " + currentItem.getCompany());
        holder.binding.receipt.setText(":  " + currentItem.getIn());
        holder.binding.payment.setText(":  " + currentItem.getOut());
        holder.binding.balance.setText(":  " + currentItem.getBalance());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        CashBookModelBinding binding;
        public MyHolder(@NonNull @NotNull CashBookModelBinding binding) {
            super(binding.getRoot());
            this.binding =binding;

        }
    }
}
