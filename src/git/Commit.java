// i copy pasted my own Commit in because i was confused what was going on with the doubly linked list 

package git;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Commit {
	private Commit parent = null;
	private Commit child = null;
	
	private Tree tree;
	
	private String summary;
	private String author;
	private String date;
	
	public String hash;
	
	public Commit(String changes, String auth, Commit prev) throws Exception {
		ArrayList<String> al = new ArrayList<String>();
		
		// read index file into an ArrayList -- for newly added Blobs
		BufferedReader indexReader = new BufferedReader(new FileReader(new File("index.txt")));
		while (indexReader.ready()) {
			String line = indexReader.readLine();
			if (line.contains(" : ")) {
				al.add(line);
			}
		}
		indexReader.close();
		
		// read tree file into the ArrayList as well -- for existing Blobs
		BufferedReader treeReader = new BufferedReader(new FileReader(new File("objects/"+prev.tree.hash)));
		while (treeReader.ready()) {
			String line = treeReader.readLine();
			if (line.contains(" : ")) {
				al.add(line);
			}
		}
		treeReader.close();
		
		// create tree
		this.tree = new Tree(al);
		
		// clear index file (from: https://www.techiedelight.com/delete-content-of-file-in-java/)
		BufferedWriter bw = Files.newBufferedWriter(Path.of("index.txt"), StandardOpenOption.TRUNCATE_EXISTING);
		bw.close();
		
		// assign other instance variables
		this.summary = changes;
		this.author = auth;
		this.date = getDate();
		
		// maintain doubly linked list
		prev.setChild(this);
	}
	
	public void setChild(Commit next) {
		this.child = next;
	}
	
	public static String encrypt(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger num = new BigInteger(1, messageDigest);
			String hex = num.toString(16);
			while (hex.length() < 32) {
				hex = "0" + hex;
			}
			return hex;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getDate() {
		return LocalDate.now().toString();
	}
	
	public void writeFile() throws Exception {
		// make and store hash
		String content = parent.hash + this.summary + this.date + this.author; 
		String shaHash = encrypt(content);
		this.hash = shaHash;
		
		// write file
		PrintWriter pw = new PrintWriter(new FileWriter(new File("objects/" + shaHash)));
		pw.println(tree.hash);
		pw.println(parent == null ? "" : parent.hash);
		pw.println(child == null ? "" : child.hash);
		pw.println(author);
		pw.println(date);
		pw.println(summary);
	}
	
}
