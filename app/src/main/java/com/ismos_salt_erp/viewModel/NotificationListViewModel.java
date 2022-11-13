package com.ismos_salt_erp.viewModel;

import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.NotificationResponse;

import org.jetbrains.annotations.NotNull;

import lombok.AllArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AllArgsConstructor
public class NotificationListViewModel extends ViewModel {
    /**
     * for get notification list list
     *
     * @param vendorID
     */
    public MutableLiveData<NotificationResponse> apiCallForGetNotificationList(FragmentActivity context, String pageNumber, String token, String vendorID,String status,String date) {
        MutableLiveData<NotificationResponse> liveData = new MutableLiveData<>();

        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<NotificationResponse> call = RetrofitClient.getInstance().getApi().getNotificationList(token,pageNumber,"1", vendorID, userId,status,date);
        call.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(@NotNull Call<NotificationResponse> call, @NotNull Response<NotificationResponse> response) {
                if (response.isSuccessful()) {

                        if (response == null) {
                            liveData.postValue(null);


                            return;
                        }
                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
                            return;
                        } if (response.body().getStatus() == 500) {
                            liveData.postValue(null);
                            return;
                        }
                        liveData.postValue(response.body());



                }
                else
                liveData.postValue(null);
            }

            @Override
            public void onFailure(@NotNull Call<NotificationResponse> call, @NotNull Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
                Toast.makeText(context, "Something Wrong Contact to Support \n" + this.getClass().getSimpleName() + " " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return liveData;
    }

}