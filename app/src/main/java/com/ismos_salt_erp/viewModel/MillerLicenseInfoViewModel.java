package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.MillerLicenseInfoEditResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MillerLicenseInfoViewModel extends ViewModel {

    public MutableLiveData<MillerLicenseInfoEditResponse> getMillerPreviousOwnerLicenseInfo(FragmentActivity context, String slid) {
        MutableLiveData<MillerLicenseInfoEditResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();

        Call<MillerLicenseInfoEditResponse> call = RetrofitClient.getInstance().getApi().getLicensePreviousInfo(token,slid);
        call.enqueue(new Callback<MillerLicenseInfoEditResponse>() {
            @Override
            public void onResponse(Call<MillerLicenseInfoEditResponse> call, Response<MillerLicenseInfoEditResponse> response) {
                try {
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
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                    liveData.postValue(null);
                    return;
                }
            }

            @Override
            public void onFailure(Call<MillerLicenseInfoEditResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }


}
