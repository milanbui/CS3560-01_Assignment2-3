package cpp.cs3560.assignment2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollBar;
import javax.swing.ScrollPaneConstants;

public class UserView extends JDialog {

	private final JPanel contentPanel;
	private JTextField txtUserId;


	/**
	 * Constructor: Create the User Window.
	 */
	public UserView(User user, Group system) {
		contentPanel = new JPanel();
		setBounds(100, 100, 565, 601);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		
		JLabel lblCurrentlyFollowing = new JLabel("Currently Following:");
		lblCurrentlyFollowing.setFont(new Font("Calibri", Font.BOLD, 15));
		
		/*
		 * List of who user is currently following
		 */
		JList listFollowing = new JList();
		listFollowing.setFont(new Font("Calibri", Font.PLAIN, 15));
		listFollowing.setModel(setFollowListModel(user)); 
		
		
		/*
		 * Text field where user enter user id of who they want to follow
		 */
		txtUserId = new JTextField();
		txtUserId.setText("USER ID");
		txtUserId.setColumns(10);
		
		JButton btnFollowUser = new JButton("Follow User");
		
		
		//Follows user with user id when button is clicked
		btnFollowUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String id = txtUserId.getText();
				
				if (id.equals(user.getId())) {
					JOptionPane.showMessageDialog(null, "CANNOT FOLLOW YOURSELF");
				}
				else {
					User userToFollow = system.findUser(id);

					if(userToFollow == null) {
						JOptionPane.showMessageDialog(null, "USER \"" + id + "\" DOES NOT EXIST");
					}
					else if (user.getCurrentlyFollowing().contains(userToFollow)){
						JOptionPane.showMessageDialog(null, "You are already following " + id);
					}
					else {
						user.follow(userToFollow);
						listFollowing.setModel(setFollowListModel(user)); 
					}
				}
			}
		});
		
		
		JLabel lblNewsFeed = new JLabel("News Feed:");
		lblNewsFeed.setFont(new Font("Calibri", Font.BOLD, 16));
		JScrollPane newsFeedScrollPane = new JScrollPane();

		/*
		 * List of messages posted by who user is following
		 */
		JList listNewsFeed = new JList();
		newsFeedScrollPane.setViewportView(listNewsFeed);
		listNewsFeed.setFont(new Font("Calibri", Font.PLAIN, 15));
		DefaultListModel newsFeedModel = new DefaultListModel();
		listNewsFeed.setModel(newsFeedModel);
		


		/*
		 * Message user wants to tweet
		 */
		JTextArea txtrEnterTweet = new JTextArea();
		txtrEnterTweet.setFont(new Font("Calibri", Font.PLAIN, 16));
		txtrEnterTweet.setText("Share your thoughts...");
		
		// Posts tweet when user clicks button
		JButton btnPostTweet = new JButton("Post Tweet");
		btnPostTweet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String message = txtrEnterTweet.getText();
				user.postMessage(message);
				newsFeedModel.insertElementAt(user.getId() + " : " +  message, 0);
				listNewsFeed.setModel(newsFeedModel);
			}
		});
		
		
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(7)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(newsFeedScrollPane, GroupLayout.PREFERRED_SIZE, 521, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewsFeed)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(txtrEnterTweet, GroupLayout.PREFERRED_SIZE, 399, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnPostTweet))
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblCurrentlyFollowing)
										.addComponent(listFollowing, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(btnFollowUser, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
										.addComponent(txtUserId, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))))
							.addGap(35))))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblCurrentlyFollowing)
					.addGap(1)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(txtUserId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(btnFollowUser))
						.addComponent(listFollowing, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(txtrEnterTweet, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPostTweet))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewsFeed)
					.addGap(1)
					.addComponent(newsFeedScrollPane, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
	}
	
	private DefaultListModel setFollowListModel(User user) {
		DefaultListModel model = new DefaultListModel();
		
		for (User id : user.getCurrentlyFollowing()) {
			model.addElement(id.getId());
		}
		
		return model;
	}
}
