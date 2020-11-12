package cpp.cs3560.assignment2;

public class CountGroupSysEntryVisitor implements SysEntryVisitor {

	@Override
	public double visit(Group group) {
		int count = 0;
		
		for(SystemEntry element : group.getGroupsAndUsers()) {
			
			if(element instanceof Group) {
				count++;
				return count + visit((Group)element);
			}
		}
		
		return count;	
	}

	@Override
	public double visit(User user) {
		return 0;
	}

}
