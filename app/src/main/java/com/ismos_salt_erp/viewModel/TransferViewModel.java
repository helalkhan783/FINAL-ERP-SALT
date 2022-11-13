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
import com.ismos_salt_erp.serverResponseModel.EditTransferInfoResonse;
import com.ismos_salt_erp.serverResponseModel.PendingTransferDetailsResponse;

import lombok.AllArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AllArgsConstructor
public class TransferViewModel extends ViewModel {

    public MutableLiveData<DuePaymentResponse> declinedPendingTransfer(FragmentActivity context, String id, String note) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .DeclinePendingTransfer(token, vendorId, id, userId, profileTypeId, note);
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
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());
                    }
                } else {
                    liveData.postValue(null);
                }

            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
                Toast.makeText(context, "Something Wrong Contact to Support \n" + this.getClass().getSimpleName() + " " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> approvePendingTransfer(FragmentActivity context, String id, String note) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String storeID = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .approvePendingTransfer(token, vendorId, id, userId, note, storeID, profileTypeId);

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
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());
                    }
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
                Toast.makeText(context, "Something Wrong Contact to Support \n" + this.getClass().getSimpleName() + " " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


        return liveData;
    }


    /**
     * For get Pending transfer details
     */
    public MutableLiveData<PendingTransferDetailsResponse> getPendingTransferDetails(FragmentActivity context, String refOrderId, String vendorId) {
        MutableLiveData<PendingTransferDetailsResponse> liveData = new MutableLiveData<>();
        ProgressDialog progressDialog = new ProgressDialog(context);
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();

        progressDialog.show();

        Call<PendingTransferDetailsResponse> call = RetrofitClient.getInstance().getApi()
                .getPendingTransferDetails(token, refOrderId, vendorId,PreferenceManager.getInstance(context).getUserCredentials().getUserId());
        call.enqueue(new Callback<PendingTransferDetailsResponse>() {
            @Override
            public void onResponse(Call<PendingTransferDetailsResponse> call, Response<PendingTransferDetailsResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null ||response.body().getStatus() == 500) {
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
                        return;
                    }
                    liveData.postValue(null);
                    progressDialog.dismiss();
                }else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<PendingTransferDetailsResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                progressDialog.dismiss();
                liveData.postValue(null);
                Toast.makeText(context, "Something Wrong Contact to Support \n" + this.getClass().getSimpleName() + " " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return liveData;

    }

    public MutableLiveData<EditTransferInfoResonse> getEditTransferData(FragmentActivity context, String orderSid) {
        MutableLiveData<EditTransferInfoResonse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String userID = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();


        Call<EditTransferInfoResonse> call =
                RetrofitClient.getInstance().getApi().getEditTransferInfo(token, userID, vendorId, orderSid);
        call.enqueue(new Callback<EditTransferInfoResonse>() {
            @Override
            public void onResponse(Call<EditTransferInfoResonse> call, Response<EditTransferInfoResonse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<EditTransferInfoResonse> call, Throwable t) {
                liveData.postValue(null);
            }
        });


        return liveData;
    }


}
