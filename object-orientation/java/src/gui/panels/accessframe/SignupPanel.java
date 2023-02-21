package gui.panels.accessframe;

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
import gui.utility.Style;

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
		errorLabel.setText("");
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
        setBackground(Style.background_color_01);
        setPreferredSize(new Dimension(450, 600));
        setLayout(null);
	}
	
	private void generateLabels() {

		iconLabel = new JLabel();
		try {
			iconLabel.setIcon(new ImageIcon(SignupPanel.class.getResource("/icone/logo-signup.png")));
		}catch(Exception e) {
			e.printStackTrace();
		}
        iconLabel.setBounds(70, 25, 300, 63);
        add(iconLabel);
        
		emailLabel = new JLabel("E-mail");
        emailLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 14));
        emailLabel.setBounds(132, 146, 177, 14);
        add(emailLabel);
		
        usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 14));
        usernameLabel.setBounds(132, 206, 177, 14);
        add(usernameLabel);
        
        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 14));
        passwordLabel.setBounds(132, 266, 177, 14);
        add(passwordLabel);
        
        repeatPasswordLabel = new JLabel("Conferma Password");
        repeatPasswordLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 14));
        repeatPasswordLabel.setBounds(132, 326, 177, 14);
        add(repeatPasswordLabel);
        
        errorLabel = new JLabel();
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 12));
        errorLabel.setForeground(Style.foreground_color_error);
        errorLabel.setBounds(0, 104, 440, 31);
        add(errorLabel);
        
        signupSuccessfulLabel = new JLabel();
        signupSuccessfulLabel.setHorizontalAlignment(SwingConstants.CENTER);
        signupSuccessfulLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 12));
        signupSuccessfulLabel.setForeground(Style.foreground_color_success);
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
		signupButton.setFont(new Font(Style.font_name_01, Font.BOLD, 18));
		signupButton.setText("Registrati");
		signupButton.setDefaultColor(Style.default_color_01);
		signupButton.setEnteredColor(Style.entered_color_01);
		signupButton.setPressedColor(Style.pressed_color_01);
		signupButton.setForeground(Style.foreground_color_02);
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
        loginButton.setFont(new Font(Style.font_name_01, Font.BOLD, 14));
        loginButton.setText("Sei gia' registrato? Accedi.");
		loginButton.setDefaultColor(Style.default_color_01);
		loginButton.setEnteredColor(Style.entered_color_01);
		loginButton.setPressedColor(Style.pressed_color_01);
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