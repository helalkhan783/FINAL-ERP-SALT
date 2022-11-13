package com.ismos_salt_erp.adapter.dashboard_adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.ToptenCustomerLayBinding;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.TopTenSupplierAndCustomerList;
import com.ismos_salt_erp.utils.replace.DataModify;

import java.util.List;

public class TopTenCustomerAdapter extends RecyclerView.Adapter<TopTenCustomerAdapter.ViewHolder> {
    FragmentActivity context;
    List<TopTenSupplierAndCustomerList> lists;

    public TopTenCustomerAdapter(FragmentActivity context, List<TopTenSupplierAndCustomerList> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ToptenCustomerLayBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R
                .layout.topten_customer_lay, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TopTenSupplierAndCustomerList customerList =  lists.get(position);
        int po=position+1;
        holder.binding.sl.setText(""+po);
     try {
         holder.binding.name.setText(""+customerList.getCompanyName() + "@"+customerList.getCustomerFname());
         holder.binding.qty.setText(""+ DataModify.kgToTon(customerList.getTotalQuantity()));
         holder.binding.amount.setText(""+ DataModify.addFourDigit(customerList.getGrandTotal()));


     }catch (Exception e){}
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ToptenCustomerLayBinding binding;
        public ViewHolder(@NonNull ToptenCustomerLayBinding binding) {
            super(binding.getRoot());
            this.binding =binding;
        }
    }
}
