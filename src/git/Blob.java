package git;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Blob {
	private String hash;
	private String name;
	public Blob (String fileName) throws IOException {
		name = fileName;
		hash = createHash ();
		writeFile();//makes the copy of the initial file with the hash function as the name
	}
	
	private String createHash () throws IOException{
		Path path = Paths.get(name); //gets the path for the initial file
		return encryptThisString(Files.readString(path)); //gets the SHA-1 hash
	}
	
	private void writeFile () throws IOException{
		Path p = Paths.get("./objects/" + hash);//gets the path to the final file
		Path path = Paths.get(name);//gets the path to original file
		String s = Files.readString(path);//gets the text of the original file 
	    try {
	        Files.writeString(p, s, StandardCharsets.ISO_8859_1); //writes string from input file at "C:\Users\jacka\eclipse-workspace\GitPrerequisites\XXXX.txt"
	    } catch (IOException e) {
	        e.printStackTrace();
	    }	
	}
	
	public String getHash () {
		return hash;
	}
	
	public static String encryptThisString(String input) {
		try {
			// getInstance() method is called with algorithm SHA-1
			MessageDigest md = MessageDigest.getInstance("SHA-1");

			// digest() method is called
			// to calculate message digest of the input string
			// returned as array of byte
			byte[] messageDigest = md.digest(input.getBytes());

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);

			// Add preceding 0s to make it 32 bit
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}

			// return the HashText
			return hashtext;
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
