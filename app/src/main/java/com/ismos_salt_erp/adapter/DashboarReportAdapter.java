package com.ismos_salt_erp.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.ManagementFragmentModelBindingBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.utils.AnimationTime;
import com.ismos_salt_erp.utils.DashBoardReportUtils;
import com.ismos_salt_erp.utils.PermissionUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;
import lombok.NonNull;

public class DashboarReportAdapter extends RecyclerView.Adapter<DashboarReportAdapter.MyHolder> {
    FragmentActivity context;
    List<String> nameList;
    List<Integer> integers;
    View view;

    public DashboarReportAdapter(FragmentActivity context, List<String> nameList, List<Integer> integers, View view) {
        this.context = context;
        this.nameList = nameList;
        this.integers = integers;
        this.view = view;
    }

    @NonNull
    @NotNull
    @Override
    public DashboarReportAdapter.MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ManagementFragmentModelBindingBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.management_fragment_model_binding, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DashboarReportAdapter.MyHolder holder, int position) {
        Integer currentImage = integers.get(position);
        String currentName = nameList.get(position);
        holder.binding.textHomeItem.setText(currentName);
        holder.binding.textHomeItem.setSelected(true);
        holder.binding.imageHomeItem.setImageDrawable(ContextCompat.getDrawable(context, currentImage));
        AnimationTime.animation( holder.binding.imageHomeItem);

        holder.binding.setClickHandle(() -> {
            gotoNext(holder, nameList.get(position), DashBoardReportUtils.todaysPurchase, 1855);
            gotoNext(holder, nameList.get(position), DashBoardReportUtils.todaysSale, 1856);
            gotoNext(holder, nameList.get(position), DashBoardReportUtils.todaysProduction, 1769);
            gotoNext(holder, nameList.get(position), DashBoardReportUtils.todaysIndustrialIodization, 1770);
            gotoNext(holder, nameList.get(position), DashBoardReportUtils.lastMonthQcQa, 1610);
            gotoNext(holder, nameList.get(position), DashBoardReportUtils.todaysExpense, 1852);
            gotoNext(holder, nameList.get(position), DashBoardReportUtils.currentAbailAbleBalance, 1772);
            gotoNext(holder, nameList.get(position), DashBoardReportUtils.todaysSaleBasedOnCustomer, 1768);
            gotoNext(holder, nameList.get(position), DashBoardReportUtils.todaysPurchaseBasedOnSupplier, 1767);
            gotoNext(holder, nameList.get(position), DashBoardReportUtils.topTenSupplierBasedOnPurchase, 1773);
            gotoNext(holder, nameList.get(position), DashBoardReportUtils.topTenCustomerBasedOnSale, 1774);

        });
    }

    private void gotoNext(@NonNull MyHolder holder, String position, String whichPortion, int permissionId) {
        if (position.equals(whichPortion)) {
            if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(permissionId)) {
                Bundle bundle = new Bundle();
                bundle.putString("pageName", whichPortion);
                bundle.putString("portion", whichPortion);
                bundle.putString("from", "FromDashboard");
                Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_packetingReportFragment, bundle);
                return;
            } else {
                Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        private ManagementFragmentModelBindingBinding binding;

        public MyHolder(ManagementFragmentModelBindingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}

