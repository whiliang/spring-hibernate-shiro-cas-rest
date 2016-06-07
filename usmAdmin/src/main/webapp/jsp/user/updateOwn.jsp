<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update Own</title>
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
              <form id="updateOwn_form" class="form-horizontal" role="form" method="POST" action="" autocomplete="off" 
              onsubmit="return submitForm()">
                <div class="page-heading" >
                  <h2 > <strong>编辑管理员</strong>
                    <small></small>
                  </h2>
                </div>
                  <input name="userId" value="${user.userId }" type="hidden" >   
                <div class="form-group">
                  <label for="username" class="col-xs-3 col-sm-2 control-label breakword-label">用户名</label>
                  <div class="col-xs-7 col-sm-3">
                    <input type="text" name="userName" id="username" class="required numberslettersonly form-control " maxlength="50" value="${user.userName }" placeholder="输入用户名" readonly></div>
                </div>           
                <div class="form-group">
                  <label for="email" class="col-xs-3 col-sm-2 control-label breakword-label">邮箱</label>
                  <div class="col-xs-7 col-sm-3">
                    <input type="text" name="email" id="email" class="required emailCheck form-control" value="${user.email }" maxlength="50" value="" placeholder="输入邮箱" ></div>
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
var isClick = false;
function submitForm() {
  if (isClick) {
    return false;
  }
  if($('#updateOwn_form').valid()){
      isClick = true;
      $.ajax({
        url: '<%=request.getContextPath() %>/user/updateOwn',
        type: 'POST',
        dataType: 'json',
        data: $('#updateOwn_form').serialize(),
        success:function(data){

          if(data.busiStatus == 'success'){
            createAlert({
              statusCode:200,
              message:data.message
            })
            setTimeout(function(){
              isClick = false;
              window.location.href = '<%=request.getContextPath() %>/user/viewOwn';
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
  }else{
     return false;
  }
    
}

</script>				
</body>
</html>