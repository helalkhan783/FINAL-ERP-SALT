package com.ismos_salt_erp.view.fragment.monitoring;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.clickHandle.AddNewMonitoringClickHandle;
import com.ismos_salt_erp.databinding.FragmentAddNewMonitoringBinding;
import com.ismos_salt_erp.serverResponseModel.AddMonitoringPageResponse;
import com.ismos_salt_erp.serverResponseModel.MillerListResponse;
import com.ismos_salt_erp.serverResponseModel.ZoneListResponse;
import com.ismos_salt_erp.utils.PathUtil;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.MonitoringViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddNewMonitoring extends BaseFragment implements DatePickerDialog.OnDateSetListener {
    private MonitoringViewModel monitoringViewModel;
    private FragmentAddNewMonitoringBinding binding;
    private static final int PICK_IMAGE = 200;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 300;
    private Uri imageUri;
    private boolean isMonitorDateClick = false;
    /**
     * For zone
     */
    private List<ZoneListResponse> zoneListResponseList;
    private List<String> zoneNameList;
    /**
     * For monitoring Type
     */
    private List<String> monitoringTypeList;
    private List<String> monitoringTypeNameList;
    /**
     * For Miller list
     */
    private List<MillerListResponse> millerListResponseList;
    private List<String> millerNameList;


    private String selectedZone, selectedMonitoringType, selectedMiller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_monitoring, container, false);
        monitoringViewModel = new ViewModelProvider(this).get(MonitoringViewModel.class);
        binding.toolbar.toolbarTitle.setText("Add new Monitoring");
        setCurrentDateTime();
        /**
         * Now Get Page Data From Server
         */
        getPageDataFromServer();

        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        binding.setClickHandle(new AddNewMonitoringClickHandle() {
            @Override
            public void save() {
                if (selectedZone == null) {
                    infoMessage(getActivity().getApplication(), "Please select zone");
                    return;
                }
                if (selectedMonitoringType == null) {
                    infoMessage(getActivity().getApplication(), "Please select monitoring type");
                    return;
                }
                if (selectedMiller == null) {
                    infoMessage(getActivity().getApplication(), "Please select miller");
                    return;
                }
                if (!(isInternetOn(getActivity()))) {
                    infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
                    return;
                }
                if (binding.monitoringSummary.getText().toString().isEmpty()) {
                    binding.monitoringSummary.setError("Empty");
                    binding.monitoringSummary.requestFocus();
                    return;
                }
                /**
                 * Everything Ok Now save Monitoring data to server
                 */
                hideKeyboard(getActivity());
                addNewMonitoringDialog();
            }

            @Override
            public void documentImage() {
                if (!(checkStoragePermission())) {
                    requestStoragePermission(STORAGE_PERMISSION_REQUEST_CODE);
                } else {
                    getLogoImageFromFile(getActivity().getApplication(), PICK_IMAGE);
                }
            }

            @Override
            public void monitoringDate() {
                isMonitorDateClick = true;
                showDatePickerDialog();
            }

            @Override
            public void publishDate() {
                showDatePickerDialog();
            }
        });
        /**
         * Now handle page all dropdown Item Click
         */
        binding.zone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedZone = zoneListResponseList.get(position).getZoneID();
                binding.zone.setEnableErrorLabel(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.monitoringType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMonitoringType = monitoringTypeList.get(position);
                binding.monitoringType.setEnableErrorLabel(false);
                if (monitoringTypeList.get(position).equals("4")) {
                    binding.otherNameView.setVisibility(View.VISIBLE);
                    return;
                }
                binding.otherNameView.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.miller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMiller = millerListResponseList.get(position).getSl();
                binding.miller.setEnableErrorLabel(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return binding.getRoot();
    }

    private void validationAndSave() {
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
                    MultipartBody.Part.createFormData("document", file.getName(), requestFile);//here document is name of from data
        } else {
            logoBody = null;
        }

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        monitoringViewModel.addNewMonitoring(
                getActivity(), binding.monitoringDate.getText().toString(),
                binding.publishDate.getText().toString(), selectedZone, selectedMiller, selectedMonitoringType, logoBody
                , binding.monitoringSummary.getText().toString(), binding.othersName.getText().toString()).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            if (response.getStatus() != 200) {
                infoMessage(getActivity().getApplication(), "" + response.getMessage());
                return;
            }
            successMessage(getActivity().getApplication(), "" + response.getMessage());
            getActivity().onBackPressed();
        });


    }

    private void getPageDataFromServer() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        monitoringViewModel.getAppMonitoringPageData(getActivity())
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() != 200) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }
                    /**
                     * Everything Ok now set server data To view
                     */
                    setServerDataToView(response);

                });
    }

    private void setServerDataToView(AddMonitoringPageResponse response) {
        /**
         * For Zone
         */
        zoneListResponseList = new ArrayList<>();
        zoneListResponseList.clear();
        zoneNameList = new ArrayList<>();
        zoneNameList.clear();
        zoneListResponseList.addAll(response.getZoneList());

        for (int i = 0; i < response.getZoneList().size(); i++) {
            zoneNameList.add(response.getZoneList().get(i).getZoneName());
        }
        binding.zone.setItem(zoneNameList);

        /**
         * now set Monitoring type dynamically
         */
        monitoringTypeList = new ArrayList<>();
        monitoringTypeList.add("1");
        monitoringTypeList.add("2");
        monitoringTypeList.add("3");
        monitoringTypeList.add("4");

        monitoringTypeNameList = new ArrayList<>();
        monitoringTypeNameList.clear();
        monitoringTypeNameList.addAll(Arrays.asList("QC/QA", "License", "Stock Availability", "Other"));
        binding.monitoringType.setItem(monitoringTypeNameList);
        /**
         * now set miller list
         */
        millerListResponseList = new ArrayList<>();
        millerListResponseList.clear();
        millerListResponseList.addAll(response.getMillerList());
        millerNameList = new ArrayList<>();
        millerNameList.clear();

        for (int i = 0; i < response.getMillerList().size(); i++) {
            millerNameList.add(response.getMillerList().get(i).getDisplayName());
        }
        binding.miller.setItem(millerNameList);

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
            binding.documentImage.setImageDrawable(null);
            binding.documentImage.setImageBitmap(bitmap);


            /**
             * now set licenseImageName
             * */
            binding.documentImageName.setText(String.valueOf(new File("" + data.getData()).getName()));

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

    private void showDatePickerDialog() {
        Calendar now = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dialog = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Initial day selection
        );
        dialog.show(getActivity().getSupportFragmentManager(), "Datepickerdialog");
    }


    private void setCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        //System.out.println(formatter.format(date));
        String currentDate = formatter.format(date);
        /*System.out.println(dtf.format(now));*/
        binding.monitoringDate.setText(currentDate);
        binding.publishDate.setText(currentDate);
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
        if (isMonitorDateClick) {
            isMonitorDateClick = false;
            binding.monitoringDate.setText(selectedDate);
            return;
        }
        binding.publishDate.setText(selectedDate);

    }


    private void addNewMonitoringDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
      /*  alertDialog.setTitle("Alert");
        alertDialog.setIcon(R.drawable.warning_btn);*/
        alertDialog.setMessage("Do you want to add ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                (dialog, which) -> {
                    dialog.dismiss();
                    validationAndSave();
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }
}