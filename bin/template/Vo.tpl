/*
 * ${date} DX Creating
 *
 * (c) Copyright TIS Inc. All rights reserved.
 */
package vo
{
    /**
     * <p>${tableComment} Domain Class</p>
     *
     * @author DX
     * @version 1.00
     */
    [RemoteClass(alias="${package}.${classdefDto}")]
    [Bindable]
    public class ${classdef} {
    
    <#list fieldList as field>
        /** ${field.comment} */
        private var _${field.name}:${field.type};
    
    </#list>
        /**
         * Default Constants
         */
        public function ${classdef}():void {
    
        }
    
    <#list fieldList as field>
        /**
         * ${field.name} getter
         *
         * @return the ${field.name}
         */
        public function get ${field.methodAppend}():${field.type}{
            return _${field.name};
        }
    
        /**
         * ${field.name} setter
         *
         * @param ${field.name} ${field.name} 
         */
        public function set ${field.methodAppend}(${field.name}:${field.type}):void {
            this._${field.name} = ${field.name};
        }
    
    </#list>
    }
}
