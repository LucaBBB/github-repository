package classes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author Luca
 * date: 2019-12-08
 *
 * Progetto esame di Programmazione ad Oggetti dell'Anno Accademico 2018/2019.
 * Classe Appuntamento che modella un appuntamento.
 */

public class Appuntamento {
    LocalDate data;
    LocalTime ora;
    int durata;         // espressa in minuti
    String nome;
    String luogo;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Appuntamento(String date, String time, int duration, String name, String place) {
        data = LocalDate.parse(date, dateFormatter);
        ora = LocalTime.parse(time);
        this.durata = duration;
        this.nome = name;
        this.luogo = place;
    }

    /**
     * Metodo che restituisce l'oggetto di tipo Date.
     * @return oggetto Date.
     */
    public LocalDate getDataObj() {
        return data;
    }

    /**
     * Metodo che restituisce la data formattata con il format corretto (dd-mm-yyyy).
     * @return la stringa contenente la data formattata.
     */
    public String getDataString() {
        return data.format(dateFormatter);
    }

    /**
     * Metodo che restituisce l'oggetto di tipo Time.
     * @return oggetto Time.
     */
    public LocalTime getOraObj() {
        return ora;
    }

    public String getOraString() {return ora.toString();}

    public int getDurata() {
        return durata;
    }

    public String getNome() {
        return nome;
    }

    public String getLuogo() {
        return luogo;
    }

    @Override
    public String toString() {
        return getDataString() + " " + getOraObj() + " | " + getDurata() + " min, " + getNome() + ", " + getLuogo() + "\n";
    }
}
