package gui.panels.accessframe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import javax.swing.SwingConstants;

import control.Controller;
import gui.buttons.RectangleButton;
import gui.buttons.UnderlineButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginPanel extends JPanel {

	private Controller controller;
	private JLabel iconLabel;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JLabel errorLabel;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private RectangleButton loginButton;
    private UnderlineButton signupButton;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginPanel(Controller controller) {
		this.controller = controller;
		
		setPanelSettings();
		generateLabels();
		generateTextFields();
		generateLoginButton();
		generateSignupButton();
		clear();
	}
	
	
	public void showErrorMessage(String msg) {
		errorLabel.setText(msg);
		errorLabel.setVisible(true);
	}
	
	public void clearErrorMessage() {
		errorLabel.setVisible(false);
	}
	
	public void clear() {
		usernameTextField.setText("");
		passwordTextField.setText("");
		clearErrorMessage();
	}
	
	
	private void setPanelSettings() {
		setOpaque(true);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(450, 600));
        setLayout(null);
	}
	
	private void generateLabels() {
		iconLabel = new JLabel();
        iconLabel.setIcon(new ImageIcon(LoginPanel.class.getResource("/icone/logo-login.png")));
        iconLabel.setBounds(30, 80, 389, 63);
        add(iconLabel);
        
        usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Century Gothic", Font.BOLD, 18));
        usernameLabel.setBounds(130, 249, 177, 20);
        add(usernameLabel);
        
        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Century Gothic", Font.BOLD, 18));
        passwordLabel.setBounds(130, 309, 177, 20);
        add(passwordLabel);
        
        errorLabel = new JLabel();
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(0, 196, 440, 31);
        add(errorLabel);
	}
	
	private void generateTextFields() {
		usernameTextField = new JTextField();
        usernameTextField.setBounds(130, 270, 179, 20);
        usernameTextField.setColumns(10);
        usernameTextField.addKeyListener(new KeyAdapter() {
        	public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    login();
                }

            }
        });
        add(usernameTextField);
        
        passwordTextField = new JPasswordField();
        passwordTextField.setBounds(130, 330, 179, 20);
        passwordTextField.setColumns(10);
        passwordTextField.addKeyListener(new KeyAdapter() {
        	public void keyPressed(KeyEvent e) { 
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    login();
                }
            }
        });
        add(passwordTextField);
        
        
	}
	
	private void generateLoginButton() {
		loginButton = new RectangleButton();
		loginButton.setFont(new Font("Century Gothic", Font.BOLD, 18));
		loginButton.setText("Accedi");
		loginButton.setDefaultColor(new Color(0, 40, 83));
		loginButton.setEnteredColor(new Color(0, 68, 140));
		loginButton.setPressedColor(new Color(90, 120, 200));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBounds(145, 410, 150, 37);
        loginButton.addActionListener(new ActionListener() { 
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		login();
        	}
        });
        add(loginButton);
        
	}
	
	private void generateSignupButton() {
        signupButton = new UnderlineButton();
        signupButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        signupButton.setText("Non hai un account? Registrati.");
        signupButton.setDefaultColor(new Color(0, 40, 83));
        signupButton.setEnteredColor(new Color(0, 68, 140));
        signupButton.setPressedColor(new Color(90, 120, 200));
        signupButton.setBounds(45, 489, 350, 31);
        signupButton.addActionListener(new ActionListener() { 
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		signup();
        	}
        });
        add(signupButton);
        
	}
	
	private void login() {
		String username = usernameTextField.getText();
		String password = String.valueOf(passwordTextField.getPassword());
		controller.loginAccessFrame(username, password);
	}
	
	private void signup() {
		controller.showSignup();
	}
	
}