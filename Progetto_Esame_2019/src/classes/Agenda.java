package classes;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Agenda {
    ArrayList<Appuntamento> listaAppuntamenti;

    public Agenda() {}

    public void initAgenda() {
        listaAppuntamenti = new ArrayList<>();
    }

    /**
     * Metodo che verifica se e' possibile aggiungere un appuntamento alla rubrica, verificando se e' presente un lasso
     * di tempo sufficiente tra un appuntamento e l'altro e se la durata del nuovo non va ad accavallarsi con quelli
     * gia' presenti.
     *
     * @param a il nuovo appuntamento da aggiungere
     * @return  -4 se non entra nei casi sottocitati, comunque non viene inserito;<br>
     *          -3 se il nuovo appuntamento termina dopo che un altro gia' presente e' iniziato;<br>
     *          -2 se il nuovo appuntamento termina quando un altro gia' presente inizia;<br>
     *          -1 se il nuovo appuntamento e' gia' presente nell'agenda;<br>
     *           0 se il nuovo appuntamento e' stato inserito nell'agenda vuota;<br>
     *           1 se il nuovo appuntamento e' stato inserito nell'agenda normalmente;<br>
     *           2 se il nuovo appuntamento e' stato inserito in fondo all'agenda.     *
     */
    public int addAppuntamento(Appuntamento a) {
        if (listaAppuntamenti.contains(a))
            return -1;
        if (listaAppuntamenti.isEmpty ()) {
            listaAppuntamenti.add(a);
            return 0;
        }
        for (int i=0; i<listaAppuntamenti.size(); i++) {
            if (listaAppuntamenti.get(i).getDataObj().equals(a.getDataObj())) {
                if (a.getOraObj().compareTo(listaAppuntamenti.get(i).getOraObj()) < 0) {
                    if (a.getOraObj().plus(a.getDurata(), ChronoUnit.MINUTES).compareTo(listaAppuntamenti.get(i).getOraObj()) < 0) {
                        if (i==0)
                            listaAppuntamenti.add(0, a);
                        else
                            listaAppuntamenti.add(a);
                        return 1;
                    } else if (a.getOraObj().plus(a.getDurata(), ChronoUnit.MINUTES).compareTo(listaAppuntamenti.get(i).getOraObj()) == 0)
                        return -2;
                    else
                        return -3;
                }
            }
        }
        Appuntamento last = listaAppuntamenti.get(listaAppuntamenti.size()-1);
        if (last.getOraObj().plus(last.getDurata(), ChronoUnit.MINUTES).compareTo(a.getOraObj())<0) {
            listaAppuntamenti.add(listaAppuntamenti.size(), a);
            return 2;
        }
        return -4;
    }

    public void printListaAppuntamenti() {
        for (Appuntamento appuntamento : listaAppuntamenti) {
            System.out.println(appuntamento.toString());
        }
    }
}
