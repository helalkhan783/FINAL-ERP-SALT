package com.ismos_salt_erp.view.fragment.items.edit;


import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;


import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


import com.bumptech.glide.Glide;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.PriceListAdapter;
import com.ismos_salt_erp.clickHandle.EditItemClickHandle;
import com.ismos_salt_erp.databinding.FragmentEditItemBinding;
import com.ismos_salt_erp.dialog.EditPrice;
import com.ismos_salt_erp.serverResponseModel.AddNewItemBrand;
import com.ismos_salt_erp.serverResponseModel.AddNewItemCategory;
import com.ismos_salt_erp.serverResponseModel.AddNewItemUnit;

import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.viewModel.AddNewItemViewModel;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;


public class EditItem extends AddUpDel implements SmartMaterialSpinner.OnItemSelectedListener, View.OnClickListener {
    public static FragmentEditItemBinding binding;
    private AddNewItemViewModel addNewItemViewModel;


    private MultipartBody.Part image = null;


    private List<AddNewItemCategory> categoryResponseList;
    private List<String> categoryNameList;

    private List<AddNewItemUnit> unitResponseList;
    private List<String> unitNameList;

    private List<AddNewItemBrand> brandResponseList;
    private List<String> brandNameList;


    private String selectedCategory, selectedPrimaryUnit, selectedBrand, id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_item, container, false);
        binding.toolbar.toolbarTitle.setText("Update item");
        addNewItemViewModel = new ViewModelProvider(this).get(AddNewItemViewModel.class);
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        getDataFromPreviousFragment();

        binding.setClickHandle(new EditItemClickHandle() {
            @Override
            public void getImage() {
                forImage();
            }

            @Override
            public void submit() {
                hideKeyboard(getActivity());
                if (selectedCategory == null) {
                    infoMessage(getActivity().getApplication(), "Please select category");
                    return;
                }
                if (binding.itemName.getText().toString().isEmpty()) {
                    binding.itemName.setError("Empty");
                    binding.itemName.requestFocus();
                    return;
                }

                if (selectedPrimaryUnit == null) {
                   message("Please select primary unit");
                    return;
                }
                if (selectedBrand == null) {
                  message(getString(R.string.brand_select_mes));
                    return;
                }
                if (binding.itemCode.getText().toString().isEmpty()) {
                    binding.itemCode.setError("Empty");
                    binding.itemCode.requestFocus();
                    return;
                }
                showDialog(getString(R.string.item_update_dialog_title));
            }
        });

        binding.category.setOnItemSelectedListener(this);
        binding.primaryUnit.setOnItemSelectedListener(this);
        binding.brand.setOnItemSelectedListener(this);

        binding.itemDetails.setOnClickListener(this);
        binding.ItemPrice.setOnClickListener(this);
        binding.addNewPriceBtn.setOnClickListener(this);


        return binding.getRoot();
    }


    private void getPageDataFromServer() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        addNewItemViewModel.getAddNewPageData(getActivity())
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }

                    /**
                     * for set previous selected item
                     */
                    addNewItemViewModel.getEditItemPageData(getActivity(), id)
                            .observe(getViewLifecycleOwner(), editItemResponse -> {
                                if (editItemResponse == null) {
                                    errorMessage(getActivity().getApplication(), "Something Wrong");
                                    return;
                                }
                                if (editItemResponse.getStatus() == 400) {
                                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                                    return;
                                }

                                /**
                                 * ok now set Data to view
                                 */
                                //for category
                                categoryResponseList = new ArrayList<>();
                                categoryResponseList.clear();
                                categoryNameList = new ArrayList<>();
                                categoryNameList.clear();
                                categoryResponseList.addAll(response.getCategory());
                                for (int i = 0; i < response.getCategory().size(); i++) {
                                    categoryNameList.add("" + response.getCategory().get(i).getCategory());
                                }
                                binding.category.setItem(categoryNameList);

                                /**
                                 * now set previous selected category
                                 */
                                for (int i = 0; i < categoryResponseList.size(); i++) {
                                    if (categoryResponseList.get(i).getCategoryID().equals(editItemResponse.getProductDetails().getCategoryID())) {
                                        binding.category.setSelection(i);
                                        break;
                                    }
                                }


                                /**
                                 * for unit
                                 */
                                unitResponseList = new ArrayList<>();
                                unitResponseList.clear();
                                unitNameList = new ArrayList<>();
                                unitResponseList.clear();
                                unitResponseList.addAll(response.getUnit());
                                for (int i = 0; i < response.getUnit().size(); i++) {
                                    unitNameList.add("" + response.getUnit().get(i).getName());
                                }
                                binding.primaryUnit.setItem(unitNameList);

                                /**
                                 * now set previous selected unit
                                 */
                                for (int i = 0; i < unitResponseList.size(); i++) {
                                    if (unitResponseList.get(i).getiD().equals(editItemResponse.getProductDetails().getBaseUnit())) {
                                        binding.primaryUnit.setSelection(i);
                                        break;
                                    }
                                }


                                /**
                                 * for brand
                                 */
                                brandResponseList = new ArrayList<>();
                                brandResponseList.clear();
                                brandNameList = new ArrayList<>();
                                brandNameList.clear();
                                brandResponseList.addAll(response.getBrand());
                                for (int i = 0; i < response.getBrand().size(); i++) {
                                    brandNameList.add("" + response.getBrand().get(i).getBrandName());
                                }
                                binding.brand.setItem(brandNameList);


                                for (int i = 0; i < brandResponseList.size(); i++) {
                                    if (brandResponseList.get(i).getBrandID().equals(editItemResponse.getProductDetails().getBrandID())) {
                                        binding.brand.setSelection(i);
                                        break;
                                    }
                                }
                                /**
                                 * now set others previous selected information
                                 */
                                binding.itemName.setText(editItemResponse.getProductDetails().getProductTitle());
                                binding.weight.setText(editItemResponse.getProductDetails().getProductDimensions());
                                binding.itemCode.setText(editItemResponse.getProductDetails().getPcode());

                                binding.note.setText(editItemResponse.getProductDetails().getProductDetails().replaceAll("\\<.*?>", ""));

                                /**
                                 * set price list data to recycler view
                                 */
                                binding.priceListRv.setLayoutManager(new LinearLayoutManager(getContext()));
                                PriceListAdapter adapter = new PriceListAdapter(getActivity(), editItemResponse.getPriceDetails(), id, getViewLifecycleOwner());
                                binding.priceListRv.setAdapter(adapter);

                                /**
                                 * now load image
                                 */
                                try {
                                    Glide
                                            .with(getContext())
                                            .load(editItemResponse.getProductDetails().getProductImage())
                                            .centerCrop()
                                            .error(R.drawable.app_sub_logo)
                                            .placeholder(R.drawable.app_sub_logo)
                                            .into(binding.image);
                                } catch (Exception e) {
                                    Log.d("ERROR", "" + e.getMessage());
                                }

                            });

                });
    }


    private void getDataFromPreviousFragment() {
        assert getArguments() != null;
        id = getArguments().getString("id");
    }

    private void getItemCodeBycatId(String selectedCategory) {

        hideKeyboard(getActivity());
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }

        addNewItemViewModel.getItemCodeByCatId(getActivity(), selectedCategory)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }
                    binding.itemCode.setText(response.getCode());
                });
    }




    @Override
    public void save() {

            updateItemDialog();

    }


    @Override
    public void imageUri(Intent data) {
        binding.image.setImageBitmap(getBitmapImage(data));
        binding.imageName.setText(new File("" + data.getData()).getName());
        image = imageLogobody(data.getData(), "");
    }


    private void updateItemDialog() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        addNewItemViewModel.submitEditItemInfo(
                getActivity(), id, binding.itemName.getText().toString(),
                selectedCategory, selectedPrimaryUnit, selectedBrand,
                binding.weight.getText().toString(), binding.note.getText().toString(), image
        ).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), "" + response.getMessage());
                return;
            }
            successMessage(getActivity().getApplication(), "" + response.getMessage());
            getActivity().onBackPressed();

        });


    }


    @Override
    public void onStart() {
        super.onStart();
        getPageDataFromServer();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.category) {
            selectedCategory = categoryResponseList.get(position).getCategoryID();
            getItemCodeBycatId(selectedCategory);
        }
        if (parent.getId() == R.id.primaryUnit) {
            selectedPrimaryUnit = unitResponseList.get(position).getiD();

        }
        if (parent.getId() == R.id.brand) {
            selectedBrand = brandResponseList.get(position).getBrandID();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.itemDetails) {
            binding.priceLayout.setVisibility(View.GONE);
            binding.itemDetailsLayout.setVisibility(View.VISIBLE);
            binding.updateBtn.setVisibility(View.VISIBLE);
            binding.ItemPrice.setBackgroundColor(getResources().getColor(R.color.gray));
            binding.itemDetails.setBackgroundColor(getResources().getColor(R.color.app_color));
        }
        if (v.getId() == R.id.ItemPrice) {
            binding.itemDetailsLayout.setVisibility(View.GONE);
            binding.updateBtn.setVisibility(View.GONE);
            binding.priceLayout.setVisibility(View.VISIBLE);
            binding.ItemPrice.setBackgroundColor(getResources().getColor(R.color.app_color));
            binding.itemDetails.setBackgroundColor(getResources().getColor(R.color.gray));
        }
        if (v.getId() == R.id.addNewPriceBtn) {
            EditPrice editPrice = new EditPrice(getActivity(), "0", "AddNewPrice", id, null, getViewLifecycleOwner());
            editPrice.addPrice();
        }
    }
}