package com.esa2000.process.base.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @Description TODO
 * @Date 2019/1/15 18:36
 * @Created by Administrator
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
