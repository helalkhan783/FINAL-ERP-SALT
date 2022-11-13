package com.ismos_salt_erp.viewModel;


 import android.app.ProgressDialog;
 import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.GetEnterpriseResponse;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItemListResponse;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.serverResponseModel.SealsRequisitionItemSearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesRequisitionViewModel extends ViewModel {
    private ProgressDialog progressDialog;
    MutableLiveData<SealsRequisitionItemSearchResponse> sealsRequisitionItemSearchResponseMutableLiveData;
    MutableLiveData<List<SalesRequisitionItems>> salesRequisitionItemsMutableLiveData;


    public SalesRequisitionViewModel() {
        sealsRequisitionItemSearchResponseMutableLiveData = new MutableLiveData<>();
        salesRequisitionItemsMutableLiveData = new MutableLiveData<>();
    }
    /**
     * for get enterprise
     */
    public MutableLiveData<GetEnterpriseResponse> getEnterpriseResponse(FragmentActivity context) {
        MutableLiveData<GetEnterpriseResponse> getEnterpriseResponseMutableLiveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String storeAccess = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getStoreAccess());
        String profile_type_id = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        Call<GetEnterpriseResponse> call = RetrofitClient.getInstance().getApi().getEnterprise(token, vendorId, userId,storeAccess, profile_type_id, storeId);
        call.enqueue(new Callback<GetEnterpriseResponse>() {
            @Override
            public void onResponse(Call<GetEnterpriseResponse> call, Response<GetEnterpriseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        getEnterpriseResponseMutableLiveData.postValue(null);
                        return;
                    }
                    assert response.body() != null;
                    if (response.body().getStatus() == 200) {
                        getEnterpriseResponseMutableLiveData.postValue(response.body());

                    } else {
                        getEnterpriseResponseMutableLiveData.postValue(null);
                    }
                } else {
                    getEnterpriseResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<GetEnterpriseResponse> call, Throwable t) {
                getEnterpriseResponseMutableLiveData.postValue(null);
                Log.d("ERROR", t.getMessage());
            }
        });

        return getEnterpriseResponseMutableLiveData;
    }

    public MutableLiveData<GetEnterpriseResponse> forGetUserEnterprise(FragmentActivity context) {
        MutableLiveData<GetEnterpriseResponse> getEnterpriseResponseMutableLiveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String storeAccess = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getStoreAccess());
        String profile_type_id = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        Call<GetEnterpriseResponse> call = RetrofitClient.getInstance().getApi().forGetUserEnterprise(token, vendorId,userId, storeAccess, profile_type_id, storeId);
        call.enqueue(new Callback<GetEnterpriseResponse>() {
            @Override
            public void onResponse(Call<GetEnterpriseResponse> call, Response<GetEnterpriseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        getEnterpriseResponseMutableLiveData.postValue(null);
                        return;
                    }
                    assert response.body() != null;
                    if (response.body().getStatus() == 200) {
                        getEnterpriseResponseMutableLiveData.postValue(response.body());

                    } else {
                        getEnterpriseResponseMutableLiveData.postValue(null);
                    }
                } else {
                    getEnterpriseResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<GetEnterpriseResponse> call, Throwable t) {
                getEnterpriseResponseMutableLiveData.postValue(null);
                Log.d("ERROR", t.getMessage());
            }
        });

        return getEnterpriseResponseMutableLiveData;
    }


    public MutableLiveData<List<SalesRequisitionItems>> getSalesRequisitionItemList(FragmentActivity context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        Call<SalesRequisitionItemListResponse> call = RetrofitClient.getInstance().getApi()
                .getSalesRequisitionItemsList(token, vendorId,userId);

        call.enqueue(new Callback<SalesRequisitionItemListResponse>() {
            @Override
            public void onResponse(Call<SalesRequisitionItemListResponse> call, Response<SalesRequisitionItemListResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 200) {
                        salesRequisitionItemsMutableLiveData.postValue(response.body().getItems());
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<SalesRequisitionItemListResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
            }
        });
        return salesRequisitionItemsMutableLiveData;
    }

    /**
     * get sales requisition items by searching Name
     */

    public MutableLiveData<SealsRequisitionItemSearchResponse> getSearchSalesRequisitionItemByName() {
        return sealsRequisitionItemSearchResponseMutableLiveData;
    }

    /**
     * api call for search sales requisition item by Name
     */

    public void apiCallForSearchSalesRequisitionItemByName(FragmentActivity context, List<String> categoryIDs, String searchKey) {
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        Call<SealsRequisitionItemSearchResponse> call =
                RetrofitClient.getInstance().getApi().searchSalesRequisitionItemByName(token, vendorId, userId,categoryIDs, searchKey);
        call.enqueue(new Callback<SealsRequisitionItemSearchResponse>() {
            @Override
            public void onResponse(Call<SealsRequisitionItemSearchResponse> call, Response<SealsRequisitionItemSearchResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        sealsRequisitionItemSearchResponseMutableLiveData.postValue(null);
                        return;
                    }
                    sealsRequisitionItemSearchResponseMutableLiveData.postValue(response.body());
                    return;
                }
                sealsRequisitionItemSearchResponseMutableLiveData.postValue(null);
            }

            @Override
            public void onFailure(Call<SealsRequisitionItemSearchResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                sealsRequisitionItemSearchResponseMutableLiveData.postValue(null);
            }
        });
    }
}
