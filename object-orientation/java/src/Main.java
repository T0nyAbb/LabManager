import dao.*;
import dto.*;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception{
        UtenteDao utenteDao = new UtenteDao();
        String ricerca = "Microscopio";
        List<Utente> l = utenteDao.getAll();
        List<Strumento> strumenti = new StrumentoDao().getStrumentoByDescription(ricerca);
        List<Laboratorio> labs = new LaboratorioDao().getAll();
        List<Sede> sedi = new SedeDao().getAll();
        List<Strumento> str = new StrumentoDao().getAll();
        List<Prenotazione> pren = new PrenotazioneDao().getAll();

        System.out.println("Elenco tutti gli utenti...");
        for(Utente u:l) {
            System.out.println(l.indexOf(u)+1 +" Email: "+ u.getEmail() +" Password: "+ u.getPassword());
        }
        System.out.println("\nElenco tutte le sedi...");
        for(Sede sede:sedi) {
            System.out.println(sedi.indexOf(sede)+1 + " Indirizzo: " + sede.getIndirizzo());
        }
        System.out.println("\nElenco tutti i laboratori...");
        for(Laboratorio lab:labs) {
            System.out.println(labs.indexOf(lab)+1 + " Nome: " + lab.getNome());
        }
        System.out.println("\nElenco tutti gli strumenti corrispondenti a '" + ricerca + "'...");
        for(Strumento s:strumenti) {
            Postazione post = s.getPostazione();
            Sede sede = post.getSede();
            System.out.println(strumenti.indexOf(s)+1 + " Strumento: " + s.getDescrizione() + " Postazione: " + post.getNome() + " Indirizzo della Sede: " + sede.getIndirizzo());
        }
        System.out.println("\nElenco tutti gli strumenti...");
        for(Strumento s:str) {
            Postazione post = s.getPostazione();
            Sede sede = post.getSede();
            System.out.println(str.indexOf(s)+1 + " Strumento: " + s.getDescrizione() + " Postazione: " + post.getNome() + " Indirizzo della Sede: " + sede.getIndirizzo());
        }
        for(Prenotazione p:pren) {
            Strumento s = p.getStrumento();
            Utente u = p.getUtente();
            System.out.println(pren.indexOf(p)+1 + " Data prenotazione: " + p.getDataPrenotazione() + " Data utilizzo: " + p.getDataInizio() + " Durata: " + p.getDurata() + " Ore " + " Strumento: " + s.getDescrizione() + " Utente: " + u.getUsername());
        }



    }

}