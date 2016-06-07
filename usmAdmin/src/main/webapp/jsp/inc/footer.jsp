  <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <!-- 删除提示 -->
  <div class="modal fade " id="delModal" tabindex="-1" role="dialog" aria-labelledby="delModalLabel">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title">温馨提示</h4>
        </div>
        <div class="modal-body">
          <p>确定要删除吗？</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->
  <!-- END -->
  <!-- 关于我们 -->
  <div class="modal fade " id="AboutModal" tabindex="-1" role="dialog" aria-labelledby="AboutModalLabel">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title">关于UMS</h4>
        </div>
        <div class="modal-body">
          <p></p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->
  <!-- end -->
    <!-- FOOTER -->
  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
 <script src="<%=request.getContextPath() %>/styles/libs/js/jquery-min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
  <script src="<%=request.getContextPath() %>/styles/libs/js/bootstrap.min.js"></script>
  <script src="<%=request.getContextPath() %>/styles/libs/js/jquery-ui.min.js"></script>
  <script src="<%=request.getContextPath() %>/styles/libs/js/jquery.bootstrap-growl.js"></script>
  <script src="<%=request.getContextPath() %>/styles/js/bootPage.js"></script>
  <script src="<%=request.getContextPath() %>/styles/libs/js/jquery.validate.min.js"></script>
  <script src="<%=request.getContextPath() %>/styles/js/additional-methods.js"></script>

  <script type="text/javascript">
$(function(){
    //close browser form's autocomplete function, because this function in IE and Chrome has bug.
    $('form').attr('autocomplete', 'off');

    /*Navbar*/
    $('.navbar-collapse').on('shown.bs.collapse', function () {
      $('.mask-collapse').show();
    })
    $('.navbar-collapse').on('hidden.bs.collapse', function () {
      $('.mask-collapse').hide();
    })
    $('.mask-collapse').click(function() {
      $('.navbar-collapse').collapse('hide');
    });
    getLocalStorage();
})
/*
    Alert
  */
  function createAlert(json) {  
      var MSG = json.message,Type;
      if (json.statusCode == 300){
        Type = 'danger';
      }else if(json.statusCode == 301) {
        Type = 'danger';
      }
      else if(json.statusCode == 200) {
        Type = 'success';
      }
      else {
        Type = 'danger';
      }
      $.bootstrapGrowl(MSG, {
              type: Type,
              offset: {from: 'top', amount: 50},
              width: 'auto',
      });
    }
  /*处理用户名显示不出来的问题*/
  function getLocalStorage(){
    var storage = window.localStorage; 
    if(!storage){
      return ;
    }
    var userName = '${userName }';
    if(userName==''||userName == null || userName == undefined ){
      var userName = storage.getItem('userName');
      if(userName){
          $('.userName').text(userName);
      }
    }else{
      storage.setItem('userName',userName);
    }
  }
  
  $(function(){	   
	    var sbt=$("#navuser").text();
	   console.log(sbt);
	   if(sbt=="admin"){		   
		   $('.mdy').addClass("disabled");
		   
		   $('.mdy').on('click',function(){
			   return false;
		   })
	   }	   	   
 }) 

</script>

  <!-- END -->
