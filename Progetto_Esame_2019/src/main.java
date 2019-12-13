import classes.Agenda;
import exception.DateFormatException;
import exception.DurataException;
import exception.NameFormatException;
import exception.TimeFormatException;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Luca
 * date: 13/12/2019
 * Versione con add funzionante ed implementazione del main.
 */
public class Main {

    /**
     * TO DO:
     *
     * - METODO PER LA MODIFICA DI UNA APPUNTAMENTO
     */

    public static void main(String[] args) throws DurataException, NameFormatException, TimeFormatException, DateFormatException, IOException {
        engine();
    }

    /**
     * METODO DA COMPLETARE MAN MANO CHE SI AGGIUNGONO FUNZIONALITA'
     * <br><br>
     * Metodo engine che e' il vero e proprio motore del software. <br>
     * Mostra il menu principale e propone diverse scelte all'utente: <br>
     *     - 1 per vedere tutti gli appuntamenti in agenda; <br>
     *     - 2 per aggiungere un nuovo appuntamento all'agenda; <br>
     *     - 3 per eliminare un appuntamento dall'agenda; <br>
     *     - 4 ricerche. <br>
     * @throws DurataException Eccezione per durata minore di 5 minuti.
     * @throws NameFormatException Eccezione per il formato errato del nome (deve essere, per esempio, Mario).
     * @throws TimeFormatException Eccezione per il formato errato dell'ora (deve essere HH-mm).
     * @throws DateFormatException Eccezione per il formato errato della data (deve essere dd-MM-yyyy).
     * @throws IOException Eccezione per IO
     */
    public static void engine() throws DurataException, NameFormatException, TimeFormatException, DateFormatException, IOException {
        menuScelta();
        Agenda ag = new Agenda();
        ag.initAgenda();
        Scanner sc = new Scanner(System.in);
        String scelta = sc.next();
        while (!scelta.equals("exit")) {
            switch (scelta) {
                case "1":
                    ag.menuViewList();
                    break;
                case "2":
                    ag.menuAdd();
                    break;
                case "3":
                    ag.menuDelete();
                    break;
                case "4":
                    ag.menuRicerche();
                    break;
            }
            menuScelta();
            scelta = sc.next();
        }
        ag.printOnFile("File_Appuntamenti_Test");
        sc.close();
    }

    /**
     * Metodo che mostra un piccolo menu con le scelte che possono essere eseguite dall'utente.
     */
    public static void menuScelta() {
        System.out.println("--- MENU' PRINCIPALE DELL'AGENDA ---");
        System.out.println("Digitare 1 per visualizzare gli appuntamenti:");
        System.out.println("Digitare 2 per aggiungere un appuntamento:");
        System.out.println("Digitare 3 per eliminare un appuntamento:");
        System.out.println("Digitare 4 per le ricerche di appuntamenti:");
        System.out.print("Digitare exit per uscire: ");
    }
}
