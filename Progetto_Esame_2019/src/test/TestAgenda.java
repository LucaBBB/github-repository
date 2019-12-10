package test;

import classes.Agenda;
import classes.Appuntamento;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestAgenda {

    @Test
    public void testAggiunta() {
        Appuntamento a = new Appuntamento("10-12-2019", "15:00", 15, "Pippo", "Ambulatorio");
        Appuntamento b = new Appuntamento("10-12-2019", "14:00", 45, "Pluto", "Ambulatorio");
        Appuntamento c = new Appuntamento("10-12-2019", "14:50", 15, "Mickey", "Ambulatorio");
        Agenda ag = new Agenda();
        ag.initAgenda();
        assertEquals(3, ag.addAppuntamento(a));
        assertEquals(1, ag.addAppuntamento(b));
        assertEquals(-1, ag.addAppuntamento(c));
        ag.printListaAppuntamenti();
    }
}
