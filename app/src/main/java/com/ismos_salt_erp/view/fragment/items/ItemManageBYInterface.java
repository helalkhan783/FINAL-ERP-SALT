package com.ismos_salt_erp.view.fragment.items;

public interface ItemManageBYInterface {
    void delete(String vendorId,String storeId,String productId);
    void addTag(Integer position,String categoryId,String productId,String packetId,String productTitl);
    void deleteTag(Integer position,String productId,String packetId);
}
