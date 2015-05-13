<%@page import="libraries.CommonLibrary"%>
<%@page import="java.util.Calendar"%>
<%@page import="libraries.Utilities"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="models.POJO.Post"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	ArrayList<Post> listRandPost = (ArrayList<Post>) request.getAttribute("listRandPost");
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	Calendar cal = Calendar.getInstance();
	String pageName = CommonLibrary.getPageByURL(request.getRequestURI());
%>
<div id="sidebar">
	<%
		if(pageName!=null && !pageName.equals("single")){
	%>
	<div class="sidebarBlock" id="timerWrap">
		<h3 id="ttlTimer">WELCOME TO KHANHSPRING</h3>
		<div id="clock">
			<span id="1h" class="h">I</span> <span id="2h" class="h">II</span> <span
				id="3h" class="h">III</span> <span id="4h" class="h">IV</span> <span
				id="5h" class="h">V</span> <span id="6h" class="h">VI</span> <span
				id="7h" class="h">VII</span> <span id="8h" class="h">VIII</span> <span
				id="9h" class="h">IX</span> <span id="10h" class="h">X</span> <span
				id="11h" class="h">XI</span> <span id="12h" class="h">XII</span> <span
				id="hoursWrap"><span id="hours"></span></span> <span id="minWrap"><span
				id="min"></span></span> <span id="secWrap"><span id="sec"></span></span> <span
				id="dateWrap"><span id="day"></span><span id="mon"></span><span
				id="year"></span></span> <span id="pin"></span> <span id="moment"></span>
		</div>
		<!-- JavaScript for Clock -->
		<script type="text/javascript">
			var i;
			for (i = 1; i <= 12; i++) {
				var h = "";
				h = i.toString() + "h";
				var x = 100 * Math.cos((Math.PI / 6) * i) + 115;
				var y = 100 * Math.sin((Math.PI / 6) * i) + 115;
				var dh = document.getElementById(h);
				var bt = x.toString() + "px";
				var l = y.toString() + "px";
				dh.style.bottom = bt;
				dh.style.left = l;
			}

			var d = new Date();
			var rt = " rotate(" + (d.getSeconds() * 6).toString() + "deg)";
			document.getElementById("secWrap").style.transform = rt;
			rt = " rotate(" + (d.getMinutes() * 6).toString() + "deg)";
			document.getElementById("minWrap").style.transform = rt;
			rt = " rotate("
					+ (d.getHours() * 30 + d.getMinutes() * 0.5).toString()
					+ "deg)";
			document.getElementById("hoursWrap").style.transform = rt;
			printerDate();
			setMoment();
			function printerDate() {
				var d = new Date();
				if (d.getDate()<10) {
					document.getElementById("day").innerHTML = "0"
							+ d.getDate().toString();
				} else {
					document.getElementById("day").innerHTML = d.getDate()
							.toString();
				}
				if (d.getMonth() < 10) {
					document.getElementById("mon").innerHTML = "0"
							+ (d.getMonth() + 1).toString();
				} else {
					document.getElementById("mon").innerHTML = (d.getMonth() + 1)
							.toString();
				}
				document.getElementById("year").innerHTML = d.getFullYear()
						.toString();
			}
			function setMoment() {
				var d = new Date();
				if (d.getHours()<= 5 || d.getHours() >= 18) {
					document.getElementById("moment").style.background = "#fff url('/WebAppDemo/images/bg/moon.png') no-repeat center center";
				} else {
					document.getElementById("moment").style.background = "#fff url('/WebAppDemo/images/bg/sun.png') no-repeat center center";
				}
				document.getElementById("moment").style.backgroundSize = "cover";
			}
			setInterval(function() {
				secAction()
			}, 200);
			function secAction() {
				var d = new Date();
				var second = d.getSeconds();
				if (second == 0) {
					minAction();
					hoursAction();
				}
				var dg = second * 6;
				var rt = " rotate(" + dg.toString() + "deg)";
				document.getElementById("secWrap").style.transform = rt;
			}
			function minAction() {
				var d = new Date();
				var min = d.getMinutes();
				var dg = min * 6;
				var rt = " rotate(" + dg.toString() + "deg)";
				document.getElementById("minWrap").style.transform = rt;
			}
			function hoursAction() {
				var d = new Date();
				var hours = d.getHours();
				var min = d.getMinutes();
				var i;
				for (i = 12; i < 24; i++) {
					if (hours >= i) {
						hours = hours - 12;
					}
				}
				var dg = hours * 30 + min * 0.5;
				var rt = " rotate(" + dg.toString() + "deg)";
				document.getElementById("hoursWrap").style.transform = rt;
				if (hours == 0) {
					printerDate();
				}
				setMoment();
			}
		</script>
		<div id="timer">
			<ul>
				<li>
					<div class="timeBox">
						<span class="timeValue"><%=cal.get(Calendar.DATE)%></span><span
							class="attTime">Ngày</span>
					</div>
				</li>
				<li>
					<div class="timeBox">
						<span class="timeValue"><%=cal.get(Calendar.MONTH) + 1%></span><span
							class="attTime">Tháng</span>
					</div>
				</li>
				<li>
					<div class="timeBox">
						<span class="timeValue"><%=cal.get(Calendar.YEAR)%></span><span
							class="attTime">Năm</span>
					</div>
				</li>
			</ul>
		</div>
		<!--end #timer-->
	</div>
	<%
		}else{ 
			ArrayList<Post> relatedPosts = (ArrayList<Post>) request.getAttribute("relatedPosts");
			if(relatedPosts.size()>0){
	%>

	<div class="sidebarBlock" id="relatedPostsWrap">
		<h3 id="ttlRelatedPosts">BÀI VIẾT LIÊN QUAN</h3>
		<ul>
			<%
			for(int i = 0; i<relatedPosts.size(); i++){
			%>
			<li>
				<div class="hotPotsSBItem">
					<div class="illRelatedPostsSB">
						<a
							href="<%=request.getContextPath()+"/"+relatedPosts.get(i).getCatePostStr()+"/"+relatedPosts.get(i).getPostSlug()%>"><img
							src="<%=request.getContextPath()%>/uploads/thumbnails/<%if(relatedPosts.get(i).getIllustration()!=null){%>
									<%=relatedPosts.get(i).getIllustration()%>
									<%}else{%>ill_default.jpg<%}%>"
							alt="<%=relatedPosts.get(i).getPostName()%>" /></a>
					</div>
					<div class="ttlRelatedPostsSB">
						<h4>
							<a
								href="<%=request.getContextPath()+"/"+relatedPosts.get(i).getCatePostStr()+"/"+relatedPosts.get(i).getPostSlug()%>"><%=Utilities.theContent(relatedPosts.get(i).getPostName(), 9)+"..."%></a>
						</h4>
						<span><%=dateFormat.format(relatedPosts.get(i).getPostOn())%></span>
					</div>
				</div>
			</li>
			<%
				}
			%>
		</ul>
		<!--end #relatedPosts-->
	</div>
	<!--end #relatedPostsWrap-->
	<%
		}
		}
	%>
	<%
		if(listRandPost!=null && listRandPost.size() != 0){
	%>
	<div class="sidebarBlock" id="hotPostSBList">
		<h3 class="ttlSidebarBlock" id="ttlHotPostSBList">CÓ THỂ BẠN SẼ
			THÍCH</h3>
		<ul>
			<%
				for(int i = 0; i<listRandPost.size(); i++){
			%>
			<li>
				<div class="hotPotsSBItem">
					<div class="illHotPostSB">
						<a
							href="<%=request.getContextPath()+"/"+listRandPost.get(i).getCatePostStr()+"/"+listRandPost.get(i).getPostSlug()%>"><img
							src="<%=request.getContextPath()%>/uploads/thumbnails/<%if(listRandPost.get(i).getIllustration()!=null){%>
									<%=listRandPost.get(i).getIllustration()%>
									<%}else{%>ill_default.jpg<%}%>"
							alt="<%=listRandPost.get(i).getPostName()%>" /></a>
					</div>
					<div class="ttlHotPostSB">
						<h4>
							<a
								href="<%=request.getContextPath()+"/"+listRandPost.get(i).getCatePostStr()+"/"+listRandPost.get(i).getPostSlug()%>"><%=Utilities.theContent(listRandPost.get(i).getPostName(), 9)+"..."%></a>
						</h4>
						<span><%=dateFormat.format(listRandPost.get(i).getPostOn())%></span>
					</div>
				</div>
			</li>
			<%
				}
			%>
		</ul>
		<span id="lineEndHotPostSB"></span>
	</div>
	<!--end #hotPostSBList-->
	<%
		}
	%>
	<div class="sidebarBlock" id="likeFBBox">
		<h3 id="ttlLikeFBBox">FACEBOOK</h3>
		<div id="fb-root"></div>
		<div id="fb-root"></div>
		<div id="fb-root"></div>
		<div id="fb-root"></div>
		<div id="fb-root"></div>
		<script>
			(function(d, s, id) {
				var js, fjs = d.getElementsByTagName(s)[0];
				if (d.getElementById(id))
					return;
				js = d.createElement(s);
				js.id = id;
				js.src = "//connect.facebook.net/vi_VN/sdk.js#xfbml=1&version=v2.0";
				fjs.parentNode.insertBefore(js, fjs);
			}(document, 'script', 'facebook-jssdk'));
		</script>

		<div class="fb-like-box"
			data-href="https://www.facebook.com/alopicture" data-width="310"
			data-colorscheme="light" data-show-faces="true" data-header="false"
			data-stream="false" data-show-border="false"></div>
	</div>
	<!--end #likeFBBox-->
</div>
<!--end #sidebar-->
