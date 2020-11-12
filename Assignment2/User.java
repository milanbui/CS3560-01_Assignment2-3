package cpp.cs3560.assignment2;

import java.util.ArrayList;

// user has messages
// has who they follow
public class User extends Subject implements SystemEntry, Observer {
	private String userID;
	private ArrayList<User> currentlyFollowing;
	private ArrayList<String> messages;
	
	public User(String userID) {
		this.userID = userID;
		this.currentlyFollowing = new ArrayList<User>();
		this.messages = new ArrayList<String>();
	}

	@Override
	public String getId() {
		return this.userID;
	}

	public ArrayList<String> getMessages() {
		return messages;
	}


	public ArrayList<User> getCurrentlyFollowing() {
		return currentlyFollowing;
	}


	public void follow(User user) {
		this.currentlyFollowing.add(user);
		user.attach(this);
	}
	
	public void postMessage(String message) {
		this.messages.add(message);
		notifyObservers();
	}
	
	@Override
	public double accept(SysEntryVisitor visitor) {
		return visitor.visit(this);
	}

	@Override
	public void update(Subject updatedUser) {
		int index = ((User)updatedUser).getMessages().size() - 1;
		
		System.out.println(((User)updatedUser).getId() + " : " + ((User)updatedUser).getMessages().get(index));
		
	}
}
