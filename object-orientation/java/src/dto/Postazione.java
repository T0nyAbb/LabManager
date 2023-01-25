package dto;

public class Postazione {
    //Attributi
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
