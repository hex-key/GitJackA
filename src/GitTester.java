import git.*;

/*
commit: 
- part of doubly linked list
- contains pointer to a tree and some other info about the commit

index:
- index file in root folder
- contains an index of all the files that have been changed since last commit

tree:
contains pointer to one previous tree, as well as any new blobs

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
		Commit c1 = git.commit("add files 1 and 2", "hexps13", null);
		
		// commit #2 
		git.addBlob("file3.txt");
		Commit c2 = git.commit("add file 3", "hexps13", c1);
		
		// commit #3
		git.addBlob("file4.txt");
		Commit c3 = git.commit("add file 4", "hexps13", c2);
		
		git.addBlob("file5.txt");
		Commit c4 = git.commit("add file 5", "hexps13", c3);
	}
}
