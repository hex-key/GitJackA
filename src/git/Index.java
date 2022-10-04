package git;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
public class Index {
	private HashMap <String, String> index = new HashMap <String, String>();
	
	public Index () { //constructor
		
	}
	
	public void initialize () {
		File theDir = new File("./objects"); //makes the directory
		if (!theDir.exists()){
		    theDir.mkdirs();//makes the directory
		}
		Path p = Paths.get("index.txt");//gets the path to the file
	    try {
	        Files.writeString(p, "", StandardCharsets.ISO_8859_1); //makes the index file
	    } catch (IOException e) {
	        e.printStackTrace();
	    }	
	}
	
	public void addBlob (String filename) throws IOException{
		Blob b = new Blob (filename);//makes a blob
		String hash = b.getHash();// gets the hash
		
		Path path = Paths.get("index.txt");//gets the path to the index file
	    Files.writeString(path, "blob : " + hash + " " + filename + "\n", StandardCharsets.ISO_8859_1, StandardOpenOption.APPEND);//writes an entry in the index file
		
		index.put(filename, hash);//adds the information to the hashmap
	}
	
	public void removeBlob (String filename) throws IOException{
		// delete the actual blob object
		File toDelete = new File ("./objects/" + index.get(filename));//gets the path to the file
		toDelete.delete();//deletes the file

		// delete the line from the index by copying index to a new file except the line to be deleted
		File oldIndex = new File("index.txt");
		oldIndex.renameTo(new File("oldIndex.txt"));
		BufferedReader br = new BufferedReader(new FileReader(oldIndex));
		
		File newIndex = new File("index.txt");
		PrintWriter pw = new PrintWriter(newIndex);
		
		String hashToRemove = index.get(filename);
		
		while(br.ready()) {
			String line = br.readLine();
			if (line.contains(" : ") && !line.contains(hashToRemove)) { // if the line is a valid entry and is not the one to remove
				pw.println(line);
			}
		}
		
		br.close();
		pw.close();
		
		oldIndex.delete();
		
		index.remove(filename);//removes the file from the hashmap
	}
	
	public Commit commit (String changes, String auth, Commit prev) throws Exception {
		return new Commit(changes, auth, prev);
	}
}
