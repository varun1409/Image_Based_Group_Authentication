<%@page  import="java.sql.*" %>    
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">    
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>    
    <link href="css/bootstrap.min.css" rel="stylesheet">    
    <link href="css/CustomStyle.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
$(document).ready( function() {
	setInterval(function() {		
	$.get("LogoutCheck", function(responseText) {					
		$("#somediv").text(responseText);
		if(responseText == " ") {					
			document.getElementById('f2').submit();
		}
	});
	},2000);
}); 
</script>
</head>
<body>
<form action="LogoutMessage" method="post" name="f2" id="f2">
</form>
<form action="LogoutServlet" method="post" name="f1" id="f1">
<%String user_name = (String) session.getAttribute("user_name");
String group_name = (String) session.getAttribute("group_name");%>
    <div class="container-fluid ">
		<div class="row">		
			<!-- header area start -->
      <div class="headerStyle">
          <span>
          <h1><img src="Images/UAlbany.png" style="height:60px;width:60px;">
				Crypto</h1>
				<p  align="right">&copy; Varun & Shanmugar</p>
            </span>    
                 <span style="float:right;">
                 <strong >Welcome <%=user_name%></strong> &nbsp;
                 <input type="submit" class="btn btn-primary" value = "Logout" style="height:28px;width:80px">                 
                </span>                           
      		</div>	
			</div>
			</div>
			<div class="row" style="margin:10px;">
            <div class="col-md-4"></div>
            <div class="col-md-4">		
            <div class="panel panel-default">
                  <div class="panel-heading" style="text-align:center;"><h4>Main Site</h4></div>                  
                   <div class="panel-body" style="text-align:center" >
     				<p style="color:red">${message}</p>
                   <h3>Welcome to the site.</h3>
                   </div>
                   </div>
                   </div>
                   </div>
<div id="somediv"></div>
<input type="hidden" name = "user_name" value=<%=user_name %>>
<input type="hidden" name = "group_name" value=<%=group_name %>>
</form>
</body>
</html>