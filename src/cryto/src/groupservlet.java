package cryto.src;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class groupservlet
 */
@SuppressWarnings("unused")
public class groupservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public groupservlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		//getting the parameters
		String gname=request.getParameter("gname");
		String resemail=request.getParameter("resemail");
		String email=request.getParameter("email");
		//connecting the database
		dao dao=new dao();
		//checking is the group is present
		boolean n=dao.checkGroup(gname);
		//checking is email is present
		boolean f=dao.checkEmail(resemail);
		//setting up the all message
		String msg="";
		String mail="";		
		String message="";
		//if group is not present and email is present
		if(!n && f)	
		{
			//sending the invite to the person
			int id=dao.getId(resemail);
			boolean m=dao.checkEmail(email);
			int fr=0;
			int recid=834948439;
			if(m)
			{
				recid=dao.getId(email);
				fr=1;
			}
			//sending email
				mail="http://f3147fd1.ngrok.io/Crypto_Project_Web/InsertServlet?gdkd=jkdiF5jgsn"+id+"&ist=sdk73eje"+fr+"&ret439jjjdsi3="+recid+"&group="+gname;			
				try {
					SendEmail.sendEmailW("smtp.gmail.com", "587", "cryptoproject2016@gmail.com",
							"crypto123",email, "--------Group Invite-------",mail);
				} catch (AddressException e) {
					e.printStackTrace();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				//setting up the parameters
				message="Invite sent";
				request.setAttribute("message", message);
				request.getRequestDispatcher("Group.jsp").forward(request, response);
		}
		else
		{
			//setting up the parameters
			if(n)
				msg="Group already exist. Select different group name.";
			
			if(!f)
				msg="You are not regstered to the system. Please register.";			
			message=msg;
			request.setAttribute("message", msg);
			request.getRequestDispatcher("Group.jsp").forward(request, response);
			//System.out.println(msg);
		}
		
	}

}
