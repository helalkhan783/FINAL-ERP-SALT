package com.ismos_salt_erp.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.CheckLicenceModelLayoutBinding;
import com.ismos_salt_erp.permission.DateFormatRight;
import com.ismos_salt_erp.serverResponseModel.LicenceExpire;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class LicenceCheckAdapter extends RecyclerView.Adapter<LicenceCheckAdapter.ViewHolder> {
    FragmentActivity context;
    List<LicenceExpire> licenceExpires;
    String enterPriseName;

    public LicenceCheckAdapter(FragmentActivity context, List<LicenceExpire> licenceExpires) {
        this.context = context;
        this.licenceExpires = licenceExpires;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        CheckLicenceModelLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.check_licence_model_layout, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        LicenceExpire currentProduct = licenceExpires.get(position);
        try {
            if (currentProduct.getStoreName() != null) {
                holder.binding.upEnterPriseName.setVisibility(View.VISIBLE);
                holder.binding.upEnterPriseName.setText("Enterprise : " + currentProduct.getStoreName());
                holder.binding.ok.setVisibility(View.VISIBLE);
                holder.binding.mainlayout.setVisibility(View.GONE);

            }

             holder.binding.name.setText("" + currentProduct.getCertificateName());
            if (!(currentProduct.getRenewDate() == null ||  currentProduct.getRenewDate().isEmpty())) {
                holder.binding.date.setText("" + new DateFormatRight(context, currentProduct.getRenewDate()).onlyDayMonthYear());
            }
             holder.binding.sl.setText("" + currentProduct.getSl());
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return licenceExpires.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final CheckLicenceModelLayoutBinding binding;

        public ViewHolder(final CheckLicenceModelLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
