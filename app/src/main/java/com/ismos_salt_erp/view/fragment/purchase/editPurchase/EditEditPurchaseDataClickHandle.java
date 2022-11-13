package com.ismos_salt_erp.view.fragment.purchase.editPurchase;

import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;

public interface EditEditPurchaseDataClickHandle {

    void removeBtn(int position);

    void insertQuantity(int position, String quantity, SalesRequisitionItems currentItem, String price, String discount, String totalPrice);

    void minusQuantity(int position, String quantity, SalesRequisitionItems currentItem, String price, String discount, String totalPrice);

}
