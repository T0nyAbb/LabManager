package gui.utility;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import control.Controller;
import dto.Prenotazione;
import dto.Strumento;

public class ReservationTimeComboBox extends JComboBox<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] allTimestamps;
	private Controller controller;
	private List<Prenotazione> calendarioPrenotazioni;

	public ReservationTimeComboBox(Controller controller) {
		super();
		this.controller = controller;
		allTimestamps = new String[] {"00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00", "04:30", "05:00", "05:30", "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30",  "15:00", "15:30",  "16:00", "16:30",  "17:00", "17:30",  "18:00", "18:30",  "19:00", "19:30",  "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30"};
	}
	
	public void fillForStrumentoAndDate(Strumento strumento, Date date) {
		try {
			calendarioPrenotazioni = controller.getPrenotazioneDao().getCalendarByStrumento(strumento);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALY);
			SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ITALY);
			String strDate = dateFormat.format(date);
			
			ArrayList<String> availableTimestamps = new ArrayList<String>();
			for(int i=0; i < allTimestamps.length; ++i) {
		        Date timestamp = timeFormat.parse(strDate + " " + allTimestamps[i]);
		        if(isTimestampAvailable(timestamp)) {
		        	availableTimestamps.add(new SimpleDateFormat("HH:mm").format(timestamp));
		        }
			}
			
			String[] at = new String[availableTimestamps.size()];
			for(int i=0; i< availableTimestamps.size(); ++i) {
				at[i] = availableTimestamps.get(i);
			}
			setModel(new DefaultComboBoxModel<String>(at));
		}
		catch(SQLException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isTimestampAvailable(Date timestamp) {
		boolean ret = true;
		for(Prenotazione p : calendarioPrenotazioni) {
			if(timestamp.compareTo(p.getDataInizio()) >= 0 && timestamp.compareTo(addHoursToJavaUtilDate(p.getDataInizio(), p.getDurata())) < 0) {
				ret = false;
				break;
			}
		}
		return ret;
	}
	
	public Date addHoursToJavaUtilDate(Date date, int hours) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.HOUR_OF_DAY, hours);
	    return calendar.getTime();
	}
}
