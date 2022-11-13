package com.ismos_salt_erp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.StoreListLayoutBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.permission.DateFormatRight;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.store.StoreListFragment;
import com.ismos_salt_erp.view.fragment.store.list_response.StoreLst;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.viewHolder> {
    private FragmentActivity context;
    List<StoreLst> lists;
    private StoreListFragment click;

    public StoreListAdapter(FragmentActivity context, List<StoreLst> lists, StoreListFragment click) {
        this.context = context;
        this.lists = lists;
        this.click = click;
    }

    @NonNull
    @NotNull
    @Override
    public StoreListAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        StoreListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.store_list_layout, parent, false);
        return new StoreListAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StoreListAdapter.viewHolder holder, int position) {
        StoreLst currentList = lists.get(position);
        try {

            if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(16)) {
                holder.itembinding.edit.setVisibility(View.GONE);
            }
            if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(17)) {
                holder.itembinding.delete.setVisibility(View.GONE);
            }

            holder.itembinding.shortName.setText(":  " + currentList.getStoreName());
            holder.itembinding.enterpriseName.setText(":  " + currentList.getFullName());
            holder.itembinding.address.setText(":  " + currentList.getStoreAddress());

            if (currentList.getCreatedAt() == null) {
                holder.itembinding.date.setText(":  ");
            } else {

                holder.itembinding.date.setText(":  " + new DateFormatRight(context, currentList.getCreatedAt()).onlyDayMonthYear());
            }
        } catch (Exception e) {
            Log.e("ERROR", "" + e.getMessage());
        }
// switch check
        holder.itembinding.statusSwitch.setChecked(false);
        holder.itembinding.statusSwitch.setSelected(false);
        if (currentList.getStatus().equals("1")) {
            holder.itembinding.statusSwitch.setChecked(true);
            holder.itembinding.statusSwitch.setSelected(true);

        }
        holder.itembinding.delete.setOnClickListener(v -> {
            try {
                click.getPosition(holder.getAdapterPosition(),  currentList.getStoreID());


            } catch (Exception e) {
            }
        });

        holder.itembinding.edit.setOnClickListener(v -> click.getDataForEditStore(holder.getAdapterPosition(), currentList.getStoreNo(), currentList.getFullName(), currentList.getStoreName(), currentList.getStoreAddress(), currentList.getStoreID()));
        holder.itembinding.statusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                click.statusManage(holder.getAdapterPosition(), currentList.getStoreID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private StoreListLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull StoreListLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}
