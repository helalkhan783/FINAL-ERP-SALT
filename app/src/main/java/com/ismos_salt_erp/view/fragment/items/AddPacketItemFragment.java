package com.ismos_salt_erp.view.fragment.items;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.ItemPacketListAdapter;
import com.ismos_salt_erp.databinding.FragmentAddItemPacketBinding;
import com.ismos_salt_erp.serverResponseModel.AddNewItemUnit;
import com.ismos_salt_erp.utils.ExpandableView;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.AddNewItemViewModel;
import com.ismos_salt_erp.viewModel.AddPacketItemViewModel;
import com.ismos_salt_erp.viewModel.ItemListViewModel;

import java.util.ArrayList;
import java.util.List;


public class AddPacketItemFragment extends BaseFragment {
    private FragmentAddItemPacketBinding binding;
    private String title, productId;
    boolean click = false;
    private AddNewItemViewModel addNewItemViewModel;
    private AddPacketItemViewModel addPacketItemViewModel;
    private ItemListViewModel itemListViewModel;
    /**
     * for unit
     */
    private List<AddNewItemUnit> unitResponseList;
    private List<String> unitNameList;

    String selectedPrimaryUnit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_item_packet, container, false);
        addNewItemViewModel = new ViewModelProvider(this).get(AddNewItemViewModel.class);
        addPacketItemViewModel = new ViewModelProvider(this).get(AddPacketItemViewModel.class);
        itemListViewModel = new ViewModelProvider(this).get(ItemListViewModel.class);
        getPreviousFragmentData();
        binding.toolbar.toolbarTitle.setText("Add Packet For " + title);

        binding.toolbar.setClickHandle(() -> getActivity().onBackPressed());
        binding.toolbar.addBtn.setVisibility(View.VISIBLE);


        binding.toolbar.addBtn.setOnClickListener(v -> {
            if (!getProfileTypeId(getActivity().getApplication()).equals("7")) {
                infoMessage(getActivity().getApplication(),  PermissionUtil.permissionMessage);
                return;
            }
       new ExpandableView(getActivity(),binding.expandableView).response();
        });

        getPageData();
        getItemPacketLisT();

        binding.unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPrimaryUnit = unitResponseList.get(position).getiD();
                binding.unit.setEnableErrorLabel(false);
                binding.unit.setErrorText("Empty");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.saveBtn.setOnClickListener(v -> validation());

        return binding.getRoot();
    }

    private void getItemPacketLisT() {
        if (!isInternetOn(getActivity())) {
            Toast.makeText(getContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        itemListViewModel.getItemPacketList(getActivity(), productId).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            if (response == null) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), "" + response.getMessage());
                return;
            }
            if (response.getLists().isEmpty() || response.getLists() == null) {
                binding.itemPacketListRv.setVisibility(View.GONE);
                binding.dataNotFound.setVisibility(View.VISIBLE);
            }

            /**
             * setData in recyclerview
             */

            ItemPacketListAdapter adapter = new ItemPacketListAdapter(getActivity(), response.getLists(),getView(),productId,title);
            binding.itemPacketListRv.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.itemPacketListRv.setAdapter(adapter);


        });
    }

    private void validation() {

        if (binding.itemName.getText().toString().isEmpty()) {
            binding.itemName.setError("Empty");
            binding.itemName.requestFocus();
            return;
        }

        if (binding.quantity.getText().toString().isEmpty()) {
            binding.quantity.setError("Empty");
            binding.quantity.requestFocus();
            return;
        }
        if (selectedPrimaryUnit == null) {
            binding.unit.setEnableErrorLabel(true);
            binding.unit.setErrorText("Empty");
            binding.unit.requestFocus();
        }

        addPacketItemViewModel.addItemPacket(getActivity(), productId, binding.itemName.getText().toString(), binding.quantity.getText().toString(), selectedPrimaryUnit,
                "1", "0").observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(requireActivity().getApplication(), response.getMessage());
                        return;
                    }
                    successMessage(getActivity().getApplication(), "" + response.getMessage());
                    binding.dataNotFound.setVisibility(View.GONE);
                    binding.itemPacketListRv.setVisibility(View.VISIBLE);
                    getItemPacketLisT();
                    binding.expandableView.setExpanded(false);
                });
    }

    private void getPageData() {
        addNewItemViewModel.getAddNewPageData(getActivity()).observe(getViewLifecycleOwner(), response -> {
            if (response == null) {
                errorMessage(getActivity().getApplication(), "Something Wrong");
                return;
            }
            if (response.getStatus() == 400) {
                infoMessage(getActivity().getApplication(), "" + response.getMessage());
                return;
            }

            unitResponseList = new ArrayList<>();
            unitResponseList.clear();
            unitNameList = new ArrayList<>();
            unitResponseList.clear();
            unitResponseList.addAll(response.getUnit());
            for (int i = 0; i < response.getUnit().size(); i++) {
                unitNameList.add(response.getUnit().get(i).getName());
            }
            binding.unit.setItem(unitNameList);
        });
    }


    private void getPreviousFragmentData() {

        try {

            title = getArguments().getString("categoryNameForTitle");
            productId = getArguments().getString("id");

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }


    }
}