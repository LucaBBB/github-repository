package classes;

import exception.NotAllNodesReachedException;
import it.uniupo.graphLib.GraphInterface;

import java.util.ArrayList;

public class DFS {
    GraphInterface grafo;
    GraphInterface Foresta;
    ArrayList<Integer> Scoperti;
    int[] padre;
    boolean esisteCiclo;

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

    /**
     * Metodo che verifica con una visita DFS se esiste un ciclo nel grafo.
     * Questo metodo di ricerca di cicli e' utilizzabile solo su grafi orientati.
     *
     * @param sorgente il nodo di partenza della visita DFS.
     */
    public boolean DFSCicliGOrientato(int sorgente) {
        esisteCiclo = false;
        Scoperti = new ArrayList<>();
        ArrayList<Integer> terminati = new ArrayList<>();
        Scoperti.add(sorgente);
        for (int vicino : grafo.getNeighbors(sorgente)) {
            if (!Scoperti.contains(vicino))
                DFSCicliGOrientato(vicino);
            else if(Scoperti.contains(vicino) && !terminati.contains(vicino))
                esisteCiclo = true;
        }
        terminati.add(sorgente);
        return esisteCiclo;
    }

    /**
     * Metodo che verifica con una visita DFS se esiste un ciclo nel grafo.
     * Questo metodo di ricerca di cicli e' utilizzabile solo su grafi non orientati.
     *
     * @param sorgente il nodo di partenza della visita DFS.
     */
    public boolean DFSCicliGNOrientato(int sorgente) {
        esisteCiclo = false;
        padre = new int[grafo.getOrder()];
        Scoperti = new ArrayList<>();
        Scoperti.add(sorgente);
        for (int vicino : grafo.getNeighbors(sorgente)) {
            if (!Scoperti.contains(vicino)) {
                padre[vicino] = sorgente;
                DFSCicliGNOrientato(vicino);
            }
            else if (vicino != padre[sorgente])
                esisteCiclo = true;
        }
        return esisteCiclo;
    }

}
