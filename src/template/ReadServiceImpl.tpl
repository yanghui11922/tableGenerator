package ${package}.read;
import java.util.*;



import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ${entityImport};
import com.code.dao.read.Read${classdef}Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>${tableComment}Service class。</p>
 *
 * @author majian
 * @version 1.00
 */
 @Service
 @CacheConfig(cacheNames="Read${classdef}Cache") 
 @Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class Read${classdef}Service {

    @Autowired
	private Read${classdef}Mapper ReadMapper;

	@Cacheable(value = "${classdef}Cache",key="'${classdef}_'+#p0") 
	public ${classdef} findById(String id){
		return ReadMapper.findById(id);
	}

	@Cacheable(keyGenerator = "keyGenerator")
	public List<${classdef}> query(Map<String,Object> queryMap){
		return ReadMapper.query(queryMap);
	}

	@Cacheable(keyGenerator = "keyGenerator")
	public int queryCount(Map<String,Object> queryMap){
		return ReadMapper.queryCount(queryMap);
	}

	@Cacheable(keyGenerator = "keyGenerator")
	public PageInfo<${classdef}> queryPage(Map<String,Object> queryMap, int pageNum, int pageSize){
		Page<${classdef}> page = PageHelper.startPage(pageNum, pageSize);
		if(pageSize==0){//当pageSize=0时查询全部的东西
			page.setPageSizeZero(true);
		}
		page.setOrderBy("${classdef}_CreateTime desc");
		ReadMapper.query(queryMap);
		return page.toPageInfo();
	}

}
