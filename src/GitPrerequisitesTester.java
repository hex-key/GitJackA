import java.io.IOException;

import git.Blob;
import git.Index;
public class GitPrerequisitesTester{

	public static void main(String[] args) throws IOException{
		Blob bob = new Blob ("Input.txt");//makes the blob
		System.out.print(bob.getHash());//returns a hash
		
		Index i = new Index ();
		i.initialize();
		i.addBlob ("Input.txt");
		i.addBlob("chucky.txt");
		i.addBlob("Chuckilicious.txt");
		i.removeBlob("Input.txt");
		i.removeBlob("Chuckilicious.txt");//removes chuckilicious
	}

}
