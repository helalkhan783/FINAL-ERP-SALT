package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.IodizationPenDingResponse;
import com.ismos_salt_erp.utils.CustomViewModel;
import com.ismos_salt_erp.serverResponseModel.IodizationHistoryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IodizationHostoryViewModel extends CustomViewModel {
    public MutableLiveData<IodizationPenDingResponse> getPendingList(FragmentActivity context, String page, String startDate, String endDate, String itemId, String enterpriseID) {

        MutableLiveData<IodizationPenDingResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String userId = getUserId(context.getApplication());
        String vendorId = getVendorId(context.getApplication());

        Call<IodizationPenDingResponse> call = RetrofitClient.getInstance().getApi().pendingList(token,page,userId, vendorId,startDate,endDate,itemId,enterpriseID);
        call.enqueue(new Callback<IodizationPenDingResponse>() {
            @Override
            public void onResponse(Call<IodizationPenDingResponse> call, Response<IodizationPenDingResponse> response) {
                if (response.isSuccessful()) {

                    if (response== null) {
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
            public void onFailure(Call<IodizationPenDingResponse> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;
    }

    public MutableLiveData<IodizationHistoryResponse> getIodizationHistory(FragmentActivity context, String page, String startDate, String endDate, String itemId, String enterpriseID) {

        MutableLiveData<IodizationHistoryResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String userId = getUserId(context.getApplication());
        String vendorId = getVendorId(context.getApplication());

        Call<IodizationHistoryResponse> call = RetrofitClient.getInstance().getApi().getIodizationHistory(token,page,userId, vendorId,startDate,endDate,itemId,enterpriseID);
        call.enqueue(new Callback<IodizationHistoryResponse>() {
            @Override
            public void onResponse(Call<IodizationHistoryResponse> call, Response<IodizationHistoryResponse> response) {
                if (response.isSuccessful()) {

                    if (response== null) {
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
            public void onFailure(Call<IodizationHistoryResponse> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;
    }

}
