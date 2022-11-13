package com.ismos_salt_erp.view.fragment.production.unpacket;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentUnPacketBinding;
import com.ismos_salt_erp.view.fragment.BaseFragment;


public class UnPacketFragment extends BaseFragment {
    private FragmentUnPacketBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_un_packet, container, false);
        binding.toolbar.toolbarTitle.setText("Unpacket");
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });




        return binding.getRoot();
    }
}