package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.VarientResponse;
import com.ismos_salt_erp.utils.CustomViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VarientViewModel extends CustomViewModel {

    public MutableLiveData<VarientResponse> getVarientList(FragmentActivity context, String refProductID) {
        MutableLiveData<VarientResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String vendoId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());
        String storeId = getStoreId(context.getApplication());


        Call<VarientResponse> call = RetrofitClient.getInstance().getApi().getVarientList(token,userId,vendoId,refProductID);
        call.enqueue(new Callback<VarientResponse>() {
            @Override
            public void onResponse(Call<VarientResponse> call, Response<VarientResponse> response) {
                if (response.isSuccessful()) {

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
                }
            }

            @Override
            public void onFailure(Call<VarientResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;

    }

}
