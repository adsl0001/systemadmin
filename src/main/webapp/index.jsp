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
<title>查询系统信息</title>
<%@include file="view/include/header.jsp"%>
</head>
<body style="background-color: white; margin-top: 2px;">
	<div class="container">
		<legend>系统信息</legend>
		<div class="row">
			<div class="col-xs-6 col-sm-6">
				<div class="panel panel-info">
					<div class="panel-heading">CPU信息</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-3 control-label text-right">cpu占用率</div>
							<div id ="cpuRatio" class="col-sm-9">
								0%
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-6 col-sm-6">
				<div class="panel panel-info">
					<div class="panel-heading">内存信息</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-3 control-label text-right">物理内存总数</div>
							<div id="totalMemorySize" class="col-sm-9">
								 0
							</div>
						</div>
						<div class="row">
							<div class="col-sm-3 control-label text-right">已使用</div>
							<div id="usedMemory" class="col-sm-9">
								 0
							</div>
						</div>
						<div class="row">
							<div class="col-sm-3 control-label text-right">可用</div>
							<div  id ="freeMemory" class="col-sm-9">
								 0
							</div>
						</div>
						 
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="view/include/bottom.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			$.initHtml = function(obj) {
				$("#cpuRatio").html((obj.cpuRatio*100).toFixed(2)+"%");
				$("#totalMemorySize").html(obj.totalMemorySize);
				$("#usedMemory").html(obj.usedMemory);
				$("#freeMemory").html(obj.freeMemory);
			}
			$.asubmit = function() {
				$.ajax({
					url : basePath + "system/systemInfo/getSystemInfo1.do",
					data : {
						time : new Date()
					},
					success : function(e) {
						e = JSON.parse(e);
						$.initHtml(e);
					}
				});
			}
			setInterval($.asubmit, 3000);
		});
	</script>
</body>
</html>