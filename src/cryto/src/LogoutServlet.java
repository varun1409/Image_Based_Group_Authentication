package cryto.src;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutServlet
 */
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		HttpSession session = request.getSession();
		//getting parameters
		String user_name = (String) session.getAttribute("user_name");
		String group_name = (String) session.getAttribute("group_name");
		DBConnect();
		//updating the DB that logged out
		logout_user(user_name, group_name);
		//clearing the session
		if (session!=null) {
			session.invalidate();
		}
		request.getRequestDispatcher("Login.jsp").forward(request, response);
	}
//database queries
	private boolean logout_user(String user_name, String group_name) {
		try {
			String update_sql = "update group_dtl set user1_status = 0 , user2_status = 0, is_active = 0 where group_name = ? and (user1 = ? or user2 = ?)";
			PreparedStatement ps = con.prepareStatement(update_sql);
			ps.setString(1, group_name);
			ps.setString(2, user_name);
			ps.setString(3, user_name);
			ps.executeUpdate();
			return true;
		}
		catch (Exception ex) {
			System.out.println(ex);
			return false;
		}
	}
	
	public boolean DBConnect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/crypto_proj","root","root");
			return true;
		}
		catch(Exception ex) {
			System.out.println(ex);
			return false;
		}
	}
}
