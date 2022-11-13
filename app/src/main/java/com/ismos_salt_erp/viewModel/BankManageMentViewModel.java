package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.bankresponse.BankAccountDetailsResponse;
import com.ismos_salt_erp.serverResponseModel.bankresponse.BankAccountListResponse;
import com.ismos_salt_erp.utils.CustomViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankManageMentViewModel extends CustomViewModel {
    public MutableLiveData<BankAccountListResponse> getBankAccountList(FragmentActivity activity ) {
        String token = getToken(activity.getApplication());
        String userId = getUserId(activity.getApplication());
        String vendorId = getVendorId(activity.getApplication());
        MutableLiveData<BankAccountListResponse> liveData = new MutableLiveData<>();

        Call<BankAccountListResponse> call = RetrofitClient.getInstance().getApi().getBankAccountList(
                token, userId,vendorId );

        call.enqueue(new Callback<BankAccountListResponse>() {
            @Override
            public void onResponse(Call<BankAccountListResponse> call, Response<BankAccountListResponse> response) {
                if (response.isSuccessful()){
                    try {
                        if (response == null){
                            liveData.postValue(null);
                            return;
                        }

                        if (response.body().getStatus() == 400){
                            liveData.postValue(response.body());
                            return;
                        }
                        if (response.body().getStatus() == 500){
                            liveData.postValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 200){
                            liveData.postValue(response.body());
                            return;
                        }
                    }catch (Exception e){
                        Log.d("ERROR",e.getMessage());
                        liveData.postValue(null);
                        return;
                    }

                }else  liveData.postValue(null);
            }
            @Override
            public void onFailure(Call<BankAccountListResponse> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
                liveData.postValue(null);
                return;
            }
        });

        return liveData;
    }
  public MutableLiveData<BankAccountDetailsResponse> getBankAccountDetails(FragmentActivity activity,String bankId ) {
        String token = getToken(activity.getApplication());
        String userId = getUserId(activity.getApplication());
        String vendorId = getVendorId(activity.getApplication());
        MutableLiveData<BankAccountDetailsResponse> liveData = new MutableLiveData<>();

        Call<BankAccountDetailsResponse> call = RetrofitClient.getInstance().getApi().getBankAccountDetails(
                token, userId,vendorId ,bankId);

        call.enqueue(new Callback<BankAccountDetailsResponse>() {
            @Override
            public void onResponse(Call<BankAccountDetailsResponse> call, Response<BankAccountDetailsResponse> response) {
                if (response.isSuccessful()){
                    try {
                        if (response == null){
                            liveData.postValue(null);
                            return;
                        }

                        if (response.body().getStatus() == 400){
                            liveData.postValue(response.body());
                            return;
                        }
                        if (response.body().getStatus() == 500){
                            liveData.postValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 200){
                            liveData.postValue(response.body());
                            return;
                        }
                    }catch (Exception e){
                        Log.d("ERROR",e.getMessage());
                        liveData.postValue(null);
                        return;
                    }

                }
            }
            @Override
            public void onFailure(Call<BankAccountDetailsResponse> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
                liveData.postValue(null);
                return;
            }
        });

        return liveData;
    }


}
