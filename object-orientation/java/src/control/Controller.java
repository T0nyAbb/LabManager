package control;

import DBconnection.OracleConnection;
import dao.*;
import dto.*;
import exceptions.EmptyFieldException;
import exceptions.IncorrectCredentialsException;
import exceptions.InvalidTextFileContentException;
import exceptions.PasswordsNotMatchingException;
import gui.frames.*;
import gui.utility.ChangePasswordDialog;
import gui.utility.ModifyDialog;
import gui.utility.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Controller {
	
	private static Connection databaseConnection;
	private Timer timer;
	
    private UtenteDao utenteDao;
    private SedeDao sedeDao;
    private StrumentoDao strumentoDao;
    private PrenotazioneDao prenotazioneDao;
    
    private Utente loggedUser;
    private AccessFrame accessFrame;
    private MainpageFrame mainpageFrame;

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

    public Controller(){
    	
    }
   
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
    
    public void showProfile() {
    	mainpageFrame.showProfilePanel();
    }
    
    public void showMakeReservationStrumento() {
    	mainpageFrame.showMakeReservationStrumentoPanel();
    }

	public void showMakeReservationDate(int idStrumento) {
    	mainpageFrame.showMakeReservationDatePanel(idStrumento);
    	mainpageFrame.getMakeReservationDatePanel().clearErrorMessage();
	}
	
    public void showHandleReservation() {
    	mainpageFrame.showHandleReservationPanel();
    }
    
    public void showStats() {
		mainpageFrame.showStatsPanel();
	}
    
    public void showWelcome() {
    	mainpageFrame.showWelcomePanel();

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
				msg += " Stai forse tentando di inserire un indirizzo e-mail?";
			
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
			
			if(SQLErrorMessage.contains("VALID_EMAIL_USR"))
				accessFrame.showSignupErrorMessage("Email non valida!");
			else if(SQLErrorMessage.contains("UNIQ_EMAIL"))
				accessFrame.showSignupErrorMessage("Email gia' in uso da un altro utente!");
			else if(SQLErrorMessage.contains("USERNAME"))
				accessFrame.showSignupErrorMessage("Username non valido! (6-18 caratteri alfanumerici)");
			else if(SQLErrorMessage.contains("UTENTE_PK"))
				accessFrame.showSignupErrorMessage("Username gia' in uso da un altro utente!");
			else if(SQLErrorMessage.contains("INSERISCI_PW"))
				accessFrame.showSignupErrorMessage("Password non valida! (6-18 caratteri alfanumerici, almeno un numero)");
			else
				accessFrame.showSignupErrorMessage("Campi non validi!");
		}
    }
    
    public void logoutMainpageFrame() {
    	closeMainpageOpenAccess();
    	loggedUser = null;
    }
    
	public void insertPrenotazione(Strumento strumento, Date data_inizio, int durata) {
		Prenotazione newPrenotazione = new Prenotazione(0, strumento, loggedUser, null, durata, data_inizio);
		mainpageFrame.getMakeReservationDatePanel().clearErrorMessage();
		mainpageFrame.getMakeReservationDatePanel().setErrorMessageColor(Style.foreground_color_error);
		try {
			prenotazioneDao.insert(newPrenotazione);
			mainpageFrame.getMakeReservationDatePanel().setErrorMessageColor(Style.foreground_color_success);
			mainpageFrame.getMakeReservationDatePanel().showErrorMessage("Prenotazione inserita!");
		} catch (SQLException e) {
			String SQLErrorMessage = e.toString().toUpperCase();
			String LabelMessage = "<html>";
			
			if(SQLErrorMessage.contains("NO_OVERLAP_PREN"))
				LabelMessage += "Strumento gia' prenotato nella data e ora inserita!";
			else if(SQLErrorMessage.contains("VALID_PREN_INIZIO"))
				LabelMessage += "La data di inizio non puo' essere una data presente o passata!";
			else if(SQLErrorMessage.contains("VALID_PREN_DURATA"))
				LabelMessage += "La durata deve essere compresa tra 1 e 24!";
			else if(SQLErrorMessage.contains("NO_DOUBLE_PREN"))
				LabelMessage += "Non e' possibile prenotare due strumenti diversi alla stessa data e ora!";
			else
				LabelMessage += "Campi non validi!";
			
			LabelMessage += "</html>";
			mainpageFrame.getMakeReservationDatePanel().showErrorMessage(LabelMessage);
		}
		
	}
	
	public void deletePrenotazione(Prenotazione prenotazione) {
		mainpageFrame.getHandleReservationPanel().clearErrorMessage();
		
		try {
			mainpageFrame.getHandleReservationPanel().clearErrorMessage();
			getPrenotazioneDao().delete(prenotazione);
			mainpageFrame.getHandleReservationPanel().loadListContent();
		} catch (SQLException e) {
			String SQLErrorMessage = e.toString().toUpperCase();
			
			if(SQLErrorMessage.contains("DELETE_OR_MODIFY_PREN"))
				mainpageFrame.getHandleReservationPanel().showErrorMessage("Non si puo' cancellare una prenotazione passata!");
			else
				mainpageFrame.getHandleReservationPanel().showErrorMessage("Campi non validi!");
		}
	}
	
	public void updatePrenotazione(Prenotazione prenotazione) {
		mainpageFrame.getHandleReservationPanel().clearErrorMessage();
		
		ModifyDialog modifyDialog = new ModifyDialog(this, mainpageFrame);
		modifyDialog.setIdStrumento(prenotazione.getStrumento().getId());
		modifyDialog.setDataInizio(prenotazione.getDataInizio());
		modifyDialog.setDurata(prenotazione.getDurata());
		modifyDialog.setVisible(true);
		
		DateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm"); 
		ArrayList<String> params = new ArrayList<>();
		
		try {
			params.add(timeFormat.format(modifyDialog.getDataInizio()));
			params.add(modifyDialog.getDurata() + "");
			getPrenotazioneDao().update(prenotazione, params);
			mainpageFrame.getHandleReservationPanel().loadListContent();
		}catch(SQLException e){
			String SQLErrorMessage = e.toString().toUpperCase();
			String LabelMessage = "<html>";
			
			if(SQLErrorMessage.contains("VALID_PREN_INIZIO"))
				LabelMessage += "La data di inizio non puo' essere una data presente o passata!";
			else if(SQLErrorMessage.contains("VALID_PREN_DURATA"))
				LabelMessage += "La durata deve essere compresa tra 1 e 24!";
			else if(SQLErrorMessage.contains("NO_DOUBLE_PREN"))
				LabelMessage += "Non e' possibile prenotare due strumenti diversi alla stessa data e ora!";
			else if(SQLErrorMessage.contains("DELETE_OR_MODIFY_PREN"))
				LabelMessage += "Non si puo' modificare una prenotazione passata!";
			else if(SQLErrorMessage.contains("NO_OVERLAP_PREN"))
				LabelMessage += "Strumento gia' prenotato nella data e ora inserita!";
			else
				LabelMessage += "Campi non validi!";
			
			LabelMessage += "</html>";
			mainpageFrame.getHandleReservationPanel().showErrorMessage(LabelMessage);
		}catch(NullPointerException e) {
		}
	}
    
	public void deleteLoggedUserAccount() {
		if(loggedUser != null) {
			try {
				int result = JOptionPane.showConfirmDialog(mainpageFrame, "Sei sicuro di voler eliminare il tuo account\ne tutti i dati associati?.", "ATTENZIONE", JOptionPane.YES_NO_CANCEL_OPTION);

				if(result == JOptionPane.YES_OPTION) {
					closeMainpageOpenAccess();
					utenteDao.delete(loggedUser);
					loggedUser = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void changeLoggedUserPassword() {
		if(loggedUser != null) {
			String errorMessage = "";
			while(true) {
				ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog(mainpageFrame);
				changePasswordDialog.showErrorMessage(errorMessage);
				changePasswordDialog.setVisible(true);
				try {
					String oldPass = changePasswordDialog.getOldPassword();
					String newPass = changePasswordDialog.getNewPassword();
					if(oldPass == null || newPass == null) {
						throw new EmptyFieldException();
					}
					
					utenteDao.getByCredentials(loggedUser.getUsername(), oldPass);
					utenteDao.updatePassword(loggedUser, newPass);
					
					closeMainpageOpenAccess();
					break;
				}
				catch(SQLException e) {
					String SQLErrorMessage = e.toString().toUpperCase();
					
					if(SQLErrorMessage.contains("INSERISCI_PW")) {
						errorMessage = "Password non valida! (6-18 caratteri alfanumerici, almeno un numero)";
					}
					else {
						errorMessage = "Campi non validi!";
					}
				}
				catch (IncorrectCredentialsException e) {
					errorMessage = "Password errata!";
				}
				catch(EmptyFieldException e) {
					errorMessage = "Campi vuoti!";
					break;
				}
			}
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
    	timer.restart();
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
