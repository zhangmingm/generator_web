package com.store.frank.utils;

import com.store.frank.webapp.module.GlobalProperty;
import com.store.frank.webapp.module.TableField;
import com.store.frank.webapp.module.TableInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @date 2019年05月14日 17:21
 * @Description: 数据库工具类
 */
public class DbTools {


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/5/31 0031 下午 1:46
     * @Description: 获取数据库名称
     */
    public static String getSchema(String url){
        String schema=url.substring(url.lastIndexOf("/")+1);
        return schema;
    }


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/5/31 0031 下午 3:37
     * @Description: 获取表中文说明
     */
    public static String getTableComment(String schema,String tableName) throws Exception {
        String sql="SELECT TABLE_COMMENT FROM information_schema.TABLES WHERE TABLE_NAME='"+tableName+"' AND table_schema='"+schema+"'";
        System.out.println("sql-- > "+sql);
        GlobalProperty globalProperty=PropertiesUtils.getGlobalProperty();
        Connection conn=MybatisUtils.getConnection(globalProperty);
        PreparedStatement ps=conn.prepareStatement(sql);
        ResultSet rs=ps.executeQuery();
        String tableComment=null;
        while(rs.next()){
            tableComment=rs.getString("TABLE_COMMENT");
        }
        return tableComment;
    }





    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/5/14 0014 下午 8:38
     * @Description: 获取数据库下的表信息
     */
    public static List<TableInfo> getTableInfo() throws Exception {
        GlobalProperty globalProperty=PropertiesUtils.getGlobalProperty();
        List<TableInfo> list=new ArrayList<>();
        String url=globalProperty.getMysqlUrl();
        String schema=url.substring(url.lastIndexOf("/")+1);
        globalProperty.setSchema(schema);
        String sql="SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.TABLES WHERE table_schema='"+schema+"'";
        System.out.println("sql-- > "+sql);
        Connection conn=MybatisUtils.getConnection(globalProperty);
        PreparedStatement ps=conn.prepareStatement(sql);
        ResultSet rs=ps.executeQuery();
        TableInfo vo=null;
        while(rs.next()){
            vo=new TableInfo();
            vo.setTableName(rs.getString("TABLE_NAME"));
            vo.setTableComment(rs.getString("TABLE_COMMENT"));
            list.add(vo);
        }
        PropertiesUtils.addProperties("schema",schema);
        return list;
    }

    /**
     * 处理表对应的类名称
     *
     * @param tableInfo 表信息
     * @return 补充完整信息后的表
     */
    public static TableInfo processTable(TableInfo tableInfo) {
        String entityName = tableInfo.getEntityName();
        tableInfo.setMapperName(entityName + Constant.MAPPER);
        tableInfo.setVoName(entityName + Constant.VO);
        tableInfo.setXmlName(entityName + Constant.MAPPER);
        tableInfo.setServiceName("I" + entityName + Constant.SERVICE);
        tableInfo.setServiceImplName(entityName + Constant.SERVICE_IMPL);
        tableInfo.setControllerName(entityName + Constant.CONTROLLER);
        return tableInfo;
    }


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/5/15 0015 下午 6:25
     * @Description: 获取表的字段和注释信息
     */
    public static List<TableField> getTableField(GlobalProperty globalProperty) throws SQLException {
        List<TableField> list=new ArrayList<>();
        String schema=globalProperty.getSchema();
        String tableName=globalProperty.getTableName();

        String sql="SELECT column_name,column_comment,data_type " +
                "FROM INFORMATION_SCHEMA.Columns " +
                "WHERE table_name='"+tableName+"' AND table_schema='"+schema+"'";
        System.out.println("sql-- > "+sql);
        Connection conn=MybatisUtils.getConnection(globalProperty);
        PreparedStatement ps=conn.prepareStatement(sql);
        ResultSet rs=ps.executeQuery();
        TableField vo=null;
        String columnName=null;
        String columnComment=null;
        String dataType=null;
        while(rs.next()){
            columnName=rs.getString("column_name");
            columnComment=rs.getString("column_comment");
            dataType=rs.getString("data_type");
            vo=new TableField(columnName, columnComment, dataType);
            list.add(vo);
        }
        return list;
    }

    public static List<TableField> getTableField() throws Exception {
        GlobalProperty globalProperty=PropertiesUtils.getGlobalProperty();
        String tableComment=DbTools.getTableComment(globalProperty.getSchema(),globalProperty.getTableName());
        PropertiesUtils.addProperties("tableComment",tableComment);
        List<TableField> list=new ArrayList<>();
        String schema=globalProperty.getSchema();
        String tableName=globalProperty.getTableName();

        String sql="SELECT column_name,column_comment,data_type " +
                "FROM INFORMATION_SCHEMA.Columns " +
                "WHERE table_name='"+tableName+"' AND table_schema='"+schema+"'";
        System.out.println("sql-- > "+sql);
        Connection conn=MybatisUtils.getConnection(globalProperty);
        PreparedStatement ps=conn.prepareStatement(sql);
        ResultSet rs=ps.executeQuery();
        TableField vo=null;
        String columnName=null;
        String columnComment=null;
        String columnType=null;
        while(rs.next()){
            columnName=rs.getString("column_name");
            columnComment=rs.getString("column_comment");
            columnType=rs.getString("data_type");
            /**
             * 如果id为long类型 idAutoIncrement 设置为true。
             */
            if("id".equalsIgnoreCase(columnName)){
                if("bigint".equalsIgnoreCase(columnType)){
                    PropertiesUtils.addProperties("idAutoIncrement","true");
                }else{
                    PropertiesUtils.addProperties("idAutoIncrement","false");
                }
            }
            vo=new TableField(columnName, columnComment, columnType);
            list.add(vo);
        }
        return list;
    }


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/5/15 0015 下午 6:12
     * @Description: 根据数据库字段类型返回对应的Java类型
     */
    public static String getJavaTypeByDbType(String dbType){
        String javaType=null;
        if("varchar".equalsIgnoreCase(dbType) || "char".equalsIgnoreCase(dbType)|| "text".equalsIgnoreCase(dbType)){
            javaType="String";
        }else if("int".equalsIgnoreCase(dbType) || "bit".equalsIgnoreCase(dbType) || "tinyint".equalsIgnoreCase(dbType)){
            javaType="Integer";
        }else if("datetime".equalsIgnoreCase(dbType) || "date".equalsIgnoreCase(dbType)){
            javaType="Date";
        }else if("bigint".equalsIgnoreCase(dbType) || "float".equalsIgnoreCase(dbType)){
            javaType="Long";
        }else if("decimal".equalsIgnoreCase(dbType)){
            javaType="BigDecimal";
        }else if("double".equalsIgnoreCase(dbType)){
            javaType="Double";
        }else{
            javaType="该类型没有添加，请在getJavaTypeByDbType里添加。";
        }
        return javaType;
    }

    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/5/23 0023 下午 6:31
     * @Description:设置哪些字段是查询字段，哪些是修改字段
     */
    public static TableInfo getTableInfo(String queryField, String updateField, String itemField, GlobalProperty globalProperty) {
        TableInfo tableInfo=new TableInfo();
        tableInfo.setTableName(globalProperty.getTableName());
        tableInfo.setEntityName(globalProperty.getEntityName());
        tableInfo.setTableComment(globalProperty.getTableComment());
        DbTools.processTable(tableInfo);
        /**
         * 循环fieldList，如果TableFieldVo字段的名称包含在上面3个中，设置对应的值为true
         */
        List<TableField> fields= new ArrayList<>();
        try {
            fields=DbTools.getTableField(globalProperty);
            tableInfo.setFields(fields);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<TableField> queryFields=new ArrayList<>();
        List<TableField> updateFields=new ArrayList<>();
        List<TableField> itemFields=new ArrayList<>();
        if(null != fields && fields.size()>0){
            TableField tableField=null;
            String propertyName = null;
            for (int i = 0; i < fields.size(); i++) {
                tableField=fields.get(i);
                propertyName=tableField.getPropertyName();
                if(queryField.contains(propertyName)){
                    queryFields.add(tableField);
                }
                if(updateField.contains(propertyName)){
                    updateFields.add(tableField);
                }
                if(itemField.contains(propertyName)){
                    itemFields.add(tableField);
                }
            }
        }
        tableInfo.setQueryFields(queryFields);
        tableInfo.setUpdateFields(updateFields);
        tableInfo.setItemFields(itemFields);
        return tableInfo;
    }



}
