package cryto.src;

import java.io.IOException;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class acceptInviteServlet
 */
public class acceptInviteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public acceptInviteServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		//getting all the parameters
		String email=request.getParameter("email");
		String name=request.getParameter("name");
		String recemail=request.getParameter("recemail");
		String recname=request.getParameter("recname");
		String group=request.getParameter("group");
		//setting up the string for email.
		String mail=recname+" has accepted your Invite";
		String message="";
		//connecting to database
		dao dao=new dao();
		//checking is group is present
		boolean isGroupExist=dao.checkGroup(group);
		//if not present then inserting
		if(!isGroupExist)
		{
		dao.insertGroup(email,recemail,group);		
		try {
			//sending email
			SendEmail.sendEmailW("smtp.gmail.com", "587", "cryptoproject2016@gmail.com",
					"crypto123",email, "--------Invite Accepted-------",mail);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		//And redirecting to the login page
		request.getRequestDispatcher("Login.jsp").forward(request, response);
		}
		else
		{
			//If the group is present then going back to invite page itself.
			ArrayList<String> st=new ArrayList<String>();
			st.add(name);
			st.add(email);
			ArrayList<String> st2=new ArrayList<String>();
			st2.add(recname);
			st2.add(recemail);
			//Setting back the attributes
			request.setAttribute("user", st);
			request.setAttribute("group", group);
			request.setAttribute("recUser", st2);			
			message="The invitation is no longer valid.";
			request.setAttribute("message", message);
			request.getRequestDispatcher("invite.jsp").forward(request, response);		
		}					
	}
}
