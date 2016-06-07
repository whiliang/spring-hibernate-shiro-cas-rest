<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"
    pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Access Denied</title>
<style type="text/css">
   .permis{
       width:300px;
       height:300px;
    
       text-align:center;
       margin-top:160px;
       margin-left:40%;
   }
  
</style>
</head>
<body>
	<div class="permis">
	   <img src="<%=request.getContextPath()%>/styles/images/permission.png" alt="permission" width="50" height="50"> 
	   <small class="ld">哎呀......您还没有权限，请联系管理员</small>
	   <h3 id="countdown"></h3>
	</div>
	
<script type="text/javascript">  
window.onload=function(){
  var odiv=document.getElementById("countdown");
  var timer=null;
  function Countdown(count,obj){
    return function(){
      if(count>1)
      {
        count=count-1;
        obj.innerHTML=count+"s后跳转到指定页面";
      }
      else
      {
        location.href="<%=request.getContextPath() %>/user/list";
        clearInterval(timer);
      }
    }
  }
  timer=setInterval(Countdown(4,odiv),1000);
}
</script> 	
</body>
</html>