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
import com.ismos_salt_erp.serverResponseModel.EditIodizationStock;
import com.ismos_salt_erp.serverResponseModel.EditWashingCrushingResponse;
import com.ismos_salt_erp.serverResponseModel.PendingIodizationDetailsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IodizationViewModel extends ViewModel {
    public IodizationViewModel() {
    }

    public MutableLiveData<PendingIodizationDetailsResponse> getPendingIodizationDetails(FragmentActivity context, String orderId, String vendorId) {
        MutableLiveData<PendingIodizationDetailsResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();



        Call<PendingIodizationDetailsResponse> call = RetrofitClient.getInstance().getApi()
                .getPendingIodizationDetails(token, vendorId,userId, orderId);
        call.enqueue(new Callback<PendingIodizationDetailsResponse>() {
            @Override
            public void onResponse(Call<PendingIodizationDetailsResponse> call, Response<PendingIodizationDetailsResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null || response.body().getStatus() ==500) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() ==400) {
                        liveData.postValue(response.body());
                        return;
                    }   if (response.body().getStatus() ==200) {
                        liveData.postValue(response.body());
                        return;
                    }

                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<PendingIodizationDetailsResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
             }
        });


        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> approveIodizationDetails(FragmentActivity context, String orderID, String note) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String userID = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().approveIodization(
                token, orderID, userID, vendorID, note, profileTypeId
        );
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                    }

                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });


        return liveData;
    }

    public MutableLiveData<DuePaymentResponse> declineIodizationDetails(FragmentActivity context, String orderID, String note) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String userID = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().declineIodization(
                token, orderID, userID, vendorID, note, profileTypeId
        );
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

                    liveData.postValue(response.body());
                    return;
                } else {
                    liveData.postValue(null);
                    return;
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });


        return liveData;
    }


    public MutableLiveData<EditWashingCrushingResponse> getIodizationPageData(FragmentActivity context, String orderSerial) {
        MutableLiveData<EditWashingCrushingResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();


        Call<EditWashingCrushingResponse> call = RetrofitClient.getInstance().getApi().getIodizationPageData(
                token, vendorID, orderSerial);


        call.enqueue(new Callback<EditWashingCrushingResponse>() {
            @Override
            public void onResponse(Call<EditWashingCrushingResponse> call, Response<EditWashingCrushingResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                    }

                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<EditWashingCrushingResponse> call, Throwable t) {
                liveData.postValue(null);
                Log.d("ERROR", "ERROR");
            }
        });
        return liveData;
    }


    public MutableLiveData<EditIodizationStock> getEditIodizationStock(FragmentActivity context, String orderId) {
        MutableLiveData<EditIodizationStock> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();


        Call<EditIodizationStock> call = RetrofitClient.getInstance()
                .getApi().getEditIodizationStock(token, vendorID, orderId);
        call.enqueue(new Callback<EditIodizationStock>() {
            @Override
            public void onResponse(Call<EditIodizationStock> call, Response<EditIodizationStock> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response == null) {
                            liveData.postValue(null);
                        }
                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
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
            public void onFailure(Call<EditIodizationStock> call, Throwable t) {
                liveData.postValue(null);
                Log.d("ERROR", "ERROR");
            }
        });
        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> submitEditIodizationInfo(
            FragmentActivity context, String orderId, String orderSerial, String customerId,
            List<String> productIdList, List<String> sold_fromList, List<String> quantityList,
            List<String> selling_priceList, List<String> productTitleList, List<String> discountList,
            List<String> previousQuantityList, List<String> oldSoldFromList, String orderDate,
            String totalAmount, String note, String old_destination_store, String stage,
            String destination_store
    ) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();
        String userID = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<DuePaymentResponse> call =
                RetrofitClient.getInstance().getApi().
                        submitIodizationEdit(
                                token, profileTypeId, vendorID, storeId, orderId, orderSerial, userID,
                                customerId, productIdList, sold_fromList, quantityList, selling_priceList,
                                productTitleList, discountList, previousQuantityList, oldSoldFromList,
                                orderDate, totalAmount, note, old_destination_store, stage, destination_store);
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
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
                        Log.d("ERROR", "" + e.getMessage());
                    }
                } else {
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


    public MutableLiveData<DuePaymentResponse> editIodizationStockMessageCheck(
            FragmentActivity context, String orderId, String orderSerial, List<String> productIdList, List<String> soldFromList,
            List<String> quantityList, List<String> productTitleList, List<String> previousQuantityList, List<String> oldSoldFromList
    ) {

        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();
        String userID = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<DuePaymentResponse> call =
                RetrofitClient.getInstance().getApi().editIodizationStockStockMessageCheck(
                        token, profileTypeId, vendorID, storeId, orderId, orderSerial, userID, productIdList, soldFromList,
                        quantityList, productTitleList, previousQuantityList, oldSoldFromList
                );


        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                    }

                    liveData.postValue(response.body());
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
