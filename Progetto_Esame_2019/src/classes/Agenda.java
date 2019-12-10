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
    public int addAppuntamento(Appuntamento a) {
        if (listaAppuntamenti.contains(a))
            return -1;
        listaAppuntamenti.add(a);
        return 1;
    }*/

    public int addAppuntamento(Appuntamento a) {
        if (listaAppuntamenti.contains(a))
            return -3;
        if (listaAppuntamenti.isEmpty ()) {
            listaAppuntamenti.add(a);
            return 3;
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
                        return 0;
                    else
                        return -1;
                }
            }
        }
        Appuntamento last = listaAppuntamenti.get(listaAppuntamenti.size()-1);
        if (last.getOraObj().plus(last.getDurata(), ChronoUnit.MINUTES).compareTo(a.getOraObj())<0) {
            listaAppuntamenti.add(listaAppuntamenti.size(), a);
            return 2;
        }
        return -2;
    }

    public void printListaAppuntamenti() {
        for (Appuntamento appuntamento : listaAppuntamenti) {
            System.out.println(appuntamento.toString());
        }
    }
}
