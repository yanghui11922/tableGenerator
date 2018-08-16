package com.code.domain;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
/**
 * <p> Entity Class</p>
 *
 * @author majian
 */
@Getter
@Setter
@ApiModel(value = "Warehouse")
public class Warehouse implements Serializable {

    public static final String COLUMN_ID = "ID";

    public static final String COLUMN_CreateTime = "CreateTime";

    public static final String COLUMN_ModifyTime = "ModifyTime";

    public static final String COLUMN_Status = "Status";

    public static final String COLUMN_Name = "Name";

    public static final String COLUMN_Code = "Code";

    public static final String COLUMN_Address = "Address";

    public static final String COLUMN_Memo = "Memo";

    public static final String COLUMN_BaseInfoID = "BaseInfoID";

    @ApiModelProperty(value = "编号")
    private String ID;

    @ApiModelProperty(value = "创建时间")
    private String CreateTime;

    @ApiModelProperty(value = "修改时间")
    private String ModifyTime;

    @ApiModelProperty(value = "状态 【正常：1   删除：88】")
    private int Status;

    @ApiModelProperty(value = "仓库名称")
    private String Name;

    @ApiModelProperty(value = "仓库编码")
    private String Code;

    @ApiModelProperty(value = "仓库地址")
    private String Address;

    @ApiModelProperty(value = "备注")
    private String Memo;

    @ApiModelProperty(value = "编号")
    private String BaseInfoID;

	@ApiModelProperty(value = "['ID','编号']['CreateTime','创建时间']['ModifyTime','修改时间']['Status','状态 【正常：1   删除：88】']['Name','仓库名称']['Code','仓库编码']['Address','仓库地址']['Memo','备注']['BaseInfoID','编号']")
	@JSONField(serialize = false)
	public String WarehouseField;
}
