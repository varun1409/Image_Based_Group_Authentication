package cryto.src;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.imageio.ImageIO;
//the class for encryption and decrytpion
public class ImageHiding {
	
	Connection con;
	public static void main(String[] args) throws IOException {
		 		 
	 }	 
	//decrypt function takes file and a string
	public String Decrypt(File var, String text) throws IOException {
		BufferedImage output_image = null;
		//calling tools class where all logics used are kept
		Tools tool = new Tools();	
		//reading the file as buffered image
		output_image = ImageIO.read(var);
		StringBuilder password_image = new StringBuilder();
		//getting the RGB value
		int[] imageRGB1 = output_image.getRGB(0, 0, output_image.getWidth(null), output_image.getHeight(null), null, 0, output_image.getWidth(null));
		//takes the lsb of the ARGB value
		 int read_lsb = 1 << 24 | 1 << 16 | 1 << 8 | 1;
		 for (int k =0;k<text.length()*2 ; k++) {
			 //Taking the first half and the second half a character 
			 String first_half = tool.getLSB1(Integer.toBinaryString(imageRGB1[k] & read_lsb));		
			 //System.out.println(first_half);
			 String second_half = tool.getLSB1(Integer.toBinaryString(imageRGB1[k+1] & read_lsb));
			 //System.out.println(second_half);
			 k=k+1;
			 String check = first_half + second_half;
			 //System.out.println(check);
			 String final_text = "0" + check.substring(1, 4) + "0" + check.substring(5,8);			 
			 //System.out.println(final_text);
			 //coverting from ascii value to character
			 char test = (char)(Integer.parseInt(final_text,2));			 
			 int to_test = (int) test;
			 if ((to_test < 65 || to_test> 90) && (to_test < 97 || to_test> 122)) {
				 //System.out.println("here");
				 final_text = "0" + check.substring(1, 4) + "1" + check.substring(5,8);
				 test = (char)(Integer.parseInt(final_text,2));				 
			 }
			 //And building the string back
			 password_image.append(test);
		 }
		 System.out.println(password_image.toString());
		return password_image.toString();
	}	
//encrypt function
	public File EncryptImage(File input, String password) throws IOException {
		//calling tools again
		Tools tool = new Tools();				
		int encodeBits = 1;			 
		BufferedImage input_image = null;
		input_image = ImageIO.read(input);
		//getting bits of the input string
		StringBuilder secret_text = tool.getBits(password);
		//getting the RGB value
		 int[] imageRGB = input_image.getRGB(0, 0, input_image.getWidth(null), input_image.getHeight(null), null, 0, input_image.getWidth(null));		 	 		 				 		 
		 int encodeByteMask = (int)(Math.pow(2, encodeBits)) - 1 << (8 - encodeBits);		 
		 int decodeByteMask = ~(encodeByteMask >>> (8 - encodeBits)) & 0xFF;		
		 int hostMask = (decodeByteMask << 24) | (decodeByteMask << 16) | (decodeByteMask << 8) | decodeByteMask;		 
		 int pos = 0;
		 //going through the length of the image pixel
		 for (int i = 0; i < imageRGB.length; i++)
		  {
			  //going through the length of the input text
		   if(pos < (secret_text.length())) {
			   int encodeData = tool.inputData(pos,encodeBits,secret_text);
			   pos = pos + (encodeBits * 4);			   
			   imageRGB[i] = (imageRGB[i] & hostMask) | (encodeData & ~hostMask);			   			   
		   }				  		 
		  }		 
		 //setting up the RGB values
		 input_image.setRGB(0, 0, input_image.getWidth(null), input_image.getHeight(null), imageRGB, 0, input_image.getWidth(null));
		 //getting the timestamp to make sure that file name is different all times
		 long t = System.currentTimeMillis();	
		 String tt = String.valueOf(t);
		 File sha = new File("Password"+tt+".jpg");
		 ImageIO.write(input_image, "bmp", sha);	
		 System.out.println(password);		 
		 return sha;		
	}
	//connecting the database
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
	//closing the db connection
	public boolean DBClose()  {
		try {
			con.close();
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}		
	}
}