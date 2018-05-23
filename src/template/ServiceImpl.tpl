package ${package}.write;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

import ${entityImport};
import com.code.dao.read.Read${classdef}Mapper;
import com.code.dao.write.${classdef}Mapper;

/**
 * <p>${tableComment}Service class。</p>
 *
 * @author majian 自动生成器
 * @version 1.00
 */
@Service
@CacheConfig(cacheNames="${classdef}Cache") 
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class ${classdef}Service {

   
    @Autowired
	private ${classdef}Mapper WriteMapper;

    @Autowired
	private Read${classdef}Mapper ReadMapper;
 

	@CachePut(key="#p0.ID")  
	@CacheEvict(value = "Read${classdef}Cache",allEntries = true)
	public ${classdef} insert(${classdef} obj){
		WriteMapper.insert(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="#p0.ID")  
	@CacheEvict(value = "Read${classdef}Cache",allEntries = true)
	public ${classdef} update(${classdef} obj){
		WriteMapper.update(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="#p0")  
	@CacheEvict(value = "Read${classdef}Cache",allEntries = true)
	public ${classdef} deleteById(String id){
		WriteMapper.deleteById(id);
		return ReadMapper.findById(id);
	}

	@CachePut(key="#p0")  
	@CacheEvict(value = "Read${classdef}Cache",allEntries = true)
	public ${classdef} recoverByID(String id){
		WriteMapper.recoverByID(id);
		return ReadMapper.findById(id);
	}

	@CacheEvict(value = {"Read${classdef}Cache","${classdef}Cache"},allEntries = true)
	public int deleteByCondition(Map<String,Object> queryMap){
		return WriteMapper.deleteByCondition(queryMap);
	}

	@CacheEvict(value = {"Read${classdef}Cache","${classdef}Cache"},allEntries = true)
	public int recoverByCondition(Map<String,Object> queryMap){
		return WriteMapper.recoverByCondition(queryMap);
	}

}
