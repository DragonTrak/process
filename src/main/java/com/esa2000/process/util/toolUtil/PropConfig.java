package com.esa2000.process.util.toolUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropConfig {
    static Properties prop = new Properties();

    static{
        try {
//            String path = System.getProperty("user.dir");
            String path = System.getProperty("confpath");
            String propPath = path + File.separator + "application.properties";
            File file = new File(propPath);
            if(file.exists()){
                prop.load(new FileInputStream(propPath));
            }else {
                prop.load(PropConfig.class.getResourceAsStream("/application.properties"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getString(String key){
        return prop.getProperty(key);
    }
}
