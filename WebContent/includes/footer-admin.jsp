<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	</div><!--end #contentWrap-->
</div><!--end #containerWrap-->
<div id="footerAdmin">
	<span>Designer: Trần Xuân Khánh | HaUI - Phone: (+84) 1676 143 529</span>
</div><!--end #footerAdmin-->
	<!-- JavaScript -->
	<script type="text/javascript"> 
		$(document).ready(function(){
		  	$("#option").click(function(even){
				even.preventDefault();
		    	$("#subUserMenu").toggle();
		  });
		});
	</script>
	<script type="text/javascript"> 
		$(document).ready(function(){
			$(".openned").show();
			$(".openned").parent().find(".parentMainMenu").addClass("activated");
		  	$(".parentMainMenu").click(function(even){
		  		even.preventDefault();
		    	$(this).parent().find(".subMainMenu").stop().slideToggle(200);
		    	$(this).parent().find(".parentMainMenu").toggleClass("activated");
		  	});
		});
	</script>
</body>
