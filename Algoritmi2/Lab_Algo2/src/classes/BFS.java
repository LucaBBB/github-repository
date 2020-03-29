/*
  @author Luca Borsalino
 * date: 2020-03-15
 *
 * Prima esercitazione di laboratorio di Algoritmi2
 * Universita' del Piemonte Orientale (Alessandria) DISIT / Informatica.
 */
package classes;

import it.uniupo.graphLib.Edge;
import it.uniupo.graphLib.GraphInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class BFS {
    //variabili di istanza
    GraphInterface grafo; //per memorizzare il grafo su cui si lavora
    boolean[] scoperto; //per memorizzare i nodi scoperti: scoperti[2]=true se il nodo 2 e' stato scoperto
    ArrayList<Integer> nodiVisitatiInOrdine; //elenco dei nodi nell'ordine in cui sono stati visitati
    int[] distanza; //distanza[v] = distanza del nodo v dalla sorgente
    GraphInterface albero;
    int[] orderOfVisit;
    int[] padre;
    int[] cc;
    int cnt;
    boolean isCycle;

    /****************************
     * Questo e' il costruttore
     ****************************/
    public BFS(GraphInterface grafoInInput) {
        this.grafo = grafoInInput;
    }

    /**
     * Visita BFS di un grafo a partire dalla sorgente data in input.
     *
     * @param sorgente nodo di partenza della visita BFS sul grafo.
     */
    private void visitaBFS(int sorgente) {
        nodiVisitatiInOrdine = new ArrayList<>();
        distanza = new int[grafo.getOrder()];
        scoperto = new boolean[grafo.getOrder()];
        ArrayList<Integer> coda = new ArrayList<>();
        albero = grafo.create();
        orderOfVisit = new int[grafo.getOrder()];

        scoperto[sorgente] = true;
        coda.add(sorgente);
        nodiVisitatiInOrdine.add(sorgente);
        distanza[sorgente] = 0;
        orderOfVisit[sorgente] = 0;

        int visit = 1;
        while (!coda.isEmpty()) {
            int u = coda.remove(0);
            for (int vicino : grafo.getNeighbors(u)) {
                if (!scoperto[vicino]) {
                    coda.add(vicino);
                    nodiVisitatiInOrdine.add(vicino);
                    albero.addEdge(u, vicino);
                    scoperto[vicino] = true;
                    distanza[vicino] = distanza[u] + 1;
                    orderOfVisit[vicino] = visit;
                    visit++;
                }
            }
        }
    }

    /**
     * Metodo che applica la visitaBFS vista a lezione ed inserisce i valori dei nodi letti all'interno dell'ArrayList.
     *
     * @param sorgente la sorgente da cui partire per visitare il grafo.
     * @return l'ArrayList contenente i nodi visitati tramite la visita BFS.
     */
    public ArrayList<Integer> getNodesInOrderOfVisit(int sorgente) {
        visitaBFS(sorgente);
        return nodiVisitatiInOrdine;
    }

    /**
     * Metodo che applica la visitaBFS vista a lezione ed inserisce la distanza di un nodo v dalla sorgente all'interno
     * di un array di interi.
     *
     * @param sorgente la sorgente da cui partire per visitare il grafo ed il punto dal quale iniziare a calcolare
     *                 la distanza dei nodi.
     * @return l'array di interi contente le distanze dei nodi dalla sorgente.
     */
    public int[] getDistance(int sorgente) { //resituisce le distanza di ciascun nodo da sorg
        visitaBFS(sorgente);
        return distanza;
    }

    /**
     * Metodo che permette di creare un albero BFS applicando la visita BFS.
     *
     * @param sorgente la sorgente da cui partire per visitare il grafo ed il punto dal quale iniziare a creare
     *                 l'albero BFS.
     * @return l'albero BFS creato dal grafo con la visita BFS.
     */
    public GraphInterface bfsTree(int sorgente) {
        visitaBFS(sorgente);
        return albero;
    }

    /**
     * Metodo che richiama la visita BFS, la quale provvede ad inizializzare la variabile array orderOfVisit.
     *
     * @param sorgente il nodo di partenza della visita BFS.
     *
     * @return l'array contenente ad ogni posizione il turno di visita di ciascun nodo.
     */
    public int[] getOrderOfVisit(int sorgente) {
        visitaBFS(sorgente);
        return orderOfVisit;
    }

    /**
     * Metodo che restituisce il percorso piu' breve dal nodo sorgente al nodo destinazione.
     *
     * @param sorgente il nodo di partenza del shortestPath ed il nodo di partenza della visita BFS.
     * @param destinazione il nodo di arrivo del shortestPath
     * @return l'ArrayList contenete il shortest path da destinazione a sorgente (al contrario quindi).
     */
    public ArrayList<Edge> getShortestPath(int sorgente, int destinazione) {
        if (sorgente == destinazione) // se il grafo ha un solo nodo
            return null;

        padre = new int[grafo.getOrder()];
        scoperto = new boolean[grafo.getOrder()];
        albero = grafo.create();
        ArrayList<Integer> coda = new ArrayList<>();

        coda.add(sorgente);

        while (!coda.isEmpty()) {
            int u = coda.remove(0);
            for (int vicino : grafo.getNeighbors(u)) {
                if (!scoperto[vicino]) {
                    padre[vicino] = u;
                    coda.add(vicino);
                    scoperto[vicino] = true;
                    albero.addEdge(u, vicino);
                }
            }
        }

        ArrayList<Edge> shortestPath = new ArrayList<>();
        shortestPath.add(new Edge(destinazione, padre[destinazione]));
        int tmp = destinazione;
        while (padre[tmp] != sorgente) {
            tmp = padre[tmp];
            shortestPath.add(new Edge(tmp, padre[tmp]));
        }

        return shortestPath;
    }

    /**
     * Metodo di supporto che effettua la visita BFS per trovare le componenti connesse.
     *
     * @param sorgente il nodo di partenza della visita BFS.
     */
    private void BFSVisitForIsConnected(int sorgente) {
        ArrayList<Integer> coda = new ArrayList<>();

        coda.add(sorgente);
        scoperto[sorgente] = true;
        while (!coda.isEmpty()) {
            int u = coda.remove(0);
            cc[u] = cnt;
            for(int vicino : grafo.getNeighbors(u)) {
                if (!scoperto[vicino]) {
                    coda.add(vicino);
                    scoperto[vicino] = true;
                }
            }
        }
    }

    /**
     * Metodo che richiama la visita BFS sul grafo per ogni nodo (visita completa). Ad ogni nodo, inserisce nell'array
     * la componente connessa a cui appartiene.
     *
     * @return l'array contenete per ogni nodo la componente connessa a cui appartiene.
     */
    public int[] connectedComponents() {
        cc = new int[grafo.getOrder()];
        cnt = 0;
        scoperto = new boolean[grafo.getOrder()];

        for (int nodo=0; nodo<grafo.getOrder(); nodo++) {
            if(!scoperto[nodo]) {
                BFSVisitForIsConnected(nodo);
                cnt++;
            }
        }

        return cc;
    }

    /**
     * Metodo che dopo aver richiamato il metodo che costruisce l'array delle componenti connesse, verifica se ad ogni
     * posizione dell'array il valore e' a 0. Se cosi' e', significa che la componente e' una sola, altrimenti ci sono
     * piu' componenti connesse.
     * NOTA: nelle slide di esercizi, questo e' posto prima del precedente metodo, ma ho pensato che costruendo l'array
     * delle componenti connesse e verificando che ce ne fosse solo una, si poteva eseguire lo stesso questo esercizio.
     *
     * @return  true se il grafo e' connesso;<br>
     *          false se il grafo non e' connesso.
     */
    public boolean isConnected() {
        int[] cc = connectedComponents();
        for (int i=0; i<cc.length; i++) {
            if (cc[i] > 0)
                return false;
        }
        return true;
    }

    /**
     * Metodo che effettua una visita BFS sul grafo e verifica se ci sono cicli.
     *
     * @param sorgente il nodo di partenza della visita BFS.
     */
    private void BFSForUndirectedCycle(int sorgente) {
        ArrayList<Integer> coda = new ArrayList<>();

        scoperto[sorgente] = true;
        coda.add(sorgente);

        while (!coda.isEmpty()) {
            int u = coda.remove(0);
            for(int vicino : grafo.getNeighbors(u)) {
                if (!scoperto[vicino]) {
                    coda.add(vicino);
                    scoperto[vicino] = true;
                    padre[vicino] = u;
                }
                else if (vicino != padre[u])
                    isCycle = true;
            }
        }

    }

    /**
     * Metodo che serve a verificare si in un grafo undirected ci sono o meno cicli.
     * Usa come metodo di supporto private void BFSForUndirectedCycle(int sorgente).
     *
     * @return  true se c'e' almeno un ciclo;<br>
     *          false se non ci sono cicli.
     */
    public boolean hasUndirectedCycle() {
        if (grafo.getOrder() == 1)
            return false;
        scoperto = new boolean[grafo.getOrder()];
        padre = new int[grafo.getOrder()];
        isCycle = false;

        for (int i=0; i<grafo.getOrder(); i++) {
            if (!scoperto[i])
                BFSForUndirectedCycle(i);
        }

        return isCycle;
    }
}