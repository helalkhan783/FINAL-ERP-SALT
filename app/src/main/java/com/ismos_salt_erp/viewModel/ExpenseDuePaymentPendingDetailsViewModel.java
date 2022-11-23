package com.ismos_salt_erp.viewModel;

import android.app.ProgressDialog;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.PendingExpensePaymentDueResponse;

import lombok.AllArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AllArgsConstructor
public class ExpenseDuePaymentPendingDetailsViewModel extends ViewModel {

    public MutableLiveData<DuePaymentResponse> declineExpenseDuePaymentApprovalDetails(FragmentActivity context, String batch) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        ProgressDialog progressDialog = new ProgressDialog(context);

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        progressDialog.show();


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .declineDuePaymentApprovalDetails(token, vendorId, batch, userId);
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200 || response.body().getStatus() == 400 ) {
                        liveData.postValue(response.body());
                        progressDialog.dismiss();
                        return;
                    }
                    if (response == null || response.body().getStatus() == 500){
                        liveData.postValue(null);
                    }
                }else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                progressDialog.dismiss();
                liveData.postValue(null);
            }
        });
        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> approveExpenseDuePaymentApprovalDetails(FragmentActivity context, String batch,String typeKey) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        ProgressDialog progressDialog = new ProgressDialog(context);

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        progressDialog.show();


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .approveDuePaymentApprovalDetails(token, vendorId, batch, userId,typeKey);
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
                        progressDialog.dismiss();
                        return;
                    }
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());
                        progressDialog.dismiss();

                    }
                } else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                progressDialog.dismiss();

                liveData.postValue(null);
            }
        });
        return liveData;
    }


    public MutableLiveData<PendingExpensePaymentDueResponse> getExpenseDuePaymentApprovalDetails(FragmentActivity context, String batch, String customerId,String type) {
        MutableLiveData<PendingExpensePaymentDueResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();



        Call<PendingExpensePaymentDueResponse> call = RetrofitClient.getInstance().getApi()
                .getExpenseDuePaymentApprovalDetails(token, vendorID,userId, batch, customerId,type);

        call.enqueue(new Callback<PendingExpensePaymentDueResponse>() {
            @Override
            public void onResponse(Call<PendingExpensePaymentDueResponse> call, Response<PendingExpensePaymentDueResponse> response) {

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


                    }
                } else
                    liveData.postValue(null);

            }

            @Override
            public void onFailure(Call<PendingExpensePaymentDueResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());

                liveData.postValue(null);
            }
        });

        return liveData;
    }
}
