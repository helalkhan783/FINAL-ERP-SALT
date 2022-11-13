package com.ismos_salt_erp.view.fragment.supplier;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.clickHandle.ToolbarClickHandle;
import com.ismos_salt_erp.databinding.FragmentEditForeignSupplierBinding;
import com.ismos_salt_erp.serverResponseModel.CountryListResponse;
import com.ismos_salt_erp.serverResponseModel.MillerProfileInfoResponse;
import com.ismos_salt_erp.utils.ImageBaseUrl;
import com.ismos_salt_erp.utils.PathUtil;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.CustomerViewModel;
import com.ismos_salt_erp.viewModel.MillerProfileInfoViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditForeignSupplierFragment extends BaseFragment {
    FragmentEditForeignSupplierBinding binding;

    MillerProfileInfoViewModel millerProfileInfoViewModel;
    private CustomerViewModel customerViewModel;

    private static final int PICK_IMAGE = 200;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 300;
    private Uri imageUri;


    /**
     * For Selected Customer
     */
    private List<String> selectedCustomerTypeName;
    private List<String> selectedCustomerTypeIdList;


    /**
     * For Selected  country
     */

    private List<String> countryName;
    private List<CountryListResponse> countryResponsesList;

    private String editable, dueLimit, oldImage, countryId, type, totalAmount, customerId, selectedCustomerType;
    MultipartBody.Part logoBody;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_foreign_supplier, container, false);
        millerProfileInfoViewModel = new ViewModelProvider(this).get(MillerProfileInfoViewModel.class);
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);

        binding.toolbar.toolbarTitle.setText("Update Foreign Supplier");
        binding.toolbar.setClickHandle(new ToolbarClickHandle() {
            @Override
            public void backBtn() {
                hideKeyboard(getActivity());
                getActivity().onBackPressed();
            }
        });
/**
 * getPrevious Fragment data
 */

        getPreviousFragmentData();
        /**
         * get page data
         */
        getPageData();
        /**
         * update data
         */

        binding.updateBtn.setOnClickListener(v -> {
            if (!(isInternetOn(getActivity()))) {
                infoMessage(getActivity().getApplication(), "Please check your internet connection");
                return;
            }
            if (binding.companyName.getText().toString().isEmpty()) {
                binding.companyName.setError("Empty");
                binding.companyName.requestFocus();
                return;
            }
            if (binding.ownerName.getText().toString().isEmpty()) {
                binding.ownerName.setError("Empty");
                binding.ownerName.requestFocus();
                return;
            }
            if (binding.phone.getText().toString().isEmpty()) {
                binding.phone.setError("Empty");
                binding.phone.requestFocus();
                return;
            }
            if (!binding.phone.getText().toString().isEmpty()) {
                if (!isValidPhone(binding.phone.getText().toString())) {
                    binding.phone.setError("Invalid Contact Number");
                    binding.phone.requestFocus();
                    return;
                }
            }

            if (!binding.altPhone.getText().toString().isEmpty()) {
                if (!isValidPhone(binding.altPhone.getText().toString())) {
                    binding.altPhone.setError("Invalid Number");
                    binding.altPhone.requestFocus();
                    return;
                }
            }

            if (!binding.email.getText().toString().isEmpty()) {
                if (!isValidEmail(binding.email.getText().toString())) {
                    binding.email.setError("Invalid Email");
                    binding.email.requestFocus();
                    return;
                }
            }
            if (countryId == null) {
                infoMessage(requireActivity().getApplication(), "Please select country");
                return;
            }
            if (selectedCustomerType == null) {
                infoMessage(requireActivity().getApplication(), "Please select supplier type");
                return;
            }
            hideKeyboard(getActivity());
            updateForeignSupplierDialog();
        });


        binding.customerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCustomerType = selectedCustomerTypeIdList.get(position);
                binding.customerType.setEnableErrorLabel(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countryId = String.valueOf(countryResponsesList.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(checkStoragePermission())) {
                    requestStoragePermission(STORAGE_PERMISSION_REQUEST_CODE);
                } else {
                    getLogoImageFromFile(getActivity().getApplication(), PICK_IMAGE);
                }
            }
        });


        return binding.getRoot();
    }

    private void getPreviousFragmentData() {
        customerId = getArguments().getString("customerId");
    }

    private void getPageData() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please check your internet connection");
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();
        millerProfileInfoViewModel.getProfileInfoResponse(getActivity()).observe(getViewLifecycleOwner(), new Observer<MillerProfileInfoResponse>() {
            @Override
            public void onChanged(MillerProfileInfoResponse response) {
                progressDialog.dismiss();
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "Something Wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    return;
                }

                if (response.getStatus() == 200) {
                    /**
                     * now set division list
                     */
                    countryResponsesList = new ArrayList<>();
                    countryResponsesList.clear();
                    countryResponsesList.addAll(response.getCountries());
                    countryName = new ArrayList<>();
                    countryName.clear();
                    //  binding.country.setItem(countryName);
                }


                /**
                 * now set selected customer
                 */
                selectedCustomerTypeName = new ArrayList<>();
                selectedCustomerTypeName.clear();
                selectedCustomerTypeIdList = new ArrayList<>();
                selectedCustomerTypeIdList.clear();
                countryResponsesList.addAll(response.getCountries());

                selectedCustomerTypeName.addAll(Arrays.asList("General"));
                selectedCustomerTypeIdList.addAll(Arrays.asList("5"));
                binding.customerType.setItem(selectedCustomerTypeName);
            }
        });

        ProgressDialog progressDialog1 = new ProgressDialog(getContext());
        progressDialog1.show();
        customerViewModel.customerEditResponse(getActivity(), customerId, "5").observe(getViewLifecycleOwner(),
                response -> {
                    progressDialog1.dismiss();
                    try {
                        if (response == null) {
                            errorMessage(getActivity().getApplication(), "Something wrong");
                            return;
                        }
                        if (response.getStatus() == 400) {
                            infoMessage(requireActivity().getApplication(), response.getMessage());
                            return;
                        }

                        editable = String.valueOf(response.getInitialAmountEditable());
                        totalAmount = response.getInitialPaymentInfo().getTotalAmount();
                        dueLimit = response.getCustomerInfo().getDueLimit();
                        type = response.getCustomerInfo().getTypeID();


                        oldImage = String.valueOf(response.getCustomerInfo().getImage());

                        /**
                         * now set data to view
                         */
                        binding.companyName.setText(response.getCustomerInfo().getCompanyName());
                        binding.ownerName.setText(response.getCustomerInfo().getCustomerFname());
                        binding.phone.setText(response.getCustomerInfo().getPhone());
                        binding.altPhone.setText(response.getCustomerInfo().getAltPhone());
                        binding.email.setText(response.getCustomerInfo().getEmail());
                        binding.tin.setText(response.getCustomerInfo().getTin());
                        binding.address.setText(response.getCustomerInfo().getAddress());
                        binding.note.setText(response.getCustomerInfo().getCustomerNote());
                        try {
                            Glide.with(getContext()).load(ImageBaseUrl.image_base_url + response.getCustomerInfo().getImage()).centerCrop().
                                    error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).
                                    into(binding.image);

                        } catch (NullPointerException e) {
                            Log.d("ERROR", e.getMessage());
                            Glide.with(getContext()).load(R.mipmap.ic_launcher).into(binding.image);
                        }

                        if (countryId == null) {
                            countryId = response.getCustomerInfo().getCountry();
                            for (int i = 0; i < countryResponsesList.size(); i++) {
                                /*if (countryResponsesList.get(i).getCountryID().equals(countryId)) {
                                    binding.country.setSelection(i);
                                }*/
                                countryName.add(countryResponsesList.get(i).getCountryName());
                            }
                            binding.country.setItem(countryName);

                            /**
                             * now show selected country
                             */
                            for (int i = 0; i < countryResponsesList.size(); i++) {
                                if (countryResponsesList.get(i).getCountryID().equals(countryId)) {
                                    binding.country.setSelection(i);
                                    break;
                                }
                            }

                        }

                        if (selectedCustomerType == null) {
                            selectedCustomerType = response.getCustomerInfo().getTypeID();
                            for (int i = 0; i < selectedCustomerTypeIdList.size(); i++) {
                                if (response.getCustomerInfo().getTypeID().equals(selectedCustomerType)) {
                                    binding.customerType.setSelection(i);
                                    break;
                                }
                            }
                        }

                    } catch (Exception e) {
                        Log.d("Error", e.getMessage());
                        progressDialog.dismiss();
                    }

                });
        progressDialog1.dismiss();


    }


    private void ValidationAndSubmit() {
        /**
         * for Image
         */
        if (imageUri != null) {//logo image not mandatory here so if user not select any logo image by default it send null
            File file = null;
            try {
                file = new File(PathUtil.getPath(getActivity(), imageUri));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            // MultipartBody.Part is used to send also the actual file name
            logoBody = MultipartBody.Part.createFormData("image", file.getName(), requestFile);//here document is name of from data
        } else {
            logoBody = null;
        }


        /**
         * All ok now send customer info to server
         */

        double editAmount = 100;
        if (editable.equals("0")) {
            double total = editAmount + Integer.parseInt(totalAmount);
            if (logoBody == null) {
                logoBody = null;
                updateData(String.valueOf(editAmount), String.valueOf(total), oldImage, logoBody);
                return;
            }
            updateData(String.valueOf(editAmount), String.valueOf(total), "", logoBody);
        }
        if (editable.equals("1")) {
            if (logoBody == null) {
                logoBody = null;
                updateData(String.valueOf(editAmount), totalAmount, oldImage, logoBody);
                return;
            }

            updateData(String.valueOf(editAmount), totalAmount, "", logoBody);

        }
        if (editable.equals("2")) {
            if (logoBody == null) {
                logoBody = null;
                updateData("", totalAmount, oldImage, logoBody);
                return;
            }
            updateData("", totalAmount, "", logoBody);
        }
    }

    private void updateData(String editAmount, String totalAmount, String oldImage, MultipartBody.Part newImage) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        customerViewModel.editCustomer(getActivity(), binding.companyName.getText().toString(),
                binding.ownerName.getText().toString(), binding.phone.getText().toString(), binding.altPhone.getText().toString(),
                binding.email.getText().toString(), "0", "0", "0", "0",
                "0", binding.tin.getText().toString(), dueLimit, countryId, "5", binding.address.getText().toString(),
                totalAmount, editAmount, newImage, oldImage, binding.note.getText().toString(), "0", customerId)
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    try {
                        if (response == null) {
                            errorMessage(getActivity().getApplication(), "ERROR");
                            return;
                        }
                        if (response.getStatus() == 400) {
                            infoMessage(getActivity().getApplication(), "" + response.getMessage());
                            return;
                        }
                        if (response.getStatus() == 200) {
                            successMessage(getActivity().getApplication(), "" + response.getMessage());
                            getActivity().onBackPressed();
                            return;
                        }
                    } catch (Exception e) {
                        progressDialog.dismiss();
                        Log.d("ERROR", e.getMessage());

                    }
                });
    }

    @SneakyThrows
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }

            InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
            imageUri = data.getData();

            //convertUriToBitmapImageAndSetInImageView(getPath(data.getData()), data.getData());
            /**
             * for set selected image in image view
             */
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
            binding.image.setImageDrawable(null);
            binding.image.setImageBitmap(bitmap);


            /**
             * now set licenseImageName
             * */
            binding.imageName.setText(String.valueOf(new File("" + data.getData()).getName()));

            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
            Log.d("LOGO_IMAGE", String.valueOf(inputStream));


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    infoMessage(requireActivity().getApplication(), "Permission Granted");
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    infoMessage(requireActivity().getApplication(), "Permission Decline");
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    public void updateForeignSupplierDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());

        @SuppressLint("InflateParams")
        View view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.purchase_dialog, null);
        //Set the view
        builder.setView(view);
        TextView tvTitle, tvMessage;
        ImageView imageIcon = view.findViewById(R.id.img_icon);
        tvMessage = view.findViewById(R.id.tv_message);
        tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText("Do you want to update ?");//set warning title
        tvMessage.setText("SALT ERP");
        imageIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.unicef_main));//set warning image
        Button bOk = view.findViewById(R.id.btn_ok);
        Button cancel = view.findViewById(R.id.cancel);
        android.app.AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        cancel.setOnClickListener(v -> alertDialog.dismiss());//for cancel
        bOk.setOnClickListener(v -> {
            alertDialog.dismiss();
            ValidationAndSubmit();
        });

        alertDialog.show();
    }

}