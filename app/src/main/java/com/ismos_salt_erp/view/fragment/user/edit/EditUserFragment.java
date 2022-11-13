package com.ismos_salt_erp.view.fragment.user.edit;

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
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import com.ismos_salt_erp.clickHandle.EditUserClickHandle;
import com.ismos_salt_erp.databinding.FragmentEditUserBinding;
import com.ismos_salt_erp.serverResponseModel.BloodGroup;
import com.ismos_salt_erp.serverResponseModel.DepartmentList;
import com.ismos_salt_erp.serverResponseModel.DesignationList;
import com.ismos_salt_erp.serverResponseModel.EnterpriseResponse;
import com.ismos_salt_erp.utils.PathUtil;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.SalesRequisitionViewModel;
import com.ismos_salt_erp.viewModel.UserViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class EditUserFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener {
    private FragmentEditUserBinding binding;
    private String selectedProfileId;
    private boolean isPasswordVisible = false; // variable to detect whether password is visible or not

    private UserViewModel userViewModel;
    private SalesRequisitionViewModel salesRequisitionViewModel;
    private boolean isJoiningDate = false;
    private static final int PICK_IMAGE = 200;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 300;
    private Uri imageUri;
    /**
     * For Department
     */
    private List<DepartmentList> departmentResponseLists;
    private List<String> departmentNameLists;

    /**
     * For DesignationList
     */
    private List<DesignationList> designationResponseLists;
    private List<String> designationNameList;

    /**
     * For Blood Group
     */
    private List<BloodGroup> bloodGroupResponseList;
    private List<String> bloodNameList;

    /**
     * For Title
     */
    List<String> titleList;
    /**
     * For Gender
     */
    List<String> genderList;
    /**
     * For Enterprise
     */
    private List<EnterpriseResponse> enterpriseResponseList;
    private List<String> enterpriseNameList;


    private String selectedTitle, selectedGender, selectedDepartment, selectedDesignation, selectedBloodGroup, selectedEnterprise;
    private String employee_vendorID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_user, container, false);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        salesRequisitionViewModel = new ViewModelProvider(this).get(SalesRequisitionViewModel.class);
        binding.toolbar.toolbarTitle.setText("Update Employee");
        setCurrentDateToView();
        getDataFromPreviousFragment();
        /**
         * now get Page Data From Server
         */
        getPageDataFromServer();
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        binding.setClickhandle(new EditUserClickHandle() {
            @Override
            public void joiningDate() {
                isJoiningDate = true;
                showDatePickerDialog();
            }

            @Override
            public void dateOfBirth() {
                showDatePickerDialog();
            }

            @Override
            public void getProfileImage() {
                if (!(checkStoragePermission())) {
                    requestStoragePermission(STORAGE_PERMISSION_REQUEST_CODE);
                } else {
                    getLogoImageFromFile(getActivity().getApplication(), PICK_IMAGE);
                }
            }

            @Override
            public void submit() {
                hideKeyboard(getActivity());
                if (!(isInternetOn(getActivity()))) {
                    infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
                    return;
                }
                if (selectedTitle == null) {
                    infoMessage(getActivity().getApplication(), "Please select title ");
                    return;
                }
                if (binding.fullName.getText().toString().isEmpty()) {
                    binding.fullName.setError("Empty");
                    binding.fullName.requestFocus();
                    return;
                }
                if (binding.displayName.getText().toString().isEmpty()) {
                    binding.displayName.setError("Empty");
                    binding.displayName.requestFocus();
                    return;
                }

                if (binding.primaryMobile.getText().toString().isEmpty()) {
                    binding.primaryMobile.setError("Empty");
                    binding.primaryMobile.requestFocus();
                    return;
                }
                if (!isValidPhone(binding.primaryMobile.getText().toString())) {
                    binding.primaryMobile.setError("Invalid Phone Number");
                    binding.primaryMobile.requestFocus();
                    return;
                }

                if (!binding.altMobile.getText().toString().isEmpty()) {
                    if (!isValidPhone(binding.altMobile.getText().toString())) {
                        binding.altMobile.setError("Invalid Phone Number");
                        binding.altMobile.requestFocus();
                        return;
                    }
                }

                if (selectedEnterprise == null) {
                    infoMessage(getActivity().getApplication(), "Please select enterprise");
                    return;
                }
                editUserDialog();
            }
        });
        /**
         * now handle on item select  on dropdown
         */
        binding.department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDepartment = departmentResponseLists.get(position).getDepartmentID();
                binding.department.setEnableErrorLabel(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.designation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDesignation = designationResponseLists.get(position).getUserDesignationId();
                binding.designation.setEnableErrorLabel(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.bloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBloodGroup = bloodGroupResponseList.get(position).getBloodGroupId();
                binding.bloodGroup.setEnableErrorLabel(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTitle = titleList.get(position);
                binding.title.setEnableErrorLabel(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGender = genderList.get(position);
                binding.gender.setEnableErrorLabel(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.enterprise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEnterprise = enterpriseResponseList.get(position).getStoreID();
                binding.enterprise.setEnableErrorLabel(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.passwordVisibilityImgBtn.setOnClickListener(v -> {
            if (isPasswordVisible) {
               binding.passwordVisibilityImgBtn.setImageResource(R.drawable.ic_visibility_off_grey_24dp);
// hide password
                binding.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                isPasswordVisible = false;
            } else {
                binding.passwordVisibilityImgBtn.setImageResource(R.drawable.ic_visibility_grey_24dp);
// show password
                binding.password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                isPasswordVisible = true;
            }
        });
        return binding.getRoot();
    }

    private void getDataFromPreviousFragment() {
        assert getArguments() != null;
        selectedProfileId = getArguments().getString("id");
    }

    private void showDatePickerDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Initial day selection
        );
        dialog.show(getActivity().getSupportFragmentManager(), "Datepickerdialog");
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int month = monthOfYear;
        if (month == 12) {
            month = 1;
        } else {
            month = monthOfYear + 1;
        }
        String mainMonth, mainDay;


        if (month <= 9) {
            mainMonth = "0" + month;
        } else {
            mainMonth = String.valueOf(month);
        }
        if (dayOfMonth <= 9) {
            mainDay = "0" + dayOfMonth;
        } else {
            mainDay = String.valueOf(dayOfMonth);
        }
        String selectedDate = year + "-" + mainMonth + "-" + mainDay;//set the selected date

        if (!isJoiningDate) {
            binding.dateOfBirth.setText(selectedDate);
            binding.dateOfBirth.setError(null);
            return;
        }
        binding.joiningDate.setText(selectedDate);
        binding.joiningDate.setError(null);

    }

    private void setCurrentDateToView() {
        /**
         * set current date will send input from user
         */
        binding.dateOfBirth.setText(getCustomCurrentDateTime("yyy/MM/dd"));
        binding.joiningDate.setText(getCustomCurrentDateTime("yyy/MM/dd"));
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

            InputStream inputStream = null;
            try {
                inputStream = getContext().getContentResolver().openInputStream(data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageUri = data.getData();

            //convertUriToBitmapImageAndSetInImageView(getPath(data.getData()), data.getData());
            /**
             * for set selected image in image view
             */
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
            binding.image.setImageDrawable(null);
            binding.image.setImageBitmap(bitmap);


            /**
             * now set profile Image
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


    private void getPageDataFromServer() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check your internet Connection");
            return;
        }
        try {
            titleList = new ArrayList<>();
            titleList.clear();
            genderList = new ArrayList<>();
            genderList.clear();
            titleList.addAll(Arrays.asList("Mr", "Mrs"));
            genderList.addAll(Arrays.asList("Male", "Female"));
            binding.title.setItem(titleList);
            binding.gender.setItem(genderList);

            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.show();
            userViewModel.getAddNewUserPageData(getActivity())
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
                         * For get previous selected data
                         */


                        userViewModel.getEditUserPageData(getActivity(), selectedProfileId)
                                .observe(getViewLifecycleOwner(), dataResponse -> {

                                    if (dataResponse == null) {
                                        errorMessage(getActivity().getApplication(), "Something Wrong");
                                        return;
                                    }

                                    if (dataResponse.getStatus() == 400) {
                                        infoMessage(getActivity().getApplication(), "backend Error");
                                        return;
                                    }

                                    try {
                                        employee_vendorID = dataResponse.getUserProfileInfos().getVendorID();//set current user vendorId

                                        binding.fullName.setText(dataResponse.getUserProfileInfos().getFullName());
                                        binding.displayName.setText(dataResponse.getUserProfileInfos().getDisplayName());
                                        if (dataResponse.getUserProfileInfos().getDateOfBirth() !=null){
                                            binding.dateOfBirth.setText(String.valueOf(dataResponse.getUserProfileInfos().getDateOfBirth()));
                                        }
                                        binding.email.setText(String.valueOf(dataResponse.getUserProfileInfos().getEmail()));
                                        if (dataResponse.getUserProfileInfos().getCreatedDate() !=null){
                                            binding.joiningDate.setText(String.valueOf(dataResponse.getUserProfileInfos().getCreatedDate()));
                                        }
                                        binding.primaryMobile.setText(String.valueOf(dataResponse.getUserProfileInfos().getPrimaryMobile()));
                                        if (dataResponse.getUserProfileInfos().getNationality() != null) {
                                            binding.nationality.setText(String.valueOf(dataResponse.getUserProfileInfos().getNationality()));

                                        }
                                        if (dataResponse.getUserProfileInfos().getAlternativeEmail() != null) {
                                            binding.altEmail.setText(String.valueOf(dataResponse.getUserProfileInfos().getAlternativeEmail()));
                                        }
                                        if (dataResponse.getUserProfileInfos().getOtherContactNumbers() != null) {
                                            binding.altMobile.setText(String.valueOf(dataResponse.getUserProfileInfos().getOtherContactNumbers()));
                                        }
                                        if (dataResponse.getUserProfileInfos().getWebsite() != null) {
                                            binding.website.setText(String.valueOf(dataResponse.getUserProfileInfos().getWebsite()));

                                        }
                                        if (dataResponse.getUserProfileInfos().getAbout() != null) {
                                            binding.description.setText(String.valueOf(dataResponse.getUserProfileInfos().getAbout()));
                                        }

                                        progressDialog.dismiss();
                                    } catch (Exception e) {
                                        Log.d("Error", e.getMessage());
                                        progressDialog.dismiss();
                                    }
                                    /**
                                     * now set profile photo
                                     */
                                    try {
                                        Glide.with(getContext())
                                                .load(dataResponse.getUserProfileInfos().getProfilePhoto())
                                                .centerCrop()
                                                .placeholder(R.drawable.error_one)
                                                .error(R.drawable.error_one)
                                                .into(binding.image);
                                        progressDialog.dismiss();

                                    } catch (Exception e) {
                                        Log.d("Error", e.getMessage());
                                        progressDialog.dismiss();
                                    }
                                    try {
                                        /**
                                         * now set department, designation_lists and blood group
                                         */
                                        departmentResponseLists = new ArrayList<>();
                                        departmentResponseLists.clear();
                                        departmentNameLists = new ArrayList<>();
                                        departmentNameLists.clear();
                                        departmentResponseLists.addAll(response.getDepartmentLists());
                                        for (int i = 0; i < response.getDepartmentLists().size(); i++) {
                                            departmentNameLists.add("" + response.getDepartmentLists().get(i).getDepartmentName());
                                        }

                                        binding.department.setItem(departmentNameLists);
                                        /**
                                         * set previous selected DepartmentName
                                         */
                                        for (int i = 0; i < departmentResponseLists.size(); i++) {
                                            if (departmentResponseLists.get(i).getDepartmentID() != null) {
                                                if (departmentResponseLists.get(i).getDepartmentID().equals(dataResponse.getUserProfileInfos().getDepartmentID())) {
                                                    binding.department.setSelection(i);
                                                    selectedDepartment = dataResponse.getUserProfileInfos().getDepartmentID();
                                                    break;
                                                }
                                            }
                                        }


                                        /**
                                         * designation_lists
                                         */
                                        designationResponseLists = new ArrayList<>();
                                        designationResponseLists.clear();
                                        designationNameList = new ArrayList<>();
                                        designationNameList.clear();
                                        designationResponseLists.addAll(response.getDesignationLists());
                                        for (int i = 0; i < response.getDesignationLists().size(); i++) {
                                            designationNameList.add("" + response.getDesignationLists().get(i).getDesignationName());
                                        }
                                        binding.designation.setItem(designationNameList);
                                        /**
                                         * set previous selected designation
                                         */
                                        for (int i = 0; i < designationResponseLists.size(); i++) {
                                            if (designationResponseLists.get(i).getUserDesignationId() != null) {
                                                if (designationResponseLists.get(i).getUserDesignationId().equals(dataResponse.getUserProfileInfos().getUserDesignationId())) {
                                                    binding.designation.setSelection(i);
                                                    selectedDesignation = dataResponse.getUserProfileInfos().getUserDesignationId();
                                                    break;
                                                }
                                            }
                                        }

                                        /**
                                         * blood group
                                         */
                                        bloodGroupResponseList = new ArrayList<>();
                                        bloodGroupResponseList.clear();
                                        bloodNameList = new ArrayList<>();
                                        bloodNameList.clear();
                                        bloodGroupResponseList.addAll(response.getBloodGroups());
                                        for (int i = 0; i < response.getBloodGroups().size(); i++) {
                                            bloodNameList.add("" + response.getBloodGroups().get(i).getGruopName());
                                        }
                                        binding.bloodGroup.setItem(bloodNameList);
                                        /**
                                         * set Previous selected blood group
                                         */
                                        for (int i = 0; i < bloodGroupResponseList.size(); i++) {
                                            if (dataResponse.getUserProfileInfos().getBloodGroup() != null) {
                                                if (bloodGroupResponseList.get(i).getBloodGroupId().equals(String.valueOf(dataResponse.getUserProfileInfos().getBloodGroup()))) {
                                                    binding.bloodGroup.setSelection(i);
                                                    selectedBloodGroup = String.valueOf(dataResponse.getUserProfileInfos().getBloodGroup());
                                                    break;
                                                }
                                            }

                                        }
                                    } catch (Exception e) {
                                        Log.d("Error", e.getMessage());
                                    }

                                    /**
                                     * now set enterprise
                                     */
                                    salesRequisitionViewModel.getEnterpriseResponse(getActivity())
                                            .observe(getViewLifecycleOwner(), enterpriseResponse -> {
                                                if (enterpriseResponse == null) {
                                                    errorMessage(getActivity().getApplication(), "Something Wrong");
                                                    return;
                                                }
                                                if (enterpriseResponse.getStatus() == 400) {
                                                    infoMessage(getActivity().getApplication(), "Something Wrong");
                                                    return;
                                                }

                                                enterpriseResponseList = new ArrayList<>();
                                                enterpriseResponseList.clear();
                                                enterpriseNameList = new ArrayList<>();
                                                enterpriseResponseList.clear();
                                                enterpriseResponseList.addAll(enterpriseResponse.getEnterprise());
                                                for (int i = 0; i < enterpriseResponse.getEnterprise().size(); i++) {
                                                    enterpriseNameList.add("" + enterpriseResponse.getEnterprise().get(i).getStoreName());
                                                }
                                                binding.enterprise.setItem(enterpriseNameList);

                                                /**
                                                 * now set previous selected enterprise
                                                 */
                                                try {
                                                    for (int i = 0; i < enterpriseResponseList.size(); i++) {
                                                        if (enterpriseResponseList.get(i).getStoreID().equals(dataResponse.getUserProfileInfos().getStoreID())) {
                                                            {
                                                                binding.enterprise.setSelection(i);
                                                                selectedEnterprise = enterpriseResponseList.get(i).getStoreID();
                                                                break;
                                                            }
                                                        }

                                                    }

                                                } catch (Exception e) {
                                                    Log.d("ERROR", e.getMessage());
                                                }
                                            });


                                    try {
                                        /**
                                         * now set previous selected others field
                                         */

                                        /**
                                         * for title
                                         */
                                        for (int i = 0; i < titleList.size(); i++) {
                                            if (titleList.get(i).equals(dataResponse.getUserProfileInfos().getTitle())) {
                                                binding.title.setSelection(i);
                                                selectedTitle = dataResponse.getUserProfileInfos().getTitle();
                                                break;
                                            }
                                        }
                                        /**
                                         * for gender
                                         */
                                        for (int i = 0; i < genderList.size(); i++) {
                                            if (dataResponse.getUserProfileInfos().getGender() != null) {
                                                if (genderList.get(i).equals(dataResponse.getUserProfileInfos().getGender())) {
                                                    binding.gender.setSelection(i);
                                                    selectedGender = dataResponse.getUserProfileInfos().getGender();
                                                    break;
                                                }
                                            }

                                        }
                                    } catch (Exception e) {
                                        Log.d("Error", e.getMessage());
                                    }

                                });

                    });


        } catch (Exception e) {
            Log.d("ERROr", e.getMessage());
        }
    }

    private void validationAndSubmit() {
        /**
         * for Image
         */

        MultipartBody.Part logoBody;
        if (imageUri != null) {//logo image not mandatory here so if user not select any logo image by default it send null
            File file = null;
            try {
                file = new File(PathUtil.getPath(getActivity(), imageUri));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

            // MultipartBody.Part is used to send also the actual file name
            logoBody =
                    MultipartBody.Part.createFormData("user_photo", file.getName(), requestFile);//here user_photo is name of from data
        } else {
            logoBody = null;
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        userViewModel.submitEditUserInfo(
                getActivity(), selectedProfileId, employee_vendorID, selectedDesignation, selectedDepartment, selectedTitle, binding.displayName.getText().toString(),
                binding.fullName.getText().toString(), selectedGender, binding.primaryMobile.getText().toString(),
                binding.email.getText().toString(), selectedEnterprise, binding.description.getText().toString(),
                binding.dateOfBirth.getText().toString(), selectedBloodGroup, binding.nationality.getText().toString(),
                binding.altEmail.getText().toString(), binding.altMobile.getText().toString(),
                binding.website.getText().toString(), binding.joiningDate.getText().toString(), logoBody
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


    public void editUserDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());

        @SuppressLint("InflateParams")
        View view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.purchase_dialog, null);
        //Set the view
        builder.setView(view);
        TextView tvTitle, tvMessage;
        ImageView imageIcon = view.findViewById(R.id.img_icon);
        tvMessage = view.findViewById(R.id.tv_message);
        tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText("Do you want to update user info?");//set warning title
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
            validationAndSubmit();
        });

        alertDialog.show();
    }


}