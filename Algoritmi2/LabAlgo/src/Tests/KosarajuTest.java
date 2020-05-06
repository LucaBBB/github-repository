package Tests;

import Classes.Kosaraju;
import it.uniupo.graphLib.DirectedGraph;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class KosarajuTest {
    Kosaraju kosaraju;
    DirectedGraph grafo;

    @Test
    public void postVisitListTest() {
        grafo = new DirectedGraph("5;0 3;1 0;1 3;1 4;2 1;2 4");
        kosaraju = new Kosaraju(grafo);
        ArrayList<Integer> ofv = kosaraju.postVisitList();
        assertEquals(5, ofv.size());
        assertEquals(3, (int)ofv.get(0));
        assertEquals(0, (int)ofv.get(1));
        assertEquals(4, (int)ofv.get(2));
        assertEquals(1, (int)ofv.get(3));
        assertEquals(2, (int)ofv.get(4));
    }

    @Test
    public void postVisitListTestCycle() {
        grafo = new DirectedGraph("6;0 1;1 3;3 4;4 1;1 5;0 2;2 1;2 5");
        kosaraju = new Kosaraju(grafo);
        ArrayList<Integer> ofv = kosaraju.postVisitList();
        assertEquals(6, ofv.size());
        assertEquals(4, (int)ofv.get(0));
        assertEquals(3, (int)ofv.get(1));
        assertEquals(5,  (int)ofv.get(2));
        assertEquals(1, (int)ofv.get(3));
        assertEquals(2, (int)ofv.get(4));
        assertEquals(0, (int)ofv.get(5));
    }

    @Test
    public void testLemmaPostVisit() {
        grafo = new DirectedGraph("5;2 0;0 2;1 4;4 3;3 1;1 2;4 0");
        kosaraju = new Kosaraju(grafo);
        ArrayList<Integer> ofv = kosaraju.postVisitList();
        assertEquals(5, ofv.size());
        assertEquals(2, (int)ofv.get(0));
        assertEquals(0, (int)ofv.get(1));
        assertEquals(3, (int)ofv.get(2));
        assertEquals(4, (int)ofv.get(3));
        assertEquals(1, (int)ofv.get(4));
    }

    @Test
    public void SCCTestOneNode() {
        grafo = new DirectedGraph(1);
        kosaraju = new Kosaraju(grafo);
        int[] risultato = kosaraju.getSCC();
        assertEquals(1, risultato.length);
        assertEquals(0, risultato[0]);
    }

    @Test
    public void SCCTestDAG() {
        grafo = new DirectedGraph("6;0 1;1 5;0 2;2 1;2 5;3 1;3 4;4 1");
        kosaraju = new Kosaraju(grafo);
        int[] risultato = kosaraju.getSCC();
        assertEquals(6, risultato.length);
        System.out.println("\nrisultato: ");
        for (int n : risultato) {
            System.out.print(n);
        }
    }

    @Test
    public void SCCTestCycle() {
        grafo = new DirectedGraph("4; 0 1; 1 2; 2 3; 3 0");
        kosaraju = new Kosaraju(grafo);
        int[] risultato = kosaraju.getSCC();
        assertEquals(4, risultato.length);
        assertEquals(0, risultato[0]);
        assertEquals(0, risultato[1]);
        assertEquals(0, risultato[2]);
        assertEquals(0, risultato[3]);
    }

    @Test
    public void SCCTestGeneric() {
        grafo = new DirectedGraph("5;2 0;0 2;1 4;4 3;3 1;1 2;4 0");
        kosaraju = new Kosaraju(grafo);
        int[] risultato = kosaraju.getSCC();
        assertEquals(5, risultato.length);
        assertEquals(1, risultato[0]);
        assertEquals(0, risultato[1]);
        assertEquals(1, risultato[2]);
        assertEquals(0, risultato[3]);
        assertEquals(0, risultato[4]);
    }
}
