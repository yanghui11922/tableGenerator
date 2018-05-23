package generator.util;

import generator.Globles;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

public class CommonUtil implements Globles {

    private CommonUtil() {

    }

    private static Properties property = null;

    static {
        if (null == property) {
            property = PropertyFileReader.getProperties("config.base");
        }
    }

    public static String getOutputColumnName(String tblName, boolean firstUpcase) {
        StringTokenizer stoken = new StringTokenizer(tblName, "_");
        String outStr = "";

        while (stoken.hasMoreTokens()) {
            String token = stoken.nextToken().toLowerCase();
            if ("".equals(outStr) && !firstUpcase) {
                outStr += token;
            } else {
                outStr += String.valueOf(token.charAt(0)).toUpperCase()
                    + token.substring(1);
            }
        }

        if (!firstUpcase) {
            if (outStr.length() > 2) {
                String first2Letter = outStr.substring( 0, 2 ).toLowerCase();
                outStr = first2Letter + outStr.substring( 2 );
            } else {
                outStr = outStr.toLowerCase();
            }
        } else {
            if (outStr.length() > 2) {
                String firstLetter = outStr.substring( 0, 1 );
                String secondLetter = outStr.substring( 1, 2 ).toLowerCase();
                outStr = firstLetter + secondLetter + outStr.substring( 2 );
            } else {
                String firstLetter = outStr.substring( 0, 1 );
                String secondLetter = outStr.substring( 1 ).toLowerCase();
                outStr = firstLetter + secondLetter;
            }
        }

        return outStr;
    }

    public static String getXName(String tblNamn) {
    		String[] a=tblNamn.split("_");
    		if(a.length>=1) {
    			return toLowerCaseFirstOne(a[1]);
    		}else {
    			return tblNamn;
    		}
      
    }
    public static String toLowerCaseFirstOne(String s){
    	  if(Character.isLowerCase(s.charAt(0)))
    	    return s;
    	  else
    	    return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    	}
    
    
    public static String getDaoOutPutPath(String tableName) {
        
    	String rootPath = property.getProperty("root.path");

        String sqlMapRootPath = getDaoPackage(tableName);
        sqlMapRootPath = sqlMapRootPath.replace('.', '/');

        return rootPath + "/" + sqlMapRootPath;
    }

    public static String getServiceOutPutPath(String tableName) {
        String rootPath = property.getProperty("root.path");

        String sqlMapRootPath = getServicePackage(tableName);
        sqlMapRootPath = sqlMapRootPath.replace('.', '/');

        return rootPath + "/" + sqlMapRootPath;
    }

    public static String getSqlMapOutPutPath(String tableName) {

        String rootPath = property.getProperty("root.path");

        String sqlMapRootPath = getSqlMapPackage(tableName);
        sqlMapRootPath = sqlMapRootPath.replace('.', '/');

        return rootPath + "/" + sqlMapRootPath;
    }

    public static String getDomainOutPutPath(String tableName) {

        String rootPath = property.getProperty("root.path");

        String domainRootPath = getDomainPackage(tableName);
        domainRootPath = domainRootPath.replace('.', '/');

        return rootPath + "/" + domainRootPath;
    }
    
    public static String getVoOutPutPath(String tableName) {

        String rootPath = property.getProperty("root.path");

        //String domainRootPath = getDomainPackage(tableName);
        //domainRootPath = domainRootPath.replace('.', '/');

        return rootPath + "/vo" ;
    }

    public static String getRootPath() {
        return property.getProperty("root.path");
    }

    public static String getXmlRootPath() {
        return property.getProperty("xml.root.path");
    }

    public static String getDomainPackage(String tableName) {

        String packageOut = property.getProperty("domain.package.root");
        String packageAppend = getPackageAppend(tableName);

        if (null == packageAppend || "".equals(packageAppend)) {
            return packageOut;
        }

        return packageOut + "." + packageAppend;
    }

    public static String getDomainColumnSkip() {
        return property.getProperty("domain.column.skip");
    }

    public static String getDomainParent() {
        return property.getProperty("domain.parent");
    }
    
    public static String[] getImplementsArray() {
        return property.getProperty("domain.implements").split(",");
    }
    
    
    public static String getDomainImplements() {
        return property.getProperty("domain.implements");
    }
    public static String getDaoPackage(String tableName) {

        String packageOut = property.getProperty("dao.package.root");
        String packageAppend = getPackageAppend(tableName);

        if (null == packageAppend || "".equals(packageAppend)) {
            return packageOut;
        }

        return packageOut + "." + packageAppend;
    }
    
    public static String getSqlMapPackage(String tableName) {

       return getDaoPackage(tableName) + "";
    }




    public static String getEntityPackage() {
        return property.getProperty("entity.package.root");
    }

    public static String getEntityParent() {
        return property.getProperty("entity.parent");
    }

    public static String getEntityOutPutPath() {

        String rootPath = property.getProperty("root.path");

        String entityRootPath = getEntityPackage();
        entityRootPath = entityRootPath.replace('.', '/');

        return rootPath + "/" + entityRootPath;
    }


    public static String getControllerPackage() {
        return property.getProperty("controller.package.root");
    }

    public static String getControllerOutPutPath() {

        String rootPath = property.getProperty("root.path");

        String entityRootPath = getControllerPackage();
        entityRootPath = entityRootPath.replace('.', '/');

        return rootPath + "/" + entityRootPath;
    }

    public static String getHtmlListOutPutPath() {
        String rootPath = property.getProperty("root.path");
        String entityRootPath = property.getProperty("htmllist.package.root");
        entityRootPath = entityRootPath.replace('.', '/');

        return rootPath + "/" + entityRootPath;
    }

    public static String getDaoParent() {
        return property.getProperty("dao.parent");
    }

    public static String getServicePackage(String tableName) {
        String packageOut = property.getProperty("service.package.root");
        String packageAppend = getPackageAppend(tableName);

        if (null == packageAppend || "".equals(packageAppend)) {
            return packageOut;
        }

        return packageOut + "." + packageAppend;
    }

    public static String getServiceParent() {
        return property.getProperty("service.parent");
    }

    public static String getConditionDomainParent() {
        return property.getProperty("condition.domain.parent");
    }
    public static String getConditionDomainImplements() {
        return property.getProperty("condition.domain.implements");
    }
    public static String getImportSkip() {
        return property.getProperty("import.skip");
    }

    public static String getJavaMappingType(String jdbcType) {

        String outPut = "";
        outPut = property.getProperty(jdbcType.toUpperCase());
        if (null == outPut || "".equals(outPut)) {
            System.out.println("Java mapping is not existence");
        }

        return outPut;
    }
    
    
    public static String getJavaTypeBySql(String Type) {

        String outPut = "";
        
        if (Type.toUpperCase().equals("INT")) {
        	outPut = "int";
        }else  if (Type.toUpperCase().equals("BIGINT")) {
        //	outPut = "long";
        	outPut = "String";
        }else  if (Type.toUpperCase().equals("FLOAT")) {
        	outPut = "float";
        }else  if (Type.toUpperCase().equals("DOUBLE")) {
        	outPut = "double";
        }else{
        	outPut = "String";
        }

        return outPut;
    }

    private static String getPackageAppend(String tableName) {
        String disPatch = "";
        disPatch = property.getProperty("package.dispach");

        if (null == disPatch || "".equals(disPatch)) {
            return "";
        }

        String[] patchs = disPatch.split(",");

        for (int i = 0; i < patchs.length; i++ ) {
            List<String> include = Arrays.asList(property.getProperty(patchs[i] + DISPATCH_INCLUDE).split(","));

            if (null == include || "".equals(include)) {
                System.out.println(patchs[i] + DISPATCH_INCLUDE + " is not Definition");
            }

            if (include.contains(tableName)) {
                return property.getProperty(patchs[i] + DISPATCH_APPEND);
            }
        }

        return "";
    }

    public static String getSourceEncode() {
        return property.getProperty("encode");
    }

    public static boolean isGenarateAll() {
        return "0".equals(getGenerateType());
    }

    public static boolean isGenarateSourceOnly() {
        return "1".equals(getGenerateType());
    }

    public static boolean isGenarateXmlOnly() {
        return "2".equals(getGenerateType());
    }

    public static String getExtends(String parentDefine) {

        String parent = parentDefine;
        final int pos = parent.lastIndexOf(".");

        if (pos > -1) {
            parent = parent.substring(pos + 1);
            parent = " extends " + parent;
        }

        return parent;
    }

    public static String getImplements(String _implements) {

        String[] _interfaces = _implements.split(",");
        StringBuffer impBuff = new StringBuffer();
        for (int i = 0; i < _interfaces.length; i++) {
            if (i == 0)
                impBuff.append(" implements ");

            String _if = _interfaces[i];
            final int pos = _if.lastIndexOf(".");

            if (pos > -1) {
                impBuff.append(_if.substring(pos + 1));
            }

            if (i < _interfaces.length - 1)
                impBuff.append(" , ");
        }

        return impBuff.toString();
    }

    public static String getType(String importValue) {

        String type = importValue;
        final int pos = importValue.lastIndexOf(".");

        if (pos > -1) {
            type = importValue.substring(pos + 1);
        }

        return type;
    }

    private static String getGenerateType() {
        return property.getProperty("gen.type");
    }

    // public static void main(String[] args) {
    // System.out.println(getSqlMapOutPutPath());
    // }
}
