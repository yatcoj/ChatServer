package encrypt;

import java.util.Random;

/*
 * To Do
 * Encrypt messages
 * 
 * Decrypt messages
 * 
 * */
public class Encryption 
{
	public static String encrypt(String message)
	{
		String encM = "";
		String allChar = "abcdefghijklmnopqrstuvwxyz1234567890`-=~_+!@#$%^&*()[]\\;',./{}|:\"<>? 	ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String jumbChar = "";

		Random r = new Random(allChar.length());
				
		for(int i = 0; i < allChar.length(); i++)
		{
			int ri = r.nextInt(allChar.length());
			jumbChar += allChar.toCharArray()[ri];
		}
		
		for(message)
					
		return encM;
	}
	
	//Should be just as easy as calling .decrypt(text); to decrypt
	public static String decrypt(String message)
	{
		String decM = "";
		
		
		return decM;
	}
	
	
	//For testing, remove after finished
	public static void main(String[] args) 
	{
		String b = encrypt("a");
	}
}
