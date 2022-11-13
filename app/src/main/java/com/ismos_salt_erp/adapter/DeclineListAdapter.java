package com.ismos_salt_erp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.MillerDeclineListLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.DeclineList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeclineListAdapter extends RecyclerView.Adapter<DeclineListAdapter.viewHolder> {
    private Context context;
    List<DeclineList> lists;

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        MillerDeclineListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R
                .layout.miller_decline_list_layout, parent, false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {
        DeclineList currentList = lists.get(position);
       holder.itembinding.entryTime.setText(":  "+currentList.getEntryTime());
       holder.itembinding.name.setText(":  "+currentList.getDisplayName());
       holder.itembinding.displayName.setText(":  "+currentList.getDisplayName());
       holder.itembinding.processTpe.setText(":  "+currentList.getProcessTypeName());
       holder.itembinding.millerType.setText(":  "+currentList.getMillTypeName());
       holder.itembinding.capacity.setText(":  "+currentList.getCapacity());
       holder.itembinding.countyName.setText(":  "+currentList.getCountryName());
       holder.itembinding.division.setText(":  "+currentList.getDivisionName());
       holder.itembinding.district.setText(":  "+currentList.getDistrictName());
       holder.itembinding.upazilla.setText(":  "+currentList.getUpazilaName());


    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private MillerDeclineListLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull MillerDeclineListLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}
