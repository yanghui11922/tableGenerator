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

public class DaoGenerator implements Globles {

    public static void genaratorDao(Configuration cfg,
        Entry<String, List<TableInfoBean>> tableInfo) throws IOException,
        TemplateException {
        BufferedWriter writer = null;

        try {
            String _package=CommonUtil.getDaoOutPutPath(tableInfo.getKey());
            String path = _package + "/write/"
                + getDaoName(tableInfo.getKey()) + "Mapper.java";

            FileUtil.mkdir(path);

            writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path), CommonUtil.getSourceEncode()));

            Map<String, Object> rootMap = new HashMap<String, Object>();

            setRootMapForDao(rootMap, tableInfo);

            Template tpl = cfg.getTemplate("DaoImpl.tpl");
            tpl.setEncoding("UTF-8");

            tpl.process(rootMap, writer);
            
          //  FileUtils.writeStringToFile(new File(_package + "/package.html"), "dao", "UTF-8");
        } finally {
            if (null != writer) {
                writer.flush();
                writer.close();
            }
        }

    }
    
    public static void ReadGenaratorDao(Configuration cfg,
            Entry<String, List<TableInfoBean>> tableInfo) throws IOException,
            TemplateException {
            BufferedWriter writer = null;

            try {
                String _package=CommonUtil.getDaoOutPutPath(tableInfo.getKey());
                String path = _package + "/read/"
                    + getReadDaoName(tableInfo.getKey()) + "Mapper.java";

                FileUtil.mkdir(path);

                writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(path), CommonUtil.getSourceEncode()));

                Map<String, Object> rootMap = new HashMap<String, Object>();

                setRootMapForDao(rootMap, tableInfo);

                Template tpl = cfg.getTemplate("ReadDaoImpl.tpl");
                tpl.setEncoding("UTF-8");

                tpl.process(rootMap, writer);
                
               // FileUtils.writeStringToFile(new File(_package + "/package.html"), "dao", "UTF-8");
            } finally {
                if (null != writer) {
                    writer.flush();
                    writer.close();
                }
            }

        }
    
    private static void setRootMapForDao(Map<String, Object> rootMap,
        Entry<String, List<TableInfoBean>> tableInfo) {

    	rootMap.put("entitypackage", CommonUtil.getEntityPackage());
        rootMap.put("date", DateUtil.getStringToday());
        rootMap.put("package", CommonUtil.getDaoPackage(tableInfo.getKey()));
        rootMap.put("parentImport", CommonUtil.getDaoParent());
        rootMap.put("classdef", getDaoName(tableInfo.getKey()));
        rootMap.put("readclassdef", getReadDaoName(tableInfo.getKey()));
        rootMap.put("extends", CommonUtil.getExtends(CommonUtil.getDaoParent()));
        rootMap.put("tableName", CommonUtil.getOutputColumnName(tableInfo
            .getKey(), false));
        rootMap.put("tableComment", CommentUtil.getTableComment(tableInfo.getKey()));

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

    public static String getDaoName(String tableName) {
    	String tableext="";
    	for(int i=1;i<tableName.split("_").length;i++){
    		tableext=tableext+(tableName.split("_")[i].substring(0, 1).toUpperCase() + tableName.split("_")[i].substring(1));
    	}
        String domainName = CommonUtil.getOutputColumnName(tableext, true);
        return domainName;// + DAO_SUFFIX;
    }
    public static String getReadDaoName(String tableName) {
    	String tableext="";
    	for(int i=1;i<tableName.split("_").length;i++){
    		tableext=tableext+(tableName.split("_")[i].substring(0, 1).toUpperCase() + tableName.split("_")[i].substring(1));
    	}
        String domainName = CommonUtil.getOutputColumnName(tableext, true);
        return "Read"+domainName;// + DAO_SUFFIX;
    }
}
