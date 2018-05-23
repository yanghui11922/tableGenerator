<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${daoPackage}.read.Read${tblName?cap_first}Mapper">
	<!-- 返回的map -->
	<resultMap id="${tblName}" type="${package}.${tblName?cap_first}">
	<#list rsltSetColumList as rsltSetColum>
		<result property="${rsltSetColum.value}" column="${rsltSetColum.key}" />
	</#list>
	</resultMap>
	<!-- sql查询语句-->
	<sql id="sql">
		${selectSql}
	</sql>
	<!-- sql查询条件-->
	<sql id="where">
		1=1 
		${conditionSql}
		<!-- 这边要数据自己处理 -->
		<if test="search != null">
			and (
				${conditionSearchSql}
				1 != 1
			)
		</if>
	</sql>
	<!-- 根据ID 查找-->
	<select id="findById" resultMap="${tblName}" parameterType="long">
		SELECT
		<include refid="sql"></include>
		FROM ${tblNameSql}
		<where>
			${conditionSqlByID} 
		</where>
	</select>
	<!-- 根据条件 查找-->
	<select id="query"  resultMap="${tblName}"
		parameterType="java.util.Map">
		SELECT
		<include refid="sql"></include>
		FROM ${tblNameSql}
		<where>
			<include refid="where"></include>
		</where>
	
	</select>
	<!-- 根据条件总数-->
	<select id="queryCount" parameterType="map" resultType="int">
		SELECT
			Count(1)
			FROM ${tblNameSql}
			<where>
				<include refid="where"></include>
			</where>
	</select>



</mapper>
