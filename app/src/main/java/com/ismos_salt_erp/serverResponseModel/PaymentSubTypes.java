package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PaymentSubTypes {
    @SerializedName("1")
    @Expose
    public String _1;
    @SerializedName("2")
    @Expose
    public String _2;
    @SerializedName("3")
    @Expose
    public String _3;
    @SerializedName("5")
    @Expose
    public String _5;

    public String get_1() {
        return _1;
    }

    public void set_1(String _1) {
        this._1 = _1;
    }

    public String get_2() {
        return _2;
    }

    public void set_2(String _2) {
        this._2 = _2;
    }

    public String get_3() {
        return _3;
    }

    public void set_3(String _3) {
        this._3 = _3;
    }

    public String get_5() {
        return _5;
    }

    public void set_5(String _5) {
        this._5 = _5;
    }
}
