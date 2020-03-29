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
import it.uniupo.graphLib.Edge;
import it.uniupo.graphLib.GraphInterface;
import it.uniupo.graphLib.UndirectedGraph;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BFSTest {

    GraphInterface grafo;
    BFS bfsTest;
    int[] distanze;

    /**
     * Test sulla creazione di un grafo.
     */
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
        assertTrue(bfsTest.getNodesInOrderOfVisit(2).get(2) == 0 || bfsTest.getNodesInOrderOfVisit(2).get(2) == 3);
        //assertFalse(bfsTest.getNodesInOrderOfVisit(2).get(2) == 1); Scritto nel modo sotto cosi' che l'IDE non desse warning.
        assertNotEquals(1, (int) bfsTest.getNodesInOrderOfVisit(2).get(2));
    }

    /**
     * Metodo che testa la lunghezza dopo le due chiamate.
     */
    @Test
    public void testInitNumeroNodiVisitati() {
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        bfsTest = new BFS(grafo); //<<- creato una volta sola
        int numeroNodi = bfsTest.getNodesInOrderOfVisit(0).size();//<<-prima chiamata del metodo
        assertEquals(4, numeroNodi);
        numeroNodi = bfsTest.getNodesInOrderOfVisit(2).size(); //<<-seconda chiamata, stesso oggetto,
        assertEquals(4, numeroNodi);
    }

    @Test
    public void testInitOrdineNodi() {
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        bfsTest = new BFS(grafo); //<<- creato una volta sola
        assertTrue(bfsTest.getNodesInOrderOfVisit(2).get(2) != 1);
        assertTrue(bfsTest.getNodesInOrderOfVisit(1).get(2) != 0);
        assertTrue(bfsTest.getNodesInOrderOfVisit(0).get(2) != 3);
    }

    /**
     * Metodo che testa la distanza in un grafo con un solo nodo.
     */
    @Test
    public void testDistanza1() {
        grafo = new UndirectedGraph(1);
        bfsTest = new BFS(grafo);
        distanze = bfsTest.getDistance(0);
        assertEquals(1, distanze.length);
        assertEquals(0, distanze[0]);
    }

    /**
     * Metodo che testa la distanza in un grafo con due nodi ed un arco che gli unisce.
     */
    @Test
    public void testDistanza2() {
        grafo = new UndirectedGraph("2;0 1");
        bfsTest = new BFS(grafo);
        distanze = bfsTest.getDistance(0);
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
        assertEquals(albero.getOrder() - 1, albero.getEdgeNum());
    }

    @Test
    public void testArchiAlbero() {
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        bfsTest = new BFS(grafo);
        GraphInterface albero = bfsTest.bfsTree(0);
        assertTrue(albero.hasEdge(2, 0) && albero.hasEdge(2, 3));
        assertTrue(albero.hasEdge(0, 1) || albero.hasEdge(1, 3));
    }

    @Test
    public void testInitAlbero() {
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        bfsTest = new BFS(grafo);
        assertTrue(bfsTest.bfsTree(0).hasEdge(2, 0) && bfsTest.bfsTree(0).hasEdge(2, 3));
        assertTrue(bfsTest.bfsTree(1).hasEdge(2, 0) || bfsTest.bfsTree(1).hasEdge(2, 3));
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
     * Test shortest path su grafo con un solo nodo.
     */
    @Test
    public void testShortestPathOneNode() {
        grafo = new UndirectedGraph(1);
        bfsTest = new BFS(grafo);
        ArrayList<Edge> shortestPath = bfsTest.getShortestPath(0, 0);
        assertNull(shortestPath);
    }

    /**
     * Test shortest path su grafo con due nodi.
     */
    @Test
    public void testShortestPathTwoNodes() {
        grafo = new UndirectedGraph("2;0 1");
        bfsTest = new BFS(grafo);
        ArrayList<Edge> shortestPath = bfsTest.getShortestPath(0, 1);
        assertEquals(1, shortestPath.size());
    }

    /**
     *
     * Test shortest path su grafo con piu' nodi.
     *              0
     *             / \
     *            1   2
     *           /\   \
     *          3 4   5
     *         /  \
     *        6    7
     */
    @Test
    public void testShortestPathMoreNodes() {
        grafo = new UndirectedGraph("8;0 1;0 2;1 3;1 4;2 5;3 6;4 7");
        bfsTest = new BFS(grafo);
        ArrayList<Edge> shortestPath = bfsTest.getShortestPath(0, 7);
        assertEquals(3, shortestPath.size());
        assertEquals(new Edge(7, 4), shortestPath.get(0));
        assertEquals(new Edge(4, 1), shortestPath.get(1));
        assertEquals(new Edge(1, 0), shortestPath.get(2));
    }

    @Test
    public void testCCWithOneNode() {
        grafo = new UndirectedGraph(1);
        bfsTest = new BFS(grafo);
        int[] cc = bfsTest.connectedComponents();
        assertEquals(1, cc.length);
        assertEquals(0, cc[0]);
    }

    @Test
    public void testCCWithTwoNodeConnected() {
        grafo = new UndirectedGraph("2;0 1");
        bfsTest = new BFS(grafo);
        int[] cc = bfsTest.connectedComponents();
        assertEquals(0, cc[0]);
        assertEquals(0, cc[1]);
    }

    @Test
    public void testCCWithTwoNodeNotConnected() {
        grafo = new UndirectedGraph("2");
        bfsTest = new BFS(grafo);
        int[] cc = bfsTest.connectedComponents();
        assertEquals(0, cc[0]);
        assertEquals(1, cc[1]);
    }

    @Test
    public void testCCGeneric() {
        grafo = new UndirectedGraph("9;0 1;2 3;2 4;5 6;5 7;6 7;5 8");
        bfsTest = new BFS(grafo);
        int[] cc = bfsTest.connectedComponents();
        assertEquals(0, cc[0]);
        assertEquals(0, cc[1]);
        assertEquals(1, cc[2]);
        assertEquals(1, cc[3]);
        assertEquals(1, cc[4]);
        assertEquals(2, cc[5]);
        assertEquals(2, cc[6]);
        assertEquals(2, cc[7]);
        assertEquals(2, cc[8]);
    }

    @Test
    public void testIsConnectedOneNode() {
        grafo = new UndirectedGraph(1);
        bfsTest = new BFS(grafo);
        assertTrue(bfsTest.isConnected());
    }

    @Test
    public void testIsConnectedTwoNodeConnected() {
        grafo = new UndirectedGraph("2;0 1");
        bfsTest = new BFS(grafo);
        assertTrue(bfsTest.isConnected());
    }

    @Test
    public void testIsConnectedTwoNodeNotConnected() {
        grafo = new UndirectedGraph("2");
        bfsTest = new BFS(grafo);
        assertFalse(bfsTest.isConnected());
    }

    @Test
    public void testIsConnectedGeneric() {
        grafo = new UndirectedGraph("9;0 1;2 3;2 4;5 6;5 7;6 7;5 8");
        bfsTest = new BFS(grafo);
        assertFalse(bfsTest.isConnected());
    }

    @Test
    public void testHasUndirectedCycleOneNode() {
        grafo = new UndirectedGraph(1);
        bfsTest = new BFS(grafo);
        assertFalse(bfsTest.hasUndirectedCycle());
    }

    @Test
    public void testHasUndirectedCycleTwoNodes() {
        grafo = new UndirectedGraph("2;0 1");
        bfsTest = new BFS(grafo);
        assertFalse(bfsTest.hasUndirectedCycle());
    }

    @Test
    public void testHasUndirectedCycleGeneric() {
        grafo = new UndirectedGraph("9;0 1;2 3;2 4;5 6;5 7;6 7;5 8");
        bfsTest = new BFS(grafo);
        assertTrue(bfsTest.hasUndirectedCycle());
    }
}
