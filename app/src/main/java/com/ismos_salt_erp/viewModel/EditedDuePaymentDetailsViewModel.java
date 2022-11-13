package com.ismos_salt_erp.viewModel;

import android.app.ProgressDialog;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.EditedPaymentDueResponse;

import lombok.AllArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AllArgsConstructor
public class EditedDuePaymentDetailsViewModel extends ViewModel {
    /**
     * For decline edited payment
     */
    public MutableLiveData<DuePaymentResponse> declineEditedPayment(FragmentActivity context, String id, String note) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        ProgressDialog progressDialog = new ProgressDialog(context);


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userID = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        progressDialog.show();

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .declineEditedPendingPayment(token, vendorId, id, userID, note);
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    liveData.postValue(response.body());

                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                progressDialog.dismiss();
                liveData.postValue(null);
            }
        });


        return liveData;
    }


    /**
     * For approve edited payment
     */


    public MutableLiveData<DuePaymentResponse> approveEditedPayment(FragmentActivity context, String id, String note) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();


        ProgressDialog progressDialog = new ProgressDialog(context);

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        progressDialog.show();


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .approveEditedPayment(token, vendorId, id, userId, note);
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.isSuccessful()) {
                        if (response == null) {
                            liveData.postValue(null);
                            return;
                        }
                        liveData.postValue(response.body());

                    } else {
                        liveData.postValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                progressDialog.dismiss();
                liveData.postValue(null);
            }
        });

        return liveData;
    }


    /**
     * For get EditedPaymentDueDetails
     */
    public MutableLiveData<EditedPaymentDueResponse> getEditedPaymentDueDetails(FragmentActivity context, String orderId) {

        MutableLiveData<EditedPaymentDueResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();


        Call<EditedPaymentDueResponse> call = RetrofitClient.getInstance().getApi()
                .getEditedPaymentDueDetailsResponseCall(token, vendorId, orderId);
        call.enqueue(new Callback<EditedPaymentDueResponse>() {
            @Override
            public void onResponse(Call<EditedPaymentDueResponse> call, Response<EditedPaymentDueResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.isSuccessful()) {
                        if (response == null) {
                            liveData.postValue(null);
                            return;
                        }
                        liveData.postValue(response.body());

                    } else {
                        liveData.postValue(null);
                    }
                }

            }

            @Override
            public void onFailure(Call<EditedPaymentDueResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                progressDialog.dismiss();
                liveData.postValue(null);
            }
        });
        return liveData;

    }


}
