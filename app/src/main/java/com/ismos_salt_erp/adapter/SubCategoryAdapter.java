package com.ismos_salt_erp.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.ismos_salt_erp.R;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.permission.Permission;
import com.ismos_salt_erp.utils.AnimationTime;
import com.ismos_salt_erp.utils.ManagementUtils;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.items.ItemListFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyHolder> {
    FragmentActivity context;
    List<Integer> imageList;
    List<String> itemNameList;

    Bundle bundle = new Bundle();

    public SubCategoryAdapter(FragmentActivity context, List<Integer> imageList, List<String> itemNameList) {
        this.context = context;
        this.imageList = imageList;
        this.itemNameList = itemNameList;
    }

    @NonNull
    @Override
    public SubCategoryAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.management_fragment_model, parent, false);
        return new SubCategoryAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.MyHolder holder, int position) {
        Integer currentImage = imageList.get(position);
        String currentTitle = itemNameList.get(position);


        holder.itemImage.setImageDrawable(context.getDrawable(currentImage));
        AnimationTime.animation(holder.itemImage);

        holder.itemText.setText(currentTitle);

        holder.itemView.setOnClickListener(v -> {
            String currentProfileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
            Permission permission = new Permission(context);
            if (itemNameList.get(holder.getAdapterPosition()).equals(ManagementUtils.addNewItem)) {
                try {

                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(2)) {
                        bundle.putString("portion", itemNameList.get(position));
                        Navigation.createNavigateOnClickListener(R.id.action_managementFragment_to_addNewItem, bundle).onClick(v);
                        return;
                    } else {
                        Toasty.info(context, "You don't have permission for access this portion", Toasty.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }
            }
            if (itemNameList.get(position).equals(ManagementUtils.itemLists)) {
                if (permission.checkPermission(1051, currentProfileTypeId) == false) {
                    return;
                }

                bundle.putString("porson", itemNameList.get(position));
                Navigation.createNavigateOnClickListener(R.id.action_managementFragment_to_itemListFragment, bundle).onClick(v);

                return;
            }
            if (itemNameList.get(position).equals(ManagementUtils.initiaItemLists)) {
                if (permission.checkPermission(1051, currentProfileTypeId) == false) {
                    return;
                }
                bundle.putString("porson", itemNameList.get(position));
                Navigation.createNavigateOnClickListener(R.id.action_managementFragment_to_itemListFragment, bundle).onClick(v);

                return;
            }

            if (itemNameList.get(position).equals(ManagementUtils.itemCategory)) {
                if (permission.checkPermission(1473, currentProfileTypeId)) {
                    bundle.putString("porson", itemNameList.get(position));
                    Navigation.createNavigateOnClickListener(R.id.action_managementFragment_to_itemListFragment, bundle).onClick(v);
                }
            }
            if (itemNameList.get(position).equals(ManagementUtils.assignItemPacket)) {
                if (permission.checkPermission(1258, currentProfileTypeId)) {
                    bundle.putString("porson", itemNameList.get(position));
                    ItemListFragment.pageNumber = 1;
                    ItemListFragment.isFirstLoad = 0;
                    Navigation.createNavigateOnClickListener(R.id.action_managementFragment_to_itemListFragment, bundle).onClick(v);
                }

            }

            if (itemNameList.get(position).equals(ManagementUtils.brands)) {
                if (permission.checkPermission(1048, currentProfileTypeId)) {
                    bundle.putString("porson", itemNameList.get(position));
                    ItemListFragment.pageNumber = 1;
                    ItemListFragment.isFirstLoad = 0;
                    Navigation.createNavigateOnClickListener(R.id.action_managementFragment_to_itemListFragment, bundle).onClick(v);
                }

            }
            if (itemNameList.get(position).equals(ManagementUtils.trash)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1049)) {
                    bundle.putString("porson", itemNameList.get(position));
                    ItemListFragment.pageNumber = 1;
                    ItemListFragment.isFirstLoad = 0;
                    Navigation.createNavigateOnClickListener(R.id.action_managementFragment_to_itemListFragment, bundle).onClick(v);
                } else {
                    Toasty.info(context, "You don't have permission for access this portion", Toasty.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemNameList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_home_item)
        ImageView itemImage;
        @BindView(R.id.text_home_item)
        TextView itemText;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
