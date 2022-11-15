package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.TransactionResponse;
import com.ismos_salt_erp.utils.CustomViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionViewModel extends CustomViewModel {

    public MutableLiveData<TransactionResponse> getTransactionInData(FragmentActivity context, String transactionType, String start, String end, String companyId,String user,String paymentType) {
        MutableLiveData<TransactionResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String userId = getUserId(context.getApplication());
        String vendorid = getVendorId(context.getApplication());
        Call<TransactionResponse> call = RetrofitClient.getInstance().getApi().getTransactionList(token,userId,vendorid,transactionType,start,end,"",companyId,user,paymentType);


        call.enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                if (response == null) {
                    liveData.postValue(null);
                    return;
                }
                try {
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }
                } catch (Exception e) {
                    liveData.postValue(null);
                    return;
                }
                if (response.body().getStatus() == 200) {
                    liveData.postValue(response.body());
                    return;
                }
            }

            @Override
            public void onFailure(Call<TransactionResponse> call, Throwable t) {
                liveData.setValue(null);
                Log.d("Error", "onFailure: " + t.getMessage());
                return;
            }
        });


        return liveData;
    }
    public MutableLiveData<TransactionResponse> dashBoardData(FragmentActivity context, String transactionType, String start, String end, String companyId,String user,String paymentType) {
        MutableLiveData<TransactionResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String userId = getUserId(context.getApplication());
        String vendorid = getVendorId(context.getApplication());
        Call<TransactionResponse> call = RetrofitClient.getInstance().getApi().dashBoardData(token,userId,vendorid,transactionType,start,end,"",companyId,user,paymentType);


        call.enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                if (response == null) {
                    liveData.postValue(null);
                    return;
                }
                try {
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }
                } catch (Exception e) {
                    liveData.postValue(null);
                    return;
                }
                if (response.body().getStatus() == 200) {
                    liveData.postValue(response.body());
                    return;
                }
            }

            @Override
            public void onFailure(Call<TransactionResponse> call, Throwable t) {
                liveData.setValue(null);
                Log.d("Error", "onFailure: " + t.getMessage());
                return;
            }
        });


        return liveData;
    }




}
