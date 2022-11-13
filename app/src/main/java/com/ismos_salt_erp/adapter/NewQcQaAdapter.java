package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.QcqaRecyclerModelBinding;
import com.ismos_salt_erp.serverResponseModel.AddQcQaPageResponse;
import com.ismos_salt_erp.serverResponseModel.TestList;
import com.ismos_salt_erp.view.fragment.QC_QA.AddQc_Qa;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NewQcQaAdapter extends RecyclerView.Adapter<NewQcQaAdapter.MyHolder> {
    private FragmentActivity context;
    public static List<TestList> testLists;
    private AddQc_Qa click;
    List<String> siz;
    private List<String> testNameList = new ArrayList<>();
    AddQcQaPageResponse response;



    public NewQcQaAdapter(FragmentActivity context, List<TestList> testLists, AddQcQaPageResponse response, List<String> sizeList, AddQc_Qa click) {
        this.context = context;
        this.testLists = testLists;
        this.response = response;
        this.siz = sizeList;
        this.click = click;
    }

    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
          QcqaRecyclerModelBinding binding
                = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.qcqa_recycler_model, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        // now swt Data to test
      /*  for (int i = 0; i < AddQc_Qa.testLists().size(); i++) {
            testNameList.add(AddQc_Qa.testLists().get(i).getTestName());
        }
*/
        holder.binding.shortName.setText("");


        if (AddQc_Qa.manageAdapter){
            testNameList.clear();
        }
        for (int i = 0; i < AddQc_Qa.testLists.size(); i++) {
            testNameList.add(AddQc_Qa.testLists.get(i).getTestName());
        }
        holder.binding.testName.setItem(testNameList);



        try {
            holder.binding.testName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        for (int i = 0; i < siz.size(); i++) {
                            holder.binding.shortName.setText(""+AddQc_Qa.testLists.get(position).getShortName());
                            holder.binding.reference.setText(""+AddQc_Qa.testLists.get(position).getReference());
                            click.getAllData(holder.getAdapterPosition(), AddQc_Qa.testLists.get(position).getTestID(), holder.binding.shortName.getText().toString(), holder.binding.reference.getText().toString(), holder.binding.value.getText().toString());
                        }
                    }catch (Exception e){}
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
        }
        holder.binding.delete.setOnClickListener(v -> {
            if (siz.size() <= 1) {
                return;
            }
            click.deleteItem(holder.getAdapterPosition());
        });
    }


    @Override
    public int getItemCount() {
        return siz.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private QcqaRecyclerModelBinding binding;

        public MyHolder(QcqaRecyclerModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            if (getLayoutPosition() == 0){

            }
        }


    }
}

