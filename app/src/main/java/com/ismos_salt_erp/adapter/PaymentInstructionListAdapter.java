package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.serverResponseModel.PaymentInstructionListResponse;
import com.ismos_salt_erp.utils.DateFormatRight;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.utils.replace.ReplaceCommaFromString;
import com.ismos_salt_erp.view.fragment.accounts.PaymentInstructionListFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentInstructionListAdapter extends RecyclerView.Adapter<PaymentInstructionListAdapter.MyHolder> {
    FragmentActivity context;
    List<PaymentInstructionListResponse> list;
    PaymentInstructionListFragment update;

    public PaymentInstructionListAdapter(FragmentActivity activity, List<PaymentInstructionListResponse> list, PaymentInstructionListFragment update) {
        this.context = activity;
        this.list = list;
        this.update = update;
    }

    @NonNull
    @Override
    public PaymentInstructionListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.payment_instruction_list_item_model, parent, false);
        return new PaymentInstructionListAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentInstructionListAdapter.MyHolder holder, int position) {
        PaymentInstructionListResponse currentList = list.get(position);
        holder.edit.setVisibility(View.VISIBLE);
        holder.companyName.setText(":  " + currentList.getCompanyName() + "@" + currentList.getCustomer_fname());
        // holder.ownerName.setText(":  " + currentList.getCustomer_fname());
        holder.date.setText(":  " + new DateFormatRight(context, currentList.getDate()).onlyDayMonthYear());
        holder.totalAmount.setText(":  " + DataModify.addFourDigit(ReplaceCommaFromString.replaceComma(currentList.getTotalAmount())) + MtUtils.priceUnit);
        holder.totalPaid.setText(":  " + DataModify.addFourDigit(ReplaceCommaFromString.replaceComma(currentList.getTotalPaid())) + MtUtils.priceUnit);
        holder.totalDue.setText(":  " + DataModify.addFourDigit(ReplaceCommaFromString.replaceComma(String.valueOf(currentList.getDue()))) + MtUtils.priceUnit);
        holder.totalLimit.setText(":  " + DataModify.addFourDigit(ReplaceCommaFromString.replaceComma(currentList.getPaymentLimit())) + MtUtils.priceUnit);
        if (currentList.getLpa() != null) {
            holder.lastPurchaseAmount.setText(":  " + DataModify.addFourDigit(ReplaceCommaFromString.replaceComma(currentList.getPaymentLimit())) + MtUtils.priceUnit);

        }

        holder.edit.setOnClickListener(v -> {
            update.updatePriceList(holder.getAdapterPosition(), currentList.getInstructionId(), currentList.getPaymentLimit(), currentList.getCompanyName() + "@" + currentList.getCustomer_fname(),currentList.getDate());
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_company_name)
        TextView companyName;
        @BindView(R.id.tv_owner_name)
        TextView ownerName;
        @BindView(R.id.tv_total_amount)
        TextView totalAmount;
        @BindView(R.id.tv_total_paid)
        TextView totalPaid;
        @BindView(R.id.tv_total_due)
        TextView totalDue;
        @BindView(R.id.tv_total_limit)
        TextView totalLimit;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.edit)
        ImageButton edit;
        @BindView(R.id.lastPurchaseAmount)
        TextView lastPurchaseAmount;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
