package com.ismos_salt_erp.view.fragment.all_report.employee_report.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class EmployeeReportProfuctList {
    @SerializedName("slId")
    @Expose
    private String slId;
    @SerializedName("profileID")
    @Expose
    private String profileID;
    @SerializedName("haveLaboratory")
    @Expose
    private String haveLaboratory;
    @SerializedName("standardProcedure")
    @Expose
    private String standardProcedure;
    @SerializedName("trainedLaboratoryPerson")
    @Expose
    private String trainedLaboratoryPerson;
    @SerializedName("useTestKit")
    @Expose
    private String useTestKit;
    @SerializedName("laboratoryPerson")
    @Expose
    private String laboratoryPerson;
    @SerializedName("fullTimeMale")
    @Expose
    private String fullTimeMale;
    @SerializedName("fullTimeFemale")
    @Expose
    private String fullTimeFemale;
    @SerializedName("partTimeMale")
    @Expose
    private String partTimeMale;
    @SerializedName("partTimeFemail")
    @Expose
    private String partTimeFemail;
    @SerializedName("totalTechMale")
    @Expose
    private String totalTechMale;
    @SerializedName("totalTechFemale")
    @Expose
    private String totalTechFemale;
    @SerializedName("labRemarks")
    @Expose
    private String labRemarks;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("reviewStatus")
    @Expose
    private String reviewStatus;
    @SerializedName("reviewTime")
    @Expose
    private String reviewTime;
    @SerializedName("reviewBy")
    @Expose
    private String reviewBy;
    @SerializedName("ref_slId")
    @Expose
    private String refSlId;
    @SerializedName("FullName")
    @Expose
    private String fullName;

    public String getSlId() {
        return slId;
    }

    public void setSlId(String slId) {
        this.slId = slId;
    }

    public String getProfileID() {
        return profileID;
    }

    public void setProfileID(String profileID) {
        this.profileID = profileID;
    }

    public String getHaveLaboratory() {
        return haveLaboratory;
    }

    public void setHaveLaboratory(String haveLaboratory) {
        this.haveLaboratory = haveLaboratory;
    }

    public String getStandardProcedure() {
        return standardProcedure;
    }

    public void setStandardProcedure(String standardProcedure) {
        this.standardProcedure = standardProcedure;
    }

    public String getTrainedLaboratoryPerson() {
        return trainedLaboratoryPerson;
    }

    public void setTrainedLaboratoryPerson(String trainedLaboratoryPerson) {
        this.trainedLaboratoryPerson = trainedLaboratoryPerson;
    }

    public String getUseTestKit() {
        return useTestKit;
    }

    public void setUseTestKit(String useTestKit) {
        this.useTestKit = useTestKit;
    }

    public String getLaboratoryPerson() {
        return laboratoryPerson;
    }

    public void setLaboratoryPerson(String laboratoryPerson) {
        this.laboratoryPerson = laboratoryPerson;
    }

    public String getFullTimeMale() {
        return fullTimeMale;
    }

    public void setFullTimeMale(String fullTimeMale) {
        this.fullTimeMale = fullTimeMale;
    }

    public String getFullTimeFemale() {
        return fullTimeFemale;
    }

    public void setFullTimeFemale(String fullTimeFemale) {
        this.fullTimeFemale = fullTimeFemale;
    }

    public String getPartTimeMale() {
        return partTimeMale;
    }

    public void setPartTimeMale(String partTimeMale) {
        this.partTimeMale = partTimeMale;
    }

    public String getPartTimeFemail() {
        return partTimeFemail;
    }

    public void setPartTimeFemail(String partTimeFemail) {
        this.partTimeFemail = partTimeFemail;
    }

    public String getTotalTechMale() {
        return totalTechMale;
    }

    public void setTotalTechMale(String totalTechMale) {
        this.totalTechMale = totalTechMale;
    }

    public String getTotalTechFemale() {
        return totalTechFemale;
    }

    public void setTotalTechFemale(String totalTechFemale) {
        this.totalTechFemale = totalTechFemale;
    }

    public String getLabRemarks() {
        return labRemarks;
    }

    public void setLabRemarks(String labRemarks) {
        this.labRemarks = labRemarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getReviewBy() {
        return reviewBy;
    }

    public void setReviewBy(String reviewBy) {
        this.reviewBy = reviewBy;
    }

    public String getRefSlId() {
        return refSlId;
    }

    public void setRefSlId(String refSlId) {
        this.refSlId = refSlId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
