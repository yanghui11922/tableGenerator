package generator.util;

import java.util.Properties;

public class CommentUtil {
    private static Properties property = null;

    static {
        if (null == property) {
            property = PropertyFileReader.getProperties("config.tblcomment");
        }
    }

    public static String getTableComment(String tableName) {

        String tableComment = property.getProperty(tableName);

        if (null == tableComment || "".equals(tableComment)) {
            System.err.println("table「" + tableName + "」 is not Existence");
            return "";
        }
        return tableComment;
    }

    public static String getColumnComent(String tableName, String columnName) {

        String columnComment = property.getProperty(tableName + "."
            + columnName);

        if (null == columnComment || "".equals(columnComment)) {

            System.err.println("column「" + tableName + "." + columnName
                + "」is not Existence");
            return "";
        }
        return columnComment;
    }
}
