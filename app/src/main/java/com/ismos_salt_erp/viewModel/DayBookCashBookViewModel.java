package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.CashBookResponse;
import com.ismos_salt_erp.serverResponseModel.DayBookResponse;
import com.ismos_salt_erp.serverResponseModel.DebitorsAndCreditorsResponse;
import com.ismos_salt_erp.utils.CustomViewModel;

import lombok.AllArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AllArgsConstructor
public class DayBookCashBookViewModel extends CustomViewModel {
    /**
     * For get Cash book
     */
    public MutableLiveData<CashBookResponse> getCashResponse(FragmentActivity context,String page, String startDate, String endDate, String millerProfileId, String payment, String receipt,String transactionTypeId) {
        MutableLiveData<CashBookResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        Call<CashBookResponse> call = RetrofitClient.getInstance().getApi()
                .getCashBookForHomePage(token, page,vendorID, getUserId(context.getApplication()), startDate, endDate, millerProfileId, payment, receipt,transactionTypeId);

        call.enqueue(new Callback<CashBookResponse>() {
            @Override
            public void onResponse(Call<CashBookResponse> call, Response<CashBookResponse> response) {

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
                        liveData.postValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<CashBookResponse> call, Throwable t) {

                Log.d("ERROR", t.getMessage());
            }
        });
        return liveData;
    }


    /**
     * for get day book
     */
    public MutableLiveData<DayBookResponse> getDaybookResponse(FragmentActivity context, String startDate, String endDate, String millerProfileId, String payment, String receipt,String transactionTypeId) {
        MutableLiveData<DayBookResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();


        Call<DayBookResponse> call = RetrofitClient.getInstance().getApi().getDayBookForHomePage(token, vendorID, getUserId(context.getApplication()), startDate, endDate, millerProfileId, payment, receipt,transactionTypeId);

        call.enqueue(new Callback<DayBookResponse>() {
            @Override
            public void onResponse(Call<DayBookResponse> call, Response<DayBookResponse> response) {
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
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DayBookResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
            }
        });
        return liveData;
    }


    public MutableLiveData<DebitorsAndCreditorsResponse> getDebitorsList(FragmentActivity context, String startDate, String endDate, String company, String payment, String type,String user) {
        MutableLiveData<DebitorsAndCreditorsResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();


        Call<DebitorsAndCreditorsResponse> call = RetrofitClient.getInstance().getApi()
                .gteDebitorsData(token, vendorID, getUserId(context.getApplication()), startDate, endDate ,company,user, payment,type);

        call.enqueue(new Callback<DebitorsAndCreditorsResponse>() {
            @Override
            public void onResponse(Call<DebitorsAndCreditorsResponse> call, Response<DebitorsAndCreditorsResponse> response) {
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
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DebitorsAndCreditorsResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
            }
        });
        return liveData;
    }


}
