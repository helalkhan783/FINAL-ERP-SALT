package com.ismos_salt_erp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.CertificateInfoInfoLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.view_details.MillerCertificateDatum;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CertificateInfoAdapter extends RecyclerView.Adapter<CertificateInfoAdapter.viewHolder> {
    private Context context;
    private List<MillerCertificateDatum> lists;

    @NonNull
    @NotNull
    @Override
    public CertificateInfoAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        CertificateInfoInfoLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R
                .layout.certificate_info_info_layout, parent, false);
        return new CertificateInfoAdapter.viewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull CertificateInfoAdapter.viewHolder holder, int position) {
        MillerCertificateDatum currentList =lists.get(position);
        if (currentList.getCertificateTypeID() == null){
            holder.itembinding.typeOfCertificate.setText(":");
        }else {
            holder.itembinding.typeOfCertificate.setText(":  "+currentList.getCertificateTypeID());

        }
        if (currentList.getIssuerName() == null){
            holder.itembinding.issuerName.setText(":");
        }else {
            holder.itembinding.issuerName.setText(":  "+currentList.getIssuerName().replaceAll("^\"|\"$", ""));

        }
        if (currentList.getIssueDate() == null){
            holder.itembinding.issuingDate.setText(":");
        }else {
            holder.itembinding.issuingDate.setText(":  "+currentList.getIssueDate());

        }
        if (currentList.getCertificateDate() == null){
            holder.itembinding.certificateDate.setText(":");
        }else {
            holder.itembinding.certificateDate.setText(":  "+currentList.getCertificateDate());

        }
        if (currentList.getRenewDate() == null){
            holder.itembinding.renewalDate.setText(":");
        }
        else {
            holder.itembinding.renewalDate.setText(":  "+currentList.getRenewDate());

        }
        if (currentList.getRemarks()==null){
            holder.itembinding.remarks.setText(":");
        }else {
            holder.itembinding.remarks.setText(":  "+currentList.getRemarks().replaceAll("^\"|\"$", ""));

        }


        try{
            Glide.with(context).load("https://cmis.usi.net.bd/"+currentList.getCertificateImage())
                    .placeholder(R.drawable.owner_image)
                    .error(R.drawable.owner_image)
                    .into(holder.itembinding.imageView);

        }catch (NullPointerException e){
            Log.d("ERROR",e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private CertificateInfoInfoLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull CertificateInfoInfoLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }}
