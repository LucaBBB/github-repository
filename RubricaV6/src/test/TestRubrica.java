package test;

import exception.DimException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import rubrica.Contatto;
import rubrica.Rubrica;
import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author Luca Borsalino
 * Date: 2019/12/07
 *
 * Versione con aggiunta della lettura e della scrittura da/su file.
 * Aggiunti anche i test (funzionanti) per i due metodi aggiunti.
 */
public class TestRubrica {
    private Random rand = new Random();
    private Rubrica rubrica;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void testCreazione() {
        int numRubricheOld = Rubrica.getNumRubriche();
        String nome = "Rubrica " + rand.nextInt();
        int maxDim = 3;
        rubrica = new Rubrica(maxDim, nome);
        assertEquals(numRubricheOld+1, Rubrica.getNumRubriche());
        assertEquals(maxDim, rubrica.MAX_DIM);
    }


    /**
     * Test dei vari tipi di aggiunta ed in più test per aggiunta oltre MAX_DIM o con contatto gia' presente.
     * Infine verifica del numero di elementi presenti.
     */
    @Test
    public void testAggiungi() throws DimException {
        Contatto c = new Contatto("Giovanni", "giovanni@email.com", "272727,1010202");
        assertEquals(1, rubrica.aggiungi(c));
        assertEquals(1, rubrica.aggiungiNET("Marco", "marcolino98@email.com", "7454774"));
        assertEquals(0, rubrica.aggiungiNE("Marco", "ciaomamma@email.com"));
        assertEquals(1, rubrica.aggiungiN("Giovannino"));
        //assertEquals(-1, rubrica.aggiungiN("Calogero"));
        assertEquals(3, rubrica.numEl());
    }

    @Test
    public void testAggiungiException() throws DimException {
        exception.expect(DimException.class);
        exception.expectMessage("Dimensione non valida!");
        Contatto c = new Contatto("Giovanni", "giovanni@email.com", "272727,1010202");
        rubrica.aggiungi(c);
        rubrica.aggiungiNET("Marco", "marcolino98@email.com", "7454774");
        rubrica.aggiungiNE("Marco", "ciaomamma@email.com");
        rubrica.aggiungiN("Giovannino");
        rubrica.aggiungiN("Calogero");
    }

    /**
     * Test della ricerca per nome e della ricerca per email
     */
    @Test
    public void testRicerca() throws DimException {
        testAggiungi(); // Aggiunge 3 elementi alla rubrica di MAX_DIM==3
        assertEquals(1, rubrica.cercaPerNome("M").size());
        assertEquals(1, rubrica.cercaPerEmail("g").size());
    }

    /**
     * Test della ricerca per l'eliminazione per nome e per l'eliminazione per email
     */
    @Test
    public void testElimina() throws DimException {
        testAggiungi(); // Aggiunge 3 elementi alla rubrica di MAX_DIM==3
        assertTrue(rubrica.eliminaPerNome("G"));
        assertFalse(rubrica.eliminaPerNome("F"));
        assertTrue(rubrica.eliminaPerEmail("m"));
        assertFalse(rubrica.eliminaPerEmail("c"));
    }

    /**
     * Test per la dimensione della rubrica
     */
    @Test
    public void testSize() throws DimException {
        testAggiungi(); // Aggiunge 3 elementi alla rubrica di MAX_DIM==3
        assertEquals(3, rubrica.numEl());
    }

    /**
     * Test per verificare il corretto funzionamento dei metodi che devono leggere e scrivere su file.
     * Si verifica che il numero di righe lette sia uguale al numero di nuovi oggetti Contatto creati ed inseriti nell'
     * ArrayList di oggetti Contatti.
     * In seguito, si verifica che il numero di oggetti di tipo Contatto sia uguale al numero di oggetti che erano pre-
     * -senti nell'ArrayList.
     * @throws IOException gestione dell'eccezione per l'IOException.
     */
    @Test
    public void testFileIO() throws IOException {
        int letti = rubrica.leggiContatti("fileContatti");
        assertEquals(letti-1, rubrica.numEl());
        int scritti = rubrica.scriviContatti("nuovoFile");
        assertEquals(rubrica.numEl(), scritti);
    }

    /**
     * Test che parte dopo tutti e fa il reset della rubrica
     */
    @After
    public void resetRubrica() {}
}
