<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>View Role</title>
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
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 main" id="rightContent" style="padding:20px">                                          
                <div class="page-heading" >
                  <h2 > 
                     <strong>角色信息</strong>                   
                  </h2>
                </div>
                <dl class="dl-horizontal person_dl">
                    <dt>角色名 :</dt>
                    <dd class="role_names">
                      ${roleVo.roleName }
                    </dd>
                    <dt>所属应用:</dt>
                    <dd>    
                        ${roleVo.appName}
                    </dd>
                    <dt>权限:</dt>
                    <dd>
                        <c:forEach items="${roleVo.permissionName}" var="role">
						     <p style="margin:0">${role }</p> 					          						    
					      </c:forEach>	
                    </dd>
                   
                  </dl>                                
                <div class="form-group"  style="margin-left:50px">
                  <ul class="list-inline  col-sm-12 col-xs-12 col-sm-offset-1">
                  <shiro:hasPermission name="role:edit">
                    <li style="margin-bottom:5px">
                      <a href = "<%=request.getContextPath() %>/role/toUpdate/${roleVo.roleId}">
                        <button type="button" class="btn btn-primary">
                          编辑
                        </button>
                      </a>
                    </li> 
                 </shiro:hasPermission>
                 <shiro:hasPermission name="role:delete">                   
                    <li style="margin-bottom:5px">
                      <a href = "javascript:void(0)" data-id="${roleVo.roleId}" data-toggle="modal" data-target="#delModal" class="deployDel">
                        <button type="button" class="btn btn-primary">
                          删除
                        </button>
                      </a>
                    </li>
                     </shiro:hasPermission>                   
                    <li style="margin-bottom:5px">
                      <a href="javascript:history.go(-1)">
                        <button type="button" class="btn btn-primary">
                          取消
                        </button>
                      </a>
                    </li>                   
                  </ul>
                </div>

            </div>
          </div>
        </div>
      </div>
    </div>
    <ul class="ui-autocomplete ui-front ui-menu ui-widget ui-widget-content" id="ui-id-1" tabindex="0" style="display: none;"></ul>
  </div>
  <!-- END -->
  <!-- FOOTER -->
   <%@ include file="../inc/footer.jsp" %>
  <!-- END -->
<script type="text/javascript">
var roleId = ''
    $(document).on('click', '.deployDel', function() {
    	roleId = $(this).attr('data-id');
    });
    $(document).on('click', '#delModal .btn-primary', function() {
      if(roleId!== undefined){
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
                setTimeout(function(){
                    isClick = false;
                    window.location.href ="<%=request.getContextPath()%>/jsp/role/listRole.jsp";
                  },2000);
                
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
   
//若查看的是administrator角色详情页，则禁用按钮组
$(function(){    	
  	if($.trim($(".role_names").text())=='administrator'){
  		 $(".form-group .btn:not(:last)").attr('disabled','true');  
  	}  	 	
  })
</script>
</body>
</html>