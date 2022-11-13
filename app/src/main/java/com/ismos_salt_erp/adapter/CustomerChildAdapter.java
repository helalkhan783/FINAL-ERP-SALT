package com.ismos_salt_erp.adapter;

import android.os.Bundle;
import android.util.Log;
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
import com.ismos_salt_erp.utils.AnimationTime;
import com.ismos_salt_erp.utils.CustomersUtil;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.monitoring.MonitoringListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerChildAdapter extends RecyclerView.Adapter<CustomerChildAdapter.MyHolder> {
    private FragmentActivity context;
    private List<String> customerNameList;
    private List<Integer> customerImageList;


    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ManagementFragmentModelBindingBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.management_fragment_model_binding, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        Integer currentImage = customerImageList.get(position);
        String currentName = customerNameList.get(position);
        holder.binding.textHomeItem.setText(currentName);
        holder.binding.imageHomeItem.setImageDrawable(ContextCompat.getDrawable(context, currentImage));
        AnimationTime.animation(holder.binding.imageHomeItem);

        holder.binding.setClickHandle(() -> {
            if (customerNameList.get(holder.getAdapterPosition()).equals(CustomersUtil.addNewCustomer)) {
                try {
                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(27)) {
                        Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_addNewCustomer);
                        return;
                    } else {
                        Toasty.info(context, "You don't have permission for access this portion", Toasty.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                    return;
                }

            }
            if (customerNameList.get(holder.getAdapterPosition()).equals(CustomersUtil.customerList)) {
                try {
                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1102)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("portion", CustomersUtil.customerList);
                        MonitoringListFragment.isFirstLoad = 0;
                        MonitoringListFragment.pageNumber = 1;
                        Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_monitoringListFragment, bundle);
                        return;
                    } else {
                        Toasty.info(context, "You don't have permission for access this portion", Toasty.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toasty.info(context, "" + e.getMessage(), Toasty.LENGTH_LONG).show();

                }
            }

            if (customerNameList.get(holder.getAdapterPosition()).equals(CustomersUtil.customerTrashList)) {
                try {
                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1103)) {
                        Bundle bundle = new Bundle();
                        MonitoringListFragment.isFirstLoad = 0;
                        MonitoringListFragment.pageNumber = 1;
                        bundle.putString("portion", CustomersUtil.customerTrashList);
                        Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_monitoringListFragment, bundle);
                        return;
                    } else {
                        Toasty.info(context, "You don't have permission for access this portion", Toasty.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return customerNameList.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        private ManagementFragmentModelBindingBinding binding;

        public MyHolder(ManagementFragmentModelBindingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
