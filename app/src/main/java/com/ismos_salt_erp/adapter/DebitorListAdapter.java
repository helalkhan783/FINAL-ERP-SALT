package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.DebitorsListLayoutBinding;

import com.ismos_salt_erp.serverResponseModel.DevitorsList;
import com.ismos_salt_erp.utils.MtUtils;

import java.util.List;

public class DebitorListAdapter extends RecyclerView.Adapter<DebitorListAdapter.MyHolder> {
    FragmentActivity activity;
    List<DevitorsList> list;
    String type;


    public DebitorListAdapter(FragmentActivity activity, List<DevitorsList> list, String type) {
        this.activity = activity;
        this.list = list;
        this.list = list;
        this.type = type;

    }

    @NonNull
    @Override
    public DebitorListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DebitorsListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.debitors_list_layout, parent, false);

        return new DebitorListAdapter.MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DebitorListAdapter.MyHolder holder, int position) {
        if (type.equals("2")) {//debotor
           holder.binding.pmtAmountTv.setText("Last Trx. Amount");
           holder.binding.pmtDateTv.setText("Last Trx. Date");
           holder.binding.supplierTv.setText("Customer Name");
         }

        DevitorsList currentList = list.get(position);
        holder.binding.companyName.setText(":  " + currentList.getCompany() +"@"+currentList.getCompany());
        holder.binding.supplierName.setText(":  " + currentList.getSupplier());
        holder.binding.pmtLastDate.setText(":  " + currentList.getLastPaymentDate());
        holder.binding.amount.setText(":  " + currentList.getLastPaymentAmount());
        holder.binding.balance.setText(":  " + currentList.getBalance());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        DebitorsListLayoutBinding binding;

        public MyHolder(@NonNull DebitorsListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
