package com.esa2000.process.util;

import java.io.*;

/**
 * @Description 序列化工具类
 * @Date 2019/1/16 16:47
 * @Created by Administrator
 */
public class SerializeUtil {
    /**
     * 反序列化磁盘文件到对象
     * @param url
     * @return
     */
    public static Object deserialize(String url){
        ObjectInputStream in = null;
        Object obj = null;
        try {
            in = new ObjectInputStream(new FileInputStream(url));
            obj = in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

        }
        return obj;
    }

    /**
     * 序列化文件到磁盘
     * @param object
     * @param url
     */
    public static void serialize(Object object,String url){
        ObjectOutputStream out = null;
        try {
            File file = new File("url");
            //如果序列化文件存在 则删除
            if (file.exists()){
                file.delete();
            }
            out = new ObjectOutputStream(new FileOutputStream(url));
            out.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


//    /**
//     * 反序列化磁盘文件到对象
//     * @param url
//     * @return
//     */
//    public static<T> T deserialize(Class<T> clazz,String url){
//        ObjectInputStream in = null;
//        Object obj = null;
//        try {
//            in = new ObjectInputStream(new FileInputStream(url));
//            obj = in.readObject();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//
//        }
//        return (T)obj;
//    }

}
