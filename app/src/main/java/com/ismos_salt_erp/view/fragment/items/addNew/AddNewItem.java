package com.ismos_salt_erp.view.fragment.items.addNew;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.bottomsheet.Bottomsheet;
import com.ismos_salt_erp.bottomsheet.FetchId;
import com.ismos_salt_erp.clickHandle.AddNewItemClickHandle;
import com.ismos_salt_erp.databinding.FragmentAddNewItemBinding;
import com.ismos_salt_erp.serverResponseModel.AddNewItemBrand;
import com.ismos_salt_erp.serverResponseModel.AddNewItemCategory;
import com.ismos_salt_erp.serverResponseModel.AddNewItemUnit;
import com.ismos_salt_erp.utils.BottomUtil;
import com.ismos_salt_erp.utils.PathUtil;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.viewModel.AddNewItemViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@NoArgsConstructor
public class AddNewItem extends AddUpDel implements AdapterView.OnItemSelectedListener, FetchId {
    private FragmentAddNewItemBinding binding;
    private AddNewItemViewModel addNewItemViewModel;
    public boolean isSubmit = false;

    MultipartBody.Part image = null;
    /**
     * For category
     */
    private List<AddNewItemCategory> categoryResponseList;
    private List<String> categoryNameList;
    /**
     * for unit
     */
    private List<AddNewItemUnit> unitResponseList;
    private List<String> unitNameList;
    /**
     * For Brand
     */
    private List<AddNewItemBrand> brandResponseList;
    private List<String> brandNameList;

    ArrayAdapter<String> arrayadapter;


    private String selectedCategory, selectedPrimaryUnit, selectedBrand;
    private boolean checkCategory = true;

    public AddNewItem(boolean isSubmit) {
        this.isSubmit = isSubmit;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_item, container, false);

        binding.toolbar.toolbarTitle.setText("Add New Item");
        addNewItemViewModel = new ViewModelProvider(this).get(AddNewItemViewModel.class);
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        binding.spinner1.setOnItemSelectedListener(this);
        /**
         * now getData from server
         */
        getPageDataFromServer();
// here new design implement
        binding.access.unitLayout.setOnClickListener((View.OnClickListener) v -> showBottomSheet(BottomUtil.selectUnit));
        binding.access.brandLayout.setOnClickListener((View.OnClickListener) v -> showBottomSheet(BottomUtil.selectBrand));
        binding.access.categoryLayout.setOnClickListener((View.OnClickListener) v -> showBottomSheet(BottomUtil.selectCategory));
        binding.access.imageGet.setOnClickListener((View.OnClickListener) v -> {
            forImage();

        });


        binding.setClickHandle(new AddNewItemClickHandle() {
            @Override
            public void getImage() {
                forImage();
            }

            @Override
            public void save() {
                if (!(isInternetOn(getActivity()))) {
                    infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
                    return;
                }
                hideKeyboard(getActivity());
                if (selectedCategory == null) {
                    infoMessage(getActivity().getApplication(), "Please select category");
                    return;

                }
                if (binding.access.itemNameEt.getText().toString().isEmpty()) {
                    binding.access.itemNameEt.setError("Empty");
                    binding.access.itemNameEt.requestFocus();
                    return;
                }


                if (selectedPrimaryUnit == null) {
                    message(getString(R.string.unit_select_mes));
                    return;
                }
                if (selectedBrand == null) {
                    message(getString(R.string.brand_select_mes));


                    return;
                }
                if (checkCategory) {
                    if (binding.access.weightEt.getText().toString().isEmpty()) {
                        message(getString(R.string.add_weigt_mes));
                        return;
                    }
                }

                showDialog(getString(R.string.add_item_dialog_title));
            }
        });

        binding.primaryUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPrimaryUnit = unitResponseList.get(position).getiD();
                binding.primaryUnit.setEnableErrorLabel(false);
                binding.primaryUnit.setErrorText("Empty");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBrand = brandResponseList.get(position).getBrandID();
                binding.brand.setEnableErrorLabel(false);
                binding.brand.setErrorText("Empty");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        return binding.getRoot();
    }

    private void showBottomSheet(String whoseFor) {
        Bottomsheet bottomsheet = new Bottomsheet(AddNewItem.this, whoseFor, unitResponseList, brandResponseList, categoryResponseList);
        bottomsheet.show((getActivity().getSupportFragmentManager()), bottomsheet.getTag());

    }

    private void submit() {
        hideKeyboard(getActivity());
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        addNewItemViewModel.submitAddNewItemInfo(
                getActivity(), selectedCategory, /*binding.itemName.getText().toString()*/ binding.access.itemNameEt.getText().toString(),
                selectedPrimaryUnit, selectedBrand, /*binding.weight.getText().toString()*/binding.access.weightEt.getText().toString(),
                binding.note.getText().toString(), image
        ).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null || response.getStatus() == 500) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), "" + response.getMessage());
                return;
            }
            successMessage(getActivity().getApplication(), "" + response.getMessage());

            removeAllInputData();

            Bundle bundle = new Bundle();
            bundle.putString("id", response.getProductID());
            bundle.putString("itemName", binding.itemName.getText().toString());
            Navigation.findNavController(getView()).navigate(R.id.action_addNewItem_to_confirmAddNewItem, bundle);
        });
    }

    private void removeAllInputData() {
        selectedCategory = null;
        selectedPrimaryUnit = null;
        selectedBrand = null;
        binding.spinner1.setSelected(false);
        binding.access.itemNameEt.setText("");
        binding.access.weightEt.setText("");

        binding.primaryUnit.setSelected(false);
        binding.weight.setText("");
        binding.brand.setSelected(false);
        binding.access.itemCode.setText("");
        binding.note.setText("");
    }

    @Override
    public void save(boolean yesOrNo) {
        if (yesOrNo == true) {
            submit();
        }
    }

    @Override
    public void imageUri(Intent data) {
        binding.access.imageGet.setImageBitmap(getBitmapImage(data));
         image = imageLogobody(data.getData(), "");

    }


    private void getPageDataFromServer() {
        if (isSubmit) {
            selectedCategory = null;
            selectedPrimaryUnit = null;
            selectedBrand = null;
            binding.spinner1.setSelected(false);
            binding.itemName.setText("");
            binding.primaryUnit.setSelected(false);
            binding.weight.setText("");
            binding.brand.setSelected(false);
            binding.itemCode.setText("");
            binding.note.setText("");
        }


        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        addNewItemViewModel.getAddNewPageData(getActivity())
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
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
                        unitNameList.add(response.getUnit().get(i).getName());
                    }
                    binding.primaryUnit.setItem(unitNameList);
                    /**
                     * for brand
                     */
                    brandResponseList = new ArrayList<>();
                    brandResponseList.clear();
                    brandNameList = new ArrayList<>();
                    brandNameList.clear();
                    brandResponseList.addAll(response.getBrand());
                    for (int i = 0; i < response.getBrand().size(); i++) {
                        brandNameList.add(response.getBrand().get(i).getBrandName());
                    }
                    binding.brand.setItem(brandNameList);


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
                        categoryNameList.add(response.getCategory().get(i).getCategory());

                    }

                    List<String> ok = new ArrayList<>(Arrays.asList("Select"));
                    ok.addAll(categoryNameList);
                    try {
                        arrayadapter = new ArrayAdapter<String>(getActivity(), R.layout.tv_ok, ok) {
                            @Override
                            public boolean isEnabled(int position) {
                                if (position == 1 || position == 7) { //Disable the third item of spinner.
                                    return false;
                                } else {
                                    return true;
                                }
                            }

                            @Override
                            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                View spinnerview = super.getDropDownView(position, convertView, parent);
                                TextView spinnertextview = (TextView) spinnerview;
                                if (position == 1 || position == 7) { //Set the disable spinner item color fade.
                                    spinnertextview.setTextColor(Color.parseColor("#bcbcbb"));
                                } else {
                                    spinnertextview.setTextColor(Color.BLACK);
                                }
                                return spinnerview;
                            }
                        };
                        arrayadapter.setDropDownViewResource(R.layout.tv_ok);
                        binding.spinner1.setAdapter(arrayadapter);
                    } catch (Exception e) {
                    }


                });
    }

    private void getItemCodeBycatId(String selectedCategory) {

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
                    binding.itemCode.setText("" + response.getCode());
                    binding.access.itemCode.setText(response.getCode());
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isSubmit) {
            selectedCategory = null;
            selectedPrimaryUnit = null;
            selectedBrand = null;
            binding.spinner1.setSelected(false);
            binding.itemName.setText("");
            binding.primaryUnit.setSelected(false);
            binding.weight.setText("");
            binding.brand.setSelected(false);
            binding.itemCode.setText("");
            binding.note.setText("");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isSubmit) {
            selectedCategory = null;
            selectedPrimaryUnit = null;
            selectedBrand = null;
            binding.spinner1.setSelected(false);
            binding.itemName.setText("");
            binding.primaryUnit.setSelected(false);
            binding.weight.setText("");
            binding.brand.setSelected(false);
            binding.itemCode.setText("");
            binding.note.setText("");
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            if (position == 0) {
                selectedCategory = null;
                return;
            }
            selectedCategory = categoryResponseList.get(position).getCategoryID();
            getItemCodeBycatId(selectedCategory);
        } catch (Exception e) {

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void fetchIdAndName(String name, String id, String whoseFor) {
        if (whoseFor != null) {
            if (whoseFor.equals(BottomUtil.selectUnit)) {
                binding.access.unitTv.setText("" + name);
                selectedPrimaryUnit = id;


            } else if (whoseFor.equals(BottomUtil.selectBrand)) {
                binding.access.brandTv.setText("" + name);
                selectedBrand = id;

            } else if (whoseFor.equals(BottomUtil.selectCategory)) {
                binding.access.categoryTv.setText("" + name);
                selectedCategory = id;

                getItemCodeBycatId(id);
                if (id.equals("739") || id.equals("740") || id.equals("741")) {
                    binding.access.weightLevelTv.setText("Weight * ");
                    checkCategory = true;
                } else {
                    binding.access.weightLevelTv.setText("Weight");
                    checkCategory = false;
                }
            }

        }

    }
}