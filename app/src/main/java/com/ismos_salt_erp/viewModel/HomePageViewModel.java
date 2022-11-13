package com.ismos_salt_erp.viewModel;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.HomePageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePageViewModel extends ViewModel {
    public MutableLiveData<HomePageResponse> getHomePageData(FragmentActivity context) {
        MutableLiveData<HomePageResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String profileId = PreferenceManager.getInstance(context).getUserCredentials().getProfileId();


        Call<HomePageResponse> call =
                RetrofitClient.getInstance().getApi().getHomePageData(token, vendorId, userId,profileId);
        call.enqueue(new Callback<HomePageResponse>() {
            @Override
            public void onResponse(Call<HomePageResponse> call, Response<HomePageResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null || response.body().getStatus() == 500) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }
                    liveData.postValue(response.body());
                }

                else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<HomePageResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });
        return liveData;

    }
}
