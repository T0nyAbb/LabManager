package gui.panels.mainpageframe;

import javax.swing.JPanel;

import control.Controller;
import gui.buttons.RectangleButton;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.EmptyBorder;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ImageIcon;

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
	
	public ProfilePanel(Controller controller) {
		setBackground(Color.WHITE);
		this.controller = controller;
		
		setPanelSettings();
		generateLabels();
		layoutComponents();
	}

	private void setPanelSettings() {
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
	
	private void layoutComponents() {
		
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(headerLabel, GroupLayout.PREFERRED_SIZE, 843, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(32)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addComponent(emailLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(usernameLabel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 799, GroupLayout.PREFERRED_SIZE))
								.addComponent(lineLabel, GroupLayout.PREFERRED_SIZE, 750, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(headerLabel, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addGap(37)
					.addComponent(usernameLabel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(emailLabel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lineLabel, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE)
					.addGap(317))
		);
		setLayout(groupLayout);
	}
}
