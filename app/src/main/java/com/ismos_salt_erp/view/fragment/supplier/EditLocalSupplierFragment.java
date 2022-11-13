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
import com.ismos_salt_erp.databinding.FragmentEditLocalSupplierBinding;
import com.ismos_salt_erp.serverResponseModel.DistrictListResponse;
import com.ismos_salt_erp.serverResponseModel.DivisionResponse;
import com.ismos_salt_erp.serverResponseModel.ThanaList;
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


public class EditLocalSupplierFragment extends BaseFragment {
    private FragmentEditLocalSupplierBinding binding;
    private MillerProfileInfoViewModel millerProfileInfoViewModel;
    private CustomerViewModel customerViewModel;

    private static final int PICK_IMAGE = 200;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 300;
    private Uri imageUri;

    /**
     * For Division
     */
    private List<DivisionResponse> divisionResponseList;
    private List<String> divisionNameList;
    /**
     * For District
     */
    private List<DistrictListResponse> districtListResponseList;
    private List<String> districtNameList;
    /**
     * For Thana
     */
    private List<ThanaList> thanaListsResponse;
    private List<String> thanaNameList;
    /**
     * For Selected Customer
     */
    private List<String> selectedCustomerTypeName;
    private List<String> selectedCustomerTypeIdList;

    private String editable, dueLimit, country, oldImage, type, totalAmount, selectedDistrict, selectedDivision, selectedThana, customerId, selectedCustomerType;
    MultipartBody.Part logoBody;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_local_supplier, container, false);
        millerProfileInfoViewModel = new ViewModelProvider(this).get(MillerProfileInfoViewModel.class);
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        binding.toolbar.setClickHandle(new ToolbarClickHandle() {
            @Override
            public void backBtn() {
                getActivity().onBackPressed();
            }
        });
        binding.toolbar.toolbarTitle.setText("Update Local Supplier");
        /**
         * get previous fragment data
         */
        previousfragmentData();
        /**
         * get page data
         */

        getPageDataFromServer();


        /**
         * Now handle division item click
         */
        binding.division.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedDivision = divisionResponseList.get(position).getDivisionId();
                binding.division.setEnableErrorLabel(false);
                /**
                 * Now get district list based on division id
                 */
                getDistrictListByDivisionId(selectedDivision);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /**
         * Nw handle district item click
         */
        binding.district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedDistrict = districtListResponseList.get(position).getDistrictId();
                binding.district.setEnableErrorLabel(false);
                /**
                 * Now get Thana list based on district
                 */
                getThanaListByDistrictId(selectedDistrict);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * now handle Thana item click
         */
        binding.thana.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedThana = thanaListsResponse.get(position).getUpazilaId();
                binding.thana.setEnableErrorLabel(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /**
         * now handle selected customer item click
         */

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
        binding.updateCustomer.setOnClickListener(v -> {
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


            if (selectedDivision == null) {
                infoMessage(getActivity().getApplication(), "Please select division");
                return;
            }
            if (selectedDistrict == null) {
                infoMessage(getActivity().getApplication(), "Please select district");
                return;
            }

            if (selectedThana == null) {
                infoMessage(getActivity().getApplication(), "Please select upazila/thana");

                return;
            }
            if (selectedCustomerType == null) {
                infoMessage(getActivity().getApplication(), "Please select supplier type");
                return;
            }
            hideKeyboard(getActivity());
            if (!(isInternetOn(getActivity()))) {
                infoMessage(getActivity().getApplication(), "Please check your internet connection");
                return;
            }
            updateLocalSupplierDialog();
        });

        return binding.getRoot();
    }

    private void previousfragmentData() {
        if (!getArguments().isEmpty()) {
            customerId = getArguments().getString("customerId");
        }
    }

    private void getPageDataFromServer() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        /**
         * For get Division list from This Api
         */
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        millerProfileInfoViewModel.getProfileInfoResponse(getActivity())
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }

                    /**
                     * now set division list
                     */
                    divisionResponseList = new ArrayList<>();
                    divisionResponseList.clear();
                    divisionResponseList.addAll(response.getDivisions());
                    divisionNameList = new ArrayList<>();
                    divisionNameList.clear();
/*
                    binding.division.setItem(divisionNameList);
*/

                });

        /**
         * now set selected customer
         */
        selectedCustomerTypeName = new ArrayList<>();
        selectedCustomerTypeName.clear();
        selectedCustomerTypeIdList = new ArrayList<>();
        selectedCustomerTypeIdList.clear();

        selectedCustomerTypeName.addAll(Arrays.asList("General"));
        selectedCustomerTypeIdList.addAll(Arrays.asList("1"));
        binding.customerType.setItem(selectedCustomerTypeName);

        ProgressDialog progressDialog1 = new ProgressDialog(getContext());
        progressDialog1.show();
        customerViewModel.customerEditResponse(getActivity(), customerId, "1").observe(getViewLifecycleOwner(),
                response -> {
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
                        if (editable.equals("2")){//
                            binding.initialAmount.setFocusable(false);
                         }
                        totalAmount = response.getInitialPaymentInfo().getTotalAmount();
                        dueLimit = response.getCustomerInfo().getDueLimit();
                        country = response.getCustomerInfo().getCountry();
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
                        binding.bazar.setText(response.getCustomerInfo().getBazar());
                        binding.nid.setText(response.getCustomerInfo().getNid());
                        binding.tin.setText(response.getCustomerInfo().getTin());
                        binding.address.setText(response.getCustomerInfo().getAddress());
                        binding.initialAmount.setText(response.getInitialPaymentInfo().getTotalAmount());
                        binding.note.setText(response.getCustomerInfo().getCustomerNote());
                        try {
                            Glide.with(getContext()).load(ImageBaseUrl.image_base_url + response.getCustomerInfo().getImage()).centerCrop().
                                    error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).
                                    into(binding.image);

                        } catch (NullPointerException e) {
                            Log.d("ERROR", e.getMessage());
                            Glide.with(getContext()).load(R.mipmap.ic_launcher).into(binding.image);
                        }


                    } catch (Exception e) {
                        Log.d("Error", e.getMessage());
                        progressDialog.dismiss();
                    }
                    if (selectedDivision == null) {
                        selectedDivision = response.getCustomerInfo().getDivision();

                        try {
                            for (int i = 0; i < divisionResponseList.size(); i++) {
                                if (divisionResponseList.get(i).getDivisionId().equals(selectedDivision)) {
                                    binding.division.setSelection(i);
                                }
                                divisionNameList.add(divisionResponseList.get(i).getName());
                            }
                            binding.division.setItem(divisionNameList);
                        } catch (Exception e) {
                            Log.d("ERROR", "" + e.getMessage());
                        }

                    }
                    /**
                     * set previous division
                     */

                    if (selectedDistrict == null) {
                        selectedDistrict = response.getCustomerInfo().getDistrict();
                    }

                    if (selectedThana == null) {
                        selectedThana = response.getCustomerInfo().getThana();
                    }

                    getDistrictListByDivisionId(selectedDivision);
                    getThanaListByDistrictId(selectedDistrict);

                    try {
                        if (selectedCustomerType == null) {
                            selectedCustomerType = response.getCustomerInfo().getTypeID();
                            for (int i = 0; i < selectedCustomerTypeIdList.size(); i++) {
                                if (response.getCustomerInfo().getTypeID().equals(selectedCustomerType)) {
                                    binding.customerType.setSelection(i);
                                }
                                break;
                            }
                        }
                    } catch (Exception e) {
                        Log.d("Error", e.getMessage());
                        progressDialog.dismiss();
                    }

                });
        progressDialog1.dismiss();

    }

    private void getDistrictListByDivisionId(String selectedDivision) {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }

        millerProfileInfoViewModel.getDistrictListByDivisionId(getActivity(), selectedDivision)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        return;
                    }
                    if (response.getStatus() != 200) {
                        return;
                    }
                    districtListResponseList = new ArrayList<>();
                    districtListResponseList.clear();
                    districtNameList = new ArrayList<>();
                    districtNameList.clear();
                    districtListResponseList.addAll(response.getLists());

                    for (int i = 0; i < response.getLists().size(); i++) {
                        /**
                         * set previous district
                         */
                        if (selectedDistrict != null) {
                            if (response.getLists().get(i).getDistrictId().equals(selectedDistrict)) {
                                binding.district.setSelection(i);

                            }
                        }
                        districtNameList.add(response.getLists().get(i).getName());
                    }
                    binding.district.setItem(districtNameList);
                });
    }

    private void getThanaListByDistrictId(String selectedDistrict) {
        millerProfileInfoViewModel.getThanaListByDistrictId(getActivity(), selectedDistrict)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        return;
                    }
                    if (response.getStatus() != 200) {
                        return;
                    }
                    thanaListsResponse = new ArrayList<>();
                    thanaListsResponse.clear();
                    thanaNameList = new ArrayList<>();
                    thanaNameList.clear();

                    thanaListsResponse.addAll(response.getLists());
                    for (int i = 0; i < response.getLists().size(); i++) {

                        /**
                         * set previous thana
                         */
                        if (selectedThana != null) {
                            if (response.getLists().get(i).getUpazilaId().equals(selectedThana)) {
                                binding.thana.setSelection(i);


                            }
                        }
                        thanaNameList.add(response.getLists().get(i).getName());
                    }
                    binding.thana.setItem(thanaNameList);
                });
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

        double editAmount = 0;
        if (!binding.initialAmount.getText().toString().isEmpty()){
            editAmount =  Double.parseDouble(binding.initialAmount.getText().toString());
        }
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
        customerViewModel.editCustomer(getActivity(), binding.companyName.getText().toString(),
                binding.ownerName.getText().toString(), binding.phone.getText().toString(), binding.altPhone.getText().toString(),
                binding.email.getText().toString(), selectedDivision, selectedDistrict, selectedThana, binding.bazar.getText().toString(),
                binding.nid.getText().toString(), binding.tin.getText().toString(), dueLimit, country, "1", binding.address.getText().toString(),
                totalAmount, editAmount, newImage, oldImage, binding.note.getText().toString(), binding.editNote.getText().toString(), customerId)
                .observe(getViewLifecycleOwner(), response -> {
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


    public void updateLocalSupplierDialog() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());

        @SuppressLint("InflateParams")
        View view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.purchase_dialog, null);
        //Set the view
        builder.setView(view);
        TextView tvTitle, tvMessage;
        ImageView imageIcon = view.findViewById(R.id.img_icon);
        tvMessage = view.findViewById(R.id.tv_message);
        tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText("Do you want to Update ?");//set warning title
        tvMessage.setText("SALT ERP");
        imageIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.app_sub_logo));//set warning image
        Button bOk = view.findViewById(R.id.btn_ok);
        Button cancel = view.findViewById(R.id.cancel);
        android.app.AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        cancel.setOnClickListener(v -> alertDialog.dismiss());//for cancel
        bOk.setOnClickListener(v -> {
            alertDialog.dismiss();
            ValidationAndSubmit();        });

        alertDialog.show();


    }


}