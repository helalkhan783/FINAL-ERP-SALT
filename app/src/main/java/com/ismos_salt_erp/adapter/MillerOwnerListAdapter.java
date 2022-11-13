package com.ismos_salt_erp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.MillerOwnerModelBinding;
import com.ismos_salt_erp.serverResponseModel.PreviousMillerOwnerInfo;
import com.ismos_salt_erp.view.fragment.miller.editmiller.MillerOwnerListClickHandle;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MillerOwnerListAdapter extends RecyclerView.Adapter<MillerOwnerListAdapter.MyClass> {
    private FragmentActivity activity;
    private List<PreviousMillerOwnerInfo> ownerInfoList;
    private MillerOwnerListClickHandle clickHandle;

    @NonNull
    @Override
    public MyClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MillerOwnerModelBinding binding
                = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.miller_owner_model, parent, false);
        return new MyClass(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyClass holder, int position) {
        try {
           /* if (portion.equals("LICENSE_INFO_EDIT")) {
                holder.binding.ownerName.setText("" + certificateInfoList.get(position).getIssuerName());
                return;
            }*/
            /**
             * Otherwise this is OWNER_INFO_EDIT
             */
            holder.binding.ownerName.setText("" + ownerInfoList.get(position).getOwnerName());
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }
        /**
         * Now Work With Edit
         */
        holder.binding.edit.setOnClickListener(v -> {
            try {
                /*if (portion.equals("LICENSE_INFO_EDIT")) {
                    clickHandle.click(certificateInfoList.get(position).getSlID());
                    return;
                }*/
                /**
                 * Otherwise this is OWNER_INFO_EDIT
                 */
                clickHandle.click(ownerInfoList.get(position).getSlID(),null);
            } catch (Exception e) {
                Log.d("ERROR", "" + e.getMessage());
            }
        });


    }

    @Override
    public int getItemCount() {
       /* int size;
        if (portion.equals("LICENSE_INFO_EDIT")) {
            size = certificateInfoList.size();
        } else {
            size = ownerInfoList.size();
        }*/
        return ownerInfoList.size();
    }

    class MyClass extends RecyclerView.ViewHolder {

        private MillerOwnerModelBinding binding;

        public MyClass(MillerOwnerModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
