package cryto.src;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutCheck
 */
public class LogoutCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutCheck() {
        super();        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//String text = new String();					 	
		DBConnect();
		//gtting the parameters
		HttpSession session = request.getSession();
		String user_name = (String) session.getAttribute("user_name");
		String group_name = (String) session.getAttribute("group_name");
		//check if both are active
		boolean check = check_active(user_name, group_name);				
		DBClose();
		//if not clearing the session and setting the flag
		if (!check) {
			if (session!=null) {
				session.invalidate();
			}
			String text = " ";
			response.setContentType("text/plain"); 
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(text);			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	//all db queries
	public void DBClose() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
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
	
	private boolean check_active(String user_name, String group_name) {	
		int temp = 0;
		try {
			String select_sql = "select is_active from group_dtl where group_name = ? and (user1 = ? or user2 = ?) and is_active = 1";
			PreparedStatement ps = con.prepareStatement(select_sql);	
			ps.setString(1, group_name);
			ps.setString(2, user_name);
			ps.setString(3, user_name);
			ResultSet output = ps.executeQuery();			
			while (output.next()) {			
				temp = output.getInt(1);
				//System.out.println(temp);
			}
			if (temp == 1) {
				return true;
			}
			else {
				return false;
			}			
		}
		catch (Exception ex) {
			System.out.println(ex);
			return false;
		}	
	}
}
