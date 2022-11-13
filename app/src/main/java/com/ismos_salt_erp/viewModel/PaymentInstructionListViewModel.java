package com.ismos_salt_erp.viewModel;

import android.app.ProgressDialog;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.PaymentInstruction;
import com.ismos_salt_erp.utils.CustomViewModel;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentInstructionListViewModel extends CustomViewModel {
    public MutableLiveData<PaymentInstruction> apiCallForGetPaymentInstructionTotalList(FragmentActivity context, String vendorID,String startDate,String endDate,String supplier) {
        MutableLiveData<PaymentInstruction> liveData = new MutableLiveData<>();
        Call<PaymentInstruction> call = RetrofitClient.getInstance().getApi().getPaymentInstructionList(getToken(context.getApplication()), vendorID, getUserId(context.getApplication()),startDate, endDate, supplier);

    call.enqueue(new Callback<PaymentInstruction>() {
        @Override
        public void onResponse(Call<PaymentInstruction> call, Response<PaymentInstruction> response) {
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
        public void onFailure(Call<PaymentInstruction> call, Throwable t) {
            liveData.postValue(null);

        }
    });


    return liveData;
    }




    public MutableLiveData<DuePaymentResponse> updatePaymentLimit(FragmentActivity context, String id, String dueLimit,String date) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().updateDPaymentLimit(token, vendorId, userId, id, dueLimit,date);


        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {

                if (response.isSuccessful()) {
                    try {
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
                            return;

                        }

                    } catch (Exception e) {
                        Log.d("Error", e.getMessage());
                        liveData.postValue(null);
                    }
                }else liveData.postValue(null);

            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", "" + t.getMessage());
                liveData.postValue(null);
            }
        });

        return liveData;
    }

}
