package com.esa2000.process.util.file;


import com.esa2000.process.util.toolUtil.PropConfig;

import java.io.IOException;
import java.io.InputStream;

public class ScpFileStore implements FileStore {

	private String ip = PropConfig.getString("scp.ip");
    private int port = Integer.parseInt(PropConfig.getString("scp.port"));
    private String userName = PropConfig.getString("scp.userName");
    private String password = PropConfig.getString("scp.password");
    
    ScpUtil scp = ScpUtil.getInstance(ip, port,userName,password);

	@Override
	public boolean storeFile(String path, InputStream inputStream) {
		boolean success = scp.upload(inputStream, path);
		// TODO Auto-generated method stub
		return success;
	}

	@Override
	public boolean isFileExist(String path) {
		try {
			boolean success = scp.isExistFile(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteFile(String path) {
		try {
			return scp.delete(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean downloadFile(String path, String local) {

		return scp.download(path, local);
	}

	@Override
	public InputStream downloadFile(String path) {

		try {
			return scp.download(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean chunkedUpload(String path, InputStream inputStream) {
		return false;
	}

}
