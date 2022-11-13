package com.ismos_salt_erp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.TrashItemListLayoutBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.TrashList;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.items.ItemManageBYInterface;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TrashListAadpter extends RecyclerView.Adapter<TrashListAadpter.viewHolder> {
    private Context context;
    List<TrashList> lists;
    ItemManageBYInterface itemManageBYInterface;


    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        TrashItemListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.trash_item_list_layout, parent, false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position) {

        holder.itembinding.trashbrandLay.setVisibility(View.GONE);
        TrashList currentList = lists.get(position);
        holder.itembinding.categoryTrashName.setText(":  " + currentList.getCategory());
        holder.itembinding.itemTrashName.setText(":  " + currentList.getProductTitle());
        holder.itembinding.trashBrandName.setText(":  " + currentList.getBrandName());
        holder.itembinding.trashUnit.setText(":  " + currentList.getName());


        holder.itembinding.delete.setOnClickListener(v -> {
            try {
                /**
                 * check permission first
                 */
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1452)) {
                    /**
                     * now call api for delete this item
                     */
                    itemManageBYInterface.delete(
                            lists.get(holder.getAdapterPosition()).getVendorID(),
                            lists.get(holder.getAdapterPosition()).getStoreID(),
                            lists.get(holder.getAdapterPosition()).getProductID()
                    );
                } else {
                    Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                Log.d("ERROR", "" + e.getMessage());
            }
        });


    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TrashItemListLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull TrashItemListLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }

}
