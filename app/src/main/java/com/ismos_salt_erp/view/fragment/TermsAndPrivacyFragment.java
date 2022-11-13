package com.ismos_salt_erp.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentTermsAndPrivacyBinding;

public class TermsAndPrivacyFragment extends Fragment {
    FragmentTermsAndPrivacyBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_terms_and_privacy, container, false);

        binding.toolbar.toolbarTitle.setText("Our Privacy Details");


        binding.toolbar.setClickHandle(() -> getActivity().onBackPressed());

        return binding.getRoot();
    }
}