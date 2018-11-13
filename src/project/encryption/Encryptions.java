package project.encryption;

public class Encryptions {
	
	public static String caesar(String text, int schluessel) {
		int zeichen;
		String rtn = "";
		byte[] geheim = text.getBytes();
		for(int i = 0; i < geheim.length; i++) {
			if((geheim[i] > 31) && (geheim[i] < 127)) {
				zeichen = geheim[i] - 32;
				geheim[i] = (byte)(((zeichen + schluessel) % 95) + 32);
			}
			rtn += (char)(geheim[i]);
		}
		return rtn;
	}
	
}
