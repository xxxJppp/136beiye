 <%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>176平台代理登录</title>
<meta name="viewport" content="width=device-width,initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link href="${pageContext.request.contextPath}/favicon.png" rel="icon" type="image/png" sizes="18x18"/>
<link href="${pageContext.request.contextPath}/icofont/iconfont.css" rel="stylesheet"/>
<link href="${pageContext.request.contextPath}/css/login/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/css/login/style.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/css/login/login.css" rel="stylesheet" type="text/css"/>
<style>
.login-box-title{
	vertical-align: middle;
	color: #fff;
	font-size: 20px;
	height: 40px;
	line-height: 40px;
	font-family:"微软雅黑";
	margin-bottom: 18px;
}
.login-box-title-ico{
	font-size: 32px;
	color: red;
}
.login-box-title-content{
	position: fixed;
	margin-left: 20px;
	letter-spacing: 2px;
	font-weight: bold;
}
</style>
<script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jsencrypt.min.js"></script>
</head>
<body>
<section id="wrapper" class="login-register">
	<div class="login-box">
		<div class="login-box-in">
			<form class="form-horizontal form-material" id="login-form" method="post" action="">
				<div class="login-box-title">
					<span class="login-box-title-ico cp-icon icon-fengshui"></span>
					<span class="login-box-title-content">176代理平台登录</span>
				</div>
				<div class="form-group ">
					<div class="col-xs-12">
						<span class="form-txt"> 手机号：</span>
						<input class="form-control" name="mobile" id="mobile" type="text" placeholder="" style="padding-left: 67px;">
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<span class="form-txt"> 密码：</span> 
						<input class="form-control" name="password" id="password" type="password" placeholder="">
					</div>
				</div>
				<div class="form-group text-center m-t-20">
					<div class="col-xs-12">
						<button class="btn btn-info btn-lg btn-block text-uppercase waves-effect waves-light" type="button" id="loginbtn">登录</button>
					</div>
				</div>
				<p class="tips-waring" id="showLoginInfo"></p>
			</form>
		</div>
	</div>
</section>
</body>
<script type="text/javascript">
var pbkey = 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuFPdRBaE6OnqQsXvVim5mOmXhXidOa16gLIV9sI2dRZW4ZejlE+zGIsAlbsDYagO+RkjgVeZd4oZ+P7roYNlLtY8YujNkMnhMqHHKAOXUqK6HMBzrCsu/JaMFNsYW7EyEnXnd1Lq15U85O1irXLYfBbrGjKrYlKv5p+3xUYKRRc+PdeYDCKPKLKl9vgZwACVYnYEEuM2ErxdYWRARukSIEOtO69X6C1xlJL+MnQlFMTWLvg2m3pvAHqa/ZMJLwFE9mf+29LnjAJJvPWjMCUGroU0Hjoq+cPTJp5EfPM6jHlIhHzl8oZCHvNRFgOkXz5aFqNbmg5m0vs8vugBGXN61wIDAQAB';
$(function()
{
	if(self.location != top.location)
	{
		top.location.href = self.location.href;
	}
	//登录
	$(document).on("click", "#loginbtn", function() 
	{
		login_submit();
	});
	//键盘监控,按回车登录
	document.onkeydown = function(event) 
	{
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 13) 
		{
			login_submit();
		}
	};
	var cookies = document.cookie.split(';');
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	for(var i = 0; i < cookies.length; i ++) 
	{
		var c = cookies[i];
		while(c.charAt(0) == ' ')
		{
			c = c.substring(1, c.length);
		}
		c = c.split("=");
		if(c[0] == "sidebarHoverFlag" || c[0] == "userPageSize" || c[0].indexOf("table_hide_columns") > -1 || c[0].indexOf("table_show_columns") > -1)
		{
			document.cookie = c[0] + "=" + c[1] + ";expires=" + exp.toGMTString();
		}
    }
});
//登录
var login_submit = function()
{
	if($.trim($("#mobile").val()) == "" || $.trim($("#password").val()) == "")
	{
		$("#showLoginInfo").html("手机号或密码不能为空！");
		$("#showLoginInfo").fadeIn(300);
		return false;
	}
	$('#password').val(getRsaData($('#password').val()));//rsa加密密码
	$.ajax({
		url : '${pageContext.request.contextPath}/agent/login',
		type : 'post',
		dataType : 'json',
		data : {mobile : $("#mobile").val(),password : $("#password").val()},
		success : function(json)
		{
			if (json.dcode == 1000)
			{
				location.href = '${pageContext.request.contextPath}/index';
			} 
			else 
			{
				$("#showLoginInfo").html(json.dmsg);
				$("#showLoginInfo").fadeIn(300);
			}
		},
		error : function() 
		{
			$("#showLoginInfo").html("服务器或网络错误,登录失败!");
		}
	});
};
//rsa加密
function getRsaData(source)
{
	var encrypt = new JSEncrypt();
	encrypt.setPublicKey(pbkey);
	var rsadata = encrypt.encrypt(source);
	return rsadata
}
/**
 * 设置cookie
 * @param	name	存入cookie中的key
 * @param	value	存入cookie中的值
 */
function setCookie(name,value,option)
{
	var Days = 30; 
	var exp = new Date();
	if(option != undefined && option.expires != undefined)
	{
		exp.setTime(exp.getTime() + option.expires);
		document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
	}
	else
	{
		document.cookie = name + "=" + escape(value);
	}
}
/**
 * 读取cookie
 * @param	name	cookie中的key
 */
function getCookie(name)
{
	var keys = name + "=";  
	var ca = document.cookie.split(';');
	for(var i = 0;i < ca.length; i ++)
	{
		var c = ca[i];
		while(c.charAt(0) == ' ') 
		{
			c = c.substring(1,c.length);
		}
		if(c.indexOf(keys) == 0) 
		{
			return unescape(c.substring(keys.length,c.length));
		}
	}
	return "";
}
/**
 * 删除指定cookie
 */
function removeCookie(name)
{
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cvalue = getCookie(name);
	if(cvalue != null && cvalue != undefined)
	{
		document.cookie = name + "=" + cvalue + ";expires=" + exp.toGMTString();
	}
}
</script>
</html>