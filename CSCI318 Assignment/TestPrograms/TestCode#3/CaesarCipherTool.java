package main;

public class CaesarCipher {

	private String text = "";
	private int s = 0;
	
	public CaesarCipher(String original, int shift) {
    	text = original;
    	s = shift;
    }

	public String toString(){
		return text + " " + s;
	}
	
    /**
     * Encrypts a message using a Caesar Cipher with a given shift. Assumes the
     * String only contains alphabetic characters defined in the standard 
     * ASCII table from 65-90 and 97-122 (A-Z and a-z)
     *
     * @param original The original String to encrypt
     * @param shift    An int containing the shift to use for the cipher
     * @return         A String containing the encrypted message
     */
    private String encrypt(String original, int shift) {
        String encrypted = "";
        String uppercase = original.toUpperCase();
        for (int i = 0; i < uppercase.length(); i++) {
            int c = uppercase.charAt(i);
            if(Character.isAlphabetic(c)) {
            	c = (int) (c + shift + 'A') % 26 +  'A';
            }
            
            encrypted += (char) c;
        }
                
        return encrypted;
    }

    /**
     * Decrypts a message that was encoded with a Caesar Cipher. Assumes the
     * String encypted only used standard ASCII characters of values 65-90 and 97-122
     * (A-Z and a-z), ignoring its case
     *
     * @param encrypted The encrypted String to decrypt
     * @param shift     An int containing the shift that was use to encrypt the
     *                  original String
     * @return          A String containing the decrypted message
     */
    private String decrypt(String encrypted, int shift) {
        return encrypt(encrypted, -shift);
    }
    
    public boolean cipherValidator() {
    	boolean validated = false;
    	
    	String encrypted = encrypt(text, s);
    	String decrypted = decrypt(text, s);
    	
    	System.out.println("Encrypted string: \"" + encrypted + "\"");
    	System.out.println("Decrypted string: \"" + decrypted + "\"");
    	
    	if(encrypted.equalsIgnoreCase(decrypted)) {
    		return true;
    	}else {
    		throw new IllegalArgumentException("Wrong encryption algorithm. Please check your math again!");
    	}
    }
}