package Classes;

import it.uniupo.graphLib.GraphInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class BFS {
    GraphInterface grafo;
    int ordine;
    ArrayList<Integer> nodesInOrderOfVisit;
    int[] distance;
    GraphInterface tree;

    public BFS(GraphInterface g) {
        grafo = g;
        ordine = grafo.getOrder();
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
        distance = new int[ordine];
        Arrays.fill(distance, -1);
        BFSVisitGetDistance(sorgente);
        return distance;
    }
    //------------------------------------------------------------------------------------------------


    //------------------------------------------------------------------------------------------------
    private void BFSTree(int sorgente) {
        ArrayList<Integer> coda = new ArrayList<>();
        boolean[] scoperti = new boolean[ordine];
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
}
