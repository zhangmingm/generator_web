package com.store.frank.mybatis.base;

import com.baomidou.mybatisplus.annotation.DbType;
import com.store.frank.utils.Constant;
import com.store.frank.utils.PropertiesUtils;
import lombok.Data;

import java.util.Properties;

/**
 * 数据库配置
 *
 * @author YangHu
 * @since 2016/8/30
 */
@Data
public class DataSourceConfig {

    /**
     * 数据库类型
     */
    private DbType dbType;
    /**
     * PostgreSQL schemaName
     */
    private String schemaName;
    /**
     * 驱动连接的URL
     */
    private String mysqlUrl;
    /**
     * 驱动名称
     */
    private String driverName;
    /**
     * 数据库连接用户名
     */
    private String username;
    /**
     * 数据库连接密码
     */
    private String password;

    public DataSourceConfig() {
        Properties properties = PropertiesUtils.getProperties();
        this.mysqlUrl = (String) properties.get("mysqlUrl");
        this.driverName = Constant.driverClass;
        this.username = (String) properties.get("username");
        this.password = (String) properties.get("password");
    }



}