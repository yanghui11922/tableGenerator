<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="js/web/include.js?v=1.0.5"></script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content" id="app">
    <!-- loading 固定格式不要删除-->
    <div id="overlay" style="background:#000;filter:alpha(opacity=50);opacity:0.5;display:none;position:absolute;top:0px;left:0px;width:100%;height:100%;z-index:100;display:none;"><div class="sk-spinner sk-spinner-double-bounce" style="margin-top: 300px;"><div class="sk-double-bounce1"></div> <div class="sk-double-bounce2"></div></div></div>
    <!-- Panel Other -->
        <div class="ibox-content">
            <div class="row row-lg">
                <div class="col-sm-12">
                    <div class="example-wrap">
                        <h4 class="example-title"></h4>
                        <div class="example">
                            <div class="btn-group hidden-xs" id="exampleTableEventsToolbar" role="group">
                                <button type="button"   @click="modify(0)" class="btn btn-primary ">
                                    <i class="glyphicon glyphicon-plus" aria-hidden="true">新增</i>
                                </button>
                                <button type="button"  @click="modify(1)" class="btn btn-success ">
                                    <i class="glyphicon glyphicon-pencil" aria-hidden="true">修改</i>
                                </button>
                                <button type="button" @click="recover(88)" class="btn  btn-danger">
                                    <i class="glyphicon glyphicon-trash" aria-hidden="true">删除</i>
                                </button>
                                <button type="button"  @click="recover(1)" class="btn btn-primary ">
                                    <i class="glyphicon glyphicon-play" aria-hidden="true">恢复</i>
                                </button>
                            </div>
                            <table id="exampleTableEvents" >
                            </table>
                        </div>
                    </div>
                    <!-- End Example Events -->
                </div>
            </div>
        </div>
</div>
<script>
    var tempApp="";//一定要写这个 ,约定
    window.onload=function () {
        /**初始化VUE */
        var app = new Vue({
            el: "#app",
            data: {
                status: -1,
                index:0
            },
            methods: {
                recover: function (val) {
                    setStatus(this,val)
                },
                modify: function (flag) {
                    var url = "../Admin/${viewPathName}/add${classdef}.html?";
                    if(flag==0){//新增
                        url+= "id=0";
                    }else{//修改
                        if(checkIsSelection()){return};
                        if(checkIsSelectionOne()){return};//用于只判断一条记录
                        url+= "id="+getRecordIDForFastJson($('#exampleTableEvents'));
                    }
                    modifyShow(this,url,"80%","80%","${classdef}",true);
                },
                 view:function(row, tr, field){//慧姐提供
                    var url = "../Admin/${viewPathName}/add${classdef}.html?";
                    url+= "id="+row.iD;
                    url+= "&viewflag=1";//查看标志，页面全部设置成只读
                    modifyShow(this,url,"80%","80%","${classdef}",true);
                }
            },
            mounted: function () {
                bindImgError();
                initFancybox();
                tempApp=this;
            }

        })
        /** 固定写法 by majian*/
        function setStatus(that,status) {
            setAdminStatus(that,status,"../${classdef}/set${classdef}Status")
        }
        /** 初始化表格*/
        (function(document, window, $) {
            'use strict';
            (function() {
                var columns=[
                    <#list fieldList as field>
			        <#if field.nullAble == false>
			        {
                        title: '${field.xame}',
                        align: 'center',
                        field: '${field.comment}',
                    },
			        </#if>
			        </#list>
                    {
                        title: getChangHead("类型","status",[{"id":88,"title":"删除"},{"id":1,"title":"正常"}]),
                        align: 'center',
                        field: 'status',
                        formatter:function(value){
                            return headFormatter(value,[{"id":88,"title":"删除"},{"id":1,"title":"正常"}]);
                        }
                    },
                ];
                getDataTable($('#exampleTableEvents'),"../${classdef}/query${classdef}Page",columns,queryParams,true,tempApp);
            })();
            function queryParams(params) {
                var temp={}
                temp.pageSize=params.limit;
                temp.pageNumber=params.offset/params.limit+1;
                temp.search=params.search;
                temp.status=app.status;
                return temp;
            }

        })(document, window, jQuery);
    }
</script>
</body>
</html>