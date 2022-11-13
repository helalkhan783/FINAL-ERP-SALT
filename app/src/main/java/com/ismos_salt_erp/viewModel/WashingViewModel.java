package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.AvailableKioResponse;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.ProductionOutputResponse;
import com.ismos_salt_erp.utils.CustomViewModel;
import com.ismos_salt_erp.serverResponseModel.WashingCrushingResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WashingViewModel extends CustomViewModel {


    public MutableLiveData<WashingCrushingResponse> getWashing(FragmentActivity context, String pageNumber, String startDate, String endDate, String itemId, String enterpriseID) {

        MutableLiveData<WashingCrushingResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String userId = getUserId(context.getApplication());
        String vendorId = getVendorId(context.getApplication());

        Call<WashingCrushingResponse> call = RetrofitClient.getInstance().getApi().getWashingList(token, pageNumber, userId, vendorId, startDate, endDate, itemId, enterpriseID);
        call.enqueue(new Callback<WashingCrushingResponse>() {
            @Override
            public void onResponse(Call<WashingCrushingResponse>
                                           call, Response<WashingCrushingResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response == null||response.body().getStatus() == 500) {
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
                } else {
                    liveData.postValue(null);
                }

            }

            @Override
            public void onFailure(Call<WashingCrushingResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
            }
        });


        return liveData;
    }


    public MutableLiveData<WashingCrushingResponse> getWashingPendingList(FragmentActivity context, String pageNumber, String startDate, String endDate, String itemId, String enterpriseID) {

        MutableLiveData<WashingCrushingResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String userId = getUserId(context.getApplication());
        String vendorId = getVendorId(context.getApplication());

        Call<WashingCrushingResponse> call = RetrofitClient.getInstance().getApi().getWashingPendingList(token, pageNumber, userId, vendorId, startDate, endDate, itemId, enterpriseID);
        call.enqueue(new Callback<WashingCrushingResponse>() {
            @Override
            public void onResponse(Call<WashingCrushingResponse> call, Response<WashingCrushingResponse> response) {
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
                } else {
                    liveData.postValue(null);
                }

            }

            @Override
            public void onFailure(Call<WashingCrushingResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.setValue(null);
            }
        });


        return liveData;
    }


    public MutableLiveData<ProductionOutputResponse> getProductionOutputList(FragmentActivity context, String production_type) {
        MutableLiveData<ProductionOutputResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();


        Call<ProductionOutputResponse> call = RetrofitClient.getInstance().getApi()
                .getProductionOutputList(token, vendorId, production_type);
        call.enqueue(new Callback<ProductionOutputResponse>() {
            @Override
            public void onResponse(Call<ProductionOutputResponse> call, Response<ProductionOutputResponse> response) {
                if (response == null) {
                    liveData.postValue(null);
                    return;
                }
                if (response.body().getStatus() != 200) {
                    liveData.postValue(null);
                    return;
                }

                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<ProductionOutputResponse> call, Throwable t) {
                Log.d("ERROR", "ERORR");
                liveData.postValue(null);
            }
        });

        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> addNewWashingAndCrushing(
            FragmentActivity context, String selectedEnterpriseId, String lastOrderId,
            String selectedCustomerId, List<String> productIdList, List<String> storeIdList,
            List<String> quantityList, List<String> unitList, List<String> productTitleList,
            String note, String outputStore, String toStoreID, String orderType, String date) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        List<String> totalDiscountList = new ArrayList<>();
        totalDiscountList.clear();
        List<String> productVatList = new ArrayList<>();
        productVatList.clear();
        List<String> buyingPriceList = new ArrayList<>();
        buyingPriceList.clear();
        List<String> storeIdLists = new ArrayList<>();
        storeIdLists.clear();

        for (int i = 0; i < productIdList.size(); i++) {
            totalDiscountList.add("0");
            productVatList.add("0");
            buyingPriceList.add("0");
            storeIdLists.add(storeIdList.get(0));
        }

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().addNewWashingAndCrushing(
                token, profileTypeId, vendorId,
                selectedEnterpriseId, lastOrderId, userId, selectedCustomerId, productIdList, storeIdLists, quantityList,
                unitList, buyingPriceList, productTitleList, totalDiscountList, productVatList, "0"
                , "0", "0", "0", "0", "0", date, note, outputStore,
                toStoreID, orderType, "0",
                ""
        );
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.body() == null) {
                    liveData.postValue(null);
                    return;
                }
                if (response.body().getStatus() == 400) {
                    liveData.postValue(response.body());
                    return;
                }

                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", "ERROR");
                liveData.postValue(null);
            }
        });
        return liveData;
    }


    public MutableLiveData<AvailableKioResponse> getAvailableKio(FragmentActivity context, String enterpriseId) {
        MutableLiveData<AvailableKioResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();


        Call<AvailableKioResponse> call = RetrofitClient.getInstance().getApi().getAvailableKio(token, enterpriseId);
        call.enqueue(new Callback<AvailableKioResponse>() {
            @Override
            public void onResponse(Call<AvailableKioResponse> call, Response<AvailableKioResponse> response) {
                if (response == null) {
                    liveData.postValue(null);
                    return;
                }
                if (response.body().getStatus() != 200) {
                    liveData.postValue(null);
                    return;
                }

                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<AvailableKioResponse> call, Throwable t) {
                Log.d("ERROR", "ERORR");
                liveData.postValue(null);
            }
        });


        return liveData;
    }

}
