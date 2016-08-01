package cryto.src;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tools {
	public int inputData(int position, int encodebitsequi, StringBuilder text) {
		
		int instead_encodeRGB = 0 ;
		int to_return_4 = 0, array_pos = 0;
		int[] array_4 = new int[4];
		 
		while(position <= text.length()-encodebitsequi && to_return_4 < 4*encodebitsequi){
			 String required = text.substring(position, position + encodebitsequi);
			 position = position + encodebitsequi;
			 to_return_4 = to_return_4 + encodebitsequi;
			 array_4[array_pos] = Integer.parseInt(required,2);
			 array_pos++;
		}
		instead_encodeRGB = array_4[0] << 24 | array_4[1] << 16 | array_4[2] << 8 | array_4[3];	 
		return instead_encodeRGB;		
	}
	
	public StringBuilder getBits(String input) {
		StringBuilder output = new StringBuilder();
		
		byte[] text_binary = input.getBytes();
		 for(int i =0; i < text_binary.length;i++) {
			 String temp = Integer.toBinaryString(text_binary[i]);
			 String temp1 = String.format("%8s", temp).replace(' ','0');
			 output.append(temp1);
		 }		
		return output;
	}
	
	public BufferedImage readImage(String Location) {
		BufferedImage image = null;
		
		try
		  {
			  image = ImageIO.read(new File(Location));
		  }
		  catch (IOException ioe) { ioe.printStackTrace(); }
		
		  return image;
	}
	public String getLSB1(String input) {
		StringBuilder output = new StringBuilder();		
		output.append(input.substring(0, 1));
		output.append(input.substring(8, 9));
		output.append(input.substring(16, 17));
		output.append(input.substring(24, 25));
		return output.toString();
	}
	public boolean check_password(String actual, String image) {
		
		return true;
	}
	
	public String convert_password(String input) {
		String original = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String got = "abcdefghabcdefgpqrstuvwpqrABCDEFGHABCDEFGPQRSTUVWPQR9:;<=>?898";		
		char[] original_c = original.toCharArray();
		char[] got_c = got.toCharArray();
		char[] input_c = input.toCharArray();
		StringBuilder to_check = new StringBuilder();
		for(int i =0; i<input_c.length;i++) {
			int pos = 0;
			for(int j = 0;j<original_c.length;j++) {
				if (input_c[i] == original_c[j]) {
					pos = j;
				}
			}
			to_check.append(got_c[pos]);
		}
		return to_check.toString();
	}
}
