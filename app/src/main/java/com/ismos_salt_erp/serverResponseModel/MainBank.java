package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class MainBank {
    @SerializedName("mainBankID")
    @Expose
    private String mainBankID;
    @SerializedName("mainBankName")
    @Expose
    private String mainBankName;

    public String getMainBankID() {
        return mainBankID;
    }

    public void setMainBankID(String mainBankID) {
        this.mainBankID = mainBankID;
    }

    public String getMainBankName() {
        return mainBankName;
    }

    public void setMainBankName(String mainBankName) {
        this.mainBankName = mainBankName;
    }
}
