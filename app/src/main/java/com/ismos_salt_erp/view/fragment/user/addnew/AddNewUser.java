package com.ismos_salt_erp.view.fragment.user.addnew;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.clickHandle.AddNewUserClickHandle;
import com.ismos_salt_erp.databinding.FragmentAddNewUserBinding;
import com.ismos_salt_erp.databinding.NumberVerificationDialogBinding;
import com.ismos_salt_erp.serverResponseModel.BloodGroup;
import com.ismos_salt_erp.serverResponseModel.DepartmentList;
import com.ismos_salt_erp.serverResponseModel.DesignationList;
import com.ismos_salt_erp.serverResponseModel.EnterpriseResponse;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.viewModel.AddNewUserViewModel;
import com.ismos_salt_erp.viewModel.SalesRequisitionViewModel;
import com.ismos_salt_erp.viewModel.UserViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.MultipartBody;

public class AddNewUser extends AddUpDel implements DatePickerDialog.OnDateSetListener, SmartMaterialSpinner.OnItemSelectedListener {
    private FragmentAddNewUserBinding binding;
    private boolean isPasswordVisible = false; // variable to detect whether password is visible or not
    private UserViewModel userViewModel;
    private SalesRequisitionViewModel salesRequisitionViewModel;
    private AddNewUserViewModel addNewUserViewModel;
    private boolean isJoiningDate = false;
    MultipartBody.Part image = null;
    private Boolean isVerifiedPhoneNumber = false;
    private List<DepartmentList> departmentResponseLists;
    private List<DesignationList> designationResponseLists;
    private List<BloodGroup> bloodGroupResponseList;
    List<String> titleList;

    List<String> genderList;
    private List<EnterpriseResponse> enterpriseResponseList;
    Set<Integer> selectedStoreAccessList;
    private String selectedTitle, selectedGender, selectedDepartment, selectedDesignation, selectedBloodGroup, selectedEnterprise;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_user, container, false);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        salesRequisitionViewModel = new ViewModelProvider(this).get(SalesRequisitionViewModel.class);
        addNewUserViewModel = new ViewModelProvider(this).get(AddNewUserViewModel.class);
        binding.toolbar.toolbarTitle.setText("Add New Employee");
        setCurrentDateToView();
        getPageDataFromServer();
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        binding.setClickhandle(new AddNewUserClickHandle() {
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
               forImage();
            }

            @Override
            public void submit() {
                try {
                    if (selectedTitle == null) {
                        infoMessage(getActivity().getApplication(), "Please select title");
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

                    if (!binding.email.getText().toString().isEmpty()) {
                        if (!isValidEmail(binding.email.getText().toString())) {
                            binding.email.setError("Invalid Email");
                            binding.email.requestFocus();
                            return;
                        }
                    }

                    if (binding.password.getText().toString().isEmpty()) {
                        binding.password.setError("Empty");
                        binding.password.requestFocus();
                        return;
                    }
                    if (binding.password.getText().toString().length() < 6) {
                        binding.password.setError("Password length should be 6 or more");
                        binding.password.requestFocus();
                        return;
                    }

                    if (!(binding.password.getText().toString().equals(binding.password1.getText().toString()))) {
                        binding.password1.requestFocus();
                        binding.password1.requestFocus();
                        infoMessage(getActivity().getApplication(), "Invalid Password");
                        return;
                    }

                    if (selectedEnterprise == null) {
                        infoMessage(requireActivity().getApplication(), "Please select enterprise");
                        return;
                    }

                    hideKeyboard(getActivity());
                    if (!(isInternetOn(getActivity()))) {
                        infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
                        return;
                    }
                } catch (Exception e) {
                }
                showDialog("Do you want to add employee ?");
            }
        });

        binding.designation.setOnItemSelectedListener(this);
        binding.bloodGroup.setOnItemSelectedListener(this);
        binding.title.setOnItemSelectedListener(this);
        binding.gender.setOnItemSelectedListener(this);
        binding.department.setOnItemSelectedListener(this);
        binding.enterprise.setOnItemSelectedListener(this);

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
        binding.storeAccess.addOnItemsSelectedListener((list, booleans) -> {
            selectedStoreAccessList = new HashSet<>();
            selectedStoreAccessList.clear();
            for (int i = 0; i < enterpriseResponseList.size(); i++) {
                if (list.contains(enterpriseResponseList.get(i).getStoreName())) {
                    selectedStoreAccessList.add(Integer.parseInt(enterpriseResponseList.get(i).getStoreID()));
                }

            }
        });

        binding.verificationBtn.setOnClickListener(v -> {

            if (binding.primaryMobile.getText().toString().isEmpty()) {
                binding.primaryMobile.setError("Empty");
                binding.primaryMobile.requestFocus();
                return;
            }
            if (!binding.primaryMobile.getText().toString().isEmpty()) {
                if (!isValidPhone(binding.primaryMobile.getText().toString())) {
                    binding.primaryMobile.setError("Invalid Mobile Number");
                    binding.primaryMobile.requestFocus();
                    return;
                }
            }
            if (!(isInternetOn(getActivity()))) {
                infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
                return;
            }

            addNewUserViewModel.sendPhoneNumberForGetOtp(getActivity().getApplication(), binding.primaryMobile.getText().toString().trim())
                    .observe(getViewLifecycleOwner(), successResponse -> {
                        try {
                            if (successResponse == null) {
                                errorMessage(getActivity().getApplication(), "Something Wrong");
                                return;
                            }
                            if (successResponse.getStatus() == 400) {
                                infoMessage(getActivity().getApplication(), "" + successResponse.getMessage());
                                return;
                            }
                            if (successResponse.getStatus() < 400) {
                                /**
                                 * Open a dialog here for verification this number
                                 */
                                sendPrimaryPhoneNumberForVerification();
                            }
                        } catch (Exception e) {
                            Log.d("ERROR", e.getMessage());
                        }
                    });


        });
        return binding.getRoot();
    }


    public void sendPrimaryPhoneNumberForVerification() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        NumberVerificationDialogBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.number_verification_dialog, null, false);
        builder.setView(dialogBinding.getRoot());
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);


        dialogBinding.submitBtn.setOnClickListener(v -> {
            hideKeyboard(getActivity());

            if (dialogBinding.otpField.getText().toString().isEmpty()) {
                dialogBinding.otpField.setError("Empty");
                dialogBinding.otpField.requestFocus();
                return;
            }
            if (dialogBinding.otpField.getText().toString().length() < 4 || dialogBinding.otpField.getText().toString().length() > 4) {
                dialogBinding.otpField.setError("Otp length should be 4");
                dialogBinding.otpField.requestFocus();
                return;
            }
            if (!(isInternetOn(getActivity()))) {
                infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
                return;
            }
            /**
             * After all call the send phone number api for get verification Otp code
             */
            addNewUserViewModel.sendOTPCode(getActivity().getApplication(), binding.primaryMobile.getText().toString().trim(), dialogBinding.otpField.getText().toString().trim())
                    .observe(getViewLifecycleOwner(), successResponse -> {
                        try {
                            if (successResponse == null) {
                                errorMessage(getActivity().getApplication(), "Something Wrong");
                                return;
                            }
                            if (successResponse.getStatus() == 400) {
                                infoMessage(getActivity().getApplication(), "" + successResponse.getMessage());
                                isVerifiedPhoneNumber = false;
                                return;
                            }
                            if (successResponse.getStatus() < 400) {
                                isVerifiedPhoneNumber = true;
                                alertDialog.dismiss();
                                successMessage(requireActivity().getApplication(), "" + successResponse.getMessage());
                                return;
                            }
                            isVerifiedPhoneNumber = false;
                            binding.primaryMobile.setError(null);
                        } catch (Exception e) {
                            Log.d("ERROR", e.getMessage());
                        }
                    });


        });

        dialogBinding.cancenBtn.setOnClickListener(v -> {
            hideKeyboard(getActivity());
            alertDialog.dismiss();
        });


        alertDialog.show();
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
        } else {
            binding.joiningDate.setText(selectedDate);
            binding.joiningDate.setError(null);
        }
    }

    private void setCurrentDateToView() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(System.currentTimeMillis());
        String currentDate = formatter.format(date);
        binding.dateOfBirth.setText(currentDate);
        binding.joiningDate.setText(currentDate);
    }


    private void getPageDataFromServer() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check your internet Connection");
            return;
        }
        titleList = new ArrayList<>();
        titleList.clear();
        genderList = new ArrayList<>();
        genderList.clear();
        titleList.addAll(Arrays.asList("Mr", "Mrs"));
        genderList.addAll(Arrays.asList("Male", "Female"));
        binding.title.setItem(titleList);
        binding.gender.setItem(genderList);


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

                    departmentResponseLists = new ArrayList<>();
                    departmentResponseLists.clear();
                    List<String> departmentNameLists = new ArrayList<>();
                    departmentNameLists.clear();
                    departmentResponseLists.addAll(response.getDepartmentLists());
                    for (int i = 0; i < response.getDepartmentLists().size(); i++) {
                        departmentNameLists.add(response.getDepartmentLists().get(i).getDepartmentName());
                    }

                    binding.department.setItem(departmentNameLists);
                    designationResponseLists = new ArrayList<>();
                    designationResponseLists.clear();
                    List<String> designationNameList;
                    designationNameList = new ArrayList<>();
                    designationNameList.clear();
                    designationResponseLists.addAll(response.getDesignationLists());
                    for (int i = 0; i < response.getDesignationLists().size(); i++) {
                        designationNameList.add(response.getDesignationLists().get(i).getDesignationName());
                    }
                    binding.designation.setItem(designationNameList);
                    /**
                     * blood group
                     */
                    bloodGroupResponseList = new ArrayList<>();
                    bloodGroupResponseList.clear();
                    List<String> bloodNameList;
                    bloodNameList = new ArrayList<>();
                    bloodNameList.clear();
                    bloodGroupResponseList.addAll(response.getBloodGroups());
                    for (int i = 0; i < response.getBloodGroups().size(); i++) {
                        bloodNameList.add(response.getBloodGroups().get(i).getGruopName());
                    }
                    binding.bloodGroup.setItem(bloodNameList);

                });

        salesRequisitionViewModel.forGetUserEnterprise(getActivity())
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }

                    enterpriseResponseList = new ArrayList<>();
                    enterpriseResponseList.clear();
                    List<String> enterpriseNameList = new ArrayList<>();
                    enterpriseResponseList.clear();
                    enterpriseResponseList.addAll(response.getEnterprise());
                    for (int i = 0; i < response.getEnterprise().size(); i++) {
                        enterpriseNameList.add("" + response.getEnterprise().get(i).getStoreName());
                    }
                    binding.enterprise.setItem(enterpriseNameList);
                    if (enterpriseResponseList.isEmpty()) {
                        return;
                    }
                    binding.storeAccess.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                    binding.storeAccess.setSpinnerList(enterpriseNameList);
                });

    }

    private void submit() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        userViewModel.submitAddNewUserInfo(
                getActivity(), selectedDesignation, selectedDepartment, selectedTitle, binding.displayName.getText().toString(),
                binding.fullName.getText().toString(), selectedGender, binding.primaryMobile.getText().toString(), binding.email.getText().toString(),
                selectedEnterprise, binding.description.getText().toString(), binding.dateOfBirth.getText().toString(),
                selectedBloodGroup, binding.nationality.getText().toString(), binding.altEmail.getText().toString(), binding.altMobile.getText().toString(),
                binding.website.getText().toString(), binding.joiningDate.getText().toString(), image,
                binding.password.getText().toString(), selectedStoreAccessList
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
    public void save() {
        submit();
    }

    @Override
    public void imageUri(Intent data) {
        binding.image.setImageBitmap(getBitmapImage(data));
        binding.imageName.setText(new File("" + data.getData()).getName());
        image = imageLogobody(data.getData(), "");

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.department) {
            selectedDepartment = departmentResponseLists.get(position).getDepartmentID();

        }
        if (parent.getId() == R.id.designation) {
            selectedDesignation = designationResponseLists.get(position).getUserDesignationId();

        }
        if (parent.getId() == R.id.bloodGroup) {
            selectedBloodGroup = bloodGroupResponseList.get(position).getBloodGroupId();

        }
        if (parent.getId() == R.id.title) {
            selectedTitle = titleList.get(position);

        }
        if (parent.getId() == R.id.gender) {
            selectedGender = genderList.get(position);

        }

        if (parent.getId() == R.id.enterprise) {
            selectedEnterprise = enterpriseResponseList.get(position).getStoreID();

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}