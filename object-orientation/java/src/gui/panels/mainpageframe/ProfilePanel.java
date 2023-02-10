package gui.panels.mainpageframe;

import javax.swing.JPanel;

import control.Controller;
import gui.buttons.RectangleButton;
import gui.buttons.UnderlineButton;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class ProfilePanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JLabel usernameLabel;
	private JLabel emailLabel;
	private JLabel headerLabel;
	private JLabel lineLabel;
	private UnderlineButton deleteAccountButton;
	private RectangleButton changePasswordButton;
	
	public ProfilePanel(Controller controller) {
		this.controller = controller;
		
		setPanelSettings();
		generateLabels();
		generateButtons();
		layoutComponents();
	}

	private void setPanelSettings() {
		setBackground(Color.WHITE);
	}

	private void generateLabels() {
		
		usernameLabel = new JLabel();
		String username = "utente";
		if(controller != null && controller.getLoggedUser() != null) {
			username = controller.getLoggedUser().getUsername();
		}
		usernameLabel.setText("<html><font color = \"#00448C\">Username:</font> \""+username+"\"</html>");
		usernameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		usernameLabel.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		
		emailLabel = new JLabel();
		String email = "n/a";
		if(controller != null && controller.getLoggedUser() != null) {
			email = controller.getLoggedUser().getEmail();
		}
		emailLabel.setText("<html><font color = \"#00448C\">Email:</font> \""+email+"\"</html>");
		emailLabel.setHorizontalAlignment(SwingConstants.LEFT);
		emailLabel.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		
		headerLabel = new JLabel();
		headerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		headerLabel.setForeground(Color.WHITE);
		headerLabel.setText("Profilo Utente");
		headerLabel.setFont(new Font("Century Gothic", Font.ITALIC, 25));
		headerLabel.setBackground(Color.GRAY);
		headerLabel.setOpaque(true);
		headerLabel.setBorder(new EmptyBorder(10, 25, 10, 25));
		
		lineLabel = new JLabel("");
		lineLabel.setOpaque(true);
		lineLabel.setBackground(Color.LIGHT_GRAY);
		
	}
	
	private void generateButtons() {
		deleteAccountButton = new UnderlineButton();
		deleteAccountButton.setFont(new Font("Century Gothic", Font.BOLD, 16));
		deleteAccountButton.setText("Elimina Account");
		deleteAccountButton.setDefaultColor(Color.GRAY);
		deleteAccountButton.setEnteredColor(new Color(180, 0, 0));
		deleteAccountButton.setPressedColor(new Color(220, 0, 0));
		deleteAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteAccount();
			}
		});
		
		changePasswordButton = new RectangleButton();
		changePasswordButton.setText("Cambia Password");
		changePasswordButton.setDefaultColor(new Color(0, 40, 83));
		changePasswordButton.setEnteredColor(new Color(0, 68, 140));
		changePasswordButton.setPressedColor(new Color(90, 120, 200));
		changePasswordButton.setForeground(Color.WHITE);
		changePasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePassword();
			}
		});
	}
	
	private void layoutComponents() {
		
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(32)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addComponent(emailLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(usernameLabel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 799, GroupLayout.PREFERRED_SIZE))
								.addComponent(changePasswordButton, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE)
								.addComponent(lineLabel, GroupLayout.PREFERRED_SIZE, 750, GroupLayout.PREFERRED_SIZE)))
						.addComponent(headerLabel, GroupLayout.PREFERRED_SIZE, 839, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(615, Short.MAX_VALUE)
					.addComponent(deleteAccountButton, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(headerLabel, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addGap(37)
					.addComponent(usernameLabel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(emailLabel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addGap(33)
					.addComponent(changePasswordButton, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addGap(33)
					.addComponent(lineLabel, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE)
					.addGap(210)
					.addComponent(deleteAccountButton, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		setLayout(groupLayout);
	}
	
	private void deleteAccount() {
		controller.deleteLoggedUserAccount();
	}
	
	private void changePassword() {
		
	}
}
