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

import com.code.domain.Warehouse;
import com.code.dao.read.ReadWarehouseMapper;
import com.code.dao.write.WarehouseMapper;

/**
 * <p>Service class。</p>
 *
 * @author majian 自动生成器
 * @version 1.00
 */
@Service
@CacheConfig(cacheNames="WarehouseCache") 
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class WarehouseService {

   
    @Autowired
	private WarehouseMapper WriteMapper;

    @Autowired
	private ReadWarehouseMapper ReadMapper;
 

	@CachePut(key="'Warehouse_'+#p0.ID")  
	@CacheEvict(value = "ReadWarehouseCache",allEntries = true)
	public Warehouse insert(Warehouse obj){
		WriteMapper.insert(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Warehouse_'+#p0.ID")  
	@CacheEvict(value = "ReadWarehouseCache",allEntries = true)
	public Warehouse update(Warehouse obj){
		WriteMapper.update(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Warehouse_'+#p0")  
	@CacheEvict(value = "ReadWarehouseCache",allEntries = true)
	public Warehouse deleteById(String id){
		WriteMapper.deleteById(id);
		return ReadMapper.findById(id);
	}

	@CachePut(key="'Warehouse_'+#p0")  
	@CacheEvict(value = "ReadWarehouseCache",allEntries = true)
	public Warehouse recoverByID(String id){
		WriteMapper.recoverByID(id);
		return ReadMapper.findById(id);
	}

	@CacheEvict(value = {"ReadWarehouseCache","WarehouseCache"},allEntries = true)
	public int deleteByCondition(Map<String,Object> queryMap){
		return WriteMapper.deleteByCondition(queryMap);
	}

	@CacheEvict(value = {"ReadWarehouseCache","WarehouseCache"},allEntries = true)
	public int recoverByCondition(Map<String,Object> queryMap){
		return WriteMapper.recoverByCondition(queryMap);
	}

}
