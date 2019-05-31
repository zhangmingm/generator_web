package com.store.frank.utils;

import com.alibaba.fastjson.JSON;
import com.store.frank.webapp.module.GlobalProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @date 2019年04月18日 14:40
 * @Description:
 */
public class PropertiesUtils {

    protected static final Logger log = LoggerFactory.getLogger(PropertiesUtils.class);


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/5/15 0015 上午 11:55
     * @Description: 向Properties 文件添加属性
     */
    public static Properties addProperties(String key,String value) throws Exception {
        Properties properties = getProperties();
        OutputStream out = new FileOutputStream(getPropertyPath());
        if(null != key){
            properties.setProperty(key,value);
            properties.store(out,null);
        }
        out.close();
        return getProperties();
    }
    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/5/15 0015 上午 11:55
     * @Description: 向Properties 文件添加属性
     */
    public static Properties addProperties(Map<String,String> param) throws Exception {
        Properties properties = getProperties();
        String key;
        String value;
        OutputStream out = new FileOutputStream(getPropertyPath());
        for (Map.Entry<String,String> entry : param.entrySet()) {
            key= (String) entry.getKey();
            value= (String) entry.getValue();
            properties.setProperty(key,value);
        }
        properties.store(out,null);
        out.close();
        return getProperties();
    }


    
    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/4/20 0020 下午 1:18
     * @Description: 加载 先前配置的参数
     */
    public static Properties getProperties(){
        Properties properties=new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream(getPropertyPath());
        } catch (FileNotFoundException e) {
            log.error("缺少配置文件: info.properties");
            e.printStackTrace();
        }
        try {
            properties.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/5/16 0016 上午 11:04
     * @Description: 将map集合转换为GlobalProperty
     */
    public static GlobalProperty getGlobalProperty(){
        Properties properties=getProperties();
        String key=null;
        String value=null; // map中value的值
        Map<String, String> mapProperty=new HashMap<>();
        for (Map.Entry<Object,Object> entry : properties.entrySet()) {
            key= (String) entry.getKey();
            value= (String) entry.getValue();
            mapProperty.put(key,value);
        }
        GlobalProperty globalProperty = JSON.parseObject(JSON.toJSONString(mapProperty), GlobalProperty.class);
        return globalProperty;
    }


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/5/15 0015 上午 10:29
     * @Description: 获取配置文件的路径
     */
    private static String getPropertyPath(){
        String projectPath = System.getProperty("user.dir");
        String propertyPath=projectPath+"/src/main/java/com/store/frank/utils/info.properties";
        return propertyPath;
    }

    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/5/15 0015 上午 10:29
     * @Description: 获取配置文件的路径
     */
    public static String getTemplatePath(String templatePath){
        String projectPath = System.getProperty("user.dir");
        String propertyPath=projectPath+"/src/main/resources/temp"; //+templatePath+".ftl";
        return propertyPath;
    }


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/5/30 0030 下午 3:47
     * @Description: 初始化配置文件内容。防止将文件中内容清空页面报错。
     */
    public static Properties initProperty() throws IOException {
        Properties properties = getProperties();
        OutputStream out = new FileOutputStream(getPropertyPath());
        properties.setProperty("entityInstanceName","activitySpree");
        properties.setProperty("moduleName","activitySpree");
        properties.setProperty("passWord","123456");
        properties.setProperty("parentPackage","cn.sstech.platform.activity");
        properties.setProperty("outputDir","D:\\proTools\\codeRepository\\xingsha\\activity\\trunk\\project\\src\\main\\java");
        properties.setProperty("schema","sstech_platform_activity");
        properties.setProperty("tableComment","备注信息");
        properties.setProperty("author","zhangmingming braveheart1115@163.com");
        properties.setProperty("mysqlUrl","jdbc:mysql://192.168.80.217:3306/sstech_platform_activity");
        properties.setProperty("entityName","ActivitySpree");
        properties.setProperty("userName","root");
        properties.setProperty("tableName","activity_spree");
        properties.setProperty("idAutoIncrement","true");
        properties.setProperty("autoGetter","false");
        properties.store(out,null);
        out.close();
        properties = getProperties();
        return properties;
    }





}
