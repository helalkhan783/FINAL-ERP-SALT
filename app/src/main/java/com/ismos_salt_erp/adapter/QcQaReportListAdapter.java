package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.QcqaReportListLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.QcqaReportList;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QcQaReportListAdapter extends RecyclerView.Adapter<QcQaReportListAdapter.ViewHolder> {
    FragmentActivity context;
    List<QcqaReportList> list;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QcqaReportListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.qcqa_report_list_layout, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QcqaReportList currentPosition = list.get(position);
        holder.binding.date.setText(":  " + currentPosition.getDate());
        holder.binding.modelName.setText(":  " + currentPosition.getModel());
        holder.binding.enterPriseName.setText(":  " + currentPosition.getStoreName());


        if (currentPosition.getTestName() != null){
    holder.binding.testName.setText(":  " + currentPosition.getTestName());
}
        if (currentPosition.getAssociation() != null) {
            holder.binding.association.setText(":  " + currentPosition.getAssociation());
        }
        if (currentPosition.getReference() != null) {
            holder.binding.reference.setText(":  " + currentPosition.getReference());
        }
        if (currentPosition.getParameterValue() != null) {
            holder.binding.value.setText(":  " + currentPosition.getParameterValue());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        QcqaReportListLayoutBinding binding;

        public ViewHolder(@NonNull QcqaReportListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
