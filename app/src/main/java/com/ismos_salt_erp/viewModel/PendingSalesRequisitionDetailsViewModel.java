package com.ismos_salt_erp.viewModel;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.PendingSalesRequisitionPendingResponse;

import lombok.AllArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AllArgsConstructor
public class PendingSalesRequisitionDetailsViewModel extends ViewModel {


    public MutableLiveData<DuePaymentResponse> declinePendingSalesRequisitionDetails(FragmentActivity context, String id, String note) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        ProgressDialog progressDialog = new ProgressDialog(context);


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        progressDialog.show();
        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .declinePendingSalesRequisitionDetails(token,vendorId, id, userId, note);

        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                progressDialog.dismiss();
                Toast.makeText(context, "Something Wrong Contact to Support \n" + this.getClass().getSimpleName() + " " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> approvePendingSalesRequisitionDetails(FragmentActivity context, String id, String note) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        ProgressDialog progressDialog = new ProgressDialog(context);


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        progressDialog.show();
        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .approvePendingSalesRequisitionDetails(token, vendorId, id, userId, note);

        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                progressDialog.dismiss();
                Toast.makeText(context, "Something Wrong Contact to Support \n" + this.getClass().getSimpleName() + " " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        return liveData;
    }


    public MutableLiveData<PendingSalesRequisitionPendingResponse> pendingSalesRequisitionPendingDetails(FragmentActivity context, String id) {
        MutableLiveData<PendingSalesRequisitionPendingResponse> liveData = new MutableLiveData<>();
        ProgressDialog progressDialog = new ProgressDialog(context);


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        progressDialog.show();
        Call<PendingSalesRequisitionPendingResponse> call = RetrofitClient.getInstance().getApi()
                .getPendingSalesRequisitionDetails(token, id, vendorId,userId);
        call.enqueue(new Callback<PendingSalesRequisitionPendingResponse>() {
            @Override
            public void onResponse(Call<PendingSalesRequisitionPendingResponse> call, Response<PendingSalesRequisitionPendingResponse> response) {
                if (response.isSuccessful()) {
                   if (response == null || response.body().getStatus() == 500){
                       liveData.postValue(null);
                       return;
                   }
                    if (response.body().getStatus() == 400) {
                        progressDialog.dismiss();
                        liveData.postValue(response.body());
                        return;
                    } if (response.body().getStatus() == 200) {
                        progressDialog.dismiss();
                        liveData.postValue(response.body());
                    }
                }else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<PendingSalesRequisitionPendingResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                progressDialog.dismiss();
                liveData.postValue(null);
             }
        });
        return liveData;

    }


}
