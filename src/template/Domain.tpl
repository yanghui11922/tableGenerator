/*
 * ${date} DX Creating
 *
 * (c) Copyright TIS Inc. All rights reserved.
 */
package ${package};

<#list importList as import>
import ${import};
</#list>
<#if importList?size != 0>

</#if>
import ${parentImport};
<#list implementsArray as implements>
import ${implements};
</#list>

/**
 * <p>${tableComment} Domain Class</p>
 *
 * @author DX
 * @version 1.00
 */
public class ${classdef}${extends}${implements} {

    private static final long serialVersionUID = 1L; 

<#list fieldList as field>
    /** ${field.comment} */
    private ${field.type} _${field.name};

</#list>
    /**
     * <p>Default Constants</p>
     */
    public ${classdef}() {

    }

<#list fieldList as field>
    /**
     * <p>${field.name} getter</p>
     *
     * @return the ${field.name}
     */
    public ${field.type} get${field.methodAppend}() {
        return _${field.name};
    }

    /**
     * <p>${field.name} setter</p>
     *
     * @param ${field.name} ${field.name} 
     */
    public void set${field.methodAppend}(${field.type} ${field.name}) {
        this._${field.name} = ${field.name};
    }

</#list>
}
