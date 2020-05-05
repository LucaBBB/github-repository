package Classes;

import it.uniupo.graphLib.Edge;
import it.uniupo.graphLib.GraphInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class BFS {
    GraphInterface grafo;
    int ordineG;
    ArrayList<Integer> nodesInOrderOfVisit;
    int[] distance;
    GraphInterface tree;
    int[] padri;

    public BFS(GraphInterface g) {
        grafo = g;
        ordineG = grafo.getOrder();
    }

    //------------------------------------------------------------------------------------------------
    private void BFSVisitGetNodesInOrderOfVisit(int sorgente) {
        // inizializzazione
        ArrayList<Integer> coda = new ArrayList<>();
        boolean[] scoperti = new boolean[grafo.getOrder()];
        coda.add(sorgente);
        scoperti[sorgente] = true;
        nodesInOrderOfVisit.add(sorgente);

        // visita
        while (!coda.isEmpty()) {
            int u = coda.remove(0);
            for (int vicino : grafo.getNeighbors(u)) {
                if (!scoperti[vicino]) {
                    coda.add(vicino);
                    scoperti[vicino] = true;
                    nodesInOrderOfVisit.add(vicino);
                }
            }
        }
    }

    public ArrayList<Integer> getNodesInOrderOfVisit(int sorgente) {
        nodesInOrderOfVisit = new ArrayList<>();
        BFSVisitGetNodesInOrderOfVisit(sorgente);
        return nodesInOrderOfVisit;
    }
    //------------------------------------------------------------------------------------------------


    //------------------------------------------------------------------------------------------------
    private void BFSVisitGetDistance(int sorgente) {
        // inizializzazione
        ArrayList<Integer> coda = new ArrayList<>();
        boolean[] scoperti = new boolean[grafo.getOrder()];
        coda.add(sorgente);
        scoperti[sorgente] = true;
        distance[sorgente] = 0;
        int i = 1;

        // visita
        while (!coda.isEmpty()) {
            int u = coda.remove(0);
            for (int vicino : grafo.getNeighbors(u)) {
                if (!scoperti[vicino]) {
                    coda.add(vicino);
                    scoperti[vicino] = true;
                    distance[vicino] = i;
                }
            }
            i++;
        }
    }

    public int[] getDistance(int sorgente) {
        nodesInOrderOfVisit = new ArrayList<>();
        distance = new int[ordineG];
        Arrays.fill(distance, -1);
        BFSVisitGetDistance(sorgente);
        return distance;
    }
    //------------------------------------------------------------------------------------------------


    //------------------------------------------------------------------------------------------------
    private void BFSTree(int sorgente) {
        ArrayList<Integer> coda = new ArrayList<>();
        boolean[] scoperti = new boolean[ordineG];
        //tree = grafo.create();
        coda.add(sorgente);
        scoperti[sorgente] = true;

        while (!coda.isEmpty()) {
            int u = coda.remove(0);
            for (int v : grafo.getNeighbors(u)) {
                if (!scoperti[v]) {
                    coda.add(v);
                    scoperti[v] = true;
                    tree.addEdge(u, v);
                }
            }
        }
    }

    public GraphInterface bfsTree(int sorgente) {
        tree = grafo.create();
        BFSTree(sorgente);
        return tree;
    }
    //------------------------------------------------------------------------------------------------


    //------------------------------------------------------------------------------------------------
    private void BFSTreeGetOrderOfVisit(int sorgente, int[] orderOfVisit) {
        ArrayList<Integer> coda = new ArrayList<>();
        boolean[] scoperti = new boolean[ordineG];
        coda.add(sorgente);
        scoperti[sorgente] = true;
        orderOfVisit[sorgente] = 0;
        int i=1;

        while (!coda.isEmpty()) {
            int u = coda.remove(0);
            for (int v : grafo.getNeighbors(u)) {
                if (!scoperti[v]) {
                    coda.add(v);
                    scoperti[v] = true;
                    orderOfVisit[v] = i;
                    i++;
                }
            }
        }
    }

    public int[] getOrderOfVisit(int sorgente) {
        int[] orderOfVisit = new int[ordineG];
        BFSTreeGetOrderOfVisit(sorgente, orderOfVisit);
        return orderOfVisit;
    }
    //------------------------------------------------------------------------------------------------


    //------------------------------------------------------------------------------------------------
    // SHORTEST PATH
    //------------------------------------------------------------------------------------------------
    private void BFSVisitGetShortestPath(int sorgente, boolean[] scoperti) {
        scoperti[sorgente] = true;
        ArrayList<Integer> coda = new ArrayList<>();
        coda.add(sorgente);

        while(!coda.isEmpty()) {
            int u = coda.remove(0);
            for (Edge e : grafo.getOutEdges(u)) {
                int vicino = e.getHead();
                if (!scoperti[vicino]) {
                    padri[vicino] = u;
                    coda.add(vicino);
                    scoperti[vicino] = true;
                }
            }
        }
    }

    public ArrayList<Edge> getShortestPath(int sorg, int dest) {
        if (sorg == dest)
            return null;

        ArrayList<Edge> camminoMinimo = new ArrayList<>();
        padri = new int[grafo.getOrder()];
        boolean[] scoperti = new boolean[grafo.getOrder()];

        BFSVisitGetShortestPath(sorg, scoperti);

        camminoMinimo.add(new Edge(dest, padri[dest]));
        int tmp = dest;
        while (padri[tmp] != sorg) {
            tmp = padri[tmp];
            camminoMinimo.add(new Edge(tmp, padri[tmp]));

        }
        return camminoMinimo;
    }
    //------------------------------------------------------------------------------------------------


    //------------------------------------------------------------------------------------------------
    // CONNESSIONE DI GRAFI NON ORIENTATI E COMPONENTI CONNESSE
    //------------------------------------------------------------------------------------------------
    private void DFSVisit(int sorgente, boolean[] scoperti, int[] cc, int cnt) {
        ArrayList<Integer> coda = new ArrayList<>();
        coda.add(sorgente);
        scoperti[sorgente] = true;

        while (!coda.isEmpty()) {
            int u = coda.remove(0);
            cc[u] = cnt;
            for (Edge e : grafo.getOutEdges(u)) {
                int vicino = e.getHead();
                if (!scoperti[vicino]) {
                    coda.add(vicino);
                    scoperti[vicino] = true;
                }
            }
        }
    }

    public boolean isConnected() {
        boolean[] scoperti = new boolean[ordineG];
        int[] cc = new int[ordineG];
        int cnt = 0;

        for (int i=0; i<ordineG; i++) {
            if (!scoperti[i]) {
                DFSVisit(i, scoperti, cc, cnt);
                cnt++;
            }
        }

        for (int n : cc) {
            if (n > 0)
                return false;
        }
        return true;
    }

    public int[] connectedComponents() {
        int[] cc = new int[ordineG];
        int cnt = 0;
        boolean[] scoperti = new boolean[ordineG];

        for (int i=0; i<ordineG; i++) {
            if (!scoperti[i]) {
                DFSVisit(i, scoperti, cc, cnt);
                cnt++;
            }
        }

        return cc;
    }
}
