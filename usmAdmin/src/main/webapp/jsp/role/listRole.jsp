<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html  lang="zh-CN">
<head>
<title>Role List</title>
<%@ include file="../inc/header.jsp" %>
</head>
<body>
<%@ include file="../inc/nav.jsp" %>

  <!-- CONTENT BEGIN -->
  <div class="body content rows scroll-y admin-body">

    <div class="row">
      <div class="col-xs-12">
        <div class="container-fluid">
          <div class="row">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 main" id="rightContent" >
            <div id="content">
              <div class="page-heading" style='float:left'>
                <h2> <strong>角色</strong>
                  <small></small>
                </h2>
              </div>
              <div class="text-right pull-right searchBox">
                <div role="form" class="form-inline">
                  <div class="form-group" >
                    <input type="text" id="searchContent" name="searchContent" placeholder="搜索角色或应用" class="form-control ui-autocomplete-input" style="display:inline-block;" autocomplete="off"></div>
                  <button class="btn btn-primary"   type="button" id="search" name="search">搜索</button>
                </div>
              </div>

              <span class="boot-total">匹配角色 500</span>
              <span class="boot-events">显示角色 1 - 5</span>
              <div class="row">
                <div class="col-xs-12">
                  <div class="boot-fL">
                    <label>
                      显示
                      <select name="example_length">
                        <option value="5" selected="selected">5</option>
                        <option value="20">20</option>
                        <option value="50">50</option>
                      </select>
                      条目
                    </label>
                  </div>                 
                </div>
              </div>

              <div class="boot-box">
                <table id="table_id" class="table table-bordered table-hover" cellspacing="0" width="100%">
                  <thead>
                    <tr>
                      <th class="sort">
                        角色
                        <i class="glyphicon glyphicon-arrow-up" data-direction="desc" data-orderfield="roleName"></i>
                      </th>
                      <th class="sort">
                        应用
                        <i class="glyphicon glyphicon-arrow-up hidden" data-direction="desc" data-orderfield="umsApp.appName"></i>
                      </th>
                      <th>操作</th>
                    </tr>
                  </thead>
                  <tbody>
                  </tbody>
                </table>
              </div>

              <div class="row">
                <div class="col-xs-12">
                  <div class="boot-fR">
                    <ul class="pagination boot-page">
                      <li class="paginate_button first disabled">
                        <a href="javascript:void(0)">&lt;&lt;&nbsp;第一页</a>
                      </li>
                      <li class="paginate_button previous disabled">
                        <a href="javascript:void(0)">&lt;&nbsp;上一页</a>
                      </li>
                      <li class="paginate_button pageNum active">
                        <a href="javascript:void(0)">1</a>
                      </li>
                      
                      <li class="paginate_button next disabled">
                        <a href="javascript:void(0)">下一页&nbsp;&gt;</a>
                      </li>
                      <li class="paginate_button last disabled">
                        <a href="javascript:void(0)">末页&nbsp;&gt;&gt;</a>
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
            </div>

          </div>
        </div>
      </div>
    </div>
  </div>
  <ul class="ui-autocomplete ui-front ui-menu ui-widget ui-widget-content" id="ui-id-1" tabindex="0" style="display: none;"></ul>
</div>
<!-- END -->
  <%@ include file="../inc/footer.jsp" %>
  <script type="text/javascript">
    pageFun.createPage({
        url:"<%=request.getContextPath() %>/role/list",
        dataTableFun:function(json){//A line corresponding to insert data from the json
          var dataTable='';//write in table
          console.log(json)
        
          for(var j=0;j<json.length;j++)
          {
        	  var dataJson=json[j],cls='';
              if(dataJson['roleName']=='administrator'){            	
            	    cls='disabled';    	   
               }
        	  
            var dataJson=json[j];
            dataTable+='<tr><td>';
            dataTable+='<a href="<%=request.getContextPath() %>/role/viewRole/'+dataJson["roleId"]+'">'+$.trim(dataJson['roleName'])+'</a>';
            dataTable+='</td>';
            dataTable+='<td>'+$.trim(dataJson['appName'])+'</td>';
            dataTable += '<td><div id="edit2" class="btn-group pull-right" style="float:left !important;">'
            + '<button class="btn btn-default btn-xs dropdown-toggle '+cls+'" type="button" data-toggle="dropdown" aria-expanded="false">'
            + '操作 <span class="caret"></span></button>'
            + '<ul class="dropdown-menu" role="menu" id="edit">';
            dataTable += ' <shiro:hasPermission name="role:delete"><li><a href = "javascript:void(0)" data-id="'+dataJson['roleId']+'"data-toggle="modal" data-target="#delModal" class="deployDel">删除</a></li></shiro:hasPermission>'
            + '<shiro:hasPermission name="role:delete"><li><a href = "<%=request.getContextPath() %>/role/toUpdate/'+ dataJson['roleId'] +'"">更新</a></li></shiro:hasPermission>'
            + '</ul></div></td>';
          } 
          return dataTable;
        },
        pageName:"角色"
      });
      
      /*search*/
      $("#search").click(function(){
        pageFun.defaultPage.searchSel("hints="+encodeURIComponent($("#searchContent").val()));
      });

      $("#searchContent").change(function(){
        pageFun.defaultPage.searchSel("hints="+encodeURIComponent($("#searchContent").val()));
      });
      $('#searchContent').keyup(function(event){
        if (event.keyCode == '13') {
          pageFun.defaultPage.searchSel("hints="+encodeURIComponent($("#searchContent").val()));
         }
      });

      $( "#searchContent" ).autocomplete({
        source: "<%=request.getContextPath() %>/role/getSearchHints",
        appendTo: ".body",
        autoFocus:true
      });
      
      
      var roleId = ''
      $(document).on('click', '.deployDel', function() {
          roleId = $(this).attr('data-id');
      });
      $(document).on('click', '#delModal .btn-primary', function() {
        if(roleId !== undefined){
          $.ajax({
            url: '<%=request.getContextPath()%>/role/deleteRole',
            type: 'get',
            dataType: 'json',
            data: {'roleId':roleId},
            success:function(data){                              
                if(data.busiStatus == 'success'){
                  createAlert({
                    statusCode:200,
                    message:data.message
                  });                   
                  pageFun.defaultPage.sel(); 
                  
                }else{
                	
                		 createAlert({
                             statusCode:300,
                             message:String(data.errorMsg)
                           })                
                 
                }            	                   	
            },
            error:function(data){
            	
               createAlert({
                      statusCode:300,
                      message:"出错了,请重试"
                    })
            }
          });
          
          
        }
      });
  
      /* 遍历所有的li，如果取到的元素都为空，则隐藏这个ul */ 
     $(document).on('click', '#edit2 button', function() {
    	  var item=$("#edit2:has(li)").length;
    	   	console.log(item);
    	   	if(item==0){
    	   		$("#edit2 ul").remove();
    	   	          
    	}
      }); 
      

     
  </script>

</body>
</html>