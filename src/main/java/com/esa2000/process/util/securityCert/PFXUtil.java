package com.esa2000.process.util.securityCert;

import com.esa2000.process.util.toolUtil.CommonUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class PFXUtil {
    private String password;
    private KeyStore keyStore;
    private String alias;
    private PrivateKey privateKey;
    private Certificate[] certChain;
    private PublicKey publicKey;
    private Certificate certificate;

    public PFXUtil(String pfxPath, String password) throws KeyStoreException, NoSuchProviderException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException {
        this(CommonUtil.readBytesFromFile(pfxPath), password);
    }

    public PFXUtil(byte[] pfxBytes, String password) throws KeyStoreException, NoSuchProviderException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException {
        this(new ByteArrayInputStream(pfxBytes), password);
    }

    public PFXUtil(InputStream is, String password) throws KeyStoreException, NoSuchProviderException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException {
        this.password = password;

        keyStore = keyStoreInstance(true);
        keyStore.load(is, password.toCharArray());

        alias = keyStore.aliases().nextElement();
        privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
        certChain = keyStore.getCertificateChain(alias);
        certificate = keyStore.getCertificate(alias);
        publicKey = keyStore.getCertificate(alias).getPublicKey();
    }


    public Certificate getCertificate() {
        Certificate certificate = null;
        try {
            String alias = keyStore.aliases().nextElement();
            certificate = keyStore.getCertificate(alias);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        return certificate;
    }

    public String getCertificateCN() {
        return CommonUtil.getCN(((X509Certificate) getCertificate()).getSubjectDN().toString(), "CN");
    }


    /**
     * 获得Certificate
     *
     * @param certificateByte 证书公钥二进制数据
     * @throws Exception
     */
    public static Certificate getCertificate(byte[] certificateByte) throws Exception {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509", new BouncyCastleProvider());
        InputStream in = new ByteArrayInputStream(certificateByte);
        Certificate certificate = certificateFactory.generateCertificate(in);
        in.close();
        return certificate;
    }

    private KeyStore keyStoreInstance(boolean isReadCertByBouncyCast) throws KeyStoreException, NoSuchProviderException {
        KeyStore keyStore;
        if (isReadCertByBouncyCast) {
            Security.addProvider(new BouncyCastleProvider());
            keyStore = KeyStore.getInstance("PKCS12", "BC");
        } else {
            keyStore = KeyStore.getInstance("PKCS12");
        }
        return keyStore;
    }

    public KeyStore getKeyStore() {
        return keyStore;
    }

    public String getAlias() {
        return alias;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public Certificate[] getCertChain() {
        return certChain;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public void setCertChain(Certificate[] certChain) {
        this.certChain = certChain;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public static void main(String[] args) {
        try {

            //做签名或者验证时，获取字符串字节数组必须支持编码UTF-8,签名后验证会出现验证失败。比如：sign(data.getBytes("UTF-8"));
            PFXUtil pfxUtil = new PFXUtil("d://test4.pfx","1111");
            String shuj = "DataSafeFile{id=d595594a32fb47eaa54f635dba8b9bdc, safeId='20180427114912IXEs', formatType='1', fileType='1', typeSource='1', fileExtensions='file', resourceName='标准A02.pdf', saveName='file', abstarct='be51547f62e8e99a2d26588b497125c27eb293a5', signAbstarct='8b5a482c2666f245843978900540c118273556af12ce3b29a0e19a33d80b1ec2014301b91b566aca33a57b242dccb7c82e414f5ada8b5379b5de33436f8498bf48797c445cfc9c9f51d99646408c2709c1cd4b736fa9880c394807ca41aac32dc2747e9578ff1d785cb3ea6a8610eb197df1961b6faca46e03ba983ac1071181', typeSource='1', safeTime='2018-04-27 11:49:12', safeSize='5791', hasResource='Y', filePath='2018/04/27/K7wzNzLhAN.pdf', remarks='null', dataSafeId='20180427114912IXEs', signData='null', createBy='1', createDate='2018-04-27 11:49:12', createAccount='admin', signData='null'}" ;
            shuj = "{id=c4b9b57d79a84f5c81b48cfb60fc6f2f, safeId='20181011054958yrlO', formatType='1', fileType='1', typeSource='1', fileExtensions='.pdf', resourceName='供货合同框架协议.pdf', saveName='file', abstarct='9c9713cc13c0de0f1a8a11f6b2ed593a8a25950cbc806b7d78e585b5dcd43e31', signAbstarct='3e11880a406aea6d6fb9a4fe945285aebb300d7414bf34c7aac66580be48bff1610960d1e707afc728b5a81b222bb9f4e543c5f12ccb349bee85e69635ae2e665bb6ce4a8fca0abe5e8b0a7460cd9654c0af5e6bc96beb9b4f5cce7bf50fcabfaea62e22d24b86bdb78aa4a4b23253d1a00569bc585fed4ddfcb89b409bdb6ce', typeSource='1', safeTime='1539251398000', safeSize='417046', hasResource='Y', filePath='2018/10/11/hOn8ktnVC2.pdf', remarks='null', dataSafeId='20181011054958yrlO', signData='null', createBy='ff80808164175be901641bfc43bf014f', createDate='1539251398000', createAccount='lih@esa2000.com', signData='null'}";
            String messageOk="DataSafeFile{id=0b218c0f68fa4603a44ac19438b6c91b, safeId='20181011040918aSs5', formatType='1', fileType='1', typeSource='1', fileExtensions='.pdf', resourceName='0.pdf', saveName='file', abstarct='41b81c4ad85f28144ff46cab60343e855843696afe3c6940ed022c1bf1f4a919', signAbstarct='1fa35127330e8debdfb3e7531e4db4434167553418c1d6ec0584315731d71dc649d65e614f06eb2a1378fc262ce33c4fc3a59af8f7c734ad7eb3f98a096bd7b8cb51cbaa83d468fd93c22f9c9aefa7d43a1c989721b4e033d35866883873ec01cbeb51f5e44f288ff9f1c40f1d449b8271dc5390e605af7032b31fcdf51cc1bd', typeSource='1', safeTime='1539245358000', safeSize='6580', hasResource='Y', filePath='2018/10/11/eGTTBBN22V.pdf', remarks='null', dataSafeId='20181011040918aSs5', signData='null', createBy='ff80808164175be901641bfc43bf014f', createDate='1539245358000', createAccount='lih@esa2000.com', signData='null'}";
            String signvalueOk="13755b2de96a8887190d04aa858d3326281fdfd638472aa55874be5028df9eed66314e96df825e67b77a388c7b824d5e788a887c580214d711d8528b8a7ebbbc869472071f3e07b2a17f360a66c8e3b2c0794dab6968252c674181e2c85c8a8edd1643bbced1cb47356a16d9cb0abe369d9107804909034d40070109b6326f26";


            String data ="DataSafeFile{id=c4b9b57d79a84f5c81b48cfb60fc6f2f, safeId='20181011054958yrlO', formatType='1', fileType='1', typeSource='1', fileExtensions='.pdf', resourceName='供货合同框架协议.pdf', saveName='file', abstarct='9c9713cc13c0de0f1a8a11f6b2ed593a8a25950cbc806b7d78e585b5dcd43e31', signAbstarct='3e11880a406aea6d6fb9a4fe945285aebb300d7414bf34c7aac66580be48bff1610960d1e707afc728b5a81b222bb9f4e543c5f12ccb349bee85e69635ae2e665bb6ce4a8fca0abe5e8b0a7460cd9654c0af5e6bc96beb9b4f5cce7bf50fcabfaea62e22d24b86bdb78aa4a4b23253d1a00569bc585fed4ddfcb89b409bdb6ce', typeSource='1', safeTime='1539251398000', safeSize='417046', hasResource='Y', filePath='2018/10/11/hOn8ktnVC2.pdf', remarks='null', dataSafeId='20181011054958yrlO', signData='null', createBy='ff80808164175be901641bfc43bf014f', createDate='1539251398000', createAccount='lih@esa2000.com', signData='null'}";
            byte[] bytes = new PrivateKeySignature(pfxUtil.getCertificate(),pfxUtil.getPrivateKey(),"SHA-256", "BC").sign(data.getBytes("UTF-8"));
            String ss = CommonUtil.bytesToHexString(bytes);
            System.out.println("ss----"+ss);
//            String message ="DataSafeFile{id=c4b9b57d79a84f5c81b48cfb60fc6f2f, safeId='20181011054958yrlO', formatType='1', fileType='1', typeSource='1', fileExtensions='.pdf', resourceName='供货合同框架协议.pdf', saveName='file', abstarct='9c9713cc13c0de0f1a8a11f6b2ed593a8a25950cbc806b7d78e585b5dcd43e31', signAbstarct='3e11880a406aea6d6fb9a4fe945285aebb300d7414bf34c7aac66580be48bff1610960d1e707afc728b5a81b222bb9f4e543c5f12ccb349bee85e69635ae2e665bb6ce4a8fca0abe5e8b0a7460cd9654c0af5e6bc96beb9b4f5cce7bf50fcabfaea62e22d24b86bdb78aa4a4b23253d1a00569bc585fed4ddfcb89b409bdb6ce', typeSource='1', safeTime='1539251398000', safeSize='417046', hasResource='Y', filePath='2018/10/11/hOn8ktnVC2.pdf', remarks='null', dataSafeId='20181011054958yrlO', signData='null', createBy='ff80808164175be901641bfc43bf014f', createDate='1539251398000', createAccount='lih@esa2000.com', signData='null'}";
////	    String signData = "VJY8rEbZuAL4RSQZZtNzsnGXrmOYlqxgEb188efEzbJ62TBRQcKEGpUGtX43s51Xl9VrUWaCp7/qUQlX8enMGI9osbB7m0J9yKH7Ox5KwWNTQ/tTVJqUtD6kRxQQ1gVOR/xsmDEkJJtNwxSjnaGTU5DBCVBCupjiaciYim3ZPJo=";
//            String signvalue = "717bc6b1b0d0e6ab1b00c60693aed045cd2429894368bfc2d1828000b873f80d64381578401a48320edac56c6525f87ac1f6868ead62b8468f58dc4ad27580242cf0c42f26695fc588af122046718b0074162f08f1f9765ce68f1c24d35ed4dc569a71ce9abce74d37cb95ef632d18b869728267fde86be7e087b479844c7f49";
//
            boolean ok = new PrivateKeySignature(pfxUtil.getCertificate(),pfxUtil.getPrivateKey(),"SHA-256", "BC").verfiy(data.getBytes("UTF-8"),CommonUtil.hexStringToBytes(ss));
            System.out.println("---------"+ok);

//            byte[] b = DigestAlgorithms.digest(new FileInputStream("e://1111.jpg"), "SHA-1", "BC");
//            System.out.println(CommonUtil.byte2Hex(b));
//            byte[] aa = CommonUtil.transIsToBytes(new FileInputStream("e://Koala.jpg"));
//            byte[] a = DigestAlgorithms.digest(new ByteArrayInputStream(aa), "SHA-1", "BC");
//            System.out.println(CommonUtil.byte2Hex(a));
//
//            File file = new File("1.txt");
//            byte[] bytes = CommonUtil.transIsToBytes(new FileInputStream(file));
//            System.out.println(bytes.length);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
