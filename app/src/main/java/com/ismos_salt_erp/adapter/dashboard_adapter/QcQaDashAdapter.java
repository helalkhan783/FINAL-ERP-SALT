package com.ismos_salt_erp.adapter.dashboard_adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.BankBalanceLayBinding;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.QcQaDashBoardList;

import java.util.List;

public class QcQaDashAdapter extends RecyclerView.Adapter<QcQaDashAdapter.ViewHolder> {
    FragmentActivity context;
    List<QcQaDashBoardList> lists;

    public QcQaDashAdapter(FragmentActivity context, List<QcQaDashBoardList> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public QcQaDashAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BankBalanceLayBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R
                .layout.bank_balance_lay, parent, false);
        return new QcQaDashAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull QcQaDashAdapter.ViewHolder holder, int position) {
        QcQaDashBoardList customerList =  lists.get(position);
        int po=position+1;
        holder.binding.sl.setText(""+po);
        holder.binding.accountName.setText(""+customerList.getEnterpriseName());
        holder.binding.balance.setText(""+customerList.getTotalQcPercent());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        BankBalanceLayBinding binding;
        public ViewHolder(@NonNull BankBalanceLayBinding binding) {
            super(binding.getRoot());
            this.binding =binding;
        }
    }
}
