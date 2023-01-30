package gui.frames;

import control.Controller;
import gui.panels.BackgroundPanel;
import gui.panels.LoginPanel;
import gui.panels.SignupPanel;

import javax.swing.*;
import java.awt.*;

public class AccessFrame extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Controller controller;
    private LoginPanel loginPanel;
    private SignupPanel signupPanel;
    private BackgroundPanel backgroundPanel;

    public AccessFrame(Controller controller){
    	
        this.controller = controller;

        setFrameSettings();
        generateLoginPanel();
        generateSignupPanel();
        generateBackgroundPanel();
    }
    
    public void showLoginErrorMessage(String msg) {
    	loginPanel.showErrorMessage(msg);
    }
    
    public void clearLoginErrorMessage() {
    	loginPanel.clearErrorMessage();
    }
    
    public void clearLogin() {
    	loginPanel.clear();
    }
    
    public void showSignupErrorMessage(String msg) {
    	signupPanel.showErrorMessage(msg);
    }
    
    public void clearSignupErrorMessage() {
    	signupPanel.clearErrorMessage();
    }
    
    public void showSignupSuccessfulMessage() {
    	signupPanel.showSignupSuccessfulMessage();
    }
    
    public void clearSignupSuccessfulMessage() {
    	signupPanel.clearSignupSuccessfulMessage();
    }
    
    public void clearSignup() {
    	signupPanel.clear();
    }
    
    public void showSignupPanel() {
    	loginPanel.clear();
    	loginPanel.setVisible(false);
    	
        getContentPane().add(signupPanel, BorderLayout.EAST);
        signupPanel.setVisible(true);
    }
    
    public void showLoginPanel() {
    	signupPanel.clear();
        signupPanel.setVisible(false);
        
        getContentPane().add(loginPanel, BorderLayout.EAST);
        loginPanel.setVisible(true);
    }
    
    private void setFrameSettings() {
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700,600);
        setResizable(false);
        setTitle("LabManager");
        Image icon = null;
        try {
        	icon = Toolkit.getDefaultToolkit().getImage(AccessFrame.class.getResource("/icone/labmanager-website-favicon-grey.png"));
        }catch(Exception e) {
        	e.printStackTrace();
        }
        setIconImage(icon);
        getContentPane().setLayout(new BorderLayout(0, 0));
        setLocationRelativeTo(null);
        
    }
    
    private void generateLoginPanel() {
    	loginPanel = new LoginPanel(controller);
        getContentPane().add(loginPanel, BorderLayout.EAST);
    }
    
    private void generateSignupPanel() {
    	signupPanel = new SignupPanel(controller);
    }
    
    private void generateBackgroundPanel() {
    	backgroundPanel = new BackgroundPanel();
    	getContentPane().add(backgroundPanel, BorderLayout.CENTER);
    }

}
