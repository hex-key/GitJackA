package git;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Tree {
	public String hash;
	
	public Tree(ArrayList<String> lines) {

		String content = "";
		
		for (String line : lines) {
			content += (line);
			content += "\n";
		}
		
		content = content.strip();	
		
		String contentHash = Commit.encrypt(content);
		this.hash = contentHash;
		
		// write new file with sha1 as the name
		Path np = Paths.get("./objects/" + contentHash);
		try {
			Files.writeString(np, content, StandardCharsets.ISO_8859_1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


