<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html  lang="zh-CN">
<head>
<title>User List</title>
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
                <h2> <strong>用户${user.isSys}</strong>
                  <small></small>
                  
                </h2>
              </div>
              <div class="text-right pull-right searchBox">
                <div role="form" class="form-inline">
                  <div class="form-group" >
                    <input type="text" id="searchContent" name="searchContent" placeholder="搜索用户或邮箱" class="form-control ui-autocomplete-input" style="display:inline-block;" autocomplete="off"></div>
                  <button class="btn btn-primary"   type="button" id="search" name="search">搜索</button>
                </div>
              </div>

              <span class="boot-total">匹配用户 500</span>
              <span class="boot-events">显示用户 1 - 5</span>
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
                        用户
                        <i class="glyphicon glyphicon-arrow-up" data-direction="desc" data-orderfield="userName"></i>
                      </th>
                      <th class="sort">
                        邮箱
                        <i class="glyphicon glyphicon-arrow-up hidden" data-direction="desc" data-orderfield="email"></i>
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
        url:"./listUsers",
        dataTableFun:function(json){//A line corresponding to insert data from the json
          var dataTable='';//write in table
          console.log(json)
          for(var j=0;j<json.length;j++)
          { 
        	 
            var dataJson=json[j],cls='';
            if(dataJson['isSys']==1){            	
          	    cls='disabled';    	   
             }
            dataTable+='<tr><td>';
            dataTable+='<a href="viewUser/'+dataJson["userId"]+'">'+$.trim(dataJson['userName'])+'</a>';
            dataTable+='</td>';
            dataTable+='<td>'+$.trim(dataJson['email'])+'</td>';
            dataTable += '<td><div id="edit1" class="btn-group pull-right" style="float:left !important;" >'
            + '<button class="btn btn-default btn-xs dropdown-toggle '+cls+'" type="button" data-toggle="dropdown" aria-expanded="false">'
            + '操作 <span class="caret"></span></button>'
            + '<ul class="dropdown-menu" role="menu" >';
            dataTable += '<shiro:hasPermission name="user:delete"><li><a href = "javascript:void(0)" data-id="'+dataJson['userId']+'"data-toggle="modal" data-target="#delModal" class="deployDel">删除</a></li></shiro:hasPermission>'
            + '<shiro:hasPermission name="user:edit"><li class=""><a href = "toUpdate/'+ dataJson['userId'] +'"">更新</a></li></shiro:hasPermission>'
            + '</ul></div></td>';                          
          }
          return dataTable;
        },
        pageName:"用户"
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
        source: "./getSearchHints",
        appendTo: ".body",
        autoFocus:true
      });
      var userId = ''
      $(document).on('click', '.deployDel', function() {
          userId = $(this).attr('data-id');
      
      });
      $(document).on('click', '#delModal .btn-primary', function() {
        if(userId !== undefined){
          $.ajax({
            url: './delete',
            type: 'get',
            dataType: 'json',
            data: {'userId':userId},
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
      
      $(document).on('click', '#edit1 button', function() {
    	  var item=$("#edit1:has(li)").length;
    	   	console.log(item);
    	  	if(item==0){
    	   		$("#edit1 ul").remove(); 
    	  	}
    	  });   
 
  </script>
</body>
</html>
