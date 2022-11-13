package com.ismos_salt_erp.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.AddNewPackatingModelBinding;
import com.ismos_salt_erp.serverResponseModel.PackatingModel;
import com.ismos_salt_erp.serverResponseModel.PacketingList;
import com.ismos_salt_erp.view.fragment.production.packating.AddNewPackating;
import com.ismos_salt_erp.view.fragment.production.packating.AddNewPackatingRecyclerClickHandle;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AddNewPackatingAdapter extends RecyclerView.Adapter<AddNewPackatingAdapter.MyHolder> {

    private FragmentActivity activity;
    private List<PackatingModel> databasepackagingList;
    private List<PacketingList> pageInitialData;
    private List<String> pageInitialDataName;
    private AddNewPackatingRecyclerClickHandle clickHandle;

    public AddNewPackatingAdapter(FragmentActivity activity, List<PackatingModel> databasepackagingList, List<PacketingList> pageInitialData, AddNewPackating addNewPackating) {
        this.activity = activity;
        this.databasepackagingList = databasepackagingList;
        this.pageInitialData = pageInitialData;
        this.pageInitialDataName = new ArrayList<>();
        clickHandle = addNewPackating;
    }

    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        AddNewPackatingModelBinding binding
                = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.add_new_packating_model, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {

        PackatingModel currentDatabaseModel = databasepackagingList.get(holder.getAdapterPosition());

        if (pageInitialData != null) {
            pageInitialDataName.clear();
            for (int i = 0; i < pageInitialData.size(); i++) {
                pageInitialDataName.add(pageInitialData.get(i).getProductTitle());
            }
            holder.binding.itemName.setItem(pageInitialDataName);
        }

        /**
         * now set all previous selected itemName if have
         */
        for (int i = 0; i < pageInitialData.size(); i++) {
            if (pageInitialData.get(i).getProductID().equals(databasepackagingList.get(holder.getAdapterPosition()).getItemId())) {
                holder.binding.itemName.setSelection(i);
            }
        }
        holder.binding.size.setText(AddNewPackating.databasepackagingList.get(holder.getAdapterPosition()).getWeight());
        holder.binding.quantity.setText(AddNewPackating.databasepackagingList.get(holder.getAdapterPosition()).getQuantity());
        holder.binding.available.setText(AddNewPackating.databasepackagingList.get(holder.getAdapterPosition()).getAvailable());

        double total = 0.0;

        total = Double.parseDouble(AddNewPackating.databasepackagingList.get(holder.getAdapterPosition()).getWeight())
                * Double.parseDouble(AddNewPackating.databasepackagingList.get(holder.getAdapterPosition()).getQuantity());

        if (total == 0.0) {
            total = Double.parseDouble(AddNewPackating.databasepackagingList.get(holder.getAdapterPosition()).getWeight());
        }

        holder.binding.total.setText(String.valueOf(total));

        double totalQuantity = 0.0;

        for (int i = 0; i < AddNewPackating.databasepackagingList.size(); i++) {
            totalQuantity += Double.parseDouble(AddNewPackating.databasepackagingList.get(i).getQuantity());
        }

        AddNewPackating.binding.totalQuantity.setText("Total Quantity: " + totalQuantity);

        /**
         * now set item Name click handle
         */
        holder.binding.itemName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (AddNewPackating.selectedStore == null) {
                    AddNewPackating.binding.store.setEnableErrorLabel(true);
                    AddNewPackating.binding.store.setErrorText("Empty");
                } else {
                    AddNewPackating.binding.store.setEnableErrorLabel(false);
                    /**
                     * After Select
                     */
                    clickHandle.selectItemName(holder.getAdapterPosition(),
                            pageInitialData.get(position),
                            databasepackagingList.get(holder.getAdapterPosition())
                    );
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        holder.binding.quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clickHandle.changeQuantity(holder.binding.quantity.getText().toString(),
                        holder.getAdapterPosition(),
                        databasepackagingList.get(holder.getAdapterPosition()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return databasepackagingList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private AddNewPackatingModelBinding binding;

        public MyHolder(AddNewPackatingModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
