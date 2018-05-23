package generator.tools;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import generator.Globles;
import generator.bean.DomainDefineBean;
import generator.bean.TableInfoBean;
import generator.util.CommentUtil;
import generator.util.CommonUtil;
import generator.util.DateUtil;
import generator.util.FileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

public class addListGenerator implements Globles {

    public static void genaratorEntity(Configuration cfg,
        Entry<String, List<TableInfoBean>> tableInfo) throws IOException,
        TemplateException {
        BufferedWriter writer = null;

        try {
        	  String _package=CommonUtil.getHtmlListOutPutPath();
            String path =_package  + "/add"
                + getDomainName(tableInfo.getKey()) + ".html";

            boolean fileb=FileUtil.mkdir(path);
            
            writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path), CommonUtil.getSourceEncode()));

            Map<String, Object> rootMap = new HashMap<String, Object>();

            setRootMapForDao(rootMap, tableInfo);

            Template tpl = cfg.getTemplate("addHtmlList.tpl");
            tpl.setEncoding("UTF-8");

            tpl.process(rootMap, writer);
            //FileUtils.writeStringToFile(new File(_package + "/package.html"), "entity", "UTF-8");
        } finally {
            if (null != writer) {
                writer.flush();
                writer.close();
            }
        }

    }

    private static void setRootMapForDao(Map<String, Object> rootMap,
        Entry<String, List<TableInfoBean>> tableInfo) {

        rootMap.put("date", DateUtil.getStringToday());
        rootMap.put("package", CommonUtil.getEntityPackage());
        rootMap.put("parentImport", CommonUtil.getEntityParent());
        rootMap.put("classdef", getDomainName(tableInfo.getKey()));
        rootMap.put("extends", CommonUtil.getExtends(CommonUtil
            .getEntityParent()));
        rootMap.put("tableComment", CommentUtil.getTableComment(tableInfo.getKey()));

        List<DomainDefineBean> fieldList = new ArrayList<DomainDefineBean>();
        List<DomainDefineBean> columnList = new ArrayList<DomainDefineBean>();
        List<String> importList = new ArrayList<String>();

        DomainDefineBean ddb = null;
        DomainDefineBean column = null;
        List<String> domainColumnSkip = Arrays.asList(CommonUtil.getDomainColumnSkip().split(","));
        String importSkip = CommonUtil.getImportSkip();
        for (TableInfoBean tableInfoBean : tableInfo.getValue()) {

            if (domainColumnSkip.contains(tableInfoBean.getColumnName())) {
                continue;
            }

            ddb = new DomainDefineBean();

            String importValue = CommonUtil.getJavaMappingType(tableInfoBean
                .getTypeName());

//            if (!importList.contains(importValue)
//                && !importSkip.contains(importValue)) {
//                importList.add(importValue);
//            }

//            ddb.setType(CommonUtil.getType(importValue));
            if(tableInfoBean.getColumnName().split("_")[1].equals("ID")) {
            		ddb.setType("String");
            }else {
            		ddb.setType(CommonUtil.getJavaTypeBySql(tableInfoBean.getTypeName()));
            }
           
            ddb.setName(tableInfoBean.getColumnName().split("_")[1]);
            ddb.setMethodAppend(CommonUtil.getOutputColumnName(tableInfoBean
                .getColumnName(), true));
            ddb.setComment(tableInfoBean.getComment());
            ddb.setXame(CommonUtil.getXName(tableInfoBean.getColumnName()));
            fieldList.add(ddb);
            
            column=new DomainDefineBean();
            column.setType(CommonUtil.getJavaTypeBySql(tableInfoBean.getTypeName()));
            column.setName(tableInfoBean.getColumnName().split("_")[1]);
            column.setComment(CommentUtil.getColumnComent(tableInfo.getKey(), tableInfoBean
                    .getColumnName()));
            columnList.add(column);
        }

        rootMap.put("fieldList", fieldList);
        rootMap.put("importList", importList);
        rootMap.put("columnList", columnList);
        List<DomainDefineBean> temp = new ArrayList<DomainDefineBean>();
        temp=fieldList;
        temp.remove(0);
        
    
        List<Integer> str=new ArrayList<>();
        for(int i=0;i<temp.size();i++) {
        		if("iD".equals(temp.get(i).getXame())||temp.get(i).getXame().contains("Time")) {
        			str.add(i);
        		}
        }
        for(int i=str.size()-1;i>=0;i--) {
        		temp.remove(str.get(i).intValue());
        }
        rootMap.put("xName",temp);
    }

    private static String getDomainName(String tableName) {
    	String tableext="";
    	for(int i=1;i<tableName.split("_").length;i++){
    		tableext=tableext+(tableName.split("_")[i].substring(0, 1).toUpperCase() + tableName.split("_")[i].substring(1));
    	}
        return CommonUtil.getOutputColumnName(tableext, true);
    }
}
