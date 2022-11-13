package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.LoginResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    public MutableLiveData<LoginResponse> sendLoginRequest(FragmentActivity context, String userName, String password, String deviceId) {

        MutableLiveData<LoginResponse> liveData = new MutableLiveData<>();

        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().primaryLogin(userName, password, deviceId);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus() == null) {
                        liveData.postValue(null);
                        return;
                    }

                    if (response.body().getStatus()!=200){
                        liveData.postValue(null);
                        return;
                    }

                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());
                        PreferenceManager.getInstance(context).saveUserCredentials(response.body()); // save the response to the shared preference
                        /**
                         *now save the user permission
                         */
                        String permission = String.valueOf(response.body().getPermissions());
                        Log.d("permissions", permission);
                        /**
                         * if have any permission of the current user
                         */
                        if (!permission.isEmpty()) {
                            String[] stringTokens = permission.split(",");


                          /*  int[] intArr = Stream.of(stringTokens)
                                    .mapToInt(strToken -> Integer.parseInt(strToken))
                                    .toArray();
                            System.out.println(Arrays.toString(intArr));*/


                            int[] numbers = new int[stringTokens.length];
                            for (int i = 0; i < stringTokens.length; i++) {
                                // Note that this is assuming valid input
                                // If you want to check then add a try/catch
                                // and another index for the numbers if to continue adding the others (see below)
                                numbers[i] = Integer.parseInt(stringTokens[i]);
                            }


                            /**(
                             * @Note now save the permission in local database
                             */
                            boolean permissions = PreferenceManager.getInstance(context).savePermissionList(Arrays.toString(numbers));
                            if (permissions) {
                                Log.d(" Response", "Save All Permission");//ok
                            }
                        }
                        /**
                         * if don't have any permission of the current user then set permission is {0}
                         */
                        else {
                            int[] intArr2 = {0};//set permission 0 (means don't have any permission  of the current user)
                            PreferenceManager.getInstance(context).savePermissionList(Arrays.toString(intArr2));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                Log.d("ERROR", t.getMessage());
            }
        });
        return liveData;
    }
}
