package com.ismos_salt_erp.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.SalePendingListAdapter;
import com.ismos_salt_erp.databinding.FragmentSalependingDetailsBinding;
import com.ismos_salt_erp.serverResponseModel.SalePendingDetailsResponse;
import com.ismos_salt_erp.viewModel.SalependingDetailsViewModel;


public class SalependingDetailsFragment extends Fragment {

    private FragmentSalependingDetailsBinding binding;
    private String serialId;
    private SalependingDetailsViewModel salependingDetailsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_salepending_details, container, false);
        getPreviousFragmentData();
        backControl();
        /**
         * view model object create
         * */
        salependingDetailsViewModel = new ViewModelProvider(this).get(SalependingDetailsViewModel.class);

        /**
         * view for salependingDetails/order details
         * */
        if (serialId.equals(SalePendingListAdapter.serialId)){
            salependingDetailsViewModel.getSalependingDetails(getActivity()).observe(getViewLifecycleOwner(), new Observer<SalePendingDetailsResponse>() {
                @Override
                public void onChanged(SalePendingDetailsResponse salePendingDetailsResponse) {

                }
            });

        }

        return binding.getRoot();
    }

    private void backControl() {
        binding.back.setOnClickListener(v -> getActivity().onBackPressed());
    }

    private void getPreviousFragmentData() {
        serialId = getArguments().getString("serialId");
    }
}