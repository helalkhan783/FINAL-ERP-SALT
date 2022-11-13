package com.ismos_salt_erp.view.fragment.sale.newSale;

import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;

public interface ItemClick {
    void removeBtn(int position);
    void insertQuantity(int position, String quantity, SalesRequisitionItems currentItem,String price,String discount,String totalPrice);
    void minusQuantity(int position, String quantity, SalesRequisitionItems currentItem);
}
