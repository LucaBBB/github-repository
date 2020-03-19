package tests;

import classes.DFS;
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
    public void test10OneNodeVisited() {
        grafo = new DirectedGraph(1);
        dfsTest = new DFS(grafo);
        albero = dfsTest.getTree(0);
        assertEquals(1, albero.getOrder());
    }

    @Test
    public void test11TwoNodeVisited() {
        grafo = new DirectedGraph("2;0 1");
        dfsTest = new DFS(grafo);
        albero = dfsTest.getTree(0);
        assertEquals(2, albero.getOrder());
    }

    @Test
    public void testScoperti() {
        grafo = new DirectedGraph("3;0 1;1 2;2 0");
        dfsTest = new DFS(grafo);
        dfsTest.getTree(0);
    }

    @Test
    public void testNumeroArchi2() {
        int nNodi = 2;
        grafo = new UndirectedGraph(nNodi + ";0 1");
        dfsTest = new DFS(grafo);
        GraphInterface albero = dfsTest.getTree(0);
        assertEquals(nNodi-1, albero.getEdgeNum());
    }

    @Test
    public void testNumeroArchi4() {
        int nNodi = 4;
        grafo = new UndirectedGraph(nNodi + ";0 2;0 1;2 3;1 3");
        dfsTest = new DFS(grafo);
        albero = dfsTest.getTree(0);
        assertEquals(nNodi-1, albero.getEdgeNum());
    }

    @Test
    public void testArchiDFS() {
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
    public void testInitAlbero() {
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
    public void testInitNumeroNodiVisitati() {
        int nNodi = 4;
        grafo = new UndirectedGraph (nNodi + ";0 2;0 1;2 3;1 3");
        dfsTest = new DFS(grafo); //<<- creato una volta sola
        assertEquals(nNodi, dfsTest.getNodesInOrderPostVisit(2).size());
    }
}
