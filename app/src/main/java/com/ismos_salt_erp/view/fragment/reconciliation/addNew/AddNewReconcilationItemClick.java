package com.ismos_salt_erp.view.fragment.reconciliation.addNew;

import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;

public interface AddNewReconcilationItemClick {
    void removeBtn(int position);

    void insertQuantity(int position, String quantity, SalesRequisitionItems currentItem,String price,String discount,String totalPrice);

    void minusQuantity(int position, String quantity, SalesRequisitionItems currentItem);
}
