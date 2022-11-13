package com.ismos_salt_erp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.clickHandle.MonitoringListModelClickHandle;
import com.ismos_salt_erp.databinding.MonitoringListModelBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.ListMonitorModel;
import com.ismos_salt_erp.serverResponseModel.MonitoringModel;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.monitoring.MonitoringListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MonitoringListAdapter extends RecyclerView.Adapter<MonitoringListAdapter.ViewHolder> {
    private Context context;
    private List<ListMonitorModel> monitoringLists;
    private MonitoringModel monitoringModelObject;


    @NonNull
    @NotNull
    @Override
    public MonitoringListAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        MonitoringListModelBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.monitoring_list_model, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MonitoringListAdapter.ViewHolder holder, int position) {
        ListMonitorModel currentmonitorList = monitoringLists.get(position);


        if (currentmonitorList.getMonitoringDate() == null) {
            holder.binding.monitoringDate.setText(":");

        } else {
            holder.binding.monitoringDate.setText(":  " + currentmonitorList.getMonitoringDate());

        }


        if (currentmonitorList.getZoneName() == null) {
            holder.binding.zoneName.setText(":");

        } else {
            holder.binding.zoneName.setText(":  " + currentmonitorList.getZoneName());
        }


        try {
            if (monitoringModelObject.getLists().get(position).getMonitoringType().equals("1")) {
                holder.binding.monitoringType.setText(":  " + monitoringModelObject.getMonitoringType().get(0));

            }
            if (monitoringModelObject.getLists().get(position).getMonitoringType().equals("2")) {
                holder.binding.monitoringType.setText(":  " + monitoringModelObject.getMonitoringType().get(1));

            }
            if (monitoringModelObject.getLists().get(position).getMonitoringType().equals("3")) {
                holder.binding.monitoringType.setText(":  " + monitoringModelObject.getMonitoringType().get(2));
            }
            if (monitoringModelObject.getLists().get(position).getMonitoringType().equals("4")) {
                holder.binding.monitoringType.setText(":  " + monitoringModelObject.getMonitoringType().get(3));
            }
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }


        if (currentmonitorList.getPublishDate() == null) {
            holder.binding.publishedDate.setText(":");

        } else {
            holder.binding.publishedDate.setText(":  " + currentmonitorList.getPublishDate());

        }
        holder.binding.setClickHandle(new MonitoringListModelClickHandle() {
            @Override
            public void download() {
                Toasty.info(context, "Will Implement it", Toasty.LENGTH_LONG).show();
            }

            @Override
            public void edit() {
                try {
                    String currentProfileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
                    if (currentProfileTypeId.equals("4") || currentProfileTypeId.equals("5") || currentProfileTypeId.equals("6") || currentProfileTypeId.equals("7")) {
                        if (PermissionUtil
                                .currentUserPermissionList(PreferenceManager.getInstance(context)
                                        .getUserCredentials().getPermissions()).contains(1) ||
                                PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1450)) {

                            String selectedId = null;
                            try {
                                selectedId = monitoringLists.get(holder.getAdapterPosition()).getSlID();
                            } catch (Exception e) {
                                Log.d("ERROR", "ERROR");
                            }
                            if (monitoringLists.get(holder.getAdapterPosition()).getMonitorId() != null) {
                                Bundle bundle = new Bundle();
                                bundle.putString("id", selectedId);
                                 MonitoringListFragment.pageNumber = 1;
                                MonitoringListFragment.isFirstLoad = 0;
                                Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_monitoringListFragment_to_editMonitoringFragment, bundle);
                            }
                            return;


                        } else {
                            Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                        }
                    } else {
                        Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }
            }

            @Override
            public void view() {
                try {
                    if (PermissionUtil
                            .currentUserPermissionList(PreferenceManager.getInstance(context)
                                    .getUserCredentials().getPermissions()).contains(1) ||
                            PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1451)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("slId", currentmonitorList.getSlID());
                        MonitoringListFragment.pageNumber = 1;
                        Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_monitoringListFragment_to_monitoringViewFragment, bundle);
                    } else {
                        Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return monitoringLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final MonitoringListModelBinding binding;

        public ViewHolder(final MonitoringListModelBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
