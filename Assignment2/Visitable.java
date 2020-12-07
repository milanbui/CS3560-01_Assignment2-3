/****************************************************************************************
 * Name  : Milan Bui
 * Date  : 12 November 2020
 * Class : CS3650.01
 * 
 * Assignment 2
 ****************************************************************************************/
package cpp.cs3560.assignment2;

public interface Visitable {
	public double accept(SysEntryVisitor visitor);
	public boolean accept(SysEntryVisitorCheck visitor);
	public User accept(SysEntryVisitorFind visitor);
}
