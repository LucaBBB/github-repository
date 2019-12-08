package rubrica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Luca Borsalino
 * Date: 2019/12/07
 *
 * Versione con aggiunta della lettura e della scrittura da/su file.
 * Aggiunti anche i test (funzionanti) per i due metodi aggiunti.
 */

public class Contatto {
    private final String NOME;
    private String eMail;
    private ArrayList<String> numeriTel;

    public Contatto(String nome, String email, String tels) {
        NOME = nome;
        this.eMail = email;
        numeriTel = new ArrayList<>(Arrays.asList(tels.split(",")));
    }

    public Contatto(String nome, String email) {
        this(nome, email, "");
    }

    public Contatto(String nome) {
        this(nome, "", "");
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    /**
     * Metodo che inserisce un numero nella lista dei numeri di telefono
     * @param num: il nuovo numero da inserire
     * @return : 1 se il nuovo numero e' stato inserito, -1 se era gia' presente.
     */
    public int inserisciNumTel (String num) {
        if (numeriTel.contains(num))
            return -1;
        numeriTel.add(num);
        return 1;
    }

    /**
     * Metodo che inserisce un numero nella lista dei numeri di telefono
     * @param num: il nuovo numero da inserire
     * @return : 1 se il nuovo numero e' stato eliminato, -1 se non era presente.
     */
    public int eliminaNumTel (String num) {
        if (!numeriTel.contains(num))
            return -1;
        numeriTel.remove(num);
        return 1;
    }

    /**
     * Metodo che crea una stringa con tutti i numeri di telefono di un contatto.
     * @return la stringa che concatena con una virgola i numeri di telefono. 
     */
    public String numTel() {
        StringBuilder sb = new StringBuilder();
        int i;

        for (i=0; i<numeriTel.size()-1; i++) {
            sb.append(numeriTel.get(i)).append(",");
        }
        sb.append(numeriTel.get(i));
        return String.valueOf(sb);
    }

    /**
     * Metodo toString modificato appositamente per effettuare la scrittura su file.
     * @return la stringa che concatena il NOME, l'eMail ed il/i numero/i di telefono del contatto.
     */
    @Override
    public String toString() {
        return NOME + "," + eMail + "," + numTel() + "\n";
    }

    /**
     * Equals modificato per confrontare due oggetti contatto.
     */
    public boolean equals (Object contatto) {
        if (this == contatto)
            return true;
        if (!(contatto instanceof Contatto))
            return false;
        Contatto cont = (Contatto) contatto;
        return Objects.equals(NOME, cont.getNome());
    }

	public String getNome() {
		return NOME;
	}
}
