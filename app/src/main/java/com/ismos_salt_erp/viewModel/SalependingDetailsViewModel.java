package com.ismos_salt_erp.viewModel;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.utils.CustomViewModel;
import com.ismos_salt_erp.serverResponseModel.SalePendingDetailsResponse;

public class SalependingDetailsViewModel extends CustomViewModel {
    public MutableLiveData<SalePendingDetailsResponse> getSalependingDetails(FragmentActivity context) {
        MutableLiveData<SalePendingDetailsResponse> liveData = new MutableLiveData<>();
        String vendorId = getVendorId(context.getApplication());
        String token = getToken(context.getApplication());
        String userId = getUserId(context.getApplication());


    /* *//**//*   Call<SalePendingDetailsResponse> call = RetrofitClient.getInstance().getApi().getSalePendingDetails(SalePendingListAdapter.serialId, token, vendorId, userId );
        call.enqueue(new Callback<SalePendingDetailsResponse>() {
            @Override
            public void onResponse(Call<SalePendingDetailsResponse> call, Response<SalePendingDetailsResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;

                    if (response.body().getStatus()==400){
                        liveData.postValue(response.body());
                        return;
                    }
                    if (response.body().getStatus() == 200) {
                        liveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<SalePendingDetailsResponse> call, Throwable t) {
                liveData.setValue(null);
            }
        }
        );*/

        return liveData;

    }

}
