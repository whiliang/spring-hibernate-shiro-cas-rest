<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"
    pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Out of Date</title>
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
	   <img src="<%=request.getContextPath()%>/styles/images/expire.png" alt="nofind the user" width="50" height="50"> 
	   <small class="ld">哎呀......该链接已经失效了~</small>	  
	</div>	
</body>
</html>