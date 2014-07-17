	var $Kint; //Keditor插件
	var $keEditor;
	var $keContainer;
	var musicPicked = 0; //当前文章播放的音乐 hywang
	var pandoraSubmitter = new pandoraSubmitter();
	var isCreate = false; //是否是新建文章
	var $fakeArticle; //如果没有文章，会生成一个仿造文章
	//创建文章的layout
	var currentLayout;
	var aid; //当前文章的ID
	//默认展示第一篇文章
	var currentArticleIndex =0;
	//当前文章
	var $currentArticle;
	//音乐播放器
	var musicPlayer = new Audio();
	//是否播放音乐,默认为true
	var play = true;
	musicPlayer.loop = "loop";
	
	var applicationParams = {
			applicationContext:"",
			basePath:""
	};
	
	//动态加载文章
	function ajaxLoad(next){
		var loadType ="previous";
		if(next){
			loadType = "next";
		}
		$.when($.ajax({
			 url: applicationParams.basePath+"article/dyload/"+loadType,
			 dataType:"json",
			 headers:{
				"startId": aid
				}
		})).then(
				//success
				function(responseText, textStatus, jqXHR){
					 if(responseText.status=="OK"){
						 var articles = responseText.data;
						 var noMore = false;
						 //没有新文章
						 if(articles.length==0){
							
							 alert("没有文章了哦 ，写一篇吧！");
							 noMore = true;
							 //如果之前就没有文章，也没新加载出文章hywang,创建一篇空文章
							 if(!hasArticle()){
								var fakeData = {"title":"","text":""};
								createArticle(fakeData,true);
								alert("No records at all!");
							 }
							//加载出新文章
						 }else{ 
							 //如果刚加载文章，索引不用+1
							 var isInitialLoad = false;
							 if(!hasArticle()){
								 isInitialLoad = true;
							 }
							 for ( var i = 0; i < articles.length; i++) {
								 var articleData = articles[i];
								 //创建文章
								 createArticle(articleData,false,!next);
								 //当前选中的artilce元素
							 }
						 }
						 if(next && !isInitialLoad && !noMore){ //如果加载下一篇，成功后索引+1，如果加载前一篇，则无论成功与否均不需要改变索引
							 currentArticleIndex++;
						 }
						 showCurrentArticle();
					 }else{
						 alert(textStratus);
					 }
				 },
				//fail
				 function(jqXHR, textStatus, errorThrown){
					var exceptionInfo = JSON.parse(jqXHR.responseText);
				alert("code: "+exceptionInfo.code+"message: "+ exceptionInfo.message);
			 });
	}
	
	function hasArticle(){
		return $("ul.article_container > li").length>0;
	}
	
	//创建文章节点
	function createArticle(articleData,isFake,reverse){
		var $articleContainer = $("ul.article_container");
		var $articleLi = $("<li/>");
		var articleId = articleData.articleId;
		//main
		var $articleMain = isFake?$("<div/>").addClass("article"):$("<div/>").addClass("article").attr("aid",articleId);
		var $displayImgContainer = $("<div/>");
		var $hiddenImgContainer = $("<div/>").css("display","none").addClass("ownedImgs");
		var $musicContainer = $("<div/>").css("display","none").addClass("ownedMusics");
		
		if(!isFake){
			
			//处理图片
			for ( var i = 0; i < articleData.images.length; i++) {
				var img = articleData.images[i];
				var ic;
				if(i==0){
					var $img = $("<img/>").attr("src",img.url).attr("imageId",img.imageId);
					ic = $displayImgContainer.addClass("wrapbg").append($img);
				}
				var $image = $("<img/>").attr("src",img.snapshotUrl);
				var $delLink = $("<a/>").attr("href","#").text("删除").addClass("delImg").on("click",onDelImg);
				$hiddenImgContainer.append($("<li/>").attr("descriptorid",img.imageId).attr("url",img.url).append($image).append($delLink));
			}
			
			//处理音乐
			for ( var i = 0; i < articleData.files.length; i++) {
				var music = articleData.files[i];
				var $delLink = $("<a/>").attr("href","#").text("删除").addClass("delMsc").on("click",onDelMusic);
				var $musicLi = $("<li/>").attr("descriptorid",music.fileId).attr("url",music.url).text(music.name+"("+3.23+"MB)").append($delLink);
				
				if(i==articleData.pickedMusicIndex){
					$("<cite/>").append($("<img/>").attr("src",applicationParams.basePath+"images/play16.ico")).addClass("pickedMusic").appendTo($musicLi);
				}
				$musicContainer.append($musicLi);
			}
		}
		
		//处理标题
		var articleTitle = articleData.title;
		$articleMain.append($("<h1/>").addClass("shown_title").text(articleTitle));
		//处理正文
		$articleMain.append($("<div/>").addClass("inner").html(articleData.text));
		
		if(!isFake){
			$articleMain.append($displayImgContainer);
		}
		$articleMain.append($hiddenImgContainer);
		
		$articleMain.append($musicContainer);
		
		if(ic){
			$articleMain.prepend(ic);
		}
		$articleLi.append($articleMain);
		if(isFake){ //如果当前创建的是FAKE
			
			$fakeArticle = $articleLi;
			$articleContainer.append($fakeArticle);
		
		}else if($fakeArticle){ //如果当前创建的不是FAKE，但是有FAKE存在，置空FAKE并且替换DOM元素
			
			$fakeArticle.replaceWith($articleLi);
			$fakeArticle = null;
		
		}else{
			if(reverse){
				$articleContainer.prepend($articleLi);
			}else{
				$articleContainer.append($articleLi);
			}
			
		}
		
	}
	
	//初始化Editor相关
	KindEditor.ready(function (K) {
			$Kint = K;
		});
	
	function confSize() {
		var h = $('.article').height() - $(".article h1").height() - 80;
		$(".article .inner").css("height", h);
	}
	
	function createEditor() {
		//设置KindEditor参数并初始化
		var editorWidth;
		var editorHeight;
		
		if($currentArticle){
			editorWidth = $currentArticle.width()+"px";
			editorHeight = $currentArticle.height()-$(".cpbottom cl").height()+"px";
		}
		var options = {
			minWidth: "100px",
			width : editorWidth,
			height: editorHeight,
			shadowMode: false,
			items: ["fontname","fontsize","|","forecolor","hilitecolor","bold","italic","underline","removeformat","|","justifyleft","justifycenter","justifyright",
        "justifyfull","insertorderedlist","insertunorderedlist","|","emoticons","link","|","fullscreen"]
		};
		
		$(".ke-container").hide();
		$keEditor = $Kint.create('textarea[name="content"]', options);
		$keContainer = $(".ke-container");
	}
	
	//显示索引所在的文章
	function showCurrentArticle(){
		
		$("ul.article_container > li").each(function(index, element) {
            if(index == currentArticleIndex){
				console.log("show article "+ currentArticleIndex);
				$currentArticle = $(".article").eq(currentArticleIndex);
				aid = $currentArticle.attr("aid");
				$(element).show(function(){
					 //设置当前文章需要播放的音乐的URL
					var $pickedMusicCite=$currentArticle.find(".ownedMusics .pickedMusic");
					if($pickedMusicCite){
						var $pickedMusicLi = $pickedMusicCite.parent();
						if($pickedMusicLi){
							var url=$pickedMusicLi.attr("url");
							if(url){
								musicPlayer.src = url;
							}else{
								musicPlayer.src=  "";
							}
						}
					}
					
					//设置音乐播放器的音轨，根据播放状态播放
					 if(play){
						 musicPlayer.play();
					 }
					$shownTitle = $currentArticle.find(".shown_title");
					
					//当前文章的背景图进行轮播效果
					playImages(3000,5000,1);
				});
			
			}else{
				$(element).stop(true,true).hide();
			}
        });
		
		replaceUrlState();
		
		//计算当前页面的URL并更新浏览器URL
		function replaceUrlState(){
			var previousUrl = location.href;
			
			
			var urlSegments=previousUrl.split("/");
			var idSuffix = urlSegments[urlSegments-1];
			var reunionUrl;
			var filled = false; //是否已经填充了/article
			var urlLength = urlSegments.length;
			if(previousUrl.indexOf("/article")>=0){
				filled = true;
			}
			for ( var i = 0; i < urlLength; i++) {
				var segment = urlSegments[i];
				if(i!=0  && i!= urlLength-1){
					reunionUrl = reunionUrl+"/"+segment;
				}else if(i==0){
					reunionUrl = segment;
				}else if(i==urlLength-1 && !filled){
					reunionUrl = reunionUrl+"/article/";
				}else if(i==urlLength-1 && filled){
					reunionUrl = reunionUrl+"/";
				}
				
				
			}
			
			if(window.history.replaceState && aid && aid>=0){
				var currentUrl =  reunionUrl+aid;
				history.replaceState(null, null, currentUrl);
			}
		};
	}
	
	//播放图片的函数
	function playImages(timeout,speed,index){
		
		var $wrapbg=$currentArticle.find(".wrapbg");
		var $bgImg = $wrapbg.find("img");
		var $containedImgs = $currentArticle.find(".ownedImgs > li");
		var imgNum = $containedImgs.length;
		
		if(imgNum>1){
			
			$wrapbg.fadeOut(speed,function(){
				
				setTimeout(function(){
					//找到下一个图片
					if($bgImg){
						var prevImgId = $bgImg.attr("imageId");
						$.each($containedImgs, function(i, li){
							var	nextIndex = (imgNum-1)==index?0:index+1;
							var imgId = $(li).attr("descriptorId");
							var imgUrl = $(li).attr("url");
							if(i==index){
								if(imgId!=prevImgId){
									$bgImg.attr("imageId",imgId);
									$bgImg.attr("src",imgUrl);
									$wrapbg.fadeIn(speed,function(){
										playImages(timeout,speed,nextIndex);
									});
								}
							}else{
								return true;
							}
						});
						
					}
				}, timeout);
			});
		}
	}; 

	//Slider效果
	$(document).keydown(function (event) {
		
		if (event.keyCode == 37) { //判断当event.keyCode 为37时（即左键）
			
			triggerNext();
		
		} else if (event.keyCode == 39) { //判断当event.keyCode 为39时（即右键）
			
			triggerNext(true);
		}
	});
	
	function triggerNext(isNext){
		if(isNext){
			var articleSize=$(".article").size();
			if(currentArticleIndex == articleSize-1){
				  console.log("currentIndex: "+currentArticleIndex+" articles_count:"+articleSize);
				  ajaxLoad(true);
//				  alert("ajax loading...");	
				}else{
					currentArticleIndex++;
					showCurrentArticle();
				}
		}else{
			if(currentArticleIndex == 0){
				ajaxLoad(false);
//				alert("ajax loading...");	
			}else if(currentArticleIndex > 0){
				currentArticleIndex--;
				showCurrentArticle();
			}
		}
		
	}
	

	//显示文章标题
	var $shownTitle;
	//可编辑文章标题
	var $editTitle;
	 //选中图片的预览功能	 
	var uploadImgArr = [];
	//此次操作需要删除的之前已经存在的图片
	var deleteImgIds = [];  
	 //选中的音乐	 
	var uploadMusicArr = [];
	//此次操作需要删除的之前已经存在的图片
	var deleteMusicIds = []; 
	var submittedImages = [];
	var submittedMusics = [];
	
	//微博登陆
	function WB2_Login(){
		WB2.login(function(){
			/***授权成功后回调***/
			getWbUserData(function(o){
				/***o是/users/show.json接口返回的json对象***/
				alert(o.screen_name);
				console.log(o);
//				self.location="http://9iu.org/qqlogin?qqsid="+o.screen_name;
			});
		});
	}

	function getWbUserData(callback) {
	    WB2.anyWhere(function (W) {
	        /***获取授权用户id***/
	        W.parseCMD("/account/get_uid.json", function (sResult, bStatus) {
	            if (!!bStatus) {
			/**请求uid成功后调用以获取用户数据**/
	                getData(W, sResult);
	            }else{
				/*** 这里只是简单处理出错***/
					alert("授权失败或错误");
				}
	        }, {}, {
	            method: 'GET'
	        });
	    });
		/***请求用户数据，并执行回调***/
	    function getData(W, User) {
	        W.parseCMD("/users/show.json", function (sResult, bStatus) {
	            if (!!bStatus && !!callback) {
	                callback.call(this,sResult);
	            }
	        },
	        {
	            'uid': User.uid
	        },
	        {
	            method: 'GET'
	        });
	    }
	};

	
	function hideElements() {
		$(".cpbottom,.setting,.cp").hide();
		$editTitle.hide();
		var $editImgs=$(".bg .cl").find("li");
		var $editMusics = $(".music .cl").find("li");
		$(".ke-container").fadeOut(function () {
			$currentArticle.find(".inner").fadeIn(1500);
			$shownTitle.fadeIn(1500);
			$(".bar,.bottombar").show();
		});
		return false;
	}
	
	//Restore musics and images to edit.
	function restoreEditItems(){
		 $currentArticle.find(".ownedImgs").append($(".bg .cl").find("li")).parent()
		 .find(".ownedMusics").append($(".music .cl").find("li"));
	}


	function clearSubmitDataCache(){
		uploadImgArr.splice(0,uploadImgArr.length);
		deleteImgIds.splice(0,deleteImgIds.length); 
		uploadMusicArr.splice(0,uploadMusicArr.length);
		deleteMusicIds.splice(0,deleteMusicIds.length);
		submittedImages.splice(0,submittedImages.length);
		submittedMusics.splice(0,submittedMusics.length);
	}
	
	 function onDelMusic(){
		 var $parent = $(this).parent();
		 var currentIndex = $(".music .cl li").index($parent);
		 $.each( $(".music .cl li"), function(i, li){
			 if(currentIndex == i){
				 var $li = $(li);
				 if($li.attr("descriptorId")){ //已经存在的音乐
					 deleteMusicIds.push($li.attr("descriptorId")); 
				 }
				 uploadMusicArr= uploadMusicArr.splice(i, 1);//从选中图片中移除
				 $(li).hide(); //隐藏当前li元素
			 }
			});
		 return false;
	 }
	
	 function onDelImg(){
		 var $parent = $(this).parent();
		 var currentIndex = $(".bg .cl li").index($parent);
		 $.each( $(".bg .cl li"), function(i, li){
			 if(currentIndex == i){
				 var $li = $(li);
				 if($li.attr("descriptorId")){ //已经存在的图片
					 deleteImgIds.push($li.attr("descriptorId")); 
				 }
				 uploadImgArr= uploadImgArr.splice(i, 1);//从选中图片中移除
				 $(li).hide(); //隐藏当前li元素
			 }
			});
		 return false;
	 }
	 
	
	 //用来请求授权的对象
	 function AuthorizationHelper(bucketType,url){
		 this.bucketType = bucketType;
		 this.url = url;
		 this.askAuthorization = function(successCallback,errorCallback){
			 $.when($.ajax({
				 type: "POST",
				 url: this.url,
				 headers:{
					"bucket_type": this.bucketType
					}
		})).then(successCallback,errorCallback);
		};
	 }
	 
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
	                        	 if(successCallback){
	                        		 successCallback();
	                        	 }
	                        	 
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
	
		
		
		
		//如果所有文件传完，则向潘多拉服务器提交文件更新信息   
		function pandoraSubmitter(){
//			this.url = "article";
			this.tags = "文艺，历史"; //hywang
			this.postAdd = function(){
				$.when($.ajax({
					type: "POST",
					url: applicationParams.basePath+"article",
					dataType: "json",
					data: {
						"addedImgs":JSON.stringify(submittedImages),
						"delImgs":JSON.stringify(deleteImgIds),
						"title":$editTitle.val(),
						"content":$keEditor.html(),
						"addedMscs": JSON.stringify(submittedMusics),
						"musicSelected": musicPicked,
						"delMscs":JSON.stringify(deleteMusicIds),
						"layout":currentLayout,
						"tags":this.tags
					}
					
				})).then(
						function(responseText, textStatus, jqXHR){
							hideElements();
							createArticle(responseText["data"]);
							articleNumber = $("ul.article_container > li").length;
							currentArticleIndex = articleNumber-1;
							showCurrentArticle();
						},	
						 function(jqXHR, responseText, errorThrown){
							alert(errorThrown);
						}
				).always(function(){
					isCreate = false;
				});
			};
			
			this.postUpdate = function(){
				$.when($.ajax({
					type: "PUT",
					url: applicationParams.basePath+"article/"+aid,
					dataType: "json",
					data: {
						"addedImgs":JSON.stringify(submittedImages),
						"delImgs":JSON.stringify(deleteImgIds),
						"title":$editTitle.val(),
						"content":$keEditor.html(),
						"addedMscs": JSON.stringify(submittedMusics),
						"musicSelected": musicPicked,
						"delMscs":JSON.stringify(deleteMusicIds),
						"layout":currentLayout,
						"tags":this.tags
					},
				
				})).then(
						//success
						function postSubmit(responseText, textStatus, jqXHR){
							//在客户端同步更新后的数据
							$currentArticle.find(".inner").html($keEditor.html());
							$shownTitle.text($editTitle.val());
							//清空缓存
							var $imgContainer = $currentArticle.find(".ownedImgs");
							$imgContainer.empty();
							var $mscContainer = $currentArticle.find(".ownedMusics");
							$mscContainer.empty();
							//处理图片
							var updatedImgs = responseText["imgs"];
							if(updatedImgs){
								var $wrapbg=$currentArticle.find(".wrapbg");
								for ( var i = 0; i < updatedImgs.length; i++) {
									var img = updatedImgs[i];
									if(i==0){
										var $img = $("<img/>").attr("src",img.url).attr("imageId",img.imageId);
										var $bgImg=$wrapbg.find("img");
										//如果已经有占位背景图，删除该图
										if($bgImg.length>0){
											$bgImg.remove();
										}
										//更新操作之后，保证有背景图
										if($wrapbg.length==0){
											$wrapbg = $("<div/>").addClass("wrapbg");
											$currentArticle.prepend($wrapbg);
										}
											$wrapbg.append($img);
									}
									var $image = $("<img/>").attr("src",img.snapshotUrl);
									var $delLink = $("<a/>").attr("href","#").text("删除").addClass("delImg").on("click",onDelImg);
									$imgContainer.append($("<li/>").attr("descriptorid",img.imageId).attr("url",img.url).append($image).append($delLink));
								}
							}
							//处理音乐
							var updateMscs = responseText["mscs"];
							if(updateMscs){
								for ( var i = 0; i < updateMscs.length; i++) {
									var music = updateMscs[i];
									var $delLink = $("<a/>").attr("href","#").text("删除").addClass("delMsc").on("click",onDelMusic);
									var $musicLi = $("<li/>").attr("descriptorid",music.fileId).attr("url",music.url).text(music.name+"("+3.23+"MB)").append($delLink);
									if(music.selected){
										$("<cite/>").append($("<img/>").attr("src","images/play16.ico")).addClass("pickedMusic").appendTo($musicLi);
									}
									$mscContainer.append($musicLi);
								}
							}
							//刷新视图
							hideElements();
							showCurrentArticle();
						},
						//error
						function errorCallback(jqXHR, responseText, errorThrown){
							alert(errorThrown);
						}		
				);
			};
		
			this.postDelete = function(){
				$.when($.ajax({
					type: "DELETE",
					url: applicationParams.basePath+"article/"+aid,
				
				})).then(
						function postSubmit(responseText, textStatus, jqXHR){
							$(".confirm").hide(function(){
								$currentArticle.parent().remove();
								if(currentArticleIndex==0){
									currentArticleIndex = currentArticleIndex+1;
								}else{
									currentArticleIndex = currentArticleIndex-1;
								}
								showCurrentArticle();
							});
						},	
						
						function errorCallback(jqXHR, responseText, errorThrown){
							alert(errorThrown);
						}
				);
			};
		}



$(document).ready(function () {
	
	$editTitle = $(".edit_title");
	//隐藏editor对应的textarea
	$(".keditor").hide();
	//隐藏分享面板
	var $sharePanel=$(".jiathis_style_24x24");
	$sharePanel.hide();
	var $navigator=$("#navigator"); //hywang
	//计算inner的高度
	confSize();
	
	$(window).resize(function () {
		confSize();
	});
	
	//给button绑定图标变换和事件
	var $shareBtn=$(".share");
	$shareBtn.click(function(){
		return false;
	});
	var isSharePanelHoving=false;
	$sharePanel.mouseleave(function(){
		 $(this).hide();
		 isSharePanelHoving = false;
	}).mouseenter(function(){
		isSharePanelHoving = true;
	});
	$shareBtn.hover(
	function(){
		$(this).removeClass("share").addClass("share_in");
	    timer = setTimeout(function(){
	    	$sharePanel.show();
	    },700);
	},function(){
		$(this).removeClass("share_in").addClass("share");
		setTimeout(function(){
			if(!isSharePanelHoving){
				$sharePanel.hide();
			}
		},1100);
	    clearTimeout(timer);
	});
	
	var $musicBtn=$(".bottombar a.music");
	
	$musicBtn.click(function(){
		if(!play){
			musicPlayer.play();
			play = true;
		}else{
			musicPlayer.pause();
			play = false;
		}
		return false;
	});
	
	$musicBtn.hover(
	 function(){
		 $(this).removeClass("music").addClass("music_in");
	 },
	 function(){
		 $(this).removeClass("music_in").addClass("music");
	 }
	);
	
	var $commentBtn =$(".bottombar a.comment");
	$commentBtn.hover(
			function(){ 
				$(this).removeClass("comment").addClass("comment_in");
			},
			function(){
				 $(this).removeClass("comment_in").addClass("comment");
			});
	var $likeBtn = $(".bottombar a.like");
		$likeBtn.hover(
			function(){
				$(this).removeClass("like").addClass("like_in");
			},
			function(){
				$(this).removeClass("like_in").addClass("like");
			}
		);
		
	var $delBtn=$(".del");
		$delBtn.hover(
			function(){
				$(this).removeClass("del").addClass("del_in");	
			},
			function(){
				$(this).removeClass("del_in").addClass("del");
			}
		);
		$delBtn.click(function () {
			$(".confirm").show();
			return false;
		});
		
	var $editBtn=$(".edit");
		$editBtn.hover(
			function(){
				$(this).removeClass("edit").addClass("edit_in");	
			},
			function(){
				$(this).removeClass("edit_in").addClass("edit");
			}
		);
		
	var $previousBtn = $(".previous");
	$previousBtn.click(function(){
		triggerNext();
		return false;
	});
	
	var $nextBtn = $(".next");
	$nextBtn.click(function(){
		triggerNext(true);
		return false;
	});
	//新建文章
	$(".addArticle").click(function () {
		//如果登陆了，可以创建，否则转入登陆流程
		if(WB2.checkLogin()){
			
			isCreate = true;
			clearSubmitDataCache();
			$(".setting,.cpbottom").show();
			$(".bar,.bottombar").hide();
			
			$(".bg .cl").empty();
			$(".music .cl").empty();
			
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
			$editTitle.val("").show().select();
			
			
			$currentArticle.find(".inner").fadeOut(500, function () {
				$keEditor.html("");
				$keContainer.fadeIn(1500);
			});

		}else{
//			location.href = "user/wblogin";
//			WB2.login(function(o){
//				console.log(o);
//				});
			WB2_Login();
		}
		return false;
	});
	
	//编辑文章
	$editBtn.click(function () {
		clearSubmitDataCache();
		$(".setting,.cpbottom").show();
		$(".bar,.bottombar").hide();
		var $containedImgs = $currentArticle.find(".ownedImgs > li");
		var $containedMusics = $currentArticle.find(".ownedMusics > li");
		var $cpImgContainer = $(".bg .cl");
		var $cpMusicContainer = $(".music .cl");
		$cpImgContainer.empty().append($containedImgs);
		var $imgsToEdit=$(".bg .cl li");
		$cpMusicContainer.empty().append($containedMusics);
		$.each($imgsToEdit, function(i, li){
			 $(li).show();
		 });
		$shownTitle.hide();
		
		var contains_edit_title = $currentArticle.has(".edit_title").length;
		var contains_keditor= $currentArticle.has(".keditor").length;
		var contains_kecontainer=$currentArticle.has(".ke-container").length;
		if( !contains_edit_title && !contains_keditor && !contains_kecontainer){
			$currentArticle.append($editTitle);
		}
		if(!$keEditor){
			 createEditor();
		}
		
		$currentArticle.find(".inner").fadeOut(500, function () {
			$editTitle.val($shownTitle.text()).fadeIn(1500);
			$keEditor.html($currentArticle.find(".inner").html());
			$keContainer = $(".ke-container");
			$keContainer.fadeIn(1500);
		});

		return false;
	});
	
	$(".delImg").click(onDelImg);
	 
	 $(".delMsc").click(onDelMusic);
	 
	 $(".uploadImgBtn").click(function(){
		 $(".addImage").click();
	 });
		
	 $(".uploadMusicBtn").click(function(){
		 $(".addMusic").click();
	  });
	 
		$(".cpbottom .submit").click(function () {
			var imagesFormData,musicsFormData;
			currentLayout = $("input[name='layout']").val();
			var $imgForm = $("#dataForm").clone();
			var $musicForm = $imgForm.clone();
			var postExecution = isCreate? pandoraSubmitter.postAdd:pandoraSubmitter.postUpdate;
			var imgformAuthorizationHelper = new AuthorizationHelper("image",applicationParams.basePath+"authorization/form");
			if(uploadImgArr.length>0){ //如果有图片需要上传
				imgformAuthorizationHelper.askAuthorization(function(requestOptions, textStatus, jqXHR){ //申请图片空间授权
					uploadFile(requestOptions,uploadImgArr,$imgForm[0],submittedImages, //上传图片
							function(){
						if(uploadMusicArr.length>0){ //如果有音乐需要上传
							imgformAuthorizationHelper.bucketType = "file";
							imgformAuthorizationHelper.askAuthorization(function(requestOptions, textStatus, jqXHR){ //2.1申请文件空间授权
								uploadFile(requestOptions,uploadMusicArr,$imgForm[0],submittedMusics, //上传音乐
										postExecution);
							});
						}else { //如果没有音乐要上传
							postExecution();
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
							postExecution);
				});
			}else{ //
				postExecution();
			}
				
			return false;	
		});
		
		$(".cpbottom .cancel").click(function(){
			hideElements();
			restoreEditItems();
			return false;
		});
		
		//切换显示下拉
		$(".classify").toggle(function () {
			$('.selectbox').css('display', 'block');
			$(this).find('i').css({
				display : ''
			});
			$(this).find('em').css({
				display : 'none'
			});
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
				});
				$('.classify').find('i').css({
					display : 'none'
				});
				return false;
			});
			return false;
		}, function () {
			$('.selectbox').css('display', 'none');
			$(this).find('em').css({
				display : ''
			});
			$(this).find('i').css({
				display : 'none'
			});
			return false;
		});
		
		
		$(".pnr").click(function(){
			pandoraSubmitter.postDelete();
		});
		
		$(".pnc").click(function(){
			$(".confirm").hide();
		});

		$(".setting").toggle(function () {
			$(".cp").show();
			$(this).find('em').css({
				display : ''
			});
			$(this).find('i').css({
				display : 'none'
			});
		}, function () {
			$(".cp").hide();
			$(this).find('em').css({
				display : ''
			});
			$(this).find('i').css({
				display : 'none'
			});
		});
		
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
		 
		
		var isBottombarHoving=false;
		var $bottomBar=$(".bottombar");
		var bottomBarHoverOutTimer;
		$bottomBar.mouseleave(function(){
			 isBottombarHoving = false;
		}).mouseenter(function(){
			isBottombarHoving = true;
		}).hover(
				function(){
					if(bottomBarHoverOutTimer){
						clearTimeout(bottomBarHoverOutTimer);
					}
					
					$(this).animate({height:"44px"},1100);
				},
				function(){
					var self = this;
					 bottomBarHoverOutTimer = setTimeout(function(){
						if(!isBottombarHoving){
							$(self).animate({height:"5px"},1100);
						}
					},1100);
				
				});
			 
		 
});
