package com.ismos_salt_erp.view.fragment.auth.forgot_password;

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
import com.ismos_salt_erp.databinding.FragmentForgorPwBinding;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.view.fragment.auth.sign_up.SuccessResponse;
import com.ismos_salt_erp.viewModel.ForgotPasswordViewModel;

import org.jetbrains.annotations.NotNull;


public class ForgotPasswordFragment extends BaseFragment {
    private FragmentForgorPwBinding binding;
    private ForgotPasswordViewModel forgotPasswordViewModel;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgor_pw, container, false);
        forgotPasswordViewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);

        binding.submmitBtn.setOnClickListener(v -> {
            numberValidation();
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.show();
            forgotPasswordViewModel.sendNumberForForgotPassword(getActivity(),binding.phoneNumber.getText().toString()).observe(getViewLifecycleOwner(), new Observer<SuccessResponse>() {
                @Override
                public void onChanged(SuccessResponse successResponse) {
                    progressDialog.dismiss();
                    try {
                        if (successResponse.getStatus() != 200) {
                            errorMessage(getActivity().getApplication(), "" + successResponse.getMessage());
                            return;
                        }
                        if (successResponse.getStatus() == 200) {
                            Bundle bundle = new Bundle();
                            bundle.putString("phone", binding.phoneNumber.getText().toString());
                            bundle.putString("portion", "FORGOT_PASSWORD");
                               Navigation.findNavController(getView()).navigate(R.id.action_forgotPasswordFragment_to_numberVerificationFragment, bundle);
                            return;
                        }
                        infoMessage(requireActivity().getApplication(), successResponse.getMessage());
                    } catch (Exception e) {
                        errorMessage(getActivity().getApplication(), "ERROR");
                    }
                }
            });

        });
binding.login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Navigation.findNavController(getView()).navigate(R.id.action_forgotPasswordFragment_to_loginFragment);
    }
});
        return binding.getRoot();
    }

    private void numberValidation() {
        String phone  =   binding.phoneNumber.getText().toString();
        if (phone.isEmpty()){
            binding.phoneNumber.setError("Empty field");
            binding.phoneNumber.requestFocus();
        }
        if (!(phone.matches("(^([+]{1}[8]{2}|0088)?(01){1}[3-9]{1}\\d{8})$"))) {
            binding.phoneNumber.setError("Invalid Phone Number");
            binding.phoneNumber.requestFocus();
            return;
        }
        if (!(isInternetOn(getActivity()))) {
            infoMessage(requireActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }

    }

}