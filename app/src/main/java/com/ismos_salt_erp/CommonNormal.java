package com.ismos_salt_erp;

public class CommonNormal implements Common {
 public static    String key, customerId;



    @Override
    public void customerKey(String key, String customerId) {
        this.key = key;
        this.customerId = customerId;
    }
}
