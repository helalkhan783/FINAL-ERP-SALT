package com.ismos_salt_erp.view.fragment.auth.forgot_password;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentUpdatePasswordBinding;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.ForgotPasswordViewModel;


public class UpdatePasswordFragment extends BaseFragment implements View.OnClickListener {
    FragmentUpdatePasswordBinding binding;
    private String phoneNumber;
    ForgotPasswordViewModel updatePasswordViewModel;
    private boolean isPasswordVisible = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update_password, container, false);

        getPreviousFragmentData();
        ClickListener();
        updatePasswordViewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);


        return binding.getRoot();
    }


    private void getPreviousFragmentData() {
        assert getArguments() != null;
        phoneNumber = getArguments().getString("phone");
    }

    private void ClickListener() {
        binding.confirmPasswordVisibility.setOnClickListener(this);
        binding.passwordVisibilityImgBtn.setOnClickListener(this);
        binding.Update.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.passwordVisibilityImgBtn:

                if (isPasswordVisible) {
                    binding.confirmPasswordVisibility.setImageResource(R.drawable.ic_visibility_off_grey_24dp);
                    // hide password
                    binding.newPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isPasswordVisible = false;
                } else {
                    binding.confirmPasswordVisibility.setImageResource(R.drawable.ic_visibility_grey_24dp);
                    // show password
                    binding.newPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isPasswordVisible = true;
                }
                break;

            case R.id.confirmPasswordVisibility:

                if (isPasswordVisible) {
                    binding.passwordVisibilityImgBtn.setImageResource(R.drawable.ic_visibility_off_grey_24dp);
                    // hide password
                    binding.confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isPasswordVisible = false;
                } else {
                    binding.passwordVisibilityImgBtn.setImageResource(R.drawable.ic_visibility_grey_24dp);
                    // show password
                    binding.confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isPasswordVisible = true;
                }
                break;

            case R.id.Update:

                validationAndUpdate();
                break;

        }
    }

    private void validationAndUpdate() {

        if (binding.newPassword.getText().toString().isEmpty()) {
            binding.newPassword.setError("Empty Field");
            binding.newPassword.requestFocus();
            return;
        }
        if (binding.confirmPassword.getText().toString().isEmpty()) {
            binding.confirmPassword.setError("Empty Field");
            binding.confirmPassword.requestFocus();
            return;
        }
        if (!(binding.newPassword.getText().toString().equals(binding.confirmPassword.getText().toString()))) {
            binding.newPassword.setError("didn't match");
            binding.confirmPassword.setError("didn't match");
            binding.newPassword.requestFocus();
            binding.confirmPassword.requestFocus();
            return;
        }

        /**
         * now all ok now call api for change password
         */

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();

        /**
         * secret key
         */
        String secretKey = "111890993941315280683986";
        updatePasswordViewModel.sendNewpassword(getActivity(), phoneNumber, secretKey, binding.confirmPassword.getText().toString())
                .observe(getViewLifecycleOwner(), successResponse -> {
                    progressDialog.dismiss();
                    try {
                        if (successResponse == null) {
                            errorMessage(getActivity().getApplication(), "");
                            return;
                        }

                        if (successResponse.getStatus() == 400) {
                            infoMessage(getActivity().getApplication(), "" + successResponse.getMessage());
                            return;
                        }
                        successMessage(getActivity().getApplication(), "" + successResponse.getMessage());
                        Navigation.findNavController(getView()).navigate(R.id.action_updatePasswordFragment_to_loginFragment);
                    } catch (Exception e) {
                        errorMessage(getActivity().getApplication(), "ERROR");
                    }

                });
    }


}