package com.ismos_salt_erp.viewModel;

import android.app.ProgressDialog;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.PendingPurchaseNotificationDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingSaleApproveDeclineViewModel extends ViewModel {
    ProgressDialog progressDialog;

    public PendingSaleApproveDeclineViewModel() {
    }

    /**
     * here PendingPurchaseNotificationDetailsResponse and saleResponse are same for that i use the response class;
     */
    public MutableLiveData<PendingPurchaseNotificationDetailsResponse> getPendingNotificationDetails(FragmentActivity context, String refOrderId, String vendorID) {
        MutableLiveData<PendingPurchaseNotificationDetailsResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
         Call<PendingPurchaseNotificationDetailsResponse> call = RetrofitClient.getInstance().getApi()
                .getPendingSalesNotificationDetails(token, vendorID,userId, refOrderId);


        call.enqueue(new Callback<PendingPurchaseNotificationDetailsResponse>() {
            @Override
            public void onResponse(Call<PendingPurchaseNotificationDetailsResponse> call, Response<PendingPurchaseNotificationDetailsResponse> response) {
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
                } else {
                    liveData.postValue(null);
                    return;
                }
            }

            @Override
            public void onFailure(Call<PendingPurchaseNotificationDetailsResponse> call, Throwable t) {
                liveData.postValue(null);
                Log.d("ERROR", t.getMessage());
            }
        });
        return liveData;
    }
    public MutableLiveData<PendingPurchaseNotificationDetailsResponse> getPendingSalesReturnNotificationDetailsNew(FragmentActivity context, String refOrderId,String vendorID) {
        MutableLiveData<PendingPurchaseNotificationDetailsResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
         Call<PendingPurchaseNotificationDetailsResponse> call = RetrofitClient.getInstance().getApi()
                .getPendingSalesReturnNotificationDetailsNew(token, vendorID, refOrderId);


        call.enqueue(new Callback<PendingPurchaseNotificationDetailsResponse>() {
            @Override
            public void onResponse(Call<PendingPurchaseNotificationDetailsResponse> call, Response<PendingPurchaseNotificationDetailsResponse> response) {
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
                } else {
                    liveData.postValue(null);
                    return;
                }
            }

            @Override
            public void onFailure(Call<PendingPurchaseNotificationDetailsResponse> call, Throwable t) {
                liveData.postValue(null);
                Log.d("ERROR", t.getMessage());
            }
        });
        return liveData;
    }

    public MutableLiveData<DuePaymentResponse> pendingSalesApproveRequest(FragmentActivity context, String orderId, String note) {
        progressDialog = new ProgressDialog(context);
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .pendingSaleApproveRequest(token, vendorId, orderId, userId, note);

        progressDialog.show();
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null || response.body().getStatus() ==500){
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 200 || response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        progressDialog.dismiss();
                    }
                }
                else {
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


    public MutableLiveData<DuePaymentResponse> pendingSalesDeclineRequest(FragmentActivity context, String orderId, String note) {
        progressDialog = new ProgressDialog(context);
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .pendingSaleDeclineRequest(token, vendorId, orderId, userId, note);

        progressDialog.show();
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null || response.body().getStatus() ==500){
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 200 || response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        progressDialog.dismiss();
                    }
                }
                else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());  liveData.postValue(null);
            }
        });
        return liveData;
    }


}
