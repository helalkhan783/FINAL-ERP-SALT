package com.ismos_salt_erp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.clickHandle.ItemListClickHandle;
import com.ismos_salt_erp.databinding.ItemListLayoutBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.Product;
import com.ismos_salt_erp.utils.ImageBaseUrl;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.items.ItemListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {
    private Context context;
    private List<Product> products;
    private boolean click = false;

    public ItemListAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_list_layout, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Product currentProduct = products.get(position);


        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(3)) {
            holder.binding.edit.setVisibility(View.GONE);
        }

        if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1499)) {
            holder.binding.addQuantity.setVisibility(View.GONE);
        }


        if (currentProduct.getCategoryID() != null) {
            if (currentProduct.getCategoryID().equals("739") || currentProduct.getCategoryID().equals("740") || currentProduct.getCategoryID().equals("741")) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1551)) {
                    holder.binding.addPacket.setVisibility(View.VISIBLE);
                    holder.binding.varient.setVisibility(View.VISIBLE);
                    holder.binding.varientLayout.setVisibility(View.VISIBLE);
                    holder.binding.varientTv.setText(":  " + currentProduct.getTotalVarient());
                }
            }

            if (!(currentProduct.getCategoryID().equals("739") || currentProduct.getCategoryID().equals("740") || currentProduct.getCategoryID().equals("741"))) {
                holder.binding.addPacket.setVisibility(View.GONE);
                holder.binding.varient.setVisibility(View.GONE);
                holder.binding.varientLayout.setVisibility(View.GONE);

            }
        }

        if (currentProduct.getCanAddInitial() != null) {
            if (currentProduct.getCanAddInitial() == 1) {
                holder.binding.addQuantity.setVisibility(View.VISIBLE);
            }if (currentProduct.getCanAddInitial() == 0) {
                holder.binding.addQuantity.setVisibility(View.GONE);
            }

        }
        if (currentProduct.getStoreID().equals("0")) {
            holder.binding.edit.setVisibility(View.GONE);
        }
        if (!(currentProduct.getStoreID().equals("0"))) {
            holder.binding.edit.setVisibility(View.VISIBLE);
        }
        String category = currentProduct.getCategory();
        String itemName = currentProduct.getProductTitle();
        String itemCode = currentProduct.getPcode();
        String brandName = currentProduct.getBrandName();
        String unit = currentProduct.getName();
        String weight = currentProduct.getProductDimensions();
        String processedBy = currentProduct.getFullName();

        int position1 = position + 1;

        holder.binding.sl.setText(":  " + position1);

        if (category == null) {
            holder.binding.categoryName.setText(":");
        } else {
            holder.binding.categoryName.setText(":  " + category);
        }

        if (itemName == null) {
            holder.binding.itemName.setText(":");
        } else {
            holder.binding.itemName.setText(":  " + itemName);
        }

        if (itemCode == null) {
            holder.binding.itemCode.setText(":");
        } else {
            holder.binding.itemCode.setText(":  " + itemCode);
        }

        if (brandName == null) {
            holder.binding.brandName.setText(":");
        } else {
            holder.binding.brandName.setText(":  " + brandName);

        }

        if (weight == null) {
            holder.binding.weight.setText(":");
        } else {
            holder.binding.weight.setText(":  " + weight + " " + unit);
        }

        if (processedBy == null) {
            holder.binding.processdBy.setText("");
        } else {
            holder.binding.processdBy.setText("" + processedBy);
        }

        try {
            Glide.with(context).load(ImageBaseUrl.image_base_url + currentProduct.getProfilePhoto()).centerCrop().
                    error(R.drawable.erro_logo).placeholder(R.drawable.erro_logo).
                    into(holder.binding.itemImageView);

        } catch (NullPointerException e) {
            Log.d("ERROR", e.getMessage());
            Glide.with(context).load(R.mipmap.ic_launcher).into(holder.binding.itemImageView);
        }


        holder.binding.setClickHandle(new ItemListClickHandle() {
            @Override
            public void edit() {
                if (manage(holder, currentProduct.getStoreID(), "This product not editable")) {

                    return;
                }

                try {

                    Bundle bundle = new Bundle();
                    bundle.putString("id", currentProduct.getProductID());
                    bundle.putString("portion", null);
                    ItemListFragment.pageNumber = 1;
                    ItemListFragment.isFirstLoad = 0;
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.itemListFragment_to_editItem, bundle);


                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }
            }

            @Override
            public void add() {
                try {
                   /* if (manage(holder, currentProduct.getStoreID(), "This product not editable")){

                        return;
                    }*/

                    if (currentProduct.getCanAddInitial() != null) {
                        if (currentProduct.getCanAddInitial() == 1) {
                            Bundle bundle = new Bundle();
                            bundle.putString("id", currentProduct.getProductID());
                            bundle.putString("portion", "Add New Item Initial Quantity");
                            bundle.putString("itemName", currentProduct.getProductTitle());
                            ItemListFragment.pageNumber = 1;
                            ItemListFragment.isFirstLoad = 0;
                            Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.itemListFragment_to_confirm_edit_editItem, bundle);
                        }else
                            Toast.makeText(context, "Initial quantity already added", Toast.LENGTH_SHORT).show();

                    }


                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }
            }

            @Override
            public void addPacket() {
                if (manage(holder, currentProduct.getStoreID(), "This product not editable")) {
                    return;
                }
                try {

                    Bundle bundle = new Bundle();
                    bundle.putString("id", currentProduct.getProductID());
                    bundle.putString("categoryNameForTitle", currentProduct.getProductTitle());
                    ItemListFragment.pageNumber = 1;
                    ItemListFragment.isFirstLoad = 0;
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.itemListFragment_to_addItemPacketFragment, bundle);

                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }
            }

            @Override
            public void addVarient() {
                Bundle bundle = new Bundle();
                bundle.putString("id", currentProduct.getProductID());
                bundle.putString("title", currentProduct.getProductTitle());
                bundle.putString("pageName", "Varient List [ " + currentProduct.getProductTitle() + " ]");
                ItemListFragment.pageNumber = 1;
                ItemListFragment.isFirstLoad = 0;
                Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.itemListFragment_to_varientlistFragment, bundle);
            }
        });
    }

    private boolean manage(ViewHolder holder, String storeID, String title) {
        if (storeID.equals("0")) {
            Toasty.info(context, "" + title, Toasty.LENGTH_LONG).show();
            return true;
        }
        return false;
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemListLayoutBinding binding;

        public ViewHolder(final ItemListLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
