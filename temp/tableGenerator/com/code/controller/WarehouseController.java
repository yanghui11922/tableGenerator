<<<<<<< HEAD:temp/tableGenerator/com/code/controller/WarehouseController.java
package com.code.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import com.code.domain.Warehouse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.code.until.CommonStatus;
import com.code.until.CommonUntil;
import com.code.config.rabbit.RabbitUtil;
/**
 * <p> 控制器 Class</p>
 *
 * @author majian 自动构建脚本
 */
@Api("Warehouse")
@RestController
@RequestMapping("/Warehouse")
public class WarehouseController extends BaseController {


    @GetMapping("/queryWarehousePage")
    @ApiOperation(value = "获取列表")
    public Map queryWarehousePage(int status,String search,int pageNumber, int pageSize,HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("search", search);
        if(status!=-1){
            queryMap.put("Status", status);
        }
        PageInfo<Warehouse> page = this.ReadWarehouseService.queryPage(queryMap, pageNumber, pageSize);
        returnMap.put("rows", page.getList());
        returnMap.put("total", page.getTotal());
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查看【Warehouse-queryWarehousePage】列表",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

    @PostMapping("/setWarehouseStatus")
    @ApiOperation(value = "设置状态")
    public Map setWarehouseStatus(String data,HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Warehouse temp=JSON.parseObject(data,Warehouse.class);
        String[] ids=temp.getID().split(",");
        for (String id : ids){
            if(temp.getStatus()==Integer.parseInt(CommonStatus.Status.Ectivity.getid())){
                WarehouseService.recoverByID(id);
            }else{
                WarehouseService.deleteById(id);
            }
        }
        queryMap.clear();
        returnMap.put("code",0);
        returnMap.put("message","操作成功");
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"设置【Warehouse-setWarehouseStatus】状态",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

    @GetMapping("/findWarehouse/{id}")
    @ApiOperation(value = "根据编号查询内容")
    public Map findWarehouse(@PathVariable("id") String id,HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Warehouse temp=ReadWarehouseService.findById(id);
        if(temp!=null){
	   		returnMap.put("code",0);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取成功");
    	}else{
			returnMap.put("code",-1);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取失败");
		}
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查询【Warehouse-findWarehouse-"+id+"】内容",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }


    @PostMapping("/modifyWarehouse")
    @ApiOperation(value = "修改")
    public Map modifyWarehouse(String data, HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Warehouse temp = JSON.parseObject(data, Warehouse.class);
        Warehouse  obj=new Warehouse();
        boolean isNew=false;
        if("0".equals(temp.getID())){
            isNew=true;
        }else{
            obj=ReadWarehouseService.findById(String.valueOf(temp.getID()));
            if(obj==null){
                isNew=true;
            }
        }

        obj.setCreateTime(temp.getCreateTime());
        obj.setStatus(temp.getStatus());
        obj.setName(temp.getName());
        obj.setCode(temp.getCode());
        obj.setAddress(temp.getAddress());
        obj.setMemo(temp.getMemo());
        obj.setBaseInfoID(temp.getBaseInfoID());


        Warehouse tempObj=null;
        if(isNew){
            obj.setID(CommonUntil.getInstance().CreateNewID());
            obj.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
            tempObj=WarehouseService.insert(obj);
        }else{
            tempObj=WarehouseService.update(obj);
        }
        returnMap.put("code", tempObj!=null?0:-1);
        returnMap.put("message", tempObj!=null?"操作成功":"操作失败");
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"修改【Warehouse-modifyWarehouse-"+obj.getID()+"】内容",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

}
=======
package com.code.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import com.code.domain.Check;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.code.until.CommonStatus;
import com.code.until.CommonUntil;
import com.code.config.rabbit.RabbitUtil;
/**
 * <p> 控制器 Class</p>
 *
 * @author majian 自动构建脚本
 */
@Api("Check")
@RestController
@RequestMapping("/Check")
public class CheckController extends BaseController {


    @GetMapping("/queryCheckPage")
    @ApiOperation(value = "获取列表")
    public Map queryCheckPage(int status,String search,int pageNumber, int pageSize,HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("search", search);
        if(status!=-1){
            queryMap.put("Status", status);
        }
        PageInfo<Check> page = this.ReadCheckService.queryPage(queryMap, pageNumber, pageSize);
        returnMap.put("rows", page.getList());
        returnMap.put("total", page.getTotal());
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查看【Check-queryCheckPage】列表",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

    @PostMapping("/setCheckStatus")
    @ApiOperation(value = "设置状态")
    public Map setCheckStatus(String data,HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Check temp=JSON.parseObject(data,Check.class);
        String[] ids=temp.getID().split(",");
        for (String id : ids){
            if(temp.getStatus()==Integer.parseInt(CommonStatus.Status.Ectivity.getid())){
                CheckService.recoverByID(id);
            }else{
                CheckService.deleteById(id);
            }
        }
        queryMap.clear();
        returnMap.put("code",0);
        returnMap.put("message","操作成功");
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"设置【Check-setCheckStatus】状态",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

    @GetMapping("/findCheck/{id}")
    @ApiOperation(value = "根据编号查询内容")
    public Map findCheck(@PathVariable("id") String id,HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Check temp=ReadCheckService.findById(id);
        if(temp!=null){
	   		returnMap.put("code",0);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取成功");
    	}else{
			returnMap.put("code",-1);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取失败");
		}
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查询【Check-findCheck-"+id+"】内容",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }


    @PostMapping("/modifyCheck")
    @ApiOperation(value = "修改")
    public Map modifyCheck(String data, HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Check temp = JSON.parseObject(data, Check.class);
        Check  obj=new Check();
        boolean isNew=false;
        if("0".equals(temp.getID())){
            isNew=true;
        }else{
            obj=ReadCheckService.findById(String.valueOf(temp.getID()));
            if(obj==null){
                isNew=true;
            }
        }

        obj.setCreateTime(temp.getCreateTime());
        obj.setStatus(temp.getStatus());
        obj.setAdminID(temp.getAdminID());
        obj.setAdministrator(temp.getAdministrator());
        obj.setWarehouseID(temp.getWarehouseID());
        obj.setName(temp.getName());
        obj.setTime(temp.getTime());
        obj.setMemo(temp.getMemo());
        obj.setStoreID(temp.getStoreID());
        obj.setAdjustStatus(temp.getAdjustStatus());
        obj.setOperator(temp.getOperator());
        obj.setUpdater(temp.getUpdater());


        Check tempObj=null;
        if(isNew){
            obj.setID(CommonUntil.getInstance().CreateNewID());
            obj.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
            tempObj=CheckService.insert(obj);
        }else{
            tempObj=CheckService.update(obj);
        }
        returnMap.put("code", tempObj!=null?0:-1);
        returnMap.put("message", tempObj!=null?"操作成功":"操作失败");
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"修改【Check-modifyCheck-"+obj.getID()+"】内容",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

}
>>>>>>> 4cb73e4e09170ed35096374a81c0430f3cfd9240:temp/tableGenerator/com/code/controller/CheckController.java
