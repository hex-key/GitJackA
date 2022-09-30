// i copy pasted my own Commit in because i was confused what was going on with the doubly linked list 

package git;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	
	public Commit(String pt, String changes, String auth, Commit prev) throws Exception {
		ArrayList<String> al = new ArrayList<String>();
		
		// read index file into 
		BufferedReader br = new BufferedReader(new FileReader(new File("index.txt")));
		while (br.ready()) {
			String line = br.readLine();
			if (line.split(" : ")[0].equals("blob")) {
				al.add(line);
			}
		}
		
		this.tree = new Tree(al); //TODO
		
		this.summary = changes;
		this.author = auth;
		this.date = getDate();
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
		String content = parent.hash + this.summary + this.date + this.author ; 
		String shaHash = encrypt(content);
		this.hash = shaHash;
		PrintWriter pw = new PrintWriter(new FileWriter(new File("objects/" + shaHash)));
		pw.println(tree.hash);
		pw.println(parent == null ? "" : parent.hash);
		pw.println(child == null ? "" : child.hash);
		pw.println(author);
		pw.println(date);
		pw.println(summary);
	}
	
}
