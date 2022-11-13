package com.ismos_salt_erp.view.fragment.miller.editmiller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bumptech.glide.Glide;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.clickHandle.MillerLicenseInfoEditClickHandle;
import com.ismos_salt_erp.databinding.FragmentMillerLicenseinfoEditBinding;
import com.ismos_salt_erp.serverResponseModel.CertificateResponse;
import com.ismos_salt_erp.serverResponseModel.MillerLicenseInfoEditResponse;
import com.ismos_salt_erp.utils.LicenseInfoDialog;
import com.ismos_salt_erp.utils.PathUtil;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.MillerLicenseInfoViewModel;
import com.ismos_salt_erp.viewModel.MillerProfileInfoViewModel;
import com.ismos_salt_erp.viewModel.UpdateMillerViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@NoArgsConstructor
public class MillerLicenseInfoEdit extends BaseFragment implements DatePickerDialog.OnDateSetListener {
    private FragmentMillerLicenseinfoEditBinding binding;
    private UpdateMillerViewModel updateMillerViewModel;
    private MillerLicenseInfoViewModel millerLicenseInfoViewModel;
    private boolean isIssuingDate = false;
    private boolean isCertificateDate = false;
    private boolean isRenewalDate = false;

    private String sid,profileID;


    public MillerLicenseInfoEdit(String slId) {
        this.sid = slId;
    }

    private MillerProfileInfoViewModel millerProfileInfoViewModel;
    private ViewPager viewPager;

    private static final int PICK_IMAGE = 200;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 300;
    private Uri imageUri;
    /**
     * For Certificate
     */
    private List<CertificateResponse> certificateResponsesList;
    private List<String> certificateNameList;

    /**
     * for store previous selected info
     */
    private MillerLicenseInfoEditResponse previousMillerInfoResponse;

    private String selectedCertificateType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_miller_licenseinfo_edit, container, false);
        updateMillerViewModel = new ViewModelProvider(this).get(UpdateMillerViewModel.class);
        millerLicenseInfoViewModel = new ViewModelProvider(this).get(MillerLicenseInfoViewModel.class);

        viewPager = getActivity().findViewById(R.id.viewPager);
        millerProfileInfoViewModel = new ViewModelProvider(this).get(MillerProfileInfoViewModel.class);
        binding.toolbar.setClickHandle(() -> getActivity().onBackPressed());
        binding.toolbar.toolbarTitle.setText("Update Owner License info");
        getDataFromPreviousFragment();
        /**
         * set Current date time
         */
        setCurrentDateTime();
        /**
         * set Page Data from server
         */
        getPageDataFromServer();


        binding.setClickHandle(new MillerLicenseInfoEditClickHandle() {
            @Override
            public void addNew() {
                LicenseInfoDialog dialog = new LicenseInfoDialog();
                dialog.show(getActivity().getSupportFragmentManager(), "dialog");
            }

            @Override
            public void save() {
                validationAndSave();
            }

            @Override
            public void certificateImage() {
                if (!(checkStoragePermission())) {
                    requestStoragePermission(STORAGE_PERMISSION_REQUEST_CODE);
                } else {
                    getLogoImageFromFile(getActivity().getApplication(), PICK_IMAGE);
                }
            }

            @Override
            public void issuingDate() {
                isIssuingDate = true;
                showDatePickerDialog();
            }

            @Override
            public void certificateDate() {
                isCertificateDate = true;
                showDatePickerDialog();
            }

            @Override
            public void renewalDate() {
                isRenewalDate = true;
                showDatePickerDialog();
            }
        });

        binding.certificateType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCertificateType = certificateResponsesList.get(position).getCertificateTypeID();
                binding.issuerNameEt.setText(certificateResponsesList.get(position).getCertificateProviderName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return binding.getRoot();
    }

    private void getDataFromPreviousFragment() {
        assert getArguments() != null;
        sid = getArguments().getString("sl_id");
        profileID = getArguments().getString("profileID");
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


        if (isIssuingDate) {
            binding.issuingDate.setText(selectedDate);
            isIssuingDate = false;
            binding.issuingDate.setError(null);
            return;
        }

        if (isCertificateDate) {
            binding.certificateDate.setText(selectedDate);
            isCertificateDate = false;
            binding.certificateDate.setError(null);
            return;
        }

        if (isRenewalDate) {
            binding.renewalDate.setText(selectedDate);
            isRenewalDate = false;
            binding.renewalDate.setError(null);
        }
    }


    private void setCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        //System.out.println(formatter.format(date));
        String currentDate = formatter.format(date);


        /*System.out.println(dtf.format(now));*/
        binding.issuingDate.setText(currentDate);
        binding.certificateDate.setText(currentDate);
        binding.renewalDate.setText(currentDate);
    }

    private void getPageDataFromServer() {

        hideKeyboard(getActivity());
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }

        /**
         * For set Previous Selected Data
         */
        getPreviousSelectedDataFromServer();


        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        millerProfileInfoViewModel.getProfileInfoResponse(getActivity())
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
                    if (response.getStatus() == 200) {
                        certificateResponsesList = new ArrayList<>();
                        certificateNameList = new ArrayList<>();
                        certificateResponsesList.clear();
                        certificateNameList.clear();

                        try {
                            certificateResponsesList.addAll(response.getCertificate());
                            for (int i = 0; i < response.getCertificate().size(); i++) {
                                certificateNameList.add(response.getCertificate().get(i).getCertificateTypeName());
                            }
                            binding.certificateType.setItem(certificateNameList);
                            if (previousMillerInfoResponse != null) {
                                for (int i = 0; i < certificateResponsesList.size(); i++) {
                                    if (certificateResponsesList.get(i).getCertificateTypeID().equals(previousMillerInfoResponse.getCertificateData().get(0).getCertificateTypeID())) {
                                        selectedCertificateType = certificateResponsesList.get(i).getCertificateTypeID();
                                        binding.certificateType.setSelection(i);
                                        break;
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Log.d("ERROR", "" + e.getLocalizedMessage());
                        }
                    }
                });
    }

    private void getPreviousSelectedDataFromServer() {

        millerLicenseInfoViewModel.getMillerPreviousOwnerLicenseInfo(getActivity(), sid)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }
                    previousMillerInfoResponse = response;

                    try {
                        binding.issuerNameEt.setText("" + response.getCertificateData().get(0).getIssuerName());
                        binding.issuingDate.setText("" + response.getCertificateData().get(0).getIssueDate());
                        binding.certificateDate.setText("" + response.getCertificateData().get(0).getCertificateDate());
                        Glide
                                .with(getContext())
                                .load(response.getCertificateData().get(0).getCertificateImage())
                                .centerCrop()
                                .placeholder(R.drawable.agamisoft_logo)
                                .into(binding.certificateImageview);

                        binding.renewalDate.setText(response.getCertificateData().get(0).getRenewDate());
                        binding.renewalDate.setText(response.getCertificateData().get(0).getRenewDate());
                        binding.remarksEt.setText(response.getCertificateData().get(0).getRemarks());
                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getLocalizedMessage());
                    }


                });


       /* updateMillerViewModel.getPreviousMillerInfoBySid(getActivity(), sid)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }
                    previousMillerInfoResponse = response;
                    *//**
         * Now Set data
         *//*
                    try {
                        binding.issuerNameEt.setText(response.getCertificateInfo().get(0).getIssuerName());
                        binding.issuingDate.setText(response.getCertificateInfo().get(0).getIssueDate());
                        binding.certificateDate.setText(response.getCertificateInfo().get(0).getCertificateDate());
                        Glide
                                .with(getContext())
                                .load(response.getCertificateInfo().get(0).getCertificateImage())
                                .centerCrop()
                                .placeholder(R.drawable.agamisoft_logo)
                                .into(binding.certificateImageview);

                        binding.renewalDate.setText(response.getCertificateInfo().get(0).getRenewDate());
                        binding.renewalDate.setText(response.getCertificateInfo().get(0).getRenewDate());
                        binding.remarksEt.setText(response.getCertificateInfo().get(0).getRemarks());
                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getLocalizedMessage());
                    }
*/
//                });
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
            binding.certificateImageview.setImageDrawable(null);
            binding.certificateImageview.setImageBitmap(bitmap);


            /**
             * now set licenseImageName
             * */
            binding.certificateImageName.setText(String.valueOf(new File("" + data.getData()).getName()));

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

    private void validationAndSave() {

        if (selectedCertificateType == null) {
            infoMessage(getActivity().getApplication(), "Please select certificate type");

            return;
        }
        if (binding.issuerNameEt.getText().toString().isEmpty()) {
            binding.issuerNameEt.setError("Empty Field");
            binding.issuerNameEt.requestFocus();
            binding.issuerNameEt.requestFocus();
            return;
        }

        if (binding.issuingDate.getText().toString().isEmpty()) {
            binding.issuingDate.setError("Empty Field");
            binding.issuingDate.requestFocus();
            return;
        }
        if (binding.certificateDate.getText().toString().isEmpty()) {
            binding.certificateDate.setError("Empty Field");
            binding.certificateDate.requestFocus();
            return;
        }
        if (binding.renewalDate.getText().toString().isEmpty()) {
            binding.renewalDate.setError("Empty Field");
            binding.renewalDate.requestFocus();
            return;
        }
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
                    MultipartBody.Part.createFormData("certificateImage", file.getName(), requestFile);//here certificateImage is name of from data
        } else {
            logoBody = null;
        }

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        updateMillerViewModel.updateMillerCertificateInfo(
                getActivity(), selectedCertificateType.replaceAll("^\"|\"$", ""),profileID, previousMillerInfoResponse.getCertificateData().get(0).getSlID(),
                binding.issuerNameEt.getText().toString().replaceAll("^\"|\"$", ""),
                binding.issuingDate.getText().toString(), binding.certificateDate.getText().toString(),
                logoBody, binding.renewalDate.getText().toString(), binding.remarksEt.getText().toString(),
                previousMillerInfoResponse.getCertificateData().get(0).getStatus()
                /*,
                previousMillerInfoResponse.getCertificateInfo().get(0).getReviewStatus(),
                previousMillerInfoResponse.getCertificateInfo().get(0).getReviewTime(),
                previousMillerInfoResponse.getCertificateInfo().get(0).getReviewBy(),
                previousMillerInfoResponse.getCertificateInfo().get(0).getRef_slId().replaceAll("^\"|\"$", "")*/)
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong here");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }
                    if (response.getStatus() != 200) {
                        Log.d("ERROR", "ERROR");
                        return;
                    }
                    Log.d("RESPONSE", String.valueOf(response.getMessage()));
                    hideKeyboard(getActivity());
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    getActivity().onBackPressed();
                    // viewPager.setCurrentItem(3);
                });
    }
}