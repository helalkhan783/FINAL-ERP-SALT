package com.ismos_salt_erp.viewModel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.utils.CustomViewModel;
import com.ismos_salt_erp.view.fragment.auth.sign_up.SuccessResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewUserViewModel extends CustomViewModel {


    public MutableLiveData<SuccessResponse> sendPhoneNumberForGetOtp(Application application, String phoneNumber) {
        MutableLiveData<SuccessResponse> liveData = new MutableLiveData<>();
        Call<SuccessResponse> call = RetrofitClient.getInstance().getApi().sendPhoneNumberForGetOtp(getToken(application), phoneNumber);
        call.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }
                    liveData.postValue(response.body());
                    return;
                }
                liveData.postValue(new SuccessResponse());
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {
                liveData.postValue(new SuccessResponse());
            }
        });
        return liveData;
    }


    public MutableLiveData<SuccessResponse> sendOTPCode(Application application, String phoneNumber,String otpCode){
        MutableLiveData<SuccessResponse> liveData = new MutableLiveData<>();
        Call<SuccessResponse> call = RetrofitClient.getInstance().getApi().sendPhoneNumberForGetOtp(getToken(application), phoneNumber);
        call.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }
                    liveData.postValue(response.body());
                    return;
                }
                liveData.postValue(new SuccessResponse());
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {
                liveData.postValue(new SuccessResponse());
            }
        });
        return liveData;
    }

}
