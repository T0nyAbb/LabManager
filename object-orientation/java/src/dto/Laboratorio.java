package dto;

public class Laboratorio {
    //Attributi
	private int id_lab;
    private String nome;
    private int annoFondazione;
    private String campo;
    private String descrizione;

    //Costruttori


    public Laboratorio(String nome, int annoFondazione, String campo, String descrizione) {
        this.nome = nome;
        this.annoFondazione = annoFondazione;
        this.campo = campo;
        this.descrizione = descrizione;
    }

    //Getters & Setters
    public String getNome() {
        return nome;
    }

    public int getId() {
		return id_lab;
	}

	public void setId(int id_lab) {
		this.id_lab = id_lab;
	}

	public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAnnoFondazione() {
        return annoFondazione;
    }

    public void setAnnoFondazione(int annoFondazione) {
        this.annoFondazione = annoFondazione;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
