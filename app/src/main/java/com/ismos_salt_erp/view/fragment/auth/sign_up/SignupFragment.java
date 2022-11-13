package com.ismos_salt_erp.view.fragment.auth.sign_up;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentSignupBinding;
import com.ismos_salt_erp.view.fragment.BaseFragment;

public class SignupFragment extends BaseFragment implements View.OnClickListener {
    private FragmentSignupBinding binding;
    private SignupViewModel signupViewModel;
    private boolean isPasswordVisible = false;
    private boolean isCheckedCondition = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false);

        signupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);

        setOnClick();

        binding.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                isCheckedCondition = true;
                return;
            }
            isCheckedCondition = false;
        });


        return binding.getRoot();
    }

    /**
     * setOnClick & onClick  both method for clickListener
     **/
    private void setOnClick() {
        binding.passwordVisibilityImgBtn.setOnClickListener(this);
        binding.confirmPasswordVisibility.setOnClickListener(this);
        binding.signUpBtn.setOnClickListener(this);
        binding.tvLogin2.setOnClickListener(this);
        binding.terms.setOnClickListener(this);
        binding.privacy.setOnClickListener(this);
        binding.helpCenter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.passwordVisibilityImgBtn:
                passwordVisivility();
                break;

            case R.id.confirmPasswordVisibility:
                conFirmPasswordVisivility();
                break;

            case R.id.signUpBtn:
                hideKeyboard(getActivity());
                validation();
                break;

            case R.id.tv_login_2:
                Navigation.findNavController(getView()).navigate(R.id.action_signupFragment_to_loginFragment);
                break;


            case R.id.terms:
                Navigation.findNavController(getView()).navigate(R.id.action_signupFragment_to_termsAndPrivacyFragment);
                break;


            case R.id.privacy:
                Navigation.findNavController(getView()).navigate(R.id.action_signupFragment_to_termsAndPrivacyFragment);
                break;

            case R.id.helpCenter:
                Navigation.findNavController(getView()).navigate(R.id.action_signupFragment_to_termsAndPrivacyFragment);
                break;


        }


    }


    /**
     * for confirm password visiviloty
     **/
    private void conFirmPasswordVisivility() {
        if (isPasswordVisible) {
            binding.passwordVisibilityImgBtn.setImageResource(R.drawable.ic_visibility_off_grey_24dp);
            // hide password
            binding.signupRetypePwEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isPasswordVisible = false;
        } else {
            binding.passwordVisibilityImgBtn.setImageResource(R.drawable.ic_visibility_grey_24dp);
            // show password
            binding.signupRetypePwEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isPasswordVisible = true;
        }
    }

    /**
     * for password visiviloty
     **/
    private void passwordVisivility() {
        if (isPasswordVisible) {
            binding.confirmPasswordVisibility.setImageResource(R.drawable.ic_visibility_off_grey_24dp);
            // hide password
            binding.signupPasswordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isPasswordVisible = false;
        } else {
            binding.confirmPasswordVisibility.setImageResource(R.drawable.ic_visibility_grey_24dp);
            // show password
            binding.signupPasswordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isPasswordVisible = true;
        }
    }

    /**
     * for validation
     **/
    private void validation() {
        if (binding.signupNameEt.getText().toString().isEmpty()) {
            binding.signupNameEt.setError("Empty Name");
            binding.signupNameEt.requestFocus();
            return;
        }
        if (!(binding.signupNPhoneEt.getText().toString().matches("(^([+]{1}[8]{2}|0088)?(01){1}[3-9]{1}\\d{8})$"))) {
            binding.signupNPhoneEt.setError("Invalid Phone Number");
            binding.signupNPhoneEt.requestFocus();
            return;
        }
        if (binding.signupPasswordEt.getText().toString().isEmpty()) {
            binding.signupPasswordEt.setError("Password Mandatory");
            binding.signupPasswordEt.requestFocus();
            return;
        }
        if (binding.signupRetypePwEt.getText().toString().isEmpty()) {
            binding.signupRetypePwEt.setError("Password Mandatory");
            binding.signupRetypePwEt.requestFocus();
            return;
        }


        if (!(binding.signupPasswordEt.getText().toString().equals(binding.signupRetypePwEt.getText().toString()))) {
            binding.signupPasswordEt.requestFocus();
            binding.signupRetypePwEt.requestFocus();
            infoMessage(getActivity().getApplication(), "Invalid Password");
            return;
        }
        if (!isCheckedCondition) {
            binding.checkBox.setError("Please Select the CheckBox For Agree With our trams & Conditions");
            binding.checkBox.requestFocus();
            return;
        }
        sendOtpToServer();

    }

    /**
     * for otp get
     **/
    private void sendOtpToServer() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(requireActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        signupViewModel.getOtpByPhone(getActivity(), binding.signupNPhoneEt.getText().toString()).observe(getViewLifecycleOwner(), new Observer<SuccessResponse>() {
            @Override
            public void onChanged(SuccessResponse otpResponse) {
                progressDialog.dismiss();
                try {
                    if (otpResponse == null) {
                        errorMessage(requireActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (otpResponse.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + otpResponse.getMessage());
                        return;
                    }
                    //infoMessage(requireActivity().getApplication(), otpResponse.getMessage());
                    Bundle bundle = new Bundle();
                    bundle.putString("name", binding.signupNameEt.getText().toString());
                    bundle.putString("phone", binding.signupNPhoneEt.getText().toString());
                    bundle.putString("password", binding.signupRetypePwEt.getText().toString());
                    bundle.putString("portion", "SIGN_UP");
                    Navigation.findNavController(getView()).navigate(R.id.action_signupFragment_to_numberVerificationFragment, bundle);

                } catch (Exception e) {
                    errorMessage(getActivity().getApplication(), "ERROR");
                }
            }
        });

    }
}