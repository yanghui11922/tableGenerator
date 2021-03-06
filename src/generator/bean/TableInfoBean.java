﻿package generator.bean;

public class TableInfoBean {

    private String  columnName = null;

    private String  typeName   = null;

    private int     size       = -1;

    private boolean primaryKey = false;

    private boolean isNullAble = false;

    private String comment;
    
    private int   charOctetLength  = -1;//对于 char 类型，该长度是列中的最大字节数

    public int getCharOctetLength() {
		return charOctetLength;
	}


	public void setCharOctetLength(int charOctetLength) {
		this.charOctetLength = charOctetLength;
	}


	public boolean isPrimaryKey() {

        return primaryKey;
    }


    public void setPrimaryKey(boolean primaryKey) {

        this.primaryKey = primaryKey;
    }


    public String getColumnName() {

        return columnName;
    }


    public void setColumnName(String columnName) {

        this.columnName = columnName;
    }


    public String getTypeName() {

        return typeName;
    }


    public void setTypeName(String typeName) {

        this.typeName = typeName;
    }


    public int getSize() {

        return size;
    }


    public void setSize(int size) {

        this.size = size;
    }


    public boolean isNullAble() {

        return isNullAble;
    }


    public void setNullAble(boolean isNullAble) {

        this.isNullAble = isNullAble;
    }


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}
    

}
