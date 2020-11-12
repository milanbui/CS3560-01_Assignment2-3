/****************************************************************************************
 * Name  : Milan Bui
 * Date  : 12 November 2020
 * Class : CS3650.01
 * 
 * Assignment 2
 ****************************************************************************************/
package cpp.cs3560.assignment2;

public class CountUserSysEntryVisitor implements SysEntryVisitor {
	// Calc total # of users
	@Override
	public double visit(Group group) {
		double count = 0;
		for(SystemEntry element : group.getGroupsAndUsers()) {
			
			if(element instanceof Group) {
				count = count + visit((Group)element);
			}
			else {		
				count =  count + visit((User)element);
			}
		}
		
		return count;
		
	}

	@Override
	public double visit(User user) {
		return (double)1;
	}

}
