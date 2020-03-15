package tests;

import classes.BFS;
import it.uniupo.graphLib.DirectedGraph;
import it.uniupo.graphLib.GraphInterface;
import it.uniupo.graphLib.UndirectedGraph;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BFSTest {

    @Test
    public void testCreate() {
        GraphInterface grafo = new DirectedGraph(3);
        BFS bfsTest = new BFS(grafo);
        assertNotNull(bfsTest);
    }

    /**
     * Metodo che testa se su un grafo con un solo nodo
     * l'algoritmo di visita restituisca un array di di lunghezza pari a uno.
     */
    @Test
    public void testScoperti1() {
        GraphInterface grafo = new UndirectedGraph(1);
        BFS bfsTest = new BFS(grafo);
        assertEquals(1, bfsTest.getNodesInOrderOfVisit(0).size());
    }

    /**
     * Metodo che testa se su un grafo non orientato con due nodi
     * l'algoritmo di visita restituisca un array di lunghezza pari a due.
     */
    @Test
    public void testScoperti2() {
        GraphInterface grafo = new UndirectedGraph("2;0 1");
        BFS bfsTest = new BFS(grafo);
        assertEquals(2, bfsTest.getNodesInOrderOfVisit(0).size());
    }

    /**
     * Metodo che testa su un grafo non orientato generico con 4 nodi
     * che l'algoritmo restituisca un array di lunghezza pari a quattro.
     */
    @Test
    public void testScopertiGenerico() {
        GraphInterface grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        BFS bfsTest = new BFS(grafo);
        assertEquals(4, bfsTest.getNodesInOrderOfVisit(2).size());
    }

    /**
     * Metodo che testa il corretto funzionamento dell'algoritmo di visita.
     */
    @Test
    public void testBFSOrder() {
        GraphInterface grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        BFS bfsTest = new BFS(grafo);
        assertTrue(bfsTest.getNodesInOrderOfVisit(2).get(0) == 2);
        assertTrue(bfsTest.getNodesInOrderOfVisit(2).get(1) == 0);
        assertTrue(bfsTest.getNodesInOrderOfVisit(2).get(2) == 3);
        assertTrue(bfsTest.getNodesInOrderOfVisit(2).get(3) == 1);
        assertFalse(bfsTest.getNodesInOrderOfVisit(2).get(2) == 1);
    }

    /**
     * Metodo che testa la lunghezza dopo le due chiamate.
     */
    @Test
    public void testInitNumeroNodiVisitati() {
        GraphInterface grafo = new UndirectedGraph ("4;0 2;0 1;2 3;1 3");
        BFS bfsTest = new BFS(grafo); //<<- creato una volta sola
        int numeroNodi = bfsTest.getNodesInOrderOfVisit(0).size();//<<-prima chiamata del metodo
        assertEquals(4, numeroNodi);
        numeroNodi = bfsTest.getNodesInOrderOfVisit(2).size(); //<<-seconda chiamata, stesso oggetto,
        assertEquals(4, numeroNodi);
    }

    /**
     * Metodo che testa la distanza in un grafo con un solo nodo.
     */
    @Test
    public void testDistanza1() {
        GraphInterface grafo = new UndirectedGraph(1);
        BFS bfsTest = new BFS(grafo);
        assertEquals(0, bfsTest.getDistance(0)[0]);
    }

    /**
     * Metodo che testa la distanza in un grafo con due nodi ed un arco che gli unisce.
     */
    @Test
    public void testDistanza2() {
        GraphInterface grafo = new UndirectedGraph("2;0 1");
        BFS bfsTest = new BFS(grafo);
        int[] distanze = bfsTest.getDistance(0);
        assertEquals(0, distanze[0]);
        assertEquals(1, distanze[1]);
    }

    /**
     * Metodo che testa su un grafo non orientato generico il corretto funzionamento del
     * calcolo della distanza dalla sorgente di un nodo.
     */
    @Test
    public void testDistanzaGenerico() {
        GraphInterface grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        BFS bfsTest = new BFS(grafo);
        int[] distanze = bfsTest.getDistance(0);
        assertEquals(0, distanze[0]);
        assertEquals(1, distanze[1]);
        assertEquals(1, distanze[2]);
        assertEquals(2, distanze[3]);
    }

    public void printArray(ArrayList<Integer> daStampare) {
        for (Integer integer : daStampare) {
            System.out.print(integer + " ");
        }
        System.out.println("");
    }

    public void printDistanze(int[] distanza) {
        for (int i=0; i<distanza.length; i++) {
            System.out.println("distanza[" + i + "] = " + distanza[i]);
        }
    }
}
