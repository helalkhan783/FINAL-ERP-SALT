package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.CustomerAndSupplierResponse;
import com.ismos_salt_erp.serverResponseModel.CustomerSearchResponse;
import com.ismos_salt_erp.serverResponseModel.DueOrdersResponse;
import com.ismos_salt_erp.utils.CustomViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DueCollectionViewModel extends CustomViewModel {
    private MutableLiveData<CustomerSearchResponse> customerList;
    private MutableLiveData<CustomerAndSupplierResponse> customerAndSupplierList;
    private MutableLiveData<DueOrdersResponse> getDueOrders;
    private MutableLiveData<Double> getCurrentDue;

    public DueCollectionViewModel() {
        customerList = new MutableLiveData<>();
        customerAndSupplierList = new MutableLiveData<>();
        getDueOrders = new MutableLiveData<>();
        getCurrentDue = new MutableLiveData<>();
    }


    public MutableLiveData<CustomerSearchResponse> getCustomerList() {
        if (customerList != null) {
            return customerList;
        }
        return null;
    }

    public MutableLiveData<CustomerAndSupplierResponse> getCustomerAndSupplierList() {
        if (customerAndSupplierList != null) {
            return customerAndSupplierList;
        }
        return null;
    }

    /**
     * for get selected customer Orders with lots of information by selected customer and vendor id.
     *
     * @return
     */
    public MutableLiveData<DueOrdersResponse> getGetDueOrders() {
        if (getDueOrders != null) {
            return getDueOrders;
        }
        return null;
    }


    public MutableLiveData<Double> getCurrentDue(double totalDue, double payDueAmount) {
        if (totalDue >= 1 && payDueAmount <= totalDue) {
            double currentDue = totalDue - payDueAmount;
            getCurrentDue.postValue(currentDue);
            return getCurrentDue;
        }
        return null;
    }


    public void apiCallForGetSupplier(FragmentActivity context, String token, String vendorID, String key) {
        Call<CustomerSearchResponse> call = RetrofitClient.getInstance().getApi().getSuppliersByKey(token, vendorID, key);
        call.enqueue(new Callback<CustomerSearchResponse>() {
            @Override
            public void onResponse(Call<CustomerSearchResponse> call, Response<CustomerSearchResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        return;
                    }
                    customerList.postValue(response.body());
                } else {
                    customerList.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<CustomerSearchResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                customerList.postValue(null);
            }
        });
    }


    public void apiCallForGetRefPerson(FragmentActivity context, String token, String vendorID, String key) {
        Call<CustomerSearchResponse> call = RetrofitClient.getInstance().getApi().getRefPersonByKey(token, vendorID, key);
        call.enqueue(new Callback<CustomerSearchResponse>() {
            @Override
            public void onResponse(Call<CustomerSearchResponse> call, Response<CustomerSearchResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        return;
                    }
                    if (response.body().getStatus() == 200) {
                        customerList.postValue(response.body());
                    }
                } else {
                    customerList.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<CustomerSearchResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                customerList.postValue(null);
            }
        });
    }


    /**
     * for get selected customer information
     */
    public void apiCallForGetCustomers(FragmentActivity context, String token, String vendorID, String key) {
        Call<CustomerSearchResponse> call = RetrofitClient.getInstance().getApi().getCustomersByKey(token, vendorID, key);
        call.enqueue(new Callback<CustomerSearchResponse>() {
            @Override
            public void onResponse(Call<CustomerSearchResponse> call, Response<CustomerSearchResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        customerList.postValue(null);
                        return;
                    }
                    customerList.postValue(response.body());
                } else {
                    customerList.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<CustomerSearchResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                customerList.postValue(null);
            }
        });
    }

    /**
     * for get selected customer information
     */
    public void apiCallForGetAndSupplierListCustomers(FragmentActivity context, String token, String vendorID, String key, String userId) {
        Call<CustomerAndSupplierResponse> call = RetrofitClient.getInstance().getApi().getCustomersAndSupplierByKey(token, userId, vendorID, key);
        call.enqueue(new Callback<CustomerAndSupplierResponse>() {
            @Override
            public void onResponse(Call<CustomerAndSupplierResponse> call, Response<CustomerAndSupplierResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        customerAndSupplierList.postValue(null);
                        return;
                    }
                    customerAndSupplierList.postValue(response.body());
                } else {
                    customerAndSupplierList.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<CustomerAndSupplierResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                customerAndSupplierList.postValue(null);
            }
        });
    }


    /**
     * for get selected customer Orders with lots of information by selected customer and vendor id.
     *
     * @param context
     * @param selectedCustomerId
     * @param vendorId
     */
    public void apiCallForgetDueOrdersBySelectedCustomerIdAndVendorId(FragmentActivity context, String selectedCustomerId, String vendorId) {

        String currentUserToken = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        Call<DueOrdersResponse> call = RetrofitClient.getInstance().getApi().dueOrdersByCustomerId(currentUserToken, vendorId, selectedCustomerId);
        call.enqueue(new Callback<DueOrdersResponse>() {
            @Override
            public void onResponse(Call<DueOrdersResponse> call, Response<DueOrdersResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        getDueOrders.postValue(null);
                        return;
                    }
                    getDueOrders.postValue(response.body());
                } else {
                    getDueOrders.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DueOrdersResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                getDueOrders.postValue(null);
            }
        });

    }

    public void getOrderListByCustomer(FragmentActivity context, String customerId, String value) {
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

    }


}
