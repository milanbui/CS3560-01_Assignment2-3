package cpp.cs3560.assignment2;

import java.util.ArrayList;

// composite
// Visitor
// groups made up of users and groups
public class Group implements SystemEntry {
	private String groupId;
	private ArrayList<SystemEntry> children;
	
	public Group(String groupId) {
		this.groupId = groupId;
		children  = new ArrayList<SystemEntry>();
	}
	
	@Override
	public String getId() {
		return this.groupId;
		
	}
	
	public ArrayList<SystemEntry> getGroupsAndUsers() {
		return this.children;
	}

	
	
	public void addChild(SystemEntry child) {
		this.children.add(child);
	}

	@Override
	public double accept(SysEntryVisitor visitor) {
		for (SystemEntry element : children) {
			element.accept(visitor);
		}
		
		return visitor.visit(this);
	}
	
	public boolean isExistingID(String id){
		boolean isExisting = false;
		
		if(this.groupId.equals(id)){
			isExisting = true;
		}
		
		for (SystemEntry element : children) {

			if (element.getId().equals(id)) {
				isExisting = true;
			}
			
			if (element instanceof Group){
				isExisting = isExisting || ((Group) element).isExistingID(id);
			}	
		}
		return isExisting;
	}
	
	
	public Group findGroup(String id) {
		Group group = null;
		if (id.equals(groupId)) {
			return this;
		}
		else {
			for(SystemEntry element : children) {
				
				if(element instanceof Group) {
					if(((Group)element).findGroup(id) != null){
						group = ((Group)element).findGroup(id);
					}
				}	
			}
		}
		
		return group;
	}

	public User findUser(String id) {
		User user = null;
		
		for(SystemEntry element : children) {
			
			if(element instanceof Group) {				
				if(((Group)element).findUser(id) != null){
					user = ((Group)element).findUser(id);
				}
			}	
			else if (element.getId().equals(id)) {
				return (User)element;
			}
		}
		
		return user;
	}

}
