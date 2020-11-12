package cpp.cs3560.assignment2;

import java.util.ArrayList;

// user has messages
// has who they follow
public class User implements SystemEntry {
	private String userID;
	private ArrayList<String> currentlyFollowing;
	private ArrayList<String> messages;
	
	public User(String userID) {
		this.userID = userID;
		this.currentlyFollowing = new ArrayList<String>();
		this.messages = new ArrayList<String>();
	}

	@Override
	public String getId() {
		return this.userID;
	}

	public ArrayList<String> getMessages() {
		return messages;
	}


	public ArrayList<String> getCurrentlyFollowing() {
		return currentlyFollowing;
	}


	public void follow(String userId) {
		this.currentlyFollowing.add(userId);
	}
	
	public void postMessage(String message) {
		this.messages.add(message);
	}
	
	@Override
	public double accept(SysEntryVisitor visitor) {
		return visitor.visit(this);
	}
}
