package com.ismos_salt_erp.view.fragment.monitoring;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentAddInitialQuantityBinding;
import com.ismos_salt_erp.databinding.FragmentConfirmAddNewItemBinding;
import com.ismos_salt_erp.serverResponseModel.StoreList;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.AddNewItemViewModel;

import java.util.ArrayList;
import java.util.List;


public class AddInitialQuantity extends BaseFragment {
    FragmentAddInitialQuantityBinding binding;
    String id, portion;
    private AddNewItemViewModel addNewItemViewModel;
    private List<StoreList> currentItemList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_initial_quantity, container, false);
        addNewItemViewModel = new ViewModelProvider(this).get(AddNewItemViewModel.class);
        getDataFromPreviousFragment();
        getPageData();
        if (portion != null) {
            binding.toolbar.toolbarTitle.setText(portion);
        } else {
            binding.toolbar.toolbarTitle.setText("Add new Item Initial Stock");
        }


        return binding.getRoot();
    }

    private void getPageData() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }


        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        addNewItemViewModel.getItemList(getActivity(), id)
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
                    currentItemList = new ArrayList<>();
                    currentItemList.clear();
                    currentItemList.addAll(response.getStoreLists());

                   /* binding.itemRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.itemRecyclerView.setHasFixedSize(true);
                    AddQuantityAdapter adapter = new AddQuantityAdapter(getActivity(), response.getStoreLists());
                    binding.itemRecyclerView.setAdapter(adapter);
*/

                  //  ListView my_list_view = (ListView) findViewById(R.id.mylistview);
                 /*   CustomArrayAdapter my_adapter = new CustomArrayAdapter(getActivity(), response.getStoreLists());
                    binding.ok.setAdapter(my_adapter);
*/
                });
    }


    private void getDataFromPreviousFragment() {
        assert getArguments() != null;
        id = getArguments().getString("id");
        portion = getArguments().getString("portion");
    }

}