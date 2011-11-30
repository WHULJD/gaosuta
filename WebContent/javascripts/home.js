		  $(function(){
				  
				  var name = 'guide';
				  if(readCookie(name)){ //check cookie, if 'guide' exists, hide the line.
					  $(".guide").hide();
					  $("input.cha").hide();
				  }
				  $("input.cha").click(function(){ //hide the line
					  writeCookie(name, "1", "30");
					  $(".guide").hide();
					  $("input.cha").hide();
				  });
		  });
	  
	  $(document).ready(function(){
		 
			  $("#text").focus(function(){
			  $("#text").animate({height:60},"fast");
			  });
		   $("#text").blur(function(){
			   $("#text").animate({height:40},"fast");
			   });
		 
		  });