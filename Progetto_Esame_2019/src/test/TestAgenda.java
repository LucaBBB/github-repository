package test;

import classes.Agenda;
import classes.Appuntamento;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestAgenda {

    /**
    Agenda ag;

    @Before
    public void init() {
        ag = new Agenda();
        Appuntamento a = new Appuntamento("10-12-2019", "15:00", 15, "Pippo", "Ambulatorio");
        Appuntamento b = new Appuntamento("10-12-2019", "14:00", 45, "Pluto", "Ambulatorio");
        assertEquals(0, ag.addAppuntamento(a));
        assertEquals(1, ag.addAppuntamento(b));
    }
    @Test
    public void testAggiunta() {
        System.out.println("Stampa pre testAggiunta()");
        ag.printListaAppuntamenti();
        assertEquals(-3, ag.addAppuntamento(new Appuntamento("10-12-2019", "14:50", 15, "Mickey", "Ambulatorio")));
        assertEquals(2, ag.addAppuntamento(new Appuntamento("11-12-2019", "10:00", 30, "Mickey", "Ambulatorio")));



        System.out.println("Stampa post testAggiunta()");
        ag.printListaAppuntamenti();
        /**

        Agenda ag = new Agenda();
        ag.initAgenda();

        assertEquals(-3, ag.addAppuntamento(c));
    }
    */

    /**
     * Test per l'aggiunta di appuntamenti in rubrica.
     * Ultimo aggiornamento: 11/12/19 - 11:19.
     */
    @Test
    public void testAggiunta() {
        Appuntamento a = new Appuntamento("10-12-2019", "15:00", 15, "Pippo", "Ambulatorio");
        Appuntamento b = new Appuntamento("10-12-2019", "14:00", 45, "Pluto", "Ambulatorio");
        Appuntamento c = new Appuntamento("10-12-2019", "14:50", 15, "Mickey", "Ambulatorio");
        Agenda ag = new Agenda();
        ag.initAgenda();
        assertEquals(0, ag.addAppuntamento(a));
        assertEquals(1, ag.addAppuntamento(b));
        assertEquals(-3, ag.addAppuntamento(c));
        ag.printListaAppuntamenti();
    }

    /**
     * Test per l'eliminazione di appuntamenti dalla rubrica.
     * Ultimo aggiornamento: 11/12/19 - 11:38.
     */
    @Test
    public void testRemove() {
        Appuntamento a = new Appuntamento("10-12-2019", "15:00", 15, "Pippo", "Ambulatorio");
        Appuntamento b = new Appuntamento("10-12-2019", "14:00", 45, "Pluto", "Ambulatorio");
        Appuntamento c = new Appuntamento("10-12-2019", "14:50", 15, "Mickey", "Ambulatorio");
        Agenda ag = new Agenda();
        ag.initAgenda();
        assertEquals(0, ag.addAppuntamento(a));
        assertEquals(1, ag.addAppuntamento(b));
        assertEquals(-3, ag.addAppuntamento(c));
        // ... FINO A QUI HO AGGIUNTO a E b ...
        System.out.println("... Stampa pre eliminazione ...");
        ag.printListaAppuntamenti();

        assertEquals(1, ag.removeAppuntamento(a));  // RIMUOVO a;
        assertEquals(-1, ag.removeAppuntamento(c)); // VERIFICO CHE NON CI SIA c DA RIMUOVERE;
        assertEquals(1, ag.removeAppuntamento(b));  // RIMUOVO b;
        assertEquals(0, ag.removeAppuntamento(a));  // VERIFICO CHE NON CI SIA PIU' NULLA DA RIMUOVERE.
        System.out.println("... Stampa post eliminazione ...");
        ag.printListaAppuntamenti();
    }

    /**
     * Test per verificare il corretto funzionamento del metodo findAppuntamento.
     * Ultimo aggiornamento: 11/12/19 - 11:38.
     */
    @Test
    public void testFindAppuntamento() {
        Appuntamento a = new Appuntamento("10-12-2019", "15:00", 15, "Pippo", "Ambulatorio");
        Appuntamento b = new Appuntamento("10-12-2019", "14:00", 45, "Pluto", "Ambulatorio");
        Appuntamento c = new Appuntamento("10-12-2019", "14:50", 15, "Mickey", "Ambulatorio");
        Agenda ag = new Agenda();
        ag.initAgenda();
        assertEquals(0, ag.addAppuntamento(a));
        assertEquals(1, ag.addAppuntamento(b));
        assertEquals(-3, ag.addAppuntamento(c));
        // ... FINO A QUI HO AGGIUNTO a E b ...
        assertEquals(a, ag.findAppuntamento(a));                // verifico di trovare a cercando a;
        assertEquals(null, ag.findAppuntamento(c));    // verifico di trovare null cercando c, non esistendo.
    }

    /**
     * Test per verificare il corretto funzionamento del metodo findAppuntamentoByName.
     * Ultimo aggiornamento: 11/12/19 - 11:38.
     */
    @Test
    public void testFindAppuntamentoByName() {
        Appuntamento a = new Appuntamento("10-12-2019", "15:00", 15, "Pippo", "Ambulatorio");
        Appuntamento b = new Appuntamento("10-12-2019", "14:00", 45, "Pluto", "Ambulatorio");
        Appuntamento c = new Appuntamento("10-12-2019", "14:50", 15, "Mickey", "Ambulatorio");
        Agenda ag = new Agenda();
        ag.initAgenda();
        assertEquals(0, ag.addAppuntamento(a));
        assertEquals(1, ag.addAppuntamento(b));
        assertEquals(-3, ag.addAppuntamento(c));
        //assertEquals(1, ag.addAppuntamento(new Appuntamento("11-12-2019", "10:00", 30, "Pluto", "Ambulatorio")));
        // ... FINO A QUI HO AGGIUNTO a E b ...
        ArrayList<Appuntamento> trovati = ag.findAppuntamentoByName("Pluto");  // verifico che ci sia 1 contatto
        assertEquals(1, trovati.size());                                    // con il nome passato in input.
    }
}