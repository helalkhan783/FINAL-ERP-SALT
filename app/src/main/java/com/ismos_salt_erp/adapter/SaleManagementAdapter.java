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
import com.ismos_salt_erp.permission.HelperClass;
import com.ismos_salt_erp.utils.AnimationTime;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.utils.SaleUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SaleManagementAdapter extends RecyclerView.Adapter<SaleManagementAdapter.MyHolder> {
    private FragmentActivity context;
    private List<String> saleNameList;
    private List<Integer> saleImageList;


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
        Integer currentImage = saleImageList.get(position);
        String currentName = saleNameList.get(position);
        holder.binding.textHomeItem.setText(currentName);
        holder.binding.imageHomeItem.setImageDrawable(ContextCompat.getDrawable(context, currentImage));
        AnimationTime.animation( holder.binding.imageHomeItem);
        /**
         * handle item Click
         */
        holder.binding.setClickHandle(() -> {

            try {

                Bundle bundle = new Bundle();
                if (saleNameList.get(holder.getAdapterPosition()).equals(SaleUtil.newSale)) {
                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(5)) {
                        Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_addNewSale);
                        return;
                    } else
                        Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                    return;
                }
                if (saleNameList.get(holder.getAdapterPosition()).equals(SaleUtil.salePending)) {
                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(24)) {
                        bundle.putString("porson", SaleUtil.salePending);
                        Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_saleAllListFragment, bundle);
                        return;
                    }
                    Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                }

                if (saleNameList.get(holder.getAdapterPosition()).equals(SaleUtil.saleHistory)) {
                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(22)) {
                        bundle.putString("porson", SaleUtil.saleHistory);
                        Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_saleAllListFragment, bundle);
                        return;
                    }
                    Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();


                }

                if (saleNameList.get(holder.getAdapterPosition()).equals(SaleUtil.declineSaleList)) {
                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1090)) {
                        bundle.putString("porson", SaleUtil.declineSaleList);
                        Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_saleAllListFragment, bundle);
                        return;
                    }
                    Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();


                }
                if (saleNameList.get(holder.getAdapterPosition()).equals(SaleUtil.saleReturnHistory)) {
                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1092)) {
                        bundle.putString("porson", SaleUtil.saleReturnHistory);
                        Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_saleAllListFragment, bundle);
                        return;
                    } else {
                        Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                    }

                }
                if (saleNameList.get(holder.getAdapterPosition()).equals(SaleUtil.salePendingReturn)) {
                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1299)) {
                        bundle.putString("porson", SaleUtil.salePendingReturn);
                        Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_saleAllListFragment, bundle);
                        return;
                    }
                    Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();

                }

                if (saleNameList.get(holder.getAdapterPosition()).equals(SaleUtil.saleReturn)) {
                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1091)) {
                        bundle.putString("porson", SaleUtil.saleReturn);
                        Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_salesReturnFragment, bundle);
                        return;
                    } else {
                        Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                    }
                }

                if (saleNameList.get(holder.getAdapterPosition()).equals(SaleUtil.stockInfo)) {
                     HelperClass.navigate(SaleUtil.stockInfo,holder.binding.getRoot(),R.id.action_managementFragment_to_storeListFragment);
                    return;
                }
            } catch (Exception e) {}






        });

    }

    @Override
    public int getItemCount() {
        return saleImageList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private ManagementFragmentModelBindingBinding binding;

        public MyHolder(ManagementFragmentModelBindingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
