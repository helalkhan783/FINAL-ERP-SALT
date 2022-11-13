package com.ismos_salt_erp.adapter;

import android.os.Bundle;
import android.util.Log;
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
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.utils.SupplierUtils;
import com.ismos_salt_erp.view.fragment.monitoring.MonitoringListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.MyHolder> {
    FragmentActivity context;
    List<String> nameList;
    List<Integer> integers;
    View view;

    @NonNull
    @NotNull
    @Override
    public SupplierAdapter.MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ManagementFragmentModelBindingBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.management_fragment_model_binding, parent, false);
        return new SupplierAdapter.MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SupplierAdapter.MyHolder holder, int position) {
        Integer currentImage = integers.get(position);
        String currentName = nameList.get(position);
        holder.binding.textHomeItem.setText(currentName);

        holder.binding.imageHomeItem.setImageDrawable(ContextCompat.getDrawable(context, currentImage));
        AnimationTime.animation(holder.binding.imageHomeItem);

        holder.binding.setClickHandle(() -> {
            if (nameList.get(position).equals(SupplierUtils.addLocalSupplier)) {
                try {

                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1104)) {
                        Navigation.findNavController(view).navigate(R.id.action_managementFragment_to_addNewLocalSupplierFragment);
                        return;
                    } else {
                        Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                    }
                    return;
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                    return;
                }
            }

            if (nameList.get(position).equals(SupplierUtils.addForeignSupplier)) {
                try {
                    String currentProfileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();

                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1105)) {
                        Navigation.findNavController(view).navigate(R.id.action_managementFragment_to_addNewForeignSupplierFragment);
                        return;
                    } else {
                        Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                    }
                    return;
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }
            }

            if (nameList.get(position).equals(SupplierUtils.supplierList)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1106)) {
                    Bundle bundle = new Bundle();
                    MonitoringListFragment.isFirstLoad = 0;
                    MonitoringListFragment.pageNumber = 1;
                    bundle.putString("portion", nameList.get(position));
                    Navigation.findNavController(view).navigate(R.id.action_managementFragment_to_monitoringListFragment, bundle);
                    return;
                } else {
                    Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                }
            }

            if (nameList.get(position).equals(SupplierUtils.supplierTrashList)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1107)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("portion", nameList.get(position));
                    MonitoringListFragment.isFirstLoad = 0;
                    MonitoringListFragment.pageNumber = 1;
                    Navigation.findNavController(view).navigate(R.id.action_managementFragment_to_monitoringListFragment, bundle);
                    return;
                } else {
                    Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                }

            }
        });
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

