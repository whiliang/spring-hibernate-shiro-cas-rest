<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <script src="<%=request.getContextPath() %>/styles/libs/js/jquery-min.js"></script>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
  <!-- NAVBAR START -->
  <div class="container admin-header">
    <div class="logo-brand header sidebar rows admin-logo-brand">
      <div class="logo">
        <h1>
          <img alt="PAX" src="<%=request.getContextPath()%>/styles/images/icon.png">
          <a href="#">UMS-Admin</a>
        </h1>
      </div>
    </div>
    <div class="header content rows-content-header admin-navbar">
      <!-- mask -->
      <div class="mask-collapse"></div>
      <!-- BEGIN NAVBAR CONTENT-->
      <div class="navbar  navbar-default" role="navigation">
        <div class="container">
          <!-- Navbar header -->
          <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"> <i class="iconfont">&#xe600;</i>
            </button>
          </div>
          <!-- End div .navbar-header -->

          <!-- Navbar collapse -->
          <div class="navbar-collapse collapse">
            <!-- left-navbar -->
            <ul class="nav navbar-nav margin-nav" >
              <li class="dropdown"  id="users">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  用户 <b class="caret"></b>
                </a>
                <ul class="dropdown-menu" >              
                  <li>
                    <a href="<%=request.getContextPath() %>/user/list">用户管理</a>
                  </li>         
				<shiro:hasPermission name="user:add">
                  <li class="divider"></li>
                  <li>
                    <a href="<%=request.getContextPath() %>/user/toAdd"> <i class="glyphicon glyphicon-plus"></i>
                      &nbsp;添加用户
                    </a>
                  </li>
				</shiro:hasPermission>
                </ul>
              </li>
              <li class="dropdown" id="roles">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  角色 <b class="caret"></b>
                </a>
                <ul class="dropdown-menu" > 
                  <shiro:hasPermission name="role:query">         
                  <li> 
                    <a href="<%=request.getContextPath()%>/jsp/role/listRole.jsp">角色管理</a>
                    
                  </li> 
                   </shiro:hasPermission>  
                   <shiro:hasPermission name="role:add">             
                  <li class="divider"></li>              
                  <li>
                    <a href="<%=request.getContextPath()%>/role/toAdd">
                      <i class="glyphicon glyphicon-plus"></i>
                      &nbsp;添加角色
                    </a>
                  </li>
                </shiro:hasPermission> 
                </ul>
              </li>

            </ul>
            <!-- right-navbar -->
            <ul class="nav navbar-nav navbar-right">

              <!-- Dropdown User session -->

              <li class="dropdown left-divider">
                <a data-toggle="dropdown" class="dropdown-toggle user-menu navbar-li-a" href="#">
                  <span class='userName' id="navuser">${userName }</span>
                  <i class="glyphicon glyphicon-user"></i>
                </a>
                <ul class="dropdown-menu">
                  <li>
                    <a href="<%=request.getContextPath() %>/user/viewOwn">个人信息</a>
                  </li>
                  <li class="mdy">
                    <a href="<%=request.getContextPath() %>/user/toChangeOwnPwd" >修改密码</a>
                  </li>
                  <li class="divider"></li>
                  <li>
                    <a href="<%=request.getContextPath() %>/logout">
                      <i class="dropdown-icon fa fa-power-off"></i>
                      &nbsp;&nbsp;登出
                    </a>
                  </li>
                  
                </ul>

              </li>

              <li class="dropdown">
                <a data-toggle="dropdown" class="dropdown-toggle user-menu" href="#">
                  <i class="iconfont">&#xe601;</i>
                </a>
                <ul class="dropdown-menu">
                <li>
                    <a href="#">
                      <span class="pull-right">
                        <i class="fa fa-cogs"></i>
                      </span>
                      Other App
                    </a>
                  </li>

                  <li class="divider"></li>

                  <li>
                    <a href="#">
                      <span class="pull-right">
                        <i class="fa fa-cogs"></i>
                      </span>
                      H5 Designer
                    </a>
                  </li>
                </ul>
              </li>

              <li class="dropdown">
                <a data-toggle="dropdown" class="dropdown-toggle user-menu" href="#">

                  <i class="glyphicon glyphicon-info-sign"></i>
                </a>
                <ul class="dropdown-menu">
                  <li>
                    <a data-target="#AboutModal" data-toggle="modal">&nbsp;&nbsp;关于UMS</a>
                  </li>
                </ul>
              </li>
            </ul>
          </div>
        </div>
        <!-- End div .container --> </div>
      <!-- END NAVBAR CONTENT--> </div>
  </div>
  <!-- END -->
<script type="text/javascript">  
/* 遍历所有的li，如果取到的元素都为空，则隐藏这个ul */
    $(function(){
    	var item=$("#roles:has(li)").length;    	
    	console.log(item);
    	if(item==0){
    		$("#roles ul").remove();
    	}   	
    })
    
    


</script> 









