<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update User</title>
<%@ include file="../inc/header.jsp" %>
</head>
<body>

<%@ include file="../inc/nav.jsp" %>

  <div class="body content rows scroll-y admin-body">

    <div class="row">
      <div class="col-xs-12">
        <div class="container-fluid">
          <div class="row">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 main" id="rightContent" style="padding:20px">
              <form id="updateuser_form" class="form-horizontal" role="form" method="POST" action="updateUser" autocomplete="off" onsubmit="return submitForm()">
                <div class="page-heading" >
                  <h2 > <strong>编辑用户</strong>
                    <small></small>
                  </h2>
                </div>
                <div class="form-group">
                  <label for="username" class="col-xs-3 col-sm-2 control-label breakword-label">用户名</label>
                  <div class="col-xs-7 col-sm-3">
                    <input type="text" name="userName" id="userName" class=" form-control" value=" ${user.userName }" maxlength="50" value="111" readonly></div>
                </div>
                 <input name="userId" value="${user.userId }" type="hidden" >   
                <div class="form-group">
                  <label for="email" class="col-xs-3 col-sm-2 control-label breakword-label">邮箱</label>
                  <div class="col-xs-7 col-sm-3">
                    <input type="text" name="email" id="email" class="required emailCheck form-control" value="${user.email }" maxlength="50" value="" placeholder="输入邮箱" ></div>
                </div>
                <div class="form-group ">
                  <label for="assignedRoles" class="col-xs-3 col-sm-2 control-label breakword-label">角色</label>            
                   <div class="col-xs-12 col-sm-5">                
                	<c:forEach items="${roleVos }" var="roleVo">                                         
                  	  <c:choose>
                  	    <c:when test="${roleVo.active==true }">
                               <span class='role-name active'>${roleVo.roleNameWithApp }
                               <input class="role-checkbox" name="assignedRoles" value="${roleVo.roleId }" type='checkbox' checked/></span>                  	 
                        </c:when>
                        <c:otherwise>
                               <span class='role-name'>${roleVo.roleNameWithApp }
					           <input class="role-checkbox" name="assignedRoles" value="${roleVo.roleId }" type='checkbox' /></span>
                        </c:otherwise>              
					  </c:choose>										
					</c:forEach>                    
                  </div>
                </div>

                <div class="form-group">
                  <div class="col-sm-offset-2 col-sm-5 col-xs-offset-3 col-xs-12">
                    <button type="submit" class="btn btn-primary">保存</button>                 
                    <a href="javascript:history.go(-1)">
                         <button type="button" class="btn btn-primary">取消</button>
                    </a>
                  </div>
                </div>
              </form>

            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
    <%@ include file="../inc/footer.jsp" %>
  <!-- END -->			
<script type="text/javascript">

$(function(){		
    $('.role-name').click(function() {
      var $this = $(this);
      if($this.hasClass('active')){
        $this.find('.role-checkbox')[0].checked =false;
        $this.removeClass('active');
      }else{
        $this.find('.role-checkbox')[0].checked =true;
        $this.addClass('active');
      }
    });    

  })

 var isClick = false;
        function submitForm() {
          if (isClick) {
            return false;
          }
          
          var bool = false;
          $("input[name='assignedRoles']").each(function(){
            if($(this).prop("checked"))
            {
              bool = true;
            }
          });
          
          if($('#updateuser_form').valid()){
            if(!bool){      
              createAlert({"message":"你必须选择一个角色"});
              return false;
            }else{
              //$('#updateuser_form').submit();
              isClick = true;
              $.ajax({
                url: '../updateUser',
                type: 'POST',
                dataType: 'json',
                data: $('#updateuser_form').serialize(),
                success:function(data){
                  
                  if(data.busiStatus == 'success'){
                    createAlert({
                      statusCode:200,
                      message:data.message
                    })
                    
                    setTimeout(function(){
                      isClick = false;
                      window.location.href = '<%=request.getContextPath() %>/user/viewUser/${user.userId }';
                    },2000);
                  }else{

                    createAlert({
                      statusCode:300,
                      message:String(data.errorMsg)
                    })
                    isClick = false;
                  }
                  
                },
                error:function(data){
                  isClick = false;
                  console.log(data)
                  createAlert({
                      statusCode:300,
                      message:"出错了,请重试"
                    })
                  
                }
              });
              return false;
              
            }
          }else{
             return false;
          }
            
        }


</script>
		
		
		
</body>
</html>