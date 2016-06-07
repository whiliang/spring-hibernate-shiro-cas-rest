<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>Add User</title>
<%@ include file="../inc/header.jsp" %>
<body>
	<%@ include file="../inc/nav.jsp" %>
  <!-- CONTENT BEGIN -->
  <div class="body content rows scroll-y admin-body">
    <div class="row">
      <div class="col-xs-12">
        <div class="container-fluid">
          <div class="row">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 main" id="rightContent" style="padding:20px">
              <form id="add_user_form" class="form-horizontal" role="form" method="POST" action="addUser" autocomplete="off" onsubmit="return submitForm()">
                <div class="page-heading" >
                  <h2 > <strong>添加用户</strong>
                    <small></small>
                  </h2>
                </div>
                <div class="form-group">
                  <label for="username" class="col-xs-3 col-sm-2 control-label breakword-label">用户名</label>
                  <div class="col-xs-7 col-sm-3">
                    <input type="text" name="userName" id="username" class="required numberslettersonly form-control" maxlength="50" value="${user.userName }" placeholder="输入用户名"></div>
                </div>
                <div class="form-group">
                  <label for="email" class="col-xs-3 col-sm-2 control-label breakword-label">邮箱</label>
                  <div class="col-xs-7 col-sm-3">
                    <input type="text" name="email" id="email" class="required emailCheck form-control" maxlength="50" value="${user.email }" placeholder="输入邮箱"></div>
                </div>
                <div class="form-group ">
                  <label for="email" class="col-xs-3 col-sm-2 control-label breakword-label">角色</label>
                  <div class="col-xs-12 col-sm-5">
                  	<c:forEach items="${roleVos }" var="roleVo">
						<span class='role-name'>${roleVo.roleNameWithApp }<input class="role-checkbox" name="assignedRoles" value="${roleVo.roleId }" type='checkbox' /></span>  
					</c:forEach>
                    

                  </div>
                </div>
                <div class="form-group">
                  <div class="col-sm-offset-2 col-sm-5">
                    <label class="breakword-label">
                      <input id="checkBox_sendEmail" name="setPwdByEmail" type="checkbox" checked="checked" value="1">&nbsp;发送一封电子邮件给您已经创建用户</label>
                  </div>
                </div>

                <!-- this content is hidden by default  ..begin-->
                <div id="other_inputs" style="display: none;">
                  <div class="form-group">
                    <label for="lastname" class="col-xs-3 col-sm-2 control-label">密码</label>
                    <div class="col-xs-7 col-sm-3">
                      <input type="password" id="passwordInput" name="password" class="required form-control passwordCheck" maxlength="8" value="${user.password }" placeholder="输入密码"></div>
                  </div>
                  <div class="form-group">
                    <label for="lastname" class="col-xs-3 col-sm-2 control-label">确认密码</label>
                    <div class="col-xs-7 col-sm-3">
                      <input type="password" id="confirmPasswordInput" name="confirmPassword" class="required form-control passwordCheck" equalto="#passwordInput"  maxlength="8" placeholder="输入确认密码"></div>
                  </div>
                </div>
                <!-- this content is hidden by default  ..end-->

                <div class="form-group">
                  <div class="col-sm-offset-2 col-sm-5 col-xs-offset-3 col-xs-12">
                    <button type="sumbit"  class="btn btn-primary">添加</button>
                    <a href="<%=request.getContextPath() %>/user/list"><button type="button"  class="btn btn-primary">取消</button></a>
                  </div>
                </div>
              </form>

            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- END -->
  <%@ include file="../inc/footer.jsp" %>

  <script type="text/javascript">
      $(function(){
        $("#other_inputs").hide();
        $("#checkBox_sendEmail").change(function() {
          $("#other_inputs").toggle();
        });
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
          
          if($('#add_user_form').valid()){
            if(!bool){      
              createAlert({"message":"你必须选择一个角色"});
              return false;
            }else{
              //$('#add_user_form').submit();
              isClick = true;
              $.ajax({
                url: './addUser',
                type: 'POST',
                dataType: 'json',
                data: $('#add_user_form').serialize(),
                success:function(data){
                  
                  if(data.busiStatus == 'success'){
                    createAlert({
                      statusCode:200,
                      message:data.message
                    })
                    
                    setTimeout(function(){
                      isClick = false;
                      window.location.href = 'list';
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