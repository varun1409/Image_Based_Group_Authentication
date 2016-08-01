package cryto.src;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InsertServlet
 */
public class InsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertServlet() {
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
		//doGet(request, response);
		//getting the parameters
		String isExist=request.getParameter("ist");
		String senderid=request.getParameter("gdkd");
		String group=request.getParameter("group");
		String recId=request.getParameter("ret439jjjdsi3");
		//parsing the values
		int receiver=Integer.parseInt(recId);
		int isAlreadyRegister=Integer.parseInt(isExist.substring(8));
		int sender=Integer.parseInt(senderid.substring(10));		
		//connecting the database
		dao dao=new dao();
		//getting the arraylist
		ArrayList<String> user=dao.getUserDetails(sender);
		ArrayList<String> recUser=dao.getUserDetails(receiver);
		//if user is not registered then going to registration page
		if(isAlreadyRegister==0)
		{
			request.getRequestDispatcher("sendmail.jsp").forward(request, response);
		}
		else
		{
			//setting up the parameters 
			request.setAttribute("user", user);
			request.setAttribute("group", group);
			request.setAttribute("recUser", recUser);
			request.getRequestDispatcher("invite.jsp").forward(request, response);
		}		
	}
}