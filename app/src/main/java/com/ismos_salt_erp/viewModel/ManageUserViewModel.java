package com.ismos_salt_erp.viewModel;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.UserPermissions;

import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageUserViewModel extends ViewModel {
    public MutableLiveData<UserPermissions> getUsersPermissions(FragmentActivity context, String employee_user_id) {
        MutableLiveData<UserPermissions> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        Call<UserPermissions> call = RetrofitClient.getInstance().getApi().getAllPermissions(
                token, vendorId, userId, employee_user_id
        );
        call.enqueue(new Callback<UserPermissions>() {
            @Override
            public void onResponse(Call<UserPermissions> call, Response<UserPermissions> response) {
                if (response.isSuccessful()) {
                    if (response == null || response.body().getStatus() == 500) {
                        liveData.postValue(null);
                        return;
                    }

                    if (response.body().getStatus() == 400 || response.body().getStatus() == 200) {
                        liveData.postValue(response.body());
                        return;
                    }


                }
                liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<UserPermissions> call, Throwable t) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }

    public MutableLiveData<DuePaymentResponse> submitPermissionUpdateInfo(
            FragmentActivity context, String employee_user_id, String employee_profile_id,
            Set<Integer> permissionList, Set<Integer> store_accessList) {

        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .submitPermissionUpdateInfo(token, vendorId, userId, employee_user_id, employee_profile_id, permissionList, store_accessList);


        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }

                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }

                    liveData.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });


        return liveData;
    }
}
