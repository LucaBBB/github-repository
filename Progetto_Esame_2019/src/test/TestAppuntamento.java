package test;

import classes.Appuntamento;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestAppuntamento {
    /**
     * Metodo utile per testare la creazione di un oggetto Appuntamento e per verificare il corretto funzionamento
     * dei metodi get della classe Appuntamento.
     */
    @Test
    public void testCreazioneAppuntamento() {
        String data = "01-01-2019";
        String ora = "10:00";
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

}
