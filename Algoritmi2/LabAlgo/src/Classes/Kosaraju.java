package Classes;

import it.uniupo.graphLib.DirectedGraph;
import it.uniupo.graphLib.Edge;
import it.uniupo.graphLib.GraphUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Kosaraju {
    DirectedGraph grafo;
    int ordineG;
    boolean[] scoperti;
    DirectedGraph grafoTrasposto;
    ArrayList<Integer> coda;

    public Kosaraju(DirectedGraph g) {
        grafo = g;
        ordineG = grafo.getOrder();
    }

    /**
     * Metodo di supporto a postVisitList() che effettua la visita DFS sul grafo.
     *
     * @param sorgente il nodo di partenza della visita DFS;
     * @param postVisitOrder l'ArrayList che tiene traccia dell'ordine di fine visita dei nodi.
     */
    private void DFSVisitPostVisitList(int sorgente, ArrayList<Integer> postVisitOrder) {
        scoperti[sorgente] = true;
        for (Edge e : grafo.getOutEdges(sorgente)) {
            int vicino = e.getHead();
            if (!scoperti[vicino]) {
                DFSVisitPostVisitList(vicino, postVisitOrder);
            }
        }
        postVisitOrder.add(sorgente);
    }

    /**
     * Metodo che restituisce l'ArrayList contenente l'ordine di fine visita del grafo.
     *
     * @return l'ArrayList contente l'ordine di fine visita.
     */
    public ArrayList<Integer> postVisitList() {
        ArrayList<Integer> postVisitOrder = new ArrayList<>();
       scoperti = new boolean[ordineG];
        for (int i=0; i<ordineG; i++) {
            if (!scoperti[i])
                DFSVisitPostVisitList(i, postVisitOrder);
        }
        return postVisitOrder;
    }

    private void BFSVisit(int sorgente, int[] cc, int cnt) {
        coda = new ArrayList<>();
        scoperti[sorgente] = true;
        coda.add(sorgente);

        while (!coda.isEmpty()) {
            int u = coda.remove(0);
            cc[u] = cnt;
            for (Edge e : grafoTrasposto.getOutEdges(u)) {
                int vicino = e.getHead();
                if (!scoperti[vicino]) {
                    coda.add(vicino);
                    scoperti[vicino] = true;
                }
            }
        }
    }

    private int[] connectedComponents(ArrayList<Integer> ofv) {
        int[] cc = new int[ordineG];
        int cnt = 0;
        scoperti = new boolean[ordineG];
        Collections.reverse(ofv);

        for (int n : ofv) {
            if (!scoperti[n]) {
                BFSVisit(n, cc, cnt);
                cnt++;
            }
        }

        return cc;
    }

    /**
     * Metodo che restituisce per ogni nodo l'SCC a cui appartiene.
     *
     * @return l'array contente ad ogni cella l'SCC a cui appartiene il nodo.
     */
    public int[] getSCC() {
        ArrayList<Integer> ofv = postVisitList();
        grafoTrasposto = GraphUtils.reverseGraph(grafo);
        return connectedComponents(ofv);
    }

    /**
     * Metodo che trova le SCC del grafo e calcola l'ordine della SCC piu' grande.
     *
     * @return l'ordine della SCC piu' grande.
     */
    public int getOrdMaxSCC() {
        int[] scc = getSCC();
        int[] maxSCC = new int[ordineG];
        Arrays.fill(maxSCC, 0);
        int ordMaxSCC = 0;

        for (int value : scc) {
            maxSCC[value]++;
        }

        for (int value : maxSCC) {
            if (value > ordMaxSCC)
                ordMaxSCC = value;
        }
        return ordMaxSCC;
    }
}
