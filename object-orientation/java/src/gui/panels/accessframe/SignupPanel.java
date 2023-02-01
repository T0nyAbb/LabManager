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

public class SignupPanel extends JPanel {

	private Controller controller;
	private JLabel iconLabel;
	private JLabel usernameLabel;
	private JLabel emailLabel;
	private JLabel passwordLabel;
	private JLabel repeatPasswordLabel;
	private JLabel errorLabel;
	private JLabel signupSuccessfulLabel;
    private JTextField usernameTextField;
    private JTextField emailTextField;
    private JPasswordField passwordTextField;
    private JPasswordField repeatPasswordTextField;
    private RectangleButton signupButton;
    private UnderlineButton loginButton;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SignupPanel(Controller controller) {
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
	
	public void showSignupSuccessfulMessage() {
		signupSuccessfulLabel.setText("Registazione completata!");
		signupSuccessfulLabel.setVisible(true);
	}
	
	public void clearSignupSuccessfulMessage() {
		signupSuccessfulLabel.setVisible(false);
	}
	
	public void clear() {
		usernameTextField.setText("");
		passwordTextField.setText("");
		emailTextField.setText("");
		repeatPasswordTextField.setText("");
		clearErrorMessage();
		clearSignupSuccessfulMessage();
	}
	
	private void setPanelSettings() {
		setOpaque(true);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(450, 600));
        setLayout(null);
	}
	
	private void generateLabels() {

		iconLabel = new JLabel();
		iconLabel.setIcon(new ImageIcon(SignupPanel.class.getResource("/icone/logo-signup.png")));
        iconLabel.setBounds(70, 25, 300, 63);
        add(iconLabel);
        
		emailLabel = new JLabel("E-mail");
        emailLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        emailLabel.setBounds(132, 146, 177, 14);
        add(emailLabel);
		
        usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        usernameLabel.setBounds(132, 206, 177, 14);
        add(usernameLabel);
        
        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        passwordLabel.setBounds(132, 266, 177, 14);
        add(passwordLabel);
        
        repeatPasswordLabel = new JLabel("Conferma Password");
        repeatPasswordLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        repeatPasswordLabel.setBounds(132, 326, 177, 14);
        add(repeatPasswordLabel);
        
        errorLabel = new JLabel();
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(0, 104, 440, 31);
        add(errorLabel);
        
        signupSuccessfulLabel = new JLabel();
        signupSuccessfulLabel.setHorizontalAlignment(SwingConstants.CENTER);
        signupSuccessfulLabel.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        signupSuccessfulLabel.setForeground(new Color(60, 179, 113));
        signupSuccessfulLabel.setBounds(0, 104, 440, 31);
        add(signupSuccessfulLabel);
	}
	
	private void generateTextFields() {
		
		emailTextField = new JTextField();
        emailTextField.setBounds(130, 166, 179, 20);
        emailTextField.setColumns(10);
        emailTextField.addKeyListener(new KeyAdapter() {
        	public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    signup();
                }

            }
        });
        add(emailTextField);
		
		usernameTextField = new JTextField();
        usernameTextField.setBounds(130, 226, 179, 20);
        usernameTextField.setColumns(10);
        usernameTextField.addKeyListener(new KeyAdapter() {
        	public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    signup();
                }

            }
        });
        add(usernameTextField);
        
        passwordTextField = new JPasswordField();
        passwordTextField.setBounds(130, 286, 179, 20);
        passwordTextField.setColumns(10);
        passwordTextField.addKeyListener(new KeyAdapter() {
        	public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    signup();
                }
            }
        });
        add(passwordTextField);
        
        repeatPasswordTextField = new JPasswordField();
        repeatPasswordTextField.setBounds(130, 346, 179, 20);
        repeatPasswordTextField.setColumns(10);
        repeatPasswordTextField.addKeyListener(new KeyAdapter() {
        	public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    signup();
                }

            }
        });
        add(repeatPasswordTextField);
        
	}
	
	private void generateSignupButton() {
		signupButton = new RectangleButton();
		signupButton.setFont(new Font("Century Gothic", Font.BOLD, 18));
		signupButton.setText("Registrati");
		signupButton.setDefaultColor(new Color(0, 40, 83));
		signupButton.setEnteredColor(new Color(0, 68, 140));
		signupButton.setPressedColor(new Color(90, 120, 200));
		signupButton.setForeground(Color.WHITE);
		signupButton.setBounds(145, 410, 150, 37);
        signupButton.addActionListener(new ActionListener() { 
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		signup();
        	}
        });
        add(signupButton);
        
	}
	
	private void generateLoginButton() {
        loginButton = new UnderlineButton();
        loginButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        loginButton.setText("Sei gia' registrato? Accedi.");
		loginButton.setDefaultColor(new Color(0, 40, 83));
		loginButton.setEnteredColor(new Color(0, 68, 140));
		loginButton.setPressedColor(new Color(90, 120, 200));
        loginButton.setBounds(45, 489, 350, 31);
        loginButton.addActionListener(new ActionListener() { 
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		login();
        	}
        });
        add(loginButton);
        
	}
	
	private void login() {
		controller.showLogin();
	}
	
	private void signup() {
		String email = emailTextField.getText();
		String username = usernameTextField.getText();
		String password = String.valueOf(passwordTextField.getPassword());
		String repeatPassword = String.valueOf(repeatPasswordTextField.getPassword());
		controller.signupAccessFrame(email, username, password, repeatPassword);
	}
}