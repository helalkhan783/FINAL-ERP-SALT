package com.ismos_salt_erp.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.QcqaDeclinedModelBinding;
import com.ismos_salt_erp.serverResponseModel.DeclineQcQaList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeclinedQcQaAdapter extends RecyclerView.Adapter<DeclinedQcQaAdapter.MyHolder> {
    private FragmentActivity context;
    private List<DeclineQcQaList> lists;

    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        QcqaDeclinedModelBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.qcqa_declined_model, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        try {
            DeclineQcQaList currentQcQa = lists.get(position);
            holder.binding.sampleName.setText(":  " + currentQcQa.getModel());
            holder.binding.date.setText(":  " + currentQcQa.getTestDate());
            holder.binding.enterpriseName.setText(":  " + currentQcQa.getStoreName());
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }
        /**
         * For show details
         */
        holder.binding.viewBtn.setOnClickListener(v -> {
            try {
                Bundle bundle = new Bundle();
                bundle.putString("id", lists.get(holder.getAdapterPosition()).getQcID());
                bundle.putString("SL_ID", lists.get(holder.getAdapterPosition()).getSlID());
                bundle.putString("pageName", "Qc-Qa Details");
                Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_qcQaPendingFragment_to_qcqaDetailsFragment, bundle);
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
        QcqaDeclinedModelBinding binding;

        public MyHolder(QcqaDeclinedModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
