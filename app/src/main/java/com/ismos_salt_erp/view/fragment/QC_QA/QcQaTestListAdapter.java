package com.ismos_salt_erp.view.fragment.QC_QA;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.QcqaDetailsTestModelBinding;
import com.ismos_salt_erp.serverResponseModel.Qc_QaDetails;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QcQaTestListAdapter extends RecyclerView.Adapter<QcQaTestListAdapter.MyHolder> {
    private FragmentActivity context;
    private List<Qc_QaDetails> testList;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QcqaDetailsTestModelBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.qcqa_details_test_model, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        try {
            Qc_QaDetails currentItem = testList.get(position);
            holder.binding.testName.setText("" + currentItem.getTestName());
            holder.binding.shortName.setText("" + currentItem.getShortName());
            holder.binding.reference.setText("" + currentItem.getReference());
            holder.binding.paramValue.setText("" + currentItem.getParameterValue());
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }


    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private QcqaDetailsTestModelBinding binding;

        public MyHolder(QcqaDetailsTestModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
