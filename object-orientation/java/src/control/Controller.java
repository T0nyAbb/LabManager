package control;

import dto.*;
import exceptions.EmptyFieldException;
import exceptions.IncorrectCredentialsException;
import exceptions.InvalidTextFileContentException;
import exceptions.PasswordsNotMatchingException;
import dao.*;
import gui.frames.*;
import DBconnection.OracleConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Controller {
	
	//ATTRIBUTI:
	private static Connection databaseConnection;
	private Timer timer;
	
    private UtenteDao utenteDao;
    private SedeDao sedeDao;
    private StrumentoDao strumentoDao;
    private PrenotazioneDao prenotazioneDao;
    
    private Utente loggedUser;
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
					JOptionPane.showMessageDialog(null ,"Si e' verificato un errore di tipo\n"+ e.getClass().getName()
							+" a runtime.\n L'applicazione sara' terminata.\n\nCausa:\n"
							+ e.getMessage(), "RUNTIME EXCEPTION", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
					System.exit(0);
				}
			}
		});
	}

    //COSTRUTTORI:
    public Controller(){
    	
    }
   
    //ALTRI METODI:
    public Connection getDatabaseConnection() {
    	return databaseConnection;
    }
    
    public Utente getLoggedUser() {
		return loggedUser;
	}

    public UtenteDao getUtenteDao() {
		return utenteDao;
	}

	public SedeDao getSedeDao() {
		return sedeDao;
	}

	public StrumentoDao getStrumentoDao() {
		return strumentoDao;
	}

	public PrenotazioneDao getPrenotazioneDao() {
		return prenotazioneDao;
	}

	public void startApplication() throws ClassNotFoundException, SQLException, IOException, InvalidTextFileContentException {
		databaseConnection = OracleConnection.getOracleConnection().getConnection();
		utenteDao = new UtenteDao(databaseConnection);
		sedeDao = new SedeDao(databaseConnection);
		strumentoDao = new StrumentoDao(databaseConnection);
		prenotazioneDao = new PrenotazioneDao(databaseConnection);
		accessFrame = new AccessFrame(this);
		accessFrame.setVisible(true);
    }
    
    public void showSignup() {
    	accessFrame.showSignupPanel();
    }
    
    public void showLogin() {
    	accessFrame.showLoginPanel();
    }
    
	public void loginAccessFrame(String username, String password) {
    	try {
    		accessFrame.clearLoginErrorMessage();
    		if(username.isBlank() || password.isBlank()) {
    			throw new EmptyFieldException();
    		}
    		else {
	    		loggedUser = utenteDao.getByCredentials(username, password);
	    		closeAccessOpenMainpage();
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
				accessFrame.showSignupErrorMessage("Password non valida! (6-18 caratteri alfanumerici, almeno un numero)");
			else
				accessFrame.showSignupErrorMessage("Campi non validi!");
		}
    }
    
    public void logoutMainpageFrame() {
    	closeMainpageOpenAccess();
    	loggedUser = null;
    }
    
	public void makePrenotazione(Strumento strumento, Date data_inizio, int durata) {
		Prenotazione newPrenotazione = new Prenotazione(0, strumento, loggedUser, null, durata, data_inizio);
		mainpageFrame.getMakeReservationPanel().clearErrorMessage();
		mainpageFrame.getMakeReservationPanel().setErrorMessageColor(Color.RED);
		try {
			prenotazioneDao.insert(newPrenotazione);
			mainpageFrame.getMakeReservationPanel().setErrorMessageColor(new Color(60, 179, 113));
			mainpageFrame.getMakeReservationPanel().showErrorMessage("Prenotazione inserita!");
		} catch (SQLException e) {
			String SQLErrorMessage = e.toString().toUpperCase();
			
			if(SQLErrorMessage.indexOf("NO_OVERLAP_PREN") != -1)
				mainpageFrame.getMakeReservationPanel().showErrorMessage("Strumento gia' prenotato nella data esposta!");
			else if(SQLErrorMessage.indexOf("VALID_PREN_INIZIO") != -1)
				mainpageFrame.getMakeReservationPanel().showErrorMessage("La data prenotazione non puo' essere una data presente o passata!");
			else if(SQLErrorMessage.indexOf("VALID_PREN_DURATA") != -1)
				mainpageFrame.getMakeReservationPanel().showErrorMessage("La durata deve essere compresa tra 1 e 24!");
			else
				mainpageFrame.getMakeReservationPanel().showErrorMessage("Campi non validi!");
		}
		
	}
	
	public void deletePrenotazione(Prenotazione pren) {
		try {
			mainpageFrame.getHandleReservationPanel().clearErrorMessage();
			getPrenotazioneDao().delete(pren);
			mainpageFrame.getHandleReservationPanel().loadListContent();
		} catch (SQLException e) {
			String SQLErrorMessage = e.toString().toUpperCase();
			
			if(SQLErrorMessage.indexOf("DELETE_OR_MODIFY_PREN") != -1)
				mainpageFrame.getHandleReservationPanel().showErrorMessage("Non si puo' cancellare una prenotazione passata!");
			e.printStackTrace();
		}
	}
	
    
    public void showProfile() {
    	mainpageFrame.showProfilePanel();
    }
    
    public void showMakeReservation() {
    	mainpageFrame.showMakeReservationPanel();
    }
    
    public void showHandleReservation() {
    	mainpageFrame.showHandleReservationPanel();
    }
    
    public void showWelcome() {
    	mainpageFrame.showWelcomePanel();

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
    
    private void closeAccessOpenMainpage() throws SQLException {
	    accessFrame.dispose();
		mainpageFrame = new MainpageFrame(this);
		mainpageFrame.setVisible(true);
    }
    
    private void closeMainpageOpenAccess() {
    	mainpageFrame.dispose();
	   	accessFrame = new AccessFrame(this);
	   	accessFrame.setVisible(true);
    	
    }
    
    private void stopTimer() {
    	if(timer != null) {
    		timer.stop();
    	}
    }

    
    
}
