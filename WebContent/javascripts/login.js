$(function(){
	$('#login').click(function(){
			  //文本失去焦点后
            $('form :input').blur(function(){
			 var $parent = $(this).parent();
			 $parent.find(".formtips").remove();
		
			  //验证邮件
			 if( $(this).is('#email') ){
				if( this.value=="" || ( this.value!="" && !/.+@.+\.[a-zA-Z]{2,4}$/.test(this.value) ) ){
                      var errorMsg = '请输入正确Email地址.';
					  $parent.append('<span class="formtips onError">'+errorMsg+'</span>');
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
					/*else{
					    var okMsg = '输入正确.';
					    $parent.append('<span class="formtips onSuccess">'+okMsg+'</span>');
					}*/
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
				
		
         var userName;
		 var password;
          userName=$('#email').val();
		 password=$('#password').val();
		 $.ajax({
			 type:"post",
			 url:"login/login.do",
			 data:{userName:$("#email").val(),password:$("#password").val()},
			 success:function(data){
				 if(data==0){
			 	//alert("登录成功");
			      window.location="home.html";
				 }
			 else{
				 alert("未找到该用户，请注册!");
				 window.location="register.html";
			 }
			 }
			 });
		 
	   });
	});
	