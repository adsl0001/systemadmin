<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	session.removeAttribute("userInfo");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<title>系统登录</title>
<%@include file="view/include/header.jsp"%>
<style type="text/css">
.loginbox {
	box-shadow: 0px 10px 60px rgba(0, 0, 0, 0.4);
	width: 400px;
	border-radius: 3px;
	padding: 20px;
	background: #fff;
	background: rgba(255, 255, 255, 0.9);
	position: absolute;
	top: 46%;
	left: 50%;
	margin-top: -209px;
	margin-left: -214px;
}
.msg{color: #f96;text-align: center;margin-bottom: -10px;padding-top: 10px;}
</style>
</head>
<body style="background-color: white; margin-top: 2px;">


	<div class="loginbox">
		<legend>系统登录</legend>
		<form id="form" class="form-horizontal">
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-3 control-label">用户名</label>
				<div class="col-sm-9">
					<input type="text" id="username" name="username"
						class="form-control" placeholder="用户名">
				</div>
			</div>
			<div class="form-group">
				<label for="inputPassword3" class="col-sm-3 control-label">密码</label>
				<div class="col-sm-9">
					<input type="password" name="password" id="password"
						class="form-control" placeholder="密码">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-3 col-sm-9">
					<button type="button" class="btn btn-info" onclick="$.asubmit();">登录</button>
				</div>
			</div>
			<div class="msg"></div>
			<div style="clear: both;"></div>
		</form>
	</div>
	<%@include file="view/include/bottom.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			$.keyListener = function(e) {
				if (e.keyCode == 13) {
					$.asubmit();
				}
			}
			document.onkeydown = $.keyListener;
			$.asubmit = function( ) {
				if ($("#username").val() == "") {
					alert('请输入用户名');
					return false;
				}
				if ($("#password").val() == "") {
					alert('请输入密码');
					return false;
				}
				$.ajax({
					url : basePath + "user/login/checkUser.do",
					data : $("#form").serialize(),
					type : "post",
					success : function(e) {
						e = JSON.parse(e);
						if (e.success) {
							window.location.href = basePath
									+ "index.jsp";
						} else {
							alert(e.errorInfo);
						}
					}
				});
			}
		});
	</script>
</body>
</html>