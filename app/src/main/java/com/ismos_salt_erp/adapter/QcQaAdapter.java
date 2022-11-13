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
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.utils.QcQaUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QcQaAdapter extends RecyclerView.Adapter<QcQaAdapter.MyHolder> {

    private FragmentActivity context;
    private List<String> nameList;
    private List<Integer> imageList;

    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ManagementFragmentModelBindingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.management_fragment_model_binding, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        Integer currentImage = imageList.get(position);
        String currentName = nameList.get(position);
        holder.binding.textHomeItem.setText(currentName);
        holder.binding.imageHomeItem.setImageDrawable(ContextCompat.getDrawable(context, currentImage));

        AnimationTime.animation(holder.binding.imageHomeItem);
         holder.binding.setClickHandle(() -> {
            if (nameList.get(position).equals(QcQaUtil.addQcQa)) {

                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1337)) {
                        Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_addQc_Qa);
                        return;
                    } else {
                        Toasty.info(context, "You don't have permission for access this portion", Toasty.LENGTH_LONG).show();
                    }

            }
            if (nameList.get(position).equals(QcQaUtil.pendingQcQa)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1341)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("portion", QcQaUtil.pendingQcQa);
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_qcQaPendingFragment, bundle);
                    return;
                } else {
                    Toasty.info(context, "You don't have permission for access this portion", Toasty.LENGTH_LONG).show();
                }
                return;


            }
            if (nameList.get(position).equals(QcQaUtil.declinedQcQa)) {

                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1342)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("portion", QcQaUtil.declinedQcQa);
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_qcQaPendingFragment, bundle);
                    return;
                } else {
                    Toasty.info(context, "You don't have permission for access this portion", Toasty.LENGTH_LONG).show();
                }
                return;


            }
            if (nameList.get(position).equals(QcQaUtil.qcQaHistory)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1343)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("portion", QcQaUtil.qcQaHistory);
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_qcQaPendingFragment, bundle);
                    return;
                } else {
                    Toasty.info(context, "You don't have permission for access this portion", Toasty.LENGTH_LONG).show();
                }
                return;
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private ManagementFragmentModelBindingBinding binding;

        public MyHolder(ManagementFragmentModelBindingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
