package com.store.frank.mybatis.base;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.store.frank.utils.Constant;
import com.store.frank.utils.PropertiesUtils;
import com.store.frank.utils.StringTools;
import com.store.frank.webapp.module.GlobalProperty;
import lombok.Data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @date 2019年05月17日 16:20
 * @Description: 包相关配置
 */
@Data
public class PackageConfig {


    private String parentPackage = "com.baomidou";

    /**
     * 模块名称 表名称转驼峰
     */
    private String moduleName = null;

    /**
     * Entity包名
     */
    public String entity = "entity";
    /**
     * vo包名
     */
    public String vo = "vo";
    /**
     * Service包名
     */
    private String service = "service";
    /**
     * Service Impl包名
     */
    private String serviceImpl = "service.impl";
    /**
     * Mapper包名
     */
    private String mapper = "mapper";
    /**
     * Mapper XML包名
     */
    private String xml = "mapper.xml";
    /**
     * Controller包名
     */
    private String controller = "controller";
    /**
     * 路径配置信息
     */
    private Map<String, String> pathInfo;


    /**
     * 包配置详情
     */
    private Map<String, String> packageInfo;


    public PackageConfig() {
        Properties properties=PropertiesUtils.getProperties();
        String parentPackage= (String) properties.get("parentPackage");
        String tableName= (String) properties.get("tableName");
        String modelName = StringTools.dbField2LowerCamel(tableName);
        this.parentPackage=parentPackage;
        this.moduleName=modelName;
    }

    public PackageConfig(GlobalProperty globalProperty) {
        String outputDir = globalProperty.getOutputDir();
        String moduleName=globalProperty.getModuleName();
        String parentPackage=globalProperty.getParentPackage();
        PackageConfig config = new PackageConfig();
        // 包信息
        packageInfo = new HashMap<>(8);
        packageInfo.put(Constant.MODULE_NAME, moduleName);
        packageInfo.put(Constant.ENTITY, joinPackage(parentPackage,moduleName,config.getEntity()));
        packageInfo.put(Constant.VO, joinPackage(parentPackage,moduleName,config.getVo()));
        packageInfo.put(Constant.MAPPER, joinPackage(parentPackage,moduleName,config.getMapper()));
        packageInfo.put(Constant.XML, joinPackage(parentPackage,moduleName,config.getXml()));
        packageInfo.put(Constant.SERVICE, joinPackage(parentPackage,moduleName,config.getService()));
        packageInfo.put(Constant.SERVICE_IMPL, joinPackage(parentPackage,moduleName,config.getServiceImpl()));
        packageInfo.put(Constant.CONTROLLER, joinPackage(parentPackage,moduleName,config.getController()));

        // 生成路径信息
        pathInfo = new HashMap<>(7);
        setPathInfo(pathInfo, outputDir, Constant.ENTITY_PATH, Constant.ENTITY);
        setPathInfo(pathInfo, outputDir, Constant.VO_PATH, Constant.VO);
        setPathInfo(pathInfo, outputDir, Constant.MAPPER_PATH, Constant.MAPPER);
        String xmlPathTemp = outputDir.replace("java", "resources") + "//mapper//" + globalProperty.getModuleName();
        setPathInfo(pathInfo, xmlPathTemp, Constant.XML_PATH, Constant.XML);
        setPathInfo(pathInfo, outputDir, Constant.SERVICE_PATH, Constant.SERVICE);
        setPathInfo(pathInfo, outputDir, Constant.SERVICE_IMPL_PATH, Constant.SERVICE_IMPL);
        setPathInfo(pathInfo, outputDir, Constant.CONTROLLER_PATH, Constant.CONTROLLER);
    }




    /**
     * 连接父子包名
     *
     * @param parent     父包名
     * @param moduleName 模块名
     * @param subPackage 子包名
     * @return 连接后的包名
     */
    private String joinPackage(String parent,String moduleName,String subPackage) {
        return parent + StringPool.DOT + moduleName + StringPool.DOT+ subPackage;
    }

    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/5/18 0018 上午 11:24
     * @Description: 设置文件路径
     */

    private void setPathInfo(Map<String, String> pathInfo, String outputDir, String path, String module) {
        if (Constant.XML.equals(module)) {
            pathInfo.put(path, outputDir);
        } else {
            pathInfo.put(path, joinPath(outputDir, packageInfo.get(module)));
        }
    }

    /**
     * 连接路径字符串
     * @param parentDir   路径常量字符串
     * @param packageName 包名
     * @return 连接后的路径
     */
    private String joinPath(String parentDir, String packageName) {
        if (StringUtils.isEmpty(parentDir)) {
            parentDir = System.getProperty(Constant.JAVA_TMPDIR);
        }
        if (!StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        packageName = packageName.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
        String result = parentDir + packageName;
        return result;
    }

}
