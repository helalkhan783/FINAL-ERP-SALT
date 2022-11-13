package com.ismos_salt_erp.view.fragment.production.iodization.edit;

import com.ismos_salt_erp.serverResponseModel.WashingCrushingModel;

public interface IodizationEditItemClickHandle {
    void removeBtn(int position);
    void insertQuantity(int position, String quantity, WashingCrushingModel currentItem);
    void minusQuantity(int position, String quantity, WashingCrushingModel currentItem);
}
