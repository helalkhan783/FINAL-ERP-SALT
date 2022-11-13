package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.GetPackatingNo;
import com.ismos_salt_erp.serverResponseModel.GetPackatingProductStockResponse;
import com.ismos_salt_erp.serverResponseModel.GetPacketingResponse;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PacketingViewModel extends ViewModel {

    public MutableLiveData<GetPacketingResponse> getPacketingPageData(FragmentActivity context) {
        MutableLiveData<GetPacketingResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();

        Call<GetPacketingResponse> call = RetrofitClient.getInstance().getApi().getPacketingPageData(token, vendorId);
        call.enqueue(new Callback<GetPacketingResponse>() {
            @Override
            public void onResponse(Call<GetPacketingResponse> call, Response<GetPacketingResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }
                    if (response.body().getStatus() != 200) {
                        liveData.postValue(null);
                        return;
                    }
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<GetPacketingResponse> call, Throwable t) {
                liveData.postValue(null);
                Log.d("ERROR", "ERROR");
            }
        });

        return liveData;
    }


    public MutableLiveData<GetPackatingProductStockResponse> getProductStockByRefProductId(
            FragmentActivity context, String selectedStoreId, List<String> selectedProductIdList
    ) {

        MutableLiveData<GetPackatingProductStockResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();


        Call<GetPackatingProductStockResponse> call = RetrofitClient.getInstance().getApi()
                .getProductStockByProductRefId(token, selectedProductIdList, selectedStoreId, vendorId);


        call.enqueue(new Callback<GetPackatingProductStockResponse>() {
            @Override
            public void onResponse(Call<GetPackatingProductStockResponse> call, Response<GetPackatingProductStockResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }
                    if (response.body().getStatus() != 200) {
                        liveData.postValue(null);
                        return;
                    }
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<GetPackatingProductStockResponse> call, Throwable t) {
                liveData.postValue(null);
                Log.d("ERROR", "ERROR");
            }
        });

        return liveData;

    }


    public MutableLiveData<GetPackatingNo> getPackagingId(FragmentActivity context, String order_type) {
        MutableLiveData<GetPackatingNo> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();

        Call<GetPackatingNo> call = RetrofitClient.getInstance().getApi()
                .getPackatingNo(token, vendorId, order_type);
        call.enqueue(new Callback<GetPackatingNo>() {
            @Override
            public void onResponse(Call<GetPackatingNo> call, Response<GetPackatingNo> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<GetPackatingNo> call, Throwable t) {
                liveData.postValue(null);
                Log.d("ERROR", "ERROR");
            }
        });


        return liveData;
    }


    /**
     * For Add new Packaging
     */
    public MutableLiveData<DuePaymentResponse> addNewPackating(
            FragmentActivity context, String selectedEnterprise, String lastOrderId, String note,
            List<String> productIdList, List<String> productQuantityList, List<String> totalList, String selectedStoreId,
            String orderDate, String refuserId
    ) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().addNewPackating(
                token, vendorId, selectedEnterprise, userId, lastOrderId, note, profileTypeId, productIdList, productQuantityList,
                totalList, Collections.singletonList(selectedStoreId), orderDate, refuserId);
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
                    if (response.body().getStatus() != 200) {
                        liveData.postValue(null);
                        return;
                    }
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                liveData.postValue(null);
                Log.d("ERROR", "ERROR");
            }
        });
        return liveData;
    }


}
