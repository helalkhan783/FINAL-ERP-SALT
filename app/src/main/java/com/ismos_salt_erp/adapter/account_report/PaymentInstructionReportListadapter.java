package com.ismos_salt_erp.adapter.account_report;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
 import com.ismos_salt_erp.databinding.PaymentReportLayoutBinding;
 import com.ismos_salt_erp.serverResponseModel.account_report.PaymentInstructionReportList;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PaymentInstructionReportListadapter extends RecyclerView.Adapter<PaymentInstructionReportListadapter.viewHolder> {
    private FragmentActivity context;
    List<PaymentInstructionReportList> saleReports;

    public PaymentInstructionReportListadapter(FragmentActivity context, List<PaymentInstructionReportList> saleReports) {
        this.context = context;
        this.saleReports = saleReports;
    }

    @NonNull
    @NotNull
    @Override
    public PaymentInstructionReportListadapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        PaymentReportLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.payment_report_layout, parent, false);
        return new PaymentInstructionReportListadapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PaymentInstructionReportListadapter.viewHolder holder, int position) {
        PaymentInstructionReportList currentitem = saleReports.get(position);
        try {
            holder.itembinding.date.setText(":  " + currentitem.getPaymentDate());
            holder.itembinding.companyName.setText(":  " + currentitem.getCompanyName());
            holder.itembinding.ownerName.setText(":  " + currentitem.getCustomerFname());
            holder.itembinding.totalAmount.setText(":  " + currentitem.getOrderGrandTotal() +" "+ MtUtils.priceUnit);
            holder.itembinding.totalPaid.setText(":  " + DataModify.addFourDigit(currentitem.getOrderTotalPaid())  +" "+ MtUtils.priceUnit);
            holder.itembinding.due.setText(":  " + DataModify.addFourDigit(String.valueOf(currentitem.getOrderDue()))  +" "+ MtUtils.priceUnit);
            holder.itembinding.paymentLimit.setText(":  " + DataModify.addFourDigit(currentitem.getPayLimitAmount()) +" "+ MtUtils.priceUnit);

        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return saleReports.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private PaymentReportLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull PaymentReportLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }}

