/****************************************************************************************
 * Name  : Milan Bui
 * Date  : 12 November 2020
 * Class : CS3650.01
 * 
 * Assignment 2
 ****************************************************************************************/
package cpp.cs3560.assignment2;

public class CountMsgSysEntryVisitor implements SysEntryVisitor {

	// Calcs total # of messages
	@Override
	public double visit(Group group) {
		double count = 0;
		
		for(SystemEntry element : group.getGroupsAndUsers()) {
			
			if(element instanceof User) {
				count = count + visit((User)element);
			}
			else {
				count = count + visit((Group)element);
			}
		}
		return count;		
	}

	@Override
	public double visit(User user) {
		int count = user.getMessages().size();
		return (double)count;
	}

}
