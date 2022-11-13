package com.ismos_salt_erp.viewModel;

import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.PermissionResponse;
import com.ismos_salt_erp.utils.CustomViewModel;
import com.ismos_salt_erp.view.fragment.ManagementFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentPermissionViewModel extends CustomViewModel {
    public MutableLiveData<PermissionResponse> getCurrentUserRealtimePermissions(String token, String userId,String userName) {
        MutableLiveData<PermissionResponse> liveData = new MutableLiveData<>();
       // String userName =getUserName(new ManagementFragment().getActivity().getApplication());
        Call<PermissionResponse> call = RetrofitClient.getInstance().getApi().getCurrentUserPermissions(token, userId, "1", userName);
        call.enqueue(new Callback<PermissionResponse>() {
            @Override
            public void onResponse(Call<PermissionResponse> call, Response<PermissionResponse> response) {
                if (response == null) {
                    liveData.postValue(null);
                    return;
                }
                if (response.body().getStatus() == 400) {
                    liveData.postValue(response.body());
                    return;
                }
                liveData.postValue(response.body());
                return;
            }

            @Override
            public void onFailure(Call<PermissionResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });


        return liveData;
    }
}
