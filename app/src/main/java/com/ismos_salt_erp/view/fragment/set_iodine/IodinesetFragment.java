package com.ismos_salt_erp.view.fragment.set_iodine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.IodineSetStoreAdapter;
import com.ismos_salt_erp.databinding.FragmentIodinesetBinding;
import com.ismos_salt_erp.serverResponseModel.SetIodineStoreList;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.viewModel.IodineStoreSetViewModel;

import java.util.ArrayList;
import java.util.List;


public class IodinesetFragment extends AddUpDel implements GetData {
    FragmentIodinesetBinding binding;
    IodineStoreSetViewModel iodineStoreSetViewModel;
    String storeId;
    List<SetIodineStoreList> storeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_iodineset, container, false);
        iodineStoreSetViewModel = new ViewModelProvider(this).get(IodineStoreSetViewModel.class);
        binding.toolbar.toolbarTitle.setText("Set Iodine Store");
        getData();
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        binding.saveBtn.setOnClickListener(v -> {


            if (storeId == null){
                message(getString(R.string.store_mes));
                return;
            }

            showDialog(getString(R.string.add_dialog_title));

        });
        return binding.getRoot();

    }


    private void saveStore() {

        iodineStoreSetViewModel.setIodineStore(getActivity(), storeId).observe(getViewLifecycleOwner(), response -> {
            if (response == null) {
                infoMessage(getActivity().getApplication(), "SomeThing Wrong");
                return;
            }
            if (response.getStatus() == 400) {
                errorMessage(getActivity().getApplication(), response.getMessage());
                getActivity().onBackPressed();
                return;
            }
            successMessage(getActivity().getApplication(), response.getMessage());
            getActivity().onBackPressed();
        });
    }

    private void getData() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        iodineStoreSetViewModel.getIodineSetStoreList(getActivity()).observe(getViewLifecycleOwner(), response -> {

            progressDialog.dismiss();
            if (response == null) {
                infoMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            if (response.getStatus() == 400) {
                errorMessage(getActivity().getApplication(), response.getMessage());
                return;
            }

            if (response.getStatus() == 200) {
                if (response.getList().isEmpty() || response.getList() == null) {
                    binding.iodineSetRv.setVisibility(View.GONE);
                    binding.noDataFound.setVisibility(View.VISIBLE);
                    binding.saveBtn.setVisibility(View.GONE);
                    return;
                }
                storeList = new ArrayList<>();
                storeList.addAll(response.getList());
                IodineSetStoreAdapter adapter = new IodineSetStoreAdapter(getActivity(), storeList, getView(), this);
                binding.iodineSetRv.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.iodineSetRv.setAdapter(adapter);
                return;
            }
        });
    }

    @Override
    public void getData(int position, String id) {
        this.storeId = id;

    }

    @Override
    public void save() {

            saveStore();

    }

    @Override
    public void imageUri(Intent uri) {

    }
}