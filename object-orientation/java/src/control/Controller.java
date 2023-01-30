package control;

import dto.*;
import exceptions.EmptyFieldException;
import exceptions.IncorrectCredentialsException;
import exceptions.PasswordsNotMatchingException;
import dao.*;
import gui.frames.*;
import oracle.net.aso.m;
import DBconnection.OracleConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Controller {
	
	//ATTRIBUTI:
	private static Connection databaseConnection;
	private Timer timer;
    private UtenteDao utenteDao;
    @SuppressWarnings("unused")
	private Utente utente;
    private AccessFrame accessFrame;
    private MainpageFrame mainpageFrame;

    //METODO MAIN:
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Controller ctrl = new Controller();
					ctrl.startApplication();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    //COSTRUTTORI:
    private Controller(){}
    
    //ALTRI METODI:
    public Connection getDatabaseConnection() {
    	return databaseConnection;
    }
    
    public AccessFrame getAccessFrame() {
    	return accessFrame;
    }
    
    public void loginAccessFrame(String username, String password) {
    	try {
    		accessFrame.clearLoginErrorMessage();
    		if(username.isBlank() || password.isBlank()) {
    			throw new EmptyFieldException();
    		}
    		else {
	    		utente = utenteDao.getByCredentials(username, password);
	    		accessFrame.dispose();
	    		
	    		mainpageFrame = new MainpageFrame(this);
	    		mainpageFrame.setVisible(true);
    		}
		} catch (EmptyFieldException e) {
			accessFrame.showLoginErrorMessage(e.getMessage());
		} catch (IncorrectCredentialsException e) {
			String msg = e.getMessage();
			
			Pattern p = Pattern.compile(".*@.*");
			Matcher m = p.matcher(username);
			if(m.find())
				msg = msg + " Stai forse tentando di inserire un indirizzo e-mail?";
			
			accessFrame.showLoginErrorMessage(msg);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void signupAccessFrame(String email, String username, String password, String repeatPassword) {
    	try {
    		accessFrame.clearSignupErrorMessage();
    		if(username.isBlank() || password.isBlank() || email.isBlank()) {
    			throw new EmptyFieldException();
    		}
    		else if(password.compareTo(repeatPassword)!=0){
    			throw new PasswordsNotMatchingException();
    		}
    		else {
    			utenteDao.insert(new Utente(email, username, password));
    			signupSuccessful();
    		}
		}
    	catch (PasswordsNotMatchingException | EmptyFieldException e) {
			accessFrame.showSignupErrorMessage(e.getMessage());
		}
		catch (SQLException e) {
			String SQLErrorMessage = e.toString().toUpperCase();
			
			if(SQLErrorMessage.indexOf("EMAIL") != -1)
				accessFrame.showSignupErrorMessage("Email non valida!");
			else if(SQLErrorMessage.indexOf("USERNAME") != -1)
				accessFrame.showSignupErrorMessage("Username non valido! (6-18 caratteri alfanumerici)");
			else if(SQLErrorMessage.indexOf("INSERISCI_PW") != -1)
				accessFrame.showSignupErrorMessage("Password non valida! (6-18 caratteri alfanumerici, almeno un carattere numerico)");
			System.out.println(e.toString());
		}
    }
    
    private void startApplication() {
    	try {
			databaseConnection = OracleConnection.getOracleConnection().getConnection();
			utenteDao = new UtenteDao(databaseConnection);
				
		    accessFrame = new AccessFrame(this);
		    accessFrame.setVisible(true);
    	}
	    catch (Exception e) {
			JOptionPane.showMessageDialog(null ,"Si e' verificato un errore di tipo\n"+ e.getClass().getName()
					+" a runtime.\n L'applicazione sara' terminata.\n\nCausa:\n"
					+ e.getMessage(), "RUNTIME EXCEPTION", JOptionPane.ERROR_MESSAGE);
			closeDatabaseConnection();
			e.printStackTrace();
			System.exit(0);
		}
    }
    
    @SuppressWarnings("unused")
	private void closeDatabaseConnection() {
    	try {
    		if(databaseConnection != null) {
    			databaseConnection.close();
    		}
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    private void signupSuccessful() {
    	timer = new Timer(1000, new ActionListener(){      
            public void actionPerformed(ActionEvent e) {
            	accessFrame.showLoginPanel();
            	accessFrame.setEnabled(true);
            	stopTimer();
            }
        });
    	
    	accessFrame.setEnabled(false);
    	accessFrame.showSignupSuccessfulMessage();
    	timer.start();
    }
    
    private void stopTimer() {
    	if(timer != null) {
    		timer.stop();
    	}
    }
    
    
}
