package com.ismos_salt_erp.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.UserAccessabilityModelBinding;
import com.ismos_salt_erp.serverResponseModel.ManageAccessibility;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserAccessibilityAdapter extends RecyclerView.Adapter<UserAccessibilityAdapter.MyHolder> {
    private FragmentActivity activity;
    private List<ManageAccessibility> lists;


    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        UserAccessabilityModelBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.user_accessability_model, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        ManageAccessibility currentData = lists.get(position);
        holder.binding.name.setText(currentData.getFullName());
        holder.binding.userName.setText(currentData.getUserName());


        holder.binding.setClickHandle(() -> {
            Bundle bundle = new Bundle();
            bundle.putString("portion", "Manage User Permissions");
            bundle.putString("id", lists.get(holder.getAdapterPosition()).getUserId());
            bundle.putString("profileId", lists.get(holder.getAdapterPosition()).getProfileId());
            Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_userAllListFragment_self, bundle);
        });


    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private UserAccessabilityModelBinding binding;

        public MyHolder(UserAccessabilityModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
