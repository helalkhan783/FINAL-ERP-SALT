package com.ismos_salt_erp.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.serverResponseModel.DistrictListResponse;
import com.ismos_salt_erp.serverResponseModel.DivisionResponse;
import com.ismos_salt_erp.serverResponseModel.MillerProfileInfoResponse;
import com.ismos_salt_erp.serverResponseModel.ThanaList;
import com.ismos_salt_erp.viewModel.MillerProfileInfoViewModel;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class DialogClass extends DialogFragment {
    private SmartMaterialSpinner division, district, upazila;

    private MillerProfileInfoViewModel millerProfileInfoViewModel;


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


    private String selectedDivision, selectedDistrict, selectedUpazila;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        millerProfileInfoViewModel = new ViewModelProvider(this).get(MillerProfileInfoViewModel.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setCancelable(false);


        View view = inflater.inflate(R.layout.dialog, null);
        builder.setView(view);
        /**
         * findView
         */
        Button saveBtn = view.findViewById(R.id.save);
        Button cancelBtn = view.findViewById(R.id.cancel);
        EditText ownerName, nid, mobile, altMobile, email;
        ownerName = view.findViewById(R.id.name);
        nid = view.findViewById(R.id.nid);
        mobile = view.findViewById(R.id.mobile);
        altMobile = view.findViewById(R.id.altMobile);
        email = view.findViewById(R.id.email);
        division = view.findViewById(R.id.division);
        district = view.findViewById(R.id.district);
        upazila = view.findViewById(R.id.upazila);




        saveBtn.setOnClickListener(v -> {
        });
        cancelBtn.setOnClickListener(v -> DialogClass.this.getDialog().dismiss());

        division.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDivision = divisionResponseList.get(position).getDivisionId();
                division.setEnableErrorLabel(false);
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

        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDistrict = districtListResponseList.get(position).getDistrictId();
                district.setEnableErrorLabel(false);
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
        upazila.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedUpazila = thanaListsResponse.get(position).getUpazilaId();
                upazila.setEnableErrorLabel(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return builder.create();


    }

    private void getPageDataFromServer() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        millerProfileInfoViewModel.getProfileInfoResponse(getActivity())
                .observe(getViewLifecycleOwner(), new Observer<MillerProfileInfoResponse>() {
                    @Override
                    public void onChanged(MillerProfileInfoResponse response) {
                        progressDialog.dismiss();
                        if (response == null) {
                            Toasty.error(getActivity(), "something Wrong", Toasty.LENGTH_LONG).show();
                            return;
                        }
                        if (response.getStatus() != 200) {
                            Log.d("ERROR", "" + "something Wrong");
                            return;
                        }
                        /**
                         * set division
                         */
                        divisionResponseList = new ArrayList<>();
                        divisionResponseList.clear();
                        districtNameList = new ArrayList<>();
                        districtNameList.clear();
                        divisionResponseList.addAll(response.getDivisions());

                        for (int i = 0; i < divisionResponseList.size(); i++) {
                            districtNameList.add(districtListResponseList.get(i).getName());
                        }
                        division.setItem(districtNameList);

                    }
                });
    }

    /**
     * for set district Name
     */
    private void getDistrictListByDivisionId(String selectedDivision) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        millerProfileInfoViewModel.getDistrictListByDivisionId(getActivity(), selectedDivision)
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    districtListResponseList = new ArrayList<>();
                    districtListResponseList.clear();
                    districtNameList = new ArrayList<>();
                    districtNameList.clear();
                    districtListResponseList.addAll(response.getLists());

                    for (int i = 0; i < districtListResponseList.size(); i++) {
                        districtNameList.add(response.getLists().get(i).getName());
                    }
                    district.setItem(districtNameList);
                });
    }

    /**
     * for set thana Name
     */
    private void getThanaListByDistrictId(String selectedDistrict) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        millerProfileInfoViewModel.getThanaListByDistrictId(getActivity(), selectedDistrict)
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    thanaListsResponse = new ArrayList<>();
                    thanaListsResponse.clear();
                    thanaNameResponse = new ArrayList<>();
                    thanaNameResponse.clear();
                    thanaListsResponse.addAll(response.getLists());
                    for (int i = 0; i < response.getLists().size(); i++) {
                        thanaNameResponse.add(response.getLists().get(i).getName());
                    }
                    upazila.setItem(thanaNameResponse);
                });

    }


}