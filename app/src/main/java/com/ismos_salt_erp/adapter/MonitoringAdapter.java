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
import com.ismos_salt_erp.utils.MonitoringUtil;
import com.ismos_salt_erp.utils.PermissionUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MonitoringAdapter extends RecyclerView.Adapter<MonitoringAdapter.MyHolder> {
    private FragmentActivity context;
    private List<String> monitoringNameList;
    private List<Integer> monitoringImageList;

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
        Integer currentImage = monitoringImageList.get(position);
        String currentName = monitoringNameList.get(position);
        holder.binding.textHomeItem.setText(currentName);
        holder.binding.imageHomeItem.setImageDrawable(ContextCompat.getDrawable(context, currentImage));


        /**
         * For handle Monitoring
         */
        holder.binding.setClickHandle(() -> {
            if (monitoringNameList.get(position).equals(MonitoringUtil.addMonitoring)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1426) || PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1)) {
                        Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_addNewMonitoring);
                        return;
                    } else {
                        Toasty.warning(context, "You don't have permission for access this portion", Toasty.LENGTH_LONG).show();
                    }

            }
            if (monitoringNameList.get(position).equals(MonitoringUtil.monitoringHistory)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1427) || PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("portion", MonitoringUtil.monitoringHistory);
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_monitoringListFragment, bundle);
                    return;
                } else {
                    Toasty.info(context, "You don't have permission for access this portion", Toasty.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return monitoringNameList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private ManagementFragmentModelBindingBinding binding;

        public MyHolder(ManagementFragmentModelBindingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
