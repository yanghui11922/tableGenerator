package ${package};

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
/**
 * <p>${tableComment} Entity Class</p>
 *
 * @author majian
 */
@Getter
@Setter
@ApiModel(value = "${classdef}")
public class ${classdef} implements Serializable {

<#list columnList as column>
    public static final String COLUMN_${column.name} = "${column.name}";

</#list>
<#list fieldList as field>
    @ApiModelProperty(value = "${field.comment}")
    private ${field.type} ${field.name};

</#list>
	@ApiModelProperty(value = "<#list fieldList as field>['${field.name}','${field.comment}']</#list>")
	@JSONField(serialize = false)
	public String ${classdef}Field;
}
