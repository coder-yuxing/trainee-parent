<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${namespace}">
    <resultMap id="BaseResultMap" type="${beanClassName}">
    <#if resultMap ?? && (resultMap ? size > 0)>
    <#list resultMap as column>
    <#if column.columnName == "id">
        <id column="${column.columnName}" property="${column.fieldName}"/>
    <#else>
        <result column="${column.columnName}" property="${column.fieldName}"/>
    </#if>
    </#list>
    </#if>
    </resultMap>

    <sql id="Base_Column_List">
    <#if columnNameList ?? && (columnNameList ? length > 0)>
        ${columnNameList}
    </#if>
    </sql>

    <select id="getById" resultMap="BaseResultMap" parameterType="${idType}">
        SELECT
            <include refid="Base_Column_List"/>
        FROM
            ${tableName}
        WHERE
            ${idColumnName} = ${r"#{"}id${r"}"}
    </select>

    <delete id="deleteById" parameterType="${idType}">
        DELETE FROM
            ${tableName}
        WHERE
            ${idColumnName} = ${r"#{"}id${r"}"}
    </delete>

    <insert id="insert" parameterType="${beanClassName}" useGeneratedKeys="true" keyProperty="${idColumnName}">
        insert into ${tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#if resultMap ?? && (resultMap ? size > 0)>
        <#list resultMap as column>
            <if test="${column.fieldName} != null">
                ${column.columnName},
            </if>
        </#list>
        </#if>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
        <#if resultMap ?? && (resultMap ? size > 0)>
        <#list resultMap as column>
            <if test="${column.fieldName} != null">
                ${r"#{"}${column.fieldName}${r"}"},
            </if>
        </#list>
        </#if>
        </trim>
    </insert>

    <update id="updateById" parameterType="${beanClassName}">
        UPDATE ${tableName}
        <set>
        <#if resultMap ?? && (resultMap ? size > 0)>
        <#list resultMap as column>
            <if test="${column.fieldName} != null">
                ${column.columnName} = ${r"#{"}${column.fieldName}${r"}"},
            </if>
        </#list>
        </#if>
        </set>
        WHERE
            ${idColumnName} = ${r"#{"}id${r"}"}
    </update>

</mapper>
