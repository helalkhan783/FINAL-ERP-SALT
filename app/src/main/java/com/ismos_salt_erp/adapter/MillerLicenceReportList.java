package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.MillerLicenceReportListLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.list_response.MillerLisenceReportList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MillerLicenceReportList extends RecyclerView.Adapter<MillerLicenceReportList.viewHolder> {
    private FragmentActivity activity;
    private List<MillerLisenceReportList> profuctList;

    @NonNull
    @NotNull
    @Override
    public MillerLicenceReportList.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        MillerLicenceReportListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.miller_licence_report_list_layout, parent, false);
        return new MillerLicenceReportList.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MillerLicenceReportList.viewHolder holder, int position) {
        MillerLisenceReportList currentitem = profuctList.get(position);
        holder.itembinding.name.setText(":  "+currentitem.getMiller());
        holder.itembinding.licenceName.setText(":  "+currentitem.getMiller());
        holder.itembinding.issuerName.setText(":  "+currentitem.getIssuerName());
        holder.itembinding.issuingDate.setText(String.valueOf(":  "+currentitem.getIssueDate()));
        holder.itembinding.certificateDate.setText(":  "+currentitem.getCertificateDate());
        holder.itembinding.renewalDate.setText(":  "+currentitem.getRenewDate());
        holder.itembinding.remarks.setText(String.valueOf(":  "+currentitem.getRemarks()));


    }

    @Override
    public int getItemCount() {
        return profuctList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private MillerLicenceReportListLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull MillerLicenceReportListLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}
