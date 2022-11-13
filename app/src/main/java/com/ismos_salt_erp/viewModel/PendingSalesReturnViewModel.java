package com.ismos_salt_erp.viewModel;

import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.PendingSalesReturnDetailsResponse;

import lombok.AllArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AllArgsConstructor
public class PendingSalesReturnViewModel extends ViewModel {

    /**
     * For decline pendingSalesReturnsDetails
     **/

    public MutableLiveData<DuePaymentResponse> declinePendingSalesReturnDetails(FragmentActivity context, String orderId, String note) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .declinePendingSalesReturnDetails(token, vendorId, orderId, userId, note);


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
                        return;
                    }
                } else {
                    liveData.postValue(null);
                    return;
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
            }
        });

        return liveData;
    }


    /**
     * For approve pendingSalesReturnsDetails
     **/
    public MutableLiveData<DuePaymentResponse> approvePendingSalesReturnDetails(FragmentActivity context, String orderId, String note) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .approvePendingSalesReturnDetails(token, vendorId, orderId, userId, note);


        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    liveData.postValue(response.body());
                    return;
                } else {
                    liveData.postValue(null);
                    return;
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
            }
        });

        return liveData;
    }

    /**
     * For get PendingSalesReturnDetails
     */

    public MutableLiveData<PendingSalesReturnDetailsResponse> getPendingSalesReturnDetails(FragmentActivity context, String orderId, String vendorId) {
        MutableLiveData<PendingSalesReturnDetailsResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<PendingSalesReturnDetailsResponse> call = RetrofitClient.getInstance().getApi()
                .getPendingSalesReturnDetailsResponse(token, vendorId,userId, orderId);
        call.enqueue(new Callback<PendingSalesReturnDetailsResponse>() {
            @Override
            public void onResponse(Call<PendingSalesReturnDetailsResponse> call, Response<PendingSalesReturnDetailsResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    liveData.postValue(response.body());
                    return;
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<PendingSalesReturnDetailsResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
            }
        });
        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> approveSalesReturnWholeOrderCancel(
            FragmentActivity context, String id, String note
    ) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .approveSalesReturnWholeOrderCancel(token, vendorId, id, userId, profileTypeId, note);


        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
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
                Toast.makeText(context, "Something Wrong Contact to Support \n" + this.getClass().getSimpleName() + " " + t.getMessage(), Toast.LENGTH_LONG).show();
                liveData.postValue(null);
            }
        });

        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> declineSalesReturnWholeOrderCancel(
            FragmentActivity context, String id, String note
    ) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .declineSalesReturnWholeOrderCancel(token, vendorId, id, userId, profileTypeId, note);


        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
            }
        });

        return liveData;
    }


}
