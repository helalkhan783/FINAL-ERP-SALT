package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.MillerHistoryResponse;
import com.ismos_salt_erp.utils.CustomViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MillerHistoryViewModel extends CustomViewModel {
    public MutableLiveData<MillerHistoryResponse> getMillerHistory(FragmentActivity context, String pageNumber, String processType, String millerType, String selectedDivision, String selectedDistrict, String selectedThana) {
        MutableLiveData<MillerHistoryResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();
        String storeAccess = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getStoreAccess());
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String userId = getUserId(context.getApplication());

        Call<MillerHistoryResponse> call = RetrofitClient.getInstance().getApi()
                .getMillerHistory(token,pageNumber, vendorId,userId, storeId, storeAccess, profileTypeId,processType,millerType,selectedDivision,selectedDistrict,selectedThana);
        call.enqueue(new Callback<MillerHistoryResponse>() {
            @Override
            public void onResponse(Call<MillerHistoryResponse> call, Response<MillerHistoryResponse> response) {
                if (response.isSuccessful()) {

                    if (response.body() == null) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }
                    if (response.body().getStatus() == 200) {
                        liveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<MillerHistoryResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;
    }


}
