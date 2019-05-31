package ${package.Vo};

import ${package.Entity}.${entity};
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.math.BigDecimal;

/**
* @author ${author}
* @since ${date}
* @Description: ${table.tableComment!} vo对象
* 与 ${entity} 字段相同，为什么要存在呢？如果是级联查询，在这里定义关联查询的对象！
*/
<#if !autoGetter>
@Data
</#if>
@ApiModel(value="${vo}对象", description="${table.tableComment!}vo对象")
public class ${vo} extends ${entity}{

<#list table.itemFields as field>
    <#if field.columnComment!?length gt 0>
    @ApiModelProperty(value = "${field.columnComment}名称")
    private String ${field.propertyName}Name;

    </#if>
</#list>


    /*
    @ApiModelProperty(value = "我是关联对象")
    private List<${entity}> entityList;
    */

<#-- autoGetter为true 生成Getter Setter 方法-->
<#if autoGetter>
    <#list table.itemFields as field>
    public String get${field.getterName}(){
        return ${field.propertyName}Name;
    }

    public void set${field.getterName}(String ${field.propertyName}Name){
        this.${field.propertyName}Name = ${field.propertyName}Name;
    }
    </#list>
</#if>


}
