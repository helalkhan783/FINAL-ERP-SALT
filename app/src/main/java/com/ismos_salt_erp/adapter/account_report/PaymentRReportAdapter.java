package com.ismos_salt_erp.adapter.account_report;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.PaymentReportLayout1Binding;
import com.ismos_salt_erp.databinding.PaymentReportLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.account_report.PaymentReportList;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PaymentRReportAdapter extends RecyclerView.Adapter<PaymentRReportAdapter.viewHolder> {
    private FragmentActivity context;
    List<PaymentReportList> saleReports;

    public PaymentRReportAdapter(FragmentActivity context, List<PaymentReportList> saleReports) {
        this.context = context;
        this.saleReports = saleReports;
    }

    @NonNull
    @NotNull
    @Override
    public PaymentRReportAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        PaymentReportLayout1Binding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.payment_report_layout1, parent, false);
        return new PaymentRReportAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PaymentRReportAdapter.viewHolder holder, int position) {
        PaymentReportList currentitem = saleReports.get(position);
        try {
            holder.itembinding.recptTv.setText("Payment");
            holder.itembinding.date.setText(":  " + currentitem.getPaymentDate());
            holder.itembinding.companyName.setText(":  " + currentitem.getCompanyName() +"@"+currentitem.getCustomerFname());
            holder.itembinding.transactionType.setText(":  " + currentitem.getPaymentTypeName());
            holder.itembinding.receiptAmount.setText(":  " + DataModify.addFourDigit(currentitem.getPaid_amount())+" "+ MtUtils.priceUnit);

        } catch (Exception e) {


        }
    }

    @Override
    public int getItemCount() {
        return saleReports.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private PaymentReportLayout1Binding itembinding;

        public viewHolder(@NonNull @NotNull PaymentReportLayout1Binding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }}

