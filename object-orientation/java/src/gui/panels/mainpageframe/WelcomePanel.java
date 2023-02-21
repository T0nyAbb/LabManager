package gui.panels.mainpageframe;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import control.Controller;
import gui.utility.Style;

import java.awt.Font;

public class WelcomePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JLabel backgroundLabel;
	private JLabel welcomeLabel;
	private JLabel nameLabel;
	
	public WelcomePanel(Controller controller) {
		this.controller = controller;
		
        setPanelSettings();
        generateLabels();
	}
	
	private void setPanelSettings() {
		setOpaque(true);
		setBackground(Style.background_color_03);
		setLayout(null);
	}
	
	private void generateLabels() {
		
		backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, 900, 591);
        try {
        	backgroundLabel.setIcon(new ImageIcon(WelcomePanel.class.getResource("/background/lab01.jpeg")));
        }catch(Exception e) {
        	e.printStackTrace();
        }
        add(backgroundLabel);
        
        
        nameLabel = new JLabel();
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        String username = "utente";
		if(controller != null && controller.getLoggedUser() != null) {
			username = controller.getLoggedUser().getUsername();
		}
		nameLabel.setText(username);
		nameLabel.setBounds(340, 210, 340, 240);
		nameLabel.setForeground(Style.background_color_01);
        int font = 50;
        if(username.length() > 10) font = font - (username.length() + username.length()/2);
        nameLabel.setFont(new Font(Style.font_name_01, Font.BOLD, font));
        nameLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        nameLabel.setVerticalTextPosition(JLabel.BOTTOM);
        add(nameLabel);
        
        welcomeLabel = new JLabel();
        try {
        	welcomeLabel.setIcon(new ImageIcon(WelcomePanel.class.getResource("/icone/labmanager-website-favicon-blue.png")));
        }catch(Exception e) {
        	e.printStackTrace();
        }
        welcomeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        welcomeLabel.setIconTextGap(30);
		welcomeLabel.setText("Benvenuto,");
        welcomeLabel.setBounds(182, 155, 718, 295);
        welcomeLabel.setForeground(Style.background_color_01);
        welcomeLabel.setFont(new Font(Style.font_name_01, Font.BOLD, 50));
        welcomeLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        welcomeLabel.setVerticalTextPosition(JLabel.TOP);
        add(welcomeLabel);
        
        setComponentZOrder(backgroundLabel, 1);
        setComponentZOrder(welcomeLabel, 0);
        setComponentZOrder(welcomeLabel, 0);
	}
}
