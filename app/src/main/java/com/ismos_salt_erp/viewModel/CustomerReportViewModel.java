package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.retrofit.VendorReportResponse;
import com.ismos_salt_erp.serverResponseModel.BankListResposne;
import com.ismos_salt_erp.serverResponseModel.CustomerReportListResponse;
import com.ismos_salt_erp.serverResponseModel.CustomerReportResponse;
import com.ismos_salt_erp.serverResponseModel.UserDataResponse;
import com.ismos_salt_erp.serverResponseModel.account_report.BankLedgerReportResponse;
import com.ismos_salt_erp.serverResponseModel.account_report.DayBookReportResponse;
import com.ismos_salt_erp.serverResponseModel.account_report.DebitorAndCreditorsReportResponse;
import com.ismos_salt_erp.serverResponseModel.account_report.PaymentInstructionReportResponse;
import com.ismos_salt_erp.serverResponseModel.account_report.PaymentReportResponse;
import com.ismos_salt_erp.serverResponseModel.account_report.TransactionReportResponse;
import com.ismos_salt_erp.serverResponseModel.account_report_response.ReceiptReportResponse;
import com.ismos_salt_erp.serverResponseModel.customer_report.CustomerReportResponse1;
import com.ismos_salt_erp.serverResponseModel.customer_report.SupplierResponse1;
import com.ismos_salt_erp.utils.CustomViewModel;
import com.ismos_salt_erp.view.fragment.all_report.customer_and_supplier.ExpenseReportListReponse;
import com.ismos_salt_erp.view.fragment.all_report.customer_and_supplier.ExpenseType;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerReportViewModel extends CustomViewModel {
//getCustomerReportList
    public MutableLiveData<CustomerReportResponse> getCustomerReportPageData(FragmentActivity context) {

        MutableLiveData<CustomerReportResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<CustomerReportResponse> call = RetrofitClient.getInstance().getApi().getCustomerReportPageData(token,vendorId,userId);
        call.enqueue(new Callback<CustomerReportResponse>() {
            @Override
            public void onResponse(Call<CustomerReportResponse> call, Response<CustomerReportResponse> response) {
                if (response.isSuccessful()){
                   try {
                       if (response == null){
                           liveData.postValue(null);
                       }
                       if (response.body().getStatus() == 500){
                           liveData.postValue(null);
                           return;
                       }
                       if (response.body().getStatus() == 400){
                           liveData.postValue(response.body());
                           return;
                       }
                       if (response.body().getStatus() == 200){
                           liveData.postValue(response.body());
                           return;
                       }
                   }
                    catch (Exception e){
                       Log.d("Error",e.getMessage());
                       liveData.postValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerReportResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                liveData.postValue(null);
            }
        });





        return liveData;


    }

   public MutableLiveData<BankListResposne> getBankData(FragmentActivity context) {

        MutableLiveData<BankListResposne> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
         String userId = getUserId(context.getApplication());
         String storeAccess = getStoreAccessId(context.getApplication());
         String vendorId = getVendorId(context.getApplication());

        Call<BankListResposne> call = RetrofitClient.getInstance().getApi().bankData(token,userId,storeAccess,vendorId);
        call.enqueue(new Callback<BankListResposne>() {
            @Override
            public void onResponse(Call<BankListResposne> call, Response<BankListResposne> response) {
                if (response.isSuccessful()){
                   try {
                       if (response == null){
                           liveData.postValue(null);
                       }
                       if (response.body().getStatus() == 500){
                           liveData.postValue(null);
                           return;
                       }
                       if (response.body().getStatus() == 400){
                           liveData.postValue(response.body());
                           return;
                       }
                       if (response.body().getStatus() == 200){
                           liveData.postValue(response.body());
                           return;
                       }
                   }
                    catch (Exception e){
                       Log.d("Error",e.getMessage());
                       liveData.postValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<BankListResposne> call, Throwable t) {
                Log.d("Error",t.getMessage());
                liveData.postValue(null);
            }
        });





        return liveData;


    }
  public MutableLiveData<ExpenseType> expenseTypeList(FragmentActivity context) {

        MutableLiveData<ExpenseType> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
         String vendorId = getVendorId(context.getApplication());

        Call<ExpenseType> call = RetrofitClient.getInstance().getApi().expenseTypeList(token,vendorId);
        call.enqueue(new Callback<ExpenseType>() {
            @Override
            public void onResponse(Call<ExpenseType> call, Response<ExpenseType> response) {
                if (response.isSuccessful()){
                   try {
                       if (response == null){
                           liveData.postValue(null);
                       }
                       if (response.body().getStatus() == 500){
                           liveData.postValue(null);
                           return;
                       }
                       if (response.body().getStatus() == 400){
                           liveData.postValue(response.body());
                           return;
                       }
                       if (response.body().getStatus() == 200){
                           liveData.postValue(response.body());
                           return;
                       }
                   }
                    catch (Exception e){
                       Log.d("Error",e.getMessage());
                       liveData.postValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<ExpenseType> call, Throwable t) {
                Log.d("Error",t.getMessage());
                liveData.postValue(null);
            }
        });





        return liveData;


    }



    public MutableLiveData<UserDataResponse> getUserpageList(FragmentActivity context) {

        MutableLiveData<UserDataResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());
        String storeAccess = getStoreAccessId(context.getApplication());

        Call<UserDataResponse> call = RetrofitClient.getInstance().getApi().getUserpageList(token,vendorId,userId,storeAccess);
        call.enqueue(new Callback<UserDataResponse>() {
            @Override
            public void onResponse(Call<UserDataResponse> call, Response<UserDataResponse> response) {
                if (response.isSuccessful()){
                   try {
                       if (response == null){
                           liveData.postValue(null);
                       }
                       if (response.body().getStatus() == 500){
                           liveData.postValue(null);
                           return;
                       }
                       if (response.body().getStatus() == 400){
                           liveData.postValue(response.body());
                           return;
                       }
                       if (response.body().getStatus() == 200){
                           liveData.postValue(response.body());
                           return;
                       }
                   }
                    catch (Exception e){
                       Log.d("Error",e.getMessage());
                       liveData.postValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDataResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                liveData.postValue(null);
            }
        });





        return liveData;


    }

// for customer report list
    public MutableLiveData<CustomerReportResponse1> getCustomerReportList(FragmentActivity context, String startDate, String endDate, String store, String customer,String date,String type) {

        MutableLiveData<CustomerReportResponse1> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<CustomerReportResponse1> call = RetrofitClient.getInstance().getApi().getCustomerReportList(token,vendorId,userId,startDate,endDate,customer,store,date,type);
        call.enqueue(new Callback<CustomerReportResponse1>() {
            @Override
            public void onResponse(Call<CustomerReportResponse1> call, Response<CustomerReportResponse1> response) {
                if (response.isSuccessful()){
                   try {
                       if (response == null){
                           liveData.postValue(null);
                       }
                       if (response.body().getStatus() == 500){
                           liveData.postValue(null);
                           return;
                       }
                       if (response.body().getStatus() == 400){
                           liveData.postValue(response.body());
                           return;
                       }
                       if (response.body().getStatus() == 200){
                           liveData.postValue(response.body());
                           return;
                       }
                   }
                    catch (Exception e){
                       Log.d("Error",e.getMessage());
                       liveData.postValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerReportResponse1> call, Throwable t) {
                Log.d("Error",t.getMessage());
                liveData.postValue(null);
            }
        });





        return liveData;


    }

    public MutableLiveData<SupplierResponse1> getSupplierReportList(FragmentActivity context, String startDate, String endDate, String store, String customer,String date,String type) {

        MutableLiveData<SupplierResponse1> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<SupplierResponse1> call = RetrofitClient.getInstance().getApi().getSupplierLedger(token,vendorId,userId,startDate,endDate,customer,store,date,type);
        call.enqueue(new Callback<SupplierResponse1>() {
            @Override
            public void onResponse(Call<SupplierResponse1> call, Response<SupplierResponse1> response) {
                if (response.isSuccessful()){
                   try {
                       if (response == null){
                           liveData.postValue(null);
                       }
                       if (response.body().getStatus() == 500){
                           liveData.postValue(null);
                           return;
                       }
                       if (response.body().getStatus() == 400){
                           liveData.postValue(response.body());
                           return;
                       }
                       if (response.body().getStatus() == 200){
                           liveData.postValue(response.body());
                           return;
                       }
                   }
                    catch (Exception e){
                       Log.d("Error",e.getMessage());
                       liveData.postValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<SupplierResponse1> call, Throwable t) {
                Log.d("Error",t.getMessage());
                liveData.postValue(null);
            }
        });





        return liveData;


    }


    public MutableLiveData<CustomerReportListResponse> getSupplierList(FragmentActivity context, String startDate, String endDate, String store, String customer) {

        MutableLiveData<CustomerReportListResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<CustomerReportListResponse> call = RetrofitClient.getInstance().getApi().getSupplierReportList(token,vendorId,userId,startDate,endDate,customer,store);
        call.enqueue(new Callback<CustomerReportListResponse>() {
            @Override
            public void onResponse(Call<CustomerReportListResponse> call, Response<CustomerReportListResponse> response) {
                if (response.isSuccessful()){
                   try {
                       if (response == null){
                           liveData.postValue(null);
                       }
                       if (response.body().getStatus() == 500){
                           liveData.postValue(null);
                           return;
                       }
                       if (response.body().getStatus() == 400){
                           liveData.postValue(response.body());
                           return;
                       }
                       if (response.body().getStatus() == 200){
                           liveData.postValue(response.body());
                           return;
                       }
                   }
                    catch (Exception e){
                       Log.d("Error",e.getMessage());
                       liveData.postValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerReportListResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                liveData.postValue(null);
            }
        });





        return liveData;


    }



  public MutableLiveData<VendorReportResponse> getVendorReportList(FragmentActivity context, String startDate, String endDate, String store, String customer,String date) {

        MutableLiveData<VendorReportResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<VendorReportResponse> call = RetrofitClient.getInstance().getApi().getVendorReportList(token,vendorId,userId,startDate,endDate,customer,store,date);
        call.enqueue(new Callback<VendorReportResponse>() {
            @Override
            public void onResponse(Call<VendorReportResponse> call, Response<VendorReportResponse> response) {
                if (response.isSuccessful()){
                   try {
                       if (response == null){
                           liveData.postValue(null);
                       }
                       if (response.body().getStatus() == 500){
                           liveData.postValue(null);
                           return;
                       }
                       if (response.body().getStatus() == 400){
                           liveData.postValue(response.body());
                           return;
                       }
                       if (response.body().getStatus() == 200){
                           liveData.postValue(response.body());
                           return;
                       }
                   }
                    catch (Exception e){
                       Log.d("Error",e.getMessage());
                       liveData.postValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<VendorReportResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                liveData.postValue(null);
            }
        });





        return liveData;


    }
  public MutableLiveData<ExpenseReportListReponse> getExpenseReportList(FragmentActivity context, String startDate, String endDate, String store, String expensetypeId) {

        MutableLiveData<ExpenseReportListReponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<ExpenseReportListReponse> call = RetrofitClient.getInstance().getApi().getExpenseReportList(token,vendorId,userId,startDate,endDate,store,expensetypeId);
        call.enqueue(new Callback<ExpenseReportListReponse>() {
            @Override
            public void onResponse(Call<ExpenseReportListReponse> call, Response<ExpenseReportListReponse> response) {
                if (response.isSuccessful()){
                   try {
                       if (response == null){
                           liveData.postValue(null);
                       }
                       if (response.body().getStatus() == 500){
                           liveData.postValue(null);
                           return;
                       }
                       if (response.body().getStatus() == 400){
                           liveData.postValue(response.body());
                           return;
                       }
                       if (response.body().getStatus() == 200){
                           liveData.postValue(response.body());
                           return;
                       }
                   }
                    catch (Exception e){
                       Log.d("Error",e.getMessage());
                       liveData.postValue(null);
                    }
                }else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<ExpenseReportListReponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                liveData.postValue(null);
            }
        });





        return liveData;


    }


 public MutableLiveData<ReceiptReportResponse> getReceiptList(FragmentActivity context, String startDate, String endDate,String company,String user,String paymentTYpe) {

        MutableLiveData<ReceiptReportResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());
        String store_access = getStoreAccessId(context.getApplication());

        Call<ReceiptReportResponse> call = RetrofitClient.getInstance().getApi().getReceiptList(token,vendorId,userId,store_access,startDate,endDate,company,user,paymentTYpe);
        call.enqueue(new Callback<ReceiptReportResponse>() {
            @Override
            public void onResponse(Call<ReceiptReportResponse> call, Response<ReceiptReportResponse> response) {
                if (response.isSuccessful()){
                   try {
                       if (response == null){
                           liveData.postValue(null);
                       }
                       if (response.body().getStatus() == 500){
                           liveData.postValue(null);
                           return;
                       }
                       if (response.body().getStatus() == 400){
                           liveData.postValue(response.body());
                           return;
                       }
                       if (response.body().getStatus() == 200){
                           liveData.postValue(response.body());
                           return;
                       }
                   }
                    catch (Exception e){
                       Log.d("Error",e.getMessage());
                       liveData.postValue(null);
                    }
                }
                else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<ReceiptReportResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                liveData.postValue(null);
            }
        });





        return liveData;


    }


    public MutableLiveData<BankLedgerReportResponse> getBankLedgerList(FragmentActivity context, String startDate, String endDate,String withdrawType,String transactionType,String bankId) {
        MutableLiveData<BankLedgerReportResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());
        String store_access = getStoreAccessId(context.getApplication());

        Call<BankLedgerReportResponse> call = RetrofitClient.getInstance().getApi().getBankledgerList(token,vendorId,userId,store_access,startDate,endDate,null,withdrawType,transactionType,bankId,null);
        call.enqueue(new Callback<BankLedgerReportResponse>() {
            @Override
            public void onResponse(Call<BankLedgerReportResponse> call, Response<BankLedgerReportResponse> response) {
                if (response.isSuccessful()){
                   try {
                       if (response == null){
                           liveData.postValue(null);
                       }
                       if (response.body().getStatus() == 500){
                           liveData.postValue(null);
                           return;
                       }
                       if (response.body().getStatus() == 400){
                           liveData.postValue(response.body());
                           return;
                       }
                       if (response.body().getStatus() == 200){
                           liveData.postValue(response.body());
                           return;
                       }
                   }
                    catch (Exception e){
                       Log.d("Error",e.getMessage());
                       liveData.postValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<BankLedgerReportResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                liveData.postValue(null);
            }
        });

        return liveData;


    }

   public MutableLiveData<PaymentInstructionReportResponse> paymentInstructionReportList(FragmentActivity context, String startDate, String endDate, String useId,String paymentType) {

        MutableLiveData<PaymentInstructionReportResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());
        String store_access = getStoreAccessId(context.getApplication());

        Call<PaymentInstructionReportResponse> call = RetrofitClient.getInstance().getApi().paymentInstructionReportList(token,vendorId,userId,store_access,startDate,endDate,null,useId,paymentType);
        call.enqueue(new Callback<PaymentInstructionReportResponse>() {
            @Override
            public void onResponse(Call<PaymentInstructionReportResponse> call, Response<PaymentInstructionReportResponse> response) {
                if (response.isSuccessful()){
                   try {
                       if (response == null ||response.body().getStatus() == 500){
                           liveData.postValue(null);
                           return;
                       }

                       if (response.body().getStatus() == 400){
                           liveData.postValue(response.body());
                           return;
                       }
                       if (response.body().getStatus() == 200){
                           liveData.postValue(response.body());
                           return;
                       }
                   }
                    catch (Exception e){
                       Log.d("Error",e.getMessage());
                       liveData.postValue(null);
                    }
                }else  liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<PaymentInstructionReportResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                liveData.postValue(null);
            }
        });





        return liveData;


    }
  public MutableLiveData<PaymentReportResponse> paymentReport(FragmentActivity context, String startDate, String endDate, String useId, String paymentType,String company) {

        MutableLiveData<PaymentReportResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());
        String store_access = getStoreAccessId(context.getApplication());

        Call<PaymentReportResponse> call = RetrofitClient.getInstance().getApi().paymentReport(token,vendorId,userId,store_access,startDate,endDate,company,useId,paymentType);
        call.enqueue(new Callback<PaymentReportResponse>() {
            @Override
            public void onResponse(Call<PaymentReportResponse> call, Response<PaymentReportResponse> response) {
                if (response.isSuccessful()){
                   try {
                       if (response == null){
                           liveData.postValue(null);
                       }
                       if (response.body().getStatus() == 500){
                           liveData.postValue(null);
                           return;
                       }
                       if (response.body().getStatus() == 400){
                           liveData.postValue(response.body());
                           return;
                       }
                       if (response.body().getStatus() == 200){
                           liveData.postValue(response.body());
                           return;
                       }
                   }
                    catch (Exception e){
                       Log.d("Error",e.getMessage());
                       liveData.postValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentReportResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                liveData.postValue(null);
            }
        });





        return liveData;


    }
public MutableLiveData<TransactionReportResponse> getIntruction(FragmentActivity context, String startDate, String endDate, String useId, String paymentType, String type,String company,String enterprise) {

        MutableLiveData<TransactionReportResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());
        String store_access = getStoreAccessId(context.getApplication());

        Call<TransactionReportResponse> call = RetrofitClient.getInstance().getApi().gteInstructionReport(token,type,vendorId,userId,store_access ,startDate,endDate,company,useId,paymentType,enterprise );
        call.enqueue(new Callback<TransactionReportResponse>() {
            @Override
            public void onResponse(Call<TransactionReportResponse> call, Response<TransactionReportResponse> response) {
                if (response.isSuccessful()){
                   try {
                       if (response == null || response.body().getStatus() == 500){
                           liveData.postValue(null);
                       }

                       if (response.body().getStatus() == 400){
                           liveData.postValue(response.body());
                           return;
                       }
                       if (response.body().getStatus() == 200){
                           liveData.postValue(response.body());
                           return;
                       }
                   }
                    catch (Exception e){
                       Log.d("Error",e.getMessage());
                       liveData.postValue(null);
                    }
                }else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<TransactionReportResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                liveData.postValue(null);
            }
        });





        return liveData;


    }
public MutableLiveData<DebitorAndCreditorsReportResponse> creditReport(FragmentActivity context, String startDate, String endDate, String useId, String paymentType, String type, String storeID) {

        MutableLiveData<DebitorAndCreditorsReportResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());
        String store_access = getStoreAccessId(context.getApplication());

        Call<DebitorAndCreditorsReportResponse> call = RetrofitClient.getInstance().getApi().creditReport(token,type,vendorId,userId,store_access,  storeID);
        call.enqueue(new Callback<DebitorAndCreditorsReportResponse>() {
            @Override
            public void onResponse(Call<DebitorAndCreditorsReportResponse> call, Response<DebitorAndCreditorsReportResponse> response) {
                if (response.isSuccessful()){
                   try {
                       if (response == null){
                           liveData.postValue(null);
                       }
                       if (response.body().getStatus() == 500){
                           liveData.postValue(null);
                           return;
                       }
                       if (response.body().getStatus() == 400){
                           liveData.postValue(response.body());
                           return;
                       }
                       if (response.body().getStatus() == 200){
                           liveData.postValue(response.body());
                           return;
                       }
                   }
                    catch (Exception e){
                       Log.d("Error",e.getMessage());
                       liveData.postValue(null);
                    }
                }else  liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<DebitorAndCreditorsReportResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                liveData.postValue(null);
            }
        });





        return liveData;


    }

public MutableLiveData<DayBookReportResponse> getDayBook(FragmentActivity context, String startDate, String endDate, String useId, String paymentType, String storeID,String type,String SupplierID) {

        MutableLiveData<DayBookReportResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());
        String store_access = getStoreAccessId(context.getApplication());

        Call<DayBookReportResponse> call = RetrofitClient.getInstance().getApi().getDaybookReport(token,paymentType,vendorId,userId,store_access,startDate,endDate, storeID,type);
        call.enqueue(new Callback<DayBookReportResponse>() {
            @Override
            public void onResponse(Call<DayBookReportResponse> call, Response<DayBookReportResponse> response) {
                if (response.isSuccessful()){
                   try {
                       if (response == null){
                           liveData.postValue(null);
                       }
                       if (response.body().getStatus() == 500){
                           liveData.postValue(null);
                           return;
                       }
                       if (response.body().getStatus() == 400){
                           liveData.postValue(response.body());
                           return;
                       }
                       if (response.body().getStatus() == 200){
                           liveData.postValue(response.body());
                           return;
                       }
                   }
                    catch (Exception e){
                       Log.d("Error",e.getMessage());
                       liveData.postValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<DayBookReportResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                liveData.postValue(null);
            }
        });





        return liveData;


    }



}
