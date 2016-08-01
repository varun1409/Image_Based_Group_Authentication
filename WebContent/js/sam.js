function myFunction()
{
	
	var fname=document.getElementById("name");
	var email=document.getElementById("email");
	var password=document.getElementById("pass");
	var confirmPassword=document.getElementById("conpass");
	var file=document.getElementById("file");
	
	var isError=true;


	
	 if(!(file.value==""))
		 {
		var value=file.files[0].size/1024/1024;		
         if(value>.15)
        	 {
        	 
        	 isError=false;
     		document.getElementById("fileerror").innerHTML="Image too large";
        	 }
         else
        	 {
        	 var fname = file.value; 
        	 var re = /(\.jpg)$/i;
        	 if(!re.exec(fname))
        		{        			
        			isError=false;
        			document.getElementById("fileerror").innerHTML="Please upload only jpg images";
        			
        		}
        	 else
        	 document.getElementById("fileerror").innerHTML="";
        	 }
		 }
	 else
		 {
		 isError=false;
		 document.getElementById("fileerror").innerHTML="Please upload an image";
		 }
	


	

	
	
	if(!ValidateEmail(email))
	{
	document.getElementById("emailerror").innerHTML="Invalid Email Address";
	isError=false;
	
	}
	else
		document.getElementById("emailerror").innerHTML="";
	//return isError;
	
	if(!(alphanumeric(password) && password.value.length>5))
	{
	
	document.getElementById("passerror").innerHTML="Password should not contain special characters.Min 6 chars.";
	isError=false;
	
	}
	else
		document.getElementById("passerror").innerHTML="";
	//return isError;
	
	if(!(confirmPassword.value==password.value))
	{
	document.getElementById("conpasserror").innerHTML="Password do not match";
	isError=false;
	
	}
	else
		document.getElementById("conpasserror").innerHTML="";
	//return isError;
	
	
	

	return isError;
}


function alphanumeric(input)  
{   
var letters = /^[0-9a-zA-Z]+$/;  
if(input.value.match(letters))  
return true;
else
return false;   
}


function ValidateEmail(mail)   
{  
	var email_regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/ ;
 if (mail.value.match(email_regex))  
  {  
    return (true)  
  }  
    //alert("You have entered an invalid email address!")  
    return (false)  
}  

function myFunction2() {
var file=document.getElementById("file");
	
	var isError=true;


	
	 if(!(file.value==""))
		 {
		var value=file.files[0].size/1024/1024;		
         if(value>1.9)
        	 {
        	 
        	 isError=false;
     		document.getElementById("fileerror").innerHTML="Image too large";
        	 }
         else
        	 {
        	 var fname = file.value; 
        	 var re = /(\.jpg)$/i;
        	 if(!re.exec(fname))
        		{        			
        			isError=false;
        			document.getElementById("fileerror").innerHTML="Please upload only jpg images";
        			
        		}
        	 else
        	 document.getElementById("fileerror").innerHTML="";
        	 }
		 }
	 else
		 {
		 isError=false;
		 document.getElementById("fileerror").innerHTML="Please upload an image";
		 }
}