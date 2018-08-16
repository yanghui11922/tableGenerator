<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="js/web/include.js?v=1.0.5"></script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content" id="app">
     <!-- loading 固定格式不要删除1-->
    <div id="overlay" style="background:#000;filter:alpha(opacity=50);opacity:0.5;display:none;position:absolute;top:0px;left:0px;width:100%;height:100%;z-index:100;display:none;"><div class="sk-spinner sk-spinner-double-bounce" style="margin-top: 300px;"><div class="sk-double-bounce1"></div> <div class="sk-double-bounce2"></div></div></div>
    <!-- Panel Other -->
    <div class="ibox float-e-margins">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
                        <div class="form-horizontal">

							<#list xName as field>
							    <div class="hr-line-dashed" ></div>
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label" v-text="${field.xame}"></label>
	                                <div class="col-sm-10">
	                                    <input type="text" v-model="data.${field.xame}" class="form-control" <#if field.charOctetLength gt 0 > maxlength="${field.charOctetLength}" </#if> >
	                            </div>
                            </div>
							</#list>
                          
                           <!--  <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <button class="btn btn-primary" @click="save" type="submit">保存内容</button>
                                </div>
                            </div> -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var tempApp="";
    window.onload=function () {
        var app = new Vue({
            el: "#app",
            data: {
                 viewflag:getUrlKey("viewflag")=="1"?false:true,//查看标志
                 <#list xName as field>
                    ${field.xame}:"${field.comment}",
                 </#list>
                data:{
                    iD:getUrlKey("id"),
                    <#list xName as field>
                    	${field.xame}:0,
                    </#list>
                }
            },
            methods: {
                initDocument: function () {//一些特殊的处理如查看时全部只读   时间控件初始化等
                    if(this.viewflag){//查看goodsDataList
                        $('input,select,textarea').attr('disabled',true);//
                        //setTimeout("$('#goodsDataList tbody tr td input').attr('readonly',true)",200);//针对有子表列表编辑时可放开此句
                    }
                },
                save: function () {
                    if(validate()){return};
                    savaData(this,"../${classdef}/modify${classdef}")
                }
            },
            mounted: function () {
                tempApp=this;
                if(this.data.iD!=0){
                    findByID(this,this.data.iD,"../${classdef}/find${classdef}/")
                }
                bindImgError();
                this.initDocument();
            }
        })
    }
    function validate(){
        var message="不允许为空。"
        <#list xName as field>
        <#if field.nullAble == false>
        if(!chechIsUnll(tempApp.data.${field.xame})){
            error(tempApp.${field.xame}+message);
            return true;
        }
        </#if>
        </#list>
        return false;
    }
</script>
</body>
</html>
