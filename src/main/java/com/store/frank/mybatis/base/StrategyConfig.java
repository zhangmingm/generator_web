package com.store.frank.mybatis.base;

import com.store.frank.utils.Constant;
import com.store.frank.utils.PropertiesUtils;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.Properties;

/**
 * 策略配置项
 * @author YangHu, tangguo, hubin
 * @since 2016/8/30
 */
@Data
public class StrategyConfig {

    /**
     * 是否大写命名
     */
    private boolean isCapitalMode = false;

//   表名称
    private String tableName;

    /**
     * 数据库表映射到实体的命名策略
     */
//    private NamingStrategy naming = NamingStrategy.underline_to_camel;
    /**
     * 数据库表字段映射到实体的命名策略
     * <p>未指定按照 naming 执行</p>
     */
//    private NamingStrategy columnNaming = NamingStrategy.underline_to_camel;
    /**
     * 自定义继承的Entity类全称，带包名
     */
    @Setter(AccessLevel.NONE)
    private String superEntityClass;
    /**
     * 自定义基础的Entity类，公共字段
     */
    @Setter(AccessLevel.NONE)
    private String[] superEntityColumns;
    /**
     * 自定义继承的Mapper类全称，带包名
     */
    private String superMapperClass = Constant.SUPER_MAPPER_CLASS;
    /**
     * 自定义继承的Service类全称，带包名
     */
    private String superServiceClass = Constant.SUPER_SERVICE_CLASS;
    /**
     * 自定义继承的ServiceImpl类全称，带包名
     */
    private String superServiceImplClass = Constant.SUPER_SERVICE_IMPL_CLASS;

    /**
     * 实体是否生成 serialVersionUID
     */
    private boolean entitySerialVersionUID = true;
    /**
     * 【实体】是否为lombok模型（默认 false）<br>
     * <a href="https://projectlombok.org/">document</a>
     */
    private boolean entityLombokModel = true;


    public boolean includeSuperEntityColumns(String fieldName) {
        if (null != superEntityColumns) {
            for (String column : superEntityColumns) {
                if (column.equals(fieldName)) {
                    return true;
                }
            }
        }
        return false;
    }





    public StrategyConfig() {
        Properties properties=PropertiesUtils.getProperties();
        String baseEntity= (String) properties.get("baseEntity");
        String tableName= (String) properties.get("tableName");
        String superColumns= (String) properties.get("superColumns");
        this.superEntityClass=baseEntity;
        if(null != superColumns && !"".equals(superColumns)){
            String[] temp=superColumns.split(",");
            this.superEntityColumns=temp;
        }else{
            this.superEntityColumns=null;
        }
        this.tableName=tableName;
    }

}
