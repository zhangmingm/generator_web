package ${package.Controller};


import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import cn.sstech.framework.common.annontation.ResponseResult;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ${author}
 * @since ${date}
 * @Description: ${table.tableComment!} controller
 */
@ResponseResult
@RestController
@Api(description = "${table.tableComment} controller")
public class ${table.controllerName} {

     @Autowired
     private ${table.serviceName} service;


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 分页查询${table.tableComment}对象集合。
    */
    @GetMapping("/${entityInstanceName}/select${entity}Page")
    @ApiOperation(notes = "分页查询${table.tableComment}对象集合", value = "分页查询${table.tableComment}对象集合")
    public PageInfo<${entity}> select${entity}Page(
                @RequestHeader(value = "X-Token") String token,
                @ApiParam(value="当前页") @RequestParam(value="current",defaultValue = "1") Integer current,
                @ApiParam(value="每页页数") @RequestParam(value="pageSize",defaultValue = "10")Integer pageSize,
        <#list table.queryFields as field>
            <#if field_index gte 0 >
                <#if field_index != queryFieldSize>
                @ApiParam(value="${field.columnComment}") @RequestParam(required = false) String ${field.propertyName},
                <#else>
                @ApiParam(value="${field.columnComment}") @RequestParam(required = false) String ${field.propertyName}){
                </#if>
            </#if>
        </#list>

         return service.select${entity}Page(current,pageSize,
                                <#list table.queryFields as field><#if field_index gte 0 >
                                        <#if field_index != queryFieldSize>
                                        ${field.propertyName},<#else>${field.propertyName});
                                        </#if>
                                    </#if>
                                </#list>
    }


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 新增${table.tableComment}对象。
    */
    @PostMapping("/${entityInstanceName}/insert${entity}")
    @ApiOperation(value = "新增${table.tableComment}对象", notes = "新增${table.tableComment}对象")
    public String insert${entity}(
        @RequestHeader(value = "X-Token") String token,
        @ApiParam(value="${table.tableComment!}对象",required = true) @RequestBody ${entity} ${entityInstanceName}){
        service.insert${entity}(${entityInstanceName});
        return "新增${table.tableComment}对象成功";
    }


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 根据id删除${table.tableComment}对象。
    */
    @GetMapping("/${entityInstanceName}/delete${entity}ById")
    @ApiOperation(value = "根据id删除${table.tableComment}对象", notes = "根据id删除${table.tableComment}对象")
    public String delete${entity}ById(@RequestHeader(value = "X-Token") String token,
                @ApiParam(value="主键id") @RequestParam String id){
            service.delete${entity}ById(id);
            return "删除成功";
    }


   /**
    * @author ${author}
    * @since ${date}
    * @Description: 更新${entity}对象。
    */
    @PostMapping("/${entityInstanceName}/update${entity}")
    @ApiOperation(value = "更新${table.tableComment}对象", notes = "更新${table.tableComment}对象")
    public String update${entity}(@RequestHeader(value = "X-Token") String token,
                @ApiParam(value="${table.tableComment!}对象") @RequestBody  ${entity} ${entityInstanceName}){
            service.update${entity}(${entityInstanceName});
            return "修改成功";
    }

  /**
    * @author ${author}
    * @since ${date}
    * @Description: 根据id查询${table.tableComment}对象。
    */
    @GetMapping("/${entityInstanceName}/select${entity}ById")
    @ApiOperation(value = "根据id查询${table.tableComment}对象", notes = "根据id查询${table.tableComment}对象")
    public ${entity} select${entity}ById(@RequestHeader(value = "X-Token") String token,
                @ApiParam(value="主键id") @RequestParam String id){
            return service.select${entity}ById(id);
    }



}
