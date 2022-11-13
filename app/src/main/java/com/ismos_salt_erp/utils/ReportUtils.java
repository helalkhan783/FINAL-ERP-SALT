package com.ismos_salt_erp.utils;

import com.ismos_salt_erp.R;

import java.util.ArrayList;
import java.util.List;

public class ReportUtils {
    public static final String purchaseReport = "Purchase Report";
    public static final String purChaseRequisition = "Purchase Requisition Report";
    public static final String purchaseReturnReport = "Purchase Return Report";
    public static final String productionReport = "Production Report";
    public static final String packagingReport = "Cartoning Report";
    public static final String iodineUsedReport = "Iodine Used Report";
    public static final String saleReport = "Sale Report";
    public static final String saleRequisition = "Sale Requisition Report";
    public static final String saleReturnReport = "Sale Return Report";
    public static final String districtSaleReport = "District Wise Sale Report";
    public static final String transferReport = "Stock Transfer Report";//
    public static final String reconciliation = "Reconciliation Report";
    public static final String stockInOutReport = "Stock I/O Report";
    public static final String employeeReport = "Employee Report";
    public static final String accountReports = "Account Report";
    public static final String PacketingReport = "Packaging Report";
    public static final String dashBoardReport = "Dashboard Report";

    public static List<String> reportNameList() {
        List<String> nameList = new ArrayList<>();
        nameList.add(purchaseReport);
        nameList.add(purChaseRequisition);
        nameList.add(purchaseReturnReport);//
        nameList.add(productionReport);//
        nameList.add(PacketingReport);
        nameList.add(packagingReport);//
        nameList.add(saleReport);
        nameList.add(saleRequisition);//
        nameList.add(saleReturnReport);
        nameList.add(districtSaleReport);//
        nameList.add(transferReport);
        nameList.add(stockInOutReport);
        nameList.add(reconciliation);
        nameList.add(employeeReport);
        nameList.add(accountReports);

        nameList.add(iodineUsedReport);
        nameList.add(dashBoardReport);

        return nameList;
    }

    public static List<Integer> reportImageList() {
        List<Integer> imageList = new ArrayList<>();

        imageList.add(R.drawable.purchase_report);
        imageList.add(R.drawable.purchase_req);
        imageList.add(R.drawable.purchase_return_report);
        imageList.add(R.drawable.production_report);
        imageList.add(R.drawable.packeting_report);
        imageList.add(R.drawable.packaging_report);
        imageList.add(R.drawable.sale_report);
        imageList.add(R.drawable.sales_requisitions);
        imageList.add(R.drawable.sale_return_report);
        imageList.add(R.drawable.sale_history);
        imageList.add(R.drawable.transfer_history_in);//
        imageList.add(R.drawable.reconciliation_report);
        imageList.add(R.drawable.iodine_report);
        imageList.add(R.drawable.licence_expire_report);
        imageList.add(R.drawable.account);
        imageList.add(R.drawable.iodine_report);
        imageList.add(R.drawable.stock_input_output_repurt);

        return imageList;
    }

}
