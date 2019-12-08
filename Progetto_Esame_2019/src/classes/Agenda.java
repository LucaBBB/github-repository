package classes;

import java.util.ArrayList;

/**
 * @author Luca
 * date: 2019-12-08
 *
 * Progetto esame di Programmazione ad Oggetti dell'Anno Accademico 2018/2019.
 * Classe Agenda che modella le funzionalita' di un'agenda (aggiunta/rimozione/modifica appuntamenti).
 */
public class Agenda {
    ArrayList<Appuntamento> listaAppuntamenti;

    public void init() {
        listaAppuntamenti = new ArrayList<>();
    }

    /**
     * Metodo che aggiunge un nuovo appuntamento alla lista degli appuntamenti.
     * Versione molto base-base!!!
     * @param nuovoAppuntamento l'appuntamento da aggiungere.
     * @return  1 se l'appuntamento e' stato inserito;<br>
     *         -1 se l'appuntamento e' gia presente, quindi non viene inserito.
     */
    public int addAppuntamento(Appuntamento nuovoAppuntamento) {
        if (listaAppuntamenti.contains(nuovoAppuntamento))
            return -1;
        listaAppuntamenti.add(nuovoAppuntamento);
        return 1;
    }
}
