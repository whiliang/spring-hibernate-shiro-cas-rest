<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>Add Role</title>
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
              <form id="add_role_form" class="form-horizontal" role="form" method="POST" action="" autocomplete="off" onsubmit="return submitForm()">
                <div class="page-heading" >
                  <h2 > <strong>添加角色</strong>
                    <small></small>
                  </h2>
                </div>
                <div class="form-group">
                  <label for="roleName" class="col-xs-3 col-sm-2 control-label breakword-label">角色名</label>
                  <div class="col-xs-7 col-sm-3">
                    <input type="text" name="roleName" id="roleName" class="required numberslettersonly form-control" maxlength="15" value="${role.roleName }" placeholder="输入角色名"></div>
                </div>
                
                <div class="form-group">
                  <label for="application" class="col-xs-3 col-sm-2 control-label breakword-label">所属应用</label>
                  <div class="col-xs-7 col-sm-3">
                    <select name="appId" class="required form-control application">
                       <c:forEach items="${ appVos}" var="appVo">                           
                            <option value="${ appVo.appId}">${ appVo.appName}</option>						  
					    </c:forEach>                       
                    </select>
                  </div>
                </div>
                 <div class="form-group " >
                  <label for="role-name" class="col-xs-3 col-sm-2 control-label breakword-label">权限</label>
                  <div class="col-xs-7 col-sm-3">
                      <div class="permission">                       
                      </div>
                  </div>
                </div>
                <div class="form-group">
                  <div class="col-sm-offset-2 col-sm-5 col-xs-offset-3 col-xs-12">
                    <button type="submit"  class="btn btn-primary">添加</button>
                        <a href="javascript:history.go(-1)"><button type="button"  class="btn btn-primary">取消</button></a>
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

  /* 跳转到该页面时即默认应用下拉框中的第一个选项是选中状态，然后显示对应的权限信息 */
  $(function(){	 
		//获取select第一个option中的value值即对应的id
	  var appid=$("select[name='appId'] option:eq(0)").val();		
	  //发送ajax请求
	  $.ajax({
		  url: '<%=request.getContextPath()%>/role/getPermission',
          type: 'POST',
          dataType: 'json',          
          data:{'appId':appid},
          success:function(json){        	 
        	 for(var j=0;j<json.length;j++){
        		 var dataJson=json[j];
        		 $(".permission").append(
                    "<span class='role-name'>"+dataJson['cnName']+'<input class="role-checkbox" name="assignedPermissions" value="'+dataJson['permissionId']+'" type="checkbox" /></span>'
        		                     );
        		 console.log(dataJson['permissionId']);
        		           } },
          error:function(data){
        	 return false;  
                             }
	         });
             }) 
    	     
//用户点击下拉框后动态显示权限信息	  
    $(function(){
		//给select绑定一个change函数
        $(".application").change(function(){
        	//获取选中的应用对应的id值
        	var item= $(this).val(); 
        	 $.ajax({
      		  url: '<%=request.getContextPath()%>/role/getPermission',
                type: 'POST',
                dataType: 'json',          
                data:{'appId':item},
                success:function(json){ 
                $(".permission").empty();	
               	 for(var j=0;j<json.length;j++){
               		 var dataJson=json[j];               		 	
               		 $(".permission").append(
                           "<span class='role-name'>"+dataJson['cnName']+"<input class='role-checkbox' name='assignedPermissions' value='"+dataJson['permissionId']+"' type='checkbox' /></span> "
               		 );
               		 console.log(dataJson['permissionId']);
               	 }
                 },
                error:function(data){
              	 return false;  
                }
               }); 
         
         });
		})
  //样式添加
		$(function(){
			$(document).on('click','.role-name',function(){
			      var $this = $(this);
			      if($this.hasClass('active')){
			        $this.find('.role-checkbox')[0].checked =false;
			        $this.removeClass('active');
			                                  }
			      else{
			        $this.find('.role-checkbox')[0].checked =true;
			        $this.addClass('active');
			          }
			                                             })		
		  }) 

//用户提交添加角色表单，实现ajax请求		
		var isClick = false;
        function submitForm() {
          if (isClick) {
            return false;
          }
          
          var bool = false;
          $("input[name='assignedPermissions']").each(function(){
            if($(this).prop("checked"))
            {
              bool = true;
            }
          });
          
          if($('#add_role_form').valid()){
            if(!bool){      
              createAlert({"message":"你必须选择一个权限"});
              return false;
            }else{
              //$('#add_role_form').submit();
              isClick = true;
              $.ajax({
                url: '<%=request.getContextPath()%>/role/addRole',
                type: 'POST',
                dataType: 'json',
                data: $('#add_role_form').serialize(),
                success:function(data){                  	
                  if(data.busiStatus == 'success'){
                    createAlert({
                      statusCode:200,
                      message:data.message
                    })
                    
                    setTimeout(function(){
                      isClick = false;
                      window.location.href = "<%=request.getContextPath()%>/jsp/role/listRole.jsp";
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
