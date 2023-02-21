package gui.utility;

import javax.swing.*;

import exceptions.EmptyFieldException;
import exceptions.PasswordsNotMatchingException;
import exceptions.SimilarPasswordException;
import gui.buttons.RectangleButton;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ChangePasswordDialog extends JDialog {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPasswordField oldPasswordField;
	private JPasswordField newPasswordField;
	private JPasswordField newRepeatPasswordField;
	private JLabel oldPasswordLabel;
	private JLabel newPasswordLabel;
	private JLabel newRepeatPasswordLabel;
	private JButton okButton;
	private JButton cancelButton;
	private JLabel errorLabel;
	private JLabel logoLabel;
	private String oldPassword;
	private String newPassword;

	public ChangePasswordDialog(JFrame jframe) {
		super(jframe);
		setDialogSettings();
		generateLabels();
		generateTextFields();
		generateButtons();
		clearErrorMessage();
		setLocationRelativeTo(jframe);
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}
	
	public void showErrorMessage(String msg) {
		errorLabel.setText("<html>"+msg+"</html>");
	}
	
	public void clearErrorMessage() {
		errorLabel.setText("");
	}
	
	private void setDialogSettings() {
		setModal (true);
		setAlwaysOnTop (true);
		setModalityType (ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 365, 445);
		setTitle("Cambia Password");
		setResizable(false);
		getContentPane().setBackground(Style.background_color_01);
		getContentPane().setLayout(null);
	}
	
	private void generateLabels() {
		logoLabel = new JLabel("");
		logoLabel.setBounds(40,30,256,64);
		try {
			ImageIcon icon  = new ImageIcon(ChangePasswordDialog.class.getResource("/icone/logo-black-256x256.png"));
			logoLabel.setIcon(icon);
		}catch(Exception e) {
			e.printStackTrace();
		}
		getContentPane().add(logoLabel);

		oldPasswordLabel = new JLabel("Vecchia Password");
		oldPasswordLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 14));
		oldPasswordLabel.setBounds(70, 129, 201, 14);
		getContentPane().add(oldPasswordLabel);
		
		newPasswordLabel = new JLabel("Nuova Password");
		newPasswordLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 14));
		newPasswordLabel.setBounds(70, 216, 201, 14);
		getContentPane().add(newPasswordLabel);
		
		newRepeatPasswordLabel = new JLabel("Ripeti Password");
		newRepeatPasswordLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 14));
		newRepeatPasswordLabel.setBounds(70, 271, 201, 14);
		getContentPane().add(newRepeatPasswordLabel);
		
		errorLabel = new JLabel("ERROR");
		errorLabel.setBounds(40, 315, 271, 56);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 12));
        errorLabel.setForeground(Color.RED);
		getContentPane().add(errorLabel);
	}
	
	private void generateButtons() {
		okButton = new RectangleButton("OK");
		okButton.setFont(new Font(Style.font_name_01, Font.BOLD, 16));
		okButton.setBounds(46, 365, 112, 30);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				confirm();
			}
		});
		getContentPane().add(okButton);
		
		cancelButton = new RectangleButton("Cancella");
		cancelButton.setFont(new Font(Style.font_name_01, Font.BOLD, 16));
		cancelButton.setBounds(183, 365, 112, 30);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(cancelButton);
		
		
	}
	
	private void generateTextFields() {
		oldPasswordField = new JPasswordField();
		oldPasswordField.setBounds(70, 154, 201, 20);
		oldPasswordField.addKeyListener(new KeyAdapter() {
        	public void keyPressed(KeyEvent e) { 
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    confirm();
                }
            }
        });
		getContentPane().add(oldPasswordField);
		
		newPasswordField = new JPasswordField();
		newPasswordField.setBounds(70, 240, 201, 20);
		newPasswordField.addKeyListener(new KeyAdapter() {
        	public void keyPressed(KeyEvent e) { 
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    confirm();
                }
            }
        });
		getContentPane().add(newPasswordField);
		
		newRepeatPasswordField = new JPasswordField();
		newRepeatPasswordField.setBounds(70, 294, 201, 20);
		newRepeatPasswordField.addKeyListener(new KeyAdapter() {
        	public void keyPressed(KeyEvent e) { 
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    confirm();
                }
            }
        });
		getContentPane().add(newRepeatPasswordField);
	}
	
	private void confirm() {
		String oldPwd = String.valueOf(oldPasswordField.getPassword());
		String newPwd = String.valueOf(newPasswordField.getPassword());
		String newRepeatPwd = String.valueOf(newRepeatPasswordField.getPassword());
		
		try {
    		clearErrorMessage();
    		if(oldPwd.isBlank() || newPwd.isBlank() || newRepeatPwd.isBlank()) {
    			throw new EmptyFieldException();
    		}
    		else if(newPwd.compareTo(newRepeatPwd)!=0){
    			throw new PasswordsNotMatchingException();
    		}
    		else if(newPwd.compareTo(oldPwd)==0) {
    			throw new SimilarPasswordException();
    		}
    		
    		oldPassword = oldPwd;
    		newPassword = newPwd;
    		setVisible(false);
		}
    	catch (PasswordsNotMatchingException | EmptyFieldException | SimilarPasswordException e) {
			showErrorMessage(e.getMessage());
		}
		
	}
}
