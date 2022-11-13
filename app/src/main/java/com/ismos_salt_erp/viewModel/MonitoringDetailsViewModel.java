package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.utils.CustomViewModel;
import com.ismos_salt_erp.serverResponseModel.MonitoringViewResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonitoringDetailsViewModel extends CustomViewModel {


    public MutableLiveData<MonitoringViewResponse> getMonitoringDetails(FragmentActivity context, String slId) {
        MutableLiveData<MonitoringViewResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());


        Call<MonitoringViewResponse> call = RetrofitClient.getInstance().getApi().getMonitorViewDetails(token, slId, vendorId,userId);
        call.enqueue(new Callback<MonitoringViewResponse>() {

            @Override
            public void onResponse(Call<MonitoringViewResponse> call, Response<MonitoringViewResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response == null) {
                            liveData.postValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
                            return;
                        }
                        liveData.postValue(response.body());
                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getMessage());
                    }
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<MonitoringViewResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;

    }


}
