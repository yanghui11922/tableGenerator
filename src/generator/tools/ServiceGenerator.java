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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

public class ServiceGenerator implements Globles {

    public static void genaratorService(Configuration cfg,
        Entry<String, List<TableInfoBean>> tableInfo) throws IOException,
        TemplateException {
        BufferedWriter writer = null;

        try {
            String _package=CommonUtil.getServiceOutPutPath(tableInfo.getKey());
            String path = _package + "/write/"
                + getServiceName(tableInfo.getKey()) + "Service.java";

            FileUtil.mkdir(path);

            writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path), CommonUtil.getSourceEncode()));

            Map<String, Object> rootMap = new HashMap<String, Object>();

            setRootMapForService(rootMap, tableInfo);

            Template tpl = cfg.getTemplate("ServiceImpl.tpl");
            tpl.setEncoding("UTF-8");

            tpl.process(rootMap, writer);
           // FileUtils.writeStringToFile(new File(_package + "/package.html"), "service", "UTF-8");
        } finally {
            if (null != writer) {
                writer.flush();
                writer.close();
            }
        }

    }

    public static void ReadgenaratorService(Configuration cfg,
            Entry<String, List<TableInfoBean>> tableInfo) throws IOException,
            TemplateException {
            BufferedWriter writer = null;

            try {
                String _package=CommonUtil.getServiceOutPutPath(tableInfo.getKey());
                String path = _package + "/read/Read"
                    + getServiceName(tableInfo.getKey()) + "Service.java";

                FileUtil.mkdir(path);

                writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(path), CommonUtil.getSourceEncode()));

                Map<String, Object> rootMap = new HashMap<String, Object>();

                setRootMapForService(rootMap, tableInfo);

                Template tpl = cfg.getTemplate("ReadServiceImpl.tpl");
                tpl.setEncoding("UTF-8");

                tpl.process(rootMap, writer);
               // FileUtils.writeStringToFile(new File(_package + "/package.html"), "service", "UTF-8");
            } finally {
                if (null != writer) {
                    writer.flush();
                    writer.close();
                }
            }

        }
    
    private static void setRootMapForService(Map<String, Object> rootMap,
        Entry<String, List<TableInfoBean>> tableInfo) {
    	String tableext="";
    	for(int i=1;i<tableInfo.getKey().split("_").length;i++){
    		tableext=tableext+(tableInfo.getKey().split("_")[i].substring(0, 1).toUpperCase() + tableInfo.getKey().split("_")[i].substring(1));
    	}
        rootMap.put("date", DateUtil.getStringToday());
        rootMap.put("package", CommonUtil.getServicePackage(tableInfo.getKey()));
        rootMap.put("parentImport", CommonUtil.getServiceParent());
        rootMap.put("entityImport", CommonUtil.getEntityPackage() + "." + CommonUtil.getOutputColumnName(tableext, true));
        rootMap.put("dtoImport", CommonUtil.getDomainPackage( tableInfo.getKey() ) + "." + CommonUtil.getOutputColumnName(tableext, true));
        rootMap.put("daoImport", CommonUtil.getDaoPackage( tableInfo.getKey() ) + "." + CommonUtil.getOutputColumnName(tableext, true));
        rootMap.put("classdef", getServiceName(tableInfo.getKey()));
        rootMap.put("extends", CommonUtil.getExtends(CommonUtil.getServiceParent()));
        rootMap.put("tableComment", CommentUtil.getTableComment(tableInfo.getKey()));
        rootMap.put("tableName", CommonUtil.getOutputColumnName(tableext, true));
        rootMap.put("dtoName", CommonUtil.getOutputColumnName(tableext, false));

        List<DomainDefineBean> fieldList = new ArrayList<DomainDefineBean>();

        DomainDefineBean ddb = null;
//        List<String> domainColumnSkip = Arrays.asList(CommonUtil.getDomainColumnSkip().split(","));
        String importSkip = CommonUtil.getImportSkip();
        for (TableInfoBean tableInfoBean : tableInfo.getValue()) {

//            if (domainColumnSkip.contains(tableInfoBean.getColumnName())) {
//                continue;
//            }

            ddb = new DomainDefineBean();

//            String importValue = CommonUtil.getJavaMappingType(tableInfoBean
//                .getTypeName());

//            if (!importList.contains(importValue)
//                && !importSkip.contains(importValue)) {
//                importList.add(importValue);
//            }

//            ddb.setType(CommonUtil.getType(importValue));
            ddb.setType(CommonUtil.getJavaTypeBySql(tableInfoBean.getTypeName()));
            ddb.setName(CommonUtil.getOutputColumnName(tableInfoBean
                .getColumnName(), false));
            ddb.setMethodAppend(CommonUtil.getOutputColumnName(tableInfoBean
                .getColumnName(), true));
            ddb.setComment(CommentUtil.getColumnComent(tableInfo.getKey(), tableInfoBean
                .getColumnName()));
            ddb.setColumn(tableInfoBean.getColumnName());

            fieldList.add(ddb);
        }
        rootMap.put("fieldList", fieldList);
    }

    public static String getServiceName(String tableName) {
    	String tableext="";
    	for(int i=1;i<tableName.split("_").length;i++){
    		tableext=tableext+(tableName.split("_")[i].substring(0, 1).toUpperCase() + tableName.split("_")[i].substring(1));
    	}
        String domainName = CommonUtil.getOutputColumnName(tableext, true);
        return domainName ;//+ SERVICE_SUFFIX;
    }
}
