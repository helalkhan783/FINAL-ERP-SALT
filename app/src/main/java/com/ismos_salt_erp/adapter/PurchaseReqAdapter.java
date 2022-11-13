package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.ManagementFragmentModelBindingBinding;
import com.ismos_salt_erp.utils.AnimationTime;
import com.ismos_salt_erp.utils.PurchaseRequisitionsUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.NonNull;

public class PurchaseReqAdapter extends RecyclerView.Adapter<PurchaseReqAdapter.MyHolder> {
    private FragmentActivity context;
    private List<String> purchaseReqNameList;
    private List<Integer> purchaseReqImage;

    public PurchaseReqAdapter(FragmentActivity context, List<String> purchaseReqNameList, List<Integer> purchaseReqImage) {
        this.context = context;
        this.purchaseReqNameList = purchaseReqNameList;
        this.purchaseReqImage = purchaseReqImage;
    }

    @NonNull
    @NotNull
    @Override
    public PurchaseReqAdapter.MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ManagementFragmentModelBindingBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.management_fragment_model_binding, parent, false);
        return new PurchaseReqAdapter.MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PurchaseReqAdapter.MyHolder holder, int position) {
        Integer currentImage = purchaseReqImage.get(position);
        String currentName = purchaseReqNameList.get(position);

        holder.binding.textHomeItem.setText(currentName);
        holder.binding.imageHomeItem.setImageDrawable(ContextCompat.getDrawable(context, currentImage));
        AnimationTime.animation(holder.binding.imageHomeItem);

        holder.binding.setClickHandle(() -> {
            if (purchaseReqNameList.get(holder.getAdapterPosition()).equals(PurchaseRequisitionsUtils.newPurchase)) {
                Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_purchaseRequisitonFragment);
                return;
            }
            if (purchaseReqNameList.get(holder.getAdapterPosition()).equals(PurchaseRequisitionsUtils.purchaseReqListTitle)) {
                Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
                return;
            }
            if (purchaseReqNameList.get(holder.getAdapterPosition()).equals(PurchaseRequisitionsUtils.pendingReqListTitle)) {
                Toast.makeText(context, "2", Toast.LENGTH_SHORT).show();
                return;
            }
            if (purchaseReqNameList.get(holder.getAdapterPosition()).equals(PurchaseRequisitionsUtils.declinedReqListTitle)) {
                Toast.makeText(context, "3", Toast.LENGTH_SHORT).show();
                return;
            }

        });
    }

    @Override
    public int getItemCount() {
        return purchaseReqNameList.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        private ManagementFragmentModelBindingBinding binding;

        public MyHolder(ManagementFragmentModelBindingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}

