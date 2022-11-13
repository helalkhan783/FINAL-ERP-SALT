package com.ismos_salt_erp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.serverResponseModel.AddNewLimitInstructionResponse;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.utils.replace.ReplaceCommaFromString;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNewLimitPaymentInstructionAdapter extends RecyclerView.Adapter<AddNewLimitPaymentInstructionAdapter.MyHolder> {

    FragmentActivity context;
    List<AddNewLimitInstructionResponse> addNewLimitPaymentInstructionResponseList;
    public static List<String> customerIdArray, paymentArray;


    public AddNewLimitPaymentInstructionAdapter(FragmentActivity activity, List<AddNewLimitInstructionResponse> addNewLimitPaymentInstructionResponseList) {
        this.context = activity;
        this.addNewLimitPaymentInstructionResponseList = addNewLimitPaymentInstructionResponseList;

        customerIdArray = new ArrayList<>();
        paymentArray = new ArrayList<>();
    }

    @NonNull
    @Override
    public AddNewLimitPaymentInstructionAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.add_new_limit_item_layout, parent, false);
        return new AddNewLimitPaymentInstructionAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddNewLimitPaymentInstructionAdapter.MyHolder holder, int position) {

        holder.companyName.setText(addNewLimitPaymentInstructionResponseList.get(position).getCompanyName());
        holder.ownerName.setText(addNewLimitPaymentInstructionResponseList.get(position).getCustomerFname());
     //   holder.totalPaid.setText(DataModify.addFourDigit(ReplaceCommaFromString.replaceComma(String.valueOf(addNewLimitPaymentInstructionResponseList.get(position).getTotalPaid()))) + MtUtils.priceUnit);
        holder.totalDue.setText(DataModify.addFourDigit(ReplaceCommaFromString.replaceComma(String.valueOf(addNewLimitPaymentInstructionResponseList.get(position).getDue()))) + MtUtils.priceUnit);
        holder.lastReceivedAmount.setText(DataModify.addFourDigit(ReplaceCommaFromString.replaceComma(String.valueOf(addNewLimitPaymentInstructionResponseList.get(position).getLastPaidAmount()))) + MtUtils.priceUnit);
        holder.lastReceivedDate.setText("Date: "+addNewLimitPaymentInstructionResponseList.get(position).getLastReceivedDate());
        customerIdArray.add(addNewLimitPaymentInstructionResponseList.get(position).getCustomerID());

    }

    @Override
    public int getItemCount() {
        try {
            return addNewLimitPaymentInstructionResponseList.size();
        } catch (Exception e) {
            Log.d(this.getClass().getSimpleName(), e.getLocalizedMessage());
            return 0;
        }
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
        @BindView(R.id.last_received_amount)
        TextView lastReceivedAmount;
        @BindView(R.id.last_received_date)
        TextView lastReceivedDate;
        @BindView(R.id.payment_limit_edittext)
        EditText paymentLimitEditText;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }

}
