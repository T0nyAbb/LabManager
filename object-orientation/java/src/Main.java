import dao.UtenteDao;
import dto.Utente;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception{
        UtenteDao utenteDao = new UtenteDao();

        List<Utente> l = utenteDao.getAll();

        for(Utente u:l) {
            System.out.println(l.indexOf(u)+1 +" "+ u.getEmail() +" "+ u.getPassword());
        }
    }

}