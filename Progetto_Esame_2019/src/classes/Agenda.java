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
 * date: 13/12/2019
 * <p>
 * Progetto di Programmazione ad Oggetti 2018
 * <p>
 * Classe Agenda che simula il funzionamento di una reale agenda, emulando le funzionalita' di aggiunta, modifica ed
 * eliminazione di appuntamenti.
 */
public class Agenda {
    ArrayList<Appuntamento> listaAppuntamenti;
    Scanner scanner = new Scanner(System.in);

    String regexDate = "^([0-2][0-9]||3[0-1])-(0[0-9]||1[0-2])-([0-9][0-9])?[0-9][0-9]$";
    String regexTime = "^(0[0-9]|1[0-9]|2[0-3]|[0-9])-[0-5][0-9]$";
    String regexName = "[A-Z]([a-z]+)";

    public Agenda() {
        initAgenda();
    }

    /**
     * Metodo che inizializza l'ArrayList ed inserisce gli appuntamenti letti dal file di testo.
     */
    public void initAgenda() {
        listaAppuntamenti = new ArrayList<>();
        readFile("File_Appuntamenti_Test");
    }

    /**
     * Metodo che permette di aggiungere un nuovo appuntamento all'Agenda.
     * @param a l'appuntamento da aggiungere.
     * @return 1 se il nuovo appuntamento e' stato aggiunto, -1 altrimenti.
     */
    public int addAppuntamento(Appuntamento a) {
        if (listaAppuntamenti.isEmpty()) {
            listaAppuntamenti.add(a);
            return 1;
        }
        if (listaAppuntamenti.contains(a))
            return -1;
        if (a.getDataObj().compareTo(listaAppuntamenti.get(0).getDataObj()) < 0) {
            listaAppuntamenti.add(0, a);
            return 1;
        }
        for (int i = 1; i < listaAppuntamenti.size(); i++) {
            Appuntamento actual = listaAppuntamenti.get(i);
            if (a.getDataObj().compareTo(actual.getDataObj()) < 0) {
                listaAppuntamenti.add(i, a);
                return 1;
            }
            if (a.getDataObj().compareTo(actual.getDataObj()) == 0) {
                if (a.getOraObj().plus(a.getDurata(), ChronoUnit.MINUTES).compareTo(actual.getOraObj()) < 0 && !a.getLuogo().equals(actual.getLuogo())) {
                    listaAppuntamenti.add(i, a);
                    return 1;
                }
            }
        }
        Appuntamento last = listaAppuntamenti.get(listaAppuntamenti.size() - 1);
        if (a.getDataObj().compareTo(last.getDataObj()) > 0 || a.getDataObj().compareTo(last.getDataObj()) == 0 && a.getOraObj().compareTo(last.getOraObj()) > 0) {
            listaAppuntamenti.add(listaAppuntamenti.size(), a);
            return 1;
        }
        return -1;
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
                System.out.println(appuntamento.toStringModificato());
            }
            System.out.println("---- FINE STAMPA ----");
        }
    }

    /**
     * Metodo di stampa generico che serve a stampare tutti gli elementi presenti nella lista data come parametro.
     *
     * @param listaDaStampare la lista da stampare.
     */
    public void genericPrintArrayList(ArrayList<Appuntamento> listaDaStampare) {
        if (listaDaStampare.isEmpty())
            System.out.println("--- NESSUN RISULTATO ---");
        else {
            System.out.println("--- STAMPA RISULTATI ---");
            for (Appuntamento appuntamento : listaDaStampare) {
                System.out.println(appuntamento.toString());
            }
            System.out.println("---- FINE STAMPA ----");
        }
    }

    /**
     * Metodo che cerca tutti gli appuntamenti che possiedono il campo nome come quello passato in input.
     *
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
     *
     * @param data parametro di ricerca.
     * @return l'ArrayList contenente tutti gli appuntamenti trovati, oppure null se non sono stati trovati.
     */
    public ArrayList<Appuntamento> findAppuntamentoByData(String data) {
        ArrayList<Appuntamento> trovati = new ArrayList<>();
        for (Appuntamento appuntamento : listaAppuntamenti) {
            if (appuntamento.getDataString().equals(data))
                trovati.add(appuntamento);
        }
        return trovati;
    }

    /**
     * Metodo che permette di leggere da file tutti gli appuntamenti presenti in agenda;
     * inoltre, dopo averli letti, vengono inseriti in una ArrayList di Appuntamento, per poter essere gestiti.
     *
     * @param fileName il nome del file dal quale leggere gli appuntamenti.
     * @return il numero di appuntamenti letti dal file.
     */
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

    /**
     * Metodo che permette di scrivrere su file gli appuntamenti presenti nell'ArrayList di Appuntamento.
     *
     * @param fileName il nome del file su cui scrivere.
     * @return il numero di appuntamenti scritti sul file.
     * @throws IOException eccezione per IO.
     */
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

    /**
     * Metodo che richiama il metodo per stampare la lista degli appuntamenti.
     */
    public void menuViewList() {
        printListaAppuntamenti();
    }

    /**
     * Metodo che richiede data, ora, durata, nome e luogo, e se questi campi sono validi, crea un appuntamento
     * e lo inserisce nella lista richiamando il metodo addAppuntamento().
     * Dopodiche', chiama il metodo per cancellare con data, ora e nome e restituisce a video l'esito della
     * cancellazione.
     *
     * @throws DurataException     eccezione per la durata;
     * @throws NameFormatException eccezione per il formato del nome;
     * @throws DateFormatException eccezione per il formato della data;
     * @throws TimeFormatException eccezione per il formato dell'ora.
     */
    public void menuAdd() throws DurataException, NameFormatException, DateFormatException, TimeFormatException {
        String date, time, name, place;
        int duration;

        System.out.print("Inserire la data dell'appuntamento (gg-MM-aaaa): ");
        date = scanner.next();
        if (!date.matches(regexDate) || LocalDate.parse(date, Appuntamento.dateFormatter).isBefore(LocalDate.now()))
            throw new DateFormatException();
        System.out.print("Inserire l'ora dell'appuntamento (HH-mm): ");
        time = scanner.next();
        if (!time.matches(regexTime))
            throw new TimeFormatException();
        System.out.print("Inserire la durata dell'appuntamento (>= 5 minuti): ");
        duration = scanner.nextInt();
        System.out.print("Inserire il nome: ");
        name = scanner.next();
        if (!name.matches(regexName))
            throw new NameFormatException();
        System.out.print("Inserire il luogo dell'appuntamento: ");
        place = scanner.next();

        Appuntamento a = new Appuntamento(date, time, duration, name, place);
        int addingResult = addAppuntamento(a);

        if (addingResult == 1)
            System.out.println(a.toString() + "\n" + "Aggiunto con successo!");
        else
            System.out.println(a.toString() + "\n" + "Appuntamento non aggiunto!");
    }

    /**
     * Metodo che richiede data, ora e nome e se questi campi sono validi, cerca nella lista di appuntamenti se e'
     * possibile eliminare il record con i campi corrispondenti a quelli dati in input.
     * @throws DateFormatException eccezione per il formato della data errata.
     */
    public void menuDelete() throws DateFormatException {
        System.out.println("--- ELIMINAZIONE CONTATTO ---");
        System.out.print("Inserire la data dell'appuntamento (gg-MM-aaaa): ");
        String date = scanner.next();
        if (!date.matches(regexDate) || LocalDate.parse(date, Appuntamento.dateFormatter).isBefore(LocalDate.now()))
            throw new DateFormatException();
        System.out.print("Inserire l'ora dell'appuntamento (HH-mm): ");
        String time = scanner.next();
        System.out.print("Inserire il nome: ");
        String name = scanner.next();

        boolean cancellato = deleteByDateTimeName(date, time, name);
        if (cancellato)
            System.out.println("Contatto eliminato correttamente!");
        else
            System.out.println("Contatto non trovato.");
    }

    /**
     * Metodo che richiede all'utente di decidere se effettuare la ricerca per nome, inserendo successivamente il nome
     * parametro di ricerca, oppure per data, inserendo succcessivamenti la data parametro di ricerca.
     * Dopodiche', chiama il metodo che stampa la lista ottenuta dalla ricerca per nome o per data.
     */
    public void menuRicerche() {
        String ricerca;

        System.out.println("--- MENU RICERCHE ---");
        System.out.println("Inserire nome per effettuare una ricerca per nome: ");
        System.out.println("Inserire data per effettuare una ricerca per data: ");
        String parametroRicerca = scanner.next();

        if (parametroRicerca.equals("nome")) {
            System.out.println("Inserire il nome da cercare: ");
            ricerca = scanner.next();
            genericPrintArrayList(findAppuntamentoByName(ricerca));
        } else if (parametroRicerca.equals("data")) {
            System.out.println("Inserire la data da cercare: ");
            ricerca = scanner.next();
            genericPrintArrayList(findAppuntamentoByData(ricerca));
        }
    }

    /**
     * Metodo che permette di eliminare dall'agenda un contatto che abbia data, ora e nome coincidenti a quelli
     * passati come parametro di ricerca.
     *
     * @param date data dell'appuntamento da eliminare;
     * @param time ora dell'appuntamento da eliminare;
     * @param name nome della persona dell'appuntamento da eliminare.
     * @return true se l'appuntamento e' stato elimanto, false altrimenti.
     */
    public boolean deleteByDateTimeName(String date, String time, String name) {
        for (int i = 0; i < listaAppuntamenti.size(); i++) {
            Appuntamento actual = listaAppuntamenti.get(i);
            if (actual.getDataString().equals(date) && actual.getOraString().equals(time) && actual.getNome().equals(name)) {
                listaAppuntamenti.remove(i);
                return true;
            }
        }
        return false;
    }
}
