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
@ApiModel(value = "Check")
public class Check implements Serializable {

    public static final String COLUMN_ID = "ID";

    public static final String COLUMN_CreateTime = "CreateTime";

    public static final String COLUMN_ModifyTime = "ModifyTime";

    public static final String COLUMN_Status = "Status";

    public static final String COLUMN_AdminID = "AdminID";

    public static final String COLUMN_Administrator = "Administrator";

    public static final String COLUMN_WarehouseID = "WarehouseID";

    public static final String COLUMN_Name = "Name";

    public static final String COLUMN_Time = "Time";

    public static final String COLUMN_Memo = "Memo";

    public static final String COLUMN_StoreID = "StoreID";

    public static final String COLUMN_AdjustStatus = "AdjustStatus";

    public static final String COLUMN_Operator = "Operator";

    public static final String COLUMN_Updater = "Updater";

    @ApiModelProperty(value = "编号")
    private String ID;

    @ApiModelProperty(value = "创建时间")
    private String CreateTime;

    @ApiModelProperty(value = "修改时间")
    private String ModifyTime;

    @ApiModelProperty(value = "状态 【正常：1   删除：88】")
    private int Status;

    @ApiModelProperty(value = "仓库管理员")
    private String AdminID;

    @ApiModelProperty(value = "管理员(冗余字段)")
    private String Administrator;

    @ApiModelProperty(value = "仓库手机(冗余字段)")
    private String WarehouseID;

    @ApiModelProperty(value = "仓库名字")
    private String Name;

    @ApiModelProperty(value = "盘点时间")
    private String Time;

    @ApiModelProperty(value = "备注")
    private String Memo;

    @ApiModelProperty(value = "盘点")
    private String StoreID;

    @ApiModelProperty(value = "是否调整 0 否  1 是")
    private String AdjustStatus;

    @ApiModelProperty(value = "操作人")
    private String Operator;

    @ApiModelProperty(value = "更新人")
    private String Updater;

	@ApiModelProperty(value = "['ID','编号']['CreateTime','创建时间']['ModifyTime','修改时间']['Status','状态 【正常：1   删除：88】']['AdminID','仓库管理员']['Administrator','管理员(冗余字段)']['WarehouseID','仓库手机(冗余字段)']['Name','仓库名字']['Time','盘点时间']['Memo','备注']['StoreID','盘点']['AdjustStatus','是否调整 0 否  1 是']['Operator','操作人']['Updater','更新人']")
	@JSONField(serialize = false)
	public String CheckField;
}
