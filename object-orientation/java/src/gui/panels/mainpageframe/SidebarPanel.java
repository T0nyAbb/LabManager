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
		profileButton = new UnderlineButton();
		profileButton.setIcon(new ImageIcon(SidebarPanel.class.getResource("/icone/dot2.png")));
		profileButton.setFont(new Font(Style.font_name_01, Font.BOLD, 28));
		profileButton.setHorizontalAlignment(SwingConstants.LEFT);
		profileButton.setText("Profilo");
		profileButton.setDefaultColor(Style.background_color_01);
		profileButton.setEnteredColor(Style.background_color_01);
		profileButton.setPressedColor(Style.background_color_01);
		profileButton.setBackground(Style.default_color_01);
		profileButton.setVerticalAlignment(SwingConstants.TOP);
		profileButton.setOpaque(true);
		profileButton.setBorder(new EmptyBorder(15, 15, 15, 15));
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
        makeReservationButton.setFont(new Font(Style.font_name_01, Font.BOLD, 20));
        makeReservationButton.setBorder(new EmptyBorder(0, 15, 0, 15));
        makeReservationButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		controller.showMakeReservation();
        	}
        });
        
        handleReservationButton = new UnderlineButton();
        handleReservationButton.setIcon(new ImageIcon(SidebarPanel.class.getResource("/icone/dot2.png")));
        handleReservationButton.setText("Gestisci prenotazioni");
        handleReservationButton.setPressedColor(Style.background_color_01);
        handleReservationButton.setHorizontalAlignment(SwingConstants.LEFT);
        handleReservationButton.setFont(new Font(Style.font_name_01, Font.BOLD, 20));
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
		statsButton.setFont(new Font(Style.font_name_01, Font.BOLD, 20));
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
        		.addComponent(profileButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addComponent(makeReservationButton, GroupLayout.PREFERRED_SIZE, 368, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        		.addGroup(groupLayout.createSequentialGroup()
        			.addComponent(handleReservationButton, GroupLayout.PREFERRED_SIZE, 368, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(statsButton, GroupLayout.PREFERRED_SIZE, 368, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
        		.addGroup(groupLayout.createSequentialGroup()
        			.addComponent(logoutButton, GroupLayout.PREFERRED_SIZE, 368, GroupLayout.PREFERRED_SIZE)
						.addContainerGap())

        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addComponent(profileButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(makeReservationButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(handleReservationButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(statsButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 172, Short.MAX_VALUE)
        			.addComponent(logoutButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
        );
        setLayout(groupLayout);
	}
	
	private void logout() {
		controller.logoutMainpageFrame();
	}
}
