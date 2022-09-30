package git;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
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
	    Files.writeString(path, "\n" + filename + " : " + hash, StandardCharsets.ISO_8859_1);//writes an entry in the index file
		
		index.put(filename,hash);//adds the information to the hashmap
	}
	
	public void removeBlob (String filename) throws IOException{
		File toDelete = new File ("./objects/" + index.get(filename));//gets the path to the file
		toDelete.delete();//deletes the file
		index.remove(filename);//removes the file from the hashmap

		
		File toDelete2 = new File ("index.txt");//gets the path to delete the index file
		toDelete2.delete();//deletes the index file
		
		Path p = Paths.get("index.txt");//gets the path to the final file
	    try {
	        Files.writeString(p, "", StandardCharsets.ISO_8859_1); //starts the new index file
	    } catch (IOException e) {
	        e.printStackTrace();
	    }	
	    
	    for (String key : index.keySet()) {
	    	Files.writeString(p, "\n" + key + " : " + index.get(key), StandardCharsets.ISO_8859_1);//writes all of the non deleted entries
	    }
	}
}
