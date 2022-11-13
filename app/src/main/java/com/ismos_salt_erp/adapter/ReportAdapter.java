package com.ismos_salt_erp.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.ManagementFragmentModelBindingBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.navigatehelaper.ReportHelper;
import com.ismos_salt_erp.utils.AnimationTime;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.utils.ReportUtils;

import org.jetbrains.annotations.NotNull;


import java.util.List;

import es.dmoral.toasty.Toasty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyHolder> {
    private FragmentActivity context;
    private List<String> nameList;
    private List<Integer> imageList;


    @NonNull
    @NotNull
    @Override
    public ReportAdapter.MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ManagementFragmentModelBindingBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.management_fragment_model_binding, parent, false);
        return new ReportAdapter.MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        String currentName = nameList.get(position);
        Integer CurrentImage = imageList.get(position);

        holder.binding.textHomeItem.setText((CharSequence) currentName);
        holder.binding.imageHomeItem.setImageDrawable(ContextCompat.getDrawable(context, CurrentImage));
        holder.binding.textHomeItem.setSelected(true);

        AnimationTime.animation( holder.binding.imageHomeItem);
        Bundle bundle = new Bundle();
        /**
         * For handle Monitoring
         */
        holder.binding.setClickHandle(() -> {
            ReportHelper reportHelper = new ReportHelper(context, holder.binding.getRoot());
            String positionString = nameList.get(position);

            reportHelper.managementToReportLisFragment(positionString, ReportUtils.purchaseReport, 1278);
            reportHelper.managementToReportLisFragment(positionString, ReportUtils.purchaseReturnReport, 1549);
            reportHelper.managementToReportLisFragment(positionString, ReportUtils.saleReport, 1277);
            reportHelper.managementToReportLisFragment(positionString, ReportUtils.saleReturnReport, 1543);
            reportHelper.managementToReportLisFragment(positionString, ReportUtils.districtSaleReport, 1591);


            reportHelper.managementReconciliation(positionString, ReportUtils.reconciliation, 1539);
            reportHelper.managementReconciliation(positionString, ReportUtils.stockInOutReport, 1550);
            reportHelper.managementReconciliation(positionString, ReportUtils.transferReport, 1537);


            reportHelper.managementToPacketingReport(positionString, ReportUtils.PacketingReport, 1556);
            reportHelper.managementToPacketingReport(positionString, ReportUtils.packagingReport, 1557);
            reportHelper.managementToPacketingReport(positionString, ReportUtils.productionReport, 1535);
            reportHelper.managementToPacketingReport(positionString, ReportUtils.iodineUsedReport, 1541);



            if (nameList.get(position).equals(ReportUtils.accountReports)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1279)) {
                    bundle.putString("Item", ReportUtils.accountReports);
                    bundle.putString("pageName", "Account Report");
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_self, bundle);
                    return;
                } else {
                    Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                }

            }

            if (nameList.get(position).equals(ReportUtils.dashBoardReport)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1550)) {
                    bundle.putString("Item", ReportUtils.dashBoardReport);
                    bundle.putString("pageName", "Dashboard Report");
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_self, bundle);
                    return;
                } else {
                    Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                }

            }


            if (nameList.get(position).equals(ReportUtils.employeeReport)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1553)) {
                    bundle.putString("pageName", "Employee Report");
                    bundle.putString("portion", ReportUtils.employeeReport);
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_reportLicenceFragment2t, bundle);
                    return;
                } else {
                    Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                }
            }



   /*   if (nameList.get(position).equals(ReportUtils.licenceReport)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1554)) {
                    bundle.putString("portion", ReportUtils.licenceReport);
                    bundle.putString("pageName", "Licence Report");
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_reportLicenceFragment2t, bundle);
                    return;
                } else {
                    Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                }

            }
            if (nameList.get(position).equals(ReportUtils.millReport)) {


                bundle.putString("portion", ReportUtils.millReport);
                bundle.putString("pageName", "Mill Report");
                Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_reportLicenceFragment2t, bundle);


            }

            if (nameList.get(position).equals(ReportUtils.licenceExpireReport)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1555)) {
                    bundle.putString("pageName", "Licence Expire Report");
                    bundle.putString("portion", ReportUtils.licenceExpireReport);
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_reportLicenceFragment2t, bundle);
                    return;
                } else {
                    Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                }

            }

            if (nameList.get(position).equals(ReportUtils.qcqaReport)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1536)) {
                    bundle.putString("pageName", "QC/QA Report");
                    bundle.putString("portion", ReportUtils.qcqaReport);
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_reportLicenceFragment2t, bundle);
                    return;
                } else {
                    Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                }

            }

            if (nameList.get(position).equals(ReportUtils.monitoringReport)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1540)) {
                    bundle.putString("pageName", "Monitoring Report");
                    bundle.putString("portion", ReportUtils.monitoringReport);
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_reportLicenceFragment2t, bundle);
                    return;
                } else {
                    Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                }

            }


            }*/





        });

    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private ManagementFragmentModelBindingBinding binding;

        public MyHolder(ManagementFragmentModelBindingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
