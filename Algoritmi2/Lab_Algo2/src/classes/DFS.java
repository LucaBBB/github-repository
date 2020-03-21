package classes;

import exception.NotAllNodesReachedException;
import it.uniupo.graphLib.GraphInterface;

import java.util.ArrayList;

public class DFS {
    GraphInterface grafo;
    GraphInterface Foresta;

    public DFS(GraphInterface g) {
        this.grafo = g;
    }

    private void DFSVisit(int sorgente, boolean[] scoperti, GraphInterface albero) {
        scoperti[sorgente] = true;
        for (int vicino : grafo.getNeighbors(sorgente)) {
            if (!scoperti[vicino]) {
                albero.addEdge(sorgente, vicino);
                DFSVisit(vicino, scoperti, albero);
            }
        }
    }

    /**
     * Metodo che chiama il metodo di supporto DFSVisit, il quale crea un albero di visita DFS.
     * Infine, questo metodo restituisce l'albero creato.
     *
     * @param sorgente il nodo di partenza della visita.
     * @return l'albero di visita DFS.
     */
    public GraphInterface getTree(int sorgente) throws NotAllNodesReachedException {
        if(sorgente<0 || sorgente>=grafo.getOrder())
            throw new IllegalArgumentException();

        GraphInterface albero = grafo.create();
        boolean[] scoperti = new boolean[grafo.getOrder()];

        DFSVisit(sorgente, scoperti, albero);

        for (int i=0; i<scoperti.length; i++) {
            if(!scoperti[i])
                throw new NotAllNodesReachedException("Nodo " + i + " non scoperto!");
        }
        return albero;
    }

    private void DFSVisitForPostOrder(int sorgente, boolean[] scoperti, ArrayList<Integer> postOrderArrayList) {
        scoperti[sorgente] = true;
        for (int vicino : grafo.getNeighbors(sorgente)) {
            if (!scoperti[vicino]) {
                DFSVisitForPostOrder(vicino, scoperti, postOrderArrayList);
            }
        }
        postOrderArrayList.add(sorgente);
    }

    public ArrayList<Integer> getNodesInOrderPostVisit(int sorgente) throws NotAllNodesReachedException {
        ArrayList<Integer> nodiVisitatiInPostOrdine = new ArrayList<>();
        boolean[] scoperti = new boolean[grafo.getOrder()];
        DFSVisitForPostOrder(sorgente, scoperti, nodiVisitatiInPostOrdine);
        for (int i=0; i<scoperti.length; i++) {
            if (!scoperti[i])
                throw new NotAllNodesReachedException("Nodo " + i + " non scoperto!");
        }
        return nodiVisitatiInPostOrdine;
    }

    public GraphInterface visitaDFSCompleta() throws NotAllNodesReachedException {
        Foresta = grafo.create();
        boolean[] scoperti = new boolean[grafo.getOrder()];
        for (int i=0; i<grafo.getOrder(); i++) {
            if (!scoperti[i])
                DFSVisit(i, scoperti, Foresta);
        }
        for (int i=0; i<scoperti.length; i++) {
            if(!scoperti[i])
                throw new NotAllNodesReachedException("Nodo " + i + " non scoperto!");
        }
        return Foresta;
    }

}
