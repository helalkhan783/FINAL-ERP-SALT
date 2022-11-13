package com.ismos_salt_erp.viewModel;

import android.app.ProgressDialog;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.AccountNumberListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DuePaymentReceivedViewModel extends ViewModel {
    private MutableLiveData<AccountNumberListResponse> accountNumberListResponseMutableLiveData;

    public DuePaymentReceivedViewModel() {
        accountNumberListResponseMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<AccountNumberListResponse> getAccountListByBankId() {
        return accountNumberListResponseMutableLiveData;
    }

    public void apiCallForGetAccountListByBankNameId(FragmentActivity context, String vendorID, String bankNameId,String storeID) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        Call<AccountNumberListResponse> call = RetrofitClient.getInstance().getApi().getAccountListByBankId(vendorID, bankNameId,storeID);
        call.enqueue(new Callback<AccountNumberListResponse>() {
            @Override
            public void onResponse(Call<AccountNumberListResponse> call, Response<AccountNumberListResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (accountNumberListResponseMutableLiveData == null) {
                        accountNumberListResponseMutableLiveData.postValue(null);
                        return;
                    }
                    accountNumberListResponseMutableLiveData.postValue(response.body());
                } else {
                    accountNumberListResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<AccountNumberListResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                progressDialog.dismiss();
                accountNumberListResponseMutableLiveData.postValue(null);
            }
        });
    }
}
