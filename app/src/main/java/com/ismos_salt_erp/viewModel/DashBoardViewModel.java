package com.ismos_salt_erp.viewModel;

import android.app.ProgressDialog;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DashBoardResponse;
import com.ismos_salt_erp.serverResponseModel.DashboardDataResponse;
import com.ismos_salt_erp.serverResponseModel.ExpenseResponse;
import com.ismos_salt_erp.serverResponseModel.ProductionResponse;
import com.ismos_salt_erp.serverResponseModel.TodaysSaleOrPurchaseResponse;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.DashboardBalanceResponse;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.PieChartDataresponse;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.ProductionResponseJust;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.QcQaResponseTodays;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.SalePurchaseJustResponse;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.TopTenSupplierAndCustomerResponse;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardViewModel extends ViewModel {

    public MutableLiveData<DashBoardResponse> getDashBoardResponse(FragmentActivity context) {
        MutableLiveData<DashBoardResponse> liveData = new MutableLiveData<>();

        ProgressDialog progressDialog = new ProgressDialog(context);
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();

        progressDialog.show();

        Call<DashBoardResponse> call = RetrofitClient.getInstance().getApi()
                .getDashBoardResponse(token, vendorID);
        call.enqueue(new Callback<DashBoardResponse>() {
            @Override
            public void onResponse(Call<DashBoardResponse> call, Response<DashBoardResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<DashBoardResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                Toasty.error(context, "" + t.getMessage(), Toasty.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        return liveData;

    }

    public MutableLiveData<DashboardDataResponse> dashboardData(FragmentActivity context) {
        MutableLiveData<DashboardDataResponse> liveData = new MutableLiveData<>();

        ProgressDialog progressDialog = new ProgressDialog(context);
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        progressDialog.show();

        Call<DashboardDataResponse> call = RetrofitClient.getInstance().getApi()
                .dashBoardData(token, userId, vendorID);
        call.enqueue(new Callback<DashboardDataResponse>() {
            @Override
            public void onResponse(Call<DashboardDataResponse> call, Response<DashboardDataResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<DashboardDataResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                progressDialog.dismiss();
            }
        });
        return liveData;

    }


    public MutableLiveData<TopTenSupplierAndCustomerResponse> getTopTenCustomerList(FragmentActivity context, String type, String start, String end, String limit) {
        MutableLiveData<TopTenSupplierAndCustomerResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<TopTenSupplierAndCustomerResponse> call = RetrofitClient.getInstance().getApi()
                .getTopTenCustomerList(token, userId, vendorID, type, start, end, limit);
        call.enqueue(new Callback<TopTenSupplierAndCustomerResponse>() {
            @Override
            public void onResponse(Call<TopTenSupplierAndCustomerResponse> call, Response<TopTenSupplierAndCustomerResponse> response) {
                if (response.isSuccessful()) {

                    if (response == null || response.body().getStatus() == 500) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }

                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());

                    }
                } else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<TopTenSupplierAndCustomerResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
            }
        });
        return liveData;

    }

    public MutableLiveData<DashboardBalanceResponse> getBankBalanceList(FragmentActivity context, String start, String end) {
        MutableLiveData<DashboardBalanceResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<DashboardBalanceResponse> call = RetrofitClient.getInstance().getApi()
                .getBankBalanceList(token, userId, vendorID, start, end);
        call.enqueue(new Callback<DashboardBalanceResponse>() {
            @Override
            public void onResponse(Call<DashboardBalanceResponse> call, Response<DashboardBalanceResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null || response.body().getStatus() == 500) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());

                    }
                } else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<DashboardBalanceResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());

                liveData.postValue(null);
            }
        });
        return liveData;

    }

    public MutableLiveData<ExpenseResponse> getExpenseList(FragmentActivity context, String startDate, String endDate, String enterpriseId) {
        MutableLiveData<ExpenseResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<ExpenseResponse> call = RetrofitClient.getInstance().getApi()
                .getExpenseList(token, userId, vendorID, startDate, endDate, enterpriseId);
        call.enqueue(new Callback<ExpenseResponse>() {
            @Override
            public void onResponse(Call<ExpenseResponse> call, Response<ExpenseResponse> response) {
                if (response.isSuccessful()) {

                    if (response.body().getStatus() == 500 || response == null) {
                        liveData.postValue(response.body());
                        return;
                    }
                    if (response.body().getStatus() == 400 || response.body().getStatus() == 200) {
                        liveData.postValue(response.body());
                        return;
                    }

                } else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<ExpenseResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
            }
        });
        return liveData;

    }

    public MutableLiveData<TodaysSaleOrPurchaseResponse> getSaleOrPurchaseList(FragmentActivity context, String type, String start, String end, String enterpriseId) {
        MutableLiveData<TodaysSaleOrPurchaseResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<TodaysSaleOrPurchaseResponse> call = RetrofitClient.getInstance().getApi()
                .getSaleOrPurchaseList(token, userId, vendorID, type, start, end, enterpriseId);
        call.enqueue(new Callback<TodaysSaleOrPurchaseResponse>() {
            @Override
            public void onResponse(Call<TodaysSaleOrPurchaseResponse> call, Response<TodaysSaleOrPurchaseResponse> response) {
                if (response.isSuccessful()) {

                    if (response == null || response.body().getStatus() == 500) {
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
                } else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<TodaysSaleOrPurchaseResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
            }
        });
        return liveData;

    }

    public MutableLiveData<ProductionResponse> getProductionList(FragmentActivity context, String startDate, String endDate, String enterpriseID) {
        MutableLiveData<ProductionResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<ProductionResponse> call = RetrofitClient.getInstance().getApi()
                .getProductionList(token, userId, vendorID, startDate, endDate, enterpriseID);
        call.enqueue(new Callback<ProductionResponse>() {
            @Override
            public void onResponse(Call<ProductionResponse> call, Response<ProductionResponse> response) {
                if (response.isSuccessful()) {

                    if (response.body().getStatus() == 500 || response == null) {
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
                } else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<ProductionResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
            }
        });
        return liveData;

    }

    public MutableLiveData<SalePurchaseJustResponse> getJustSalePurchaseList(FragmentActivity context, String type, String startDate, String endDate, String enterprise) {
        MutableLiveData<SalePurchaseJustResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<SalePurchaseJustResponse> call = RetrofitClient.getInstance().getApi()
                .getJustSalePurchaseList(token, userId, vendorID, type, startDate, endDate, enterprise);
        call.enqueue(new Callback<SalePurchaseJustResponse>() {
            @Override
            public void onResponse(Call<SalePurchaseJustResponse> call, Response<SalePurchaseJustResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null || response.body().getStatus() == 500) {
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
                } else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<SalePurchaseJustResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
            }
        });
        return liveData;

    }

    public MutableLiveData<ProductionResponseJust> getProductionListJust(FragmentActivity context, String type, String start, String end, String enterpriseID) {
        MutableLiveData<ProductionResponseJust> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        Call<ProductionResponseJust> call = RetrofitClient.getInstance().getApi().getProductionListJust(token, userId, vendorID, type, start, end, enterpriseID);
        call.enqueue(new Callback<ProductionResponseJust>() {
            @Override
            public void onResponse(Call<ProductionResponseJust> call, Response<ProductionResponseJust> response) {
                if (response.isSuccessful()) {

                    if (response == null || response.body().getStatus() == 500) {
                        liveData.postValue(null);
                        return;

                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());

                        return;

                    }
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());

                    }
                } else
                    liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<ProductionResponseJust> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
            }
        });
        return liveData;

    }

    public MutableLiveData<QcQaResponseTodays> getQcQaList(FragmentActivity context, String startDate, String endDate, String enterpriseID) {
        MutableLiveData<QcQaResponseTodays> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<QcQaResponseTodays> call = RetrofitClient.getInstance().getApi()
                .getQcQaList(token, userId, vendorID, startDate, endDate, enterpriseID);
        call.enqueue(new Callback<QcQaResponseTodays>() {
            @Override
            public void onResponse(Call<QcQaResponseTodays> call, Response<QcQaResponseTodays> response) {
                if (response.isSuccessful()) {

                    if (response.body().getStatus() == 500 || response == null) {
                        liveData.postValue(null);

                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());

                    }
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());

                    }
                } else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<QcQaResponseTodays> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
            }
        });
        return liveData;

    }


    public MutableLiveData<PieChartDataresponse> pieChartData(FragmentActivity context) {
        MutableLiveData<PieChartDataresponse> liveData = new MutableLiveData<>();

        ProgressDialog progressDialog = new ProgressDialog(context);
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        progressDialog.show();

        Call<PieChartDataresponse> call = RetrofitClient.getInstance().getApi()
                .pieChartData(token, userId, vendorID);
        call.enqueue(new Callback<PieChartDataresponse>() {
            @Override
            public void onResponse(Call<PieChartDataresponse> call, Response<PieChartDataresponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<PieChartDataresponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                Toasty.error(context, "" + t.getMessage(), Toasty.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        return liveData;

    }


}
