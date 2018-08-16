package generator.tools;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import generator.bean.KeyValueBean;
import generator.bean.TableInfoBean;
import generator.util.CommonUtil;
import generator.util.FileUtil;
import generator.util.PropertyFileReader;

public class SqlMapGenerator {

    private SqlMapGenerator() {

    }


    public static void genaratorSqlMap(Configuration cfg, Entry <String, List <TableInfoBean>> tableInfo) throws IOException, TemplateException {

        BufferedWriter writer = null;

        try {
            String path = CommonUtil.getSqlMapOutPutPath(tableInfo.getKey()) + "/write/" + getSqlMapName(tableInfo.getKey());

            FileUtil.mkdir(path);

            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));

            Map <String, Object> rootMap = new HashMap <String, Object>();

            setRootMapForSQLMap(rootMap, tableInfo);

            Template tpl = cfg.getTemplate("SqlMap.tpl");
            tpl.setEncoding("UTF-8");

            tpl.process(rootMap, writer);
        } finally {
            if (null != writer) {
                writer.flush();
                writer.close();
            }
        }

    }

    
    public static void ReadGenaratorSqlMap(Configuration cfg, Entry <String, List <TableInfoBean>> tableInfo) throws IOException, TemplateException {

        BufferedWriter writer = null;

        try {
            String path = CommonUtil.getSqlMapOutPutPath(tableInfo.getKey()) + "/read/" + getReadSqlMapName(tableInfo.getKey());

            FileUtil.mkdir(path);

            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));

            Map <String, Object> rootMap = new HashMap <String, Object>();

            setRootMapForSQLMap(rootMap, tableInfo);

            Template tpl = cfg.getTemplate("ReadSqlMap.tpl");
            tpl.setEncoding("UTF-8");

            tpl.process(rootMap, writer);
        } finally {
            if (null != writer) {
                writer.flush();
                writer.close();
            }
        }

    }

    private static void setRootMapForSQLMap(Map <String, Object> rootMap, Entry <String, List <TableInfoBean>> tableInfo) {

        Properties props = PropertyFileReader.getProperties("config.base");
        List <String> commonColumns = Arrays.asList(props.getProperty("update.columns.nouse").split(","));
        String tableext="";
    	for(int i=1;i<tableInfo.getKey().split("_").length;i++){
    		tableext=tableext+(tableInfo.getKey().split("_")[i].substring(0, 1).toUpperCase() + tableInfo.getKey().split("_")[i].substring(1));
    	}
        rootMap.put("tblName", CommonUtil.getOutputColumnName(tableext, false));
        rootMap.put("tblNameSql", tableInfo.getKey());

        //        rootMap.put("package", CommonUtil.getDomainPackage(tableInfo.getKey()));
        rootMap.put("package", CommonUtil.getEntityPackage());

        rootMap.put("daoPackage", CommonUtil.getDaoPackage(tableInfo.getKey()));

        List <KeyValueBean> whereKeyValueList = new ArrayList <KeyValueBean>();
        rootMap.put("whereConditionList", whereKeyValueList);

        List <KeyValueBean> updateKeyValueList = new ArrayList <KeyValueBean>();
        rootMap.put("updateColumnList", updateKeyValueList);

        List <KeyValueBean> rsltSetKeyValueList = new ArrayList <KeyValueBean>();
        rootMap.put("rsltSetColumList", rsltSetKeyValueList);

        KeyValueBean keyValueBean = null;

        String primaryKey = "";
        String defaultSortKeys = "";
        String Status="";
        String ID="";

        // add start for where key DEL_FLG='0'
        List <String> allColums = new ArrayList <String>();
        boolean isDelFlgPK = false;
        // add end for where key DEL_FLG='0'

        for (TableInfoBean tableInfoBean : tableInfo.getValue()) {

            // add start for where key DEL_FLG='0'
            allColums.add(tableInfoBean.getColumnName());
            // add end for where key DEL_FLG='0'

            keyValueBean = new KeyValueBean();
            keyValueBean.setKey(tableInfoBean.getColumnName());
            System.out.println(tableInfoBean.getColumnName());
            keyValueBean.setValue(tableInfoBean.getColumnName().split("_")[1]);
            whereKeyValueList.add(keyValueBean);
            if (!commonColumns.contains(keyValueBean.getKey())) {
                updateKeyValueList.add(keyValueBean);
            }
            rsltSetKeyValueList.add(keyValueBean);

            if (!rootMap.containsKey("countColumn")) {
                rootMap.put("countColumn", keyValueBean.getKey());
            }
            if(tableInfoBean.getColumnName().contains("Status")) {
            		Status=tableInfoBean.getColumnName();
            }
            if(tableInfoBean.getColumnName().split("_")[1].equals("ID")) {
        			ID=tableInfoBean.getColumnName();
            }
            
            if (tableInfoBean.isPrimaryKey()) {
                // add start for where key DEL_FLG='0'
                if ("DEL_FLG".equals(keyValueBean.getKey())) {
                    isDelFlgPK = true;
                }
                // add end for where key DEL_FLG='0'

                if ("".equals(primaryKey)) {
                    primaryKey += keyValueBean.getKey() + "=#{" + keyValueBean.getValue() + "}";
                    defaultSortKeys += keyValueBean.getKey() + " asc";
                } else {
                    primaryKey += " and " + keyValueBean.getKey() + "=#{" + keyValueBean.getValue() + "}";
                    defaultSortKeys += ", " + keyValueBean.getKey() + " asc";
                }
            }
        }

        // add start for where key DEL_FLG='0'
        if (!isDelFlgPK && allColums.contains("DEL_FLG")) {
            primaryKey += " and DEL_FLG='0'";
        }
        // add end for where key DEL_FLG='0'

        rootMap.put("primaryKeys", primaryKey);
        rootMap.put("selectSql", getSelectSql(tableInfo));
        rootMap.put("insertKeySql", getInsertKeySql(tableInfo));
        rootMap.put("insertValueSql", getInsertValueSql(tableInfo));
        rootMap.put("updateSql", getUpdateSql(tableInfo));
        rootMap.put("conditionSql", getConditionSql(tableInfo));
        rootMap.put("conditionSearchSql", getConditionSearchSql(tableInfo));
        rootMap.put("conditionSqlByID", getConditionSqlByID(tableInfo));
        rootMap.put("conditionStatus", Status);
        rootMap.put("conditionID", ID+"= #{ID}");
        rootMap.put("start", "${start}");
        rootMap.put("limit", "${limit}");
     
        String sortKeys = props.getProperty(tableInfo.getKey() + ".sortKeys");
        if (null == sortKeys || "".equals(sortKeys)) {
            rootMap.put("sortKeys", defaultSortKeys);
        } else {
            rootMap.put("sortKeys", sortKeys);
        }
    }

    private static String getSelectSql(Entry <String, List <TableInfoBean>> tableInfo) {

        String selectSql = "";

        selectSql += "";
        for (TableInfoBean tableInfoBean : tableInfo.getValue()) {
            selectSql += tableInfoBean.getColumnName() + ",";
        }

        selectSql = selectSql.substring(0, selectSql.length() - 1);

        return selectSql;
    }

    private static String getInsertKeySql(Entry <String, List <TableInfoBean>> tableInfo) {

        String insertSql = "";

        insertSql += "\t\t";
        String keys = "";
        for (TableInfoBean tableInfoBean : tableInfo.getValue()) {
            keys += tableInfoBean.getColumnName() + ",";
        }

        keys = keys.substring(0, keys.length() - 1);

        return keys;
    }


    private static String getInsertValueSql(Entry <String, List <TableInfoBean>> tableInfo) {

        ArrayList <TableInfoBean> tableInfoList = new ArrayList <TableInfoBean>();

        int columnCount = 0;

        for (TableInfoBean tableInfoBean : tableInfo.getValue()) {
            tableInfoList.add(columnCount, tableInfoBean);
            columnCount++;
        }

        String updateSql = "";

        for (int i = 0; i < tableInfoList.size(); i++) {

            TableInfoBean tableInfoBean = tableInfoList.get(i);
            String columnName="#{" + tableInfoBean.getColumnName().split("_")[1] + "}";
            if(tableInfoBean.getColumnName().contains("CreateTime") || tableInfoBean.getColumnName().contains("ModifyTime")){
        			columnName="now()";
            }
            
            if (i < tableInfoList.size() - 1) {
                updateSql += columnName + ",\n";
            } else {
                updateSql += columnName;
            }
               
        }

        return updateSql;
    }


    private static String getUpdateSql(Entry <String, List <TableInfoBean>> tableInfo) {

        String updateSql = "";

        for (TableInfoBean tableInfoBean : tableInfo.getValue()) {
        		if(!tableInfoBean.getColumnName().split("_")[1].equals("ID")&& !"CreateTime".equals(tableInfoBean.getColumnName().split("_")[1])&&!"ModifyTime".equals(tableInfoBean.getColumnName().split("_")[1])) {
//        		 	if(tableInfoBean.getColumnName().split("_")[1].equals("ModifyTime")){
//	            		updateSql += tableInfoBean.getColumnName() + " = now(),";
//	                }else{
//	                	updateSql += tableInfoBean.getColumnName() + " = #{" + tableInfoBean.getColumnName().split("_")[1]+ "},";
//	             }
        			updateSql+="<if test=\""+tableInfoBean.getColumnName().split("_")[1]+"!=null\">";
        			updateSql += ","+tableInfoBean.getColumnName() + " = #{" + tableInfoBean.getColumnName().split("_")[1]+ "}";
        			updateSql+="</if>\n";
        		}
    
            
        }
       // updateSql = updateSql.substring(0, updateSql.length() - 1);

        return updateSql;
    }


    private static String getConditionSql(Entry <String, List <TableInfoBean>> tableInfo) {

        String whereSql = "";

        for (TableInfoBean tableInfoBean : tableInfo.getValue()) {
            whereSql += "<if test=\""
                    + tableInfoBean.getColumnName().split("_")[1] + "!=null\">\r\n";
            whereSql +=  "\t\t\t\t and " + tableInfoBean.getColumnName() + " = #{"
                    + tableInfoBean.getColumnName().split("_")[1] + "}\r\n";
            whereSql += "</if>\r\n";
        }
        return whereSql;
    }
    
    private static String getConditionSearchSql(Entry <String, List <TableInfoBean>> tableInfo) {
        String whereSql = "";
        for (TableInfoBean tableInfoBean : tableInfo.getValue()) {
        		whereSql+= tableInfoBean.getColumnName() +" like binary CONCAT(CONCAT('%', #{search,jdbcType=VARCHAR}),'%') or \r\n\t\t\t\t";
        }
        return whereSql;
    }
    
    private static String getConditionSqlByID(Entry <String, List <TableInfoBean>> tableInfo) {

        String whereSql = "";
        if(tableInfo.getValue().size()>0){
              whereSql += "" + tableInfo.getValue().get(0).getColumnName() + " = #{_parameter}";
        }
        
        return whereSql;
    }


    public static String getSqlMapName(String tableName) {
    	String tableext="";
    	for(int i=1;i<tableName.split("_").length;i++){
    		tableext=tableext+(tableName.split("_")[i].substring(0, 1).toUpperCase() + tableName.split("_")[i].substring(1));
    	}
        String sqlMapTableName = CommonUtil.getOutputColumnName(tableext, true);
        return sqlMapTableName + "Mapper.xml";
    }
    public static String getReadSqlMapName(String tableName) {
    	String tableext="";
    	for(int i=1;i<tableName.split("_").length;i++){
    		tableext=tableext+(tableName.split("_")[i].substring(0, 1).toUpperCase() + tableName.split("_")[i].substring(1));
    	}
        String sqlMapTableName = CommonUtil.getOutputColumnName(tableext, true);
        return "Read"+sqlMapTableName + "Mapper.xml";
    }
}
