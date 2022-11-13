package com.ismos_salt_erp.viewModel;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.GetInboxListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InboxViewModel extends CustomerViewModel {
    public MutableLiveData<GetInboxListResponse> getInboxList(
            FragmentActivity context, String page
    ) {
        MutableLiveData<GetInboxListResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        Call<GetInboxListResponse> call = RetrofitClient.getInstance().getApi()
                .getInboxList(token, page, vendorId, userId);
        call.enqueue(new Callback<GetInboxListResponse>() {
            @Override
            public void onResponse(Call<GetInboxListResponse> call, Response<GetInboxListResponse> response) {
                try {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }
                    liveData.postValue(response.body());
                } catch (Exception e) {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<GetInboxListResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });


        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> sendSeenStatus(
            FragmentActivity context, String messageId
    ) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
         String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .sendSeenStatus(token, userId, messageId);
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                try {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }
                    liveData.postValue(response.body());
                } catch (Exception e) {
                    liveData.postValue(null);
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
