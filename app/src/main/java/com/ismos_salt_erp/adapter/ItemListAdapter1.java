package com.ismos_salt_erp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.CustomItemLayoutBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.Product;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.items.ItemListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class ItemListAdapter1 extends RecyclerView.Adapter<ItemListAdapter1.ViewHolder> {
    private Context context;
    private List<Product> products;
    private boolean click = false;
    PopupWindow mypopupWindow;
    List<Integer> permissionId = PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions());

    public ItemListAdapter1(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @NotNull
    @Override
    public ItemListAdapter1.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        CustomItemLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.custom_item_layout, parent, false);
        return new ItemListAdapter1.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemListAdapter1.ViewHolder holder, int position) {
        Product currentProduct = products.get(position);
        holder.binding.productName.setText("" + currentProduct.getProductTitle());
        holder.binding.categoryName.setText("| " + currentProduct.getCategory());
        if (currentProduct.getBrandName() != null) {
            holder.binding.brand.setText("" + currentProduct.getBrandName());
        }
        holder.binding.price.setText("৳ " + currentProduct.getPcode());
        holder.binding.disPrice.setText("৳ " + currentProduct.getPcode());
        holder.binding.weight.setText("" + currentProduct.getName());


        if (currentProduct.getCategoryID() != null) {
            if (currentProduct.getCategoryID().equals("739") || currentProduct.getCategoryID().equals("740") || currentProduct.getCategoryID().equals("741")) {
                if (permissionId.contains(1551)) {
                    holder.binding.varientLayout.setVisibility(View.VISIBLE);
                    holder.binding.varientTv.setText("" + currentProduct.getTotalVarient());
                }
            } else {
                holder.binding.varientLayout.setVisibility(View.GONE);
            }
        }

        if (currentProduct.getCategoryID() != null) {
            if (currentProduct.getCategoryID().equals("739") || currentProduct.getCategoryID().equals("740") || currentProduct.getCategoryID().equals("741")) {
                if (permissionId.contains(1551)) {

                    holder.binding.varientLayout.setVisibility(View.VISIBLE);
                    holder.binding.varientTv.setText("" + currentProduct.getTotalVarient());
                }
            }

            if (!(currentProduct.getCategoryID().equals("739") || currentProduct.getCategoryID().equals("740") || currentProduct.getCategoryID().equals("741"))) {

                holder.binding.varientLayout.setVisibility(View.GONE);

            }
        }


        holder.binding.menuBtn.setOnClickListener(v -> {
            setPopUpWindow(currentProduct, holder.binding.getRoot());
            mypopupWindow.showAsDropDown(v, -153, 0);

        });


    }

    private void setPopUpWindow(Product currentProduct, View root) {
        //here get permission id

        // inflate custom layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_popup_layout, null);
//pop up size declare
        mypopupWindow = new PopupWindow(view, 460, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        LinearLayout editItem, addQty, addvarient, addPacket;
        editItem = (LinearLayout) view.findViewById(R.id.editItem);
        addQty = (LinearLayout) view.findViewById(R.id.addQty);
        addvarient = (LinearLayout) view.findViewById(R.id.addVarient);
        addPacket = (LinearLayout) view.findViewById(R.id.addPacket);
        TextView productName = (TextView) view.findViewById(R.id.nameTv);
        productName.setSelected(true);
        productName.setText("" + currentProduct.getProductTitle());

        if (currentProduct.getCategoryID().equals("739") || currentProduct.getCategoryID().equals("740") || currentProduct.getCategoryID().equals("741")) {
            if (permissionId.contains(1551)) {
                addPacket.setVisibility(View.VISIBLE);
                addvarient.setVisibility(View.VISIBLE);

            }
        }

        if (!(currentProduct.getCategoryID().equals("739") || currentProduct.getCategoryID().equals("740") || currentProduct.getCategoryID().equals("741"))) {
            addPacket.setVisibility(View.GONE);
            addvarient.setVisibility(View.GONE);

        }

        if (!permissionId.contains(3)) {
            editItem.setVisibility(View.GONE);
        }
        if (!permissionId.contains(1499)) {
            addQty.setVisibility(View.GONE);
        }


        editItem.setOnClickListener(v -> {
            if (manage(currentProduct.getStoreID(), "This product not editable")) {
                return;
            }

            try {
                Bundle bundle = new Bundle();
                bundle.putString("id", currentProduct.getProductID());
                bundle.putString("portion", null);
                ItemListFragment.pageNumber = 1;
                ItemListFragment.isFirstLoad = 0;
                Navigation.findNavController(root).navigate(R.id.itemListFragment_to_editItem, bundle);
            } catch (Exception e) {
                Log.d("ERROR", "" + e.getMessage());
            }
            mypopupWindow.dismiss();
        });

        addQty.setOnClickListener(v -> {
            if (currentProduct.getCanAddInitial() != null) {
                if (currentProduct.getCanAddInitial() == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", currentProduct.getProductID());
                    bundle.putString("portion", "Add New Item Initial Quantity");
                    bundle.putString("itemName", currentProduct.getProductTitle());
                    ItemListFragment.pageNumber = 1;
                    ItemListFragment.isFirstLoad = 0;
                    Navigation.findNavController(root).navigate(R.id.itemListFragment_to_confirm_edit_editItem, bundle);
                } else
                    Toast.makeText(context, "Initial quantity already added", Toast.LENGTH_SHORT).show();

            }
            mypopupWindow.dismiss();
        });


        addvarient.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", currentProduct.getProductID());
            bundle.putString("title", currentProduct.getProductTitle());
            bundle.putString("pageName", "Varient List [ " + currentProduct.getProductTitle() + " ]");
            ItemListFragment.pageNumber = 1;
            ItemListFragment.isFirstLoad = 0;
            Navigation.findNavController(root).navigate(R.id.itemListFragment_to_varientlistFragment, bundle);
            mypopupWindow.dismiss();
        });

        addPacket.setOnClickListener(v -> {
            if (manage(currentProduct.getStoreID(), "This product not editable")) {
                return;
            }
            try {
                Bundle bundle = new Bundle();
                bundle.putString("id", currentProduct.getProductID());
                bundle.putString("categoryNameForTitle", currentProduct.getProductTitle());
                ItemListFragment.pageNumber = 1;
                ItemListFragment.isFirstLoad = 0;
                Navigation.findNavController(root).navigate(R.id.itemListFragment_to_addItemPacketFragment, bundle);

            } catch (Exception e) {
                Log.d("ERROR", "" + e.getMessage());
            }
            mypopupWindow.dismiss();
        });

    }

    private boolean manage(String storeID, String title) {
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

        private final CustomItemLayoutBinding binding;

        public ViewHolder(final CustomItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}

