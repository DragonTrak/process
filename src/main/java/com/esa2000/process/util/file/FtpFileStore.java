package com.esa2000.process.util.file;

import com.esa2000.process.util.file.utils.FileUtils;
import com.esa2000.process.util.toolUtil.PropConfig;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2015/5/26.
 */
public class FtpFileStore implements FileStore{
    private String ip = PropConfig.getString("ftp.ip");
    private int port = Integer.parseInt(PropConfig.getString("ftp.port"));
    private String userName = PropConfig.getString("ftp.userName");
    private String password = PropConfig.getString("ftp.password");

    @Override
    public boolean storeFile(String path, InputStream inputStream) {
        FtpUtil ftpUtil = new FtpUtil(ip, port, userName, password);
        boolean success = ftpUtil.upload(inputStream, path);
        ftpUtil.disConFtp();
        return success;
    }

    @Override
    public boolean isFileExist(String path) {
        FtpUtil ftpUtil = new FtpUtil(ip, port, userName, password);
        boolean success = false;
        try {
            success = ftpUtil.isExistFile(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ftpUtil.disConFtp();
        return success;
    }

    @Override
    public boolean deleteFile(String path) {
        FtpUtil ftpUtil = new FtpUtil(ip, port, userName, password);
        boolean success = false;
        try {
            success = ftpUtil.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public boolean downloadFile(String path, String local) {
        FtpUtil ftpUtil = new FtpUtil(ip, port, userName, password);
        try {
            return ftpUtil.download(path, local);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ftpUtil.disConFtp();
        return false;
    }
    
    @Override
    public InputStream downloadFile(String path) {
        FtpUtil ftpUtil = new FtpUtil(ip, port, userName, password);
        try {
            return ftpUtil.download(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ftpUtil.disConFtp();
        return null;
    }

    @Override
    public boolean chunkedUpload(String path, InputStream inputStream) {
        return false;
    }
}
