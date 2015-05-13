<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

	<div id="footerWrap">
		<div id="footer">
			<div class="footerBlock" id="about">
				<h3><a id="logoFooter" href="<%=request.getContextPath() %>"><%if(request.getAttribute("blogName")!=null){
						out.print((String) request.getAttribute("blogName"));
					}%></a></h3>
				<p id="slogan"><%if(request.getAttribute("blogDesc")!=null){
						out.print((String) request.getAttribute("blogDesc"));
					}%></p>
			</div><!--end #about-->
			<div class="footerBlock" id="contact">
				<h3 class="ttlFooterBlock">LIÊN HỆ</h3>
				<ul>
					<li><a id="fb" href="https://www.facebook.com/xuankhanhyb">Be a fan on Facebook</a></li>
					<li><a id="yt" href="#">Subscribe on Youtube chanel</a></li>
					<li><a id="tt" href="#">Follow us on Twitter</a></li>
					<li><a id="gp" href="#">Circle us on Google+</a></li>
					<li><a id="gm" href="#">Gmail: xuankhanhyb@gmail.com</a></li>
					<li><a id="yh" href="#">Yahoo: xuankhanhyb</a></li>
					<li><a id="ph" href="#">Phone: +84 1676 143 529</a></li>
				</ul>
			</div><!--end #contact-->
			<div class="footerBlock" id="other">
				<h3 class="ttlFooterBlock">ĐÓNG GÓP Ý KIẾN</h3>
				<p>KHÁNHSPRING - Website tổng hợp tin tức, giải trí, học tập, mẹo hay. KHÁNHSPRING mong nhận được sự ủng hộ của quý bạn đọc.</p>
				<a id="btnContribute" href="#">Đóng góp ý kiến</a>
			</div><!--end #other-->
		</div><!--end #footer-->
		<div id="endFooterWrap">
			<div id="endFooter">
				<span id="copyright">&copy; khanhspring.tk 2014 | Designer: Trần Xuân Khánh - HaUI</span>
				<span id="link">Liên kết trang: <a href="#">Alopic</a><a href="#">Alopic Video</a></span>
			</div>
		</div>

	</div><!--end #footerWrap-->
</body>
</html>