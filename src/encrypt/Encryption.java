package encrypt;

import java.util.Random;

/*
 *Finished with the Encryption. To use just create a new object Encryption and call .encrypt(message) or .decrypt(message)
 * 
 * */
public class Encryption 
{
	public static String allChar = "abcdefghijklmnopqrstuvwxyz1234567890`-=~_+!@#$%^&*()[]\\;',./{}|:\"<>? 	ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public String encrypt(String message)
	{
		String encM = "";
		String jumbChar = "";

		Random r = new Random(allChar.length());
		jumbChar = jumbleText(allChar, r);
		
		
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
	public String decrypt(String message)
	{
		String decMH = "";
		String decM = "";
		String jumbChar = "";

		Random r = new Random(allChar.length());
		//makes the encoded text
		jumbChar = jumbleText(allChar, r);
		
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
	
	private String jumbleText(String allChar, Random r)
	{
		String jumbChar = "";
		String allCharT = allChar;
				
		for(int i = 0; i < allChar.length(); i++)
		{
			int ri = r.nextInt(allCharT.length());
			jumbChar += allCharT.toCharArray()[ri];
			String temp = "";
			for(int j = 0; j < allCharT.length(); j++)
			{
				if(j != ri)
				{
					temp += allCharT.charAt(j);
				}
			}
			allCharT = temp;
		}
		
		return jumbChar;
	}
	
}
