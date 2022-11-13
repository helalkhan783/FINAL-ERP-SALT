package com.ismos_salt_erp.view.fragment;

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
import com.ismos_salt_erp.navigatehelaper.HomePageHelperClass;
import com.ismos_salt_erp.utils.AccountReportUtils;
import com.ismos_salt_erp.utils.AnimationTime;
import com.ismos_salt_erp.utils.PermissionUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;
import lombok.NonNull;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyHolder> {
    FragmentActivity context;
    List<String> nameList;
    List<Integer> integers;
    View view;
    AccountAdapter.MyHolder holder1;

    public AccountAdapter(FragmentActivity context, List<String> nameList, List<Integer> integers, View view) {
        this.context = context;
        this.nameList = nameList;
        this.integers = integers;
        this.view = view;
    }

    @NonNull
    @NotNull
    @Override
    public AccountAdapter.MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ManagementFragmentModelBindingBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.management_fragment_model_binding, parent, false);
        return new AccountAdapter.MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AccountAdapter.MyHolder holder, int position) {
        holder1 = holder;
        Integer currentImage = integers.get(position);
        String currentName = nameList.get(position);
        holder.binding.textHomeItem.setText(currentName);

        holder.binding.imageHomeItem.setImageDrawable(ContextCompat.getDrawable(context, currentImage));
        AnimationTime.animation(holder.binding.imageHomeItem);

        holder.binding.setClickHandle(() -> {
            if (nameList.get(position).equals(AccountReportUtils.creditors)) {
                HomePageHelperClass homePageHelperClass = new HomePageHelperClass(context,holder.binding.getRoot());
                homePageHelperClass.homePageToAccountListFragment(null, AccountReportUtils.creditors, R.id.action_managementFragment_to_transactionListFragmentt);
                return;
            }
            if (nameList.get(position).equals(AccountReportUtils.debitors)) {
                HomePageHelperClass homePageHelperClass = new HomePageHelperClass(context, holder.binding.getRoot());
                homePageHelperClass.homePageToAccountListFragment(null, AccountReportUtils.debitors, R.id.action_managementFragment_to_transactionListFragmentt);
                return;
            }

            gotoNext(nameList.get(position), AccountReportUtils.customerLedgerReport, 1752, "1");
            gotoNext(nameList.get(position), AccountReportUtils.supplierLedgerReport, 1753, "1");
            gotoNext(nameList.get(position), AccountReportUtils.vendorLedgerReport, 1754, "1");
            gotoNext(nameList.get(position), AccountReportUtils.bankLedger, 1755, "2");
            gotoNext(nameList.get(position), AccountReportUtils.receipt, 1756, "2");
            gotoNext(nameList.get(position), AccountReportUtils.paymentInstructions, 1759, "2");
            gotoNext(nameList.get(position), AccountReportUtils.payment, 1758, "2");
            gotoNext(nameList.get(position), AccountReportUtils.expense, 1650, "2");
            gotoNext(nameList.get(position), AccountReportUtils.transactionIn, 1760, "2");
            gotoNext(nameList.get(position), AccountReportUtils.transactionOut, 1761, "2");
            gotoNext(nameList.get(position), AccountReportUtils.dayBook, 1763, "2");
            gotoNext(nameList.get(position), AccountReportUtils.cashBook, 1762, "2");
        });
    }

    private void gotoNext(String position, String whichPortion, int permissionId, String valueForManageLayout) {
        try {
            if (position.equals(whichPortion)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(permissionId)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("pageName", whichPortion);
                    bundle.putString("portion", whichPortion);
                    bundle.putString("valueForManageLayout", valueForManageLayout);
                    Navigation.findNavController(holder1.binding.getRoot()).navigate(R.id.action_managementFragment_to_customerReportFragment, bundle);
                    return;
                } else {
                    Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                }
            }

        } catch (Exception e) {
            context.onBackPressed();
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

