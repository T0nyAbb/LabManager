package dto;

import java.util.Date;

public class Prenotazione {
    //Attributi
	private int id_prenotazione;
    private Strumento strumento;
    private Utente utente;
    private Date dataPrenotazione;
    private int durata;
    private Date dataInizio;

    //Costruttori
    public Prenotazione(int id_prenotazione, Strumento strumento, Utente utente, Date dataPrenotazione, int durata, Date dataInizio) {
        this.id_prenotazione = id_prenotazione;
    	this.strumento = strumento;
        this.utente = utente;
        this.dataPrenotazione = dataPrenotazione;
        this.durata = durata;
        this.dataInizio = dataInizio;
    }

    //Getters & Setters
    public Strumento getStrumento() {
        return strumento;
    }

    public int getId() {
		return id_prenotazione;
	}

	public void setId(int id_prenotazione) {
		this.id_prenotazione = id_prenotazione;
	}

	public void setStrumento(Strumento strumento) {
        this.strumento = strumento;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Date getDataPrenotazione() {
        return dataPrenotazione;
    }

    public void setDataPrenotazione(Date dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }
}
