package com.esa2000.process.util.file;


import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import com.esa2000.process.util.file.utils.FileUtils;
import com.esa2000.process.util.toolUtil.PropConfig;

import java.io.*;

public class ScpUtil {

    // public Scpclient(){}
    static private ScpUtil instance;
    private String rootdir = PropConfig.getString("scp.rootdir");//scp根目录
    

    static synchronized public ScpUtil getInstance(String IP, int port,
                                                   String username, String passward) {
        if (instance == null) {
            instance = new ScpUtil(IP, port, username, passward);
        }
        return instance;
    }

    public ScpUtil(String IP, int port, String username, String passward) {
        this.ip = IP;
        this.port = port;
        this.username = username;
        this.password = passward;
    }

    /**
	 * @param remoteFile 远程文件地址
	 * @param localTargetDirectory Directory本地文件地址
	 * @return true：下载成功！false：下载失败！
	 * @throws Exception
	 */
    public boolean download(String remoteFile, String localTargetDirectory) {
    	remoteFile ="/"+rootdir+ "/" + remoteFile;
    	String fileName = remoteFile.substring(remoteFile.lastIndexOf("/") + 1);
    	String localTargetFileName = localTargetDirectory.substring(localTargetDirectory.lastIndexOf("\\") + 1);
    	String localTargetDir= localTargetDirectory.substring(0, localTargetDirectory.lastIndexOf("\\"));
    	
        Connection conn = new Connection(ip,port);
        boolean b=false;
        try {
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username,
                    password);
            if (isAuthenticated == false) {
                System.err.println("authentication failed");
            }
            SCPClient client = new SCPClient(conn);
            client.get(remoteFile, localTargetDir);
            
            renameFile(localTargetDir+"\\"+fileName,localTargetDir+"\\"+localTargetFileName);//重命名
            b=true;
        } catch (IOException ex) {
        	b=false;
        	ex.printStackTrace();
            //Logger.getLogger(SCPClient.class.getName()).log(Level.SEVERE, null,ex);
        }finally{
        	conn.close();
        }
        return b;
    }
    
    public InputStream download(String remoteFile) throws Exception {
    	InputStream in=null;
    	remoteFile ="/"+rootdir+ "/" + remoteFile;
		String targetPath = remoteFile.substring(0, remoteFile.lastIndexOf("/"));
		String fileName = remoteFile.substring(remoteFile.lastIndexOf("/") + 1);
		String backFileTempPath = creteTempPath();
		
		Connection conn = new Connection(ip,port);
        try {
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username,
                    password);
            if (isAuthenticated == false) {
                System.err.println("authentication failed");
            }
            SCPClient client = new SCPClient(conn);
            client.get(remoteFile, backFileTempPath);
            
             in=new FileInputStream(new File(backFileTempPath+"/"+fileName));
            
            return in;
            
        } catch (IOException ex) {
        	ex.printStackTrace();
            //Logger.getLogger(SCPClient.class.getName()).log(Level.SEVERE, null,ex);
        }finally{
        	conn.close();
        }
        return in;
	}
    
    /**
     *@param fis 要上传的文件输入流
	 * @param targetFile 上传的目标文件路径跟文件名
	 * @return true：上传成功！false：上传失败！
	 */
    public boolean upload(InputStream fis, String targetFile) {
    	targetFile = "/"+rootdir+ "/" + targetFile;
		String targetPath = targetFile.substring(0, targetFile.lastIndexOf("/"));
		String fileName = targetFile.substring(targetFile.lastIndexOf("/") + 1);
		boolean b = false;
        Connection conn = new Connection(ip,port);
        try {
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username,
                    password);
            if (isAuthenticated == false) {
                System.err.println("authentication failed");
            }
            SCPClient client = new SCPClient(conn);
            mkDir(targetPath,conn);//新建目录
            client.put(input2byte(fis), fileName, targetPath, "0600");
            b=true;
        } catch (IOException ex) {
        	b=false;
        	ex.printStackTrace();
            //Logger.getLogger(SCPClient.class.getName()).log(Level.SEVERE, null,ex);
        }finally{
        	conn.close();
        }
        return b;
    }
    
    /**
	 * @param filePath
	 *            要创建的文件路径
	 * @throws IOException
	 */
	private synchronized void mkDir(String filePath, Connection conn){
		
		try {
			ch.ethz.ssh2.Session sess = conn.openSession();
			sess.execCommand("mkdir -p " + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
   
    public static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream(1024*1024);
            byte[] b = new byte[1024*1024];
            int i;
            while ((i = fis.read(b)) != -1) {
                byteArray.write(b, 0, i);
            }
            fis.close();
            byteArray.close();
            buffer = byteArray.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
    
    public static final byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream(1024*1024);
        byte[] buff = new byte[1024*1024];  
        int rc = 0;  
        while ((rc = inStream.read(buff, 0, 100)) > 0) {  
            swapStream.write(buff, 0, rc);  
        }  
        byte[] in2b = swapStream.toByteArray();  
        return in2b;  
    }
    
    /**
	 * 是否已经存在路径文件
	 * 
	 * @param remoteFile 文件路径
	 * @return 是否存在
	 * @throws IOException
	 */
	public boolean isExistFile(String remoteFile) throws Exception {
		boolean b=false;
    	remoteFile = "/" + remoteFile;
		String targetPath = remoteFile.substring(0, remoteFile.lastIndexOf("/"));
		String fileName = remoteFile.substring(remoteFile.lastIndexOf("/") + 1);
		String backFileTempPath = FileUtils.creteTempPath();
		Connection conn = new Connection(ip,port);
        try {
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username,
                    password);
            if (isAuthenticated == false) {
                System.err.println("authentication failed");
            }
            SCPClient client = new SCPClient(conn);
            client.get(remoteFile, backFileTempPath);

            if(new File(backFileTempPath+"/"+fileName).exists()){
            	b=true;
            }
            
        } catch (IOException ex) {
        	b=false;
        	ex.printStackTrace();
            //Logger.getLogger(SCPClient.class.getName()).log(Level.SEVERE, null,ex);
        }finally{
        	conn.close();
        }
        return b;
	}
	
	/**
	 * @param pathname 服务器上的文件
	 * @throws IOException
	 */
	public boolean delete(String pathname) throws IOException {
		Connection conn = new Connection(ip,port);
		boolean b=false;
        try {
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username,
                    password);
            if (isAuthenticated == false) {
                System.err.println("authentication failed");
            }
            SCPClient client = new SCPClient(conn);
            
            //重命名
            ch.ethz.ssh2.Session sess = conn.openSession();
            
            //String tmpPathName = remoteTargetDirectory +File.separator+ remoteFileName;
            //String newPathName = tmpPathName.substring(0, tmpPathName.lastIndexOf("."));
            sess.execCommand("rm " + pathname );//删除
            b=true;
           
        } catch (IOException ex) {
        	b=false;
        	ex.printStackTrace();
            //Logger.getLogger(SCPClient.class.getName()).log(Level.SEVERE, null,ex);
        }finally{
        	conn.close();
        }
        return b;
	}
	
	public static String creteTempPath() {
		String tempDir = FileUtils.getTempDirectoryPath();
		String aztTempDir = tempDir + File.separator + "azttemp" + File.separator;
		// 目录不存在，则新建一个目录
		if(!FileUtils.exists(aztTempDir)) {
			FileUtils.mkdir(aztTempDir);
		}
		String aztTempFilePath = aztTempDir;
		return aztTempFilePath;
	}
	
	/**修改文件名称
	 * @param
	 * 
	 */
	public void renameFile(String oldFilePath, String newFilePath) {

        File toBeRenamed = new File(oldFilePath);
        //检查要重命名的文件是否存在，是否是文件
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {

            System.out.println("File does not exist: " + oldFilePath);
            return;
        }

        File newFile = new File(newFilePath);

        //修改文件名
        if (toBeRenamed.renameTo(newFile)) {
            System.out.println("File has been renamed.");
        } else {
            System.out.println("Error renmaing file");
        }

    }

    private String ip;
    private int port;
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}