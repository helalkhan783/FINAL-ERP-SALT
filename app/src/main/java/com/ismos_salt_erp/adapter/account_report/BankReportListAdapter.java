package com.ismos_salt_erp.adapter.account_report;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.BankReportLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.account_report.BankReportList;
import com.ismos_salt_erp.utils.replace.DataModify;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BankReportListAdapter extends RecyclerView.Adapter<BankReportListAdapter.viewHolder> {
    private FragmentActivity context;
    List<BankReportList> saleReports;
    String from;

    public BankReportListAdapter(FragmentActivity context, List<BankReportList> saleReports, String from) {
        this.context = context;
        this.saleReports = saleReports;
        this.from = from;
    }

    @NonNull
    @NotNull
    @Override
    public BankReportListAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        BankReportLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.bank_report_layout, parent, false);
        return new BankReportListAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BankReportListAdapter.viewHolder holder, int position) {
        BankReportList currentitem = saleReports.get(position);
        if (from != null) {
            if (from.equals("bankAccount")) {
                holder.itembinding.slLay.setVisibility(View.GONE);
                holder.itembinding.detailsBtnLayout.setVisibility(View.VISIBLE);
                holder.itembinding.processByLay.setVisibility(View.VISIBLE);
                holder.itembinding.partyNameLayout.setVisibility(View.VISIBLE);
                //    holder.itembinding.refLay.setVisibility(View.VISIBLE);
                if (!String.valueOf(currentitem.getChequeNo()).equals("-1.0")) {
                    holder.itembinding.chequeLayout.setVisibility(View.VISIBLE);
                    holder.itembinding.chequeNumber.setText(":  " + currentitem.getChequeNo());
                }
            }
        }
        position += 1;
        try {
            holder.itembinding.sl.setText(":  " + position);
            holder.itembinding.date.setText(":  " + currentitem.getTransactionDate());
            holder.itembinding.particulars.setText(":  " + currentitem.getTransectionType());
            if (currentitem.getBankName() != null) {
                holder.itembinding.bankName.setText(":  " + currentitem.getBankName());

            }
            if (currentitem.getAccountantName() != null) {

                holder.itembinding.accountName.setText(":  " + currentitem.getAccountantName());

            }
            if (currentitem.getAccountNumber() != null) {
                holder.itembinding.accountNo.setText(":  " + currentitem.getAccountNumber());

            }

            if (currentitem.getParty_name() != null) {
                holder.itembinding.partyName.setText(":  " + currentitem.getParty_name());

            }
            if (currentitem.getReferenceID() != null) {
                holder.itembinding.refId.setText(":  " + currentitem.getReferenceID());
            }
            if (currentitem.getEntryUserName() != null) {
                holder.itembinding.processBy.setText(":  " + currentitem.getEntryUserName());
            }

            holder.itembinding.in.setText(":  " + DataModify.addFourDigit(String.valueOf(currentitem.getDepositeAmountIn())));
            holder.itembinding.out.setText(":  " + DataModify.addFourDigit(currentitem.getDepositeAmountOut()));
            double balance = currentitem.getDepositeAmountIn() - Double.parseDouble(currentitem.getDepositeAmountOut());
            holder.itembinding.balance.setText(":  " + DataModify.addFourDigit(String.valueOf(balance)));

        } catch (Exception e) {
        }


        holder.itembinding.viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from != null) {
                    if (from.equals("bankAccount")) {
                        Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return saleReports.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private BankReportLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull BankReportLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}

