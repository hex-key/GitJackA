import git.*;

/*
commit: 
- part of doubly linked list
- contains pointer to a tree and some other info about the commit

index:
- index file in objects (???) folder
- contains an index of all the files that have been changed (

tree:
contains pointer to one (1) previous tree, as well as blobs (which contain the actual files we're controlling) 

format of tree entry:
<type: "blob" or "tree"> : <SHA1 filename of blob/tree> <original filename if blob, nothing if tree> 

blob: 
represents file being version-controlled - content is content, filename is hash of content

when a commit is made:
- use Index to make blobs of the files you are adding 
- make new Commit -- tree of this commit will be previous tree + newly added blobs (check index file for that)
- write the commit file 
 */

public class GitTester {
	public static void main (String[] args) throws Exception {
		Index git = new Index();
		
		// commit #1
		git.addBlob("file1.txt");
		git.addBlob("file2.txt");
		git.commit("add files 1 and 2", "hexps13", null);
		
		// commit #2 
		
	}
}
