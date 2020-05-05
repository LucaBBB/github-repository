package Tests;

import Classes.SoftwareSystem;
import it.uniupo.graphLib.DirectedGraph;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestSoftwareSystem {
    SoftwareSystem ssytest;
    DirectedGraph grafo;

    @Test
    public void testHasSofSysCycleTrue() {
        grafo = new DirectedGraph("5;0 1;2 0;2 1;2 3;3 4;4 2");
        ssytest = new SoftwareSystem(grafo);
        assertTrue(ssytest.hasCycle());
    }

    @Test
    public void testHasSofSysCycleFalse() {
        grafo = new DirectedGraph("5;3 4;4 2;3 2");
        ssytest = new SoftwareSystem(grafo);
        assertFalse(ssytest.hasCycle());
    }
}
