package git;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Tree {
	
	HashMap<String, String> files = new HashMap<String, String>();
	public String hash;
	
	public Tree(ArrayList<String> pairs) {
		
//		for (String pair : pairs) {
//			
//			int colon = pair.indexOf(':');
//			
//			String key = pair.substring(0, colon);
//			
//			String value = pair.substring(colon + 2);
//			
//			files.put(key, value);
//			
//		}
		
		String content = "";
		
		for (String pair : pairs) {
			
			int colon = pair.indexOf(':');
			
			content += pair.substring(0, colon) + " : " + pair.substring(colon+2) + "something.txt" + "\n";
			
		}
		
		String contentHash = Commit.encrypt(content);
		this.hash = contentHash;
		
		// write new file with sha1 as the name
		Path np = Paths.get(".\\objects\\" + contentHash);
		try {
			Files.writeString(np, content, StandardCharsets.ISO_8859_1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}


