package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.InitialItemListResponse;
import com.ismos_salt_erp.serverResponseModel.ItemListResponse;
import com.ismos_salt_erp.serverResponseModel.ItemPacketListResponse;
import com.ismos_salt_erp.utils.CustomViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemListViewModel extends CustomViewModel {


    public MutableLiveData<ItemListResponse> getItemlist(FragmentActivity context, String pageNumber, String categoryTYpe, String itemName, String brandType) {
        MutableLiveData<ItemListResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String vendoId = getVendorId(context.getApplication());
        String profileIdType = getProfileTypeId(context.getApplication());
        String storId = getStoreId(context.getApplication());

        Call<ItemListResponse> call = RetrofitClient.getInstance().getApi().getItemList(token, pageNumber, profileIdType, vendoId, storId, getUserId(context.getApplication()), brandType, itemName, categoryTYpe);
        call.enqueue(new Callback<ItemListResponse>() {
            @Override
            public void onResponse(Call<ItemListResponse> call, Response<ItemListResponse> response) {
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
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ItemListResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;

    }


    public MutableLiveData<InitialItemListResponse> initialItemList(FragmentActivity context, String pageNumber, String categoryTYpe, String itemName, String brandType) {
        MutableLiveData<InitialItemListResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String vendoId = getVendorId(context.getApplication());
        String profileIdType = getProfileTypeId(context.getApplication());
        String storId = getStoreId(context.getApplication());

        Call<InitialItemListResponse> call = RetrofitClient.getInstance().getApi().initialItemList(token, pageNumber, profileIdType, vendoId, storId, getUserId(context.getApplication()), brandType, itemName, categoryTYpe);
        call.enqueue(new Callback<InitialItemListResponse>() {
            @Override
            public void onResponse(Call<InitialItemListResponse> call, Response<InitialItemListResponse> response) {
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
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<InitialItemListResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;

    }


    /**
     * this is for ItemPacketList
     */

    public MutableLiveData<ItemPacketListResponse> getItemPacketList(FragmentActivity context, String productId) {
        MutableLiveData<ItemPacketListResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String user_id = getUserId(context.getApplication());
        String vendorId = getVendorId(context.getApplication());


        Call<ItemPacketListResponse> call = RetrofitClient.getInstance().getApi().getItemPacketList(token,user_id,vendorId ,productId );
        call.enqueue(new Callback<ItemPacketListResponse>() {
            @Override
            public void onResponse(Call<ItemPacketListResponse> call, Response<ItemPacketListResponse> response) {
                if (response.isSuccessful()) {
                    try
                    {
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
                        Log.d("Error",e.getMessage());
                        liveData.postValue(null);
                        return;
                    }
                }
            }

            @Override
            public void onFailure(Call<ItemPacketListResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;

    }


}
