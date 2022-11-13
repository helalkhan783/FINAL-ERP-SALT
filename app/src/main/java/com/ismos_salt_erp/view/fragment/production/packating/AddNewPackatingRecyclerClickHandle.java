package com.ismos_salt_erp.view.fragment.production.packating;

import com.ismos_salt_erp.serverResponseModel.PackatingModel;
import com.ismos_salt_erp.serverResponseModel.PacketingList;

public interface AddNewPackatingRecyclerClickHandle {
    void selectItemName(int holderPosition, PacketingList selectedItemMainId, PackatingModel model);
    void changeQuantity(String currentQuantity,int holderPosition, PackatingModel model);
}
