package com.ismos_salt_erp.page_data;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.PurchaseReportResponse;
import com.ismos_salt_erp.serverResponseModel.ReportPurchaseSupplierList;
import com.ismos_salt_erp.viewModel.report_all_view_model.ReportViewModel;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class AllPageData    {
    FragmentActivity activity;
    LifecycleOwner lifecycleOwner;
    private ReportViewModel reportViewModel;
   /* public static  List<ReportPurchaseSupplierList> supplierResponseLists;
    public static  List<ReportPurchaseSupplierList> customerList;
*/
    PurchaseReportResponse response1;


    public AllPageData(FragmentActivity activity, LifecycleOwner lifecycleOwner ) {
        this.activity = activity;
        this.lifecycleOwner = lifecycleOwner;
        this.reportViewModel = new ViewModelProvider(activity).get(ReportViewModel.class);
    }
    public ArrayList<ReportPurchaseSupplierList> supplierList()    {
         String profileId = PreferenceManager.getInstance(activity).getUserCredentials().getProfileId();

        reportViewModel.getPurchaseReportPageData(activity, profileId).observe(lifecycleOwner, response -> {
            if (response == null || response.getStatus() == 400 || response.getStatus() == 500) {
                Toasty.error(activity, "Something Wrong Contact to Support", Toasty.LENGTH_LONG).show();
                return;
            }
            response1 =response;
           /* supplierResponseLists = new ArrayList<>();
            customerList = new ArrayList<>();
            supplierResponseLists.addAll(response.getSupplierList());
            customerList.addAll(response.getCustomerList());*/

        });

        return (ArrayList<ReportPurchaseSupplierList>) response1.getSupplierList();
    }



}
