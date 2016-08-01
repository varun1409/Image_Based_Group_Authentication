<%@page  import="java.sql.*" %>    
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">    
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>    
    <link href="css/bootstrap.min.css" rel="stylesheet">    
    <link href="css/CustomStyle.css" rel="stylesheet">
</head>
<body>
<form action="groupservlet" method="post" enctype="multipart/form-data">
<%String message=(String)request.getAttribute("message");
boolean isNull=true;
if(message!=null)
	isNull=false;
%>
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
                  <div class="panel-heading" style="text-align:center;"><h4>Group Creating Page</h4></div>                  
                   <div class="panel-body" style="text-align:center" >
     				<p style="color:red">${message}</p>     				
                    <div class="form-group" style="text-align:left">
                    <label for="gname" style="text-align:left">Group Name:*</label>
                <input type="text" class="form-control" name="gname" id="gname" required maxlength="15">
            </div>
        
            <div class="form-group" style="text-align:left">
                <label for="resemail" style="text-align:left">My Email:*</label>                
                <input type="text" class="form-control" name="resemail" id="resemail" required maxlength="30">
            </div>
            
            <div class="form-group" style="text-align:left">
                <label for="email" style="text-align:left">Other Person's Email:*</label>
                <input type="text" class="form-control" name="email" id="email" required maxlength="30">
            </div>
                        
                
            <button type="submit" class="btn btn-default" >Invite</button>                                   
              </div>
            </div>            
            <div class="col-md-4"></div>
    </div>
</div>
</form>     
</body>
</html>