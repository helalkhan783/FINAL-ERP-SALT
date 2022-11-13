package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.MillerEmployeeReportListLayoutBinding;
import com.ismos_salt_erp.view.fragment.all_report.employee_report.list.EmployeeReportProfuctList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MillerEmployeeReportListAdapter extends RecyclerView.Adapter<MillerEmployeeReportListAdapter.viewHolder>  {
    private   FragmentActivity activity;
    private  List<EmployeeReportProfuctList> profuctList;
    @NonNull
    @NotNull
    @Override
    public MillerEmployeeReportListAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        MillerEmployeeReportListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.miller_employee_report_list_layout, parent, false);
        return new MillerEmployeeReportListAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MillerEmployeeReportListAdapter.viewHolder holder, int position) {
        EmployeeReportProfuctList currentitem = profuctList.get(position);
        try {

            String fullTimeMalee = "0", fullTimeFemalee = "0", partTimemalee = "0", partTimeFemailee = "0", totalTechMalee = "0", totalTechFemalee = "0";
            holder.itembinding.millerName.setText(":  " + currentitem.getFullName());
            if (!currentitem.getFullTimeMale().isEmpty()) {
                fullTimeMalee = currentitem.getFullTimeMale();
            }
            if (!currentitem.getFullTimeFemale().isEmpty()) {
                fullTimeFemalee = currentitem.getFullTimeFemale();
            }
            if (!currentitem.getPartTimeMale().isEmpty()) {
                partTimemalee = currentitem.getPartTimeMale();
            }
            if (!currentitem.getPartTimeFemail().isEmpty()) {
                partTimeFemailee = currentitem.getPartTimeFemail();
            }

            if (!currentitem.getTotalTechMale().isEmpty()) {
                totalTechMalee = currentitem.getTotalTechMale();
            }

            if (!currentitem.getTotalTechFemale().isEmpty()) {
                totalTechFemalee = currentitem.getTotalTechFemale();
            }

            holder.itembinding.fullTimeMale.setText(":  " + "M=" + fullTimeMalee);
            holder.itembinding.fullTimeFemale.setText("F=" + fullTimeFemalee);

            holder.itembinding.partTimeMale.setText(":  " + "M=" + partTimemalee);
            holder.itembinding.partTimeFemale.setText("F=" + partTimeFemailee);

            holder.itembinding.technicalMale.setText(":  " + "M=" + totalTechMalee);
            holder.itembinding.technicalFemale.setText("F=" + totalTechFemalee);


            int totalMaleEmployee = 0, totalFemaleEmployee = 0, inTotalEmployee = 0, totalTechFemale = 0;
            int fullTimeMale = 0, partTimeMale = 0, totalTechMale = 0, fullTimeFemale = 0, partTimeFemale = 0;

            if (!currentitem.getFullTimeMale().isEmpty()) {
                fullTimeMale = Integer.parseInt(currentitem.getFullTimeMale());
            }
            if (!currentitem.getPartTimeMale().isEmpty()) {
                partTimeMale = Integer.parseInt(currentitem.getPartTimeMale());
            }
            if (!currentitem.getTotalTechMale().isEmpty()) {
                totalTechMale = Integer.parseInt(currentitem.getTotalTechMale());
            }
            if (!currentitem.getFullTimeFemale().isEmpty()) {
                fullTimeFemale = Integer.parseInt(currentitem.getFullTimeFemale());
            }
            if (!currentitem.getPartTimeFemail().isEmpty()) {
                partTimeFemale = Integer.parseInt(currentitem.getPartTimeFemail());
            }
            if (!currentitem.getTotalTechFemale().isEmpty()) {
                totalTechFemale = Integer.parseInt(currentitem.getTotalTechFemale());
            }

            totalMaleEmployee = fullTimeMale + partTimeMale + totalTechMale;
            totalFemaleEmployee = fullTimeFemale + partTimeFemale + totalTechFemale;
            inTotalEmployee = totalMaleEmployee + totalFemaleEmployee;

            holder.itembinding.totalEmployee.setText(":  " + inTotalEmployee);
            holder.itembinding.totalMaleEmployee.setText(":  " + "M=" + totalMaleEmployee);
            holder.itembinding.totalFemaleEmployee.setText("F=" + totalFemaleEmployee);
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return profuctList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private MillerEmployeeReportListLayoutBinding itembinding;
        public viewHolder(@NonNull @NotNull MillerEmployeeReportListLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}