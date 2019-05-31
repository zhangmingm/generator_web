package ${package.Service};

import ${package.Entity}.${entity};
import ${package.Vo}.${vo};
import java.util.List;
import com.github.pagehelper.PageInfo;
import java.util.Date;


/**
 * @author ${author}
 * @since ${date}
 * @Description: ${table.tableComment!} service 接口
 */
public interface ${table.serviceName}{


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 新增${table.tableComment}对象。
    */
    public int insert${entity}(${entity} entity);


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 删除${table.tableComment}对象。
    */
    public int delete${entity}ById(String id);


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 修改${table.tableComment}对象。
    */
    public int update${entity}(${entity} ${entityInstanceName});

   /**
    * @author ${author}
    * @since ${date}
    * @Description: 分页查询${table.tableComment}对象集合。
    */
    public PageInfo<${entity}> select${entity}Page(Integer current,Integer pageSize,
<#list table.queryFields as field>
    <#if field_index gte 0 >
        <#if field_index != queryFieldSize>
            String ${field.propertyName},
        <#else>
            String ${field.propertyName}
        </#if>
    </#if>
</#list>
    );

   /**
    * @author ${author}
    * @since ${date}
    * @Description: 查询${table.tableComment}对象集合。
    */
    public List<${entity}> select${entity}List(
<#list table.queryFields as field>
    <#if field_index gte 0 >
        <#if field_index != queryFieldSize>
            String ${field.propertyName},<#else>String ${field.propertyName}
        </#if>
    </#if>
</#list>
    );


    /**
     * @author ${author}
     * @since ${date}
     * @Description: 根据SQL查询${table.tableComment}Vo对象集合
     */
    public List<${vo}> select${entity}VoListBySql(<#list table.queryFields as field><#if field_index != queryFieldSize>String ${field.propertyName},<#else>String ${field.propertyName});
        </#if>
</#list>


   /**
     * @author ${author}
     * @since ${date}
     * @Description: 根据ID查询${table.tableComment}对象。
   */
   public ${entity} select${entity}ById(String id);


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 多参数查询${table.tableComment}对象。
    */
   public ${entity} select${entity}(${entity} entity);







}
