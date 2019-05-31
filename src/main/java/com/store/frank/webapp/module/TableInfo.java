package com.store.frank.webapp.module;

import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @date 2019年05月14日 20:31
 * @Description:
 */
@Data
public class TableInfo {

    // 表名称
    private String tableName;
    // 表注释
    private String tableComment;

    private String entityName; // 实体名称
    private String mapperName; // mapper名称
    private String voName; // vo名称
    private String xmlName; // xml名称
    private String serviceName; // service 接口名称
    private String serviceImplName; // service 接口实现类名称
    private String controllerName; // controller名称

    private List<TableField> fields;
    private List<TableField> queryFields;
    private List<TableField> updateFields;
    private List<TableField> itemFields;

    /**
     * 公共字段
     */
    private List<TableField> commonFields;

    private final Set<String> importPackages = new HashSet<>();


    private String fieldNames;

}
