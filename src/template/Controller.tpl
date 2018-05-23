package com.code.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import com.code.domain.${classdef};
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.code.until.CommonStatus;
import com.code.until.CommonUntil;
import com.code.config.rabbit.RabbitUtil;
/**
 * <p>${tableComment} 控制器 Class</p>
 *
 * @author majian 自动构建脚本
 */
@Api("${classdef}")
@RestController
@RequestMapping("/${classdef}")
public class ${classdef}Controller extends BaseController {


    @GetMapping("/query${classdef}Page")
    @ApiOperation(value = "获取列表")
    public Map query${classdef}Page(int status,String search,int pageNumber, int pageSize,HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("search", search);
        if(status!=-1){
            queryMap.put("Status", status);
        }
        PageInfo<${classdef}> page = this.Read${classdef}Service.queryPage(queryMap, pageNumber, pageSize);
        returnMap.put("rows", page.getList());
        returnMap.put("total", page.getTotal());
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查看【${classdef}-query${classdef}Page】列表",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

    @PostMapping("/set${classdef}Status")
    @ApiOperation(value = "设置状态")
    public Map set${classdef}Status(String data,HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        ${classdef} temp=JSON.parseObject(data,${classdef}.class);
        String[] ids=temp.getID().split(",");
        for (String id : ids){
            if(temp.getStatus()==Integer.parseInt(CommonStatus.Status.Ectivity.getid())){
                ${classdef}Service.recoverByID(id);
            }else{
                ${classdef}Service.deleteById(id);
            }
            queryMap.clear();
        }
        returnMap.put("code",0);
        returnMap.put("message","操作成功");
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"设置【${classdef}-set${classdef}Status】状态",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

    @GetMapping("/find${classdef}/{id}")
    @ApiOperation(value = "根据编号查询内容")
    public Map find${classdef}(@PathVariable("id") String id,HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        ${classdef} temp=Read${classdef}Service.findById(id);
        if(temp!=null){
	   		returnMap.put("code",0);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取成功");
    	}else{
			returnMap.put("code",-1);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取失败");
		}
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查询【${classdef}-find${classdef}-"+id+"】内容",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }


    @PostMapping("/modify${classdef}")
    @ApiOperation(value = "修改")
    public Map modify${classdef}(String data, HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        ${classdef} temp = JSON.parseObject(data, ${classdef}.class);
        ${classdef}  obj=new ${classdef}();
        boolean isNew=false;
        if("0".equals(temp.getID())){
            isNew=true;
        }else{
            obj=Read${classdef}Service.findById(String.valueOf(temp.getID()));
            if(obj==null){
                isNew=true;
            }
        }

        <#list setFieldList as f>
        obj.set${f.name}(temp.get${f.name}());
        </#list>


        ${classdef} tempObj=null;
        if(isNew){
            obj.setID(CommonUntil.getInstance().CreateNewID());
            obj.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
            tempObj=${classdef}Service.insert(obj);
        }else{
            tempObj=${classdef}Service.update(obj);
        }
        returnMap.put("code", tempObj!=null?0:-1);
        returnMap.put("message", tempObj!=null?"修改成功":"修改失败");
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"修改【${classdef}-modify${classdef}-"+obj.getID()+"】内容",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

}
