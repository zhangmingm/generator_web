package com.store.frank.webapp.module;

import com.store.frank.mybatis.base.PackageConfig;
import com.store.frank.mybatis.base.TemplateConfig;
import lombok.Data;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @date 2019年05月14日 16:49
 * @Description: 全部的属性
 */
@Data
public class GlobalProperty {


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

//    勾选的表,如 activity_rule_relation globalProperty
    private String tableName;

//    表注释
    private String tableComment;

//    模块名称-表名称转换到驼峰 ,如 activityRuleRelation
    private String moduleName;

//    模块名称-表名称转换到驼峰 ,如 activityRuleRelation
    private String entityName;

//    模块名称-表名称转换到驼峰 ,如 activityRuleRelation
    private String entityInstanceName;

//    数据库名称
    private String schema;


    /**
     * 表相关信息
     */
    private TableInfo tableInfo;

    /**
     * 包和路径配置
     */
    private PackageConfig packageConfig;

    /**
     * 模板路径配置信息
     */
    private TemplateConfig template;


}
