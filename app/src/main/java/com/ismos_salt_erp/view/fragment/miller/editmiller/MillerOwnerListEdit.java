package com.ismos_salt_erp.view.fragment.miller.editmiller;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.MillerOwnerListAdapter;
import com.ismos_salt_erp.adapter.MillerOwnerListLicenseAdapter;
import com.ismos_salt_erp.databinding.FragmentMillerOwnerListEditBinding;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.UpdateMillerViewModel;


public class MillerOwnerListEdit extends BaseFragment implements MillerOwnerListClickHandle {

    private FragmentMillerOwnerListEditBinding binding;
    private UpdateMillerViewModel updateMillerViewModel;
    private String slId, portion;


    public MillerOwnerListEdit() {
    }

    public MillerOwnerListEdit(String slId, String portion) {
        this.slId = slId;
        this.portion = portion;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_miller_owner_list_edit, container, false);
        updateMillerViewModel = new ViewModelProvider(this).get(UpdateMillerViewModel.class);

        getPageDataFromServer();

        return binding.getRoot();
    }

    private void getPageDataFromServer() {

        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check your Internet Connection");
            return;
        }

        updateMillerViewModel.getPreviousMillerInfoBySid(getActivity(), slId)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        return;
                    }

                    if (response.getOwnerInfo().isEmpty() || response.getOwnerInfo() == null) {
                        binding.ownerListRv.setVisibility(View.GONE);
                        binding.noDataFound.setVisibility(View.VISIBLE);
                        return;
                    }


                    if (portion.equals("LICENSE_INFO_EDIT")) {
                        MillerOwnerListLicenseAdapter adapter = new MillerOwnerListLicenseAdapter(getActivity(), response.getCertificateInfo(), MillerOwnerListEdit.this);
                        binding.ownerListRv.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.ownerListRv.setAdapter(adapter);
                    } else {
                        //response.getOwnerInfo() // all list
                        MillerOwnerListAdapter adapter = new MillerOwnerListAdapter(getActivity(), response.getOwnerInfo(), MillerOwnerListEdit.this);
                        binding.ownerListRv.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.ownerListRv.setAdapter(adapter);
                    }

                });
    }

    @Override
    public void click(String ownerId, String profileId) {
        if (portion.equals("OWNER_INFO_EDIT")) {
            try {
                Bundle bundle = new Bundle();
                bundle.putString("sl_id", ownerId);
            } catch (Exception e) {
                Log.d("ERROR", "" + e.getMessage());
            }
            return;
        }

        if (portion.equals("LICENSE_INFO_EDIT")) {
            try {
                Bundle bundle = new Bundle();
                bundle.putString("sl_id", ownerId);
                bundle.putString("profileID", profileId);

            } catch (Exception e) {
                Log.d("ERROR", "" + e.getMessage());
            }
            return;
        }

    }
}