package com.code.service.write;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

import com.code.domain.Role1;
import com.code.dao.read.ReadRole1Mapper;
import com.code.dao.write.Role1Mapper;

/**
 * <p>Service class。</p>
 *
 * @author majian 自动生成器
 * @version 1.00
 */
@Service
@CacheConfig(cacheNames="Role1Cache") 
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class Role1Service {

   
    @Autowired
	private Role1Mapper WriteMapper;

    @Autowired
	private ReadRole1Mapper ReadMapper;
 

	@CachePut(key="'Role1_'+#p0.ID")  
	@CacheEvict(value = "ReadRole1Cache",allEntries = true)
	public Role1 insert(Role1 obj){
		WriteMapper.insert(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Role1_'+#p0.ID")  
	@CacheEvict(value = "ReadRole1Cache",allEntries = true)
	public Role1 update(Role1 obj){
		WriteMapper.update(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Role1_'+#p0")  
	@CacheEvict(value = "ReadRole1Cache",allEntries = true)
	public Role1 deleteById(String id){
		WriteMapper.deleteById(id);
		return ReadMapper.findById(id);
	}

	@CachePut(key="'Role1_'+#p0")  
	@CacheEvict(value = "ReadRole1Cache",allEntries = true)
	public Role1 recoverByID(String id){
		WriteMapper.recoverByID(id);
		return ReadMapper.findById(id);
	}

	@CacheEvict(value = {"ReadRole1Cache","Role1Cache"},allEntries = true)
	public int deleteByCondition(Map<String,Object> queryMap){
		return WriteMapper.deleteByCondition(queryMap);
	}

	@CacheEvict(value = {"ReadRole1Cache","Role1Cache"},allEntries = true)
	public int recoverByCondition(Map<String,Object> queryMap){
		return WriteMapper.recoverByCondition(queryMap);
	}

}
