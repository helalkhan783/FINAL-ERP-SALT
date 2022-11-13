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

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRequisitionViewmodel extends ViewModel {
    private ProgressDialog progressDialog;
    MutableLiveData<DuePaymentResponse> duePaymentResponseMutableLiveData;

    public AddRequisitionViewmodel() {
        duePaymentResponseMutableLiveData = new MutableLiveData<>();
    }
    public MutableLiveData<DuePaymentResponse> isAddRequisitionSuccessful() {
        return duePaymentResponseMutableLiveData;
    }


    public MutableLiveData<DuePaymentResponse>  apiCallForCreateRequisition(FragmentActivity context,
                                            List<String> productIdList, String enterPriceId, List<String> unitList, String lastUserId, String findingCustomerId,
                                            List<String> productTitleList, List<String> sellingPriceList, List<String> quantityList, String totalDiscount,
                                            String collectedAmount, String paymentTypeVal,
                                            String startOrderDate, String endOrderDate) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String storeAccess = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getStoreAccess());
        String profile_type_id = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();


        /**
         * here isConfirm 0 byDefault provide from backend (will change)
         */
        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().addRequisition(
                token, profile_type_id, vendorId, storeId, productIdList, enterPriceId, unitList, lastUserId,
                userId, findingCustomerId, productTitleList, sellingPriceList, quantityList, totalDiscount, "0",
                collectedAmount, "0", paymentTypeVal, startOrderDate, endOrderDate
        );
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                 Log.d("SMS", response.body().getMessage());
                if (response.isSuccessful()) {
                    if (response == null || response.body().getStatus() == 500){
                        liveData.postValue(null);
                        return;
                    }if (  response.body().getStatus() == 400){
                        liveData.postValue(response.body());
                        return;
                    }
                    if (response.body().getStatus() == 200) {
                        Log.d("SUCCESS", "successful");
                        liveData.postValue(response.body());

                    }
                }
                else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());

                liveData.postValue(null);
             }
        });
  return  liveData;  }
}
