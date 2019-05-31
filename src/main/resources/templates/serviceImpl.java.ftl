package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Vo}.${vo};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;


/**
 * @author ${author}
 * @since ${date}
 * @Description: ${table.tableComment!} service 接口实现类
 */
@Service
public class ${table.serviceImplName}  implements ${table.serviceName} {

     @Autowired
     private ${table.mapperName} mapper;


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 新增${table.tableComment}对象。
    */
    public int insert${entity}(${entity} entity){
        entity.setCreateBy("");
        return mapper.insert(entity);
    }


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 删除${table.tableComment}对象。
    */
    public int delete${entity}ById(String id){
        QueryWrapper<${entity}> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return mapper.delete(queryWrapper);
    }


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 修改${table.tableComment}对象。
    */
    public int update${entity}(${entity} entity){
        UpdateWrapper<${entity}> uw = new UpdateWrapper<>();
<#list table.updateFields as field>
    <#if field_index gte 0 >
        <#if field_index != updateFieldSize>
        if(null != entity.get${field.getterName}()){
            uw.set("${field.columnName}",entity.get${field.getterName}());
        }
        <#else>
        uw.eq("id",entity.getId());
        </#if>
    </#if>
</#list>
        return mapper.update(new ${entity}(), uw);
    }

    /**
    * @author ${author}
    * @since ${date}
    * @Description: 分页查询${table.tableComment}对象集合。
    */
    public PageInfo<${entity}> select${entity}Page(Integer current,Integer pageSize,
<#list table.queryFields as field>
    <#if field_index gte 0 >
        <#if field_index != queryFieldSize>
            String ${field.propertyName},<#else>String ${field.propertyName}){
        </#if>
    </#if>
</#list>
        PageHelper.startPage(current,pageSize);
        List<${entity}> list=select${entity}List(
<#list table.queryFields as field>
    <#if field_index gte 0 >
        <#if field_index != queryFieldSize>
            ${field.propertyName},<#else>${field.propertyName});
        </#if>
    </#if>
</#list>
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }



  /**
    * @author ${author}
    * @since ${date}
    * @Description: 查询${table.tableComment}对象集合。
    */
    public List<${entity}> select${entity}List(
<#list table.queryFields as field>
    <#if field_index gte 0 >
        <#if field_index != queryFieldSize>
            String ${field.propertyName},<#else>String ${field.propertyName}){
        </#if>
    </#if>
</#list>
        QueryWrapper<${entity}> queryWrapper=new QueryWrapper<>();
<#list table.queryFields as field>
    <#if field_index gte 0 >
        if(StringUtils.isNotEmpty(${field.propertyName})){
            queryWrapper.eq("${field.columnName}",${field.propertyName});
        }
    </#if>
</#list>
        List<${entity}> list=mapper.selectList(queryWrapper);
        return list;
}

   /**
    * @author ${author}
    * @since ${date}
    * @Description: 查询${table.tableComment}Vo对象集合。
    */
    public List<${vo}> select${entity}VoListBySql(<#list table.queryFields as field><#if field_index != queryFieldSize>String ${field.propertyName},<#else>String ${field.propertyName}){
    </#if>
</#list>
        ${entity} entity=new ${entity}();
    <#list table.queryFields as field>
        entity.set${field.getterName}(${field.propertyName});
    </#list>
        List<${vo}> voList=mapper.select${entity}VoListBySql(entity);
        if(null != voList && voList.size()>0){
            ${vo} vo;
            for (int i = 0; i < voList.size(); i++) {
                vo=voList.get(i);
            <#list table.itemFields as field>
                <#if field.columnComment!?length gt 0>
                vo.set${field.getterName}Name("");
                </#if>
            </#list>
            }
        }
        return voList;
}




    /**
    * @author ${author}
    * @since ${date}
    * @Description: 根据ID查询${table.tableComment}对象。
    */
    public ${entity} select${entity}ById(String id){
        ${entity} result=mapper.selectById(id);
        return result;
    }


    /**
     * @author ${author}
     * @since ${date}
     * @Description: 多参数查询${table.tableComment}对象。
     */
    public ${entity} select${entity}(${entity} entity){
        QueryWrapper<${entity}> queryWrapper=new QueryWrapper<>();
    <#list table.queryFields as field>
        <#if field_index gte 0 >
        if(null != entity.get${field.getterName}()){
            queryWrapper.eq("${field.columnName}",entity.get${field.getterName}());
        }
        </#if>
    </#list>
        ${entity} result=mapper.selectOne(queryWrapper);
        return result;
    }




}
