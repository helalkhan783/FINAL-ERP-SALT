package com.ismos_salt_erp.view.fragment.reconciliation.edit;

import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;

public interface EditReconciliationItemClick {
    void removeBtn(int position);

    void insertQuantity(int position, String quantity, SalesRequisitionItems currentItem,String price,String discount,String totalPrice);

    void minusQuantity(int position, String quantity, SalesRequisitionItems currentItem);
}
