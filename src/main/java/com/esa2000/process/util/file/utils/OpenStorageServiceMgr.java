package com.esa2000.process.util.file.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.internal.Mimetypes;
import com.aliyun.oss.model.*;
import com.esa2000.process.util.toolUtil.PropConfig;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * OpenStorageService
 *@author
 *@date
 *@version
 */
public class OpenStorageServiceMgr {

	private static String ACCESS_ID = PropConfig.getString("oss.id");
	private static String ACCESS_KEY = PropConfig.getString("oss.secret");
	private static String OSS_ENDPOINT = PropConfig.getString("oss.endpoint");
	private static String BUCKET_NAME = PropConfig.getString("oss.bucket");
	private Logger logger = Logger.getLogger(this.getClass());
	private static OSSClient client = null;
	
	private static OpenStorageServiceMgr instance = null;

	public static OpenStorageServiceMgr getInstance() {
		if (instance == null) {
			instance = new OpenStorageServiceMgr();
		}
		return instance;
	}

	private OpenStorageServiceMgr() {

		client = new OSSClient(OSS_ENDPOINT, ACCESS_ID, ACCESS_KEY);

	}



	/**
	 * 上传文件
	 * 
	 * @param filePath
	 * @param inputStream
	 * @return
	 */
	public boolean uploadFile(String filePath, InputStream inputStream) {
		String fileTempPath = FileUtils.creteTempPath();
		File file = new File(fileTempPath);
		inputStreamToFile(inputStream, file);

		boolean success = OpenStorageServiceMgr.getInstance()
				.uploadFile(filePath, file);
		return success;
	}

	/**
	 * 上传文件
	 * 
	 * @param filePath
	 * @param file
	 * @return
	 */
	public boolean uploadFile(String filePath, File file) {
		ObjectMetadata objectMeta = new ObjectMetadata();
		objectMeta.setContentLength(file.length());
		// 判断上传类型，多的可根据自己需求来判定
		setContentType(filePath, objectMeta);

		InputStream input;
		try {
			input = new FileInputStream(file);
			PutObjectResult result = client.putObject(BUCKET_NAME, filePath,
					input, objectMeta);
			String eTag = result.getETag();
			System.out.println(eTag);
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			FileUtils.deleteFile(file.getPath());

		}

	}

	/**
	 * 设置类型
	 * 
	 * @param filename
	 * @param objectMeta
	 */
	private void setContentType(String filename, ObjectMetadata objectMeta) {

//		if (filename.equalsIgnoreCase("pdf")) {
//			objectMeta.setContentType("application/pdf");
//		}
//		if (filename.equalsIgnoreCase("docx")
//				|| filename.equalsIgnoreCase("doc")) {
//			objectMeta.setContentType("application/msword");
//		}
//
		String mimeType = Mimetypes.getInstance().getMimetype(filename);
		objectMeta.setContentType(mimeType);

	}

	/**
	 * 下载文件
	 * 
	 * @param Objectkey
	 * @param filename
	 * @throws OSSException
	 * @throws ClientException
	 */
	public boolean downloadFile(String Objectkey, String filename) {

		// 下载Object到文件filename
		try {
			client.getObject(new GetObjectRequest(BUCKET_NAME, Objectkey),
					new File(filename));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 *  select all objects
	 * @param
	 */
	public void listObjects() {

		// 获取指定bucket下的所有Object信息
		ObjectListing listing = client.listObjects(BUCKET_NAME);

		// 遍历所有Object
		for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
			String key = objectSummary.getKey();
			System.out.println(key);
		}

	}
	/**
	 * 单个文件删除
	 * @param key
	 */
	public boolean deleteFile(String key){
		try {
			client.deleteObject(BUCKET_NAME,key);

		} catch (OSSException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 大文件分片上传
	 * @param key       OSS存在路径，唯一
	 * @param inputStream 文件流
	 * @return
	 */
	public boolean chunkedFile(String key,InputStream inputStream){
		try {
			ObjectMetadata objectMeta = new ObjectMetadata();
			objectMeta.setContentLength(inputStream.available());
			// 判断上传类型，多的可根据自己需求来判定
			setContentType(key, objectMeta);
			InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(BUCKET_NAME, key);
			InitiateMultipartUploadResult result = client.initiateMultipartUpload(request);

			String uploadId = result.getUploadId();
			List<PartETag> partETags = new ArrayList<PartETag>();
//			InputStream instream = null;
//			instream = new ByteArrayInputStream(inputStream);
			UploadPartRequest uploadPartRequest = new UploadPartRequest();
			uploadPartRequest.setBucketName(BUCKET_NAME);
			uploadPartRequest.setKey(key);
			uploadPartRequest.setUploadId(uploadId);
			uploadPartRequest.setInputStream(inputStream);
			// 设置分片大小，除最后一个分片外，其它分片要大于100KB
			uploadPartRequest.setPartSize(100 * 1024);
			// 设置分片号，范围是1~10000，
			uploadPartRequest.setPartNumber(1);
			UploadPartResult uploadPartResult;
			uploadPartResult = client.uploadPart(uploadPartRequest);
			partETags.add(uploadPartResult.getPartETag());

			// 将文件分块按照升序排序
			Collections.sort(partETags, new Comparator<PartETag>() {
				@Override
				public int compare(PartETag p1, PartETag p2) {
					return p1.getPartNumber() - p2.getPartNumber();
				}
			});

			CompleteMultipartUploadRequest completeMultipartUploadRequest =
					new CompleteMultipartUploadRequest(BUCKET_NAME, key, uploadId, partETags);
			client.completeMultipartUpload(completeMultipartUploadRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 查询文件是否存在
	 *
	 * @param key
	 * @return
	 */
	public boolean doesFileExist(String key){
		boolean flag = false;
		try {
			flag = client.doesObjectExist(BUCKET_NAME,key);

		} catch (OSSException e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 多个文件删除
	 * @param keys
	 */
	public void deleteFiles(List<String> keys){
		try {
			client.deleteObject(new DeleteObjectsRequest(BUCKET_NAME).withKeys(keys));
		} catch (OSSException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		OpenStorageServiceMgr.getInstance().listObjects();
	}

	/**
	 * 流转成文件FILE
	 * 
	 * @param ins
	 * @param file
	 */
	private void inputStreamToFile(InputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}