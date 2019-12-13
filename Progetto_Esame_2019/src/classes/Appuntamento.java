package classes;

import exception.DurataException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Luca
 * date: 2019-12-08
 *
 * Progetto esame di Programmazione ad Oggetti dell'Anno Accademico 2018/2019.
 * Classe Appuntamento che modella un appuntamento.
 */

public class Appuntamento {
    private LocalDate data;
    private LocalTime ora;
    private int durata;         // espressa in minuti
    private String nome;
    private String luogo;

    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH-mm");

    public Appuntamento(String date, String time, int duration, String name, String place) throws DurataException {
        data = LocalDate.parse(date, dateFormatter);
        ora = LocalTime.parse(time, timeFormatter);
        if (duration<5)
            throw new DurataException();
        else
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

    public String getOraString() {return ora.format(timeFormatter);}

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
        return getDataString() + "," + getOraString() + "," + getDurata() + "," + getNome() + "," + getLuogo();
    }
}