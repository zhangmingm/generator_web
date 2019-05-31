package com.store.frank.mybatis.base;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.store.frank.utils.Constant;
import com.store.frank.utils.PropertiesUtils;
import com.store.frank.webapp.module.GlobalProperty;
import com.store.frank.webapp.module.TableInfo;
import lombok.Data;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置汇总 传递给文件生成工具
 *
 * @author YangHu, tangguo, hubin
 * @since 2016-08-30
 */
@Data
public class ConfigBuilder {

    /**
     * 模板路径配置信息
     */
    private final TemplateConfig template;
    /**
     * 数据库配置
     */
    private final DataSourceConfig dataSourceConfig;
    /**
     * SQL连接
     */
    private Connection connection;
    /**
     * SQL语句类型
     */
    private String superEntityClass;
    private String superMapperClass;
    /**
     * service超类定义
     */
    private String superServiceClass;
    private String superServiceImplClass;
    /**
     * 数据库表信息
     */
    private TableInfo tableInfo;
    private List<TableInfo> tableInfoList;
    /**
     * 包配置详情
     */
    private Map<String, String> packageInfo;
    /**
     * 路径配置信息
     */
    private Map<String, String> pathInfo;
    /**
     * 策略配置
     */
    private StrategyConfig strategyConfig;
    /**
     * 全局配置信息
     */
    private GlobalConfig globalConfig;



    public ConfigBuilder(PackageConfig packageConfig, DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig,
                         GlobalConfig globalConfig,TableInfo tableInfo) {
        // 全局配置
        if (null == globalConfig) {
            this.globalConfig = new GlobalConfig();
        } else {
            this.globalConfig = globalConfig;
        }

        this.template = new TemplateConfig();

        // 包配置
        handlerPackage(this.template,packageConfig);
        this.dataSourceConfig = dataSourceConfig;
        // 策略配置
        if (null == strategyConfig) {
            this.strategyConfig = new StrategyConfig();
        } else {
            this.strategyConfig = strategyConfig;
        }
        handlerStrategy(this.strategyConfig,tableInfo);
    }

    // ************************ 曝露方法 BEGIN*****************************

    /**
     * 所有包配置信息
     *
     * @return 包配置
     */
    public Map<String, String> getPackageInfo() {
        return packageInfo;
    }


    /**
     * 所有路径配置
     *
     * @return 路径配置
     */
    public Map<String, String> getPathInfo() {
        return pathInfo;
    }


    public String getSuperEntityClass() {
        return superEntityClass;
    }


    public String getSuperMapperClass() {
        return superMapperClass;
    }


    /**
     * 获取超类定义
     *
     * @return 完整超类名称
     */
    public String getSuperServiceClass() {
        return superServiceClass;
    }


    public String getSuperServiceImplClass() {
        return superServiceImplClass;
    }




    /**
     * 模板路径配置信息
     *
     * @return 所以模板路径配置信息
     */
    public TemplateConfig getTemplate() {
        return template == null ? new TemplateConfig() : template;
    }

    // ****************************** 曝露方法 END**********************************

    /**
     * 处理包配置
     *
     * @param template  TemplateConfig
     */
    private void handlerPackage(TemplateConfig template,PackageConfig config) {
        GlobalProperty globalProperty=PropertiesUtils.getGlobalProperty();
        String outputDir = globalProperty.getOutputDir();
        String moduleName=globalProperty.getModuleName();
        String parentPackage=globalProperty.getParentPackage();
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

        setPathInfo(pathInfo, template.getEntity(), outputDir, Constant.ENTITY_PATH, Constant.ENTITY);
        setPathInfo(pathInfo, template.getVo(), outputDir, Constant.VO_PATH, Constant.VO);
        setPathInfo(pathInfo, template.getMapper(), outputDir, Constant.MAPPER_PATH, Constant.MAPPER);
        String xmlPathTemp=outputDir.replace("java","resources")+"//mapper//"+moduleName;
        setPathInfo(pathInfo, template.getXml(), xmlPathTemp, Constant.XML_PATH, Constant.XML);
        setPathInfo(pathInfo, template.getService(), outputDir, Constant.SERVICE_PATH, Constant.SERVICE);
        setPathInfo(pathInfo, template.getServiceImpl(), outputDir, Constant.SERVICE_IMPL_PATH, Constant.SERVICE_IMPL);
        setPathInfo(pathInfo, template.getController(), outputDir, Constant.CONTROLLER_PATH, Constant.CONTROLLER);
    }

    private void setPathInfo(Map<String, String> pathInfo, String template, String outputDir, String path, String module) {
        if (StringUtils.isNotEmpty(template)) {
            if(Constant.XML.equals(module)){
                pathInfo.put(path, outputDir);
            }else{
                pathInfo.put(path, joinPath(outputDir, packageInfo.get(module)));
            }
        }
    }


    private void handlerStrategy(StrategyConfig config,TableInfo tableInfo) {
        processTypes(config);
        this.tableInfo = tableInfo;
    }


    /**
     * 处理superClassName,IdClassType,IdStrategy配置
     *
     * @param config 策略配置
     */
    private void processTypes(StrategyConfig config) {
        if (StringUtils.isEmpty(config.getSuperServiceClass())) {
            superServiceClass = Constant.SUPER_SERVICE_CLASS;
        } else {
            superServiceClass = config.getSuperServiceClass();
        }
        if (StringUtils.isEmpty(config.getSuperServiceImplClass())) {
            superServiceImplClass = Constant.SUPER_SERVICE_IMPL_CLASS;
        } else {
            superServiceImplClass = config.getSuperServiceImplClass();
        }
        if (StringUtils.isEmpty(config.getSuperMapperClass())) {
            superMapperClass = Constant.SUPER_MAPPER_CLASS;
        } else {
            superMapperClass = config.getSuperMapperClass();
        }
        superEntityClass = config.getSuperEntityClass();
    }



    /**
     * 连接路径字符串
     *
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
        String result=parentDir + packageName;
        return result;
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




    public StrategyConfig getStrategyConfig() {
        return strategyConfig;
    }



    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }


}
