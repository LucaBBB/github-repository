package test;

import exception.DimException;
import exception.FormatoException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import rubrica.Contatto;

import static org.junit.Assert.assertEquals;

/**
 * @author Luca Borsalino
 * Date: 2019/12/09
 *
 * Versione con aggiunta della lettura e della scrittura da/su file.
 * Aggiunti anche i test (funzionanti) per i due metodi aggiunti.
 * Aggiunta la regex per il nome, da capire perche' non funziona quella per l'email.
 */
public class TestContatto {


    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testCostruttore1() throws FormatoException {
        String nome = "Pippo";
        String email = "pippo@email.com";
        String tels = "2020202,4848484,382634876";
        Contatto c = new Contatto(nome, email, tels);
        assertEquals(nome, c.getNome());
        assertEquals(email, c.getEMail());
        assertEquals(tels, c.numTel());
    }

    @Test
    public void testCostruttore2() throws FormatoException {
        String nome = "Pippo";
        String email = "pippo@email.com";
        Contatto c = new Contatto(nome, email);
        assertEquals(nome, c.getNome());
        assertEquals(email, c.getEMail());
        assertEquals("", c.numTel());
    }

    @Test
    public void testGetSetEmail() throws FormatoException {
        String nome = "Pippo";
        String email = "pippo@email.com";
        Contatto c = new Contatto(nome);
        assertEquals(nome, c.getNome());
        c.setEMail(email);
        assertEquals(email, c.getEMail());
    }

    /**
     * Test per verificare se l'eccezione viene chiamata quando si inserisce un nome con caratteri non validi.
     * @throws FormatoException eccezione per caratteri non validi del nome.
     */
    @Test
    public void testEccezioneFormato() throws FormatoException {
        exception.expect(FormatoException.class);
        exception.expectMessage("Errore formato!");
        Contatto c = new Contatto("Pipp0", "p1ppo@email.com", "0123456789");
    }

}
