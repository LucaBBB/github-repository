package test;

import classes.Agenda;
import classes.Appuntamento;
import exception.DurataException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 *
 */
public class TestAgenda {

    Agenda ag;

    @Before
    public void init() {
        ag = new Agenda();
        ag.initAgenda();
    }

    @Test
    public void testAggiunta() throws DurataException, IOException {
        //assertEquals(2, ag.readFile("File_Appuntamenti_Test")-1);
        assertEquals(1, ag.addAppuntamento(new Appuntamento("16-12-2019", "14-00", 30, "Pippo", "Aula_103")));
        //assertEquals(-1, ag.addAppuntamento(new Appuntamento("16-12-2019", "14-00", 30, "Luca", "Aula_103")));
        assertEquals(1, ag.addAppuntamento(new Appuntamento("19-12-2019", "14-00", 30, "Giovanni", "Aula_103")));
        //assertEquals(-1, ag.addAppuntamento(new Appuntamento("13-12-2019", "13-45", 30, "Francesco", "Aula_103")));
        ag.printOnFile("File_Appuntamenti_Test");
    }
}