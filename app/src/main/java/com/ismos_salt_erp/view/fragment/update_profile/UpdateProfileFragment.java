package com.ismos_salt_erp.view.fragment.update_profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.clickHandle.EditUserClickHandle;
import com.ismos_salt_erp.databinding.FragmentUpdateProfile1Binding;
import com.ismos_salt_erp.date_time_picker.DateTimePicker;
import com.ismos_salt_erp.serverResponseModel.BloodGroup;
import com.ismos_salt_erp.serverResponseModel.DepartmentList;
import com.ismos_salt_erp.serverResponseModel.DesignationList;
import com.ismos_salt_erp.serverResponseModel.EnterpriseResponse;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.viewModel.SalesRequisitionViewModel;
import com.ismos_salt_erp.viewModel.UserViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import okhttp3.MultipartBody;

public class UpdateProfileFragment extends AddUpDel implements DatePickerDialog.OnDateSetListener {
    FragmentUpdateProfile1Binding binding;

    private String selectedProfileId;

    private UserViewModel userViewModel;
    private SalesRequisitionViewModel salesRequisitionViewModel;
    private boolean isJoiningDate = false;

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

    Set<Integer> selectedStoreAccessList;


    private String selectedTitle, selectedGender, selectedDepartment, selectedDesignation, selectedBloodGroup, selectedEnterprise;
    private String employee_vendorID,joyiningDate;
    MultipartBody.Part image = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update_profile1, container, false);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        salesRequisitionViewModel = new ViewModelProvider(this).get(SalesRequisitionViewModel.class);
        binding.toolbar.toolbarTitle.setText("Profile Update");

            setCurrentDateToView();
            getDataFromPreviousFragment();

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
              forImage();
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
                if (binding.email.getText().toString().isEmpty()) {
                    binding.email.setError("Empty");
                    binding.email.requestFocus();
                    return;
                }
                if (!isValidEmail(binding.email.getText().toString())) {
                    binding.email.setError("Invalid Email");
                    binding.email.requestFocus();
                    return;
                }
                if (!binding.altEmail.getText().toString().isEmpty()) {
                    if (!isValidEmail(binding.altEmail.getText().toString())) {
                        binding.altEmail.setError("Invalid Email");
                        binding.altEmail.requestFocus();
                        return;
                    }
                }

           showDialog(getString(R.string.update_dialog_title));
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


        return binding.getRoot();
    }

    private void getDataFromPreviousFragment() {
    /*    assert getArguments() != null;
        selectedProfileId = getArguments().getString("id");
  */
    }

    private void showDatePickerDialog() {
        DateTimePicker.openDatePicker(this,getActivity());
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        binding.dateOfBirth.setText(DateTimePicker.dateSelect(year,monthOfYear,dayOfMonth));


    }

    private void setCurrentDateToView() {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(System.currentTimeMillis());
        String currentDate = formatter.format(date);
        binding.dateOfBirth.setText(currentDate);
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
                        userViewModel.getEditUserPageData(getActivity(), getProfileId(getActivity().getApplication()))
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
                                        joyiningDate = String.valueOf(dataResponse.getUserProfileInfos().getCreatedDate());
                                        if (dataResponse.getUserProfileInfos().getFullName() != null) {
                                            binding.fullName.setText(dataResponse.getUserProfileInfos().getFullName());

                                        }
                                        if (dataResponse.getUserProfileInfos().getDisplayName() != null) {
                                            binding.displayName.setText(dataResponse.getUserProfileInfos().getDisplayName());

                                        }
                                        if (dataResponse.getUserProfileInfos().getDateOfBirth() != null) {
                                            binding.dateOfBirth.setText(String.valueOf(dataResponse.getUserProfileInfos().getDateOfBirth()));
                                        }
                                        if (dataResponse.getUserProfileInfos().getEmail() != null) {
                                            binding.email.setText(String.valueOf(dataResponse.getUserProfileInfos().getEmail()));
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


                                    selectedDepartment = dataResponse.getUserProfileInfos().getDepartmentID();
                                    selectedDesignation = dataResponse.getUserProfileInfos().getUserDesignationId();
                                    selectedBloodGroup = String.valueOf(dataResponse.getUserProfileInfos().getBloodGroup());

                                    /**
                                     * now set profile photo
                                     */
                                    try {
                                        if (dataResponse.getUserProfileInfos().getProfilePhoto() != null) {
                                            Glide.with(getContext())
                                                    .load(dataResponse.getUserProfileInfos().getProfilePhoto())
                                                    .centerCrop()
                                                    .placeholder(R.drawable.person)
                                                    .error(R.drawable.person)
                                                    .into(binding.image);
                                        }

                                        progressDialog.dismiss();

                                    } catch (Exception e) {
                                        Log.d("Error", e.getMessage());
                                        progressDialog.dismiss();
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
                                                selectedEnterprise = dataResponse.getUserProfileInfos().getStoreID();
                                          /*      selectedTitle = dataResponse.getUserProfileInfos().getTitle();
                                                selectedGender = dataResponse.getUserProfileInfos().getGender();

                                           */
                                            });
                                });
                    });


        } catch (Exception e) {
            Log.d("ERROr", e.getMessage());
        }
    }

    private void submit() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        userViewModel.submitEditUserInfo1(
                getActivity(), getProfileId(getActivity().getApplication()), employee_vendorID, selectedDesignation, selectedDepartment, selectedTitle, binding.displayName.getText().toString(),
                binding.fullName.getText().toString(), selectedGender, binding.primaryMobile.getText().toString(),
                binding.email.getText().toString(), selectedEnterprise, binding.description.getText().toString(),
                binding.dateOfBirth.getText().toString(), selectedBloodGroup, binding.nationality.getText().toString(),
                binding.altEmail.getText().toString(), binding.altMobile.getText().toString(),
                binding.website.getText().toString(), joyiningDate,"1", image
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

}