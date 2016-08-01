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
 * Servlet implementation class ForgetServlet
 */
public class ForgetServlet extends HttpServlet {	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForgetServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		//getting the parameters
		String email=request.getParameter("email");
		//connecting the database
		dao dao=new dao();
		//checking is email is present
		boolean isExist=dao.emailCheck(email);
		//if present processing else saying not present
		if(isExist)
		{
			long t = System.currentTimeMillis();	
			String tt = String.valueOf(t);
			dao.getPassword(email, tt);			
			File  f=new File("D:/Password"+tt+".jpg");
			String msg="Please login with using the attached image";
			//getting the password and sending it in email
			try {
				SendEmail.sendEmail("smtp.gmail.com", "587", "cryptoproject2016@gmail.com", "crypto123",email, "--------Forgot Password---------",msg,f);
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			f.delete();	
			msg="Password has been sent to your email.";
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("ForgetPassword.jsp").forward(request, response);
		}
		else
		{
			String msg="Entered email is not registered.";
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("ForgetPassword.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);		
	}
}
