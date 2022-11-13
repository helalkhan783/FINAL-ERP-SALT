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
import com.ismos_salt_erp.permission.HelperClass;
import com.ismos_salt_erp.utils.AnimationTime;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.utils.PurchaseUtill;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;
import lombok.AllArgsConstructor;


public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.MyHolder> {
    private FragmentActivity context;
    private List<String> saleNameList;
    private List<Integer> saleImageList;

    public PurchaseAdapter(FragmentActivity context, List<String> saleNameList, List<Integer> saleImageList) {
        this.context = context;
        this.saleNameList = saleNameList;
        this.saleImageList = saleImageList;
    }

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
        AnimationTime.animation(holder.binding.imageHomeItem);
        holder.binding.setClickHandle(() -> {

            if (saleNameList.get(holder.getAdapterPosition()).equals(PurchaseUtill.purchaseHistory)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1053) || PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("porson", PurchaseUtill.purchaseHistory);
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_allPurchaseListFragment, bundle);
                    return;
                } else {
                    Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                }
                return;
            }
            /**
             * for purchase pending list
             */
            if (saleNameList.get(holder.getAdapterPosition()).equals(PurchaseUtill.pendingPurchaseList)) {
                try {

                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1498)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("porson", PurchaseUtill.pendingPurchaseList);
                        Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_allPurchaseListFragment, bundle);
                        return;
                    } else {
                        Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }
                return;


            }


            if (saleNameList.get(holder.getAdapterPosition()).equals(PurchaseUtill.pendingPurchaseReturn)) {
                try {

                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1052)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("porson", PurchaseUtill.pendingPurchaseReturn);
                        Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_allPurchaseListFragment, bundle);
                        return;
                    } else {
                        Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }
                return;


            }

            if (saleNameList.get(holder.getAdapterPosition()).equals(PurchaseUtill.purchaseReturnHistory)) {

                try {

                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1057)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("porson", PurchaseUtill.purchaseReturnHistory);
                        Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_allPurchaseListFragment, bundle);
                        return;
                    } else {
                        Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }
                return;


            }


            if (saleNameList.get(holder.getAdapterPosition()).equals(PurchaseUtill.declinePurchaseList)) {

                Bundle bundle = new Bundle();
                bundle.putString("porson", PurchaseUtill.declinePurchaseList);
                Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_allPurchaseListFragment, bundle);
                return;
            }


            if (saleNameList.get(holder.getAdapterPosition()).equals(PurchaseUtill.addNewPurchase)) {
                /**
                 * go to add new purchase fragment
                 */
                try {
                    String currentProfileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();

                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(6)) {
                        Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_addNewPurchase2);
                    } else {
                        Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }
                return;
            }

            if (saleNameList.get(holder.getAdapterPosition()).equals(PurchaseUtill.purchaseReturn)) {
                try {

                    Bundle bundle = new Bundle();
                    bundle.putString("portion", PurchaseUtill.purchaseReturn);
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_purchaseReturnFragment, bundle);
                    return;
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }
            }


            if (saleNameList.get(holder.getAdapterPosition()).equals(PurchaseUtill.stockInfo)) {
                HelperClass.navigate(PurchaseUtill.stockInfo, holder.binding.getRoot(), R.id.action_managementFragment_to_storeListFragment);
                return;
            }


        });
    }

    @Override
    public int getItemCount() {
        return saleNameList.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        private ManagementFragmentModelBindingBinding binding;

        public MyHolder(ManagementFragmentModelBindingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
