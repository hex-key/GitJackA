
public class Commit {
	private Commit parent;
	private Commit child;
	private String pTree;
	private String summary;//cap at 150 chars
	private String author;
	private String date;
	public Commit (String summaryL, String authorL, String dateL, Commit parent) {
		
	}
}
