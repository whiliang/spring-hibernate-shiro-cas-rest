/**
	 * Description: get Table Data 
	 * @author zhu
     * @date 2014-10-29
     */
var dataDescription={
	/***
	 * severity: returns the number corresponding to the data.
	 * The num parameters represent the Arabic numeral data returned by the back-end.
	***/
	severity:function(num){
		if(num==0)
			return 'Emergency';
		else if(num==1)
			return 'Alert';
		else if(num==2)
			return 'Critical';
		else if(num==3)
			return 'Error';
		else if(num==4)
			return 'Warning';
		else if(num==5)
			return 'Notice';
		else if(num==6)
			return 'Informational';
		else if(num==7)
			return 'Debug';
	},
	/**
	 * Time format conversion.
	 * The time parameters represent the time format returned by the back-end.
	 **/
	createDate:function(time){
		var d = new Date();//Time objthe attribute of date is json objectect,Now get the current time.
		d.setTime(time);//Translate into actual time.
		var year=d.getFullYear();//Years of data.
		var month=d.getMonth()+1;//Month of data.
		month=month<10?'0'+month:month;
		var date=d.getDate();//Days of data.
		date=date<10?'0'+date:date;
		var hours=d.getHours();//Hours of data.
		hours=hours<10?'0'+hours:hours;
		var minutes=d.getMinutes();//Minutes of data.
		minutes=minutes<10?'0'+minutes:minutes;
		var seconds=d.getSeconds();//Seconds of data.
		seconds=seconds<10?'0'+seconds:seconds;
		/*
		 * The result returns a Json object data.
		 * The attribute of date is json object,it represent "Year-month-day" such format.
		 * The attribute of dateTime is Json object,it represent "hours:minutes:seconds" such format.
		 */		
		return {
			date:year+'-'+month+'-'+date,
			dateTime:hours+':'+minutes+':'+seconds
		}
	}
	
}
var pageFun={
		defaultPage:this,//The default page object.
		type: "POST",//Initializes the request.
		url: "event/ajaxlist",//Initializes the request address.
		/*
		 * Initialize the data,Sent to the back-end data.
		 * The num parameters represent the current page number.
		 * The pageSelNum parameters represent the number of each page can display at most.
		 */

		sendData:function(num,pageSelNum,orderField,orderDirection){
			if(!orderField && !orderDirection){
				return "pageNum="+num+"&numPerPage="+pageSelNum;
			}else{
				return "pageNum="+num+"&numPerPage="+pageSelNum+"&orderField="+orderField+"&orderDirection="+orderDirection;
			}
			
		},
		/*
		 * A line corresponding to insert data from the json.
		 * The json parameters represent an array type data, the internal element is a json object
		 */
		dataTableFun:function(json){
			var dataTable='';//write in table
			for(var j=0;j<json.length;j++)
			{
				var dataJson=typeof json[j] === 'object'?json[j]:jQuery.parseJSON(json[j]);
				dataTable+='<tr><td>'+dataDescription.severity(dataJson['severity'])+'</td>';
				dataTable+='<td>'+dataDescription.createDate(dataJson['eventTime'])['date']+'&nbsp;&nbsp;&nbsp;'+dataDescription.createDate(dataJson['eventTime'])['dateTime']+'</td>';
				dataTable+='<td><a href="../terminal/view/'+$.trim(dataJson['source'])+'">' + $.trim(dataJson['source']) + '</a></td>';
				dataTable+='<td>'+$.trim(dataJson['message'])+'</td></tr>';
				
			}
			return dataTable;
		},
		pageName:'events',//Initialize the page name
		selNode:'select[name="example_length"]',//Initialize the drop-down menu node
		tableNode:'#table_id',//Initialize the table node
		/*
		 * Excessive paging appear ellipsis
		 * The num parameters represent the current page number.
		 * The iTotal parameters represent the total number of pages.
		 */
		pageEllipsis:function(num,iTotal){
			if(num<=5)
			{	
				var eqNum;
				this.pageProcessing(num,iTotal,this.pageClass);
				
				if(num<=3)
					eqNum=4;
				else//num==4 || num==5
					eqNum=num+1;				
				$('.boot-page').each(function(){
					$(this).children(".pageNum").eq(eqNum).nextAll('.pageNum').remove();
					$(this).children(".pageNum").eq(eqNum).after('<li class="paginate_button"><a href="javascript:void(0)" style="border:0 none; background:none;cursor:default;">...</a></li>');
				});
				
				//The current page style
				$(".boot-page").each(function(){
					$(this).children('.pageNum').eq(num-1).addClass('active');
				});
			}
			else
			{
				var parameterNumSart,parameterNumEnd;
				var liTotal='';//Li node score elements combined
				 liTotal+='<li class="paginate_button first"><a href="javascript:void(0)">&lt;&lt;&nbsp;First</a></li>';
				 liTotal+='<li class="paginate_button previous"><a href="javascript:void(0)">&lt;&nbsp;Previous</a></li>';
				 liTotal+='<li class="paginate_button pageNum"><a href="javascript:void(0)">1</a></li>';
				 liTotal+='<li class="paginate_button pageNum"><a href="javascript:void(0)">2</a></li>';
				 liTotal+='<li class="paginate_button"><a href="javascript:void(0)" style="border:0 none; background:none;cursor:default;">...</a></li>'; 
				if(iTotal-num<=3)//The last four pages
				{
					parameterNumSart=iTotal-5;
					parameterNumEnd=iTotal;
				}
				else//More than 5 pages to the last page 5
				{
					parameterNumSart=num-2;
					parameterNumEnd=num+2;
				}

				 for(var i=parameterNumSart;i<=parameterNumEnd;i++)//Written to the paging
				{
					if(num==i)
						liTotal+='<li class="paginate_button pageNum active"><a href="javascript:void(0)">'+i+'</a></li>';
					else
						 liTotal+='<li class="paginate_button pageNum"><a href="javascript:void(0)">'+i+'</a></li>';
				}
				 if(iTotal-num>3)
				 	liTotal+='<li class="paginate_button"><a href="javascript:void(0)" style="border:0 none; background:none;cursor:default;">...</a></li>'; 
				 liTotal+='<li class="paginate_button next"><a href="javascript:void(0)">Next&nbsp;&gt;</a></li>';
				 liTotal+='<li class="paginate_button last"><a href="javascript:void(0)">Last&nbsp;&gt;&gt;</a></li>';
				 $('.boot-page').html(liTotal);//Paging insert 
				 
				 this.pageClass(num,iTotal);
			}
			
		},
		/*
		 * Paging processing
		 * The num parameters represent the current page number.
		 * The iTotal parameters represent the total number of pages.
		 * The callback parameters represent the callback function.
		 */
		pageProcessing:function(num,iTotal,callback){	
			 var liTotal='';//Li node score elements combined
			 liTotal+='<li class="paginate_button first"><a href="javascript:void(0)">&lt;&lt;&nbsp;首页</a></li>';
			 liTotal+='<li class="paginate_button previous"><a href="javascript:void(0)">&lt;&nbsp;上一页</a></li>';
			 for(var i=1;i<=iTotal;i++)//Written to the paging
			{
				 liTotal+='<li class="paginate_button pageNum"><a href="javascript:void(0)">'+i+'</a></li>';
			}
			 liTotal+='<li class="paginate_button next"><a href="javascript:void(0)">下一页&nbsp;&gt;</a></li>';
			 liTotal+='<li class="paginate_button last"><a href="javascript:void(0)">尾页&nbsp;&gt;&gt;</a></li>';
			$('.boot-page').html(liTotal);//Paging insert
			if(callback)
				callback(num,iTotal);
		},
		/*
		 * Pagination style effect
		 * The num parameters represent the current page number.
		 * The iTotal parameters represent the total number of pages.
		 */
		pageClass:function(num,iTotal){
			 if(num==1)
			{
				$('.previous').next().addClass('active');
				$('.first,.previous').addClass("disabled");
				if(num==iTotal||iTotal==0){
					$('.next,.last').addClass("disabled");
				}
			}
			 else if(num==iTotal){
				$('.next,.last').addClass("disabled");
				$('.first,.previous').removeClass("disabled");
			 }
			 else{
				 $('.next,.last').removeClass("disabled");
				 $('.first,.previous').removeClass("disabled");
			 }
		},
		/*
		 * Refresh the page and page load the merge function
		 * The num parameters represent the current page number.
		 * The refreshBool parameters represent whether you need to refresh;if you want to refresh than you must set true,or else you must set false.
		 */
		sel:function(num,orderField,orderDirection,refreshBool){
			var obj=this;//the paging object
			var pageSelNum=parseInt($(this.selNode).val());//Each page shows the number of
			var winPageNum=parseInt(window.location.hash.substring(1));//Get the Arabic numerals  after the url address
			if(!num && !winPageNum)
				num=1;
			else if(!num && winPageNum)
				num=winPageNum;
			if(!refreshBool)//The loading pictures shows when the site load。
			{
				var tbodyHeight= $(obj.tableNode).children('tbody').height();
				tbodyHeight=tbodyHeight<60?60:tbodyHeight;
				var noLoadingFlash = $(obj.tableNode).attr("noLoadingFlash");
				if(!noLoadingFlash){			
					$(obj.tableNode).children('tbody').html("<tr class='loading' style='height:"+tbodyHeight+"px'><td width='100%' colspan='"+$(obj.tableNode).children('thead').find('th').length+"'><div class='spinner'><div class='spinner-container container1'><div class='circle1'></div><div class='circle2'></div><div class='circle3'></div><div class='circle4'></div></div><div class='spinner-container container2'><div class='circle1'></div><div class='circle2'></div><div class='circle3'></div><div class='circle4'></div></div><div class='spinner-container container3'><div class='circle1'></div><div class='circle2'></div><div class='circle3'></div><div class='circle4'></div></div></div></td></tr>");
				}
			}
			$.ajax({//ajax
				type: obj.type,//Request type
				 url: obj.url+"?random="+Math.random(),//Request the address
				 data:(function(obj,num,pageSelNum,orderField,orderDirection){ 
				 		if(!orderDirection && !orderDirection){
				 			
				 			return obj.sendData(num,pageSelNum)
				 		}else{
				 			
				 			return obj.sendData(num,pageSelNum,orderField,orderDirection)
				 		}
				 		})(obj,num,pageSelNum,orderField,orderDirection),//Request to send data
				 error:function(){//When the request error
					 $(obj.tableNode).children('tbody').html("<tr><td width='100%' colspan='"+$(obj.tableNode).children('thead').find('th').length+"'><div class='alert alert-danger paxinfo'><a class='btn btn-xs btn-danger pull-right' onclick='location.reload()' href='javascript:void(0)'>无访问权限</a><span class='paxspan'><strong>提示:</strong>你还没有权限访问该页面！</span></div></td></tr>");
				 },
				 dataType:'json',
				 success: function(page){//When the request is successful
						if(!page.items || page.items.length===0)//If the request is successful but there is no data, or other circumstances
						{
							//If org.notFound prompt refresh modal
							if(!page.items && (page.errorCode==='org.notFound' || page.errorCode==='tsnOrGroup.notExist' || page.errorCode==='org.parentNotFound'))
							{
								if($('#orgNotFound').is(':visible'))
									return;
								$(obj.tableNode).children('tbody').html("<tr><td width='100%' colspan='"+$(obj.tableNode).children('thead').find('th').length+"'><div class='alert alert-info paxinfonext'><a class='btn btn-xs btn-primary pull-right' onclick='location.reload()' href='javascript:void(0)'>可以单击“刷新”</a><span  class='paxspannext'><strong>提示:</strong>"+page.message+"</span></div></td></tr>");
								$('#orgNotFound').find('p').html(page.message+"<br />请刷新页面!");
								$('#orgNotFound').modal({backdrop: 'static', keyboard: false,show:true});
								//The countdown to 6 seconds
								orgNotFoundToRefresh(6);

							}
							else
							$(obj.tableNode).children('tbody').html("<tr><td width='100%' colspan='"+$(obj.tableNode).children('thead').find('th').length+"'><div class='alert alert-info paxinfonext'><a class='btn btn-xs btn-primary pull-right' onclick='location.reload()' href='javascript:void(0)'>可以单击“刷新”</a><span  class='paxspannext'><strong>提示:</strong>没有数据!</span></div></td></tr>");
							 obj.pageTotal=0;
							 obj.pageProcessing(num,0,pageFun.pageClass);
							//Display event number
							 $('.boot-total').text('0'+' 匹配 '+obj.pageName);
							 $('.boot-events').text('显示  '+obj.pageName+' 0 到 0');
							 obj.totalCount=null;
							 return;
						}
					
					if(refreshBool!=null)//refresh
					{	
						var totalCount=page.totalCount;//Data to the total number of article
						if(totalCount===obj.totalCount)//According to the total amount of change
							return;
						obj.totalCount=totalCount;

					}

					//And shows how many total items
					 var pageTotal=page.totalCount;//Data to the total number of article
					 var pagePerNum=page.items.length;//Actually received the number of each page
					 var iTotal;//number of total pages 					 
					 if(pageTotal%pageSelNum==0)
						 iTotal=parseInt(pageTotal/pageSelNum);
					 else
						 iTotal=parseInt(pageTotal/pageSelNum)+1;
					
					obj.pageTotal=iTotal;//Record the total number of page number
					obj.totalCount=pageTotal;//Record the data of the total number of article
					
					//Display data number
					$('.boot-total').text(pageTotal+' 匹配 '+obj.pageName);
					 if(num==1)
					{	   					 
						 if(pagePerNum>pageSelNum)
							 $('.boot-events').text('显示  '+obj.pageName+' 1 到 '+pageSelNum);
						 else if(pagePerNum==0)
							 $('.boot-events').text('显示  '+obj.pageName+' 0 到 0');
						 else
							 $('.boot-events').text('显示  '+obj.pageName+' 1 到 '+pagePerNum);
					}
					else if(num>=iTotal)
					{
						num=iTotal;
						$('.boot-events').text('显示  '+obj.pageName+' '+(pageSelNum*(num-1)+1)+' 到 '+pageTotal);
					}
					else
						$('.boot-events').text('显示  '+obj.pageName+' '+(pageSelNum*(num-1)+1)+' 到 '+pageSelNum*num); 
					 
					if(iTotal>10)//Excessive paging appear ellipsis
					{
						obj.pageEllipsis(num,iTotal);
					}
					else
					{
						obj.pageProcessing(num,iTotal,pageFun.pageClass);
						
						//The current page style
						$(".boot-page").each(function(){
							$(this).children('.pageNum').eq(num-1).addClass('active');
						});
					}
					
					//write in data					
					$(obj.tableNode).children('tbody').html(obj.dataTableFun(page.items));
					
					obj.rowTrFun();
					
					
				}
				 
			});
		},
		pageTotal:null,//Total number of pages
		totalCount:null,//Total number of tr
		/*
		 * search list
		 * The val parameters represent search also need to send data.
		 */
		searchSel:function(val){		
			this.sendData=function(num,pageSelNum,orderField,orderDirection){
			if(!orderField && !orderDirection){
				return "pageNum="+num+"&numPerPage="+pageSelNum+"&"+val;
			}else{
				return "pageNum="+num+"&numPerPage="+pageSelNum+"&orderField="+orderField+"&orderDirection="+orderDirection+"&"+val;
			}

			
		}

			window.location.hash="#"+1;
			this.sel(1);
			/*
				When search frist table column sort icon show
			*/
			$('.sort .fa').addClass('hidden');
			$('.sort .fa').eq(0).removeClass('hidden');
			$('.sort .fa').eq(0).attr('data-direction','desc');
			$('.sort .fa').eq(0).removeClass('fa-caret-down').addClass('fa-caret-up');
		},
		/*
			sort
		*/

		rowTrBool:false,//If you want to form the first column of each row values are equal and adjacent to merge,you must set 'true',or else you must set 'false'.
		rowTrFun:function(){//Merge rows
			if(this.rowTrBool)
			{
				var arrTr=[];//Create an array.
				//In each of the first column is stored in the arrTr.
				$(this.tableNode).find('tbody tr').each(function(){
					arrTr.push($(this).find('td:first').html());
				});
				
				//Under the normal state how many columns per rows.
				var normalTdNum=$(this.tableNode).find('thead tr:first').find("th").length;
				for(var i=0;i<arrTr.length;i++)
				{
					for(var j=i+1;j<arrTr.length;j++)
					{
						//Record the current rows how many columns.
						var curTdNum=$(this.tableNode).find('tbody tr').eq(j).find("td").length;
						if(arrTr[i]===arrTr[j] && curTdNum===normalTdNum)
						{
							//Delete rows to be combined。
							$(this.tableNode).find('tbody tr').eq(j).find('td:first').remove();
							
							if(j===arrTr.length-1)
								$(this.tableNode).find('tbody tr').eq(i).find('td:first').attr({'rowspan':(j-i+1),valign:"middle"}).css('vertical-align','middle');
						}
						else
						{
							if(j-i>1)
								$(this.tableNode).find('tbody tr').eq(i).find('td:first').attr({'rowspan':(j-i),valign:"middle"}).css('vertical-align','middle');
							break;
						}
					}
				}
			}
		},
		//Create a page
		createPage:function(obj,selNum){
			obj=obj?obj:{};
			for (var property in this) {
				if(property in obj)
					continue;
				obj[property] = this[property];
			}
			obj.sel(selNum);
			pageFun.defaultPage=obj;
		}
}

$(function(){
	//get now sort direction and id
	function getDirId(){
		var node = $(".sort.active").find('.glyphicon');
		var	a = [];
		if(node.attr('data-direction') == 'desc'){
			a[0] = 'asc';
		}else if(node.attr('data-direction') == 'asc'){
			a[0] = 'desc';
		}
		
		a[1] = node.attr('data-orderField');
		return a;
	}
	//sort
	$(document).on('click','.sort',function(){
	var num = $("pageNum active").text(),
		node = $(this).find('.glyphicon'),
		orderDirection = node.attr('data-direction'),
		orderField = node.attr('data-orderField');
	$('.sort').removeClass('active');
	$(this).addClass('active');
	if(orderDirection == 'desc'){
		node.attr('data-direction','asc');
		node.removeClass('glyphicon-arrow-up').addClass('glyphicon-arrow-down');
	}else{
		node.attr('data-direction','desc');
		node.removeClass('glyphicon-arrow-down').addClass('glyphicon-arrow-up');
	}
	$('.sort .glyphicon').addClass('hidden');
	node.removeClass('hidden');
	 pageFun.defaultPage.sel(num,orderField,orderDirection);
	});
	//The drop-down menu
	$('html').on('change',pageFun.selNode,function(){
	 	pageFun.defaultPage.sel(1,getDirId()[1],getDirId()[0]);
		window.location.hash="#"+1;
	});
	
	//Page number effect
	$('html').on('click','.pageNum',function(){
		var num=parseInt($(this).text());
		//The current page style
		if($(this).hasClass('active'))
			return;
		
		pageFun.defaultPage.sel(num,getDirId()[1],getDirId()[0]);
		window.location.hash="#"+num;
	});
	
	//Effect of the previous page
	 $('html').on('click','.previous',function(){
		 var num=parseInt($('.boot-page .active:first').text());

		 if(num==1 || pageFun.defaultPage.pageTotal==0)
			 return;
		 else{
			 num=num-1;
		
		
			pageFun.defaultPage.sel(num,getDirId()[1],getDirId()[0]);
		 }
		 window.location.hash="#"+num;
	 });
	
	 //Effect of the next page
	 $('html').on('click','.next',function(){
		 var num=parseInt($('.boot-page .active:first').text());
		 var lastNum=pageFun.defaultPage.pageTotal;
		 //console.log(num);
		 if(num==lastNum || pageFun.defaultPage.pageTotal==0)
			 return;
		 else{
			 num=num+1;
	
			 pageFun.defaultPage.sel(num,getDirId()[1],getDirId()[0]);
		 }
		 window.location.hash="#"+num;
	 });
	 
	 //The first page and last page
	 $('html').on('click','.first',function(){
		 var num=parseInt($('.boot-page .active:first').text());
		 if(num==1 || pageFun.defaultPage.pageTotal==0)
			 return;
		 else
		 	pageFun.defaultPage.sel(1,getDirId()[1],getDirId()[0]); 
		window.location.hash="#"+1;
	 });
	 $('html').on('click','.last',function(){
		var num=parseInt($('.boot-page .active:first').text());
		var lastNum=pageFun.defaultPage.pageTotal;
		if(num==lastNum || pageFun.defaultPage.pageTotal===0)
			 return;
		else
			pageFun.defaultPage.sel(lastNum,getDirId()[1],getDirId()[0]);
		window.location.hash="#"+lastNum;
	 });
	 
	 //To determine whether a table needs to have a horizontal scroll bar
	 pageFun.thLength=$(pageFun.tableNode).find('thead th').length;
	 if(pageFun.thLength<=2){
		 $(pageFun.tableNode).css('min-width','100%');
	}
	 
	 //Optimize the button position
	$('#table_id,#nodeTable').on('click','button[data-toggle="dropdown"]',function(){
		var sreenWidth=$(window).width();
		var sreenHeight=$(window).height();
		var btnHeight=parseInt($(this).offset().top);
		var oUlHeight=$(this).siblings('ul').height()+$(this).outerHeight(true);
		
		if((sreenHeight-btnHeight)<oUlHeight)
		{
			$(this).parent('div.btn-group').addClass('dropup');
		}
	});
});