package com.ismos_salt_erp.view.fragment.sale.newSale;

import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItemsResponse;

public interface ItemClickOne {


        void insertQuantity(int position, String quantity, SalesRequisitionItemsResponse currentItem, String price, String discount, String totalPric, String productId);
        void update(int position, String quantity, SalesRequisitionItemsResponse currentItem, String price, String discount, String totalPric, String productId);

        void updateConfirm(int position, String quantity, SalesRequisitionItems currentItem, String price, String discount, String totalPric, String productId);

        void removeBtn(int position);
}
