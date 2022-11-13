package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.UserTrashlistModelBinding;
import com.ismos_salt_erp.serverResponseModel.UserTrashList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserTrashListAdapter extends RecyclerView.Adapter<UserTrashListAdapter.MyHolder> {
    private FragmentActivity activity;
    private List<UserTrashList> lists;


    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        UserTrashlistModelBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.user_trashlist_model, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        UserTrashList currentUserTrashList = lists.get(position);
        holder.binding.name.setText(":  "+currentUserTrashList.getFullName());
        holder.binding.userName.setText(":  "+currentUserTrashList.getUserName());
        /**
         * now handle backup
         */
        holder.binding.backUp.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private UserTrashlistModelBinding binding;

        public MyHolder(UserTrashlistModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
