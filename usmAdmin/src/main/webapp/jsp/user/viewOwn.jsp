<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>View Own</title>
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
                     <strong>管理员个人信息</strong>                   
                  </h2>
                </div>
                <dl class="dl-horizontal person_dl">
                    <dt>用户名 :</dt>
                    <dd>
                      ${user.userName }
                    </dd>                   
                    <dt>邮箱:</dt>
                    <dd> ${user.email }</dd>                   
                  </dl>                                
                <div class="form-group" style="margin-left:50px">
                  <ul class="list-inline  col-sm-12 col-xs-offset-1 ">
                    <li style="margin-bottom:5px">
                      <a href = "<%=request.getContextPath() %>/user/toUpdateOwn">
                        <button type="button" class="btn btn-primary">
                          编辑
                        </button>
                      </a>
                    </li>
                    <li style="margin-bottom:5px">
                      <a href="<%=request.getContextPath() %>/user/list">
                        <button type="button" class="btn btn-primary">
                          返回
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