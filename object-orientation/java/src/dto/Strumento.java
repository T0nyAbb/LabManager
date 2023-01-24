package dto;

public class Strumento {
    //Attributi
    private Postazione postazione;
    private String descrizione;
    private String schedaTecnica;

    //Getters & Setters
    public Postazione getPostazione() {
        return postazione;
    }

    public void setPostazione(Postazione postazione) {
        this.postazione = postazione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getSchedaTecnica() {
        return schedaTecnica;
    }

    public void setSchedaTecnica(String schedaTecnica) {
        this.schedaTecnica = schedaTecnica;
    }
}
