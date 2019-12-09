package rubrica;

import exception.DimException;
import exception.FormatoException;

import java.io.*;
import java.util.ArrayList;

/**
 * @author Luca Borsalino
 * Date: 2019/12/09
 * <p>
 * Versione con aggiunta della lettura e della scrittura da/su file.
 * Aggiunti anche i test (funzionanti) per i due metodi aggiunti.
 */
public class Rubrica {
    private static int numRubriche;
    private ArrayList<Contatto> rubrica;
    public final int MAX_DIM;
    private final String NOME;

    public Rubrica(int dimensione, String nome) {
        NOME = nome;
        numRubriche++;
        MAX_DIM = dimensione;
        rubrica = new ArrayList<>();
    }

    public Rubrica() {
        this(5, "Rubrica " + numRubriche);
    }

    public static int getNumRubriche() {
        return numRubriche;
    }

    public void crea() {
        rubrica = new ArrayList<>();
    }

    /**
     * Metodo che aggiunge un nuovo contatto alla rubrica
     *
     * @param c: il nuovo contatto da aggiungere
     * @return : 1 se aggiunto, 0 se c era gia' presente,
     * -1 se la dim non permette aggiunte.
     */
    public int aggiungi(Contatto c) throws DimException{
        if (rubrica.size() >= MAX_DIM)
            throw new DimException();
        if (rubrica.contains(c))
            return 0;
        rubrica.add(c);
        return 1;
    }

    /**
     * Metodo aggiungi con il solo parametro nome.
     *
     * @param nome il nome della persona da aggiungere.
     * @return vedere return del metodo aggiugi(Contatto c).
     * @throws DimException l'eccezione per aver cercato di inserire oltre il limite.
     */
    public int aggiungiN(String nome) throws DimException, FormatoException {
        return aggiungi(new Contatto(nome));
    }

    /**
     * Metodo aggiungi con i parametri nome ed email.
     *
     * @param nome  il nome della persona da aggiungere.
     * @param email l'email della persona da aggiungere
     * @return vedere return del metodo aggiugi(Contatto c).
     * @throws DimException l'eccezione per aver cercato di inserire oltre il limite.
     */
    public int aggiungiNE(String nome, String email) throws DimException, FormatoException {
        return aggiungi(new Contatto(nome, email));
    }

    /**
     * Metodo aggiungi con i parametri nome, email ed il telefono..
     *
     * @param nome  il nome della persona da aggiungere.
     * @param email l'email della persona da aggiungere
     * @param tel   il numero di telefono della persona da aggiungere.
     * @return vedere return del metodo aggiugi(Contatto c).
     * @throws DimException l'eccezione per aver cercato di inserire oltre il limite.
     */
    public int aggiungiNET(String nome, String email, String tel) throws DimException, FormatoException {
        return aggiungi(new Contatto(nome, email, tel));
    }

    /**
     * Metodo che cerca se nell'arrayList sono presenti contatti con il nome che inizia per la stringa data in input.
     *
     * @param s la stringa parametro di ricerca.
     * @return l'arrayList contenente i contatti trovati se trovati, vuota altrimenti.
     */
    public ArrayList<Contatto> cercaPerNome(String s) {
        ArrayList<Contatto> risCerca = new ArrayList<>();
        for (Contatto contatto : rubrica) {
            if (contatto.getNome().startsWith(s))
                risCerca.add(contatto);
        }
        return risCerca;
    }

    /**
     * Metodo che cerca se nell'arrayList sono presenti contatti con l'email che inizia per la stringa data in input.
     *
     * @param s la stringa parametro di ricerca.
     * @return l'arrayList contenente i contatti trovati se trovati, vuota altrimenti.
     */
    public ArrayList<Contatto> cercaPerEmail(String s) {
        ArrayList<Contatto> risCerca = new ArrayList<>();
        for (Contatto contatto : rubrica) {
            if (contatto.getEMail().startsWith(s))
                risCerca.add(contatto);
        }
        return risCerca;
    }

    /**
     * Metodo che rimuove tutte le occorrenze cui nome inizia per la stringa data in input
     *
     * @param s la stringa parametro di ricerca.
     * @return true se ha rimosso, false altrimenti.
     */
    public boolean eliminaPerNome(String s) {
        return rubrica.removeAll(cercaPerNome(s));
    }

    /**
     * Metodo che rimuove tutte le occorrenze la quale email inizia per la stringa data in input
     *
     * @param s la stringa parametro di ricerca.
     * @return true se ha rimosso, false altrimenti.
     */
    public boolean eliminaPerEmail(String s) {
        return rubrica.removeAll(cercaPerEmail(s));
    }

    /**
     * Metodo che restituisce la dimensione dell'arrayList rubrica.
     *
     * @return il numero di elementi (rubrica.size()).
     */
    public int numEl() {
        return rubrica.size();
    }

    @Override
    public String toString() {
        return "Rubrica{" + "rubrica=" + rubrica + '}';
    }

    public String getNome() {
        return NOME;
    }

    /**
     * Metodo che permette di leggere da un file, creare un oggetto Contatto con i dati letti, ed infine inserire
     * i nuovi oggetti nell'arrayList rubrica.
     *
     * @param fileName il nome del file su cui leggere.
     * @return letti il numero di righe lette.
     */
    public int leggiContatti(String fileName) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String linea = in.readLine();
            int letti = 1;
            while (linea != null) {
                String[] dati = linea.split(",");
                String nome = dati[0].trim();
                String email = dati[1].trim();
                String tel = dati[2].trim();
                linea = in.readLine();
                letti++;
                System.out.println("Ho letto " + nome + ", " + email + ", " + tel);
                Contatto c = new Contatto(nome, email, tel);
                rubrica.add(c);
            }
            in.close();
            return letti;
        } catch (FileNotFoundException e) {
            System.out.println("Non esiste il file " + fileName);
        } catch (IOException e) {
            System.out.println("Eccezione IOException!");
        } catch (FormatoException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Metodo che scrive su file ogni contatto della rubrica nel seguente formato:
     * nome,email,tel\n
     * <p>
     * Cercare il modo di implementare il fatto che ci sia gia' oppure no il file sul
     * quale scrivere.
     *
     * @param fileName il nome del file su cui scrivere
     * @return scritti il numero di oggetti Contatto scritti.
     */
    public int scriviContatti(String fileName) {
        try {
            int scritti = 0;
            PrintWriter out = new PrintWriter(new FileWriter(fileName));
            for (Contatto contatto : rubrica) {
                out.print(contatto.toString());
                scritti++;
            }
            out.close();
            return scritti;
        } catch (IOException e) {
            System.out.println("E' stata sollevata un'eccezione per IOException!");
        }
        return 0;
    }
}