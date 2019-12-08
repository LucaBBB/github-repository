package test;

import org.junit.Test;
import rubrica.Contatto;

import static org.junit.Assert.assertEquals;

public class TestContatto {

    @Test
    public void testCostruttore1() {
        String nome = "Pippo";
        String email = "pippo@email.com";
        String tels = "2020202,4848484,382634876";
        Contatto c = new Contatto(nome, email, tels);
        assertEquals(nome, c.getNome());
        assertEquals(email, c.getEMail());
        assertEquals(tels, c.numTel());
    }

    @Test
    public void testCostruttore2() {
        String nome = "Pippo";
        String email = "pippo@email.com";
        Contatto c = new Contatto(nome, email);
        assertEquals(nome, c.getNome());
        assertEquals(email, c.getEMail());
        assertEquals("", c.numTel());
    }

    @Test
    public void testGetSetEmail() {
        String nome = "Pippo";
        String email = "pippo@email.com";
        Contatto c = new Contatto(nome);
        assertEquals(nome, c.getNome());
        c.setEMail(email);
        assertEquals(email, c.getEMail());
    }

}
