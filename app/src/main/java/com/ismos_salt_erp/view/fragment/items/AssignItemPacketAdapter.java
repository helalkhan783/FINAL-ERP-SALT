package com.ismos_salt_erp.view.fragment.items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.AssignItemPacketLayoutBinding;
import com.ismos_salt_erp.databinding.TrashItemListLayoutBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.ProductList;
import com.ismos_salt_erp.utils.PermissionUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class AssignItemPacketAdapter extends RecyclerView.Adapter<AssignItemPacketAdapter.viewHolder> {
    private FragmentActivity context;
    List<ProductList> lists;
    ItemListFragment forClick;

    public AssignItemPacketAdapter(FragmentActivity context, List<ProductList> lists, ItemListFragment forClick) {
        this.context = context;
        this.lists = lists;
        this.forClick = forClick;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        AssignItemPacketLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.assign_item_packet_layout, parent, false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {
        ProductList currentList = lists.get(position);
        holder.itembinding.categoryName.setText(":  " + currentList.getCategory());
        holder.itembinding.itemName.setText(":  " + currentList.getProductTitle());
        holder.itembinding.itemCode.setText(":  " + currentList.getPcode());
        if (currentList.getBrandName() !=null){
            holder.itembinding.brandName.setText(":  " + currentList.getBrandName());

        }
        holder.itembinding.weight.setText(":  " + currentList.getProductDimensions() + " " + currentList.getName());
        holder.itembinding.tagItem.setText(":  " + currentList.getPacketName());
        if (!currentList.getPacketId().isEmpty()) {
            holder.itembinding.delete.setVisibility(View.VISIBLE);
        }

        holder.itembinding.addTag.setOnClickListener(v -> {
            if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1552)) {
                Toasty.info(context, "You don't have permission access this potion", Toasty.LENGTH_LONG).show();
                return;
            }
            forClick.addTag(holder.getAdapterPosition(), currentList.getPacketName(), currentList.getProductID(), currentList.getPacketId(), currentList.getProductTitle());
        });

        holder.itembinding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forClick.deleteTag(holder.getAdapterPosition(), currentList.getProductID(), currentList.getPacketId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private AssignItemPacketLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull AssignItemPacketLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }

}
