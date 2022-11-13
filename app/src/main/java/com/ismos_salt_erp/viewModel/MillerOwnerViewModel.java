package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.OwnerDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MillerOwnerViewModel extends ViewModel {

    public MutableLiveData<OwnerDetailsResponse> getOwnerDetailsResponse(FragmentActivity context, String slid) {
        MutableLiveData<OwnerDetailsResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();


        Call<OwnerDetailsResponse> call = RetrofitClient.getInstance().getApi().getOwnerDetailsResponse(token, slid);
        call.enqueue(new Callback<OwnerDetailsResponse>() {
            @Override
            public void onResponse(Call<OwnerDetailsResponse> call, Response<OwnerDetailsResponse> response) {
                try {
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
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<OwnerDetailsResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }
}
