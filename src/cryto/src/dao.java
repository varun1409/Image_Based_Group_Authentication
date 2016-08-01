package cryto.src;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
//database class
public class dao {
	//getting the group member
	public String getGroupMember(String grp,String username) {		
		String grpmem="";
		try
		{
			//preparing connection
		Connection con=null;
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/crypto_proj", "root", "root");
		String sql2="Select * from group_dtl where group_name=? and (user1=? or user2=?)";		
		PreparedStatement preparedStmt2 = con.prepareStatement(sql2);
		preparedStmt2.setString(1, grp);
		preparedStmt2.setString(2, username);
		preparedStmt2.setString(3, username);
		ResultSet rs=preparedStmt2.executeQuery();
		if(rs.next())
		{
			if(username.equals(rs.getString("user1")))
			{
				grpmem=(rs.getString("user2"));
			}
			else
			{
				grpmem=(rs.getString("user1"));
			}				
		}			
		con.close();		
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return grpmem;
	}
//inserting file
	public void insertFile(String name,String email,String pass,File file)
	{
		try
		{
		Connection con=null;
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/crypto_proj", "root", "root");
		FileInputStream in= new FileInputStream(file);
		String sql2="Insert into user(name,email,pass,fil)"+"values(?,?,?,?)";		
		PreparedStatement preparedStmt2 = con.prepareStatement(sql2);
		preparedStmt2.setString(1, name);
		preparedStmt2.setString(2, email);
		preparedStmt2.setString(3, pass);	
		preparedStmt2.setBinaryStream(4,in);		
		preparedStmt2.execute();
		con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
//updating the password
	public void updateApass(String test_password, String email) {		 
		try
		{
		Connection con=null;
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/crypto_proj", "root", "root");				
		String update_sql="update user set apass = ? where email = ?";		
		PreparedStatement preparedStmt2 = con.prepareStatement(update_sql);
		preparedStmt2.setString(1, test_password);
		preparedStmt2.setString(2, email);							
		preparedStmt2.execute();
		con.close();				
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	//checking the email to make sure same user is not registering again.
	public boolean checkEmail(String email) {
		boolean isExist=false;
		try
		{
		Connection con=null;
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/crypto_proj", "root", "root");	
		String sql2="Select * from user where email=?";		
		PreparedStatement preparedStmt2 = con.prepareStatement(sql2);
		preparedStmt2.setString(1, email);
		ResultSet rs=preparedStmt2.executeQuery();
		if(rs.next())
			isExist=true;
		con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return isExist;
	}
//checking group is present or not
	public boolean checkGroup(String grp) {
		boolean isExist=false;
		try
		{
		Connection con=null;
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/crypto_proj", "root", "root");	
		String sql2="Select * from group_dtl where group_name=?";
		PreparedStatement preparedStmt2 = con.prepareStatement(sql2);
		preparedStmt2.setString(1, grp);
		ResultSet rs=preparedStmt2.executeQuery();
		if(rs.next())
			isExist=true;
		con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return isExist;
	}
//getting the id
	public int getId(String email) {		
		int id=0;
		try
		{
		Connection con=null;
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/crypto_proj", "root", "root");	
		String sql2="Select id from user where email=?";		
		PreparedStatement preparedStmt2 = con.prepareStatement(sql2);
		preparedStmt2.setString(1, email);
		ResultSet rs=preparedStmt2.executeQuery();
		if(rs.next())
			id=rs.getInt("id");
		con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return id;
	}
//getting the user details
	public ArrayList<String> getUserDetails(int sender) {
		ArrayList<String> st=new ArrayList<String>();
		try
		{
		Connection con=null;
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/crypto_proj", "root", "root");
		String sql2="Select * from user where id=?";		
		PreparedStatement preparedStmt2 = con.prepareStatement(sql2);
		preparedStmt2.setInt(1, sender);
		ResultSet rs=preparedStmt2.executeQuery();
		while(rs.next())
		{
			st.add(rs.getString("name"));
			st.add(rs.getString("email"));
		}
		con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return st;
	}
//inserting the group
	public void insertGroup(String email, String recemail, String group) {
		try
		{
		Connection con=null;
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/crypto_proj", "root", "root");
		String sql2="Insert into group_dtl(user1,user2,group_name,user1_status,user2_status,is_active)"+"values(?,?,?,?,?,?)";		
		PreparedStatement preparedStmt2 = con.prepareStatement(sql2);
		preparedStmt2.setString(1, email);
		preparedStmt2.setString(2, recemail);
		preparedStmt2.setString(3, group);
		preparedStmt2.setString(4,"0");
		preparedStmt2.setString(5,"0");
		preparedStmt2.setString(6,"0");		
		preparedStmt2.execute();
		con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	//checking the email
	public boolean emailCheck(String email)
	{
		String sql;
		boolean isExist=false;
		try
		{
		Connection con=null;
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/crypto_proj", "root", "root");	
		sql="select * from user where email=?";
		PreparedStatement preparedStmt = con.prepareStatement(sql);
		preparedStmt.setString(1, email);		
		ResultSet rs=preparedStmt.executeQuery();
		if(rs.next())
			isExist=true;
		else
			isExist=false;		
		con.close();		
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return isExist;
	}
	//get password
	@SuppressWarnings("resource")
	public void getPassword(String email, String tt) {
		try
		{
		Connection con=null;
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/crypto_proj", "root", "root");
		String sql2="Select fil from user where email=?";		
		PreparedStatement preparedStmt2 = con.prepareStatement(sql2);
		preparedStmt2.setString(1, email);
		OutputStream outputStream = null;
		outputStream = new FileOutputStream(new File("D:/Password"+tt+".jpg"));		
		ResultSet rs=preparedStmt2.executeQuery();
		InputStream in;
		if(rs.next())
		{
			in=rs.getBinaryStream("fil");		
		int read = 0;
		byte[] bytes = new byte[1024];
		while ((read = in.read(bytes)) != -1) {			
			outputStream.write(bytes, 0, read);
		}
		}
		con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}