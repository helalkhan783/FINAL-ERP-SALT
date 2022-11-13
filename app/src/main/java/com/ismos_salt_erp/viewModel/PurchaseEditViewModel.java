package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseEditViewModel extends ViewModel {


    public MutableLiveData<DuePaymentResponse> submitPurchaseEditData(
            FragmentActivity context, String orderId, String orderSerial, String customerId,
            List<String> productIdList, List<String> soldFromList, List<String> quantityList,
            List<String> p_unitList, List<String> selling_priceList, List<String> product_titleList,
            List<String> discountList, List<String> previous_quantityList, String paymentType, String discount_amount,
            String discount_type, String order_date, String total_amount, String paid_amount, String custom_discount, String note
    ) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();
        String userID = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<DuePaymentResponse> call =
                RetrofitClient.getInstance().getApi().
                        submitPurchaseEditData(
                                token, profileTypeId, vendorID, storeId, orderId,
                                orderSerial, userID, customerId, productIdList, soldFromList,
                                quantityList, p_unitList, selling_priceList, product_titleList,
                                discountList, previous_quantityList, paymentType, discount_amount,
                                discount_type, order_date, total_amount, paid_amount, custom_discount,
                                note);

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
                        return;
                    }

                    liveData.postValue(response.body());
                    return;
                }else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                liveData.postValue(null);
                Log.d("ERROR", "" + t.getMessage());
            }
        });

        return liveData;
    }

}
