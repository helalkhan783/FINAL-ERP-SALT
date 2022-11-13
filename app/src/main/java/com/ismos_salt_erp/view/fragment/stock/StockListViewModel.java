package com.ismos_salt_erp.view.fragment.stock;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.utils.CustomViewModel;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockDeclineReconciliationListResponse;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockDeclineTransferredListResponse;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockPendingReconciliationListResponse;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockPendingTransferredListResponse;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockReconciliationHistoryListResponse;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockTransferHistoryListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockListViewModel extends CustomViewModel {

    /**
     * for stock transferred History List done
     */
    public MutableLiveData<StockTransferHistoryListResponse> getStockTransferHistoryList(
            FragmentActivity context, String pageNumber, String transferType, String storeOptional, String enterpriseOptional,
            String startOptional, String endOptional, String itemOptional
    ) {

        MutableLiveData<StockTransferHistoryListResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String userId = getUserId(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        Call<StockTransferHistoryListResponse> call =
                RetrofitClient.getInstance().getApi().getStockTransferHistoryList(
                        token, pageNumber, userId, vendorId, transferType, storeOptional, enterpriseOptional, startOptional, endOptional,
                        itemOptional);
        call.enqueue(new Callback<StockTransferHistoryListResponse>() {
            @Override
            public void onResponse(Call<StockTransferHistoryListResponse> call, Response<StockTransferHistoryListResponse> response) {
                try {
                    if (response == null) {
                        liveData.setValue(null);
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
            public void onFailure(Call<StockTransferHistoryListResponse> call, Throwable t) {
                Log.d("Error", "onFailure: " + t.getMessage());
                liveData.setValue(null);
            }
        });
        return liveData;
    }


    /**
     * Stock Pending Transferred List done
     */
    public MutableLiveData<StockPendingTransferredListResponse> getStockPendingTransferredList(FragmentActivity context, String pageNumber, String storeOptional, String enterpriseOptional,
                                                                                               String startOptional, String endOptional, String itemOptional

    ) {

        MutableLiveData<StockPendingTransferredListResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String userId = getUserId(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        Call<StockPendingTransferredListResponse> call =
                RetrofitClient.getInstance().getApi().getStockPendingTransferredList(token, pageNumber, userId, vendorId, startOptional, endOptional, storeOptional, enterpriseOptional, itemOptional);
        call.enqueue(new Callback<StockPendingTransferredListResponse>() {
            @Override
            public void onResponse(Call<StockPendingTransferredListResponse> call, Response<StockPendingTransferredListResponse> response) {
                try {
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
                } catch (Exception e) {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<StockPendingTransferredListResponse> call, Throwable t) {
                Log.d("Error", "onFailure: " + t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;

    }


    /**
     * decline Transferred List done
     */

    public MutableLiveData<StockDeclineTransferredListResponse> getStockDeclineTransferredList(FragmentActivity context, String pageNumber, String storeOptional, String enterpriseOptional,
                                                                                               String startOptional, String endOptional, String itemOptional
    ) {

        MutableLiveData<StockDeclineTransferredListResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String userId = getUserId(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        Call<StockDeclineTransferredListResponse> call =
                RetrofitClient.getInstance().getApi().getStockDeclineTransferredList(token, pageNumber, userId, vendorId, startOptional, endOptional, storeOptional, enterpriseOptional, itemOptional);
        call.enqueue(new Callback<StockDeclineTransferredListResponse>() {
            @Override
            public void onResponse(Call<StockDeclineTransferredListResponse> call, Response<StockDeclineTransferredListResponse> response) {
                try {
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
                } catch (Exception e) {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<StockDeclineTransferredListResponse> call, Throwable t) {
                Log.d("Error", "onFailure: " + t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;

    }

    /**
     * reconciliation History List
     */

    public MutableLiveData<StockReconciliationHistoryListResponse> getReconciliationHistoryList(FragmentActivity context, String pageNumber, String startDate, String endDate,

                                                                                                String storeId, String enterPriseId, String itemId, String type) {

        MutableLiveData<StockReconciliationHistoryListResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String userId = getUserId(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        Call<StockReconciliationHistoryListResponse> call =
                RetrofitClient.getInstance().getApi().getStockReconciliationList(token, pageNumber,
                        userId, vendorId, startDate, endDate, storeId, enterPriseId, itemId, type);
        call.enqueue(new Callback<StockReconciliationHistoryListResponse>() {
            @Override
            public void onResponse(Call<StockReconciliationHistoryListResponse> call, Response<StockReconciliationHistoryListResponse> response) {
                try {
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
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<StockReconciliationHistoryListResponse> call, Throwable t) {
                Log.d("Error", "onFailure: " + t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;

    }


    /**
     * pending Reconciliation List
     */

    public MutableLiveData<StockPendingReconciliationListResponse> getPendingReconciliationList(FragmentActivity context, String pageNumber, String startDate, String endDate, String storeId,
                                                                                                String enterPriseId, String itemId, String type) {

        MutableLiveData<StockPendingReconciliationListResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String userId = getUserId(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        Call<StockPendingReconciliationListResponse> call =
                RetrofitClient.getInstance().getApi().getStockPendingReconciliationList(token, pageNumber,
                        userId, vendorId, startDate, endDate, storeId, enterPriseId, itemId, type);
        call.enqueue(new Callback<StockPendingReconciliationListResponse>() {
            @Override
            public void onResponse(Call<StockPendingReconciliationListResponse> call, Response<StockPendingReconciliationListResponse> response) {
                try {
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
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<StockPendingReconciliationListResponse> call, Throwable t) {
                Log.d("Error", "onFailure: " + t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;

    }

    /**
     * decline Reconciliation List
     */
    public MutableLiveData<StockDeclineReconciliationListResponse> getDeclineReconciliationList(FragmentActivity context, String pageNumber, String startDate, String endDate, String storeId,
                                                                                                String enterPriseId, String itemId, String type
    ) {

        MutableLiveData<StockDeclineReconciliationListResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String userId = getUserId(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        Call<StockDeclineReconciliationListResponse> call =
                RetrofitClient.getInstance().getApi().getStockDeclineReconciliationList(token, pageNumber, userId, vendorId, startDate, endDate, storeId, enterPriseId, itemId, type);
        call.enqueue(new Callback<StockDeclineReconciliationListResponse>() {
            @Override
            public void onResponse(Call<StockDeclineReconciliationListResponse> call, Response<StockDeclineReconciliationListResponse> response) {
                try {
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
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<StockDeclineReconciliationListResponse> call, Throwable t) {
                Log.d("Error", "onFailure: " + t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;

    }


}
