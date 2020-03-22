/*
  @author Luca Borsalino
 * date: 2020-03-15
 *
 * Prima esercitazione di laboratorio di Algoritmi2
 * Universita' del Piemonte Orientale (Alessandria) DISIT / Informatica.
 */
package classes;

import it.uniupo.graphLib.GraphInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class BFS {
    GraphInterface grafo; //per memorizzare il grafo su cui si lavora
    boolean[] scoperto;   //per memorizzare i nodi scoperti: scoperti[2]=true se il nodo 2 e' stato scoperto
    ArrayList<Integer> Scoperti;
    ArrayList<Integer> Coda;
    GraphInterface Albero;

    ArrayList<Integer> nodiVisitatiInOrdine; //elenco dei nodi nell'ordine in cui sono stati visitati
    int[] distanza; //distanza[v] = distanza del nodo v dalla sorgente
    int[] padre;

    boolean esisteCiclo;

    /**
     * Costruttore della classe BFS
     *
     * @param grafoInInput il grafo creato con la classe GraphInterface.
     */
    public BFS(GraphInterface grafoInInput){
        this.grafo = grafoInInput;
    }

    /**
     * Metodo che applica la visitaBFS vista a lezione ed inserisce i valori dei nodi letti all'interno dell'ArrayList.
     *
     * @param sorgente la sorgente da cui partire per visitare il grafo.
     * @return l'ArrayList contenente i nodi visitati tramite la visita BFS.
     */
    public ArrayList<Integer> getNodesInOrderOfVisit(int sorgente){
        nodiVisitatiInOrdine = new ArrayList<>();
        ArrayList<Integer> coda = new ArrayList<>();
        scoperto = new boolean[grafo.getOrder()];

        nodiVisitatiInOrdine.add(sorgente);
        coda.add(sorgente);

        while (!coda.isEmpty()) {
            Integer u = coda.remove(0);

            for (int vicino : grafo.getNeighbors(u)) {
                 if (!nodiVisitatiInOrdine.contains(vicino)) {
                     coda.add(vicino);
                     nodiVisitatiInOrdine.add(vicino);
                     scoperto[vicino] = true;
                 }
            }
        }
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
        distanza = new int[grafo.getOrder()];

        ArrayList<Integer> Coda = new ArrayList<>();
        ArrayList<Integer> Scoperti = new ArrayList<>();
        Coda.add(sorgente);
        Scoperti.add(sorgente);

        Arrays.fill(distanza, -1);
        distanza[sorgente] = 0;

        while (!Coda.isEmpty()) {
            int u = Coda.remove(0);
            for (int v : grafo.getNeighbors(u)) {
                if (!Scoperti.contains(v)) {
                    Coda.add(v);
                    Scoperti.add(v);
                    distanza[v] = distanza[u]+1;
                }
            }
        }
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
        ArrayList<Integer> Coda = new ArrayList<>();
        ArrayList<Integer> Scoperti = new ArrayList<>();

        Coda.add(sorgente);
        Scoperti.add(sorgente);
        GraphInterface tree = grafo.create();

        while (!Coda.isEmpty()) {
            int u = Coda.remove(0);
            for (int v : grafo.getNeighbors(u)) {
                if (!Scoperti.contains(v)) {
                    Coda.add(v);
                    Scoperti.add(v);
                    tree.addEdge(u, v);
                }
            }
        }
        return tree;
    }

    /**
     * Metodo che permette di salvare in un array di interi l'ordine di visita di ciascun nodo visitato tramite BFS.
     *
     * @param sorgente la sorgente da cui partire per visitare il grafo.
     * @return l'array di interi contenente l'ordine di visita di ciascun nodo.
     */
    public int[] getOrderOfVisit(int sorgente) {
        int[] ordineDiVisita = new int[grafo.getOrder()];

        ArrayList<Integer> Coda = new ArrayList<>();
        ArrayList<Integer> Scoperti = new ArrayList<>();

        Coda.add(sorgente);
        Scoperti.add(sorgente);
        ordineDiVisita[sorgente] = 0;
        int i = 1;

        while (!Coda.isEmpty()) {
            int u = Coda.remove(0);
            for (int v : grafo.getNeighbors(u)) {
                if (!Scoperti.contains(v)) {
                    Coda.add(v);
                    Scoperti.add(v);
                    ordineDiVisita[v] = i;
                    i++;
                }
            }
        }
        return ordineDiVisita;
    }

    /**
     * Metodo che dopo aver effettuato la visita BFS sul grafo e dopo aver inizializzato correttamente l'array padri,
     * inserisce, guardando l'array dei padri, il cammino minimo dal nodo alla sorgente seguendo i padri.
     *
     * @param sorgente il nodo di partenza della visita BFS.
     * @param nodo il nodo da cui calcolare il cammino minimo.
     * @return l'ArrayList contentente il cammino minimo dal nodo alla sorgente (al contrario!).
     */
    public ArrayList<Integer> camminoMinimo(int sorgente, int nodo) {
        ArrayList<Integer> camminoMinimoDaSorgenteANodo = new ArrayList<>();
        padre = new int[grafo.getOrder()];
        VisitaBFSCamminoMinimo(sorgente);

        camminoMinimoDaSorgenteANodo.add(nodo);
        int tmp = nodo;

        while(padre[tmp] != sorgente) {
            tmp = padre[tmp];
            camminoMinimoDaSorgenteANodo.add(tmp);
        }
        camminoMinimoDaSorgenteANodo.add(sorgente);

        return camminoMinimoDaSorgenteANodo;
    }

    /**
     * Metodo che effettua la visita BFS su un grafo a partire dalla sorgente;
     * inoltre, aggiunge all'array padri chi e' il padre del nodo u.
     *
     * @param sorgente il nodo di partenza della visita.
     */
    private void VisitaBFSCamminoMinimo(int sorgente) {
        ArrayList<Integer> S = new ArrayList<>();
        GraphInterface A = grafo.create();
        ArrayList<Integer> CODA = new ArrayList<>();

        S.add(sorgente);
        CODA.add(sorgente);
        while(!CODA.isEmpty()) {
            int u = CODA.remove(0);
            for (int vicino : grafo.getNeighbors(u)) {
                if (!S.contains(vicino)) {
                    padre[vicino] = u;
                    CODA.add(vicino);
                    S.add(vicino);
                    A.addEdge(u, vicino);
                }
            }
        }
    }

    /**
     * Metodo per verificare partendo dalla sorgente con una visita BFS se in un grafo ci sono dei cicli.
     *
     * @param sorgente il nodo di partenza della visita BFS.
     */
    public boolean BFSCicliGNOrientato(int sorgente) {
        Scoperti = new ArrayList<>();
        Coda = new ArrayList<>();
        padre = new int[grafo.getOrder()];
        esisteCiclo = false;

        Scoperti.add(sorgente);
        Coda.add(sorgente);

        while (!Coda.isEmpty()) {
            int u = Coda.remove(0);
            for (int vicino : grafo.getNeighbors(u)) {
                if (!Scoperti.contains(vicino)) {
                    Coda.add(vicino);
                    Scoperti.add(vicino);
                    padre[vicino] = u;
                }
                else if (vicino != padre[u])
                    esisteCiclo = true;
            }
        }
        return esisteCiclo;
    }

    /**
     * Metodo BFS generico che effettua la visita ed inizializza tutte le strutture dati.
     *
     * @param sorgente il nodo di partenza della visita.
     */
    private void BFSVisit(int sorgente) {
        nodiVisitatiInOrdine = new ArrayList<>();
        distanza = new int[grafo.getOrder()];
        Coda = new ArrayList<>();
        Scoperti = new ArrayList<>();
        Albero = grafo.create();

        scoperto = new boolean[grafo.getOrder()];

        nodiVisitatiInOrdine.add(sorgente);
        Coda.add(sorgente);
        Scoperti.add(sorgente);

        Arrays.fill(distanza, -1);
        distanza[sorgente] = 0;

        while (!Coda.isEmpty()) {
            Integer u = Coda.remove(0);

            for (int vicino : grafo.getNeighbors(u)) {
                if (!nodiVisitatiInOrdine.contains(vicino) && !Scoperti.contains(vicino)) {
                    Coda.add(vicino);
                    Scoperti.add(vicino);
                    nodiVisitatiInOrdine.add(vicino);
                    scoperto[vicino] = true;
                    distanza[vicino] = distanza[u]+1;
                    Albero.addEdge(u, vicino);
                }
            }
        }
    }
}
