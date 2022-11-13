package com.ismos_salt_erp.view.fragment.miller;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.utils.CustomViewModel;
import com.ismos_salt_erp.serverResponseModel.view_details.MIllerViewResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MillerViewDetailsViewModel extends CustomViewModel {

    public MutableLiveData<MIllerViewResponse> getMillerViewDetails(FragmentActivity context, String id) {
        MutableLiveData<MIllerViewResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());


        Call<MIllerViewResponse> call = RetrofitClient.getInstance().getApi().getMillerViewDetails(token, id);
        call.enqueue(new Callback<MIllerViewResponse>() {
            @Override
            public void onResponse(Call<MIllerViewResponse> call, Response<MIllerViewResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.setValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.setValue(response.body());
                        return;
                    }

                    if (response.body().getStatus() == 200) {
                        liveData.setValue(response.body());
                        return;
                    }
                } else {
                    liveData.postValue(null);
                    return;
                }

            }

            @Override
            public void onFailure(Call<MIllerViewResponse> call, Throwable t) {
                Log.d("Error", "onFailure: " + t.getMessage());
                liveData.postValue(null);
                return;
            }
        });

        return liveData;


    }

    public MutableLiveData<DuePaymentResponse> approveMiller(FragmentActivity context, String slId, String reviewStatus, String s) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String vendorId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());
        String profileType = getProfileTypeId(context.getApplication());
        String token = getToken(context.getApplication());

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().millerApprove(token, vendorId, userId, profileType, slId, reviewStatus);


        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                try {
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
                            liveData.postValue(response.body());
                            return;

                        }

                    }
                } catch (Exception e) {
                    Log.d("Error", e.getMessage());
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                liveData.postValue(null);
                return;
            }
        });

        return liveData;

    }
}
