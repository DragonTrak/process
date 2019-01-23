package com.esa2000.process.util.securityCert;

import com.esa2000.process.util.toolUtil.CommonUtil;
import com.esa2000.process.util.toolUtil.PropConfig;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.cert.Certificate;
import java.security.spec.InvalidKeySpecException;


@Component
public class SignCertSource {

    PrivateKey privateKey;

    PublicKey publicKey;

    Certificate[] chain ;

    Certificate certificate ;


    ExternalSignature es;

    {

        try {
            PFXUtil pfxUtil = new PFXUtil(PropConfig.getString("mybq.signCertPath"),PropConfig.getString("mybq.signCertPassword"));
            certificate = pfxUtil.getCertificate() ;

            // 初始化证书链对象
            chain = pfxUtil.getCertChain();
            privateKey = pfxUtil.getPrivateKey();
            publicKey = pfxUtil.getPublicKey();

            // 初始化私钥签名对象，散列算法为SHA-1，签名工具包为BCs
            es = new PrivateKeySignature(certificate,privateKey,PropConfig.getString("mybq.signCertHashAlgorithm").toUpperCase(), "BC");
            Logger.getLogger(getClass()).info("加载证书成功！");
        }catch (Exception e){
            Logger.getLogger(getClass()).info("加载证书报错，请检查平台签名证书是否存在！配置是否正确");
        }
    }

    public String sign(byte[] source) throws GeneralSecurityException {
        return CommonUtil.byte2Hex(this.getEs().sign(source)) ;
    }

    public boolean verify(byte[] source,byte[] signData) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        return this.getEs().verfiy(source,signData);
    }

    public boolean verify(byte[] source,byte[] signData,String publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException,InvalidKeySpecException {
        return this.getEs().verfiy(source,signData,publicKey);
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public Certificate[] getChain() {
        return chain;
    }

    public void setChain(Certificate[] chain) {
        this.chain = chain;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public ExternalSignature getEs() {
        return es;
    }

    public void setEs(ExternalSignature es) {
        this.es = es;
    }

}
