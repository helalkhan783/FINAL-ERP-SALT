package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.SupplierListBindingBinding;
import com.ismos_salt_erp.serverResponseModel.SupplierTrashList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SupplierTrashListAdapter extends RecyclerView.Adapter<SupplierTrashListAdapter.ViewHolder> {
    private FragmentActivity context;
    private List<SupplierTrashList> supplierTrashLists;


    @NonNull
    @NotNull
    @Override
    public SupplierTrashListAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        SupplierListBindingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent
                .getContext()), R.layout.supplier_list_binding, parent, false);
        return new SupplierTrashListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SupplierTrashListAdapter.ViewHolder holder, int position) {
        SupplierTrashList customerList = supplierTrashLists.get(position);
        holder.binding.companyName.setText(":  "+customerList.getCompanyName());
        holder.binding.ownerName.setText(":  "+customerList.getCustomerFname());
        holder.binding.phoneNumber.setText(":  "+customerList.getPhone());
        holder.binding.address.setText(":  "+customerList.getAddress());


        String typeId  =  customerList.getTypeID();
        if (typeId.equals("1") ){
            holder.binding.type.setText("Local");
        }if (typeId.equals("5")){
            holder.binding.type.setText("Foreign");
        }
    }

    @Override
    public int getItemCount() {
        return  supplierTrashLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final SupplierListBindingBinding binding;

        public ViewHolder(SupplierListBindingBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
