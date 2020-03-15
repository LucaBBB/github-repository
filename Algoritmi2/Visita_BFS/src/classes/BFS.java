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

    ArrayList<Integer> nodiVisitatiInOrdine; //elenco dei nodi nell'ordine in cui sono stati visitati
    int[] distanza; //distanza[v] = distanza del nodo v dalla sorgente

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
}
