package dto;

public class Sede {
    //Attributi
	private int id_sede;
    private Laboratorio laboratorio;
    private String indirizzo;

    //Costruttori
    public Sede(String indirizzo) {
        this.indirizzo = indirizzo;
    }
    public Sede(String indirizzo, Laboratorio laboratorio) {
        this.indirizzo = indirizzo;
        this.laboratorio = laboratorio;
    }

    //Getters & Setters
    public Laboratorio getLaboratorio() {
        return laboratorio;
    }

    public int getId() {
		return id_sede;
	}
	public void setId(int id_sede) {
		this.id_sede = id_sede;
	}
	public void setLaboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
}
