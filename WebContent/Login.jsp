<%@page  import="java.sql.*" %>    
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">    
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login Page</title>    
    <link href="css/bootstrap.min.css" rel="stylesheet">    
    <link href="css/CustomStyle.css" rel="stylesheet">
    <script src="js/sam.js"></script>
</head>
<body>
<form action="LoginServlet" method="post" enctype="multipart/form-data" onSubmit="return myFunction2()">
<div class="container-fluid ">
		<div class="row">		
			<!-- header area start -->
      <div class="headerStyle">
          <span>
          <h1><img src="Images/UAlbany.png" style="height:60px;width:60px;">
				 Image-based Password Group Authentication Schemes - Login</h1>
				 <p  align="right">&copy; Varun & Shanmugar</p>
            </span>                
      </div>            
	  </div>
	  </div> 
<div class="row" style="margin:10px;">
            <div class="col-md-4"></div>
            <div class="col-md-4">		
            <div class="panel panel-default">
                  <div class="panel-heading" style="text-align:center;"><h4>Login</h4></div>                  
                   <div class="panel-body" style="text-align:center" >
     				<p style="color:red">${zc}</p>
     				<p style="color:black">${zc1}</p>
                    <div class="form-group" style="text-align:left">
                    <label for="name" style="text-align:left">Email ID:</label>
                <input type="text" class="form-control" name="user_name" id="username" required maxlength="30">
                <div id ="username" style="color:Red"></div>
            </div>
        
            <div class="form-group" style="text-align:left">
                <label for="pwd" style="text-align:left">Password Image:</label>                
                <input type="file" class="form-control" name="file" id="file" required>
                <div id="fileerror" style="color:Red"></div>
            </div>
            
            <div class="form-group" style="text-align:left">
                <label for="group_name" style="text-align:left">Group Name:</label>
                <input type="text" class="form-control" name="group_name" id="group_name" required maxlength="15">
            </div>
                                        
            <button type="submit" class="btn btn-default" >Login</button> <a href="sendmail.jsp"> Sign Up ?</a>  <a href="Group.jsp"> Create Group ?</a> <br> <a href="ForgetPassword.jsp"> Forgot Password ?</a>                                  
              </div>
            </div>            
            <div class="col-md-4"></div>
    </div>
</div>
</form>
</body>
</html>