<%@page  import="java.sql.*" %>    
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">    
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>    
    <link href="css/bootstrap.min.css" rel="stylesheet">    
    <link href="css/CustomStyle.css" rel="stylesheet">
    <script src="js/sam.js"></script>
</head>
<body>
<form action="mailservlet" method="post" enctype="multipart/form-data" onSubmit="return myFunction()">
<%String msg=(String)request.getParameter("msg");
System.out.println("MY:"+msg);
if(msg!=null){%>
<h3><%=msg %></h3>
<%} %>
<div class="container-fluid ">
		<div class="row">		
			<!-- header area start -->
      <div class="headerStyle">
          <span>
          <h1><img src="Images/UAlbany.png" style="height:60px;width:60px;">
				 Crypto - Registration</h1>
            </span>                
      </div>            
	  </div>
	  </div> 
<div class="row" style="margin:10px;">
            <div class="col-md-4"></div>
            <div class="col-md-4">		
            <div class="panel panel-default">
                  <div class="panel-heading" style="text-align:center;"><h4>Registration</h4></div>                  
                   <div class="panel-body" style="text-align:center" >
     				<p style="color:red">${msg}</p>
     				
     		<div class="form-group" style="text-align:left">
                <label for="name" style="text-align:left">Name:*</label>
                <input type="text" class="form-control" name="name" id="name" required maxlength="30">
            </div>
     				
            <div class="form-group" style="text-align:left">
                <label for="name" style="text-align:left">Email ID:*</label>
                <input type="text" class="form-control" name="email" id="email" required maxlength="30">
                <div id="emailerror" style="color:Red"></div>
            </div>
        
            <div class="form-group" style="text-align:left">
                <label for="pwd" style="text-align:left">Password:*</label>
                <input type="password" class="form-control" name="pass" id="pass" required maxlength="30">
                <div id="passerror" style="color:Red"></div>
            </div>
            
            <div class="form-group" style="text-align:left">
                <label for="pwd" style="text-align:left">Confirm Password:*</label>
                <input type="password" class="form-control" name="conpass" id="conpass" required maxlength="30">
                <div id="conpasserror" style="color:Red"></div>
            </div>
            
            <div class="form-group" style="text-align:left">
                <label for="group_name" style="text-align:left">Image:*</label>
                <input type="file" class="form-control" name="file" id="file" required>
                <div id="fileerror" style="color:Red"></div>
            </div>
                        
                
            <button type="submit" class="btn btn-default" >Register</button>                                    
              </div>
            </div>            
            <div class="col-md-4"></div>
    </div>
</div>
</form>     
</body>
</html>