package com.ismos_salt_erp.view.fragment.items;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentEditItemPacketBinding;
import com.ismos_salt_erp.serverResponseModel.AddNewItemResponse;
import com.ismos_salt_erp.serverResponseModel.AddNewItemUnit;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.AddNewItemViewModel;
import com.ismos_salt_erp.viewModel.AddPacketItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class EditItemPacketFragment extends BaseFragment {
    private FragmentEditItemPacketBinding binding;
    private AddNewItemViewModel addNewItemViewModel;
    private AddPacketItemViewModel addPacketItemViewModel;

    /**
     * for unit
     */
    private List<AddNewItemUnit> unitResponseList;
    private List<String> unitNameList;

    String name, quantity, selectedPrimaryUnit, productId, price, title, baseUnit, unit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_item_packet, container, false);
        addNewItemViewModel = new ViewModelProvider(this).get(AddNewItemViewModel.class);
        addPacketItemViewModel = new ViewModelProvider(this).get(AddPacketItemViewModel.class);
        getPreviousFragmentData();
        getPageData();
        binding.toolbar.toolbarTitle.setText("Update Packet");
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        try {
            binding.itemName.setText(name);
            binding.quantity.setText(quantity);
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }

        binding.updateBtn.setOnClickListener(v -> validation());

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

        return binding.getRoot();
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
        addPacketItemViewModel.updatePacketItem(getActivity(), productId, binding.itemName.getText().toString(), binding.quantity.getText().toString(), selectedPrimaryUnit, price).observe(getViewLifecycleOwner(), new Observer<DuePaymentResponse>() {
            @Override
            public void onChanged(DuePaymentResponse response) {
                if (response == null || response.getStatus() == 500) {
                    errorMessage(getActivity().getApplication(), "Something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(requireActivity().getApplication(), response.getMessage());
                    return;
                }
                hideKeyboard(getActivity());
                successMessage(getActivity().getApplication(), "" + response.getMessage());
                getActivity().onBackPressed();
            }
        });


    }

    private void getPreviousFragmentData() {
        try {
            name = getArguments().getString("name");
            quantity = getArguments().getString("quantity");
            productId = getArguments().getString("productId");
            price = getArguments().getString("price");
            unit = getArguments().getString("unit");
            baseUnit = getArguments().getString("baseUnit");
            title = getArguments().getString("title");
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }


    private void getPageData() {
        addNewItemViewModel.getAddNewPageData(getActivity()).observe(getViewLifecycleOwner(), new Observer<AddNewItemResponse>() {
            @Override
            public void onChanged(AddNewItemResponse response) {
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

                /**
                 * set previous data
                 */

                for (int i = 0; i < unitResponseList.size(); i++) {
                    if (unitResponseList.get(i).getiD() != null) {
                        if (unitResponseList.get(i).getiD().equals(baseUnit)) {
                            binding.unit.setSelection(i);
                            selectedPrimaryUnit = String.valueOf(equals(unitResponseList.get(i).getiD()));
                        }
                    }
                }
            }
        });
    }

}