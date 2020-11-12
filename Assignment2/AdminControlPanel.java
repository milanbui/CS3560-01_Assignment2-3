package cpp.cs3560.assignment2;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class AdminControlPanel extends JFrame {

	private static AdminControlPanel frame;
	private JPanel contentPane;
	private JTextField txtUserId;
	private JTextField txtGroupId;
	
	private Group groupsAndUsers;
	
	
	/**
	 * Constructor: Create the frame.
	 */
	private AdminControlPanel() {
		setTitle("Control Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 760, 580);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		

		
		/*
		 * TREE DISPLAY OF GROUPS AND USERS
		 */
		groupsAndUsers = new Group("Root");
		JTree treeGroupsUsers = new JTree();
		treeGroupsUsers.setModel(new DefaultTreeModel(new DefaultMutableTreeNode(groupsAndUsers.getId())));
		
		// FORMATTING
		treeGroupsUsers.setCellRenderer(new DefaultTreeCellRenderer() {

			@Override
			public Component getTreeCellRendererComponent(JTree tree, Object value, 
					boolean selected, boolean expanded, boolean isLeaf, int row, 
					boolean focused) {
				super.getTreeCellRendererComponent(treeGroupsUsers, value, selected, 
						expanded, isLeaf, row, focused);
				
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
				
				// No icon next to node name
				setIcon(null);
				
				// BOLD if group, PLAIN if user.
				if(node.getAllowsChildren()) {
					setFont(new Font("Calibri", Font.BOLD, 14));
				}
				else
				{
					setFont(new Font("Calibri", Font.PLAIN, 14));
				}

				return this;
			}
		});

		expandAllNodes(treeGroupsUsers, 0, treeGroupsUsers.getRowCount());
		
		
		/*
		 * TEXT FIELD FOR USER ID - FOR ID OF USER BEING ADDED
		 */
		txtUserId = new JTextField();
		txtUserId.setFont(new Font("Calibri", Font.PLAIN, 16));
		txtUserId.setForeground(Color.BLACK);
		txtUserId.setText("USER ID");
		txtUserId.setColumns(10);
		
		/*
		 * BUTTON TO ADD USER
		 */
		JButton btnAddUser = new JButton("Add User");
		btnAddUser.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		// When button is clicked, adds user.
		// TODO - storing the data as actual User type
		btnAddUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String userID = txtUserId.getText();
				
				// Gets group that was selected to add user into
				if (treeGroupsUsers.getSelectionPath() == null) {
					JOptionPane.showMessageDialog(null, "Please select a group to add the user to.");
				}
				else {
					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)
							treeGroupsUsers.getSelectionPath().getLastPathComponent();
					
	
					if (!selectedNode.getAllowsChildren()) {
						JOptionPane.showMessageDialog(null, "Cannot add a user to a user! Please select a group.");
					}
					else if (groupsAndUsers.isExistingID(userID)) {
							JOptionPane.showMessageDialog(null, "ID ALREADY EXISTS!");	
					}
					else {
						User newUser = new User(userID);
						Group parentNode = groupsAndUsers.findGroup(selectedNode.toString());
						if (parentNode == null) {
							JOptionPane.showMessageDialog(null, "PARENT NODE NOT FOUND");
						}
						else {
							parentNode.addChild(newUser);
							
							// Creates the user and adds under selected group. allowsChildren = false
							DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(userID, false);
							selectedNode.add(newNode);
							
							// Reloads tree display to show updated version with new user
							((DefaultTreeModel)treeGroupsUsers.getModel()).reload();
							expandAllNodes(treeGroupsUsers, 0, treeGroupsUsers.getRowCount());
						}
					}
				}
			}
		});
		
		
		/*
		 * TEXT FIELD FOR USER ID - FOR ID OF GROUP BEING ADDED
		 */
		txtGroupId = new JTextField();
		txtGroupId.setFont(new Font("Calibri", Font.PLAIN, 16));
		txtGroupId.setForeground(Color.BLACK);
		txtGroupId.setText("GROUP ID");
		txtGroupId.setColumns(10);
		
		/*
		 * BUTTON TO ADD USER
		 */
		JButton btnAddGroup = new JButton("Add Group");
		btnAddGroup.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		// When button is clicked, adds group
		// TODO - storing the data as actual Group type
		btnAddGroup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String groupID = txtGroupId.getText();
				

				if (treeGroupsUsers.getSelectionPath() == null) {
					JOptionPane.showMessageDialog(null, "Please select a group to add the group to.");
				}
				else {
					// Gets group that was selected to add user into
					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)
							treeGroupsUsers.getSelectionPath().getLastPathComponent();
					
					if (!selectedNode.getAllowsChildren()) {
						JOptionPane.showMessageDialog(null, "Cannot add a group to a user! Please select a group.");
					}

					else if(groupsAndUsers.isExistingID(groupID)){
						    JOptionPane.showMessageDialog(null, "ID ALREADY EXISTS!");
					}
					else {
						Group newGroup = new Group(groupID);
						Group parentNode = groupsAndUsers.findGroup(selectedNode.toString());
						
						if (parentNode == null) {
							JOptionPane.showMessageDialog(null, "PARENT NODE NOT FOUND");
						}
						else {
							parentNode.addChild(newGroup);
							
							// Creates the group and adds group under selected group
							DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(groupID);
							selectedNode.add(newNode);
							
							// Reloads tree display to show updated version with new user
							((DefaultTreeModel)treeGroupsUsers.getModel()).reload();
							expandAllNodes(treeGroupsUsers, 0, treeGroupsUsers.getRowCount());
						}
					}
				}
			}
		});
		
		
		
		JButton btnOpenUserView = new JButton("Open User View");
		btnOpenUserView.setFont(new Font("Calibri", Font.PLAIN, 16));
		btnOpenUserView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)
						treeGroupsUsers.getLastSelectedPathComponent();
				
				if ( node != null && !node.getAllowsChildren()) {
					String userId = node.toString();
					User user = groupsAndUsers.findUser(userId);
					
					UserView temp = new UserView(user, groupsAndUsers);
					temp.setTitle(userId);
					temp.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(null, "Please select a USER to view.");
				}
			}
		});

		
		
		
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		
		JButton btnUserTotal = new JButton("Show User Total");
		btnUserTotal.setFont(new Font("Calibri", Font.PLAIN, 16)); 
		
		btnUserTotal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CountUserSysEntryVisitor countUser = new CountUserSysEntryVisitor();
				int userTotal = (int)(groupsAndUsers.accept(countUser));
				String message = String.valueOf(userTotal) + " TOTAL USERS";
				textPane.setText(message);
			}
		});
		
		
		
		
		
		JButton btnShowGroupTotal = new JButton("Show Group Total");
		btnShowGroupTotal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {	
				CountGroupSysEntryVisitor groupCalc = new CountGroupSysEntryVisitor();
				int groupTotal = (int)(groupsAndUsers.accept(groupCalc));
				String message = String.valueOf(groupTotal) + " TOTAL GROUPS";
				textPane.setText(message);
			}
		});
		btnShowGroupTotal.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		
		
		
		JButton btnShowMessageTotal = new JButton("Show Message Total");
		btnShowMessageTotal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {	
				CountMsgSysEntryVisitor msgTotal = new CountMsgSysEntryVisitor();
				int messageTotal = (int)(groupsAndUsers.accept(msgTotal));
				String message = String.valueOf(messageTotal) + " TOTAL MESSAGES";
				textPane.setText(message);
			}
		});
		btnShowMessageTotal.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		
		
		JButton btnShowPositivePercentage = new JButton("Show Positive Percentage");
		btnShowPositivePercentage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {			
				PositiveSysEntryVisitor positiveCalc = new PositiveSysEntryVisitor();
				double positivePerc = groupsAndUsers.accept(positiveCalc);
				String message = String.valueOf(positivePerc) + "% POSITIVE";
				textPane.setText(message);
			}
		});
		btnShowPositivePercentage.setFont(new Font("Calibri", Font.PLAIN, 16));
	
		
		
		/*
		 * LAYOUT OF GUI
		 */
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(treeGroupsUsers, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(txtGroupId, GroupLayout.PREFERRED_SIZE, 288, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtUserId, GroupLayout.PREFERRED_SIZE, 288, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnOpenUserView, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnAddGroup, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnAddUser, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
							.addGap(10))
						.addComponent(textPane, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 408, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnUserTotal, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
								.addComponent(btnShowMessageTotal, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnShowPositivePercentage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnShowGroupTotal, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(treeGroupsUsers, GroupLayout.PREFERRED_SIZE, 514, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtUserId, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnAddUser))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtGroupId, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnAddGroup))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnOpenUserView)
							.addGap(18)
							.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnUserTotal)
								.addComponent(btnShowGroupTotal))
							.addGap(1)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnShowPositivePercentage)
								.addComponent(btnShowMessageTotal))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	
	public static AdminControlPanel getFrame() {
		if(frame == null) {
			synchronized(AdminControlPanel.class) {
				if(frame == null) {
					frame = new AdminControlPanel();
				}
			}
		}
		return frame;
	}
	
	
	// method
	private void expandAllNodes(JTree tree, int startingIndex, int rowCount){
		for(int i = startingIndex; i < rowCount; ++i){
			tree.expandRow(i);
		}

		if(tree.getRowCount() != rowCount){
			expandAllNodes(tree, rowCount, tree.getRowCount());
		}
	}

	


}
