package general.function;

import android.annotation.SuppressLint;
import java.security.MessageDigest;

public class MD5 {
	@SuppressLint("DefaultLocale") 
	public static final String MD5_Encrypt(final String toEncrypt) {
	    try {
	        final MessageDigest digest = MessageDigest.getInstance("md5");
	        digest.update(toEncrypt.getBytes());
	        final byte[] bytes = digest.digest();
	        final StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < bytes.length; i++) {
	            sb.append(String.format("%02X", bytes[i]));
	        }
	        return sb.toString().toUpperCase();
	    } catch (Exception exc) {
	        return ""; // Impossibru!
	    }
	}
	/**
	 * End Class
	 */
}
