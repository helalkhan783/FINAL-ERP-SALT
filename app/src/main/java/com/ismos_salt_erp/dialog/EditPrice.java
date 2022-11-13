package com.ismos_salt_erp.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.PriceListAdapter;
import com.ismos_salt_erp.view.fragment.items.edit.EditItem;
import com.ismos_salt_erp.viewModel.AddNewItemViewModel;

public class EditPrice {
    AddNewItemViewModel addNewItemViewModel;
    FragmentActivity activity;
    String type,sellingPrice1,productId,productPriceId;
    LifecycleOwner lifecycleOwner;
    public EditPrice(FragmentActivity activity, String sellingPrice, String type, String productId, String productPriceId, LifecycleOwner lifecycleOwner) {
        this.activity= activity;
        this.sellingPrice1= sellingPrice;
        this.type= type;
        this.productId= productId;
        this.productPriceId= productPriceId;
        this.lifecycleOwner= lifecycleOwner;

    }
 //   addNewItemViewModel = new ViewModelProvider( this).get(AddNewItemViewModel.class);

    public void addPrice() {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        addNewItemViewModel = new ViewModelProvider(activity).get(AddNewItemViewModel.class);

        @SuppressLint("InflateParams")
        View view = ((activity).getLayoutInflater().inflate(R.layout.add_priice_layout, null));
        //Set the view
        builder.setView(view);
        EditText sellingPriceEt;
        sellingPriceEt = view.findViewById(R.id.sellingPriceEt);
        Button bOk = view.findViewById(R.id.saveBtn1);
        Button cancel = view.findViewById(R.id.cancelBtn1);
        AlertDialog alertDialog = builder.create();
       // Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        if (type.equals("edit")){
            sellingPriceEt.setText(sellingPrice1);
        }

        bOk.setOnClickListener(v -> {
            if (sellingPriceEt.getText().toString().isEmpty()) {
                sellingPriceEt.setError("Empty");
                sellingPriceEt.requestFocus();
                return;
            }
            /**
             * for edit price
             */
            if (type.equals("edit")){
                ProgressDialog progressDialog = new ProgressDialog(activity);
                progressDialog.show();
                addNewItemViewModel.editItemPrice(activity,productId,productPriceId,sellingPriceEt.getText().toString(),sellingPrice1).observe(lifecycleOwner,
                        response -> {
                            progressDialog.dismiss();
                         if (response.getStatus()==400){
                             Toast.makeText(activity, ""+response.getMessage(), Toast.LENGTH_SHORT).show();
                             return;
                         }
                         if (response.getStatus()==200){
                             Toast.makeText(activity, ""+response.getMessage(), Toast.LENGTH_SHORT).show();

                             getPriceList();
                         }
                        });
            }


            /**
             * for add new price
             */
            if (type.equals("AddNewPrice") || productPriceId == null){
                ProgressDialog progressDialog = new ProgressDialog(activity);
                progressDialog.show();
                addNewItemViewModel.addNewPrice(activity,productId,sellingPriceEt.getText().toString()).observe(lifecycleOwner
                        , response -> {
                            progressDialog.dismiss();
                            if (response.getStatus()==400){
                                Toast.makeText(activity, ""+response.getMessage(), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (response.getStatus()==200){
                                Toast.makeText(activity, ""+response.getMessage(), Toast.LENGTH_SHORT).show();
                                getPriceList();
                            }

                        });
            }


            /*try {
                View view1 = activity.findViewById(android.R.id.content);
                    if (view1 != null) {
                        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
                    }
                } catch (Exception ignored) {
                }*/

            alertDialog.dismiss();

        });
        cancel.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        alertDialog.show();
    }

    private void getPriceList() {
        addNewItemViewModel.getEditItemPageData(activity, productId)
                .observe(lifecycleOwner, editItemResponse -> {
                    if (editItemResponse == null) {
                        //       errorMessage(activity.getApplication(), "Something Wrong");
                        return;
                    }
                    if (editItemResponse.getStatus() == 400) {
                        //   infoMessage(getActivity().getApplication(), "" + editItemResponse.getMessage());
                        return;
                    }
                    /**
                     * set price list data to recycler view
                     */
                    EditItem.binding.priceListRv.setLayoutManager(new LinearLayoutManager(activity));
                    PriceListAdapter adapter = new PriceListAdapter(activity,editItemResponse.getPriceDetails(),productId,lifecycleOwner);
                    EditItem.binding.priceListRv.setAdapter(adapter);
                });
    }

}
