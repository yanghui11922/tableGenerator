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

import com.code.domain.Role2;
import com.code.dao.read.ReadRole2Mapper;
import com.code.dao.write.Role2Mapper;

/**
 * <p>Service class。</p>
 *
 * @author majian 自动生成器
 * @version 1.00
 */
@Service
@CacheConfig(cacheNames="Role2Cache") 
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class Role2Service {

   
    @Autowired
	private Role2Mapper WriteMapper;

    @Autowired
	private ReadRole2Mapper ReadMapper;
 

	@CachePut(key="'Role2_'+#p0.ID")  
	@CacheEvict(value = "ReadRole2Cache",allEntries = true)
	public Role2 insert(Role2 obj){
		WriteMapper.insert(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Role2_'+#p0.ID")  
	@CacheEvict(value = "ReadRole2Cache",allEntries = true)
	public Role2 update(Role2 obj){
		WriteMapper.update(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Role2_'+#p0")  
	@CacheEvict(value = "ReadRole2Cache",allEntries = true)
	public Role2 deleteById(String id){
		WriteMapper.deleteById(id);
		return ReadMapper.findById(id);
	}

	@CachePut(key="'Role2_'+#p0")  
	@CacheEvict(value = "ReadRole2Cache",allEntries = true)
	public Role2 recoverByID(String id){
		WriteMapper.recoverByID(id);
		return ReadMapper.findById(id);
	}

	@CacheEvict(value = {"ReadRole2Cache","Role2Cache"},allEntries = true)
	public int deleteByCondition(Map<String,Object> queryMap){
		return WriteMapper.deleteByCondition(queryMap);
	}

	@CacheEvict(value = {"ReadRole2Cache","Role2Cache"},allEntries = true)
	public int recoverByCondition(Map<String,Object> queryMap){
		return WriteMapper.recoverByCondition(queryMap);
	}

}
