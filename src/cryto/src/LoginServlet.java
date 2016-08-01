package cryto.src;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//connecting to the database
		DBConnect();
		//getting the file which was uploaded
		File uploadedFiles = saveUploadedFiles(request);
		//creating the insatance for encrypt and decrypt
		ImageHiding imh = new ImageHiding();
		//getting the parameters
		String user_name = request.getParameter("user_name");
		String pass = getPass(user_name);
		//getting the password back from the image
		String password = imh.Decrypt(uploadedFiles, pass);
		HttpSession session = request.getSession();		
		//String password = request.getParameter("password");
		String group_name = request.getParameter("group_name");
		//checking the login table and the group table
		boolean check_login = check_password(user_name, password);
		boolean check_group = check_group (user_name, group_name);
		//if both are valid going in
		if (check_login && check_group) {
			//updating user
			update_group_dtl(user_name, group_name);
			dao dao = new dao();
			String email = dao.getGroupMember(group_name, user_name);
			//sending email about activity
			SendMail(user_name,group_name,email);
			//if both are logged in then going in
			if (check_both_login(group_name)) {	
				session.setAttribute("user_name", user_name);
				session.setAttribute("group_name", group_name);
				request.getRequestDispatcher("Welcome.jsp").forward(request, response);
			}
			//if other person didn't login then showing the error message
			else {
				clear_group(user_name,group_name);
				request.setAttribute("zc", "Other user didn't login, Please Login Again !");
				request.getRequestDispatcher("Login.jsp").forward(request, response);
			}
			
		}
		//if password is not matching
		else {
			request.setAttribute("zc", "Login ID / Password / Group doesn't match");
			request.getRequestDispatcher("Login.jsp").forward(request, response);
		}
	}	
	//logic for seding email	
	public void SendMail (String user_name, String group_name, String email) {
		String mail="User " +user_name+ " of the group "+ group_name +" has invited you to log in, Please login to the system to enable him to login.";		
		try {			
			SendEmail.sendEmailW("smtp.gmail.com", "587", "cryptoproject2016@gmail.com",
					"crypto123",email, "--------Group Member Activity-------",mail);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) { 
			e.printStackTrace();
		}
	}
	//all database queries
	public String getPass (String user_name) {
		String pass = new String();
		try {
			String select = "select apass from user where email = ?";
			PreparedStatement ps = con.prepareStatement(select);
			ps.setString(1, user_name);
			ResultSet output = ps.executeQuery();
			while(output.next()) {
				pass = output.getString(1);
			}
			return pass;
		}
		catch (Exception ex) {
			System.out.println(ex);
			return pass;
		}		
	}
	
	private boolean clear_group(String user_name, String group_name) {
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
	
	private boolean check_both_login(String group_name) {
		for (int i = 0 ; i < 25; i++) {
			boolean check_both = check_both_user_status(group_name);
			if (check_both) {
				set_is_active(group_name);		
				return true;
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				System.out.println(e);				
			}
		}				
		return false;
	}

	private boolean set_is_active(String group_name) {
		try {
			String update_sql = "update group_dtl set is_active = 1 where group_name = ?";
			PreparedStatement ps = con.prepareStatement(update_sql);
			ps.setString(1, group_name);
			ps.executeUpdate();
			return true;
		}
		catch (Exception ex) {
			System.out.println(ex);
			return false;
		}
		
	}

	private boolean check_both_user_status(String group_name) {
		try {
			String select_sql = "select * from group_dtl where group_name = ? and user1_status = 1 and user2_status = 1";
			PreparedStatement ps = con.prepareStatement(select_sql);
			ps.setString(1, group_name);
			ResultSet output = ps.executeQuery();
			int count = 0;
			while (output.next()) {
				count = 1;
			}
			if (count == 1) {
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

	private boolean update_group_dtl(String user_name, String group_name) {
		try {
			String select_sql = "select user1_status from group_dtl where (user1 = ? or user2 = ?) and group_name = ?";
			String column_name = new String();
			PreparedStatement ps1 = con.prepareStatement(select_sql);
			ps1.setString(1, user_name);
			ps1.setString(2, user_name);
			ps1.setString(3, group_name);
			ResultSet output = ps1.executeQuery();
			while(output.next()) {
				String user_status = output.getString(1);
				if (user_status.equals("1")) {
					column_name = "user2_status";
				}
				else {
					column_name = "user1_status";
				}
			}
			String update_sql = new String();
			if (column_name.equals("user1_status")) {
				update_sql = "update group_dtl set user1_status = ? where group_name = ? and (user1 = ? or user2 = ?)";
			}
			else {
				update_sql = "update group_dtl set user2_status = ? where group_name = ? and (user1 = ? or user2 = ?)";
			}
			PreparedStatement ps = con.prepareStatement(update_sql);			
			ps.setString(1, "1");
			ps.setString(2, group_name);
			ps.setString(3, user_name);
			ps.setString(4, user_name);
			ps.executeUpdate();
			return true;
		}
		catch(Exception ex) {
			System.out.println(ex);
			return false;
		}		
	}

	private boolean check_group(String user_name, String group_name) {
		try {
			String select_sql = "select * from group_dtl where group_name = ? and (user1 = ? or user2 = ?)";
			PreparedStatement ps = con.prepareStatement(select_sql);
			ps.setString(1, group_name);
			ps.setString(2, user_name);
			ps.setString(3, user_name);
			int count = 0;
			ResultSet output = ps.executeQuery();
			while(output.next()) {
				count=1;
			}
			if (count==1) {
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

	private boolean check_password(String user_name, String password) {
		try {
			String select_sql = "select count(*) from user where email = ? and apass = ?";
			PreparedStatement ps = con.prepareStatement(select_sql);
			int max_id = 0;
			ps.setString(1, user_name);
			ps.setString(2, password);
			ResultSet output = ps.executeQuery();
			while(output.next()) {
				max_id = output.getInt(1);
			}
			if (max_id > 0) {
				return true;
			}
			return false;
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
	
	private File saveUploadedFiles(HttpServletRequest request)
            throws IllegalStateException, IOException, ServletException {
        //File listFiles = new File();
    	File saveFile=null;
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        Collection<Part> multiparts = request.getParts();
        if (multiparts.size() > 0) {
            for (Part part : request.getParts()) {
                // creates a file to be saved
                String fileName = extractFileName(part);
                if (fileName == null || fileName.equals("")) {
                    // not attachment part, continue
                    continue;
                }
                 
                saveFile = new File(fileName);
                //System.out.println("saveFile: " + saveFile.getAbsolutePath());
                FileOutputStream outputStream = new FileOutputStream(saveFile);
                 
                // saves uploaded file
                InputStream inputStream = part.getInputStream();
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                inputStream.close();                                
            }
        }
        return saveFile;
    }
 
    /**
     * Retrieves file name of a upload part from its HTTP header
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return null;
    }
}
