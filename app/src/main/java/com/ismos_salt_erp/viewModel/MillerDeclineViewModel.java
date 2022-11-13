package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.MillerDeclineResponse;
import com.ismos_salt_erp.utils.CustomViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MillerDeclineViewModel extends CustomViewModel {

    public MutableLiveData<MillerDeclineResponse> getMillerDeclineHistory(FragmentActivity context, String pageNumber, String processType, String millerType, String selectedDivision, String selectedDistrict, String selectedThana) {
        MutableLiveData<MillerDeclineResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = getUserId(context.getApplication());
        Call<MillerDeclineResponse> call = RetrofitClient.getInstance().getApi().getMillerDecline(token,pageNumber,vendorId,userId,processType,millerType,selectedDivision,selectedDistrict,selectedThana
        );
        call.enqueue(new Callback<MillerDeclineResponse>() {
            @Override
            public void onResponse(Call<MillerDeclineResponse> call, Response<MillerDeclineResponse> response) {
                if (response.isSuccessful()) {
                   if (response == null){
                       liveData.setValue(null);
                       return;
                   } if (response.body().getStatus() == 400){
                        liveData.setValue(response.body());
                        return;
                    }
                    if (response.body().getStatus() == 200) {
                        liveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<MillerDeclineResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;
    }

}
