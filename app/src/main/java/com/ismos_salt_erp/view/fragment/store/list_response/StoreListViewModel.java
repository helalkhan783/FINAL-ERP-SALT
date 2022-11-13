package com.ismos_salt_erp.view.fragment.store.list_response;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.StockInfDetailsResponse;
import com.ismos_salt_erp.serverResponseModel.StockListResponse;
import com.ismos_salt_erp.utils.CustomViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreListViewModel extends CustomViewModel {

    public MutableLiveData<StockInfDetailsResponse> stockDetails(FragmentActivity context, String productId) {
        MutableLiveData<StockInfDetailsResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String vendoId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());
        String storeId = getStoreId(context.getApplication());


        Call<StockInfDetailsResponse> call = RetrofitClient.getInstance().getApi().getStockDetails(token, userId, vendoId, storeId, productId);
        call.enqueue(new Callback<StockInfDetailsResponse>() {
            @Override
            public void onResponse(Call<StockInfDetailsResponse> call, Response<StockInfDetailsResponse> response) {
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
            public void onFailure(Call<StockInfDetailsResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;

    }

    public MutableLiveData<DuePaymentResponse> editStore(FragmentActivity context, String enterpriseId, String fullName, String shortName, String address, String storeId) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());
        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().editStore(token, vendorId, userId, enterpriseId, fullName, shortName, address, storeId);
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null ||response.body().getStatus() == 400) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());
                        return;
                    }

                }else
                    liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                liveData.postValue(null);
                return;
            }
        });


        return liveData;
    }


    public MutableLiveData<StockListResponse> getStockList(FragmentActivity context, String page, String productTitle, String storeId, String brandId, String categoryId, String enterprise) {
        MutableLiveData<StockListResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String vendoId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<StockListResponse> call = RetrofitClient.getInstance().getApi().getStockList(token, page, userId, vendoId, productTitle, storeId, brandId, categoryId, enterprise);
        call.enqueue(new Callback<StockListResponse>() {
            @Override
            public void onResponse(Call<StockListResponse> call, Response<StockListResponse> response) {
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
            public void onFailure(Call<StockListResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;

    }


    public MutableLiveData<StoreListResponse> getStoreList(FragmentActivity context, String page) {
        MutableLiveData<StoreListResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String vendoId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<StoreListResponse> call = RetrofitClient.getInstance().getApi().getStoreList(token, page, userId, vendoId);
        call.enqueue(new Callback<StoreListResponse>() {
            @Override
            public void onResponse(Call<StoreListResponse> call, Response<StoreListResponse> response) {
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
            public void onFailure(Call<StoreListResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;

    }

    /**
     * for add new store
     */
    public MutableLiveData<DuePaymentResponse> addNewStore(FragmentActivity context, String enterpriseId, String fullName, String shortName, String address) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());
        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().addNewstore(token, vendorId, userId, enterpriseId, fullName, shortName, address);
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null || response.body().getStatus() == 500) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }
                    liveData.postValue(response.body());

                } else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                liveData.postValue(null);
                return;
            }
        });


        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> unPackResponse(FragmentActivity context, String enterpriseId,
                                                              String storeId, String itemId, String customerId, String itemQty, String subTotal, String orderdate, String note) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());
        String profileTypeId = getProfileTypeId(context.getApplication());
        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().unPackResponse(token, vendorId, userId, profileTypeId,
                enterpriseId, storeId, itemId, customerId,
                itemQty, subTotal, orderdate, note);
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
                    liveData.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                liveData.postValue(null);
                return;
            }
        });


        return liveData;
    }

    // enterprise and store status manage
    public MutableLiveData<DuePaymentResponse> statusManage(FragmentActivity context, String storeId) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());
        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().statusManage(token, vendorId, userId, storeId);
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null || response.body().getStatus() == 500) {
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

                } else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                liveData.postValue(null);
                return;
            }
        });


        return liveData;
    }


}
