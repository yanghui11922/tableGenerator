package generator.bean;

public class DomainDefineBean {

    private String type = null;

    private String name = null;

    private String comment = null;

    private String methodAppend = null;

    private String column = null;
    
    private String xame=null;
    
    private int   charOctetLength  = 0;//对于 char 类型，该长度是列中的最大字节数
    
    private boolean isNullAble = false;

    public boolean getNullAble() {
		return isNullAble;
	}

	public void setNullAble(boolean isNullAble) {
		this.isNullAble = isNullAble;
	}

	public int getCharOctetLength() {
		return charOctetLength;
	}

	public void setCharOctetLength(int charOctetLength) {
		this.charOctetLength = charOctetLength;
	}

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMethodAppend() {
        return methodAppend;
    }

    public void setMethodAppend(String methodAppend) {
        this.methodAppend = methodAppend;
    }



    public String getColumn() {
        return column;
    }



    public void setColumn( String column ) {
        this.column = column;
    }

    public void setXame(String xame) {
		this.xame = xame;
	}
    public String getXame() {
		return xame;
	}


    
}
