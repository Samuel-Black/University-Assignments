package main;

public class TestCode3_CaesarCipher {
	public static void main(String[] args) {
		//creates new Caesar Cipher object
		CaesarCipherTool caesar = new CaesarCipherTool(args[0], Integer.parseInt(args[1]));
		System.out.println(caesar.cipherValidator());
	}
}

