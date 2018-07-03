/* 
	Coded by:
	Shreshth Tuli
*/
	
import java.security.MessageDigest;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.security.spec.X509EncodedKeySpec;
import java.security.KeyFactory;
import java.util.Base64;

public class StringUtil{

	//Applies Sha256 to a string and returns the result. 
	public static String applySha256(String input){		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");	        
			//Applies sha256 to our input, 
			byte[] hash = digest.digest(input.getBytes("UTF-8"));	        
			StringBuffer hexString = new StringBuffer(); // This will contain hash as hexadecimal
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		}
		catch(Exception e) {
		    System.out.println(e.toString());
			return "";
		}
	}

	public static String Sign(String data) throws Exception{
		try{
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
	        SecureRandom secRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
	 
	        keyGen.initialize(1024, secRandom);
	 
	        KeyPair keyPair = keyGen.generateKeyPair();
	        PrivateKey privkey = keyPair.getPrivate();
	        PublicKey pubkey = keyPair.getPublic();
	        Signature dsaSign = Signature.getInstance("SHA1withDSA", "SUN");
	 
	        dsaSign.initSign(privkey);

	        byte[] buffer = data.getBytes(StandardCharsets.UTF_8);
	        dsaSign.update(buffer);

	        byte[] realSign = dsaSign.sign();
	        String sign = new String(Base64.getEncoder().encodeToString(realSign));
	        String publickey = new String(Base64.getEncoder().encodeToString(pubkey.getEncoded())); 
	        String output = publickey + " " + sign;  
	        return(output);
		}
		catch(Exception e){
			System.out.println(e.toString());
			return "";
		}
	}	

	public static boolean Verify(String data, String publickey, String signature) throws Exception{
		byte[] encKey = Base64.getDecoder().decode(publickey);
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
		KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
		PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);

		byte[] sigToVerify = Base64.getDecoder().decode(signature);
		Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
		sig.initVerify(pubKey);

		byte[] originaldata = data.getBytes(StandardCharsets.UTF_8);
		sig.update(originaldata);

		return sig.verify(sigToVerify);
	}

	public static String repeat(int count, String with) {
    	return new String(new char[count]).replace("\0", with);
	}
}