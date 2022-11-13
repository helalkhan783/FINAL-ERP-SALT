package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.SalePendingResponse;
import com.ismos_salt_erp.serverResponseModel.SalePendingReturnResponse;
import com.ismos_salt_erp.serverResponseModel.SaleReturnResponse;
import com.ismos_salt_erp.serverResponseModel.SalesHistoryResponse;
import com.ismos_salt_erp.utils.CustomViewModel;
import com.ismos_salt_erp.serverResponseModel.SaleDeclinedResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalePendingListViewModel extends CustomViewModel {
    public MutableLiveData<SalePendingResponse> getSalePendinglist(FragmentActivity context, String page, String startDate, String endDate,
                                                                   String companyId, String enterPriseId){
        MutableLiveData<SalePendingResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String vendoId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<SalePendingResponse> call = RetrofitClient.getInstance().getApi().getSalePendingList(token,page,userId,vendoId,startDate,endDate,companyId,enterPriseId);
        call.enqueue(new Callback<SalePendingResponse>() {
            @Override
            public void onResponse(Call<SalePendingResponse> call, Response<SalePendingResponse> response) {
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
            public void onFailure(Call<SalePendingResponse> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;

    }

/** for sale decline*/
     public MutableLiveData<SaleDeclinedResponse> getSaleDeclinedList(FragmentActivity context, String page, String startDate, String endDate, String companyId, String enterPriseID){
        MutableLiveData<SaleDeclinedResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String vendoId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<SaleDeclinedResponse> call = RetrofitClient.getInstance().getApi().getSalePendingDeclinedList(token,page,userId,vendoId,startDate,endDate,companyId,enterPriseID);
        call.enqueue(new Callback<SaleDeclinedResponse>() {
            @Override
            public void onResponse(Call<SaleDeclinedResponse> call, Response<SaleDeclinedResponse> response) {
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
            public void onFailure(Call<SaleDeclinedResponse> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;

    }
/** for sale return history*/

    public MutableLiveData<SalesHistoryResponse> getSaleReturnHistoryList(FragmentActivity context, String page, String startDate, String endDate, String companyId, String enterPriseID){
        MutableLiveData<SalesHistoryResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String vendoId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<SalesHistoryResponse> call = RetrofitClient.getInstance().getApi().getSaleReturnHistoryList(token,page,userId,vendoId,startDate,endDate,companyId,enterPriseID);
        call.enqueue(new Callback<SalesHistoryResponse>() {
            @Override
            public void onResponse(Call<SalesHistoryResponse> call, Response<SalesHistoryResponse> response) {
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
            public void onFailure(Call<SalesHistoryResponse> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;

    }



    /**  sale pending return*/


    public MutableLiveData<SalePendingReturnResponse> getSalePendingReturnList(FragmentActivity context, String page, String startDate, String endDate, String companyId, String enterPriseID){
        MutableLiveData<SalePendingReturnResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String vendoId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<SalePendingReturnResponse> call = RetrofitClient.getInstance().getApi().getSalePendingReturnList(token,page,userId,vendoId,startDate,endDate,companyId,enterPriseID);
        call.enqueue(new Callback<SalePendingReturnResponse>() {
            @Override
            public void onResponse(Call<SalePendingReturnResponse> call, Response<SalePendingReturnResponse> response) {
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
            public void onFailure(Call<SalePendingReturnResponse> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;

    }

}
