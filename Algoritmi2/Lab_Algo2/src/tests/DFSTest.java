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

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DFSTest {
    GraphInterface grafo;
    GraphInterface albero;
    DFS dfsTest;

    @Test
    public void test00Create() {
        grafo = new UndirectedGraph(1);
        dfsTest = new DFS(grafo);
        assertNotNull(dfsTest);
    }

    @Test
    public void test10OneNodeVisited() throws NotAllNodesReachedException {
        grafo = new UndirectedGraph(1);
        dfsTest = new DFS(grafo);
        albero = dfsTest.getTree(0);
        int nArchi = albero.getEdgeNum();
        int nodiAlbero = albero.getOrder();
        assertEquals(0, nArchi);
        assertEquals(1, nodiAlbero);
    }

    @Test
    public void test11TwoNodesVisited() throws NotAllNodesReachedException {
        grafo = new DirectedGraph("2;0 1");
        dfsTest = new DFS(grafo);
        albero = dfsTest.getTree(0);
        int nArchi = albero.getEdgeNum();
        int nodiAlbero = albero.getOrder();
        assertEquals(1, nArchi);
        assertEquals(2, nodiAlbero);
    }

    @Test
    public void test15DFSOrder() throws NotAllNodesReachedException {
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        dfsTest = new DFS(grafo);
        albero = dfsTest.getTree(2);
        assertTrue(albero.hasEdge(2, 3) || albero.hasEdge(0, 2));
        assertTrue(albero.hasEdge(1, 3) && albero.hasEdge(0, 1));
    }

    @Test
    public void test20InitArchi() throws NotAllNodesReachedException {
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        dfsTest = new DFS(grafo);
        assertTrue(dfsTest.getTree(2).hasEdge(2, 3) || dfsTest.getTree(2).hasEdge(0, 2));
        assertTrue(dfsTest.getTree(0).hasEdge(0, 2) || dfsTest.getTree(0).hasEdge(0, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test30illegalargBig() throws NotAllNodesReachedException {
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        dfsTest = new DFS(grafo);
        dfsTest.getTree(5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test35illegalargSmall() throws NotAllNodesReachedException {
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        dfsTest = new DFS(grafo);
        dfsTest.getTree(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test40illegalargOrd() throws NotAllNodesReachedException {
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        dfsTest = new DFS(grafo);
        dfsTest.getTree(4);
    }

    @Test
    public void test50illegalargLegal() {
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        dfsTest = new DFS(grafo);
        try {
            dfsTest.getTree(0);
        } catch (IllegalArgumentException | NotAllNodesReachedException e) {
            fail("0 e' un argomento legittimo");
        }
    }

    /**
     * Metodo che testa il corretto funzionamento dell'inserimento dei nodi in post visit.
     * <p>
     * Appunto che ho messo i assertEquals() dove sono sicuro che non ci dubbi sul nodo che viene visitato in tali posizioni,
     * mentre ho messo i assertTrue() dove non sono sicuro, poiche' non so se l'algoritmo visita i nodi
     * in ordine crescente o con quale altro criterio.
     *
     * @throws NotAllNodesReachedException eccezione per i nodi non raggiunti dalla visita.
     */
    @Test
    public void testPostVisit() throws NotAllNodesReachedException {
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        dfsTest = new DFS(grafo);
        ArrayList<Integer> postVisitArray = dfsTest.getNodesInOrderPostVisit(2);
        int nNodiVisitati = postVisitArray.size();
        assertEquals(4, nNodiVisitati);
        assertTrue(postVisitArray.get(0) == 0 || postVisitArray.get(0) == 3);
        assertEquals(1, (int) postVisitArray.get(1));
        assertTrue(postVisitArray.get(2) == 0 || postVisitArray.get(2) == 3);
        assertEquals(2, (int) postVisitArray.get(3));
    }

    @Test
    public void testForestaInit() throws NotAllNodesReachedException {
        int nNodi = 5;
        grafo = new UndirectedGraph(nNodi + ";0 1;1 2;3 4");
        dfsTest = new DFS(grafo);
        assertEquals(5, dfsTest.getForest().getOrder());
        assertEquals(3, dfsTest.getForest().getEdgeNum());
    }

    @Test
    public void testForesta() throws NotAllNodesReachedException {
        int nNodi = 5;
        grafo = new UndirectedGraph(nNodi + ";0 1;1 2;3 4");
        dfsTest = new DFS(grafo);
        GraphInterface foresta = dfsTest.getForest();
        assertTrue(foresta.hasEdge(0, 1) && foresta.hasEdge(1, 2));
        assertTrue(foresta.hasEdge(3, 4));
        assertFalse(foresta.hasEdge(2, 3));
    }

    @Test
    public void testOrderOfVisitInit() {
        int nNodi = 5;
        grafo = new UndirectedGraph(nNodi + ";0 1;1 2;1 4;3 4");
        dfsTest = new DFS(grafo);
        assertEquals(nNodi, dfsTest.getNodesInOrderOfVisit(2).size());
    }

    @Test
    public void testOrderOfVisit() {
        int nNodi = 5;
        grafo = new UndirectedGraph(nNodi + ";0 1;1 2;1 4;3 4");
        dfsTest = new DFS(grafo);
        ArrayList<Integer> ordine = dfsTest.getNodesInOrderOfVisit(2);
        assertTrue(ordine.get(0)==2 && ordine.get(1)==1 && ordine.get(2)==0 && ordine.get(3)==4 && ordine.get(4)==3
                    ||       ordine.get(0)==2 && ordine.get(1)==1 && ordine.get(2)==4 && ordine.get(3)==3 && ordine.get(4)==0
        );
    }

    @Test
    public void testOrderVisitAndPostVisitInit() {
        int nNodi = 5;
        grafo = new UndirectedGraph(nNodi + ";0 1;1 2;1 4;3 4");
        dfsTest = new DFS(grafo);
        assertEquals(nNodi, dfsTest.getOrderOfVisit(2).length);
        assertEquals(nNodi, dfsTest.getOrderPostVisit(2).length);
    }

    /**
     * Non funziona la visita in post.
     */
    @Test
    public void testOrderVisitAndPostVisit() {
        int nNodi = 5;
        grafo = new UndirectedGraph(nNodi + ";0 1;1 2;1 4;3 4");
        dfsTest = new DFS(grafo);
        int[] ordineVisita = dfsTest.getOrderOfVisit(2);
        int[] ordinePostVisita = dfsTest.getOrderPostVisit(2);
        for (int i = 0; i < nNodi; i++) {
            System.out.print(ordinePostVisita[i] + "\n");
        }
        assertEquals(2, ordineVisita[0]);
        assertEquals(1, ordineVisita[1]);
        assertEquals(0, ordineVisita[2]);
        assertEquals(4, ordineVisita[3]);
        assertEquals(3, ordineVisita[4]);

        assertEquals(0, ordinePostVisita[0]);
        assertEquals(3, ordinePostVisita[1]);
        assertEquals(4, ordinePostVisita[2]);
        assertEquals(1, ordinePostVisita[3]);
        assertEquals(2, ordinePostVisita[4]);

    }

    @Test
    public void testHasDirCycleOneNode() {
        grafo = new DirectedGraph(1);
        dfsTest = new DFS(grafo);
        assertFalse(dfsTest.hasDirCycle());
    }

    @Test
    public void testHasDirCycleTwoNodes() {
        grafo = new DirectedGraph("2;0 1");
        dfsTest = new DFS(grafo);
        assertFalse(dfsTest.hasDirCycle());
    }

    @Test
    public void testHasDirCycleThreeNodesNoCycle() {
        grafo = new DirectedGraph("3;1 0;0 2;1 2");
        dfsTest = new DFS(grafo);
        assertFalse(dfsTest.hasDirCycle());
    }

    @Test
    public void testHasDirCycleThreeNodesWithCycle() {
        grafo = new DirectedGraph("3;0 2;2 1;1 0");
        dfsTest = new DFS(grafo);
        assertTrue(dfsTest.hasDirCycle());
    }

    @Test
    public void testHasDirCyclewithComplicatedGraph() {
        grafo = new DirectedGraph("5;4 0;4 1;4 2;2 3;3 4");
        dfsTest = new DFS(grafo);
        assertTrue(dfsTest.hasDirCycle());
    }

    @Test
    public void testCycleOneNode() {
        grafo = new DirectedGraph(1);
        dfsTest = new DFS(grafo);
        assertNull(dfsTest.getDirCycle());
    }

    @Test
    public void testCycleTwoNodes() {
        grafo = new DirectedGraph("2;0 1");
        dfsTest = new DFS(grafo);
        assertNull(dfsTest.getDirCycle());
    }

    @Test
    public void testCycleThreeNodesNoCycle() {
        grafo = new DirectedGraph("3;1 0;0 2;1 2");
        dfsTest = new DFS(grafo);
        assertNull(dfsTest.getDirCycle());
    }

    /**
     * NON FUNZIONA
     */
    /**
    @Test
    public void testCycleThreeNodesWithCycle() {
        grafo = new DirectedGraph("3;0 2;2 1;1 0");
        dfsTest = new DFS(grafo);
        ArrayList<Integer> ciclo = dfsTest.getDirCycle();
        for (Integer integer : ciclo) {
            System.out.println(integer);
        }
    }
    */
}