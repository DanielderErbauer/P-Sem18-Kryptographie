package project.encryption;


public class Caesar {



	public static String encrypt(String input, int key) {
		String enc = "";
		for (int i = 0; i < input.length(); i++){
			char ch = (char)(input.codePointAt(i) + key);
			enc += ch;
		}
		return enc;
	}
	public static String decrypt(String input, int key) {
		String dec = "";
		for (int i = 0; i < input.length(); i++){
			char ch = (char)(input.codePointAt(i) - key);
			dec += ch;
		}
		return dec;
	}




	}

