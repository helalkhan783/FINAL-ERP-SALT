package com.ismos_salt_erp.viewModel;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DueOrdersResponse;
import com.ismos_salt_erp.utils.CustomViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerOrderViewmodel extends CustomViewModel {

    public MutableLiveData<DueOrdersResponse> getOrderListByCustomer(FragmentActivity context, String customerId, String value) {
        Call<DueOrdersResponse> call = RetrofitClient.getInstance().getApi().dueOrdersByCustomerId(getToken(context.getApplication()), getVendorId(context.getApplication()), customerId);
        MutableLiveData<DueOrdersResponse> liveData = new MutableLiveData<>();
        call.enqueue(new Callback<DueOrdersResponse>() {
            @Override
            public void onResponse(Call<DueOrdersResponse> call, Response<DueOrdersResponse> response) {
                if (response.isSuccessful()){
                    if (response == null || response.body().getStatus() == 500){
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }   if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());
                        return;
                    }
                }

                else
                    liveData.postValue(null);

            }

            @Override
            public void onFailure(Call<DueOrdersResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });
return  liveData;
    }

}
