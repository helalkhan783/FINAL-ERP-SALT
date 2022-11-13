package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.SetIodineResponse;
import com.ismos_salt_erp.utils.CustomViewModel;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IodineStoreSetViewModel extends CustomViewModel {
    public MutableLiveData<SetIodineResponse> getIodineSetStoreList(FragmentActivity context) {

        MutableLiveData<SetIodineResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String storeId = getStoreId(context.getApplication());
        String profileTypeId = getProfileTypeId(context.getApplication());
        String storeAccessId = getStoreAccessId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<SetIodineResponse> call = RetrofitClient.getInstance().getApi()
                .getIOdineStoreSet(token, userId, vendorId, storeAccessId, profileTypeId, storeId);


        call.enqueue(new Callback<SetIodineResponse>() {
            @Override
            public void onResponse(Call<SetIodineResponse> call, Response<SetIodineResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<SetIodineResponse> call, Throwable t) {
                liveData.postValue(null);
                Log.d("ERROR", "ERROR");
            }
        });


        return liveData;
    }

    public MutableLiveData<DuePaymentResponse> setIodineStore(FragmentActivity context, String setStore) {

        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String storeId = getStoreId(context.getApplication());
        String profileTypeId = getProfileTypeId(context.getApplication());
        String storeAccessId = getStoreAccessId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .setStoreForIodine(token, userId, vendorId, storeAccessId, profileTypeId, storeId, setStore);


        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null || response.body().getStatus() == 500) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(null);
                        return;
                    }
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                liveData.postValue(null);
                Log.d("ERROR", "ERROR");
            }
        });


        return liveData;
    }
}