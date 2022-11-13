package com.ismos_salt_erp.localDatabase;

import lombok.Data;

@Data
public class PackagingDatabaseModel {
    private int id;
    private String itemId;
    private String packedId;
    private String weight;
    private String quantity;
    private String note;
    private String pktTag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getPackedId() {
        return packedId;
    }

    public void setPackedId(String packedId) {
        this.packedId = packedId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPktTag() {
        return pktTag;
    }

    public void setPktTag(String pktTag) {
        this.pktTag = pktTag;
    }
}
