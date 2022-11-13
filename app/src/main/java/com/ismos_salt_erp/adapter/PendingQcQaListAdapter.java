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
import com.ismos_salt_erp.databinding.PendingQcqalistModelBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.PendingQcQaList;
import com.ismos_salt_erp.utils.PermissionUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PendingQcQaListAdapter extends RecyclerView.Adapter<PendingQcQaListAdapter.MyHolder> {
    private FragmentActivity activity;
    private List<PendingQcQaList> lists;


    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        PendingQcqalistModelBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.pending_qcqalist_model, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(activity).getUserCredentials().getPermissions()).contains(1833)) {
            holder.binding.view.setVisibility(View.GONE);
        }
        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(activity).getUserCredentials().getPermissions()).contains(1832)) {
            holder.binding.edit.setVisibility(View.GONE);
        }

        PendingQcQaList currentQcQa = lists.get(position);
        if (currentQcQa.getModel() == null) {
            holder.binding.sampleName.setText(":  ");
        } else {
            holder.binding.sampleName.setText(":  " + currentQcQa.getModel());

        }
        if (currentQcQa.getTestDate() == null) {
            holder.binding.date.setText(":  ");
        } else {
            holder.binding.date.setText(":  " + currentQcQa.getTestDate());

        }

        if (currentQcQa.getStoreName() == null) {
            holder.binding.enterpriseName.setText(":  ");
        } else {
            holder.binding.enterpriseName.setText(":  " + currentQcQa.getStoreName());

        }

        holder.binding.view.setOnClickListener(v -> {
            try {
                Bundle bundle = new Bundle();
                bundle.putString("id", lists.get(holder.getAdapterPosition()).getQcID());
                bundle.putString("SL_ID", lists.get(holder.getAdapterPosition()).getSlID());
                bundle.putString("status", "2");
                bundle.putString("pageName", "Pending Qc-Qa Details");
                bundle.putString("type", "2");
                Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_qcQaPendingFragment_to_qcqaDetailsFragment, bundle);
            } catch (Exception e) {
                Log.d("ERROR", "" + e.getMessage());
            }
        });


        holder.binding.setClickHandle(() -> {
            try {

                Log.d("QC_ID", "" + lists.get(holder.getAdapterPosition()).getQcID());
                Bundle bundle = new Bundle();
                bundle.putString("id", lists.get(holder.getAdapterPosition()).getQcID());
                bundle.putString("SL_ID", lists.get(holder.getAdapterPosition()).getSlID());
                bundle.putString("status", "2");
                bundle.putString("type", "2");
                Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_qcQaPendingFragment_to_edit_QCQAFragment, bundle);
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
        private PendingQcqalistModelBinding binding;

        public MyHolder(PendingQcqalistModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
