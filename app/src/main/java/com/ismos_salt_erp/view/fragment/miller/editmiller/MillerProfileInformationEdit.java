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
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.clickHandle.EditMillerProfileInformationEditClickHandle;
import com.ismos_salt_erp.databinding.FragmentMillerProfileinformationEditBinding;
import com.ismos_salt_erp.serverResponseModel.DistrictListResponse;
import com.ismos_salt_erp.serverResponseModel.DivisionResponse;
import com.ismos_salt_erp.serverResponseModel.GetPreviousMillerInfoResponse;
import com.ismos_salt_erp.serverResponseModel.MillTypeResponse;
import com.ismos_salt_erp.serverResponseModel.OwnerTypeResponse;
import com.ismos_salt_erp.serverResponseModel.ProcessTypeResponse;
import com.ismos_salt_erp.serverResponseModel.ThanaList;
import com.ismos_salt_erp.serverResponseModel.ZoneResponse;
import com.ismos_salt_erp.utils.PathUtil;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.MillerProfileInfoViewModel;
import com.ismos_salt_erp.viewModel.UpdateMillerViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MillerProfileInformationEdit extends BaseFragment {
    private FragmentMillerProfileinformationEditBinding binding;
    private UpdateMillerViewModel updateMillerViewModel;

    private ViewPager viewPager;

    private static final int PICK_IMAGE = 200;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 300;
    private Uri imageUri;
    private MillerProfileInfoViewModel millerProfileInfoViewModel;

    /**
     * for zone
     */
    private List<ZoneResponse> zoneResponseList;
    private List<String> zoneList;
    /**
     * for process type
     */
    private List<ProcessTypeResponse> processTypeResponseList;
    private List<String> processTypeNameList;
    /**
     * for mill type
     */
    private List<MillTypeResponse> millTypeResponseList;
    /**
     * for type of owner
     */
    private List<OwnerTypeResponse> ownerTypeResponseList;
    private List<String> ownerTypeNameList;
    /**
     * for division
     */
    private List<DivisionResponse> divisionResponseList;
    private List<String> divisionNameList;
    /**
     * for district
     */
    private List<DistrictListResponse> districtListResponseList;
    private List<String> districtNameList;
    /**
     * for Thana
     */
    private List<ThanaList> thanaListsResponse;
    private List<String> thanaNameResponse;


    private GetPreviousMillerInfoResponse previousGetPreviousMillerInfoResponse;//for store previous miller information


    private String selectedZone, selectedProcessType, selectedOwnerType, selectedDivision, selectedDistrict, selectedThana;
    private String millerType1 = "";
    private String millerType2 = "";
    private String millerType3 = "";
    private String selectedZoneRemarks, countProfile;

    private Set<String> selectedMillerTypeList = new HashSet<>();//for store miller type id
    private boolean firstCondition = false;

    private boolean sndCondition = false;


    private String slid;

    public MillerProfileInformationEdit(String slId) {
        this.slid = slId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_miller_profileinformation_edit, container, false);
        millerProfileInfoViewModel = new ViewModelProvider(this).get(MillerProfileInfoViewModel.class);
        updateMillerViewModel = new ViewModelProvider(this).get(UpdateMillerViewModel.class);
        viewPager = getActivity().findViewById(R.id.viewPager);
        selectedMillerTypeList = new HashSet<>();

        getPreviousMillerInformationBySid();

        binding.setClickHandle(new EditMillerProfileInformationEditClickHandle() {
            @Override
            public void save() {
                validationAndSave();
            }

            @Override
            public void pickImage() {
                if (!(checkStoragePermission())) {
                    requestStoragePermission(STORAGE_PERMISSION_REQUEST_CODE);
                } else {
                    getLogoImageFromFile(getActivity().getApplication(), PICK_IMAGE);
                }
            }
        });

        binding.millType1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (binding.millType1.isChecked()) {
                millerType1 += millTypeResponseList.get(0).getRemarks();
                selectedMillerTypeList.add(millTypeResponseList.get(0).getMillTypeID());//set selected id
                if (selectedZone == null) {
                    return;
                }
                setMillIdBasedOnZoneAndMillType();//set mill id
                return;
            }
            millerType1 = "";
            selectedMillerTypeList.remove(millTypeResponseList.get(0).getMillTypeID());
            setMillIdBasedOnZoneAndMillType();//set mill id

        });
        binding.millType2.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (binding.millType2.isChecked()) {
                millerType2 += millTypeResponseList.get(1).getRemarks();
                selectedMillerTypeList.add(millTypeResponseList.get(1).getMillTypeID());
                if (selectedZone == null) {
                    return;
                }
                setMillIdBasedOnZoneAndMillType();//set mill id
                return;
            }
            millerType2 = "";
            selectedMillerTypeList.remove(millTypeResponseList.get(1).getMillTypeID());
            setMillIdBasedOnZoneAndMillType();//set mill id


        });
        binding.millType3.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (binding.millType3.isChecked()) {
                millerType3 += millTypeResponseList.get(2).getRemarks();
                selectedMillerTypeList.add(millTypeResponseList.get(2).getMillTypeID());
                if (selectedZone == null) {
                    return;
                }
                setMillIdBasedOnZoneAndMillType();//set mill id
                return;
            }
            millerType3 = "";
            selectedMillerTypeList.remove(millTypeResponseList.get(2).getMillTypeID());
            setMillIdBasedOnZoneAndMillType();//set mill id
        });
        binding.firstCondition.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (binding.firstCondition.isChecked()) {
                firstCondition = true;
                return;
            }
            firstCondition = false;
        });
        binding.sndCondition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (binding.sndCondition.isChecked()) {
                    sndCondition = true;
                    return;
                }
                sndCondition = false;
            }
        });


        return binding.getRoot();
    }


    private void getPageInfoFromServer() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check your internet Connection");
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        millerProfileInfoViewModel.getProfileInfoResponse(getActivity())
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    /**
                     * for zone
                     */
                    zoneResponseList = new ArrayList<>();
                    zoneResponseList.clear();
                    zoneList = new ArrayList<>();
                    zoneList.clear();
                    zoneResponseList.addAll(response.getZones());

                    for (int i = 0; i < response.getZones().size(); i++) {
                        zoneList.add(response.getZones().get(i).getZoneName());
                    }
                    binding.zone.setItem(zoneList);

                    /**
                     * now set select Previous selected zone
                     */
                    for (int i = 0; i < zoneResponseList.size(); i++) {
                        if (zoneResponseList.get(i).getZoneID().equals(previousGetPreviousMillerInfoResponse.getProfileInfo().getZoneID())) {
                            binding.zone.setSelection(i);
                            break;
                        }
                    }
                    /**
                     * for process type
                     */
                    processTypeResponseList = new ArrayList<>();
                    processTypeResponseList.clear();
                    processTypeNameList = new ArrayList<>();
                    processTypeNameList.clear();
                    processTypeResponseList.addAll(response.getProcessTypes());

                    for (int i = 0; i < response.getProcessTypes().size(); i++) {
                        processTypeNameList.add(response.getProcessTypes().get(i).getProcessTypeName());
                    }
                    binding.processType.setItem(processTypeNameList);

                    /**
                     * now set previous selected process type
                     */
                    for (int i = 0; i < processTypeResponseList.size(); i++) {
                        if (processTypeResponseList.get(i).getProcessTypeID().equals(previousGetPreviousMillerInfoResponse.getProfileInfo().getProcessTypeID())) {
                            binding.processType.setSelection(i);
                            break;
                        }
                    }
                    /**
                     * now set Previous selected name and others more
                     */
                    binding.nameEt.setText(previousGetPreviousMillerInfoResponse.getProfileInfo().getDisplayName());
                    // binding.millType1//will set mill type
                    binding.millId.setText(previousGetPreviousMillerInfoResponse.getProfileInfo().getMillID());
                    binding.capacity.setText(previousGetPreviousMillerInfoResponse.getProfileInfo().getCapacity());
                    binding.remarks.setText(previousGetPreviousMillerInfoResponse.getProfileInfo().getRemarks());
                    /**
                     * now set logo image
                     */
                    Glide.with(getContext())
                            .load(previousGetPreviousMillerInfoResponse.getProfileInfo().getProfilePhoto())
                            .centerCrop()
                            .placeholder(R.drawable.unicef_main)
                            .error(R.drawable.unicef_main)
                            .into(binding.logoImage);

                    /**
                     * now set millType
                     */

                    millTypeResponseList = new ArrayList<>();
                    millTypeResponseList.clear();
                    millTypeResponseList.addAll(response.getMillTypes());


                    binding.millType1.setTag(response.getMillTypes().get(0).getMillTypeName());
                    binding.millType2.setTag(response.getMillTypes().get(1).getMillTypeName());
                    binding.millType3.setTag(response.getMillTypes().get(2).getMillTypeName());
                    for (int i = 0; i < previousGetPreviousMillerInfoResponse.getProfileInfo().getMillTypeIDs().size(); i++) {
                        for (int i1 = 0; i1 < response.getMillTypes().size(); i1++) {
                            if (previousGetPreviousMillerInfoResponse.getProfileInfo().getMillTypeIDs().get(i).equals(response.getMillTypes().get(i1).getMillTypeID())) {
                                if (i1 == 0) {
                                    binding.millType1.setChecked(true);
                                }
                                if (i1 == 1) {
                                    binding.millType2.setChecked(true);
                                }
                                if (i1 == 2) {
                                    binding.millType3.setChecked(true);
                                }
                            }
                        }
                    }


                    /**
                     * now set Type of owner
                     */
                    ownerTypeResponseList = new ArrayList<>();
                    ownerTypeResponseList.clear();
                    ownerTypeResponseList.addAll(response.getOwnerTypes());
                    ownerTypeNameList = new ArrayList<>();
                    ownerTypeNameList.clear();
                    for (int i = 0; i < response.getOwnerTypes().size(); i++) {
                        ownerTypeNameList.add(response.getOwnerTypes().get(i).getOwnerTypeName());
                    }
                    binding.ownerType.setItem(ownerTypeNameList);

                    /**
                     * Now set previous selected owner type
                     */
                    for (int i = 0; i < ownerTypeResponseList.size(); i++) {
                        if (ownerTypeResponseList.get(i).getOwnerTypeID().equals(previousGetPreviousMillerInfoResponse.getProfileInfo().getOwnerTypeID())) {
                            binding.ownerType.setSelection(i);
                            break;
                        }
                    }


                    /**
                     * now set division
                     */
                    divisionResponseList = new ArrayList<>();
                    divisionResponseList.clear();
                    divisionNameList = new ArrayList<>();
                    divisionResponseList.clear();
                    divisionResponseList.addAll(response.getDivisions());
                    for (int i = 0; i < response.getDivisions().size(); i++) {
                        divisionNameList.add(response.getDivisions().get(i).getName());
                    }
                    binding.division.setItem(divisionNameList);

                    /**
                     * Now set Previous selected division
                     */
                    for (int i = 0; i < divisionResponseList.size(); i++) {
                        if (divisionResponseList.get(i).getDivisionId().equals(previousGetPreviousMillerInfoResponse.getProfileInfo().getDivisionID())) {
                            binding.division.setSelection(i);
                            selectedDivision = previousGetPreviousMillerInfoResponse.getProfileInfo().getDivisionID();//set Previous selected Division
                            break;
                        }
                    }
                    /**
                     * Now set Previous selected district by previous selected division id
                     */

                    getDistrictListByDivisionId(selectedDivision);


                    /**
                     * set count profile
                     */
                    countProfile = response.getCountProfile();

                });


        binding.zone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedZone = zoneResponseList.get(position).getZoneID();
                selectedZoneRemarks = zoneResponseList.get(position).getRemarks();
                setMillIdBasedOnZoneAndMillType();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.processType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedProcessType = processTypeResponseList.get(position).getProcessTypeID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.ownerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOwnerType = ownerTypeResponseList.get(position).getOwnerTypeID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.division.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDivision = divisionResponseList.get(position).getDivisionId();
                binding.division.setEnableErrorLabel(false);
                /**
                 * now set district based on the current division
                 */
                if (selectedDivision != null) {
                    getDistrictListByDivisionId(selectedDivision);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDistrict = districtListResponseList.get(position).getDistrictId();
                binding.district.setEnableErrorLabel(false);
                /**
                 * now set thana based on the current district
                 */
                if (selectedDistrict != null) {
                    getThanaListByDistrictId(selectedDistrict);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
    }

    private void setMillIdBasedOnZoneAndMillType() {
        if (millerType1.isEmpty() && millerType2.isEmpty() && millerType3.isEmpty()) {
            binding.millId.setText("");
            return;
        }
        String millId = selectedZoneRemarks + "-" + millerType1 + millerType2 + millerType3 + "-" + countProfile;
        binding.millId.setText(millId);
    }

    /**
     * for set district Name
     */
    private void getDistrictListByDivisionId(String selectedDivision) {
        millerProfileInfoViewModel.getDistrictListByDivisionId(getActivity(), selectedDivision)
                .observe(getViewLifecycleOwner(), response -> {
                    districtListResponseList = new ArrayList<>();
                    districtListResponseList.clear();
                    districtNameList = new ArrayList<>();
                    districtNameList.clear();
                    districtListResponseList.addAll(response.getLists());

                    for (int i = 0; i < districtListResponseList.size(); i++) {
                        districtNameList.add(response.getLists().get(i).getName());
                    }
                    binding.district.setItem(districtNameList);


                    if (previousGetPreviousMillerInfoResponse != null) {
                        for (int i = 0; i < districtListResponseList.size(); i++) {
                            if (districtListResponseList.get(i).getDistrictId().equals(previousGetPreviousMillerInfoResponse.getProfileInfo().getDistrictID())) {
                                binding.district.setSelection(i);
                                selectedDistrict = districtListResponseList.get(i).getDistrictId();//set previous selected district
                                getThanaListByDistrictId(selectedDistrict);
                                break;
                            }
                        }
                    }


                });
    }


    /**
     * for set thana Name
     */
    private void getThanaListByDistrictId(String selectedDistrict) {
        millerProfileInfoViewModel.getThanaListByDistrictId(getActivity(), selectedDistrict)
                .observe(getViewLifecycleOwner(), response -> {
                    thanaListsResponse = new ArrayList<>();
                    thanaListsResponse.clear();
                    thanaNameResponse = new ArrayList<>();
                    thanaNameResponse.clear();
                    thanaListsResponse.addAll(response.getLists());
                    for (int i = 0; i < response.getLists().size(); i++) {
                        thanaNameResponse.add(response.getLists().get(i).getName());
                    }
                    binding.thana.setItem(thanaNameResponse);

                    if (previousGetPreviousMillerInfoResponse != null) {
                        for (int i = 0; i < thanaListsResponse.size(); i++) {
                            if (thanaListsResponse.get(i).getUpazilaId().equals(previousGetPreviousMillerInfoResponse.getProfileInfo().getUpazilaID())) {
                                selectedThana = thanaListsResponse.get(i).getUpazilaId();
                                binding.thana.setSelection(i);
                                break;
                            }
                        }
                    }
                });

    }


    public void getPreviousMillerInformationBySid() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check your Internet Connection");
            return;
        }
        updateMillerViewModel.getPreviousMillerInfoBySid(getActivity(), slid)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), "" + response.getMessage());
                        return;
                    }
                    previousGetPreviousMillerInfoResponse = response;
                    getPageInfoFromServer();
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
            binding.logoImage.setImageDrawable(null);
            binding.logoImage.setImageBitmap(bitmap);


            /**
             * now set licenseImageName
             * */
            binding.logoimageName.setText(String.valueOf(new File("" + data.getData()).getName()));

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
        if (selectedZone == null) {
          infoMessage(getActivity().getApplication(), "Please select zone");
            return;
        }
        if (selectedProcessType == null) {
            infoMessage(getActivity().getApplication(), "Please select process type");
            return;
        }
        if (binding.nameEt.getText().toString().isEmpty()) {
            binding.nameEt.setError("Empty Field");
            binding.nameEt.requestFocus();
            return;
        }
        if (selectedMillerTypeList.isEmpty()) {
            infoMessage(getActivity().getApplication(), "Please Select Your mill Type");
            return;
        }
        if (binding.capacity.getText().toString().isEmpty()) {
            binding.capacity.setError("Empty Field");
            binding.capacity.requestFocus();
            return;
        }
        if (binding.remarks.getText().toString().isEmpty()) {
            binding.remarks.setError("Empty Field");
            binding.remarks.requestFocus();
            return;
        }

        if (selectedOwnerType == null) {
            infoMessage(getActivity().getApplication(), "Please select owner type");
            return;
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
        if (!firstCondition) {
            binding.firstCondition.setError("Please check this box,if you want to process");
            return;
        }
        if (!sndCondition) {
            binding.sndCondition.setError("Please check this box,if you want to process");
            return;
        }

        /**
         * for logo image
         */

        MultipartBody.Part logoBody;
        if (imageUri != null) {//logo image not mandatory here so if user not select any logo image by default it send null
            File file = null;
            try {
                file = new File(Objects.requireNonNull(PathUtil.getPath(getActivity(), imageUri)));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

            // MultipartBody.Part is used to send also the actual file name
            logoBody =
                    MultipartBody.Part.createFormData("profile_photo", file.getName(), requestFile);//here logoImage is name of from data
        } else {
            logoBody = null;
        }

        updateMillerViewModel.submitUpdateMillerProfileInformation(
                getActivity(), binding.remarks.getText().toString(), binding.capacity.getText().toString(), selectedZone, selectedOwnerType, selectedProcessType, binding.nameEt.getText().toString(),
                selectedMillerTypeList, binding.millId.getText().toString(), selectedDivision, selectedDistrict, selectedThana,
                slid, previousGetPreviousMillerInfoResponse.getProfileInfo().getProfileID(), logoBody,
                previousGetPreviousMillerInfoResponse.getProfileInfo().getProfileDetailsId(),
                previousGetPreviousMillerInfoResponse.getProfileInfo().getRefSl(), previousGetPreviousMillerInfoResponse.getProfileInfo().getIsSubmit(),
                previousGetPreviousMillerInfoResponse.getProfileInfo().getAssociationID())

                .observe(getViewLifecycleOwner(), response -> {
                    try {
                        if (response == null) {
                            errorMessage(getActivity().getApplication(), "Something Wrong");
                            return;
                        }
                        if (response.getStatus() == 400) {
                            infoMessage(getActivity().getApplication(), "" + response.getMessage());
                            Log.d("CURRENT_RESPONSE", "" + response.getMessage());
                            return;
                        }
                        successMessage(getActivity().getApplication(), "" + response.getMessage());
                        Log.d("RESPONSE", response.getMessage());
                        viewPager.setCurrentItem(1);//go to snd tab
                    } catch (Exception e) {
                        Log.e("Error", e.getMessage());
                    }
                });
    }
}