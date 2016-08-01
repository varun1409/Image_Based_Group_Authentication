<%@page  import="java.sql.*" %>
<%@page  import="java.util.*" %>        
<%! @SuppressWarnings("unchecked") %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">    
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Group Acceptance</title>    
    <link href="css/bootstrap.min.css" rel="stylesheet">    
    <link href="css/CustomStyle.css" rel="stylesheet">
</head>
<body>
<form action="acceptInviteServlet" method="post">
<%String message=(String)request.getAttribute("message");
boolean isNull=true;
if(message!=null)
	isNull=false;
%>
<%ArrayList<String> user=(ArrayList<String>)request.getAttribute("user");
ArrayList<String> recUser=(ArrayList<String>)request.getAttribute("recUser");%>
<%if(!isNull){ %>
<%} %>

<div class="container-fluid ">
		<div class="row">		
			<!-- header area start -->
      <div class="headerStyle">
          <span>
          <h1><img src="Images/UAlbany.png" style="height:60px;width:60px;">
				 Crypto - Login</h1>
            </span>                
      </div>            
	  </div>
	  </div> 
<div class="row" style="margin:10px;">
            <div class="col-md-4"></div>
            <div class="col-md-4">		
            <div class="panel panel-default">
                  <div class="panel-heading" style="text-align:center;"><h4>Group Acceptance</h4></div>                  
                   <div class="panel-body" style="text-align:center" >
     				<p style="color:red">${message}</p>
                   <h3><%=user.get(0) %> has invited you the group. Please click on the below link to accept.</h3>
<input type="submit" id="accept" name="accept" value="accept" class="btn btn-default">
<input type="hidden" name="email" id="email" value=<%=user.get(1) %>>
<input type="hidden" name="name" id="name" value=<%=user.get(0) %>>
<input type="hidden" name="recemail" id="recemail" value=<%=recUser.get(1) %>>
<input type="hidden" name="recname" id="recname" value=<%=recUser.get(0) %>>
<input type="hidden" name="group" id="group" value=<%=request.getAttribute("group") %>>
              </div>
            </div>            
            <div class="col-md-4"></div>
    </div>
</div>
</form>     
</body>
</html>