package com.ismos_salt_erp.adapter.account_report;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.InstructionReportLayoutBinding;
import com.ismos_salt_erp.databinding.PaymentReportLayout1Binding;
import com.ismos_salt_erp.serverResponseModel.account_report.TransactionReportList;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InstructionreportAdapter extends RecyclerView.Adapter<InstructionreportAdapter.viewHolder> {
    private FragmentActivity context;
    List<TransactionReportList> saleReports;

    public InstructionreportAdapter(FragmentActivity context, List<TransactionReportList> saleReports) {
        this.context = context;
        this.saleReports = saleReports;
    }

    @NonNull
    @NotNull
    @Override
    public InstructionreportAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        InstructionReportLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.instruction_report_layout, parent, false);
        return new InstructionreportAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull InstructionreportAdapter.viewHolder holder, int position) {
        TransactionReportList currentitem = saleReports.get(position);
        try {
            holder.itembinding.date.setText(":  " + currentitem.getPaymentDateTime());
            holder.itembinding.companyName.setText(":  " +  currentitem.getCompanyName() +"@"+currentitem.getCustomerFname());
            holder.itembinding.transactionType.setText(":  " + currentitem.getPaymentTypeName());
            holder.itembinding.particular.setText(":  " + currentitem.getParticular());
            holder.itembinding.enterprise.setText(":  " + currentitem.getStoreName());

            holder.itembinding.amount.setText(":  " + DataModify.addFourDigit(String.valueOf(currentitem.getTotalAmount()))+" "+ MtUtils.priceUnit);

        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return saleReports.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private InstructionReportLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull InstructionReportLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }}

