package com.ismos_salt_erp.view.fragment.items.brand_edit;

public interface BrandEdit {
    void getData(int position,String brandSlId,String brandName,String image);
    void delete(int position,String brandSlId);
}
