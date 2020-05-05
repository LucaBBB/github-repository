package Tests;

import Classes.BFS;
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

    @Test
    public void testCreate() {
        grafo = new DirectedGraph(3);
        bfsTest = new BFS(grafo);
        assertNotNull(bfsTest);
    }

    @Test
    public void testScoperti() {
        grafo = new DirectedGraph ("3;0 1;1 2;2 0");
        bfsTest = new BFS(grafo);
        bfsTest.getNodesInOrderOfVisit(0);
    }

    @Test
    public void testNumeroNodiVisitati1Nodo() {
        grafo = new DirectedGraph(1);
        bfsTest = new BFS(grafo);
        assertEquals(1, bfsTest.getNodesInOrderOfVisit(0).size());
    }

    @Test
    public void testNumeroNodiVisitati2Nodi() {
        grafo = new DirectedGraph("2;0 1");
        bfsTest = new BFS(grafo);
        assertEquals(2, bfsTest.getNodesInOrderOfVisit(0).size());
    }

    @Test
    public void testNumeroNodiVisitatiGenericUndirected() {
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        bfsTest = new BFS(grafo);
        assertEquals(4, bfsTest.getNodesInOrderOfVisit(2).size());
    }

    @Test
    public void testBFSOrder() {
        grafo = new UndirectedGraph("4;0 2;0 1;2 3;1 3");
        bfsTest = new BFS(grafo);
        assertTrue(bfsTest.getNodesInOrderOfVisit(2).get(2) == 0 ||
                bfsTest.getNodesInOrderOfVisit(2).get(2) == 3);
        assertFalse(bfsTest.getNodesInOrderOfVisit(2).get(2) == 1);
    }

    @Test
    public void testInitNumeroNodiVisitati() {
        grafo = new UndirectedGraph ("4;0 2;0 1;2 3;1 3");
        bfsTest = new BFS(grafo); //<<- creato una volta sola
        int numeroNodi = bfsTest.getNodesInOrderOfVisit(0).size(); // <-- prima chiamata
        assertEquals(4, numeroNodi);
        numeroNodi = bfsTest.getNodesInOrderOfVisit(2).size(); // <-- seconda chiamata
        assertEquals(4, numeroNodi);
    }

    //------------------------------------------------------------------------------------------------
    // DISTANCE
    //------------------------------------------------------------------------------------------------
    @Test
    public void testDistance1() {
        grafo = new UndirectedGraph(1);
        bfsTest = new BFS(grafo);
        int[] distance = bfsTest.getDistance(0);
        assertEquals(0, distance[0]);
    }

    @Test
    public void testDistance2() {
        grafo = new UndirectedGraph("2;0 1");
        bfsTest = new BFS(grafo);
        int[] distance = bfsTest.getDistance(0);
        assertEquals(0, distance[0]);
        assertEquals(1, distance[1]);
    }

    @Test
    public void testDistanceGeneric() {
        grafo = new UndirectedGraph("4;0 2;2 3;0 1;1 3");
        bfsTest = new BFS(grafo);
        int[] distance = bfsTest.getDistance(2);
        assertEquals(0, distance[2]);
        assertEquals(distance[0], distance[3]);
        assertEquals(1, distance[0]);
        assertEquals(2, distance[1]);
    }

    //------------------------------------------------------------------------------------------------
    // BFS TREE
    //------------------------------------------------------------------------------------------------
    @Test
    public void testDimensioneAlbero2() {
        grafo = new UndirectedGraph("2;0 1");
        bfsTest = new BFS(grafo);
        GraphInterface tree = bfsTest.bfsTree(0);
        int n = tree.getOrder();
        assertEquals(n-1, tree.getEdgeNum());
    }

    @Test
    public void testDimensioneAlbero4() {
        grafo = new UndirectedGraph("4;0 2;2 3;0 1;1 3");
        bfsTest = new BFS(grafo);
        GraphInterface tree = bfsTest.bfsTree(2);
        int n = tree.getOrder();
        assertEquals(n-1, tree.getEdgeNum());
    }

    @Test
    public void testArchiAlbero() {
        grafo = new UndirectedGraph("4;0 2;2 3;0 1;1 3");
        bfsTest = new BFS(grafo);
        GraphInterface tree = bfsTest.bfsTree(2);
        assertTrue(tree.hasEdge(2, 0) && tree.hasEdge(2, 3));
        assertTrue(tree.hasEdge(0,1 ) || tree.hasEdge(1, 3));
    }

    @Test
    public void testInitAlbero() {
        grafo = new UndirectedGraph("4;0 2;2 3;0 1;1 3");
        bfsTest = new BFS(grafo);
        GraphInterface tree = bfsTest.bfsTree(2);
        assertTrue(tree.hasEdge(2, 0) && tree.hasEdge(2, 3));
        GraphInterface treeBis = bfsTest.bfsTree(1);
        assertTrue(treeBis.hasEdge(2, 0 ) || treeBis.hasEdge(2, 3));
    }

    //------------------------------------------------------------------------------------------------
    // BFS ORDER OF VISIT
    //------------------------------------------------------------------------------------------------
    @Test
    public void testOrderOfVisitGenerico() {
        grafo = new UndirectedGraph("4;0 2;2 3;0 1;1 3");
        bfsTest = new BFS(grafo);
        int[] ordine = bfsTest.getOrderOfVisit(2);
        assertEquals(1, ordine[0]);
        assertEquals(3, ordine[1]);
        assertEquals(0, ordine[2]);
        assertEquals(2, ordine[3]);
    }

    //------------------------------------------------------------------------------------------------
    // SHORTEST PATH
    //------------------------------------------------------------------------------------------------
    @Test
    public void testShortestPath1Node() {
        grafo = new UndirectedGraph(1);
        bfsTest = new BFS(grafo);
        assertNull(bfsTest.getShortestPath(0, 0));
    }

    @Test
    public void testShortestPath2Nodes() {
        grafo = new UndirectedGraph("2;0 1");
        bfsTest = new BFS(grafo);
        assertEquals(1, bfsTest.getShortestPath(0, 1).size());
    }

    @Test
    public void testShortestPathGeneric() {
        grafo = new UndirectedGraph("6;0 1;0 2;1 3;3 4;3 5;4 5");
        bfsTest = new BFS(grafo);
        ArrayList<Edge> shortestPath = bfsTest.getShortestPath(0, 5);
        Edge e = new Edge(5, 3);
        assertEquals(e, shortestPath.get(0));
    }

    //------------------------------------------------------------------------------------------------
    // CONNESSIONE DI GRAFI NON ORIENTATI E COMPONENTI CONNESSE
    //------------------------------------------------------------------------------------------------
    @Test
    public void testIsConnectedOneNode() {
        grafo = new UndirectedGraph(1);
        bfsTest = new BFS(grafo);
        assertTrue(bfsTest.isConnected());
    }

    @Test
    public void testIsConnectedTwoNodesConnected() {
        grafo = new UndirectedGraph("2;0 1");
        bfsTest = new BFS(grafo);
        assertTrue(bfsTest.isConnected());
    }

    @Test
    public void testIsConnectedTwoNodesNotConnected() {
        grafo = new UndirectedGraph("2");
        bfsTest = new BFS(grafo);
        assertFalse(bfsTest.isConnected());
    }

    @Test
    public void testCCOneNode() {
        grafo = new UndirectedGraph(1);
        bfsTest = new BFS(grafo);
        int[] risultato = bfsTest.connectedComponents();
        assertEquals(1, risultato.length);
        assertEquals(0, risultato[0]);
    }

    @Test
    public void testCCTwoNodesConnected() {
        grafo = new UndirectedGraph("2;0 1");
        bfsTest = new BFS(grafo);
        int[] risultato = bfsTest.connectedComponents();
        assertEquals(2, risultato.length);
        assertEquals(0, risultato[0]);
        assertEquals(0, risultato[1]);
    }

    @Test
    public void testCCTwoNodesNotConnected() {
        grafo = new UndirectedGraph("2");
        bfsTest = new BFS(grafo);
        int[] risultato = bfsTest.connectedComponents();
        assertEquals(2, risultato.length);
        assertEquals(0, risultato[0]);
        assertEquals(1, risultato[1]);
    }

    @Test
    public void testCCGeneric() {
        grafo = new UndirectedGraph("10;3 0;0 1;1 2;4 5;6 7;7 8;7 9");
        bfsTest = new BFS(grafo);
        int[] risultato = bfsTest.connectedComponents();
        assertEquals(10, risultato.length);
        assertEquals(0, risultato[0]);
        assertEquals(0, risultato[1]);
        assertEquals(0, risultato[2]);
        assertEquals(0, risultato[3]);
        assertEquals(1, risultato[4]);
        assertEquals(1, risultato[5]);
        assertEquals(2, risultato[6]);
        assertEquals(2, risultato[7]);
        assertEquals(2, risultato[8]);
        assertEquals(2, risultato[9]);
    }
}