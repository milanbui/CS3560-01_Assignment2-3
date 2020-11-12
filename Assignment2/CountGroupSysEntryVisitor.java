package cpp.cs3560.assignment2;

public class CountGroupSysEntryVisitor implements SysEntryVisitor {

	@Override
	public double visit(Group group) {
		double count;
		if(group.getId().equals("Root")) {
			count = 0;
		}
		else {
			count = 1;
		}
		
		for(SystemEntry element : group.getGroupsAndUsers()) {
			
			if(element instanceof Group) {
				count = count + visit((Group)element);
			}
			else {
				count = count + visit((User)element);
			}
		}
		
		return count;	
	}

	@Override
	public double visit(User user) {
		return 0;
	}

}
