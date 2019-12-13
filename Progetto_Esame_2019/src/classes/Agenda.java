package classes;

import exception.DateFormatException;
import exception.DurataException;
import exception.NameFormatException;
import exception.TimeFormatException;

import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Luca
 * date: 11/12/2019
 * <p>
 * Progetto di Programmazione ad Oggetti 2018
 * <p>
 * Classe Agenda che simula il funzionamento di una reale agenda, emulando le funzionalita' di aggiunta, modifica ed
 * eliminazione di appuntamenti.
 */
public class Agenda {
    ArrayList<Appuntamento> listaAppuntamenti;
    Scanner sc2 = new Scanner(System.in);

    public Agenda() {
        initAgenda();
    }

    public void initAgenda() {
        listaAppuntamenti = new ArrayList<>();
        readFile("File_Appuntamenti_Test");
    }

    /**
     * Versione super base dell'add di appuntamenti
     */
    public int addAppuntamento(Appuntamento a) {
        if (listaAppuntamenti.isEmpty()) {
            listaAppuntamenti.add(a);
            return 1;
        }
        if (listaAppuntamenti.contains(a))
            return -1;
        if (a.getDataObj().compareTo(listaAppuntamenti.get(0).getDataObj())<0) {
            listaAppuntamenti.add(0, a);
            return 1;
        }
        for (int i=1; i<listaAppuntamenti.size(); i++) {
            Appuntamento actual = listaAppuntamenti.get(i);
            if (a.getDataObj().compareTo(actual.getDataObj())<0) {
                listaAppuntamenti.add(i, a);
                return 1;
            }
            if (a.getDataObj().compareTo(actual.getDataObj())==0) {
                if (a.getOraObj().plus(a.getDurata(), ChronoUnit.MINUTES).compareTo(actual.getOraObj())<=0 && !a.getLuogo().equals(actual.getLuogo())) {
                    listaAppuntamenti.add(i, a);
                    return 1;
                }
            }
        }
        Appuntamento last = listaAppuntamenti.get(listaAppuntamenti.size()-1);
        if (a.getDataObj().compareTo(last.getDataObj())>0 || a.getDataObj().compareTo(last.getDataObj())==0 && a.getOraObj().compareTo(last.getOraObj())>0)
        {
            listaAppuntamenti.add(listaAppuntamenti.size(), a);
            return 1;
        }
        return -1;
    }

    /**
     * Metodo che permette di rimuovere un appuntamento dall'agenda.
     *
     * @param a l'appuntamento da rimuovere
     * @return 1 se l'appuntamento e' stato rimosso dall'agenda;<br>
     * 0 se l'agenda e' vuota, quindi non ha rimosso;<br>
     * -1 se l'appuntamento non e' stato rimosso perche' non era presente nell'agenda.
     */
    public boolean removeAppuntamento(Appuntamento a) {
        if (listaAppuntamenti.isEmpty())
            return false;
        return listaAppuntamenti.remove(a);
    }

    /**
     * Metodo che stampa a video ogni appuntamento presente nell'agenda.
     */
    public void printListaAppuntamenti() {
        if (listaAppuntamenti.isEmpty())
            System.out.println("--- LISTA VUOTA ---");
        else {
            System.out.println("--- STAMPA AGENDA ---");
            for (Appuntamento appuntamento : listaAppuntamenti) {
                System.out.println(appuntamento.toString());
            }
            System.out.println("---- FINE STAMPA ----");
        }
    }

    /**
     * Metodo che permette di cercare un appuntamento uguale a quello dato in input.
     *
     * @param a l'appuntamento da cercare nell'agenda.
     * @return l'appuntamento trovato;<br>
     * se l'appuntamento non e' stato trovato, null.
     */
    public Appuntamento findAppuntamento(Appuntamento a) {
        for (Appuntamento appuntamento : listaAppuntamenti) {
            if (appuntamento == a)
                return appuntamento;
        }
        return null;
    }

    /**
     * Metodo che cerca tutti gli appuntamenti che possiedono il campo nome come quello passato in input.
     * @param nome parametro di ricerca.
     * @return l'ArrayList contenente tutti gli appuntamenti trovati, oppure null se non sono stati trovati.
     */
    public ArrayList<Appuntamento> findAppuntamentoByName(String nome) {
        ArrayList<Appuntamento> trovati = new ArrayList<>();
        for (Appuntamento appuntamento : listaAppuntamenti) {
            if (appuntamento.getNome().equals(nome))
                trovati.add(appuntamento);
        }
        return trovati;
    }

    /**
     * Metodo che cerca tutti gli appuntamenti che possiedono il campo luogo come quello passato in input.
     * @param place parametro di ricerca.
     * @return l'ArrayList contenente tutti gli appuntamenti trovati, oppure null se non sono stati trovati.
     */
    public ArrayList<Appuntamento> findAppuntamentoByPlace(String place) {
        ArrayList<Appuntamento> trovati = new ArrayList<>();
        for (Appuntamento appuntamento : listaAppuntamenti) {
            if (appuntamento.getLuogo().equals(place))
                trovati.add(appuntamento);
        }
        return trovati;
    }

    public int readFile(String fileName) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String linea = in.readLine();
            int letti = 1;
            while (linea != null) {
                String[] dati = linea.split(",");
                String date = dati[0].trim();
                String time = dati[1].trim();
                String duration = dati[2].trim();
                String name = dati[3].trim();
                String place = dati[4].trim();
                linea = in.readLine();
                letti++;
                //System.out.println("Ho letto " + nome + ", " + email + ", " + tel);
                Appuntamento a = new Appuntamento(date, time, Integer.parseInt(duration), name, place);
                listaAppuntamenti.add(a);
            }
            in.close();
            return letti;
        } catch (FileNotFoundException e) {
            System.out.println("Non esiste il file " + fileName);
        } catch (IOException e) {
            System.out.println("Eccezione IOException!");
        } catch (DurataException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int printOnFile(String fileName) throws IOException {
        int scritti = 0;
        PrintWriter out = new PrintWriter(new FileWriter(fileName));
        for (Appuntamento appuntamento : listaAppuntamenti) {
            out.println(appuntamento.toString());
            scritti++;
        }
        out.close();
        return scritti;
    }

    public void menuViewList() {
        printListaAppuntamenti();
    }

    public void menuAdd() throws DurataException, NameFormatException, DateFormatException, TimeFormatException, IOException {
        String date;
        String time;
        int duration;
        String name;
        String place;

        System.out.print("Inserire la data dell'appuntamento (gg-MM-aaaa): ");
        date = sc2.next();
        System.out.print("Inserire l'ora dell'appuntamento (HH-mm): ");
        time = sc2.next();
        System.out.print("Inserire la durata dell'appuntamento (>= 5 minuti): ");
        duration = sc2.nextInt();
        System.out.print("Inserire il nome: ");
        name = sc2.next();
        System.out.print("Inserire il luogo dell'appuntamento: ");
        place = sc2.next();

        //if (checkDati(date, time, name)) {
            Appuntamento a = new Appuntamento(date, time, duration, name, place);
            int addingResult = addAppuntamento(a);

            if (addingResult == 1) {
                System.out.println(a.toString() + "\n" + "Aggiunto con successo!");
            }
       // }
    }

    public void menuDelete() {
        //Scanner sc = new Scanner(System.in);

        System.out.println("--- ELIMINAZIONE CONTATTO ---");
        System.out.print("Inserire la data dell'appuntamento (gg-MM-aaaa): ");
        String date = sc2.next();
        //while (!LocalDate.parse(date, Appuntamento.dateFormatter).isAfter(LocalDate.now()))
            // questa e' una implementazione da fare per verificare che la data che si sta inserendo
            // non sia antecedente al momento in cui si usa il software!
        System.out.print("Inserire l'ora dell'appuntamento (HH-mm): ");
        String time = sc2.next();
        System.out.print("Inserire il nome: ");
        String name = sc2.next();

        boolean cancellato = deleteByDateTimeName(date, time, name);
        if (cancellato)
            System.out.println("Contatto eliminato correttamente!");
        else
            System.out.println("Contatto non trovato.");
    }

    public void menuRicerche() {
        System.out.println("--- MENU RICERCHE ---");
        System.out.println("Inserire nome per effettuare una ricerca per nome: ");
    }

    public boolean checkDati(String date, String time, String name) throws NameFormatException, DateFormatException, TimeFormatException {
        String regexDate = "(0[1-9]|[12][0-9]|3[01])-(0[1-9]|[10-12]-(2[0-9])[0-9]{2})";
        String regexTime = "^(0[0-9]|1[0-9]|2[0-3]|[0-9])-[0-5][0-9]$";
        String regexName = "[A-Z]([a-z]+)";
        if (!date.matches(regexDate))
            throw new DateFormatException();
        if (!time.matches(regexTime))
            throw new TimeFormatException();
        if (!name.matches(regexName))
            throw new NameFormatException();
        return true;
    }

    public Appuntamento getAppuntamentoByIndex(int index) {
        return listaAppuntamenti.get(index);
    }

    /**
     * Metodo che permette di eliminare dall'agenda un contatto che abbia data, ora e nome coincidenti a quelli
     * passati come parametro di ricerca.
     * @param date data dell'appuntamento da eliminare;
     * @param time ora dell'appuntamento da eliminare;
     * @param name nome della persona dell'appuntamento da eliminare.
     * @return true se l'appuntamento e' stato elimanto, false altrimenti.
     */
    public boolean deleteByDateTimeName(String date, String time, String name) {
        for (int i=0; i<listaAppuntamenti.size(); i++) {
            Appuntamento actual = listaAppuntamenti.get(i);
            if (actual.getDataString().equals(date) && actual.getOraString().equals(time) && actual.getNome().equals(name)) {
                listaAppuntamenti.remove(i);
                return true;
            }
        }
        return false;
    }
}
