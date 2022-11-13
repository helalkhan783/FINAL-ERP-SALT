package com.ismos_salt_erp.view.fragment.miller.addNewMiller;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentMillerQcInformationBinding;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.MillerProfileInfoViewModel;

public class MillerQcInformation extends BaseFragment {
    private FragmentMillerQcInformationBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_miller_qc_information, container, false);


        return binding.getRoot();
    }
}