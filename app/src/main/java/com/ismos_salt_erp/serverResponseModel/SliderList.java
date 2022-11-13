package com.ismos_salt_erp.serverResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class SliderList {
    @SerializedName("bannerImageID")
    @Expose
    private String bannerImageID;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("alt_text")
    @Expose
    private String altText;
    @SerializedName("url")
    @Expose
    private String url;



    public String getBannerImageID() {
        return bannerImageID;
    }

    public void setBannerImageID(String bannerImageID) {
        this.bannerImageID = bannerImageID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
