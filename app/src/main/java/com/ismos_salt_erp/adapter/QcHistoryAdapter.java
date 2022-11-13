package com.ismos_salt_erp.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.QcHistoryListModelBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.QcHistoryList;
import com.ismos_salt_erp.utils.PermissionUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QcHistoryAdapter extends RecyclerView.Adapter<QcHistoryAdapter.ViewHolder> {
    FragmentActivity activity;
    List<QcHistoryList> lists;

    @NonNull
    @NotNull
    @Override
    public QcHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        QcHistoryListModelBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.qc_history_list_model, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull QcHistoryAdapter.ViewHolder holder, int position) {
        try {
            if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(activity).getUserCredentials().getPermissions()).contains(1338)) {
                holder.binding.edit.setVisibility(View.GONE);
            } if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(activity).getUserCredentials().getPermissions()).contains(1339)) {
                holder.binding.view.setVisibility(View.GONE);
            }
            QcHistoryList currentList = lists.get(position);
            holder.binding.date.setText(":  " + currentList.getEntryDateTime());
            holder.binding.sampleName.setText(String.valueOf(":  " + currentList.getModel()));
            holder.binding.enterpriseName.setText(":  " + currentList.getStoreName());
        } catch (Exception e) {}

        holder.binding.edit.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", lists.get(holder.getAdapterPosition()).getQcID());
            bundle.putString("SL_ID", lists.get(holder.getAdapterPosition()).getSlID());
            bundle.putString("type", "1");
            Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_qcQaPendingFragment_to_edit_QCQAFragment, bundle);

        });
        holder.binding.view.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", lists.get(holder.getAdapterPosition()).getQcID());
            bundle.putString("SL_ID", lists.get(holder.getAdapterPosition()).getSlID());
            bundle.putString("pageName", "Qc-Qa Details");
            bundle.putString("type", "1");
            Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_qcQaPendingFragment_to_qcqaDetailsFragment, bundle);

        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        QcHistoryListModelBinding binding;

        public ViewHolder(@NonNull @NotNull QcHistoryListModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
