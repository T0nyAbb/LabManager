package gui.panels.mainpageframe;

import javax.swing.JPanel;

import control.Controller;
import gui.buttons.RectangleButton;
import gui.buttons.UnderlineButton;
import gui.utility.Style;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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
	private JPanel statsPanel;
	private JLabel usernameLabel;
	private JLabel emailLabel;
	private JLabel headerLabel;
	private JLabel lineLabel;
	private JLabel prenLabel;
	private JLabel prenCounterLabel;
	private UnderlineButton deleteAccountButton;
	private RectangleButton changePasswordButton;
	
	public ProfilePanel(Controller controller) {
		this.controller = controller;
		setPanelSettings();
		generatePanels();
		generateLabels();
		generateButtons();
		layoutComponents();
	}

	private void setPanelSettings() {
		setBackground(Style.background_color_01);
	}

	private void generateLabels() {
		usernameLabel = new JLabel();
		String username = "utente";
		if(controller != null && controller.getLoggedUser() != null) {
			username = controller.getLoggedUser().getUsername();
		}
		usernameLabel.setText("<html><font color = \""+
				String.format("#%02x%02x%02x", Style.entered_color_01.getRed(), Style.entered_color_01.getGreen(), Style.entered_color_01.getBlue())+
				"\">Username:</font> \""+username+"\"</html>");
		usernameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		usernameLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 22));
		
		emailLabel = new JLabel();
		String email = "n/a";
		if(controller != null && controller.getLoggedUser() != null) {
			email = controller.getLoggedUser().getEmail();
		}
		emailLabel.setText("<html><font color = \""+
				String.format("#%02x%02x%02x", Style.entered_color_01.getRed(), Style.entered_color_01.getGreen(), Style.entered_color_01.getBlue())+
				"\">Email:</font> \""+email+"\"</html>");
		emailLabel.setHorizontalAlignment(SwingConstants.LEFT);
		emailLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 22));
		
		headerLabel = new JLabel();
		headerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		headerLabel.setForeground(Style.background_color_01);
		headerLabel.setText("Profilo Utente");
		headerLabel.setFont(new Font(Style.font_name_01, Font.ITALIC, 25));
		headerLabel.setBackground(Style.background_color_03);
		headerLabel.setOpaque(true);
		headerLabel.setBorder(new EmptyBorder(10, 25, 10, 25));
		
		lineLabel = new JLabel("");
		lineLabel.setOpaque(true);
		lineLabel.setBackground(Style.background_color_03);
		
		prenLabel = new JLabel("Prenotazioni effettuate:");
		prenLabel.setHorizontalAlignment(SwingConstants.CENTER);
		prenLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 22));
		prenLabel.setForeground(Style.foreground_color_02);
		
		prenCounterLabel = new JLabel("0");
		prenCounterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		prenCounterLabel.setFont(new Font(Style.font_name_01, Font.BOLD, 30));
		prenCounterLabel.setForeground(Style.foreground_color_02);
		
	}
	
	public void showPrenCount() {
		try {
			int prenCount = controller.getPrenotazioneDao().getCountByUtente(controller.getLoggedUser());
			prenCounterLabel.setText(String.valueOf(prenCount));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void generatePanels() {
		statsPanel = new JPanel();
		statsPanel.setBackground(Style.background_color_03);
	}
	
	private void generateButtons() {
		deleteAccountButton = new UnderlineButton();
		deleteAccountButton.setFont(new Font(Style.font_name_01, Font.BOLD, 16));
		deleteAccountButton.setText("Elimina Account");
		deleteAccountButton.setDefaultColor(Style.default_color_02);
		deleteAccountButton.setEnteredColor(Style.entered_color_02);
		deleteAccountButton.setPressedColor(Style.pressed_color_02);
		deleteAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteAccount();
			}
		});
		
		changePasswordButton = new RectangleButton();
		changePasswordButton.setText("Cambia Password");
		changePasswordButton.setDefaultColor(Style.default_color_01);
		changePasswordButton.setEnteredColor(Style.entered_color_01);
		changePasswordButton.setPressedColor(Style.pressed_color_01);
		changePasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePassword();
			}
		});
	}
	
	private void layoutComponents() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(changePasswordButton, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 363, Short.MAX_VALUE)
					.addComponent(deleteAccountButton, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addComponent(headerLabel, GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lineLabel, GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(32)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(usernameLabel, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
								.addComponent(emailLabel, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(statsPanel, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
							.addGap(9)))
					.addGap(23))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(headerLabel, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(49)
							.addComponent(usernameLabel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(emailLabel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(41)
							.addComponent(statsPanel, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)))
					.addGap(54)
					.addComponent(lineLabel, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 144, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(deleteAccountButton, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addComponent(changePasswordButton, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);

		GroupLayout gl_panel = new GroupLayout(statsPanel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(28)
					.addComponent(prenLabel, GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
					.addGap(23))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(108)
					.addComponent(prenCounterLabel, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
					.addGap(106))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(41)
					.addComponent(prenLabel)
					.addGap(32)
					.addComponent(prenCounterLabel)
					.addContainerGap(46, Short.MAX_VALUE))
		);
		statsPanel.setLayout(gl_panel);
		setLayout(groupLayout);
	}
	
	private void deleteAccount() {
		controller.deleteLoggedUserAccount();
	}
	
	private void changePassword() {
		controller.changeLoggedUserPassword();
	}
}
