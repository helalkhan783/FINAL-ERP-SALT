package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.PackagingListResponse;
import com.ismos_salt_erp.serverResponseModel.PacketingListResponse;
import com.ismos_salt_erp.utils.CustomViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackagingListViewModel extends CustomViewModel {
/**
 * for packaging list
 * */
    public MutableLiveData<PackagingListResponse> getPackagingList(FragmentActivity context, String pageNumber, String startDate, String endDate, String itemId, String enterpriseID) {

        MutableLiveData<PackagingListResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<PackagingListResponse> call = RetrofitClient.getInstance().getApi().getPackagingList(token,pageNumber,vendorId,userId,startDate,endDate,itemId,enterpriseID);
        call.enqueue(new Callback<PackagingListResponse>() {
            @Override
            public void onResponse(Call<PackagingListResponse> call, Response<PackagingListResponse> response) {
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
                       if (response.body().getStatus() == 200) {
                           liveData.postValue(response.body());
                           return;
                       }
                   }catch (Exception e){
                       liveData.postValue(null);
                   }


                }else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<PackagingListResponse> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
                liveData.setValue(null);
            }
        });

        return liveData;
    }


/**
 * for packaging list
 * */

     public MutableLiveData<PacketingListResponse> getPacketingList(FragmentActivity context, String pageNumber, String startDate, String endDate, String itemId, String enterpriseID) {

        MutableLiveData<PacketingListResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<PacketingListResponse> call = RetrofitClient.getInstance().getApi().getPacketingList(token,pageNumber,vendorId,userId,startDate,endDate,itemId,enterpriseID);
        call.enqueue(new Callback<PacketingListResponse>() {
            @Override
            public void onResponse(Call<PacketingListResponse> call, Response<PacketingListResponse> response) {
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
                       if (response.body().getStatus() == 200) {
                           liveData.postValue(response.body());
                           return;
                       }
                   }
                   catch (Exception e){
                       liveData.postValue(null);
                   }
                }
                else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<PacketingListResponse> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
                liveData.postValue(null);
            }
        });

        return liveData;
    }



}
