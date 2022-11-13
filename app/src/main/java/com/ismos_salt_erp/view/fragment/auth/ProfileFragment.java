package com.ismos_salt_erp.view.fragment.auth;

import android.graphics.Color;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentProfileBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.utils.SettingsUtil;
import com.ismos_salt_erp.view.fragment.BaseFragment;

import es.dmoral.toasty.Toasty;


public class ProfileFragment extends BaseFragment {
    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.toolbar.toolbarTitle.setText("Profile");
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });

        setPageData();
        if (!getUserId(getActivity().getApplication()).equals(getVendorId(getActivity().getApplication()))) {
            binding.updateBtn.setClickable(false);
            binding.updateBtn.setBackgroundColor(Color.GRAY);
        }

        binding.updateBtn.setOnClickListener(v -> {
            try {
                if (!getUserId(getActivity().getApplication()).equals(getVendorId(getActivity().getApplication()))) {
                    binding.updateBtn.setClickable(false);
                    binding.updateBtn.setBackgroundColor(Color.GRAY);
                    return;
                }
                Navigation.findNavController(getView()).navigate(R.id.action_profileFragment_to_updateProfileFragmentn);
            } catch (Exception e) {}
        });


        binding.passUpdate.setOnClickListener(v -> {
            try {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1130)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("phone", getPhone(getActivity().getApplication()));
                    Navigation.findNavController(getView()).navigate(R.id.action_profileFragment_to_updatePasswordFragment, bundle);
                    return;
                } else {
                    Toasty.info(getContext(), "You don't have permission", Toasty.LENGTH_LONG).show();
                }



            } catch (Exception e) {
            }
        });
        return binding.getRoot();
    }

    private void setPageData() {
        binding.phone.setText("" + getPhone(getActivity().getApplication()));
        if (getEmail(getActivity().getApplication()) != null) {
            binding.email.setText("" + getEmail(getActivity().getApplication()));
        }
        Glide.with(getContext())
                .load(getProfilePhoto(getActivity().getApplication()))
                .centerInside()
                .error(R.drawable.person)
                .placeholder(R.drawable.person)
                .into(binding.profilePic);
    }
}