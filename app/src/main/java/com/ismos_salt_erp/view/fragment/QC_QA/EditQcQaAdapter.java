package com.ismos_salt_erp.view.fragment.QC_QA;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.EditQcqaModelBinding;
import com.ismos_salt_erp.serverResponseModel.GetEditQcQaResponse;
import com.ismos_salt_erp.serverResponseModel.QcDetail;
import com.ismos_salt_erp.serverResponseModel.TestList;

import java.util.List;

public class EditQcQaAdapter extends RecyclerView.Adapter<EditQcQaAdapter.MyClass> {
    private FragmentActivity activity;
    private List<String> testMainNameList;
    private GetEditQcQaResponse getEditQcQaResponse;
    private List<TestList> testNameList;
    private List<QcDetail> testList;
    private EditQcQaSelectionHandle clickHandle;

    public EditQcQaAdapter(FragmentActivity activity, List<String> testMainNameList, GetEditQcQaResponse getEditQcQaResponse, List<TestList> testNameList, List<QcDetail> testList, EditQcQaSelectionHandle clickHandle) {
        this.activity = activity;
        this.testMainNameList = testMainNameList;
        this.getEditQcQaResponse = getEditQcQaResponse;
        this.testNameList = testNameList;
        this.testList = testList;
        this.clickHandle = clickHandle;
    }

    @NonNull
    @Override
    public MyClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EditQcqaModelBinding binding
                = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.edit_qcqa_model, parent, false);
        return new MyClass(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyClass holder, int position) {
        try {
            holder.binding.testName.setItem(testMainNameList);

            boolean isComplete = false;

            for (int i = 0; i < getEditQcQaResponse.getQcDetails().size(); i++) {
                if (isComplete) {
                    break;
                }
                for (int i1 = 0; i1 < getEditQcQaResponse.getTestList().size(); i1++) {
                    if (getEditQcQaResponse.getQcDetails().get(i).getTestID().equals(getEditQcQaResponse.getTestList().get(i1).getTestID())) {
                        holder.binding.testName.setSelection(i1);
                        holder.binding.testName.setEnableErrorLabel(false);

                        holder.binding.shortName.setText("" + getEditQcQaResponse.getTestList().get(i1).getShortName());
                        holder.binding.sampleName.setText("" + getEditQcQaResponse.getTestList().get(i1).getTestName());
                        holder.binding.value.setText("" + getEditQcQaResponse.getQcDetails().get(i).getParameterValue());
                        isComplete = true;
                        break;
                    }
                }


            }


            holder.binding.testName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    holder.binding.testName.setSelected(true);
                    holder.binding.testName.setSelection(position);

                    holder.binding.shortName.setText(getEditQcQaResponse.getTestList().get(position).getShortName());
                    holder.binding.sampleName.setText(testNameList.get(position).getReference());


              try{      for (int i = 0; i < testNameList.size(); i++) {
                  if (testNameList.get(i).getTestID().equals(getEditQcQaResponse.getQcDetails().get(i).getTestID())) {
                      holder.binding.value.setText(getEditQcQaResponse.getQcDetails().get(i).getParameterValue());
                      break;
                  }
              }}catch (Exception e){}
                    clickHandle.select(holder.getAdapterPosition(), getEditQcQaResponse.getTestList().get(position).getTestID());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }


    }

    @Override
    public int getItemCount() {
        return getEditQcQaResponse.getQcDetails().size();
    }


    class MyClass extends RecyclerView.ViewHolder {
        private EditQcqaModelBinding binding;

        public MyClass(EditQcqaModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}
