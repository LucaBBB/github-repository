/*
  @author Luca Borsalino
 * date: 2020-03-22
 *
 * Seconda esercitazione di laboratorio di Algoritmi2
 * Universita' del Piemonte Orientale (Alessandria) DISIT / Informatica.
 */

package tests;

import classes.DFS;
import exception.NotAllNodesReachedException;
import it.uniupo.graphLib.DirectedGraph;
import it.uniupo.graphLib.GraphInterface;
import it.uniupo.graphLib.UndirectedGraph;
import org.junit.Test;

import static org.junit.Assert.*;

public class DFSTest {
    GraphInterface grafo;
    GraphInterface albero;
    DFS dfsTest;

    @Test
    public void testCreate() {
        grafo = new DirectedGraph(3);
        dfsTest = new DFS(grafo);
        assertNotNull(dfsTest);
    }

    @Test
    public void test10OneNodeVisited() throws NotAllNodesReachedException {
        grafo = new DirectedGraph(1);
        dfsTest = new DFS(grafo);
        albero = dfsTest.getTree(0);
        assertEquals(1, albero.getOrder());
    }

    @Test
    public void test11TwoNodeVisited() throws NotAllNodesReachedException {
        grafo = new DirectedGraph("2;0 1");
        dfsTest = new DFS(grafo);
        albero = dfsTest.getTree(0);
        assertEquals(2, albero.getOrder());
    }

    @Test
    public void testScoperti() throws NotAllNodesReachedException {
        grafo = new DirectedGraph("3;0 1;1 2;2 0");
        dfsTest = new DFS(grafo);
        dfsTest.getTree(0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testExceptionIllegalArgument() throws NotAllNodesReachedException {
        grafo = new DirectedGraph("3;0 1;1 2;2 0");
        dfsTest = new DFS(grafo);
        dfsTest.getTree(-1);
    }

    @Test
    public void testNumeroArchi2() throws NotAllNodesReachedException {
        int nNodi = 2;
        grafo = new UndirectedGraph(nNodi + ";0 1");
        dfsTest = new DFS(grafo);
        GraphInterface albero = dfsTest.getTree(0);
        assertEquals(nNodi-1, albero.getEdgeNum());
    }

    @Test
    public void testNumeroArchi4() throws NotAllNodesReachedException {
        int nNodi = 4;
        grafo = new UndirectedGraph(nNodi + ";0 2;0 1;2 3;1 3");
        dfsTest = new DFS(grafo);
        albero = dfsTest.getTree(0);
        assertEquals(nNodi-1, albero.getEdgeNum());
    }

    @Test
    public void testArchiDFS() throws NotAllNodesReachedException {
        int nArchi = 4;
        grafo = new UndirectedGraph(nArchi + ";0 2;0 1;2 3;1 3");
        dfsTest = new DFS(grafo);
        albero = dfsTest.getTree(2);
        assertTrue(albero.hasEdge(2, 0) && albero.hasEdge(0, 1) && albero.hasEdge(1, 3)
        || albero.hasEdge(2, 3) && albero.hasEdge(3, 1) && albero.hasEdge(1, 0));
        assertFalse(albero.hasEdge(2, 3) && albero.hasEdge(2, 0) && albero.hasEdge(0, 1)
        || albero.hasEdge(2, 0) && albero.hasEdge(3, 2) && albero.hasEdge(1, 3));
    }

    @Test
    public void testInitAlbero() throws NotAllNodesReachedException {
        int nNodi = 4;
        grafo = new UndirectedGraph(nNodi + ";0 2;0 1;2 3;1 3");
        dfsTest = new DFS(grafo);
        assertEquals(nNodi-1, dfsTest.getTree(2).getEdgeNum());
        assertEquals(nNodi-1, dfsTest.getTree(1).getEdgeNum());
    }

    /**
     * Metodo che testa la lunghezza dell'ArrayList della visita in post-ordine.
     */
    @Test
    public void testInitNumeroNodiVisitati() throws NotAllNodesReachedException {
        int nNodi = 4;
        grafo = new UndirectedGraph (nNodi + ";0 2;0 1;2 3;1 3");
        dfsTest = new DFS(grafo); //<<- creato una volta sola
        assertEquals(nNodi, dfsTest.getNodesInOrderPostVisit(2).size());
    }

    @Test (expected = NotAllNodesReachedException.class)
    public void testExceptionNotAllNodesReached() throws NotAllNodesReachedException {
        int nNodi = 5;
        grafo = new UndirectedGraph(nNodi + ";0 2;0 1;1 3;2 3");
        dfsTest = new DFS(grafo);
        dfsTest.getNodesInOrderPostVisit(0);
    }

    @Test
    public void testForesta() throws NotAllNodesReachedException {
        int nNodi = 5;
        grafo = new UndirectedGraph (nNodi + ";0 1;1 2;3 4");
        dfsTest = new DFS(grafo);
        assertEquals(5, dfsTest.visitaDFSCompleta().getOrder());
        assertEquals(3, dfsTest.visitaDFSCompleta().getEdgeNum());
    }

    @Test
    public void testCicliGOrientato() {
        int nNodi = 6;
        grafo = new DirectedGraph(nNodi + ";0 1;0 2;2 3;3 5;1 5;4 1");
        dfsTest = new DFS(grafo);
        assertFalse(dfsTest.DFSCicliGOrientato(0));
    }

    /**
     * Non funziona!
     */
    @Test
    public void testCicliGNOrientato() {
        int nNodi = 6;
        grafo = new UndirectedGraph(nNodi + ";0 1;0 4;1 2;1 3;2 3;4 5");
        dfsTest = new DFS(grafo);
        assertFalse(dfsTest.DFSCicliGNOrientato(0));
    }

}
