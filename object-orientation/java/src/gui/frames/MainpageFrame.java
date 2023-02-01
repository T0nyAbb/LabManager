package gui.frames;

import java.awt.*;
import java.sql.SQLException;

import javax.swing.JFrame;
import control.Controller;
import gui.panels.mainpageframe.*;
import gui.utility.DatePicker;

public class MainpageFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private SidebarPanel sidebarPanel;
	private WelcomePanel welcomePanel;
	private ProfilePanel profilePanel;
	private MakeReservationPanel makeReservationPanel;
	private HandleReservationPanel handleReservationPanel;

	
	public MainpageFrame(Controller controller) throws SQLException {
		this.controller = controller;
		
		setFrameSettings();
		generateSidebarPanel();
		generateWelcomePanel();
		generateProfilePanel();
		generateMakeReservationPanel();
		generateHandleReservationPanel();
	}

	public void showProfilePanel() {
		welcomePanel.setVisible(false);
		makeReservationPanel.setVisible(false);
		handleReservationPanel.setVisible(false);
        profilePanel.setVisible(true);
        getContentPane().add(profilePanel, BorderLayout.CENTER);
	}

	public void showMakeReservationPanel() {
		welcomePanel.setVisible(false);
		profilePanel.setVisible(false);
		handleReservationPanel.setVisible(false);
        makeReservationPanel.setVisible(true);
        getContentPane().add(makeReservationPanel, BorderLayout.CENTER);
	}

	public void showHandleReservationPanel() {
		welcomePanel.setVisible(false);
		makeReservationPanel.setVisible(false);
		profilePanel.setVisible(false);
        handleReservationPanel.setVisible(true);
        getContentPane().add(handleReservationPanel, BorderLayout.CENTER);
	}
	
	public void showWelcomePanel() {
		handleReservationPanel.setVisible(false);
		makeReservationPanel.setVisible(false);
		profilePanel.setVisible(false);
        welcomePanel.setVisible(true);
        getContentPane().add(welcomePanel, BorderLayout.CENTER);
	}
	
	public SidebarPanel getSidebarPanel() {
		return sidebarPanel;
	}

	public WelcomePanel getWelcomePanel() {
		return welcomePanel;
	}

	public ProfilePanel getProfilePanel() {
		return profilePanel;
	}

	public MakeReservationPanel getMakeReservationPanel() {
		return makeReservationPanel;
	}

	public HandleReservationPanel getHandleReservationPanel() {
		return handleReservationPanel;
	}

	private void setFrameSettings() {
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	setSize(1150, 630);
        setResizable(false);
        setTitle("LabManager");
        Image icon = null;
        try {
        	icon = Toolkit.getDefaultToolkit().getImage(AccessFrame.class.getResource("/icone/labmanager-website-favicon-blue.png"));
        }catch(Exception e) {
        	e.printStackTrace();
        }
        setIconImage(icon);
        getContentPane().setLayout(new BorderLayout(0, 0));
        setLocationRelativeTo(null);
        
    }
	
	private void generateSidebarPanel() {
		sidebarPanel = new SidebarPanel(controller);
        sidebarPanel.setPreferredSize(new Dimension(300, 600));
        getContentPane().add(sidebarPanel, BorderLayout.WEST);
	}
	
	private void generateWelcomePanel() {
		welcomePanel = new WelcomePanel(controller);
        getContentPane().add(welcomePanel, BorderLayout.CENTER);
	}
	
	private void generateProfilePanel() {
		profilePanel = new ProfilePanel(controller);
	}
	
	private void generateMakeReservationPanel() throws SQLException {
		makeReservationPanel = new MakeReservationPanel(controller);
	}
	
	private void generateHandleReservationPanel() {
		handleReservationPanel = new HandleReservationPanel(controller);
	}
}
