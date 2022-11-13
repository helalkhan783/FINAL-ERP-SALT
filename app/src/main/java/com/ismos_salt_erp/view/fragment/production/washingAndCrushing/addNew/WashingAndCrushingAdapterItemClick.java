package com.ismos_salt_erp.view.fragment.production.washingAndCrushing.addNew;

import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;

public interface WashingAndCrushingAdapterItemClick {
    void removeBtn(int position);

    void insertQuantity(int position, String quantity, SalesRequisitionItems currentItem);

    void minusQuantity(int position, String quantity, SalesRequisitionItems currentItem);
}
