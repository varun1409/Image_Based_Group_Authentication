<%@page  import="java.sql.*" %>    
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">    
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Forgot Password Page</title>    
    <link href="css/bootstrap.min.css" rel="stylesheet">    
    <link href="css/CustomStyle.css" rel="stylesheet">
</head>
<body>
<form action="ForgetServlet" method="post" enctype="multipart/form-data" onSubmit="return myFunction()">
<%String msg=(String)request.getAttribute("msg");
if(msg!=null){
%>
<h3><%=msg %></h3>
<%} %>
<div class="container-fluid ">
		<div class="row">		
			<!-- header area start -->
      <div class="headerStyle">
          <span>
          <h1><img src="Images/UAlbany.png" style="height:60px;width:60px;">
				 Image-based Password Group Authentication Schemes - Forgot Password</h1>
				 <p  align="right">&copy; Varun & Shanmugar</p>
            </span>                
      </div>            
	  </div>
	  </div> 
<div class="row" style="margin:10px;">
            <div class="col-md-4"></div>
            <div class="col-md-4">		
            <div class="panel panel-default">
                  <div class="panel-heading" style="text-align:center;"><h4>Forgot Password</h4></div>                  
                   <div class="panel-body" style="text-align:center" >
     				<p style="color:red">${msg}</p>
                    <div class="form-group" style="text-align:left">
                    <label for="name" style="text-align:left">Please enter Email ID:</label>
                <input type="text" class="form-control" name="email" id="email" required maxlength="30">
                <div id ="emailerror" style="color:Red"></div>
            </div>                                           
                
            <button type="submit" class="btn btn-default" >Request Password</button>                                  
              </div>
            </div>            
            <div class="col-md-4"></div>
    </div>
</div>
</form>
<script>
function myFunction()
{	
	var email=document.getElementById("username");		
	var isError=true;
	
	if(!ValidateEmail(email))
	{
	alert("Invalid Email Address");
	isError=false;	
	}	
	
	return isError;
}

function ValidateEmail(mail)   
{  
	var email_regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/ ;
 if (mail.value.match(email_regex))  
  {  
    return (true)  
  }        
    return (false)  
}  
</script>     
</body>
</html>