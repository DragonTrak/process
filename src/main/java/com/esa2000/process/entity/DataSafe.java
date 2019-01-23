package com.esa2000.process.entity;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

public class DataSafe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String safeId;

    private String safeType;

    private String sourceType;

    private String dataSource;

    private Date safeTime;

    private String saveUser;

    private String serviceNo;

    private Long safeSize;

    private String dataStatus;

    private String safeStatus;

    private Date safeEndTime;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private String remarks;

    private String isDelete ;

    private String createAccount;

    //同步状态
    private String synState ;

    //签名值
    private String signValue ;

    private String hashAlgorithm;

    private String hashSource;// 算法来源 1 客户 2 安证通

    private String publicKey; //公钥

    //保存当前记录签名验证结果, 对应数据库表无需此字段
    @Transient
    private Boolean verifyResult;





    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(String createAccount) {
        this.createAccount = createAccount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSafeId() {
        return safeId;
    }

    public void setSafeId(String safeId) {
        this.safeId = safeId == null ? null : safeId.trim();
    }

    public String getSafeType() {
        return safeType;
    }

    public void setSafeType(String safeType) {
        this.safeType = safeType == null ? null : safeType.trim();
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType == null ? null : sourceType.trim();
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    public Date getSafeTime() {
        return safeTime;
    }

    public void setSafeTime(Date safeTime) {
        this.safeTime = safeTime;
    }

    public String getSaveUser() {
        return saveUser;
    }

    public void setSaveUser(String saveUser) {
        this.saveUser = saveUser == null ? null : saveUser.trim();
    }

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo == null ? null : serviceNo.trim();
    }

    public Long getSafeSize() {
        return safeSize;
    }

    public void setSafeSize(Long safeSize) {
        this.safeSize = safeSize == null ? null : safeSize;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus == null ? null : dataStatus.trim();
    }

    public String getSafeStatus() {
        return safeStatus;
    }

    public void setSafeStatus(String safeStatus) {
        this.safeStatus = safeStatus == null ? null : safeStatus.trim();
    }

    public Date getSafeEndTime() {
        return safeEndTime;
    }

    public void setSafeEndTime(Date safeEndTime) {
        this.safeEndTime = safeEndTime;
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

    public String getSynState() {
        return synState;
    }

    public void setSynState(String synState) {
        this.synState = synState;
    }

    public String getHashAlgorithm() {
        return hashAlgorithm;
    }

    public void setHashAlgorithm(String hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    public String getHashSource() {
        return hashSource;
    }

    public void setHashSource(String hashSource) {
        this.hashSource = hashSource;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String toString() {
        return "DataSafe{" +
                "id=" + id +
                ", safeId='" + safeId + '\'' +
                ", safeType='" + safeType + '\'' +
                ", sourceType='" + sourceType + '\'' +
                ", dataSource='" + dataSource + '\'' +
                ", safeTime='" + (safeTime==null?"":safeTime) +'\'' +
                ", saveUser='" + saveUser +'\'' +
                ", serviceNo='" + serviceNo +'\'' +
                ", safeEndTime='" + (safeEndTime==null?"":safeEndTime) +'\'' +
                ", remarks='" + remarks +'\'' +
                ", createBy='" + createBy +'\'' +
                ", createDate='" + (createDate==null?"":createDate) +'\'' +
                ", createAccount='" + createAccount +'\'' +
                '}';
    }
}