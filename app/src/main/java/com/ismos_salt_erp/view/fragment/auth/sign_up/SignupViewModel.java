package com.ismos_salt_erp.view.fragment.auth.sign_up;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.viewModel.CustomerViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupViewModel extends CustomerViewModel {

    /**
     * getOtp by the  phoneNumber  ,
     */

    public MutableLiveData<SuccessResponse> getOtpByPhone(FragmentActivity context, String primamryPhone) {
        MutableLiveData<SuccessResponse> liveData = new MutableLiveData<>();
        ProgressDialog progressDialog = new ProgressDialog(context);
        Call<SuccessResponse> call = RetrofitClient.getInstance().getApi().sendPhoneNumberForVerify(primamryPhone);
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
                    if (response.body().getStatus() == 200) {
                        Log.d("TYPE", new Gson().toJson(response.body()));
                        liveData.postValue(response.body());
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                progressDialog.dismiss();
                Toast.makeText(context, "Something Wrong Contact to Support \n" + this.getClass().getSimpleName() + " " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        return liveData;
    }


    /**
     * send otp & phone for phone verify
     **/

    public MutableLiveData<SuccessResponse> sendOtpForVerifyNumber(FragmentActivity context, String code, String primamryPhone) {
        MutableLiveData<SuccessResponse> liveData = new MutableLiveData<>();
        Call<SuccessResponse> call = RetrofitClient.getInstance().getApi().sendOtpForNumberVerify(code, primamryPhone);
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
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                Toast.makeText(context, "Something Wrong Contact to Support \n" + this.getClass().getSimpleName() + " " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        return liveData;
    }

    /**
     * submit new user data
     */
    public MutableLiveData<SuccessResponse> senSignUpIformation(FragmentActivity context, String name, String primamryPhone, String password) {
        MutableLiveData<SuccessResponse> liveData = new MutableLiveData<>();
        Call<SuccessResponse> call = RetrofitClient.getInstance().getApi().sendSignupInfo(name, primamryPhone, password, primamryPhone);
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
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                Toast.makeText(context, "Something Wrong Contact to Support \n" + this.getClass().getSimpleName() + " " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return liveData;
    }


}
