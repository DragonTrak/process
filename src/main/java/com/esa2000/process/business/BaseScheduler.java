package com.esa2000.process.business;

import java.util.Map;

/**
 * @Description TODO
 * @Date 2019/1/16 13:58
 * @Created by Administrator
 */
public abstract class BaseScheduler {
    public abstract void apply(Map<String,String> parm) throws Exception;
}
