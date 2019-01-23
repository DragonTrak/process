package com.esa2000.process;

import com.esa2000.process.business.BaseScheduler;
import com.esa2000.process.util.SpringUtil;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableAutoConfiguration
@EnableAsync //开启异步
public class ProcessApplication implements CommandLineRunner {
	private static Logger logger = Logger.getLogger(ProcessApplication.class);

	public static void main(String[] args) {
	    //-Dconfpath 直接设置java运行参数 可以替换下面的用法
		for (String arg : args) {
			if (arg.contains("confpath")){
				String confpath = arg.split("=")[1].trim();
				System.setProperty("confpath",confpath);
			}
		}
//		SpringApplication.run(ProcessApplication.class, args);
		new SpringApplicationBuilder(ProcessApplication.class).web(false).run(args);

	}

	@Override
	public void run(String... args) throws Exception {
	    Map<String,String> parm = new HashMap<>();
        String processName = "";
        for (String arg : args) {
            if (arg.contains("processName")){
                 processName = arg.split("=")[1].trim();
            }
            if (arg.contains("userId")){
                String userId = arg.split("=")[1].trim();
                parm.put("userId",userId);
            }
            if (arg.contains("dataDir")){
                String dataDir = arg.split("=")[1].trim();
                parm.put("dataDir",dataDir);
            }
        }
		BaseScheduler baseTask = (BaseScheduler)SpringUtil.getBean(processName);
		baseTask.apply(parm);
		System.exit(0);
	}

}

