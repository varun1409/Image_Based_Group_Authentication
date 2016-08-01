package cryto.src;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
/**
 * Servlet implementation class mailservlet
 */
public class mailservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public mailservlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
			//registration page
			//getting the uploaded file
			File uploadedFiles = saveUploadedFiles(request);
			//getting the parameters
			String name=request.getParameter("name");
			String email=request.getParameter("email");
			String pass=request.getParameter("pass");
			ImageHiding imh = new ImageHiding();
			//encrypting the file
			uploadedFiles = imh.EncryptImage(uploadedFiles, pass);
			//connecting the database
			dao dao=new dao();
			//checking email id is already registered or not
			boolean isExist=dao.emailCheck(email);
			if(!isExist)
			{
			dao.insertFile(name,email,pass,uploadedFiles);		
			Tools tool = new Tools();			
			String test_password = tool.convert_password(pass);
			//System.out.println(test_password);
			dao.updateApass(test_password,email);
			//Part file2 = request.getPart("file");	
			//sending the email
			String msg="Thank you for registering to the site you would get the image with which you can sign in again.";
			SendEmail.sendEmail("smtp.gmail.com", "587", "cryptoproject2016@gmail.com", "crypto123", email , "--------Registration Successfull---------",msg,uploadedFiles);
			request.setAttribute("zc1", "Registration Succssfull");
			request.getRequestDispatcher("Login.jsp").forward(request, response);
			}
			else {
				String msg="User already registered.";
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("sendmail.jsp").forward(request, response);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}	
//for file processing
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
