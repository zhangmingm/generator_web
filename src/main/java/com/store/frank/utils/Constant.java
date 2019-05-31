package com.store.frank.utils;

import com.baomidou.mybatisplus.core.toolkit.StringPool;

import java.nio.charset.StandardCharsets;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @date 2019年05月14日 17:54
 * @Description: 常量
 */
public class Constant {
    public static final String driverClass="com.mysql.cj.jdbc.Driver";

    public static final String urlParam="?useUnicode=true&autoReconnect=true&rewriteBatchedStatements=true&zeroDateTimeBehavior=convertToNull&noAccessToProcedureBodies=true&allowMultiQueries=true&serverTimezone=UTC&characterEncoding=UTF-8";

//    模块名
    public static final String MODULE_NAME = "ModuleName";
//    实体名
    public static final String ENTITY = "Entity";
//    值对象名
    public static final String VO = "Vo";
//    service接口名
    public static final String SERVICE = "Service";
//    service接口实现类名称
    public static final String SERVICE_IMPL = "ServiceImpl";
//    mapper名
    public static final String MAPPER = "Mapper";
//    xml名
    public static final String XML = "Xml";
//    controller 名
    public static final String CONTROLLER = "Controller";



    public static final String TEMPLATE_ENTITY = "/templates/entity.java";
    public static final String TEMPLATE_VO = "/templates/vo.java";
    public static final String TEMPLATE_MAPPER = "/templates/mapper.java";
    public static final String TEMPLATE_XML = "/templates/mapper.xml";
    public static final String TEMPLATE_SERVICE = "/templates/service.java";
    public static final String TEMPLATE_SERVICE_IMPL = "/templates/serviceImpl.java";
    public static final String TEMPLATE_CONTROLLER = "/templates/controller.java";



    public static final String ENTITY_PATH = "entity_path";
    public static final String VO_PATH = "vo_path";
    public static final String SERVICE_PATH = "service_path";
    public static final String SERVICE_IMPL_PATH = "service_impl_path";
    public static final String MAPPER_PATH = "mapper_path";
    public static final String XML_PATH = "xml_path";
    public static final String CONTROLLER_PATH = "controller_path";

    public static final String JAVA_TMPDIR = "java.io.tmpdir";

    public static final String XML_SUFFIX = ".xml";

    public static final String JAVA_SUFFIX = StringPool.DOT_JAVA;

    public static final String UTF8 = StandardCharsets.UTF_8.name();


    public static final String SUPER_MAPPER_CLASS = "com.baomidou.mybatisplus.core.mapper.BaseMapper";
    public static final String SUPER_SERVICE_CLASS = "";
    public static final String SUPER_SERVICE_IMPL_CLASS = "";
    public static final String UNDERLINE = "_";
}
