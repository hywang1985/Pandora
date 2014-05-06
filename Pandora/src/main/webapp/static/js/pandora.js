
$(document).ready(function () {
	var $Kint; //Keditor插件
	var $keEditor;
	var $keContainer;
	
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
	var $shownTitle ;
	//可编辑文章标题
	var $editTitle = $(".edit_title");
	
	
	 //选中图片的预览功能	 
	var uploadImgArr = [];
	//此次操作需要删除的之前已经存在的图片
	var deleteImgIds = [];  
	
	$('.edit').click(function () {
		uploadImgArr.splice(0,uploadImgArr.length);
		deleteImgIds.splice(0,deleteImgIds.length); 
		$(".setting,.cpbottom").show();
		$(".bar,.bottombar").hide();
		
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

	$('.cpbottom .cancel').click(function () {
		$(".cpbottom,.setting,.cp").hide();
		$editTitle.hide();
		$shownTitle.show();
		$(".ke-container").hide(function () {
			$currentArticle.find(".inner").fadeIn(1500);
			$(".bar,.bottombar").show();
		});
		return false;
	})

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
	
	 function onDelImg(){
		 var $parent = $(this).parent();
		 var currentIndex = $(".bg .cl li").index($parent);
		 $.each( $(".bg .cl li"), function(i, li){
			 if(currentIndex == i){
				 if(li.descriptorId){ //已经存在的图片
					 deleteImgIds.push(li); 
				 }
				 uploadImgArr= uploadImgArr.splice(i, 1);//从选中图片中移除
				 $(li).hide(); //隐藏当前li元素
			 }
			});
	 }
	 
	 $(".delImg").click(onDelImg);
})
