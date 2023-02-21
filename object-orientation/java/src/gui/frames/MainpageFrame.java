package gui.frames;

import java.awt.*;
import java.sql.SQLException;

import javax.swing.JFrame;
import control.Controller;
import gui.panels.mainpageframe.*;

public class MainpageFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private SidebarPanel sidebarPanel;
	private WelcomePanel welcomePanel;
	private ProfilePanel profilePanel;
	private MakeReservationStrumentoPanel makeReservationStrumentoPanel;
	private MakeReservationDatePanel makeReservationDatePanel;
	private HandleReservationPanel handleReservationPanel;
	private StrumentoStatsPanel strumentoStatsPanel;

	public MainpageFrame(Controller controller) throws SQLException {
		this.controller = controller;
		
		setFrameSettings();
		generateSidebarPanel();
		generateWelcomePanel();
		generateProfilePanel();
		generateMakeReservationStrumentoPanel();
		generateMakeReservationDatePanel();
		generateHandleReservationPanel();
		generateStatsPanel();
	}


	public void showProfilePanel() {
		welcomePanel.setVisible(false);
		makeReservationStrumentoPanel.setVisible(false);
        makeReservationDatePanel.setVisible(false);
		handleReservationPanel.setVisible(false);
		strumentoStatsPanel.setVisible(false);
		profilePanel.showPrenCount();
        profilePanel.setVisible(true);
        getContentPane().add(profilePanel, BorderLayout.CENTER);
	}

	public void showMakeReservationStrumentoPanel() {
		welcomePanel.setVisible(false);
		profilePanel.setVisible(false);
		handleReservationPanel.setVisible(false);
        strumentoStatsPanel.setVisible(false);
        makeReservationDatePanel.setVisible(false);
        makeReservationStrumentoPanel.setVisible(true);
        getContentPane().add(makeReservationStrumentoPanel, BorderLayout.CENTER);
	}


	public void showMakeReservationDatePanel(int idStrumento) {
		welcomePanel.setVisible(false);
		profilePanel.setVisible(false);
		handleReservationPanel.setVisible(false);
        strumentoStatsPanel.setVisible(false);
        makeReservationStrumentoPanel.setVisible(false);
        makeReservationDatePanel.setIdStrumento(idStrumento);
        makeReservationDatePanel.setVisible(true);
        getContentPane().add(makeReservationDatePanel, BorderLayout.CENTER);
	}

	public void showHandleReservationPanel() {
		welcomePanel.setVisible(false);
		makeReservationStrumentoPanel.setVisible(false);
        makeReservationDatePanel.setVisible(false);
		profilePanel.setVisible(false);
        strumentoStatsPanel.setVisible(false);
		handleReservationPanel.loadListContent();
        handleReservationPanel.setVisible(true);
		getContentPane().add(handleReservationPanel, BorderLayout.CENTER);
	}
	
	public void showWelcomePanel() {
		handleReservationPanel.setVisible(false);
		makeReservationStrumentoPanel.setVisible(false);
        makeReservationDatePanel.setVisible(false);
		profilePanel.setVisible(false);
        strumentoStatsPanel.setVisible(false);
		welcomePanel.setVisible(true);
        getContentPane().add(welcomePanel, BorderLayout.CENTER);
	}
	
	public void showStatsPanel() {
		handleReservationPanel.setVisible(false);
		makeReservationStrumentoPanel.setVisible(false);
        makeReservationDatePanel.setVisible(false);
		profilePanel.setVisible(false);
		welcomePanel.setVisible(false);
		strumentoStatsPanel.loadStats();
		strumentoStatsPanel.setVisible(true);
		getContentPane().add(strumentoStatsPanel, BorderLayout.CENTER);
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

	public MakeReservationStrumentoPanel getMakeReservationStrumentoPanel() {
		return makeReservationStrumentoPanel;
	}
	
	public MakeReservationDatePanel getMakeReservationDatePanel() {
		return makeReservationDatePanel;
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
	
	private void generateMakeReservationStrumentoPanel() throws SQLException {
		makeReservationStrumentoPanel = new MakeReservationStrumentoPanel(controller);
	}
	
	private void generateMakeReservationDatePanel() throws SQLException {
		makeReservationDatePanel = new MakeReservationDatePanel(controller);
	}
	
	private void generateHandleReservationPanel() {
		handleReservationPanel = new HandleReservationPanel(controller);
	}

	private void generateStatsPanel() throws SQLException {
		strumentoStatsPanel = new StrumentoStatsPanel(controller);
	}


}

