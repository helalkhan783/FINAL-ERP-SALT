package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.PurchaseReturnPendingDetailsResponse;
import com.ismos_salt_erp.utils.CustomViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseReturnPendingDetailsViewModel extends CustomViewModel {
    /**
     * for purchase return pendingdetails
     */


    public MutableLiveData<PurchaseReturnPendingDetailsResponse> getPurchaseReturnPendingDetails(FragmentActivity context,
                                                                                                 String vendorId, String id) {
        MutableLiveData<PurchaseReturnPendingDetailsResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        Call<PurchaseReturnPendingDetailsResponse> call = RetrofitClient.getInstance().getApi().purchaseReturnPendingDetails(token, vendorId,getUserId(context.getApplication()), id);
        call.enqueue(new Callback<PurchaseReturnPendingDetailsResponse>() {
            @Override
            public void onResponse(Call<PurchaseReturnPendingDetailsResponse> call, Response<PurchaseReturnPendingDetailsResponse> response) {
                if (response.isSuccessful()) {
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
                        Log.d("Error", e.getMessage());
                        liveData.postValue(null);
                    }


                }
            }

            @Override
            public void onFailure(Call<PurchaseReturnPendingDetailsResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                liveData.postValue(null);
            }
        });
        return liveData;

    }

    public MutableLiveData<PurchaseReturnPendingDetailsResponse> getSaleReturnPendingDetails(FragmentActivity context,
                                                                                             String vendorId, String id) {
        MutableLiveData<PurchaseReturnPendingDetailsResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        Call<PurchaseReturnPendingDetailsResponse> call = RetrofitClient.getInstance().getApi().saleReturnPendingDetails(token, vendorId, id);
        call.enqueue(new Callback<PurchaseReturnPendingDetailsResponse>() {
            @Override
            public void onResponse(Call<PurchaseReturnPendingDetailsResponse> call, Response<PurchaseReturnPendingDetailsResponse> response) {
                if (response.isSuccessful()) {
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
                        Log.d("Error", e.getMessage());
                        liveData.postValue(null);
                    }


                }
            }

            @Override
            public void onFailure(Call<PurchaseReturnPendingDetailsResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                liveData.postValue(null);
            }
        });
        return liveData;

    }


}
