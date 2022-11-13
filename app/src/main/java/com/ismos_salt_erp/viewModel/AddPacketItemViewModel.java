package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.utils.CustomViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPacketItemViewModel extends CustomViewModel {

    public MutableLiveData<DuePaymentResponse> addItemPacket(FragmentActivity context, String productId, String packetName, String quantity,
                                                             String unit, String savePacket, String price) {

        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String storeId = getStoreId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().addNewPacketItem(token, vendorId, userId, packetName, quantity, productId, price,
                unit, savePacket, storeId,"1");


        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
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
                        Log.d("ERROR", e.getMessage());
                        liveData.postValue(null);
                        return;
                    }


                }
                else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
            }
        });

        return liveData;


    }

    public MutableLiveData<DuePaymentResponse> addVarient(FragmentActivity context, String productId, String packetName, String quantity,
                                                             String unit, String savePacket, String price) {

        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String storeId = getStoreId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().addVarient(token, vendorId, userId, packetName, quantity, productId, price,
                unit, savePacket, storeId,"2");


        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
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
                        Log.d("ERROR", e.getMessage());
                        liveData.postValue(null);
                        return;
                    }


                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
            }
        });

        return liveData;


    }


/**
 * update data
 */


public MutableLiveData<DuePaymentResponse> updatePacketItem(FragmentActivity context, String productId, String packetName, String quantity,
                                                         String unit,   String price) {

    MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

    String token = getToken(context.getApplication());
    String vendorId = getVendorId(context.getApplication());
    String storeId = getStoreId(context.getApplication());
    String userId = getUserId(context.getApplication());

    Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().updateItemPacket(token,vendorId,userId,storeId,productId,packetName,quantity,unit,price);


    call.enqueue(new Callback<DuePaymentResponse>() {
        @Override
        public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
            if (response.isSuccessful()) {
                try {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    } if (response.body().getStatus() == 500) {
                        liveData.postValue(null);
                        return;
                    }
                    liveData.postValue(response.body());
                } catch (Exception e) {
                    Log.d("ERROR", e.getMessage());
                    liveData.postValue(null);
                    return;
                }


            }
            else {
                liveData.postValue(null);
            }
        }

        @Override
        public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
            Log.d("ERROR", t.getMessage());
            liveData.postValue(null);
        }
    });

    return liveData;


}

}
