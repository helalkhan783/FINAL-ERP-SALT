package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.UserPermissionModelBinding;
import com.ismos_salt_erp.serverResponseModel.PermisssionList;
import com.ismos_salt_erp.view.fragment.user.UserAllListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserPermissionAdapter extends RecyclerView.Adapter<UserPermissionAdapter.MyHolder> {
    private FragmentActivity context;
    private List<PermisssionList> permisssionLists;
    private UserAllListFragment permissionCheckedHandle;
    private  List<String> userPermissions;



    public UserPermissionAdapter(FragmentActivity context, List<PermisssionList> permisssionLists, List<String> userPermissions, UserAllListFragment userAllListFragment) {
        this.context = context;
        this.permisssionLists = permisssionLists;
        this.permissionCheckedHandle = userAllListFragment;
        this.userPermissions = userPermissions;
    }


    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        UserPermissionModelBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.user_permission_model, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        PermisssionList currentPermission = permisssionLists.get(position);

        String currentHead = currentPermission.getGroupName();
        String head = currentHead.substring(0, 1).toUpperCase() + currentHead.substring(1);

        holder.binding.head.setText("" + head);
        /**
         * set child based on head
         */
        PermissionListAdapter adapter = new PermissionListAdapter(context, currentPermission.getLists(),userPermissions, permissionCheckedHandle);
        holder.binding.itemRv.setLayoutManager(new LinearLayoutManager(context));
        holder.binding.itemRv.setAdapter(adapter);


      /*  for (int i = 0; i < currentPermission.getLists().size(); i++) {
          try {
              if (userPermissions.contains(currentPermission.getLists().get(i).getPermissionID())){
                  ((CheckBox) holder.binding.itemRv.getLayoutManager().findViewByPosition(i).findViewById(R.id.checkboxItem)).setChecked(true);
              }
          }catch (Exception e){
              Log.d("ERROR",""+e.getMessage());
          }
        }*/






//        try {
//            List<String> currentPermissionList = userPermissions;
//            for (int i = 0; i < userPermissions.size(); i++) {
//                           /* if (currentPermissionList.contains(response.getPermisssionLists().get(i).getLists().get(0).getPermissionID())) {
//                                ((CheckBox) binding.itemListRv.getLayoutManager().findViewByPosition(i).findViewById(R.id.item)).setChecked(true);
//                            }*/
//                for (int i1 = 0; i1 < currentPermission.getLists().size(); i1++) {
//                    try {
//                        if (currentPermissionList.contains(response.getPermisssionLists().get(i).getLists().get(i1).getPermissionID())){
//                            ((CheckBox) binding.itemListRv.getLayoutManager().findViewByPosition(i).findViewById(R.id.checkboxItem)).setChecked(true);
//                        }
//                    }catch (Exception e){
//                        Log.d("ERROR", e.getLocalizedMessage());
//                    }
//                }
//
//
//
//            }
//        } catch (Exception e) {
//            Log.d("ERROR", e.getLocalizedMessage());
//        }







        holder.binding.head.setOnClickListener(v -> {
            if (holder.binding.expandableLayout.isExpanded()) {
                holder.binding.expandableLayout.collapse();
                return;
            }
            holder.binding.expandableLayout.setExpanded(true);
        });

    }

    @Override
    public int getItemCount() {
        return permisssionLists.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private UserPermissionModelBinding binding;

        public MyHolder(UserPermissionModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
