package com.ismos_salt_erp.view.fragment.auth;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentNumberVerificationBinding;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.ForgotPasswordViewModel;
import com.ismos_salt_erp.view.fragment.auth.sign_up.SignupViewModel;
import com.ismos_salt_erp.view.fragment.auth.sign_up.SuccessResponse;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberVerificationFragment extends BaseFragment {
    private FragmentNumberVerificationBinding binding;
    private String name, phone, password, portion;
    private SmsVerifyCatcher smsVerifyCatcher;
    private String code;
    private SignupViewModel signupViewModel;
    private ForgotPasswordViewModel forgotPasswordViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_number_verification, container, false);
        getPreviousFragmentData();
        signupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);
        forgotPasswordViewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);


        smsVerifyCatcher = new SmsVerifyCatcher(getActivity(), message -> {
            code = parseCode(message);
            binding.otp.setText(code);
            if (!(binding.otp.getText().toString().isEmpty())) {
                if (code.length() == 4) {
                    /**
                     * send information for complete signup by valid OTP code
                     */
                    if (portion.equals("FORGOT_PASSWORD")) {//call from forgot password
                        sendForgotPhoneNumberForGetOtp(code);

                    }

                    if (portion.equals("SIGN_UP")) {
                        sendDataFromCompleteSignUp(code);
                    }

                }
            }
        });

        binding.cardLoginBtn.setOnClickListener(v -> {

            if (portion.equals("FORGOT_PASSWORD")) {
                sendForgotPhoneNumberForGetOtp();
            }

            if (portion.equals("SIGN_UP")) {
                sendDataFromCompleteSignUp();
            }
        });

        binding.login.setOnClickListener(v -> {
            Navigation.findNavController(getView()).navigate(R.id.action_numberVerificationFragment_to_loginFragment);
        });
        return binding.getRoot();
    }


    public String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{4}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }

    private void getPreviousFragmentData() {
        assert getArguments() != null;
        name = getArguments().getString("name");
        phone = getArguments().getString("phone");
        password = getArguments().getString("password");
        portion = getArguments().getString("portion");
    }

    private void sendForgotPhoneNumberForGetOtp(String code) {
        if (binding.otp.getText().toString().isEmpty()) {
            binding.otp.setError("Empty Otp");
            binding.otp.requestFocus();
            return;
        }
        if (binding.otp.getText().toString().length() < 4 || binding.otp.getText().toString().length() > 4) {
            binding.otp.setError("OTP length should Be 4");
            binding.otp.requestFocus();
            return;
        }

        hideKeyboard(getActivity());

        if (!(isInternetOn(getActivity()))) {
            infoMessage(requireActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        hideKeyboard(getActivity());

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        forgotPasswordViewModel.sendNumberAndOtpforForget(getActivity(), code, phone).observe(getViewLifecycleOwner(), new Observer<SuccessResponse>() {
            @Override
            public void onChanged(SuccessResponse successResponse) {
                progressDialog.dismiss();
                try {
                    if (successResponse.getStatus() != 200) {
                        errorMessage(getActivity().getApplication(), "" + successResponse.getMessage());
                        return;
                    }
                    /**
                     * All ok now we can update our password from update password fragment
                     */
                    Bundle bundle = new Bundle();
                    bundle.putString("phone", phone);
                    Navigation.findNavController(getView()).navigate(R.id.action_numberVerificationFragment_to_updatePasswordFragment, bundle);
                } catch (Exception e) {
                    errorMessage(getActivity().getApplication(), "ERROR");
                }
            }
        });

    }

    private void sendForgotPhoneNumberForGetOtp() {
        if (binding.otp.getText().toString().isEmpty()) {
            binding.otp.setError("Empty Otp");
            binding.otp.requestFocus();
            return;
        }
        if (binding.otp.getText().toString().length() < 4 || binding.otp.getText().toString().length() > 4) {
            binding.otp.setError("OTP length should Be 4");
            binding.otp.requestFocus();
            return;
        }

        hideKeyboard(getActivity());

        if (!(isInternetOn(getActivity()))) {
            infoMessage(requireActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        hideKeyboard(getActivity());

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        forgotPasswordViewModel.sendNumberAndOtpforForget(getActivity(), binding.otp.getText().toString(), phone).observe(getViewLifecycleOwner(), new Observer<SuccessResponse>() {
            @Override
            public void onChanged(SuccessResponse successResponse) {
                progressDialog.dismiss();
                try {
                 if (successResponse == null){// null for null and 500
                     errorMessage(getActivity().getApplication(),"Something wrong");
                     return;
                 }
                    if (successResponse.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + successResponse.getMessage());
                        return;
                    }
                    /**
                     * All ok now we can update our password from update password fragment
                     */
                    Bundle bundle = new Bundle();
                    bundle.putString("phone", phone);
                    Navigation.findNavController(getView()).navigate(R.id.action_numberVerificationFragment_to_updatePasswordFragment, bundle);
                } catch (Exception e) {
                    errorMessage(getActivity().getApplication(), "ERROR");
                }
            }
        });

    }

    private void sendDataFromCompleteSignUp() {

        if (binding.otp.getText().toString().isEmpty()) {
            binding.otp.setError("Empty Otp");
            binding.otp.requestFocus();
            return;
        }
        if (binding.otp.getText().toString().length() < 4 || binding.otp.getText().toString().length() > 4) {
            binding.otp.setError("OTP length should Be 4");
            binding.otp.requestFocus();
            return;
        }


        if (!(isInternetOn(getActivity()))) {
            infoMessage(requireActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        hideKeyboard(getActivity());

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        signupViewModel.sendOtpForVerifyNumber(getActivity(), binding.otp.getText().toString(), phone)
                .observe(getViewLifecycleOwner(), successResponse -> {
                    progressDialog.dismiss();
                    try {
                        if (successResponse == null) {
                            errorMessage(getActivity().getApplication(), "Something Wrong");
                            return;
                        }
                        if (successResponse.getStatus() == 400) {
                            infoMessage(getActivity().getApplication(), "" + successResponse.getMessage());
                            return;
                        }
                        signupViewModel.senSignUpIformation(getActivity(), name, phone, password)
                                .observe(getViewLifecycleOwner(), response -> {
                                    try {
                                        if (response == null) {
                                            errorMessage(getActivity().getApplication(), "Something Wrong");
                                            return;
                                        }
                                        if (response.getStatus() == 400) {
                                            infoMessage(getActivity().getApplication(), "" + response.getMessage());
                                            return;
                                        }
                                        successMessage(getActivity().getApplication(), "" + response.getMessage());
                                        Navigation.findNavController(getView()).navigate(R.id.action_numberVerificationFragment_to_loginFragment);
                                    } catch (Exception e) {
                                        errorMessage(getActivity().getApplication(), "ERROR");
                                    }
                                });
                    } catch (Exception e) {
                        errorMessage(getActivity().getApplication(), "ERROR");
                    }

                });
    }

    private void sendDataFromCompleteSignUp(String code) {

        if (binding.otp.getText().toString().isEmpty()) {
            binding.otp.setError("Empty Otp");
            binding.otp.requestFocus();
            return;
        }
        if (binding.otp.getText().toString().length() < 4 || binding.otp.getText().toString().length() > 4) {
            binding.otp.setError("OTP length should Be 4");
            binding.otp.requestFocus();
            return;
        }


        if (!(isInternetOn(getActivity()))) {
            infoMessage(requireActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        hideKeyboard(getActivity());

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        signupViewModel.sendOtpForVerifyNumber(getActivity(), code, phone)
                .observe(getViewLifecycleOwner(), successResponse -> {
                    progressDialog.dismiss();
                    try {
                        if (successResponse == null) {
                            errorMessage(getActivity().getApplication(), "Something Wrong");
                            return;
                        }
                        if (successResponse.getStatus() == 400) {
                            infoMessage(getActivity().getApplication(), successResponse.getMessage());
                            return;
                        }
                        signupViewModel.senSignUpIformation(getActivity(), name, phone, password)
                                .observe(getViewLifecycleOwner(), response -> {
                                    try {
                                        if (response == null) {
                                            errorMessage(getActivity().getApplication(), "Something Wrong");
                                            return;
                                        }
                                        if (response.getStatus() == 400) {
                                            infoMessage(getActivity().getApplication(), response.getMessage());
                                            return;
                                        }
                                        Navigation.findNavController(getView()).navigate(R.id.action_numberVerificationFragment_to_loginFragment);
                                    } catch (Exception e) {
                                        errorMessage(getActivity().getApplication(), "ERROR");
                                    }
                                });
                    } catch (Exception e) {
                        errorMessage(getActivity().getApplication(), "ERROR");
                    }

                });
    }


}