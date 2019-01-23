package com.esa2000.process.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataSafeFile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String safeId;

    private String formatType;

    private String fileType;

    private String typeSource;

    private String fileExtensions;

    private String resourceName;

    private String saveName;

    private String abstarct;

    private String signAbstarct;

    private Integer downCount;

    private Date safeTime;

    private Long safeSize;

    private String hasResource;

    private String filePath;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private String remarks;

    private String dataSafeId ;

    private DataSafe dataSafe ;

    private String createAccount;

    private String signData ;

    private String synState ;

    //签名值
    private String signValue ;

    //保存当前记录签名验证结果, 对应数据库表无需此字段
    @Transient
    private Boolean verifyResult;

    @Transient
    private String userName ;

    @Transient
    private String idNumber;

    @Transient
    private String telPhone ;

    @Transient
    private List<DataSafeUser> dataSafeUserList = new ArrayList<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDataSafeId() {
        return dataSafeId;
    }

    public void setDataSafeId(String dataSafeId) {
        this.dataSafeId = dataSafeId == null ? null : dataSafeId.trim();
    }

    public DataSafe getDataSafe() {
        return dataSafe;
    }

    public void setDataSafe(DataSafe dataSafe) {
        this.dataSafe = dataSafe;
    }

    public String getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(String createAccount) {
        this.createAccount = createAccount;
    }

    public String getSafeId() {
        return safeId;
    }

    public void setSafeId(String safeId) {
        this.safeId = safeId == null ? null : safeId.trim();
    }

    public String getFormatType() {
        return formatType;
    }

    public void setFormatType(String formatType) {
        this.formatType = formatType == null ? null : formatType.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public String getTypeSource() {
        return typeSource;
    }

    public void setTypeSource(String typeSource) {
        this.typeSource = typeSource == null ? null : typeSource.trim();
    }

    public String getFileExtensions() {
        return fileExtensions;
    }

    public void setFileExtensions(String fileExtensions) {
        this.fileExtensions = fileExtensions == null ? null : fileExtensions.trim();
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.trim();
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName == null ? null : saveName.trim();
    }

    public String getAbstarct() {
        return abstarct;
    }

    public void setAbstarct(String abstarct) {
        this.abstarct = abstarct == null ? null : abstarct.trim();
    }

    public String getSignAbstarct() {
        return signAbstarct;
    }

    public void setSignAbstarct(String signAbstarct) {
        this.signAbstarct = signAbstarct == null ? null : signAbstarct.trim();
    }

    public Integer getDownCount() {
        return downCount;
    }

    public void setDownCount(Integer downCount) {
        this.downCount = downCount;
    }

    public Date getSafeTime() {
        return safeTime;
    }

    public void setSafeTime(Date safeTime) {
        this.safeTime = safeTime;
    }

    public Long getSafeSize() {
        return safeSize;
    }

    public void setSafeSize(Long safeSize) {
        this.safeSize = safeSize == null ? null : safeSize;
    }

    public String getHasResource() {
        return hasResource;
    }

    public void setHasResource(String hasResource) {
        this.hasResource = hasResource == null ? null : hasResource.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getSignValue() {
        return signValue;
    }

    public void setSignValue(String signValue) {
        this.signValue = signValue;
    }

    public Boolean getVerifyResult() {
        return verifyResult;
    }

    public void setVerifyResult(Boolean verifyResult) {
        this.verifyResult = verifyResult;
    }

    public String getSignData() {
        return signData;
    }

    public void setSignData(String signData) {
        this.signData = signData;
    }

    public String getSynState() {
        return synState;
    }

    public void setSynState(String synState) {
        this.synState = synState;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }


    public List<DataSafeUser> getDataSafeUserList() {
        return dataSafeUserList;
    }

    public void setDataSafeUserList(List<DataSafeUser> dataSafeUserList) {
        this.dataSafeUserList = dataSafeUserList;
    }

    @Override
    public String toString() {
        return "DataSafeFile{" +
                "id=" + id +
                ", safeId='" + safeId + '\'' +
                ", formatType='" + formatType + '\'' +
                ", fileType='" + fileType + '\'' +
                ", typeSource='" + typeSource + '\'' +
                ", fileExtensions='" + fileExtensions + '\'' +
                ", resourceName='" + resourceName + '\'' +
                ", saveName='" + saveName + '\'' +
                ", abstarct='" + abstarct + '\'' +
                ", signAbstarct='" + signAbstarct + '\'' +
                ", typeSource='" + typeSource + '\'' +
                ", safeTime='" + (safeTime==null?"":safeTime.getTime()) +'\'' +
                ", safeSize='" + safeSize +'\'' +
                ", hasResource='" + hasResource +'\'' +
                ", filePath='" + filePath +'\'' +
                ", remarks='" + remarks +'\'' +
                ", dataSafeId='" + safeId +'\'' +
                ", signData='" +signData +'\'' +
                ", createBy='" + createBy +'\'' +
                ", createDate='" + (createDate==null?"":createDate.getTime()) +'\'' +
                ", createAccount='" + createAccount +'\'' +
                ", signData='" + signData +'\'' +
                '}';
    }
}