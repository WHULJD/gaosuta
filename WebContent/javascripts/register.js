$(function(){
$("form :input.required").each(function(){
	var $required=$("<strong class='high'> *</strong>");   //创建元素
	$(this).parent().append($required);     //将它追加到文档中
	});
 
		//提交，最终验证。
		 $('#register').click(function(){
			 
			 //文本失去焦点后
            $('form :input').blur(function(){
			 var $parent = $(this).parent();
			 $parent.find(".formtips").remove();
		
			  //验证邮件
			 if( $(this).is('#email') ){
				if( this.value=="" || ( this.value!="" && !/.+@.+\.[a-zA-Z]{2,4}$/.test(this.value) ) ){
                      var errorMsg = '请输入正确Email地址.';
					  $parent.append('<span class="formtips onError">'+errorMsg+'</span>');
			
				    $.ajax({
					 type:"post",
					 url:"login/checkUserNameExist.do",
					 data:{userName:$('#email').val()},
					 success:function(msg){
						 if (msg==1){
						 var errorMsg = '该邮箱已被注册';
						 $parent.append('<span class="formtips onError">'+errorMsg+'<span>');
						 }
			          }
						  });
					
				}
				/*else{
                      var okMsg = '输入正确.';
					  $parent.append('<span class="formtips onSuccess">'+okMsg+'</span>');
				}*/
			 }
			 
			 //验证密码
			 if( $(this).is('#password') ){
					if( this.value=="" || this.value.length < 6 ||this.value.length > 18){
					    var errorMsg = '请输入6-18位密码.';
                        $parent.append('<span class="formtips onError">'+errorMsg+'</span>');
					}
				/*	else{
					    var okMsg = '输入正确.';
					    $parent.append('<span class="formtips onSuccess">'+okMsg+'</span>');
					}
				*/
			 }
			 //再次验证密码
			 if( $(this).is('#passwordagain') ){
					if( this.value=="" || this.value.length < 6 ||this.value.length > 18){
					    var errorMsg = '请输入6-18位密码.';
                        $parent.append('<span class="formtips onError">'+errorMsg+'</span>');
					}else if(this.value==password.value){
					 break;
						//   var okMsg = '输入正确.';
					 // $parent.append('<span class="formtips onSuccess">'+okMsg+'</span>');
					}else{
						var disagreeMsg='两次输入密码不一致.';
						$parent.append('<span class="formtips onError">'+disagreeMsg+'</span>');
						}
			 }
			 
			
		}).keyup(function(){
		   $(this).triggerHandler("blur");
		}).focus(function(){
	  	   $(this).triggerHandler("blur");
		});//end blur
 
			 
				$("form :input.required").trigger('blur');
				var numError = $('form .onError').length;
				if(numError){
					return false;
				} 
				
				 $.ajax({
					 type:"post",
					 url:"user/insert.do",
					 data:{userName:$("#email").val(),password:$("#password").val(),nickName:$("#passwordagain").val(),headPhoto:$("#email")},
					 success:function(msg){
						 alert(msg);
	                    if(msg==0){
					 	  alert("注册成功");
					      window.location="login.html";
						 }
					 }
				});
				 
		 });
		 
	});	
