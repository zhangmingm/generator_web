package com.store.frank.webapp.module;

import com.store.frank.utils.DbTools;
import com.store.frank.utils.StringTools;
import lombok.Data;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @date 2019年05月15日 15:43
 * @Description: 表对应字段vo
 */
@Data
public class TableField {

    // 字段名称
    public String columnName;

    // 字段注释
    public String columnComment;

    // java字段类型
    public String columnType;

    // 字段java名称 下划线转驼峰
    public String propertyName;

    // 字段java名称 get方法的字段名称，首字母大写
    public String getterName;


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/5/15 0015 下午 6:09
     * @Description: 根据字段名称获取Java属性名称
     */
    public TableField(String columnName, String columnComment, String dataType) {
        this.columnName = columnName;
        this.columnComment = columnComment;
        this.columnType = DbTools.getJavaTypeByDbType(dataType);
        this.propertyName=StringTools.UnderlineToHump(columnName);
        this.getterName=StringTools.dbField2UpperCamel(columnName);
    }
}
