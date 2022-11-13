package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.HomepageListResponse;
import com.ismos_salt_erp.utils.CustomViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePageListViewModel extends CustomViewModel {

    public MutableLiveData<HomepageListResponse> homepageList(FragmentActivity context, String pageNumber, String categoryId, String typeId) {
        MutableLiveData<HomepageListResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String vendorID = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<HomepageListResponse> call = RetrofitClient.getInstance().getApi().homePageList(token, pageNumber, vendorID, userId, categoryId, typeId);


        call.enqueue(new Callback<HomepageListResponse>() {
            @Override
            public void onResponse(Call<HomepageListResponse> call, Response<HomepageListResponse> response) {
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

                        if (response.body().getStatus() == 200) {
                            liveData.postValue(response.body());
                            return;
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", e.getMessage());
                    }


                }
                else {
                    liveData.postValue(null);
                    return;
                }
            }

            @Override
            public void onFailure(Call<HomepageListResponse> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
                liveData.postValue(null);
            }
        });

        return liveData;
    }

}
