package Classes;

import it.uniupo.graphLib.DirectedGraph;
import it.uniupo.graphLib.GraphInterface;

public class SoftwareSystem {
    DirectedGraph grafo;
    DFS dfsClass;

    public SoftwareSystem(DirectedGraph system) {
        grafo = system;
        dfsClass = new DFS(grafo);
    }

    public boolean hasCycle() {
        return dfsClass.hasDirCycle();
    }
}
