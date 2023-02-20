package gui.panels.mainpageframe;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import control.Controller;
import gui.buttons.UnderlineButton;
import gui.utility.Style;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionEvent;

public class SidebarPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private UnderlineButton welcomeButton;
	private UnderlineButton profileButton;
	private UnderlineButton makeReservationButton;
	private UnderlineButton handleReservationButton;
	private UnderlineButton logoutButton;
	private UnderlineButton statsButton;
	
	public SidebarPanel(Controller controller) {
		this.controller = controller;
		
        setPanelSettings();
        generateButtons();
        setLayoutComponents();
	}
	
	private void setPanelSettings() {
		setOpaque(true);
		setBackground(Style.entered_color_01);
	}
	
	private void generateButtons() {
		welcomeButton = new UnderlineButton();
		welcomeButton.setText("</u>Home<u>");
		welcomeButton.setHorizontalAlignment(SwingConstants.LEFT);
		welcomeButton.setIcon(new ImageIcon(SidebarPanel.class.getResource("/icone/labmanager-website-favicon-white.png")));
		welcomeButton.setFont(new Font(Style.font_name_01, Font.BOLD, 32));
		welcomeButton.setDefaultColor(Style.background_color_01);
		welcomeButton.setEnteredColor(Style.background_color_01);
		welcomeButton.setPressedColor(Style.background_color_01);
		welcomeButton.setBackground(Style.default_color_01);
		welcomeButton.setOpaque(true);
		welcomeButton.setHorizontalTextPosition(SwingConstants.RIGHT);
		welcomeButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.showWelcome();
        	}
        });
        
		profileButton = new UnderlineButton();
		profileButton.setIcon(new ImageIcon(SidebarPanel.class.getResource("/icone/dot2.png")));
		profileButton.setText("Profilo");
		profileButton.setPressedColor(Style.background_color_01);
		profileButton.setHorizontalAlignment(SwingConstants.LEFT);
		profileButton.setFont(new Font("Century Gothic", Font.BOLD, 22));
		profileButton.setEnteredColor(Style.background_color_01);
		profileButton.setDefaultColor(Style.background_color_01);
		profileButton.setBorder(new EmptyBorder(0, 15, 0, 15));
		profileButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.showProfile();
        	}
        });
		
        makeReservationButton = new UnderlineButton();
        makeReservationButton.setIcon(new ImageIcon(SidebarPanel.class.getResource("/icone/dot2.png")));
        makeReservationButton.setText("Effettua prenotazione");
        makeReservationButton.setEnteredColor(Style.background_color_01);
        makeReservationButton.setDefaultColor(Style.background_color_01);
        makeReservationButton.setPressedColor(Style.background_color_01);
        makeReservationButton.setHorizontalAlignment(SwingConstants.LEFT);
        makeReservationButton.setFont(new Font("Century Gothic", Font.BOLD, 22));
        makeReservationButton.setBorder(new EmptyBorder(0, 15, 0, 15));
        makeReservationButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.showMakeReservationStrumento();
        	}
        });
        
        handleReservationButton = new UnderlineButton();
        handleReservationButton.setIcon(new ImageIcon(SidebarPanel.class.getResource("/icone/dot2.png")));
        handleReservationButton.setText("Gestisci prenotazioni");
        handleReservationButton.setPressedColor(Style.background_color_01);
        handleReservationButton.setHorizontalAlignment(SwingConstants.LEFT);
        handleReservationButton.setFont(new Font("Century Gothic", Font.BOLD, 22));
        handleReservationButton.setEnteredColor(Style.background_color_01);
        handleReservationButton.setDefaultColor(Style.background_color_01);
        handleReservationButton.setBorder(new EmptyBorder(0, 15, 0, 15));
        handleReservationButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.showHandleReservation();
        	}
        });
        
        logoutButton = new UnderlineButton();
        logoutButton.setIcon(new ImageIcon(SidebarPanel.class.getResource("/icone/arrow.png")));
        logoutButton.setText("Esci");
        logoutButton.setPressedColor(Style.background_color_01);
        logoutButton.setHorizontalAlignment(SwingConstants.LEFT);
        logoutButton.setFont(new Font(Style.font_name_01, Font.BOLD, 28));
        logoutButton.setEnteredColor(Style.background_color_01);
        logoutButton.setDefaultColor(Style.background_color_01);
        logoutButton.setBorder(new EmptyBorder(0, 15, 0, 15));
        logoutButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		logout();
        	}
        });
		statsButton = new UnderlineButton();
		statsButton.setIcon(new ImageIcon(SidebarPanel.class.getResource("/icone/dot2.png")));
		statsButton.setFont(new Font(Style.font_name_01, Font.BOLD, 22));
		statsButton.setHorizontalAlignment(SwingConstants.LEFT);
		statsButton.setText("Statistiche");
		statsButton.setDefaultColor(Style.background_color_01);
		statsButton.setEnteredColor(Style.background_color_01);
		statsButton.setPressedColor(Style.background_color_01);
		statsButton.setBorder(new EmptyBorder(0, 15, 0, 15));
		statsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showStats();
			}
		});
	}
	
	private void setLayoutComponents() {
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(logoutButton, GroupLayout.PREFERRED_SIZE, 368, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(161, Short.MAX_VALUE))
				.addComponent(statsButton, GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
				.addComponent(handleReservationButton, GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
				.addComponent(makeReservationButton, GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
				.addComponent(profileButton, GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
				.addComponent(welcomeButton, GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(welcomeButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(profileButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(makeReservationButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(handleReservationButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(statsButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
					.addComponent(logoutButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
		);
        setLayout(groupLayout);
	}
	
	private void logout() {
		controller.logoutMainpageFrame();
	}
}
