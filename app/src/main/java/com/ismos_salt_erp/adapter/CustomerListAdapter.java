package com.ismos_salt_erp.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.AddStoreDialogLayoutBinding;
import com.ismos_salt_erp.databinding.CustomerListBindingBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.CustomerListModel;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.monitoring.MonitoringListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ViewHolder> {
    private FragmentActivity context;
    private List<CustomerListModel> customerLists;
    private View view;
    MonitoringListFragment click;

    @NonNull
    @NotNull
    @Override
    public CustomerListAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        CustomerListBindingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.
                getContext()), R.layout.customer_list_binding, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CustomerListAdapter.ViewHolder holder, int position) {
        CustomerListModel customerList = customerLists.get(position);
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(28)) {
            holder.binding.editBtn.setVisibility(View.GONE);
        }
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(29)) {
            holder.binding.delete.setVisibility(View.GONE);
        }
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1293)) {
            holder.binding.history.setVisibility(View.GONE);
        }
        try {
            holder.binding.comapanyName.setText(":  " + customerList.getCompanyName());
            holder.binding.ownerName.setText(":  " + customerList.getCustomerFname());
            holder.binding.phone.setText(":  " + customerList.getPhone());
            holder.binding.address.setText(":  " + customerList.getThana() + "; " + customerList.getDistrict() + "; \n   " + customerList.getDivision());
            holder.binding.dueLimit.setText(":  " + DataModify.addFourDigit(customerList.getDueLimit()) + MtUtils.priceUnit);
            holder.binding.editBtn.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("customerId", customerList.getCustomerID());
                MonitoringListFragment.pageNumber = 1;
                MonitoringListFragment.isFirstLoad = 0;
                Navigation.findNavController(view).navigate(R.id.action_monitoringListFragment_to_editCustomerFragment, bundle);

            });


            holder.binding.dueLimit.setOnClickListener(v -> {

                click.getPositionFromAdapter(holder.getAdapterPosition(), customerList.getCustomerID(), customerList.getDueLimit(),customerList.getCompanyName()+"@"+customerList.getCustomerFname());
            });

        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return customerLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CustomerListBindingBinding binding;

        public ViewHolder(CustomerListBindingBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
