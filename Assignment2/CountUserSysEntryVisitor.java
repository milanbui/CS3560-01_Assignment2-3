package cpp.cs3560.assignment2;

public class CountUserSysEntryVisitor implements SysEntryVisitor {

	@Override
	public double visit(Group group) {

		for(SystemEntry element : group.getGroupsAndUsers()) {
			
			if(element instanceof Group) {
				return 0 + visit((Group)element);
			}
			else {
				return 0 + visit((User)element);
			}
		}
		return 0;
		
	}

	@Override
	public double visit(User user) {
		return (double)1;
	}

}
