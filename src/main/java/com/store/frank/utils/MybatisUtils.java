package com.store.frank.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.store.frank.webapp.module.GlobalProperty;
import com.store.frank.webapp.module.TableInfo;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @date 2019年05月14日 17:21
 * @Description: 用来创建mybatis相关的对象。
 */
public class MybatisUtils {

    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/5/14 0014 下午 5:50
     * @Description: 获取数据库连接池对象
     */
    private static DataSource getC3p0DataSource(GlobalProperty globalProperty) throws PropertyVetoException {
        ComboPooledDataSource  dataSource=new ComboPooledDataSource ();
        dataSource.setDriverClass(Constant.driverClass);
        dataSource.setJdbcUrl(globalProperty.getMysqlUrl()+Constant.urlParam);
        dataSource.setUser(globalProperty.getUserName());
        dataSource.setPassword(globalProperty.getPassWord());
        dataSource.setMaxPoolSize(30);
        return dataSource;
    }


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/5/14 0014 下午 6:02
     * @Description: 获取SqlSessionFactory
     */
    /*public static SqlSessionFactory getSqlSessionFactory(GlobalProperty globalProperty){
        DataSource dataSource= null;
        try {
            dataSource=getC3p0DataSource(globalProperty);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
//        构建数据库事务
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
//        构建mybatis环境
        Environment environment = new Environment("development", transactionFactory, dataSource);
//        构建配置环境
        Configuration configuration = new Configuration(environment);
//       构建SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return sqlSessionFactory;
    }*/

    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/5/14 0014 下午 6:12
     * @Description: 获取数据库连接对象
     */
    public static Connection getConnection(GlobalProperty globalProperty){
        DataSource dataSource= null;
        try {
            dataSource=getC3p0DataSource(globalProperty);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        Connection conn= null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static Map<String, Object> getObjectMap(GlobalProperty globalProperty) {
//        GlobalProperty globalProperty=PropertiesUtils.getGlobalProperty();
        Map<String, Object> map=new HashMap<>();
        map.put("package", globalProperty.getPackageConfig());
        map.put("author", globalProperty.getAuthor());
        map.put("date", new SimpleDateFormat("yyyy-MM-dd hh:MM:ss").format(new Date()));
        TableInfo tableInfo= globalProperty.getTableInfo();
        map.put("table", tableInfo);
        map.put("tableName", globalProperty.getTableName());
        map.put("tableFieldSize", tableInfo.getFields().size()-1);
        map.put("entity", globalProperty.getModuleName());
        map.put("entityInstanceName", globalProperty.getEntityInstanceName()); // 实体实例化名称 首字母小写
        map.put("superMapperClassPackage", Constant.SUPER_MAPPER_CLASS);
        map.put("superMapperClass", StringTools.getSuperClassName(Constant.SUPER_MAPPER_CLASS));
        return map;
    }

}
