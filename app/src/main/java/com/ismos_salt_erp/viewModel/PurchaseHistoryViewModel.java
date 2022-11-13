package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.PurchaseDeclineResponse;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.PurchaseHistoryResponse;
import com.ismos_salt_erp.serverResponseModel.PurchasePendingResponse;
import com.ismos_salt_erp.serverResponseModel.PurchaseReturnHistoryResponse;
import com.ismos_salt_erp.serverResponseModel.PurchaseReturnPendingResponse;
import com.ismos_salt_erp.utils.CustomViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseHistoryViewModel extends CustomViewModel {
    /**
     * purchase history list
     */
    public MutableLiveData<PurchaseHistoryResponse> getPeechaseHistoryList(FragmentActivity context, String pageNumber, String startDate, String endDate, String companyId, String enterpriseId) {
        MutableLiveData<PurchaseHistoryResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendoId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<PurchaseHistoryResponse> call = RetrofitClient.getInstance().getApi().getPurchaseHistoryList(token, pageNumber, userId, vendoId, startDate, endDate, companyId, enterpriseId);
        call.enqueue(new Callback<PurchaseHistoryResponse>() {
            @Override
            public void onResponse(Call<PurchaseHistoryResponse> call, Response<PurchaseHistoryResponse> response) {
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
            public void onFailure(Call<PurchaseHistoryResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;

    }

    /**
     * purchase history pending list
     */

    public MutableLiveData<PurchasePendingResponse> getPendingPurchaseList(FragmentActivity context, String pageNumber, String startDate, String endDate, String companyId, String enterpriseId) {
        MutableLiveData<PurchasePendingResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendoId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<PurchasePendingResponse> call = RetrofitClient.getInstance().getApi().getPendingPurchaseList(token, pageNumber, userId, vendoId, startDate, endDate, companyId, enterpriseId);
        call.enqueue(new Callback<PurchasePendingResponse>() {
            @Override
            public void onResponse(Call<PurchasePendingResponse> call, Response<PurchasePendingResponse> response) {
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
                    } catch (Exception e) {
                        Log.d("Error", e.getMessage());
                    }
                } else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<PurchasePendingResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;

    }

    /**
     * purchase  return pending list
     */
    public MutableLiveData<PurchaseReturnPendingResponse> getPurchaseReturnPendingList(FragmentActivity context, String pageNumber, String startDate, String endDate, String companyId, String enterpriseId) {
        MutableLiveData<PurchaseReturnPendingResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendoId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<PurchaseReturnPendingResponse> call = RetrofitClient.getInstance().getApi().getPurchaseReturnPendingList(token, pageNumber, userId, vendoId, startDate, endDate, companyId, enterpriseId);
        call.enqueue(new Callback<PurchaseReturnPendingResponse>() {
            @Override
            public void onResponse(Call<PurchaseReturnPendingResponse> call, Response<PurchaseReturnPendingResponse> response) {
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
                    } catch (Exception e) {
                        Log.d("Error", e.getMessage());
                    }
                } else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<PurchaseReturnPendingResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;

    }

    /**
     * purchase  return history list
     */


    public MutableLiveData<PurchaseHistoryResponse> getPurchaseReturnHistoryList(FragmentActivity context, String pageNumber, String startDate, String endDate, String companyId, String enterpriseId) {
        MutableLiveData<PurchaseHistoryResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendoId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<PurchaseHistoryResponse> call = RetrofitClient.getInstance().getApi().getPurchaseReturnHistoryList(token, pageNumber, userId, vendoId, startDate, endDate, companyId, enterpriseId);
        call.enqueue(new Callback<PurchaseHistoryResponse>() {
            @Override
            public void onResponse(Call<PurchaseHistoryResponse> call, Response<PurchaseHistoryResponse> response) {
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
                    } catch (Exception e) {
                        Log.d("Error", e.getMessage());
                    }
                } else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<PurchaseHistoryResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;

    }

 /**
     * purchase  return history list
     */


    public MutableLiveData<PurchaseDeclineResponse> getPurchaseDeclineList(FragmentActivity context, String pageNumber, String startDate, String endDate, String companyId, String enterpriseId) {
        MutableLiveData<PurchaseDeclineResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendoId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<PurchaseDeclineResponse> call = RetrofitClient.getInstance().getApi().getPurchaseDeclineList(token, pageNumber, userId, vendoId, startDate, endDate, companyId, enterpriseId);
        call.enqueue(new Callback<PurchaseDeclineResponse>() {
            @Override
            public void onResponse(Call<PurchaseDeclineResponse> call, Response<PurchaseDeclineResponse> response) {
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
                    } catch (Exception e) {
                        Log.d("Error", e.getMessage());
                    }
                } else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<PurchaseDeclineResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;

    }


}
