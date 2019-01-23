package com.esa2000.process.util.file.utils;

import com.esa2000.process.util.toolUtil.CommonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * 工具类
 * @author liyul
 * @create 2018/3/1
 *
 */
public class FileUtils {

    private static Log log = LogFactory.getLog(FileUtils.class);

    /**
     * 文件流转byte
     * @param in
     * @return
     */
    public static byte[] transInputstreamToBytes(InputStream in) {
        byte[] in2b = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            byte[] buff = new byte[4096];
            int len = 0;
            while ((len = in.read(buff, 0, 4096)) > 0) {
                baos.write(buff, 0, len);
                baos.flush();
            }
            in2b = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return in2b;
    }

    /**
     * 将字节数组写到文件中
     *
     * @param b 字节数组
     *
     * @param outputFile  文件名称
     *
     * @return
     */
    public static File writeBytesToFile(byte[] b, String outputFile) {
        File file = null;
        OutputStream os = null;
        try {
            file = new File(outputFile);
            os = new FileOutputStream(file);
            os.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 从文件中读取字节数组
     *
     * @param fileName
     *            文件名称
     * @return
     */
    public static byte[] readBytesFromFile(String fileName) {
        return readBytesFromFile(new File(fileName));
    }

    public static byte[] readBytesFromFile(File file) {
        byte[] bytes = null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            bytes = transIsToBytes(is);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    /**
     * 将InputStream中的内容读取出来，转为字节数据 InputStream --> byte[]
     *
     * @param is
     * @return
     */
    public static byte[] transIsToBytes(InputStream is) {
        int BUFF_SIZE = 1024;
        byte[] in2b = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            byte[] buff = new byte[BUFF_SIZE];
            int len = 0;
            while ((len = is.read(buff, 0, BUFF_SIZE)) > 0) {
                baos.write(buff, 0, len);
                baos.flush();
            }
            in2b = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return in2b;
    }

    /**
     * 对字符串进行Base64编码
     *
     * @param value
     *            需编码的字符串
     * @return 编码后的字符串
     */
    public static String base64EncodeString(String value) {
        String encodeValue = null;
        if (value != null) {
            encodeValue = new BASE64Encoder().encode(value.getBytes());
            return encodeValue;
        }
        return encodeValue;
    }

    /**
     * 对字符串进行Base64编码
     *
     * @param value
     *            需编码的字符串
     * @param charSet
     *            编码格式
     * @return 编码后的字符串
     */
    public static String base64EncodeString(String value, String charSet) {
        String encodeValue = null;
        if (value != null) {
            try {
                encodeValue = new BASE64Encoder().encode(value.getBytes(charSet));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return encodeValue;
    }

    /**
     * 对字符串进行Base64解码
     *
     * @param value
     *            要编码的字符串
     * @param charSet
     *            编码格式
     * @return 编码后的字符串
     */
    public static String base64DecodeString(String value, String charSet) {
        String decodeValue = null;
        if (value != null) {
            try {
                BASE64Decoder decoder = new BASE64Decoder();
                decodeValue = new String(decoder.decodeBuffer(value), charSet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return decodeValue;
    }

    /**
     * 对字节数组进行Base64编码
     *
     * @param input
     *            要编码的字节数组
     * @return 编码后的字节数组
     */
    public static byte[] base64EncodeByte(byte[] input) {
        byte[] b = null;
        try {
            if (input != null && input.length > 0) {
                BASE64Encoder encoder = new BASE64Encoder();
                b = encoder.encode(input).getBytes();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /**
     * 对字节数组进行Base64编码
     *
     * @param input
     *            要编码的字节数组
     * @return 编码后的字节数组
     */
    public static String base64EncodeString(byte[] input) {
        String str = null;
        try {
            if (input != null && input.length > 0) {
                BASE64Encoder encoder = new BASE64Encoder();
                str = encoder.encode(input);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return str;
    }

    /**
     * 对字节数组进行Base64解码
     *
     * @param input
     *            要编码的字节数组
     * @return 编码后的字节数组
     */
    public static byte[] base64DecodeByte(byte[] input) {
        byte[] b = null;
        try {
            if (input != null && input.length > 0) {
                BASE64Decoder bd = new BASE64Decoder();
                b = bd.decodeBuffer(new ByteArrayInputStream(input));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    public static String getFileType(String filePath) {
        String fileType = "";
        int oIndex = filePath.lastIndexOf(".");
        if(oIndex > 0) {
            fileType =  filePath.substring(oIndex + 1, filePath.length());
        }
        return fileType.toLowerCase();
    }

    /**
     *
     * 删除单个文件
     *
     * @param fileName 被删除的文件名
     * @return 如果删除成功，则返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                log.info("删除单个文件 " + fileName + " 成功!");
                return true;
            } else {
                log.info("删除单个文件 " + fileName + " 失败!");
                return false;
            }
        } else {
            log.info(fileName + " 文件不存在!");
            return true;
        }
    }

    public static synchronized byte[] loadFile(String filePath){
        FileInputStream fileIn = null;
        try {
            fileIn =new FileInputStream(filePath);
            return transIsToBytes(fileIn);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if(fileIn != null){
                    fileIn.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String creteTempPath() {
        return creteTempPath("pdf");
    }

    public static String creteTempPath(String fileType) {
        String tempDir = getTempDirectoryPath();
        String aztTempDir = tempDir + "azttemp" + File.separator;
        // 目录不存在，则新建一个目录
        if(!FileUtils.exists(aztTempDir)) {
            mkdir(aztTempDir);
        }
        String aztTempFilePath = aztTempDir + CommonUtil.getRandPass() + "." + fileType;
        return aztTempFilePath;
    }

    public static String getTempDirectoryPath() {
        return System.getProperty("java.io.tmpdir");
    }
    /**
     * 根据folderPath创建相应的文件夹
     * @param folderPath
     * @return
     * @throws IOException
     */
    public static File mkdir(String folderPath) {
        File file = new File(folderPath);
        if(!file.exists() || !file.isDirectory()){
            boolean success = false;
            do {
                success = file.mkdirs();
            } while (success);

        }
        return file;
    }

    /**
     * 创建目录
     * @param descDirName 目录名,包含路径
     * @return 如果创建成功，则返回true，否则返回false
     */
    public static boolean createDirectory(String descDirName) {
        String descDirNames = descDirName;
        if (!descDirNames.endsWith(File.separator)) {
            descDirNames = descDirNames + File.separator;
        }
        File descDir = new File(descDirNames);
        if (descDir.exists()) {
            log.info("目录 " + descDirNames + " 已存在!");
            return false;
        }
        // 创建目录
        if (descDir.mkdirs()) {
            log.info("目录 " + descDirNames + " 创建成功!");
            return true;
        } else {
            log.info("目录 " + descDirNames + " 创建失败!");
            return false;
        }

    }

    public static boolean exists(String path) {
        return new File(path).exists();
    }


}

