package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.GetEditedItemStockResponse;
import com.ismos_salt_erp.serverResponseModel.ProductStockResponse;
import com.ismos_salt_erp.serverResponseModel.SealsRequisitionItemSearchResponse;
import com.ismos_salt_erp.serverResponseModel.SearchTransport;
import com.ismos_salt_erp.serverResponseModel.StoreListByOptionalEnterpriseId;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleViewModel extends ViewModel {



    public MutableLiveData<SearchTransport> searchTransport(FragmentActivity context, String key) {
        MutableLiveData<SearchTransport> liveData = new MutableLiveData<>();

        String currentUserToken = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String currentUserVendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<SearchTransport> call = RetrofitClient.getInstance().getApi()
                .searchTransport(currentUserToken, currentUserVendorId,userId, key);
        call.enqueue(new Callback<SearchTransport>() {
            @Override
            public void onResponse(Call<SearchTransport> call, Response<SearchTransport> response) {
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        liveData.postValue(null);
                        return;
                    }
                    assert response.body() != null;
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());

                    } else {
                        liveData.postValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchTransport> call, Throwable t) {
                liveData.postValue(null);
                Log.d("ERROR", t.getMessage());
            }
        });


        return liveData;
    }


    public MutableLiveData<StoreListByOptionalEnterpriseId> getStoreListByOptionalEnterpriseId(FragmentActivity context
            , String selectedEnterpriseId) {
        MutableLiveData<StoreListByOptionalEnterpriseId> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        Call<StoreListByOptionalEnterpriseId> call
                = RetrofitClient.getInstance().getApi().getStoreListByOptionalEnterpriseId(token, vendorId, userId,selectedEnterpriseId);
        call.enqueue(new Callback<StoreListByOptionalEnterpriseId>() {
            @Override
            public void onResponse(Call<StoreListByOptionalEnterpriseId> call, Response<StoreListByOptionalEnterpriseId> response) {
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

                }
            }

            @Override
            public void onFailure(Call<StoreListByOptionalEnterpriseId> call, Throwable t) {
                liveData.postValue(null);
                Log.d("ERROR", t.getMessage());
            }
        });
        return liveData;
    }


    public MutableLiveData<SealsRequisitionItemSearchResponse> getSearchProduct(
            FragmentActivity context, String key, List<String> categoryIDList) {

        MutableLiveData<SealsRequisitionItemSearchResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userID = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        Call<SealsRequisitionItemSearchResponse> call = RetrofitClient.getInstance().getApi()
                .getSearchProduct(token, vendorId,userID, categoryIDList, key);
        call.enqueue(new Callback<SealsRequisitionItemSearchResponse>() {
            @Override
            public void onResponse(Call<SealsRequisitionItemSearchResponse> call, Response<SealsRequisitionItemSearchResponse> response) {
                if (response.isSuccessful()) {
                try {
                    if (response.body() == null) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());

                    } else {
                        liveData.postValue(null);
                    }
                }catch (Exception e){
                    liveData.postValue(null);
                }
                }
            }

            @Override
            public void onFailure(Call<SealsRequisitionItemSearchResponse> call, Throwable t) {
                liveData.postValue(null);
                Log.d("ERROR", t.getMessage());
            }
        });

        return liveData;
    }


    public MutableLiveData<ProductStockResponse> getProductStockDataByProductId(
            FragmentActivity context, List<String> productIds, String storeId) {
        MutableLiveData<ProductStockResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();


        Call<ProductStockResponse> call = RetrofitClient.getInstance().getApi()
                .getProductStockDataByProductIds(token, productIds, storeId);
        call.enqueue(new Callback<ProductStockResponse>() {
            @Override
            public void onResponse(Call<ProductStockResponse> call, Response<ProductStockResponse> response) {
               try {
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
                   }
               }catch (Exception e){
                   liveData.postValue(null);
               }
            }

            @Override
            public void onFailure(Call<ProductStockResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
            }
        });


        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> newSale(
            FragmentActivity context, String selectedEnterpriseId, String lastOrderId,
            String selectedCustomerId, List<String> productIdList, List<String> storeIdList,
            List<String> quantityList, List<String> unitList, List<String> productTitleList,
            String personName, String personPhone, String vehicleNo, String transportName,List<String> discountList,String collectedPaidAmount
    ,String due,String totalAmount,String discount,List<String> priceList,String vat,String carryCost) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        List<String> totalDiscountList = new ArrayList<>();
        totalDiscountList.clear();
        List<String> productVatList = new ArrayList<>();
        productVatList.clear();
        List<String> sellingPriceList = new ArrayList<>();
        sellingPriceList.clear();
        List<String> storeIdLists = new ArrayList<>();
        storeIdLists.clear();

        for (int i = 0; i < productIdList.size(); i++) {
            totalDiscountList.add("0");
            sellingPriceList.add("0");
            productVatList.add("0");
            storeIdLists.add(storeIdList.get(0));
        }
        /**
         * set current date will send input from user
         */
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        //System.out.println(formatter.format(date));
        String currentDate = formatter.format(date);
        /*System.out.println(dtf.format(now));*/

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().addNewSale(
                token, profileTypeId, vendorId,
                selectedEnterpriseId, lastOrderId, userId, selectedCustomerId, productIdList, storeIdLists, quantityList,
                unitList, priceList, productTitleList, discountList, productVatList, "1"
                , collectedPaidAmount, due, totalAmount, discount, "0", currentDate, personName,
                personPhone, vehicleNo, transportName, "0", "0", "0", carryCost, "",
       vat );

        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                   try {
                       if (response.body() == null) {
                           liveData.postValue(null);
                           return;
                       }
                       if (response.body().getStatus() == 400) {
                           liveData.postValue(response.body());
                           return;
                       }
                       liveData.postValue(response.body());
                   }catch (Exception e){
                       liveData.postValue(null);
                   }
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                liveData.postValue(null);
                Log.d("ERROR", "ERROR");
            }
        });

        return liveData;
    }


    public MutableLiveData<GetEditedItemStockResponse> getItemStockList(
            FragmentActivity context, List<String> productIdList, List<String> soldFromList
    ) {
        MutableLiveData<GetEditedItemStockResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();


        Call<GetEditedItemStockResponse> call = RetrofitClient.getInstance().getApi()
                .getEditedItemStockList(
                        token, vendorId, productIdList, soldFromList);

        call.enqueue(new Callback<GetEditedItemStockResponse>() {
            @Override
            public void onResponse(Call<GetEditedItemStockResponse> call, Response<GetEditedItemStockResponse> response) {
                if (response.isSuccessful()) {
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
            }

            @Override
            public void onFailure(Call<GetEditedItemStockResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });


        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> getTransferItemStockList(
            FragmentActivity context, String orderId, String orderSerial, List<String> productIdList, List<String> soldFromList,
            List<String> quantityList, List<String> productTitleList, List<String> previousQuantityList,
            List<String> oldSoldFromList
    ) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String storeID = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<DuePaymentResponse> call =
                RetrofitClient.getInstance().getApi().getTransferStockList(
                        token, profileTypeId, vendorId, storeID, orderId, orderSerial, userId, productIdList, soldFromList,
                        quantityList, productTitleList, previousQuantityList, oldSoldFromList
                );
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
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
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });


        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> submitEditTransferInfo(
            FragmentActivity context, String orderSerial,String orderID, Set<Integer> productIdList,
            List<Double> quantityList, List<String> productTitleList, List<Double> sellingPriceList,
            List<String> soldFromList,List<String>old_sold_fromList, List<String> previousQuantityList, String transferStoreId, String note,
            String transferFromEnterprise, String transferToEnterPrice
    ) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String storeID = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        Call<DuePaymentResponse> call =
                RetrofitClient.getInstance().getApi().submitEditTransferInfo(
                        token, storeID, profileTypeId, userId, orderSerial,orderID, vendorId, storeID, productIdList, productTitleList,
                        quantityList, sellingPriceList, soldFromList,old_sold_fromList, previousQuantityList, transferStoreId, note,
                        transferFromEnterprise, transferToEnterPrice
                );
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("RESPONSE",""+response.body().getMessage());
                    if (response.body() == null) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }
                    Log.d("RESPONSE",""+response.body().getMessage());
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }

}
