/*
  @author Luca Borsalino
 * date: 2020-03-15
 *
 * Prima esercitazione di laboratorio di Algoritmi2
 * Universita' del Piemonte Orientale (Alessandria) DISIT / Informatica.
 */
package tests;

import classes.BFS;
import it.uniupo.graphLib.DirectedGraph;
import it.uniupo.graphLib.GraphInterface;
import it.uniupo.graphLib.UndirectedGraph;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BFSTest {

    GraphInterface grafo;
    BFS bfsTest;

    @Test
    public void testCreate() {
        grafo = new DirectedGraph(3);
        bfsTest = new BFS(grafo);
        assertNotNull(bfsTest);
    }

    /**
     * Metodo che testa se su un grafo con un solo nodo
     * l'algoritmo di visita restituisca un array di di lunghezza pari a uno.
     */
    @Test
    public void testScoperti1() {
        grafo = new UndirectedGraph(1);
        bfsTest = new BFS(grafo);
        assertEquals(1, bfsTest.getNodesInOrderOfVisit(0).size());
    }

    /**
     * Metodo che testa se su un grafo non orientato con due nodi
     * l'algoritmo di visita restituisca un array di lunghezza pari a due.
     */
    @Test
    public void testScoperti2() {
        grafo = new UndirectedGraph("2;0 1");
        bfsTest = new BFS(grafo);
        assertEquals(2, bfsTest.getNodesInOrderOfVisit(0).size());
    }

    /**
     * Metodo che testa su un grafo non orientato generico con 4 nodi
     * che l'algoritmo restituisca un array di lunghezza pari a quattro.
     */
    @Test
    public void testScopertiGenerico() {
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        bfsTest = new BFS(grafo);
        assertEquals(4, bfsTest.getNodesInOrderOfVisit(2).size());
    }

    /**
     * Metodo che testa il corretto funzionamento dell'algoritmo di visita.
     */
    @Test
    public void testBFSOrder() {
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        bfsTest = new BFS(grafo);
        assertTrue(bfsTest.getNodesInOrderOfVisit(2).get(2)==0 || bfsTest.getNodesInOrderOfVisit(2).get(2)==3);
        assertNotEquals(1, (int) bfsTest.getNodesInOrderOfVisit(2).get(2));
    }

    /**
     * Metodo che testa la lunghezza dopo le due chiamate.
     */
    @Test
    public void testInitNumeroNodiVisitati() {
        grafo = new UndirectedGraph ("4;0 2;0 1;2 3;1 3");
        bfsTest = new BFS(grafo); //<<- creato una volta sola
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
        grafo = new UndirectedGraph(1);
        bfsTest = new BFS(grafo);
        assertEquals(0, bfsTest.getDistance(0)[0]);
    }

    /**
     * Metodo che testa la distanza in un grafo con due nodi ed un arco che gli unisce.
     */
    @Test
    public void testDistanza2() {
        grafo = new UndirectedGraph("2;0 1");
        bfsTest = new BFS(grafo);
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
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        bfsTest = new BFS(grafo);
        int[] distanze = bfsTest.getDistance(0);
        assertEquals(0, distanze[0]);
        assertEquals(1, distanze[1]);
        assertEquals(1, distanze[2]);
        assertEquals(2, distanze[3]);
    }

    /**
     * Metodo che testa il corretto funzionamento della creazione di un albero con 2 nodi
     * verificando che il numero di nodi sia corretto cosi' come il numero di archi
     * (nArchi = nNodi-1).
     */
    @Test
    public void testDimensioneAlbero2() {
        grafo = new UndirectedGraph("2;0 1");
        bfsTest = new BFS(grafo);
        GraphInterface albero = bfsTest.bfsTree(0);
        assertEquals(2, albero.getOrder());
        assertEquals(1, albero.getEdgeNum());
    }

    /**
     * Metodo che testa il corretto funzionamento della creazione di un albero con 4 nodi
     * verificando che il numero di nodi sia corretto cosi' come il numero di archi
     * (nArchi = nNodi-1).
     */
    @Test
    public void testDimensioneAlbero4() {
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        bfsTest = new BFS(grafo);
        GraphInterface albero = bfsTest.bfsTree(0);
        assertEquals(4, albero.getOrder());
        assertEquals(albero.getOrder()-1, albero.getEdgeNum());
    }

    /**
     * Metodo che testa il corretto funzionamento della creazione di un'array contenente l'ordine di visita
     * dei nodi.
     */
    @Test
    public void testOrdineDiVisita() {
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        bfsTest = new BFS(grafo);
        int[] ordineDiVisita = bfsTest.getOrderOfVisit(2);
        assertEquals(0, ordineDiVisita[2]);
        assertEquals(1, ordineDiVisita[0]);
        assertEquals(2, ordineDiVisita[3]);
        assertEquals(3, ordineDiVisita[1]);
    }

    /**
     * Metodo che testa il corretto funzionamento della creazione dell'ArrayList contenente il cammino minimo dalla
     * sorgente ad un nodo v.
     */
    @Test
    public void testCamminoMinimo() {
        grafo = new UndirectedGraph("7;0 1;0 2;1 3;2 3;3 4;3 5;1 6;4 6");
        bfsTest = new BFS(grafo);
        ArrayList<Integer> camminoMinimo = bfsTest.camminoMinimo(0, 6);
        for (int i=camminoMinimo.size()-1; i>=0; i--) {
            System.out.println(camminoMinimo.get(i));
        }
        assertEquals(0, (int)camminoMinimo.get(2));
        assertEquals(1, (int)camminoMinimo.get(1));
        assertEquals(6, (int)camminoMinimo.get(0));
    }
}
