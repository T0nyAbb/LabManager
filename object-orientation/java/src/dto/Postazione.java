package dto;

public class Postazione {
    //Attributi
	private int id_postazione;
    private Sede sede;
    private String nome;

    //Costruttori
    public Postazione(String nome) {
        this.nome = nome;
    }
    public Postazione(Sede sede, String nome) {
        this.sede = sede;
        this.nome = nome;
    }

    //Getters & Setters
    public Sede getSede() {
        return sede;
    }

    public int getId() {
		return id_postazione;
	}
	public void setId(int id_postazione) {
		this.id_postazione = id_postazione;
	}
	public void setSede(Sede sede) {
        this.sede = sede;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
