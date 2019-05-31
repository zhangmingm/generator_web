package com.store.frank.mybatis.base;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.toolkit.PackageHelper;
import com.store.frank.utils.Constant;
import com.store.frank.utils.StringTools;
import com.store.frank.webapp.module.TableInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 模板引擎抽象类
 *
 * @author hubin
 * @since 2018-01-10
 */
public abstract class AbstractTemplateEngine {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractTemplateEngine.class);
    /**
     * 配置信息
     */
    private ConfigBuilder configBuilder;



    /**
     * 模板引擎初始化
     */
    public AbstractTemplateEngine init(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        return this;
    }


    /**
     * 输出 java xml 文件
     */
    public AbstractTemplateEngine batchOutput() {
        try {
            TableInfo tableInfo= getConfigBuilder().getTableInfo();
                Map<String, Object> objectMap = getObjectMap(tableInfo);
                Map<String, String> pathInfo = getConfigBuilder().getPathInfo();
                TemplateConfig template = getConfigBuilder().getTemplate();
                // Mp.java
                String filePath=null;
                String entityName = tableInfo.getEntityName();
                if (null != entityName && null != pathInfo.get(Constant.ENTITY_PATH)) {
                    String entityFile = String.format((pathInfo.get(Constant.ENTITY_PATH) + File.separator + "%s" + suffixJavaOrKt()), entityName);
                    if (isCreate(entityFile)) {
                        filePath=templateFilePath(template.getEntity());
                        writer(objectMap, filePath, entityFile);
                    }
                }
                // Vo.java
                String voName = tableInfo.getVoName();
                if (null != voName && null != pathInfo.get(Constant.VO_PATH)) {
                    String voFile = String.format((pathInfo.get(Constant.VO_PATH) + File.separator + "%s" + suffixJavaOrKt()), voName);
                    if (isCreate(voFile)) {
                        filePath=templateFilePath(template.getVo());
                        writer(objectMap, filePath, voFile);
                    }
                }
                // MpMapper.java
                if (null != tableInfo.getMapperName() && null != pathInfo.get(Constant.MAPPER_PATH)) {
                    String mapperFile = String.format((pathInfo.get(Constant.MAPPER_PATH) + File.separator + tableInfo.getMapperName() + suffixJavaOrKt()), entityName);
                    if (isCreate(mapperFile)) {
                        writer(objectMap, templateFilePath(template.getMapper()), mapperFile);
                    }
                }
                // MpMapper.xml
                if (null != tableInfo.getXmlName() && null != pathInfo.get(Constant.XML_PATH)) {
                    String temp=pathInfo.get(Constant.XML_PATH) + File.separator + tableInfo.getXmlName() + Constant.XML_SUFFIX;
                    String xmlFile = String.format(temp, entityName);
                    if (isCreate(xmlFile)) {
                        String templateFilePath=templateFilePath(template.getXml());
                        writer(objectMap, templateFilePath, xmlFile);
                    }
                }
                // IMpService.java
                if (null != tableInfo.getServiceName() && null != pathInfo.get(Constant.SERVICE_PATH)) {
                    String serviceFile = String.format((pathInfo.get(Constant.SERVICE_PATH) + File.separator + tableInfo.getServiceName() + suffixJavaOrKt()), entityName);
                    if (isCreate(serviceFile)) {
                        writer(objectMap, templateFilePath(template.getService()), serviceFile);
                    }
                }
                // MpServiceImpl.java
                if (null != tableInfo.getServiceImplName() && null != pathInfo.get(Constant.SERVICE_IMPL_PATH)) {
                    String implFile = String.format((pathInfo.get(Constant.SERVICE_IMPL_PATH) + File.separator + tableInfo.getServiceImplName() + suffixJavaOrKt()), entityName);
                    if (isCreate(implFile)) {
                        writer(objectMap, templateFilePath(template.getServiceImpl()), implFile);
                    }
                }
                // MpController.java
                if (null != tableInfo.getControllerName() && null != pathInfo.get(Constant.CONTROLLER_PATH)) {
                    String controllerFile = String.format((pathInfo.get(Constant.CONTROLLER_PATH) + File.separator + tableInfo.getControllerName() + suffixJavaOrKt()), entityName);
                    if (isCreate(controllerFile)) {
                        writer(objectMap, templateFilePath(template.getController()), controllerFile);
                    }
                }
        } catch (Exception e) {
            logger.error("无法创建文件，请检查配置信息！", e);
        }
        return this;
    }


    /**
     * 将模板转化成为文件
     *
     * @param objectMap    渲染对象 MAP 信息
     * @param templatePath 模板文件
     * @param outputFile   文件生成的目录
     */
    public abstract void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception;

    /**
     * 处理输出目录
     */
    public AbstractTemplateEngine mkdirs() {
        getConfigBuilder().getPathInfo().forEach((key, value) -> {
            File dir = new File(value);
            if (!dir.exists()) {
                boolean result = dir.mkdirs();
                if (result) {
                    logger.debug("创建目录： [" + value + "]");
                }
            }
        });
        return this;
    }







    /**
     * 渲染对象 MAP 信息
     *
     * @param tableInfo 表信息对象
     * @return ignore
     */
    public Map<String, Object> getObjectMap(TableInfo tableInfo) {
        Map<String, Object> objectMap = new HashMap<>(30);
        ConfigBuilder config = getConfigBuilder();
        objectMap.put("package", config.getPackageInfo());
        GlobalConfig globalConfig = config.getGlobalConfig();
        objectMap.put("author", globalConfig.getAuthor());
        objectMap.put("date", new SimpleDateFormat("yyyy-MM-dd hh:MM:ss").format(new Date()));
        objectMap.put("table", tableInfo);
        objectMap.put("tableName", tableInfo.getTableName());
        objectMap.put("tableFieldSize", tableInfo.getFields().size()-1);
        objectMap.put("queryFieldSize", tableInfo.getQueryFields().size()-1);
        objectMap.put("updateFieldSize", tableInfo.getUpdateFields().size()-1);
        objectMap.put("itemFieldSize", tableInfo.getItemFields().size()-1);
        objectMap.put("entity", tableInfo.getEntityName());
        objectMap.put("vo", tableInfo.getVoName());
        objectMap.put("entityInstanceName", StringTools.getInstanceName(tableInfo.getTableName())); // 实体实例化名称 首字母小写
        objectMap.put("superMapperClassPackage", config.getSuperMapperClass());
        objectMap.put("superMapperClass", getSuperClassName(config.getSuperMapperClass()));

        // 主键是否自增，自增id 添加 @TableId(value = "id",type = IdType.AUTO)
        objectMap.put("idAutoIncrement", globalConfig.getIdAutoIncrement());
        // 是否生成Getter方法
        objectMap.put("autoGetter", globalConfig.getAutoGetter());
        return objectMap;
    }


    /**
     * 获取类名
     *
     * @param classPath ignore
     * @return ignore
     */
    private String getSuperClassName(String classPath) {
        if (StringUtils.isEmpty(classPath)) {
            return null;
        }
        return classPath.substring(classPath.lastIndexOf(StringPool.DOT) + 1);
    }


    /**
     * 模板真实文件路径
     *
     * @param filePath 文件路径
     * @return ignore
     */
    public abstract String templateFilePath(String filePath);


    /**
     * 检测文件是否存在
     * @return 文件是否存在
     */
    protected boolean isCreate(String filePath) {
        boolean flag=getConfigBuilder().getGlobalConfig().isFileOverride();
        File file = new File(filePath);
        if(flag){ // true 需要覆盖原来的文件
            PackageHelper.mkDir(file.getParentFile());
            return flag;
        }else{
            boolean exist = file.exists();
            if (!exist) {
                PackageHelper.mkDir(file.getParentFile());
            }
            return !flag;
        }
    }

    /**
     * 文件后缀
     */
    protected String suffixJavaOrKt() {
        return Constant.JAVA_SUFFIX;
    }


    public ConfigBuilder getConfigBuilder() {
        return configBuilder;
    }

}
