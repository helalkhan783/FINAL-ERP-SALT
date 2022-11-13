package com.ismos_salt_erp.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.PackagingRecyclerModelBinding;
import com.ismos_salt_erp.localDatabase.PackagingDatabaseModel;
import com.ismos_salt_erp.serverResponseModel.PackagingOriginItemList;
import com.ismos_salt_erp.view.fragment.production.packaging.AddNewPackaging;
import com.ismos_salt_erp.view.fragment.production.packaging.PackagingRecyclerItemClickHandle;
import com.ismos_salt_erp.viewModel.PackagingViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AddNewPackagingAdapter extends RecyclerView.Adapter<AddNewPackagingAdapter.MyHolder> {
    private PackagingViewModel packagingViewModel;
    private FragmentActivity context;
    private List<PackagingOriginItemList> itemList;
    private PackagingRecyclerItemClickHandle changeHandle;
    private List<PackagingDatabaseModel> currentItemList;


    public AddNewPackagingAdapter(FragmentActivity context, List<PackagingOriginItemList> itemList,
                                  PackagingRecyclerItemClickHandle changeHandle, List<PackagingDatabaseModel> currentItemList,AddNewPackaging addNewPackaging) {
        this.context = context;
        this.itemList = itemList;
        this.changeHandle = changeHandle;
        this.currentItemList = currentItemList;
        packagingViewModel = new ViewModelProvider(context).get(PackagingViewModel.class);
    }

    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        PackagingRecyclerModelBinding binding
                = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.packaging_recycler_model, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        PackagingDatabaseModel currentDatabaseItem = currentItemList.get(holder.getAdapterPosition());
       try {
     //      PackagingDatabaseModel currentDatabaseItem = AddNewPackaging.packagingDatabaseModelList.get(holder.getAdapterPosition());

          /* if (holder.getAdapterPosition()==0){
               holder.binding.delete.setVisibility(View.GONE);
           }*/
           // item delete

           holder.binding.packagingQuantity.setText(""+currentDatabaseItem.getQuantity());

           holder.binding.delete.setOnClickListener(v -> {
               changeHandle.removeItem(holder.getAdapterPosition());
           });

           /**
            * set Item Name for every item
            */
           List<String> itemNameList = new ArrayList<>();
           itemNameList.clear();
           for (int i = 0; i < itemList.size(); i++) {
               itemNameList.add(itemList.get(i).getProductTitle());
           }
           holder.binding.itemName.setItem(itemNameList);

           if (currentItemList != null) {
               String previousSelectedItemId = null;
               for (int i = 0; i < itemList.size(); i++) {
                   if (currentDatabaseItem.getItemId().equals(itemList.get(i).getProductID())) {
                       holder.binding.itemName.setSelection(i);//for set previous selected itemName
                       previousSelectedItemId = itemList.get(i).getProductID();
                   }
               }
               if (!(currentDatabaseItem.getItemId().equals("0"))) {
                   String currentSelectedPackedId = currentDatabaseItem.getPackedId();
                   /**
                    * now set current selected packed id by itemName id
                    */
                   //setPreviousSelectedPacked(currentSelectedItemId,holder);
                   changeHandle.setPreviousSelectedItemName(holder.getAdapterPosition(), previousSelectedItemId, currentSelectedPackedId);
                   holder.binding.weight.setText(currentDatabaseItem.getWeight());
                   holder.binding.packagingQuantity.setText(currentDatabaseItem.getQuantity());
                   holder.binding.pktTagItems.setText(currentDatabaseItem.getPktTag());
                   holder.binding.note.setText(currentDatabaseItem.getNote());


                   try {
                       int weight = Integer.parseInt(currentDatabaseItem.getWeight());
                       int quantity = Integer.parseInt(currentDatabaseItem.getQuantity());
                       double totalQuantity = weight * quantity;

                       holder.binding.totalWeight.setText(String.valueOf(totalQuantity));
                       holder.binding.note.setText(currentDatabaseItem.getNote());
                   } catch (Exception e) {
                       Log.d("ERROR", "ERROR");
                   }
               }


           }
       }catch (Exception d){}
        /**
         * now set item name click handle
         */
        holder.binding.itemName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (AddNewPackaging.selectedStore == null) {
                    AddNewPackaging.binding.store.setEnableErrorLabel(true);
                    AddNewPackaging.binding.store.setErrorText("Empty");
                    return;
                }
                changeHandle.selectItemName(position, holder.getAdapterPosition(), currentItemList.get(holder.getAdapterPosition()));//handle selection
                holder.binding.itemName.setEnableErrorLabel(false);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.binding.packedName.setOnEmptySpinnerClickListener(() -> {
            holder.binding.itemName.setEnableErrorLabel(true);
            holder.binding.itemName.setErrorText("Empty");
        });



        /**
         * now set packed name click handle
         */
        holder.binding.packedName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                changeHandle.selectPackedName(position, holder.getAdapterPosition(), AddNewPackaging.packagingDatabaseModelList.get(holder.getAdapterPosition()));
//                changeHandle.selectPackedName(position, holder.getAdapterPosition(),currentItemList.get(holder.getAdapterPosition()));
                /**
                 * now set selected packed name stock
                 */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /**
         * now handle quantity change
         */
        holder.binding.packagingQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
           try{     String currentQuantity = holder.binding.packagingQuantity.getText().toString();
               if (currentQuantity.isEmpty() || currentQuantity.equals("")) {
                   currentQuantity = "1";
               }
               String currentWeight = holder.binding.weight.getText().toString();

               if (currentWeight.equals("0") || currentWeight.isEmpty()) {
                   currentWeight = "1";
               }
               double total = Double.parseDouble(currentWeight) * Double.parseDouble(currentQuantity);
               holder.binding.totalWeight.setText(String.valueOf(total));
               changeHandle.changeQuantity(currentQuantity, holder.getAdapterPosition(), AddNewPackaging.packagingDatabaseModelList.get(holder.getAdapterPosition()));
           }catch (Exception e){}
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        holder.binding.note.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String currentQuantity = holder.binding.packagingQuantity.getText().toString();
                changeHandle.changeNote(holder.binding.note.getText().toString(), currentQuantity, holder.getAdapterPosition(), AddNewPackaging.packagingDatabaseModelList.get(holder.getAdapterPosition()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return currentItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private PackagingRecyclerModelBinding binding;

        public MyHolder(PackagingRecyclerModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }


}

