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

import com.code.domain.Check;
import com.code.dao.read.ReadCheckMapper;
import com.code.dao.write.CheckMapper;

/**
 * <p>Service class。</p>
 *
 * @author majian 自动生成器
 * @version 1.00
 */
@Service
@CacheConfig(cacheNames="CheckCache") 
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class CheckService {

   
    @Autowired
	private CheckMapper WriteMapper;

    @Autowired
	private ReadCheckMapper ReadMapper;
 

	@CachePut(key="'Check_'+#p0.ID")  
	@CacheEvict(value = "ReadCheckCache",allEntries = true)
	public Check insert(Check obj){
		WriteMapper.insert(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Check_'+#p0.ID")  
	@CacheEvict(value = "ReadCheckCache",allEntries = true)
	public Check update(Check obj){
		WriteMapper.update(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Check_'+#p0")  
	@CacheEvict(value = "ReadCheckCache",allEntries = true)
	public Check deleteById(String id){
		WriteMapper.deleteById(id);
		return ReadMapper.findById(id);
	}

	@CachePut(key="'Check_'+#p0")  
	@CacheEvict(value = "ReadCheckCache",allEntries = true)
	public Check recoverByID(String id){
		WriteMapper.recoverByID(id);
		return ReadMapper.findById(id);
	}

	@CacheEvict(value = {"ReadCheckCache","CheckCache"},allEntries = true)
	public int deleteByCondition(Map<String,Object> queryMap){
		return WriteMapper.deleteByCondition(queryMap);
	}

	@CacheEvict(value = {"ReadCheckCache","CheckCache"},allEntries = true)
	public int recoverByCondition(Map<String,Object> queryMap){
		return WriteMapper.recoverByCondition(queryMap);
	}

}
