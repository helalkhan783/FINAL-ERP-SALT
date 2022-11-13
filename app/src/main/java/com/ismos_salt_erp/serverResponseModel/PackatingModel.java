package com.ismos_salt_erp.serverResponseModel;

import lombok.Data;

@Data
public class PackatingModel {
    private int id;
    private String itemId;
    private String weight;
    private String quantity;
    private String available;
    private String total;
}
