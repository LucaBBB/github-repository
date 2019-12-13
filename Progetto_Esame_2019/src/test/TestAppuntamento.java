package test;

import classes.Appuntamento;
import exception.DurataException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

/**
 * Versione 2.1
 * Aggiunta dell'eccezione per la durata troppo breve.
 */
public class TestAppuntamento {
    @Rule
    public ExpectedException exception = ExpectedException.none();
    /**
     * Metodo utile per testare la creazione di un oggetto Appuntamento e per verificare il corretto funzionamento
     * dei metodi get della classe Appuntamento.
     */
    @Test
    public void testCreazioneAppuntamento() throws DurataException {
        String data = "01-01-2019";
        String ora = "10-00";
        int durata = 30;
        String nome = "Pippo";
        String luogo = "Casa";

        Appuntamento a = new Appuntamento(data, ora, durata, nome, luogo);

        assertEquals(data, a.getDataString());
        assertEquals(ora, a.getOraString());
        assertEquals(durata, a.getDurata());
        assertEquals(nome, a.getNome());
        assertEquals(luogo, a.getLuogo());
    }

    /**
     * Test che verifica che venga lanciata l'eccezione per la durata troppo breve di un appuntamento (< 5 minuti).
     * @throws DurataException Eccezione per la durata troppo breve.
     */
    @Test
    public void testCreazioneAppuntamentoEccezioneDurata() throws DurataException {
        exception.expect(DurataException.class);
        exception.expectMessage("Durata dell'appuntamento non valida!");
        String data = "01-01-2019";
        String ora = "10-00";
        int durata = 2;
        String nome = "Pippo";
        String luogo = "Casa";
        new Appuntamento(data, ora, durata, nome, luogo);
    }

}
