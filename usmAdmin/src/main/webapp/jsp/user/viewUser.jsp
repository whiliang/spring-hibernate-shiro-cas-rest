<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>View User</title>
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
                     <strong>用户信息</strong>                                                    
                  </h2>
                </div>
                <dl class="dl-horizontal person_dl">
                    <dt>用户名 :</dt>
                    <dd>
                      ${user.userName }
                    </dd>
                    <dt>角色:</dt>
                    <dd>
                          <c:forEach items="${user.roleNameWithApp}" var="role">
						     <p style="margin:0">${role }</p> 					          						    
					      </c:forEach>				
                    </dd>
                    <dt>邮箱:</dt>
                    <dd> ${user.email }</dd>
                    <dt>最后登录时间:</dt>
                    <dd>
                       <fmt:formatDate value="${user.loginTime }"  pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                    </dd>
                  </dl>                                
                <div class="form-group" style="margin-left:50px">
                <!-- col-sm-offset-2 col-xs-offset-1  -->
                  <ul class="list-inline  col-sm-12 col-xs-12 col-sm-offset-1">
              <shiro:hasPermission name="user:edit">
                    <li style="margin-bottom:5px">
                      <a href = "<%=request.getContextPath() %>/user/toUpdate/${user.userId } ">
                        <button type="button" class="btn btn-primary">
                          编辑
                        </button>
                      </a>
                    </li>
              </shiro:hasPermission>
              <shiro:hasPermission name="user:changeUserPassword">
                     <li style="margin-bottom:5px">
                      <a href="<%=request.getContextPath() %>/user/toChangeUserPwd/${user.userId } " >
                        <button type="button" class="btn btn-primary">
                          重置密码
                        </button>
                      </a>
                    </li>
               </shiro:hasPermission>
               <shiro:hasPermission name="user:delete">
                    <li style="margin-bottom:5px">
                      <a href = "javascript:void(0)" data-id="${user.userId }" data-toggle="modal" data-target="#delModal" class="deployDel">
                        <button type="button" class="btn btn-primary">
                          删除
                        </button>
                      </a>
                    </li>
               </shiro:hasPermission>
                    <li style="margin-bottom:5px">
                      <a href="<%=request.getContextPath() %>/user/list">
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
var userId = ''
    $(document).on('click', '.deployDel', function() {
        userId = $(this).attr('data-id');
    });
    $(document).on('click', '#delModal .btn-primary', function() {
      if(userId !== undefined){
        $.ajax({
          url: '../delete',
          type: 'get',
          dataType: 'json',
          data: {'userId':userId},
          success:function(data){                              
          
              if(data.busiStatus == 'success'){
                createAlert({
                  statusCode:200,
                  message:data.message
                });                   
                setTimeout(function(){
                    isClick = false;
                    window.location.href = '<%=request.getContextPath() %>/user/list';
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
</script>
<script>
  $(function(){
  	var judge=${user.isSys};
  	console.log("bool="+judge);
  	if(judge==1){
  		$(".form-group .btn:not(:last)").attr('disabled','true'); 
  	}
  	
  }) 
</script>
</body>
</html>