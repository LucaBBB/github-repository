package Classes;

import Exceptions.NotAllNodesReachedException;
import it.uniupo.graphLib.Edge;
import it.uniupo.graphLib.GraphInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class DFS {
    GraphInterface grafo;
    int ordineG;
    boolean hasCycle;
    int sorg;
    int dest;

    public DFS(GraphInterface g) {
        grafo = g;
        ordineG = grafo.getOrder();
    }

    //------------------------------------------------------------------------------------------------
    /**
     * Metodo che effettua la visita DFS e fornisce supporto al metodo getNodesInOrderOfVisit(int sorgente).
     *
     * @param sorgente il nodo di partenza della visita DFS;
     * @param scoperti l'array di boolean che tiene traccia dei nodi scoperti;
     * @param orderOfVisit l'ArrayList che tiene traccia dell'ordine di fine visita dei nodi del grafo.
     */
    private void DFSVisitGetNodesInOrderOfVisit(int sorgente, boolean[] scoperti, ArrayList<Integer> orderOfVisit) {
        scoperti[sorgente] = true;
        orderOfVisit.add(sorgente);

        for (Edge e : grafo.getOutEdges(sorgente)) {
            int vicino = e.getHead();
            if (!scoperti[vicino])
                DFSVisitGetNodesInOrderOfVisit(vicino, scoperti, orderOfVisit);
        }
    }

    /**
     * Metodo che restituisce l'ArrayList contente l'ordine di visita.
     *
     * @param sorgente il nodo di partenza della visita DFS;
     *
     * @return l'ArrayList contenente l'ordine di visita dell'algoritmo.
     */
    public ArrayList<Integer> getNodesInOrderOfVisit(int sorgente) {
        ArrayList<Integer> orderOfVisit = new ArrayList<>();
        boolean[] scoperti = new boolean[ordineG];

        DFSVisitGetNodesInOrderOfVisit(sorgente, scoperti, orderOfVisit);

        return orderOfVisit;
    }
    //------------------------------------------------------------------------------------------------


    //------------------------------------------------------------------------------------------------
    /**
     * Metodo che effettua la visita DFS e fornisce supporto al metodo dfsTree(int sorgente).
     *
     * @param sorgente il nodo di partenza della visita DFS;
     * @param scoperti l'array di boolean che tiene traccia dei nodi scoperti;
     * @param albero la GraphInterface che tiene traccia dei nodi appartenenti all'albero.
     */
    private void DFSVisitDfsTree(int sorgente, boolean[] scoperti, GraphInterface albero) {
        scoperti[sorgente] = true;
        for (Edge e : grafo.getOutEdges(sorgente)) {
            int vicino = e.getHead();
            if (!scoperti[vicino]) {
                albero.addEdge(sorgente, vicino);  // albero.addEdge(e);
                DFSVisitDfsTree(vicino, scoperti, albero);
            }
        }
    }

    /**
     * Metodo che restituisce l'albero DFS dopo aver effettuato la visita DFS.
     *
     * @param sorgente il nodo di partenza della visita DFS;
     *
     * @return l'albero di visita DFS.
     *
     * @throws NotAllNodesReachedException eccezione per quando non tutti i nodi vengono raggiunti dalla visita DFS.
     */
    public GraphInterface dfsTree(int sorgente) throws NotAllNodesReachedException {
        if (sorgente<0)
            throw new java.lang.IllegalArgumentException("Sorgente non valida");
        GraphInterface albero = grafo.create();
        boolean[] scoperti = new boolean[ordineG];

        DFSVisitDfsTree(sorgente, scoperti, albero);

        if (albero.getOrder() < ordineG)
            throw new NotAllNodesReachedException("");

        return albero;
    }
    //------------------------------------------------------------------------------------------------


    //------------------------------------------------------------------------------------------------
    // Esercizio 2: ordine di fine visita
    //------------------------------------------------------------------------------------------------
    /**
     * Metodo che effettua la visita DFS e fornisce supporto al metodo getNodesInOrderPostVisit(int sorgente).
     *
     * @param sorgente il nodo di partenza della visita DFS;
     * @param scoperti l'array di boolean che tiene traccia dei nodi scoperti;
     * @param orderPostVisit l'ArrayList che tiene traccia dei nodi la cui visita e' terminata.
     */
    private void DFSVisitGetNodesInOrderPostVisit(int sorgente, boolean[] scoperti, ArrayList<Integer> orderPostVisit) {
        scoperti[sorgente] = true;
        for (Edge e : grafo.getOutEdges(sorgente)) {
            int vicino = e.getHead();
            if (!scoperti[vicino]) {
                DFSVisitGetNodesInOrderPostVisit(vicino, scoperti, orderPostVisit);
            }
        }
        orderPostVisit.add(sorgente);
    }

    /**
     * Metodo che restituisce l'ArrayList contenente l'ordine di fine visita.
     *
     * @param sorgente il nodo di partenza della visita DFS;
     *
     * @return l'ArrayList contenente l'ordine di fine visita.
     */
    public ArrayList<Integer> getNodesInOrderPostVisit(int sorgente) {
        ArrayList<Integer> postVisitOrder = new ArrayList<>();
        boolean[] scoperti = new boolean[ordineG];

        DFSVisitGetNodesInOrderPostVisit(sorgente, scoperti, postVisitOrder);

        return postVisitOrder;
    }
    //------------------------------------------------------------------------------------------------


    //------------------------------------------------------------------------------------------------
    // Esercizio 3: visita DFS completa
    //------------------------------------------------------------------------------------------------
    /**
     * Metodo che effettua la visita DFS e fornisce supporto al metodo getForest().
     *
     * @param sorgente il nodo di partenza della visita DFS;
     * @param scoperti l'array di boolean che tiene traccia dei nodi scoperti;
     * @param foresta la GraphInterface che rappresenta la foresta degli alberi del grafo.
     */
    private void DFSVisitGetForest(int sorgente, boolean[] scoperti, GraphInterface foresta) {
        scoperti[sorgente] = true;
        for (Edge e : grafo.getOutEdges(sorgente)) {
            int vicino = e.getHead();
            foresta.addEdge(e);
            if (!scoperti[vicino]) {
                DFSVisitGetForest(vicino, scoperti, foresta);
            }
        }
    }

    /**
     * Metodo che restituisce la foresta di un grafo, effettuando una visita DFS completa.
     *
     * @return la foresta del grafo.
     */
    public GraphInterface getForest() {
        boolean[] scoperti = new boolean[ordineG];
        GraphInterface foresta = grafo.create();

        for (int i=0; i<ordineG; i++) {
            if (!scoperti[i])
                DFSVisitGetForest(i, scoperti, foresta);
        }

        return foresta;
    }
    //------------------------------------------------------------------------------------------------


    //------------------------------------------------------------------------------------------------
    // CICLI IN GRAFI ORIENTATI
    //------------------------------------------------------------------------------------------------
    private void DFSVisit(int sorgente, boolean[] scoperti, boolean[] terminati, int[] padri) {
        scoperti[sorgente] = true;
        for (Edge e : grafo.getOutEdges(sorgente)) {
            int vicino = e.getHead();
            if (!scoperti[vicino]) {
                padri[vicino] = sorgente;
                DFSVisit(vicino, scoperti, terminati, padri);
            }
            else if (scoperti[vicino] && !terminati[vicino]) {
                hasCycle = true;
                dest = sorgente;
                sorg = vicino;
            }
        }
        terminati[sorgente] = true;
    }

    /**
     * Metodo che restituisce se nel grafo e' presente o meno un ciclo.
     *
     * @return  true se c'e' almeno un ciclo;
     *          false se non c'e' presenza di cicli.
     */
    public boolean hasDirCycle() {
        hasCycle = false;
        boolean[] scoperti = new boolean[ordineG];
        boolean[] terminati = new boolean[ordineG];
        int[] padri = new int[ordineG];
        Arrays.fill(padri, -1);

        for (int i=0; i<ordineG; i++) {
            if (!scoperti[i])
                DFSVisit(i, scoperti, terminati, padri);
        }

        return hasCycle;
    }

    public ArrayList<Integer> getDirCycle() {
        boolean[] scoperti = new boolean[ordineG];
        boolean[] terminati = new boolean[ordineG];
        int[] padri = new int[ordineG];
        hasCycle = false;
        Arrays.fill(padri, -1);
        sorg = -1;
        dest = -1;

        for (int i=0; i<ordineG; i++) {
            if (!scoperti[i])
                DFSVisit(i, scoperti, terminati, padri);
        }

        if (!hasCycle && sorg==-1 && dest==-1)
            return null;

        ArrayList<Integer> cyclePath = new ArrayList<>();
        cyclePath.add(dest);
        int tmp = dest;
        while (padri[tmp] != sorg) {
            tmp = padri[tmp];
            cyclePath.add(tmp);
        }
        cyclePath.add(sorg);
        return cyclePath;
    }
}
