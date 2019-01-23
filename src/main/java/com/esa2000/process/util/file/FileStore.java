package com.esa2000.process.util.file;

import java.io.InputStream;

/**
 * Created by Administrator on 2015/5/26.
 */
public interface FileStore {


    /**
     * 保存文件到
     * @param path 文件路径，格式比如：Pdfs/w3vwpcFQXx.pdf
     * @param inputStream 文件流
     * @return
     */
    public boolean storeFile(String path, InputStream inputStream);

    /**
     * 判断文件是否村咋
     * @param path 文件路径
     * @return
     */
    public boolean isFileExist(String path);

    /**
     * 删除文件
     * @param path 文件路径
     * @return
     */
    public boolean deleteFile(String path);


    public boolean downloadFile(String path, String local);

	public InputStream downloadFile(String path);


	boolean chunkedUpload(String path, InputStream inputStream);
}
