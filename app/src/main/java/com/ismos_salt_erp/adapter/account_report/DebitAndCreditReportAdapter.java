package com.ismos_salt_erp.adapter.account_report;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.DebitReportLayoutBinding;
import com.ismos_salt_erp.databinding.InstructionReportLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.account_report.DebitAndCreditReportList;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DebitAndCreditReportAdapter extends RecyclerView.Adapter<DebitAndCreditReportAdapter.viewHolder> {
    private FragmentActivity context;
    List<DebitAndCreditReportList> saleReports;
    String type;

    public DebitAndCreditReportAdapter(FragmentActivity context, List<DebitAndCreditReportList> saleReports,String type) {
        this.context = context;
        this.saleReports = saleReports;
        this.type = type;
    }

    @NonNull
    @NotNull
    @Override
    public DebitAndCreditReportAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        DebitReportLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.debit_report_layout, parent, false);
        return new DebitAndCreditReportAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DebitAndCreditReportAdapter.viewHolder holder, int position) {
        DebitAndCreditReportList currentitem = saleReports.get(position);
        if (type.equals("1")){//1 Creditors
            holder.itembinding.date.setText("LPUR Date");
            holder.itembinding.ok.setText("LPNT Amount");
        }
        try {
            if (!(currentitem.getPaymentDate() ==null || currentitem.getPaymentDate().isEmpty())){
                holder.itembinding.rptLastDate.setText(":  " + currentitem.getPaymentDate());
               // holder.itembinding.rptLastDate.setText(":  " + new DateFormatRight(context,currentitem.getPaymentDate()).onlyDayMonthYear());
            }
            holder.itembinding.companyName.setText(":  " + currentitem.getCompanyName() +"@"+ currentitem.getCustomerFname());
            holder.itembinding.customerName.setText(":  " + currentitem.getCustomerFname());
            holder.itembinding.balance.setText(":  " + DataModify.addFourDigit(String.valueOf(currentitem.getBalanceAmount())) +MtUtils.priceUnit);
            holder.itembinding.rptLastAmount.setText(":  " + DataModify.addFourDigit(currentitem.getPaidAmount()) +MtUtils.priceUnit);
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return saleReports.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private DebitReportLayoutBinding itembinding;
        public viewHolder(@NonNull @NotNull DebitReportLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }}

