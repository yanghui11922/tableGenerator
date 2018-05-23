package generator;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import generator.bean.TableInfoBean;
import generator.tblloader.TableInfoFetch;
import generator.tools.ControllerGenerator;
import generator.tools.DaoGenerator;
import generator.tools.DomainGenerator;
import generator.tools.EntityGenerator;
import generator.tools.addListGenerator;
import generator.tools.HtmlListGenerator;
import generator.tools.ServiceGenerator;
import generator.tools.SqlMapGenerator;
import generator.tools.VoGenerator;
import generator.tools.XmlGenerator;
import generator.util.CommonUtil;
import generator.util.FileUtil;

public class Generator {

    private static Configuration cfg = null;

    static {
        if (null == cfg) {
            cfg = new Configuration();
            try {
                cfg.setDirectoryForTemplateLoading(new File(Generator.class
                    .getResource("/").getPath()
                    + "template"));
                cfg.setDefaultEncoding("UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String args[]) {
        try {
            Map<String, List<TableInfoBean>> infoMap = TableInfoFetch
                .fetchTableInfo();

            if (null != infoMap) {
                Entry<String, List<TableInfoBean>> tblInfo = null;

                List<String> tableNames = new ArrayList<String>();

                for (Iterator<Entry<String, List<TableInfoBean>>> i = infoMap
                    .entrySet().iterator(); i.hasNext();) {             
                    tblInfo = i.next();

                    if (CommonUtil.isGenarateAll() || CommonUtil.isGenarateSourceOnly()) {
                    	    EntityGenerator.genaratorEntity( cfg, tblInfo );
                    	   
                        DaoGenerator.genaratorDao(cfg, tblInfo);
                        DaoGenerator.ReadGenaratorDao(cfg, tblInfo);
                        
                        SqlMapGenerator.genaratorSqlMap(cfg, tblInfo);
                        SqlMapGenerator.ReadGenaratorSqlMap(cfg, tblInfo);
                        
                        ServiceGenerator.genaratorService(cfg, tblInfo);
                        ServiceGenerator.ReadgenaratorService(cfg, tblInfo);
                        
                        ControllerGenerator.genaratorEntity(cfg, tblInfo);
                        
                        HtmlListGenerator.genaratorEntity(cfg, tblInfo);
                        addListGenerator.genaratorEntity(cfg, tblInfo);
                        
                        
                        
                       // DomainGenerator.genaratorDomain(cfg, tblInfo);
                     //   VoGenerator.genaratorVo(cfg, tblInfo);
//                        ConditionDomainGenerator.genaratorDomain(cfg, tblInfo);
                       
                       
                    }
                    tableNames.add(tblInfo.getKey());
                }

                if (CommonUtil.isGenarateAll()
                    || CommonUtil.isGenarateXmlOnly()) {
                    XmlGenerator.genaratorXmls(cfg, tableNames);
                }

            } else {
                System.out.println("table is not defined, please check the config file");
            }
        } catch (IOException e) {

            e.printStackTrace();
        } catch (TemplateException e) {

            e.printStackTrace();
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}
