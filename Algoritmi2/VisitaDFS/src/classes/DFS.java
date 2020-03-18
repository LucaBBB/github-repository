package classes;

import it.uniupo.graphLib.GraphInterface;

import java.util.ArrayList;

public class DFS {

    GraphInterface grafo;
    boolean[] scoperti;

    public DFS(GraphInterface grafoInInput) {
        this.grafo = grafoInInput;
    }

    private void visitaDFS(int sorgente) {
        scoperti = new boolean[grafo.getOrder()];
        scoperti[sorgente] = true;

        for (int vicino : grafo.getNeighbors(sorgente)) {
            if (!scoperti[vicino])
                visitaDFS(vicino);
        }
    }

    public ArrayList<Integer> getNodesInOrderOfVisit(int sorgente) {
        ArrayList<Integer> risultato = new ArrayList<>();
        scoperti = new boolean[grafo.getOrder()];

        scoperti[sorgente] = true;

        for (int vicino : grafo.getNeighbors(sorgente)) {
            if (!scoperti[vicino]) {
                risultato.add(sorgente);
                visitaDFS(vicino);
            }
        }
        return risultato;
    }
}
