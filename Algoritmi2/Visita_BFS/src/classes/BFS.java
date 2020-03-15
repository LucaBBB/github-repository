package classes;

import it.uniupo.graphLib.GraphInterface;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.ArrayList;
import java.util.Arrays;

public class BFS {
    //variabili di istanza
    GraphInterface grafo; //per memorizzare il grafo su cui si lavora
    boolean[] scoperto; //per memorizzare i nodi scoperti: scoperti[2]=true se il nodo 2 e' stato scoperto
    ArrayList<Integer> nodiVisitatiInOrdine; //elenco dei nodi nell'ordine in cui sono stati visitati
    int[] distanza; //distanza[v] = distanza del nodo v dalla sorgente

    /****************************
     * Questo e' il costruttore
     ****************************/
    public BFS(GraphInterface grafoInInput){
        this.grafo = grafoInInput;
    }

    public void initScoperto() {
        int ordineDelGrafo = grafo.getOrder();
        scoperto = new boolean[ordineDelGrafo];

        for (int i=0; i<ordineDelGrafo; i++) {
            scoperto[i] = false;
        }
    }


    /********
     *  Il metodo visitaBFS(int sorgente) fa una visita BFS dalla sorgente, ma non restituisce niente:
     *  modifica i valori delle opportune variabili di istanza
     */
    private void visitaBFS(int sorgente) {
        //qui inizializzate correttamente le variabili di istanza che vi servono

        //la coda puo' essere implementata come una ArrayList di interi
        ArrayList<Integer> coda = new ArrayList<Integer>();

        coda.add(sorgente);
        scoperto[0] = true;

        while (!coda.isEmpty()) {
            int u = coda.remove(0);

            for (int vicino : grafo.getNeighbors(u)) {
                if (!scoperto[vicino]) {
                    coda.add(vicino);
                    scoperto[vicino] = true;
                }
            }
        }

        //per aggiungere un elemento in fondo alla "coda": coda.add(elemento)
        //per leggere e cancellare il primo elemento coda.remove(0)

        /*********
         * questo metodo non e' completo,
         * dovete scrivere la visita BFS
         * aiutatevi con le istruzioni
         */

    }

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


    public int[] getDistance(int sorgente) { //resituisce le distanza di ciascun nodo da sorg
        int[] distanza = new int[grafo.getOrder()];
        //scoperto = new boolean[grafo.getOrder()];

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


}
