package com.ismos_salt_erp.viewModel;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.ManageAccessibilityResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserManageAccessibilityViewModel extends ViewModel {

    public MutableLiveData<ManageAccessibilityResponse> getManageAccessibility(
            FragmentActivity context) {
        MutableLiveData<ManageAccessibilityResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<ManageAccessibilityResponse> call =
                RetrofitClient.getInstance().getApi().getMangeUserAccessibility(token, vendorId, userId);
        call.enqueue(new Callback<ManageAccessibilityResponse>() {
            @Override
            public void onResponse(Call<ManageAccessibilityResponse> call, Response<ManageAccessibilityResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null ||response.body().getStatus() == 500 ) {
                        liveData.postValue(null);
                        return;
                    }

                    if (response.body().getStatus() == 400 ||response.body().getStatus() == 200 ) {
                        liveData.postValue(response.body());
                        return;
                    }


                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ManageAccessibilityResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }

}
