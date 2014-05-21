
$(document).ready(function () {
	var $Kint; //Keditor插件
	var $keEditor;
	var $keContainer;
	
	//当前文章的操作，POST/GET/PUT/DELETE其中一种
	var articleOperation = "POST";
	
	//计算inner的高度
	confSize();
	$(window).resize(function () {
		confSize();
	});
	//初始化Editor相关
	KindEditor.ready(function (K) {
			$Kint = K;
		});
	$(".keditor").hide();
	//默认只展示第一篇文章
	var currentArticleIndex =0;
	
	//当前选中的artilce元素
	showCurrentArticle();
	var $currentArticle;
	function confSize() {
		var h = $('.article').height() - $(".article h1").height() - 80;
		$(".article .inner").css("height", h);
	}
	
	function createEditor() {
		//设置KindEditor参数并初始化
		var options = {
			width : '38%',
			height: '700px'
		};
		$(".ke-container").hide();
		$keEditor = $Kint.create('textarea[name="content"]', options);
		$keContainer = $(".ke-container");
	}
	
	//显示索引所在的文章
	function showCurrentArticle(){
		
		$("ul.article_container > li").each(function(index, element) {
            if(index==currentArticleIndex){
				console.log("show article "+ currentArticleIndex);
				$(element).show(function(){
					$currentArticle = $(".article").eq(currentArticleIndex);
					$shownTitle = $currentArticle.find(".shown_title");
				});
			
			}else{
				$(element).stop(true,true).hide();
			}
        });
	}

	//Slider效果
	$(document).keydown(function (event) {
		
		if (event.keyCode == 37) { //判断当event.keyCode 为37时（即左方面键），执行函数to_left();
			currentArticleIndex--;
			if(currentArticleIndex >= 0){
					showCurrentArticle();
			}else{
				currentArticleIndex=0; //重置当前下标
				alert("No articles any more.");
				console.log("No articles any more.");	
			}
		
		} else if (event.keyCode == 39) { //判断当event.keyCode 为39时（即右方面键），执行函数to_right();
			currentArticleIndex++;
			if(currentArticleIndex > $(".article").size()-1){
			  //ajax加载新的文章
			  console.log("currentIndex: "+currentArticleIndex+" articles_count:"+$(".article").size());
			  alert("ajax loading...");	
			}else{
				showCurrentArticle();
			}
			
		}
	});

	//切换显示下拉
	$(".classify").toggle(function () {
		$('.selectbox').css('display', 'block');
		$(this).find('i').css({
			display : ''
		})
		$(this).find('em').css({
			display : 'none'
		})
		//替换下拉选中后的字体
		$('.selectbox .item a').click(function (event) {
			/* Act on the event */
			var Text = $(this).text();
			$('.classify strong').text(Text);
			$('.selectbox').css({
				display : 'none'
			});
			$('.classify').find('em').css({
				display : ''
			})
			$('.classify').find('i').css({
				display : 'none'
			})
		});
		return false;
	}, function () {
		$('.selectbox').css('display', 'none');
		$(this).find('em').css({
			display : ''
		})
		$(this).find('i').css({
			display : 'none'
		});
		return false;
	});
	//显示文章标题
	var $shownTitle;
	//可编辑文章标题
	var $editTitle = $(".edit_title");
	
	
	 //选中图片的预览功能	 
	var uploadImgArr = [];
	//此次操作需要删除的之前已经存在的图片
	var deleteImgIds = [];  
	 //选中的音乐	 
	var uploadMusicArr = [];
	//此次操作需要删除的之前已经存在的图片
	var deleteMusicIds = []; 
	//编辑文章
	$('.edit').click(function () {
		articleOperation = "PUT";
		uploadImgArr.splice(0,uploadImgArr.length);
		deleteImgIds.splice(0,deleteImgIds.length); 
		$(".setting,.cpbottom").show();
		$(".bar,.bottombar").hide();
		//hywang 需要将当前文章的音乐和图片分别读到.bg和.music里
		$.each( $(".bg .cl li"), function(i, li){
			 $(li).show();
		 });
		$shownTitle.hide();
		
		var contains_edit_title = $currentArticle.has(".edit_title").length;
		var contains_keditor= $currentArticle.has(".keditor").length;
		var contains_kecontainer=$currentArticle.has(".ke-container").length;
		if( !contains_edit_title && !contains_keditor && !contains_kecontainer){
			//$currentArticle.append($editTitle).append($(".keditor"));
			$currentArticle.append($editTitle);
			//$currentArticle.append($editTitle).append($keEditor);
		}
		if(!$keEditor){
			 createEditor();
		}
		$editTitle.val($shownTitle.text()).show();
		
		
		$currentArticle.find(".inner").fadeOut(500, function () {
			$keEditor.html($currentArticle.find(".inner").html());
			$keContainer.fadeIn(1500);
		});

		return false;
	})
	
	//新建文章
	$(".addArticle").click(function () {
		articleOperation = "POST";
		uploadImgArr.splice(0,uploadImgArr.length);
		deleteImgIds.splice(0,deleteImgIds.length); 
		$(".setting,.cpbottom").show();
		$(".bar,.bottombar").hide();
		$shownTitle.hide();
		$(".bg .cl").empty();
		$(".music .cl").empty();
		
		var contains_edit_title = $currentArticle.has(".edit_title").length;
		var contains_keditor= $currentArticle.has(".keditor").length;
		var contains_kecontainer=$currentArticle.has(".ke-container").length;
		if( !contains_edit_title && !contains_keditor && !contains_kecontainer){
			//$currentArticle.append($editTitle).append($(".keditor"));
			$currentArticle.append($editTitle);
			//$currentArticle.append($editTitle).append($keEditor);
		}
		if(!$keEditor){
			 createEditor();
		}
		$editTitle.val("").show().select();
		
		
		$currentArticle.find(".inner").fadeOut(500, function () {
			$keEditor.html("");
			$keContainer.fadeIn(1500);
		});

		return false;
	})

	$('.cpbottom .cancel').click(hideElements);
	
	function hideElements() {
		$(".cpbottom,.setting,.cp").hide();
		$editTitle.hide();
		$shownTitle.show();
		$(".ke-container").hide(function () {
			$currentArticle.find(".inner").fadeIn(1500);
			$(".bar,.bottombar").show();
		});
		return false;
	}

	$('.del').click(function () {
		$(".confirm").show();
		return false;
	})

	$('.confirm button').eq(1).click(function () {
		$(".confirm").hide();
	})
	$(".setting").toggle(function () {
		$(".cp").show();
		$(this).find('em').css({
			display : ''
		})
		$(this).find('i').css({
			display : 'none'
		})
	}, function () {
		$(".cp").hide();
		$(this).find('em').css({
			display : ''
		})
		$(this).find('i').css({
			display : 'none'
		})
	})

	
	
	 $(".addImage").change(function(e){
	  	 var files = e.target.files;
	  	 for (var i = 0,f; f = files[i]; i++) {
	  	  	 uploadImgArr.push(f);
	  	  	 var reader = new FileReader();
	  	  	 reader.onload = (function(file){
	  	  		 return function(e) {
	  	  		 var $image = $("<img/>").attr("src",this.result);
	  	  	 		var $delLink = $("<a/>").attr("href","#").text("删除").addClass("delImg").on("click",onDelImg);
	  	  	 		$("<li/>").append($image).append($delLink).appendTo($(".bg .cl"));
	  	  	 
  			};
	  	  	 		
	  	  	})(f);
	  	    reader.readAsDataURL(f);
	  	 }
	  });  
	
	 $(".addMusic").change(function(e){
	  	 var files = e.target.files;
	  	 for (var i = 0,f; f = files[i]; i++) {
	  		uploadMusicArr.push(f);
	  	  	 var reader = new FileReader();
	  	  	 reader.onload = (function(file){
	  	  		 return function(e) {
	  	  	 		var $delLink = $("<a/>").attr("href","#").text("删除").addClass("delMsc").on("click",onDelMusic);
	  	  	 		var size= new Number(file.size/1000000);
	  	  	 		$("<li/>").text(file.name+"("+size.toPrecision(3)+"MB)").append($delLink).appendTo($(".music .cl"));
	  	  	 
  			};
	  	  	 		
	  	  	})(f);
	  	    reader.readAsDataURL(f);
	  	 }
	  }); 
	 
	 function onDelMusic(){
		 var $parent = $(this).parent();
		 var currentIndex = $(".music .cl li").index($parent);
		 $.each( $(".music .cl li"), function(i, li){
			 if(currentIndex == i){
				 if(li.descriptorId){ //已经存在的音乐
					 deleteMusicIds.push(li.descriptorId); 
				 }
				 uploadMusicArr= uploadMusicArr.splice(i, 1);//从选中图片中移除
				 $(li).hide(); //隐藏当前li元素
			 }
			});
	 }
	
	 function onDelImg(){
		 var $parent = $(this).parent();
		 var currentIndex = $(".bg .cl li").index($parent);
		 $.each( $(".bg .cl li"), function(i, li){
			 if(currentIndex == i){
				 if(li.descriptorId){ //已经存在的图片
					 deleteImgIds.push(li.descriptorId); 
				 }
				 uploadImgArr= uploadImgArr.splice(i, 1);//从选中图片中移除
				 $(li).hide(); //隐藏当前li元素
			 }
			});
	 }
	 
	
	 //用来请求授权的对象
	 function AuthorizationHelper(bucketType,url){
		 this.bucketType = bucketType;
		 this.url = url;
		 this.askAuthorization = function(successCallback,errorCallback){
				$.ajax({
					 type: "POST",
					 url: this.url,
					 headers:{
						"bucket_type": this.bucketType
						},		
				 success: successCallback,
				 error: errorCallback
			});
		};
	 }
	 
	 var submittedImages = [];
	 var submittedMusics = [];
	//上传文件
	 function uploadFile(requestOptions,fileArray,htmlForm,uploadedRecords,successCallback) {
		var j = 0;
		isUpload = false;
	 	var url = requestOptions.requestURL;
		var signature = requestOptions.signature;
		var policy= requestOptions.policy;
		function upload() {
	         if (fileArray.length > 0 && !isUpload) {
	             var file = fileArray[j];
	             var xhr = new XMLHttpRequest();
	             if (xhr.upload) {
	                 // 文件上传成功或是失败
	                 xhr.onreadystatechange = function(e) {
	                     if (xhr.readyState == 4) {
	                         if (xhr.readyState==4 && xhr.status==200) {
	                             isUpload = true;
	                             uploadedRecords.push(xhr.responseText); //记录已经上传成功的文件信息
	                         } else {
	                             alert( file.name + "上传失败");
	                         }
	                         //上传成功（或者失败）后，如果还有文件没传，则再次调用upload函数
	                         if (j < fileArray.length - 1) {
	                             j++;
	                             isUpload = false;
	                             upload();
	                             
	                          
	                         }else{ //如果传完文件，执行上传成功后的函数
	                        	successCallback();
	                         }
	                     }
	                 };
	                 var formdata = new FormData(htmlForm);
	                 formdata.append("signature",signature);
	                 formdata.append("policy",policy);
	                 formdata.append("file",file);
	                 // 开始上传
	                 xhr.open("POST", url, true);
	                 xhr.send(formdata);
	             }
	         }
	     }
	     	upload();
	 }
	 
	
	 $(".delImg").click(onDelImg);
	 
	 $(".delMsc").click(onDelMusic);
	 
	 $(".uploadImgBtn").click(function(){
		 $(".addImage").click();
	 });
		
	 $(".uploadMusicBtn").click(function(){
		 $(".addMusic").click();
	  });
	 
		$('.cpbottom .submit').click(function () {
			var imagesFormData,musicsFormData;
			var $imgForm = $("#dataForm").clone();
			var $musicForm = $imgForm.clone();
			var imgformAuthorizationHelper = new AuthorizationHelper("image","authorization/form");
			if(uploadImgArr.length>0){ //如果有图片需要上传
				imgformAuthorizationHelper.askAuthorization(function(requestOptions, textStatus, jqXHR){ //申请图片空间授权
					uploadFile(requestOptions,uploadImgArr,$imgForm[0],submittedImages, //上传图片
							function(){
						if(uploadMusicArr.length>0){ //如果有音乐需要上传
							imgformAuthorizationHelper.bucketType = "file";
							imgformAuthorizationHelper.askAuthorization(function(requestOptions, textStatus, jqXHR){ //2.1申请文件空间授权
								uploadFile(requestOptions,uploadMusicArr,$imgForm[0],submittedMusics, //上传音乐
										submitToPandora);
							});
						}else { //如果没有音乐要上传
							submitToPandora();
						}
					});
				},
				function(jqXHR, textStatus, errorThrown){
					var exceptionInfo = JSON.parse(jqXHR.responseText);
					alert("code: "+exceptionInfo.code+"message: "+ exceptionInfo.message);
				});
			}else if(uploadMusicArr.length>0){ //如果只有音乐需要上传
				imgformAuthorizationHelper.bucketType = "file";
				imgformAuthorizationHelper.askAuthorization(function(requestOptions, textStatus, jqXHR){ //2.1申请文件空间授权
					uploadFile(requestOptions,uploadMusicArr,$imgForm[0],submittedMusics, //上传音乐
							submitToPandora);
				});
			}else{ //
				submitToPandora();
			}
				
			
		});
		
		//如果所有文件传完，则向潘多拉服务器提交文件更新信息   
		function submitToPandora(){
			url = "article";
			var result="修改文章";
			if(articleOperation=="PUT"){
				var aid=$currentArticle.attr("aid"); //当前文章的ID
				url = url+"/"+aid;
			}else if(articleOperation=="POST"){
				result="创建文章";
			}
			var currentLayout = $("input[name='layout']").val();
			var tags = "文艺，历史";
			$.ajax({
				type: articleOperation,
				url: url,
				data: {
					"addedImgs":JSON.stringify(submittedImages),
					"delImgs":JSON.stringify(deleteImgIds),
					"title":$editTitle.val(),
					"content":$keEditor.html(),
					"addedMscs": JSON.stringify(submittedMusics),
					"musicSelected": 1, //hywang
					"delMscs":JSON.stringify(deleteMusicIds),
					"layout":currentLayout,
					"tags":tags
				},
//			 traditional:true,
				// contentType: "application/json",
				success: function(responseText, textStatus, jqXHR){
					hideElements();
					alert(result+responseText.URL+": "+responseText.status);
				},
				error: function(jqXHR, responseText, errorThrown){
					alert(textStatus);
				}
			});
		}
		
})
