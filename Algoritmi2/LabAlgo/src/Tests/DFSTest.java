package Tests;

import Classes.DFS;
import Exceptions.NotAllNodesReachedException;
import it.uniupo.graphLib.DirectedGraph;
import it.uniupo.graphLib.GraphInterface;
import it.uniupo.graphLib.UndirectedGraph;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DFSTest {
    GraphInterface grafo;
    DFS dfsTest;

    @Test
    public void testCreate() {
        grafo = new DirectedGraph(3);
        dfsTest = new DFS(grafo);
        assertNotNull(dfsTest);
    }
    @Test
    public void testScoperti() {
        grafo = new DirectedGraph ("3;0 1;1 2;2 0");
        dfsTest = new DFS(grafo);
        dfsTest.getNodesInOrderOfVisit(0);
    }

    @Test
    public void test10OneNodeVisited() {
        grafo = new DirectedGraph(1);
        dfsTest = new DFS(grafo);
        assertEquals(1, dfsTest.getNodesInOrderOfVisit(0).size());
    }

    @Test
    public void test11TwoNodeVisited() {
        grafo = new DirectedGraph("2;0 1");
        dfsTest = new DFS(grafo);
        assertEquals(2, dfsTest.getNodesInOrderOfVisit(0).size());
    }

    @Test
    public void test15DFSOrder() {
        grafo = new UndirectedGraph("4;0 2;2 3;0 1;1 3");
        dfsTest = new DFS(grafo);
        ArrayList<Integer> risultato = dfsTest.getNodesInOrderOfVisit(2);
        assertEquals(4, risultato.size());
        assertEquals(2, (int) risultato.get(0));
        assertEquals(0, (int) risultato.get(1));
        assertEquals(1, (int) risultato.get(2));
        assertEquals(3, (int) risultato.get(3));
    }

    @Test
    public void testInitNumeroNodiVisitati() {
        grafo = new UndirectedGraph ("4;0 2;0 1;2 3;1 3");
        dfsTest = new DFS(grafo); //<<- creato una volta sola
        int numeroNodi = dfsTest.getNodesInOrderOfVisit(0).size(); // <-- prima chiamata
        assertEquals(4, numeroNodi);
        numeroNodi = dfsTest.getNodesInOrderOfVisit(2).size(); // <-- seconda chiamata
        assertEquals(4, numeroNodi);
    }

    //------------------------------------------------------------------------------------------------
    // DFS TREE
    //------------------------------------------------------------------------------------------------
    @Test
    public void testNumeroArchi2() throws NotAllNodesReachedException {
        grafo = new UndirectedGraph ("2;0 1");
        dfsTest = new DFS(grafo);
        GraphInterface albero = dfsTest.dfsTree(0);
        int nNodi = albero.getOrder();
        assertEquals(nNodi-1, albero.getEdgeNum());
    }

    @Test
    public void testNumeroArchi4() throws NotAllNodesReachedException {
        grafo = new UndirectedGraph ("4;0 2;0 1;2 3;1 3");
        dfsTest = new DFS(grafo);
        GraphInterface albero = dfsTest.dfsTree(2);
        int nNodi = albero.getOrder();
        assertEquals(nNodi-1, albero.getEdgeNum());
    }

    @Test
    public void testArchiDFS() throws NotAllNodesReachedException {
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        dfsTest = new DFS(grafo);
        GraphInterface albero = dfsTest.dfsTree(2);
        assertTrue( (albero.hasEdge(2, 0) && albero.hasEdge(0, 1) && albero.hasEdge(1, 3))
                    || (albero.hasEdge(2, 3) && albero.hasEdge(3, 1) && albero.hasEdge(1, 0)));
        assertFalse( (albero.hasEdge(1, 0) && albero.hasEdge(0, 2) && albero.hasEdge(2, 3))
                || (albero.hasEdge(0, 2) && albero.hasEdge(2, 3) && albero.hasEdge(3, 1)));
    }

    @Test
    public void testInitAlbero() throws NotAllNodesReachedException {
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        dfsTest = new DFS(grafo);
        GraphInterface albero = dfsTest.dfsTree(2);
        assertTrue( (albero.hasEdge(2, 0) && albero.hasEdge(0, 1) && albero.hasEdge(1, 3))
                || (albero.hasEdge(2, 3) && albero.hasEdge(3, 1) && albero.hasEdge(1, 0)));
        albero = dfsTest.dfsTree(1);
        assertTrue( (albero.hasEdge(1, 0) && albero.hasEdge(0, 2) && albero.hasEdge(2, 3))
                || (albero.hasEdge(1, 3) && albero.hasEdge(3, 2) && albero.hasEdge(2, 0)));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testEccezioneModo1() throws NotAllNodesReachedException {
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        dfsTest = new DFS(grafo);
        dfsTest.dfsTree(-1);
    }

    @Test
    public void testEccezioneModo2() throws NotAllNodesReachedException {
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        dfsTest = new DFS(grafo);
        try {
            dfsTest.dfsTree(-3);
            fail("Avrebbe dovuto lanciare un’eccezione");
        }
        catch (IllegalArgumentException e) {}
        catch (Exception e) {
            fail("Avrebbe dovuto lanciare una IllegalArgumentException e non una " + e);
        }
    }

    /**
    @Test (expected = NotAllNodesReachedException.class)
    public void testEccezioneNodiNonRaggiunti() throws NotAllNodesReachedException {
        grafo = new UndirectedGraph("6;0 1;1 2;2 3;3 0;4 5");
        dfsTest = new DFS(grafo);
        dfsTest.dfsTree(0);
    }*/

    //------------------------------------------------------------------------------------------------
    // Esercizio 2: ordine di fine visita
    //------------------------------------------------------------------------------------------------
    @Test
    public void testLunghezzaOFV() {
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        dfsTest = new DFS(grafo);
        assertEquals(4, dfsTest.getNodesInOrderPostVisit(2).size());
    }

    @Test
    public void testOFV2Nodi() {
        grafo = new UndirectedGraph("2;0 1");
        dfsTest = new DFS(grafo);
        ArrayList<Integer> OFV = dfsTest.getNodesInOrderPostVisit(0);
        assertEquals(1, (int) OFV.get(0));
        assertEquals(0, (int) OFV.get(1));
    }

    @Test
    public void testOFV4Nodi() {
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        dfsTest = new DFS(grafo);
        ArrayList<Integer> OFV = dfsTest.getNodesInOrderPostVisit(2);
        assertTrue(OFV.get(0)==0 || OFV.get(0)==3);
        assertEquals(1, (int) OFV.get(1));
        assertTrue(OFV.get(2)==3 || OFV.get(2)==0);
        assertEquals(2, (int) OFV.get(3));
    }


    //------------------------------------------------------------------------------------------------
    // Esercizio 3: visita DFS completa
    //------------------------------------------------------------------------------------------------
    @Test
    public void testDimensioneForesta() {
        grafo = new UndirectedGraph("5;0 2;2 1;3 4");
        dfsTest = new DFS(grafo);
        assertEquals(5, dfsTest.getForest().getOrder());
        assertEquals(3, dfsTest.getForest().getEdgeNum());
    }

    @Test
    public void testForestaUndirectedGraph() {
        grafo = new UndirectedGraph("5;0 2;2 1;3 4");
        dfsTest = new DFS(grafo);
        GraphInterface foresta = dfsTest.getForest();
        assertTrue(foresta.hasEdge(0, 2) && foresta.hasEdge(2, 1) && foresta.hasEdge(3, 4));
        assertFalse(foresta.hasEdge(2, 3));
    }

    @Test
    public void testForestaDirectedGraph() {
        grafo = new UndirectedGraph("5;0 2;1 2;3 4");
        dfsTest = new DFS(grafo);
        GraphInterface foresta = dfsTest.getForest();
        assertTrue(foresta.hasEdge(0, 2) && foresta.hasEdge(1, 2) && foresta.hasEdge(3, 4));
        assertFalse(foresta.hasEdge(2, 3));
    }


    //------------------------------------------------------------------------------------------------
    // CICLI IN GRAFI ORIENTATI
    //------------------------------------------------------------------------------------------------
    @Test
    public void testHasCycle1() {
        grafo = new DirectedGraph(1);
        dfsTest = new DFS(grafo);
        assertFalse(dfsTest.hasDirCycle());
    }

    @Test
    public void testHasCycle2() {
        grafo = new DirectedGraph("2;0 1");
        dfsTest = new DFS(grafo);
        assertFalse(dfsTest.hasDirCycle());
    }

    @Test
    public void testHasCycle3() {
        grafo = new DirectedGraph("3;1 0;0 2;1 2");
        dfsTest = new DFS(grafo);
        assertFalse(dfsTest.hasDirCycle());
    }

    @Test
    public void testHasCycle4() {
        grafo = new DirectedGraph("3;0 2;2 1;1 0");
        dfsTest = new DFS(grafo);
        assertTrue(dfsTest.hasDirCycle());
    }

    @Test
    public void testHasCycleGeneric() {
        grafo = new DirectedGraph("5;4 0;4 1;4 2;2 3;3 4");
        dfsTest = new DFS(grafo);
        assertTrue(dfsTest.hasDirCycle());
    }

    @Test
    public void testCyclePath1() {
        grafo = new DirectedGraph(1);
        dfsTest = new DFS(grafo);
        assertNull(dfsTest.getDirCycle());
    }

    @Test
    public void testCyclePath2() {
        grafo = new DirectedGraph("2;0 1");
        dfsTest = new DFS(grafo);
        assertNull(dfsTest.getDirCycle());
    }

    @Test
    public void testCyclePath3() {
        grafo = new DirectedGraph("3;1 0;0 2;1 2");
        dfsTest = new DFS(grafo);
        assertNull(dfsTest.getDirCycle());
    }

    @Test
    public void testCyclePath4() {
        grafo = new DirectedGraph("3;0 2;2 1;1 0");
        dfsTest = new DFS(grafo);
        ArrayList<Integer> risultato = dfsTest.getDirCycle();
        assertNotNull(risultato);
        assertEquals(1, (int)risultato.get(0));
        assertEquals(2, (int)risultato.get(1));
        assertEquals(0, (int)risultato.get(2));
    }


    @Test
    public void testCyclePathGeneric() {
        grafo = new DirectedGraph("5;4 0;4 1;4 2;2 3;3 4");
        dfsTest = new DFS(grafo);
        ArrayList<Integer> risultato = dfsTest.getDirCycle();
        assertNotNull(risultato);
        assertEquals(4, (int)risultato.get(0));
        assertEquals(3, (int)risultato.get(1));
        assertEquals(2, (int)risultato.get(2));
    }

    //------------------------------------------------------------------------------------------------
    // ORDINE TOPOLOGICO
    //------------------------------------------------------------------------------------------------
    @Test
    public void testTopologicalOrder2Nodes() {
        grafo = new DirectedGraph("2;0 1");
        dfsTest = new DFS(grafo);
        ArrayList<Integer> risultato = dfsTest.topologicalOrder();
        assertEquals(2, risultato.size());
        assertEquals(0, (int)risultato.get(0));
        assertEquals(1, (int)risultato.get(1));
    }

    @Test
    public void testTopologicalOrderGeneric() {
        grafo = new DirectedGraph("4;3 1;3 0;1 0;1 2;0 2");
        dfsTest = new DFS(grafo);
        ArrayList<Integer> risultato = dfsTest.topologicalOrder();
        assertEquals(4, risultato.size());
        assertEquals(3, (int)risultato.get(0));
        assertEquals(1, (int)risultato.get(1));
        assertEquals(0, (int)risultato.get(2));
        assertEquals(2, (int)risultato.get(3));
    }
}
