package dto;

import java.util.Date;

public class Tecnico extends Dipendente {

    public Tecnico(Sede sede, String matricola, String nome, String cognome, Date dataNascita, String codiceFiscale, String indirizzo, String telefono1, String telefono2, String email) {
        super(sede, matricola, nome, cognome, dataNascita, codiceFiscale, indirizzo, telefono1, telefono2, email);
    }
}
