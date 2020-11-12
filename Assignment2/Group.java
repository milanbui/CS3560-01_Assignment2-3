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
		
		if(this.groupId.equals(id)){
			return true;
		}
		
		for (SystemEntry element : children) {

			if (element.getId().equals(id)) {
				return true;
			}
			
			if (element instanceof Group){
				return ((Group) element).isExistingID(id);

			}	
		}
		
		return false;
	}
	
	public Group findGroup(String id) {

		if (id.equals(groupId)) {
			return this;
		}
		else {
		
			for(SystemEntry element : children) {
				
	
				if(element instanceof Group) {

					if(element.getId().equals(id)) {
						return (Group)element;
					}
					
					return ((Group)element).findGroup(id);
				}	
			}
		}

		return null;
	}

	public User findUser(String id) {

		for(SystemEntry element : children) {
			
			if(element instanceof Group) {				
				return ((Group)element).findUser(id);
			}	
			else if (element.getId().equals(id)) {
				return (User)element;
			}
		}
		
		return null;
	}

}
