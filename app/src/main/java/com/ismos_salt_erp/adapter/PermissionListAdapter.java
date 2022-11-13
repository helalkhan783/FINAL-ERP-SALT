package com.ismos_salt_erp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.PermissionListBinding;
import com.ismos_salt_erp.serverResponseModel.Permisssions;
import com.ismos_salt_erp.view.fragment.user.UserAllListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PermissionListAdapter extends RecyclerView.Adapter<PermissionListAdapter.MyHolder> {
    private FragmentActivity context;
    private List<Permisssions> lists;
    private List<String> userPermissions;
    private UserAllListFragment permissionCheckedHandle;


    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        PermissionListBinding binding
                = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.permission_list, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        Permisssions currentPermission = lists.get(position);

        String currentPermissionName = currentPermission.getPermissionName().substring(0, 1).toUpperCase() + currentPermission.getPermissionName().substring(1);

        holder.binding.checkboxItem.setText("" + currentPermissionName);


        try {
            if (userPermissions.contains(currentPermission.getPermissionID())) {
                holder.binding.checkboxItem.setChecked(true);
            }
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }


        /**
         * Handle checkbox checkbox from here
         */
        holder.binding.checkboxItem.setOnCheckedChangeListener((buttonView, isChecked) -> {
            try {
                String currentPermissionId = lists.get(holder.getAdapterPosition()).getPermissionID();
                if (isChecked) {
                    if (currentPermissionId != null) {
                        permissionCheckedHandle.checkedPermission(Integer.parseInt(currentPermissionId));
                    }
                    return;
                }
                permissionCheckedHandle.unCheckedPermission(Integer.parseInt(currentPermissionId));
            } catch (Exception e) {
                Log.d("ERROR", "" + e.getMessage());
            }
        });


    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private PermissionListBinding binding;

        public MyHolder(PermissionListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
