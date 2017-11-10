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
		
		for(int i = 0; i < message.length(); i++)
		{
			for(int j = 0; j < jumbChar.length(); j++)
			{
				if(message.toCharArray()[i] == allChar.toCharArray()[j])
				{
					encM += jumbChar.toCharArray()[j];
					encM +=	allChar.toCharArray()[r.nextInt(allChar.length())];
				}
			}
		}
		return encM;
	}
	
	//Should be just as easy as calling .decrypt(text); to decrypt
	public static String decrypt(String message)
	{
		String decMH = "";
		String decM = "";
		String allChar = "abcdefghijklmnopqrstuvwxyz1234567890`-=~_+!@#$%^&*()[]\\;',./{}|:\"<>? 	ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String jumbChar = "";

		Random r = new Random(allChar.length());
		//makes the encoded text
		for(int i = 0; i < allChar.length(); i++)
		{
			int ri = r.nextInt(allChar.length());
			jumbChar += allChar.toCharArray()[ri];
		}
		
		//Removes the trash numbers
		for(int i = 0; i < message.length(); i++)
		{
			if(i%2 == 0)
			{
				decMH += message.toCharArray()[i];
			}
		}
		
		for(int i = 0; i < decMH.length(); i++)
		{
			for(int j = 0; j < jumbChar.length(); j++)
			{
				if((""+decMH.toCharArray()[i]).equals( ""+jumbChar.toCharArray()[j]))
				{
					decM += allChar.toCharArray()[j];
				}
			}
		}
		
		return decM;
	}
	
	
	//For testing, remove after finished
	public static void main(String[] args) 
	{
		String b = encrypt("abcdefghijklmnopqrstuvwxyz");
		String c = decrypt(b);
		System.out.println(b);
		System.out.println(c);
	}
}
