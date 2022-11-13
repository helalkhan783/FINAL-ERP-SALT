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
import com.ismos_salt_erp.utils.AnimationTime;
import com.ismos_salt_erp.utils.BankmanagementUtils;
import com.ismos_salt_erp.utils.PermissionUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;
import lombok.NonNull;

public class BankManagementAdapter extends RecyclerView.Adapter<BankManagementAdapter.MyHolder> {
    FragmentActivity context;
    List<String> nameList;
    List<Integer> integers;
    View view;

    public BankManagementAdapter(FragmentActivity context, List<String> nameList, List<Integer> integers, View view) {
        this.context = context;
        this.nameList = nameList;
        this.integers = integers;
        this.view = view;
    }

    @NonNull
    @NotNull
    @Override
    public BankManagementAdapter.MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ManagementFragmentModelBindingBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.management_fragment_model_binding, parent, false);
        return new BankManagementAdapter.MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BankManagementAdapter.MyHolder holder, int position) {
        Integer currentImage = integers.get(position);
        String currentName = nameList.get(position);
        holder.binding.textHomeItem.setText(currentName);

        holder.binding.imageHomeItem.setImageDrawable(ContextCompat.getDrawable(context, currentImage));
        AnimationTime.animation(holder.binding.imageHomeItem);

        holder.binding.setClickHandle(() -> {
            if (nameList.get(position).equals(BankmanagementUtils.bankAccountlist)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1096)) {
                   Bundle bundle = new Bundle();
                   bundle.putString("portion",BankmanagementUtils.bankAccountlist);
                   bundle.putString("pageName",BankmanagementUtils.bankAccountlist);
                    Navigation.findNavController(view).navigate(R.id.action_managementFragment_to_bankAllListFragment,bundle);
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

