package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.serverResponseModel.PaymentInstructionResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentInstructionAdapter extends RecyclerView.Adapter<PaymentInstructionAdapter.MyHolder> {

    FragmentActivity context;
    List<PaymentInstructionResponse> paymentInstructionResponseList;

    public PaymentInstructionAdapter(FragmentActivity activity, List<PaymentInstructionResponse> paymentInstructionResponseList) {
        this.context = activity;
        this.paymentInstructionResponseList = paymentInstructionResponseList;
    }

    @NonNull
    @Override
    public PaymentInstructionAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.payment_instruction_item_model, parent, false);
        return new PaymentInstructionAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentInstructionAdapter.MyHolder holder, int position) {

        holder.companyName.setText(paymentInstructionResponseList.get(position).getCompanyName());
        holder.ownerName.setText(paymentInstructionResponseList.get(position).getCustomer_fname());
       holder.totalPaid.setText(paymentInstructionResponseList.get(position).getTotalPaid() + " BDT");
        holder.totalDue.setText(paymentInstructionResponseList.get(position).getDue() + " BDT");
        holder.paymentLimitTV.setText(paymentInstructionResponseList.get(position).getPaymentLimit() + " BDT");

    }

    @Override
    public int getItemCount() {
        return paymentInstructionResponseList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_company_name)
        TextView companyName;
        @BindView(R.id.tv_owner_name)
        TextView ownerName;
        @BindView(R.id.tv_total_paid)
        TextView totalPaid;
        @BindView(R.id.tv_total_due)
        TextView totalDue;
        @BindView(R.id.payment_limit_tv)
        TextView paymentLimitTV;
        @BindView(R.id.payment_limit_edittext)
        EditText paymentLimit;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
