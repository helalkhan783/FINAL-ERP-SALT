package com.ismos_salt_erp.viewModel;

import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.GetPendingWashingCrushingDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetPendingWashingCrushingViewModel extends ViewModel {


    /**
     * For get pending and crushing details
     */
    public MutableLiveData<GetPendingWashingCrushingDetailsResponse> getPendingWashingDetails(FragmentActivity context, String orderId, String vendorId) {

        MutableLiveData<GetPendingWashingCrushingDetailsResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        Call<GetPendingWashingCrushingDetailsResponse> call = RetrofitClient.getInstance().getApi()
                .getPendingWashingDetails(token, vendorId, orderId, userId);

        call.enqueue(new Callback<GetPendingWashingCrushingDetailsResponse>() {
            @Override
            public void onResponse(Call<GetPendingWashingCrushingDetailsResponse> call, Response<GetPendingWashingCrushingDetailsResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response == null) {
                            liveData.postValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
                            return;
                        }
                        liveData.postValue(response.body());
                    } catch (Exception e) {
                        liveData.postValue(null);
                    }
                } else {
                    liveData.postValue(null);
                }

            }

            @Override
            public void onFailure(Call<GetPendingWashingCrushingDetailsResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());

                liveData.postValue(null);
                Toast.makeText(context, "Something Wrong Contact to Support \n" + this.getClass().getSimpleName() + " " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        return liveData;

    }


}
