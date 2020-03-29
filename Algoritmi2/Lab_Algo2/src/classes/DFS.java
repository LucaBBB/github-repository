/*
  @author Luca Borsalino
 * date: 2020-03-22
 *
 * Seconda esercitazione di laboratorio di Algoritmi2
 * Universita' del Piemonte Orientale (Alessandria) DISIT / Informatica.
 */

package classes;

import exception.NotAllNodesReachedException;
import it.uniupo.graphLib.Edge;
import it.uniupo.graphLib.GraphInterface;

import java.util.ArrayList;

public class DFS {
    GraphInterface grafo;
    GraphInterface foresta;
    ArrayList<Integer> Scoperti;
    ArrayList<Integer> risultato;
    int[] ordineDiVisita;
    int[] ordineDiPostVisita;
    boolean[] terminati;
    boolean isCycle;
    int startCycle; int endCycle;
    int i;

    public DFS(GraphInterface g) {
        this.grafo = g;
        Scoperti = new ArrayList<>();
        terminati = new boolean[grafo.getOrder()];
        risultato = new ArrayList<>();
    }

    /**
     * Visita DFS.
     * @param sorgente nodo di partenza della visita DFS.
     * @param scoperti array che tiene traccia dei nodi scoperti.
     * @param albero struttura che viene costruita quando si visita un nodo.
     */
    private void DFSVisit(int sorgente, boolean[] scoperti, GraphInterface albero) {
        scoperti[sorgente] = true;
        risultato.add(sorgente);
        for (Edge e : grafo.getOutEdges(sorgente)) {
            if (!scoperti[e.getHead()]) {
                albero.addEdge(e);
                DFSVisit(e.getHead(), scoperti, albero);
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

    /**
     * Metodo che svolge la visita DFS sul grafo dal nodo di partenza sorgente.
     *
     * @param sorgente il nodo di partenza della visita DFS;
     * @param scoperti l'array contenente l'indicazione se un nodo e' gia' stato scoperto o meno;
     * @param postOrderArrayList ArrayList contenente i nodi la cui visita e' terminata.
     */
    private void DFSVisitForPostOrder(int sorgente, boolean[] scoperti, ArrayList<Integer> postOrderArrayList) {
        scoperti[sorgente] = true;
        //for (int vicino : grafo.getNeighbors(sorgente)) {
        for (Edge e : grafo.getOutEdges(sorgente)) {
            if (!scoperti[e.getHead()])
                DFSVisitForPostOrder(e.getHead(), scoperti, postOrderArrayList);
        }
        postOrderArrayList.add(sorgente);
    }

    /**
     * Metodo che salva i nodi dopo aver terminato la visita del nodo stesso.
     * Questo determina l'ordine di fine visita.
     *
     * @param sorgente il nodi di partenza della visita.
     *
     * @return l'ArrayList contenente i nodi in ordine di terminazione della visita
     *
     * @throws NotAllNodesReachedException eccezione per i nodi non raggiunti dalla visita.
     */
    public ArrayList<Integer> getNodesInOrderPostVisit(int sorgente) throws NotAllNodesReachedException {
        ArrayList<Integer> OFV = new ArrayList<>();
        boolean[] scoperti = new boolean[grafo.getOrder()];
        DFSVisitForPostOrder(sorgente, scoperti, OFV);
        for (int i=0; i<scoperti.length; i++) {
            if (!scoperti[i])
                throw new NotAllNodesReachedException("Nodo " + i + " non scoperto!");
        }
        return OFV;
    }

    /**
     * Metodo di visita DFS completa. Ad ogni nodo richiama il metodo DFSVisit().
     *
     * @throws NotAllNodesReachedException eccezione che viene chiamata quando non tutti i nodi sono stati visitati.
     */
    private void visitaDFSCompleta() throws NotAllNodesReachedException {
        boolean[] scoperti = new boolean[grafo.getOrder()];
        for (int i=0; i<grafo.getOrder(); i++) {
            if (!scoperti[i])
                DFSVisit(i, scoperti, foresta);
        }
        for (int i=0; i<scoperti.length; i++) {
            if(!scoperti[i])
                throw new NotAllNodesReachedException("Nodo " + i + " non scoperto!");
        }
    }

    /**
     * Metodo che dopo aver richiamato la visita DFS completa restituisce la foresta ricavata dalla visita.
     * @return la foresta ricavata.
     * @throws NotAllNodesReachedException eccezione per i nodi non raggiunti dalla visita.
     */
    public GraphInterface getForest() throws NotAllNodesReachedException {
        foresta = grafo.create();

        visitaDFSCompleta();

        return foresta;
    }

    /**
     * Metodo che restituisce l'ArrayList contenente i nodi nell'ordine in cui vengono visitati.
     *
     * @param sorgente il nodo di partenza della visita DFS.
     *
     * @return l'ArrayList contenente i nodi nell'ordine in cui vengono visitati.
     */
    public ArrayList<Integer> getNodesInOrderOfVisit(int sorgente) {
        boolean[] nodiScoperti = new boolean[grafo.getOrder()];
        GraphInterface albero = grafo.create();

        DFSVisit(sorgente, nodiScoperti, albero);

        return risultato;
    }

    /**
     * Metodo che effettua la visita DFS sul grafo. E' un metodo di supporto a quello che deve restituire l'ordine
     * di visita dei nodi.
     *
     * @param sorgente il nodo di partenza della visita DFS.
     * @param i il counter per sapere l'ordine di visita dei nodi.
     * @param scoperti l'array contenente i nodi scoperti.
     */
    private void visitForOrderOfVisit(int sorgente, int i, boolean[] scoperti) {
        scoperti[sorgente] = true;
        ordineDiVisita[sorgente] = i;
        for (Edge e : grafo.getOutEdges(sorgente)) {
            if (!scoperti[e.getHead()]) {
                i++;
                visitForOrderOfVisit(e.getHead(), i, scoperti);
            }
        }
    }

    /**
     * Metodo che richiama visitForOrderOfVisit(). Restituisce un array il quale ad ogni posizione contiene l'ordine
     * di visita del nodo corrispondente.
     *
     * @param sorgente il nodo di partenza della visita DFS.
     * @return l'array contenente ad ogni posizione l'ordine con cui vengono visitati nel grafo.
     */
    public int[] getOrderOfVisit(int sorgente) {
        ordineDiVisita = new int[grafo.getOrder()];
        boolean[] scoperti = new boolean[grafo.getOrder()];
        visitForOrderOfVisit(sorgente, 0, scoperti);
        return ordineDiVisita;
    }

    /**
     * Metodo che inizializza l'array dell'ordine di post visita di ciascun nodo.
     *
     * @param sorgente il nodo di partenza della visita DFS.
     * @param scoperti l'array che tiene traccia dei nodi scoperti.
     */
    private void visitForOrderPostVisit(int sorgente, boolean[] scoperti) {
        scoperti[sorgente] = true;
        for (Edge e : grafo.getOutEdges(sorgente)) {
            if (!scoperti[e.getHead()]) {
                visitForOrderPostVisit(e.getHead(), scoperti);
            }
        }
        ordineDiPostVisita[sorgente] = i;
        i++;
    }

    /**
     * Metodo che restituisce l'array dell'ordine di post visita di ciascun nodo.
     *
     * @param sorgente il nodo di partenza della visita DFS.
     * @return l'array contenente ad ogni posizione l'ordine con cui termina la visita.
     */
    public int[] getOrderPostVisit(int sorgente) {
        ordineDiPostVisita = new int[grafo.getOrder()];
        boolean[] scoperti = new boolean[grafo.getOrder()];
        i = 0;
        visitForOrderPostVisit(sorgente, scoperti);
        return ordineDiPostVisita;
    }

    /**
     * Metodo che svolge la visita DFS in supporto al metodo che verifica se ci sono dei cicli in un grafo orientato.
     *
     * @param sorgente il nodo di partenza della visita DFS;
     * @param scoperti l'array che tiene traccia dei nodi gia' scoperti;
     * @param terminati l'array che tiene traccia dei nodi scoperti e terminati.
     */
    private void DFSForCycle(int sorgente, boolean[] scoperti, boolean[] terminati) {
        scoperti[sorgente] = true;
        for (Edge e : grafo.getOutEdges(sorgente)) {
            if (!scoperti[e.getHead()]) {
                DFSForCycle(e.getHead(), scoperti, terminati);
            }
            else if (scoperti[e.getHead()] && !terminati[e.getHead()])
                isCycle = true;
        }
        terminati[sorgente] = true;
    }

    /**
     * Metodo che grazie al supporto di DFSForCycle() scopre se sono presenti cicli nel grafo orientato.
     *
     * @return  true se ci sono cicli;<br>
     *          false se non ci sono cicli o se il grafo ha uno o due soli nodi.
     */
    public boolean hasDirCycle() {
        if (grafo.getOrder() == 1 || grafo.getOrder() == 2) // se il grafo ha uno o due nodi, penso che non abbia cicli.
            return false;
        boolean[] scoperti = new boolean[grafo.getOrder()];
        boolean[] terminati = new boolean[grafo.getOrder()];
        isCycle = false;

        for (int i=0; i<grafo.getOrder(); i++) {
            if (!scoperti[i])
                DFSForCycle(i, scoperti, terminati);
        }

        return isCycle;
    }

    /**
     * Metodo di supporto, il quale inizializza le variabili.
     * @param sorgente il nodo di partenza della visita DFS.
     * @param scoperti l'array che tiene traccia dei nodi scoperti.
     * @param terminati l'array che tiene traccia dei nodi terminati.
     * @param padre l'array che ad ogni posizione contiene il padre del nodo corrispondente.
     */
    private void DFSForBuildCycle(int sorgente, boolean[] scoperti, boolean[] terminati, int[] padre) {
        scoperti[sorgente] = true;
        for (Edge e : grafo.getOutEdges(sorgente)) {
            if (!scoperti[e.getHead()]) {
                padre[e.getHead()] = sorgente;
                DFSForCycle(e.getHead(), scoperti, terminati);
            }
            else if (scoperti[e.getHead()] && !terminati[e.getHead()]) {
                isCycle = true;
                startCycle = sorgente;
                endCycle = e.getHead();
            }
        }
        terminati[sorgente] = true;
    }

    /**
     * Metodo che verifica se su un grafo non orientato sono presenti cicli.
     * Se ci sono cicli, allora ritorna l'ArrayList contenente i nodi dei cicli.
     * NON FUNZIONA!
     *
     * @return  null se non ci sono cicli;<br>
     *          l'ArrayList contenente i nodi dei cicli.
     */
    public ArrayList<Integer> getDirCycle() {
        if (grafo.getOrder() == 1 || grafo.getOrder() == 2) // se il grafo ha uno o due nodi, penso che non abbia cicli.
            return null;
        boolean[] scoperti = new boolean[grafo.getOrder()];
        boolean[] terminati = new boolean[grafo.getOrder()];
        int[] padre = new int[grafo.getOrder()];
        isCycle = false;
        startCycle = -1;
        endCycle = -1;

        for (int i=0; i<grafo.getOrder(); i++) {
            if (!scoperti[i])
                DFSForBuildCycle(i, scoperti, terminati, padre);
        }
        if (!isCycle)
            return null;
        else if (startCycle != -1 && endCycle != -1) {
            ArrayList<Integer> camminoCiclo = new ArrayList<>();
            camminoCiclo.add(endCycle);
            int tmp = endCycle;
            while (padre[tmp] != startCycle) {
                tmp = padre[tmp];
                camminoCiclo.add(tmp);
            }
            return camminoCiclo;
        }
        return null;
    }

}
