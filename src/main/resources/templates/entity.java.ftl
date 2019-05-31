package ${package.Entity};

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.math.BigDecimal;
<#if idAutoIncrement>
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
</#if>

/**
* @author ${author}
* @since ${date}
* @Description: ${table.tableComment!} 实体对象
* Entity文件排除非表中字段,使用 transient 修饰,如 [private transient String noColumn;]
* 或 使用 TableField 注解[@TableField(exist=false)]
*/
<#if !autoGetter>
@Data
</#if>
@ApiModel(value="${entity}对象", description="${table.tableComment!}")
public class ${entity}{

<#if idAutoIncrement>
    @TableId(value = "id",type = IdType.AUTO)
</#if>
<#list table.fields as field>
    <#if field.columnComment!?length gt 0>
    @ApiModelProperty(value = "${field.columnComment}")
    private ${field.columnType} ${field.propertyName};

    </#if>
</#list>


// 以下是需要将对应的code转换为名称的字段，使用 transient 修饰
<#list table.itemFields as field>
    <#if field.columnComment!?length gt 0>
    @ApiModelProperty(value = "${field.columnComment}名称")
    private transient String ${field.propertyName}Name;

    </#if>
</#list>


<#-- autoGetter为true 生成Getter Setter 方法-->
<#if autoGetter>
    <#list table.fields as field>
    public ${field.columnType} get${field.getterName}() {
        return ${field.propertyName};
    }

    public void set${field.getterName}(${field.columnType} ${field.propertyName}){
        this.${field.propertyName} = ${field.propertyName};
    }
    </#list>

    <#list table.itemFields as field>
    public String get${field.getterName}Name() {
        return ${field.propertyName}Name;
    }

    public void set${field.getterName}Name(String ${field.propertyName}Name){
        this.${field.propertyName}Name = ${field.propertyName}Name;
    }
    </#list>

</#if>




}
