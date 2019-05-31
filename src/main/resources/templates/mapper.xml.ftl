<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">


    <!-- 多条件查询${table.tableComment}对象 -->
    <select id="select${entity}ListBySql" parameterType="${package.Entity}.${entity}"
            resultType="${package.Entity}.${entity}">
        select
    <#list table.fields as field>
        <#if field_index gte 0>
            <#if field_index != tableFieldSize>
                ${field.columnName} ${field.propertyName},
            <#else>
                ${field.columnName} ${field.propertyName}
            </#if>
        </#if>
    </#list>
        from ${tableName} where 1=1
    <#list table.queryFields as field>
        <#if field_index gte 0 >
            <#if field_index != queryFieldSize>
            <if test="null != ${field.propertyName}">
                and ${field.columnName} = @{${field.propertyName}},
            </if>
            <#else>
            <if test="null != ${field.propertyName}">
                and ${field.columnName} = @{${field.propertyName}}
            </if>
            </#if>
        </#if>
    </#list>
    </select>

    <!-- 多条件查询${table.tableComment}Vo对象 -->
    <select id="select${entity}VoListBySql" parameterType="${package.Entity}.${entity}"
            resultType="${package.Vo}.${vo}">
        select
        <#list table.fields as field>
            ${field.columnName} ${field.propertyName},
        </#list>
        <#list table.itemFields as field>
            <#if field_index != itemFieldSize>
            ${field.columnName} ${field.propertyName},
            <#else>
            ${field.columnName} ${field.propertyName}
            </#if>
        </#list>
        from ${tableName} where 1=1
    <#list table.queryFields as field>
            <#if field_index != queryFieldSize>
                <if test="null != ${field.propertyName}">
                    and ${field.columnName} = @{${field.propertyName}},
                </if>
            <#else>
                <if test="null != ${field.propertyName}">
                    and ${field.columnName} = @{${field.propertyName}}
                </if>
            </#if>
    </#list>
    </select>


    <!-- 添加${table.tableComment}对象 -->
    <insert id="insert${entity}BySql" parameterType="${package.Entity}.${entity}"
            useGeneratedKeys="true" keyProperty="id" >
            INSERT INTO ${tableName} (
        <#list table.fields as field>
            <#if field_index gte 0 >
                <#if field_index != tableFieldSize>
                    ${field.columnName},
                <#else>
                    ${field.columnName}
                </#if>
            </#if>
        </#list>
             ) VALUES (
         <#list table.fields as field>
             <#if field_index gte 0 >
                 <#if field_index != tableFieldSize>
                     @{${field.propertyName}},
                 <#else>
                     @{${field.propertyName}}
                 </#if>
             </#if>
         </#list>
              )

    </insert>


    <!-- 修改${table.tableComment}对象 -->
    <update id="update${entity}BySql" parameterType="${package.Entity}.${entity}">
            UPDATE ${tableName} SET
        <#list table.updateFields as field>
            <#if field_index gte 0 >
                <if test="null != ${field.propertyName}">
                ${field.columnName} = @{${field.propertyName}},
                </if>
            </#if>
        </#list>
             update_by = @{updateBy},
             update_time = @{updateTime}
             WHERE id = @{id}
    </update>




</mapper>
