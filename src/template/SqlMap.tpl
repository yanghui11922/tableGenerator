<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${daoPackage}.write.${tblName?cap_first}Mapper">
	<sql id="sql">
		${selectSql}
	</sql>
	<sql id="where">
		1=1 
		${conditionSql}
	</sql>

	<!-- 插入数据 -->
	<insert id="insert" parameterType="${package}.${tblName?cap_first}"  useGeneratedKeys="true">
		insert into ${tblNameSql}( <include refid="sql"></include> )
		values(
				${insertValueSql}
				)
	</insert>
	<update id="update" parameterType="${package}.${tblName?cap_first}">
		update ${tblNameSql} set ${updateSql}
		<where>
			${conditionID} 
		</where>
	</update>


	<!-- 根据编号删除-->
	<update id="deleteById" parameterType="${package}.${tblName?cap_first}">
		update ${tblNameSql} set ${conditionStatus}=88 where ${conditionSqlByID} 
	</update>

	<!-- 根据编号恢复-->
	<update id="recoverByID" parameterType="${package}.${tblName?cap_first}">
		update ${tblNameSql} set ${conditionStatus}=1 where ${conditionSqlByID} 
	</update>

	<!-- 根据条件删除-->
	<update id="deleteByCondition" parameterType="${package}.${tblName?cap_first}">
		update ${tblNameSql} set ${conditionStatus}=88 
		<where>
			<include refid="where"></include>
		</where>
	</update>

	<!-- 根据条件恢复-->
	<update id="recoverByCondition" parameterType="${package}.${tblName?cap_first}">
		update ${tblNameSql} set ${conditionStatus}=1 
		<where>
			<include refid="where"></include>
		</where>
	</update>


</mapper>
