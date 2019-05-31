package com.store.frank.webapp.module;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @date 2019年05月14日 16:21
 * @Description: 存放传入的基本信息
 */
@Data
public class IndexProperty implements Serializable {

// mysql 连接地址
    public String mysqlUrl;
// 用户名
    public String userName;
// 密码
    public String passWord;
// 作者
    public String author;
// 父包路径
    public String parentPackage;
// 输出目录
    public String outputDir;
// 是否生成getter方法
    public String autoGetter;
// 表名称
    public String tableName;
// 数据库名称
    public String schema;
    public String aaa;

}
