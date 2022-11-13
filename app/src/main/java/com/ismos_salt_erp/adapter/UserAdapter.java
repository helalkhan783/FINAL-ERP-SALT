package com.ismos_salt_erp.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.ismos_salt_erp.utils.UserUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyHolder> {
    private FragmentActivity context;
    private List<String> reconciliationItemName;
    private List<Integer> reconciliationItemImage;
    private boolean havePermission;


    @NonNull
    @NotNull
    @Override
    public UserAdapter.MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ManagementFragmentModelBindingBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.management_fragment_model_binding, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UserAdapter.MyHolder holder, int position) {
        Integer currentImage = reconciliationItemImage.get(position);
        String currentName = reconciliationItemName.get(position);
        holder.binding.textHomeItem.setText(currentName);

        holder.binding.imageHomeItem.setImageDrawable(ContextCompat.getDrawable(context, currentImage));
        AnimationTime.animation(holder.binding.imageHomeItem);
        holder.binding.setClickHandle(() -> {
            if (reconciliationItemName.get(holder.getAdapterPosition()).equals(UserUtil.addNewUser)) {
                try {
                    String currentProfileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1124)) {
                        Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_add_new_user);
                        return;
                    } else {
                        Toasty.info(context, "You don't have permission for access this portion", Toasty.LENGTH_LONG).show();
                    }

                } catch (Exception e) {

                }
            }

            if (reconciliationItemName.get(holder.getAdapterPosition()).equals(UserUtil.userList)) {
                try {
                    if ((PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1439)
                    )  ) {
                        Bundle bundle = new Bundle();
                        bundle.putString("portion", UserUtil.userList);
                        Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_userAllListFragment, bundle);
                        return;
                    } else {
                        Toasty.info(context, "You don't have permission for access this portion", Toasty.LENGTH_LONG).show();
                    }
                    return;
                } catch (Exception e) {
                    Toasty.info(context, "" + e.getMessage(), Toasty.LENGTH_LONG).show();
                }
            }

            if ((reconciliationItemName.get(holder.getAdapterPosition()).equals(UserUtil.userTrashList))) {
                try {
                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1440)  ) {
                        Bundle bundle = new Bundle();
                        bundle.putString("portion", UserUtil.userTrashList);
                        Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_userAllListFragment, bundle);
                        return;
                    } else {
                        Toasty.info(context, "You don't have permission for access this portion", Toasty.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                }
            }

            if ((reconciliationItemName.get(holder.getAdapterPosition()).equals(UserUtil.manageAccesability))) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1436)  ) {
                    Bundle bundle = new Bundle();
                    bundle.putString("portion", UserUtil.manageAccesability);
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_userAllListFragment, bundle);
                    return;
                } else {
                    Toasty.info(context, "You don't have permission for access this portion", Toasty.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return reconciliationItemName.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        private ManagementFragmentModelBindingBinding binding;

        public MyHolder(ManagementFragmentModelBindingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}

