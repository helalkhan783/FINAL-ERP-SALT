package com.ismos_salt_erp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.InboxLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.InboxListResponse;
import com.ismos_salt_erp.view.fragment.inbox.ClickInboxList;
import com.ismos_salt_erp.notification.MyNotificationManager;

import java.util.List;

import lombok.AllArgsConstructor;
@AllArgsConstructor
 public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.MyHolder> {
    private FragmentActivity activity;
    private List<InboxListResponse> lists;
    private ClickInboxList clickInboxList;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        InboxLayoutBinding binding
                = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.inbox_layout, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        InboxListResponse currentItem = lists.get(position);
        try {
            holder.binding.title.setText("" + MyNotificationManager.HTML_TO_PLAIN_TEXT(currentItem.getTitle()));
            holder.binding.message.setText("" + currentItem.getMessage().replaceAll("\\<.*?\\>", ""));
            holder.binding.date.setText("" + currentItem.getEntryDate());
            if (currentItem.getIsSeen().equals("1")) {
                holder.binding.activeStatus.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }
        /**
         * Click Handle
         */
        holder.binding.getRoot().setOnClickListener(v -> clickInboxList.click(lists.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private InboxLayoutBinding binding;

        public MyHolder(InboxLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
