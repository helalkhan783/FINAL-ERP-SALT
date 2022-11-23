package com.ismos_salt_erp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.clickHandle.MillerHistoryListClickHandle;
import com.ismos_salt_erp.databinding.MillerHistoryListLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.HistoryList;
import com.ismos_salt_erp.utils.MillerUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MillerHistoryListAdapter extends RecyclerView.Adapter<MillerHistoryListAdapter.viewHolder> {
    private Context context;
    List<HistoryList> lists;

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        MillerHistoryListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R
                .layout.miller_history_list_layout, parent, false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {
        HistoryList currentList = lists.get(position);
        if (currentList.getEntryTime() == null) {
            holder.itembinding.entryTime.setText(":  ");
        } else {
            holder.itembinding.entryTime.setText(":  " + currentList.getEntryTime());
        }

        if (currentList.getDisplayName() == null) {
            holder.itembinding.displayName.setText(":  ");
        } else {
            holder.itembinding.displayName.setText(":  " + currentList.getDisplayName());
        }

        if (currentList.getProcessTypeName() == null) {
            holder.itembinding.processType.setText(":  ");
        } else {
            holder.itembinding.processType.setText(":  " + currentList.getProcessTypeName());

        }

        if (currentList.getMillTypeName() == null) {
            holder.itembinding.millType.setText(":  ");
        } else {
            holder.itembinding.millType.setText(":  " + currentList.getMillTypeName());

        }
        if (currentList.getCapacity() == null) {
            holder.itembinding.capacity.setText(":  ");
        } else {
            holder.itembinding.capacity.setText(":  " + currentList.getCapacity());

        }
        if (currentList.getCountryName() == null
        ) {
            holder.itembinding.countryName.setText(":  ");
        } else {
            holder.itembinding.countryName.setText(":  " + currentList.getCountryName());

        }
        if (currentList.getDivisionName() == null) {
            holder.itembinding.divisionName.setText(":  ");
        } else {
            holder.itembinding.divisionName.setText(":  " + currentList.getDivisionName());

        }
        if (currentList.getDistrictName() == null) {
            holder.itembinding.districtName.setText(":  ");
        } else {
            holder.itembinding.districtName.setText(":  " + currentList.getDistrictName());

        }
        if (currentList.getUpazilaName() == null) {
            holder.itembinding.upazillaName.setText(":  ");
        } else {

            holder.itembinding.upazillaName.setText(":  " + currentList.getUpazilaName());
        }


        /**
         * Now Handle onclick on edit miller
         */
        holder.itembinding.setClickHandle(new MillerHistoryListClickHandle() {
            @Override
            public void editMiller() {
                Bundle bundle = new Bundle();
                bundle.putString("slID", lists.get(holder.getAdapterPosition()).getSl());
                bundle.putString("portion", MillerUtils.millerHistoryList);
             }
        });
        holder.itembinding.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putString("slId", lists.get(holder.getAdapterPosition()).getSl());
                    Navigation.findNavController(holder.itembinding.getRoot()).navigate(R.id.action_millerAllListFragment_to_millerDetailsViewFragment, bundle);
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private MillerHistoryListLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull MillerHistoryListLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}
