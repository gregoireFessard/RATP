package mesclasses;

import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleToLongFunction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main {


    public static void RATP() throws IOException, JSONException {

        //create the Graph
        //Graph g = new Graph("/reseau.json");
        weightedGraph graph = new weightedGraph("/reseau.json");
        //Graph.printGraph(g);

        Dijkstra dij = new Dijkstra("1973", graph.HashmapArray);


        dij.PrintShortestPath("1973", "1952");

        //afficher tout le chemin BFS
        //System.out.println("le chemin complet que parcours l'algorithme");
        //BFS.bfs(g, 1, 200, Stations);
        //System.out.println("Le chemin le plus court :");
        //graph.printGraph();
        //BFS.findShortestPath(g, "1973", "1952");
        //System.out.print(Math.sqrt(Math.abs(48.8946326891969 - 48.8972870603971)*Math.abs(2.34709106533484 - 2.34477887737988) + Math.abs(2.34709106533484 - 2.34477887737988)*Math.abs(48.8946326891969 - 48.8972870603971)));
    }

    public static HashMap<String, Double> Cluster(weightedGraph Dij_clus, HashMap<String, Double> Table_cluster) {
        Double number_cluster = 1.0;
        Dijkstra dij;
        for (Map.Entry<String, Double> vertex : Table_cluster.entrySet()) {
            if (vertex.getValue() == 0.0) {
                dij = new Dijkstra(vertex.getKey(), Dij_clus.HashmapArray);

                for (Map.Entry<String, Double> ver : dij.distance.entrySet()) {
                    if (ver.getValue() < 100000) {
                        Table_cluster.put(ver.getKey(), number_cluster);
                    }
                }
                number_cluster += 1;
            }
        }
        return Table_cluster;
    }

    public static void ClusterCenter (HashMap<String, Double> Cluster, weightedGraph graph) throws IOException {
        JSONObject obj = collection.getJSONObjectFromFile("/reseau.json");

        JSONObject stat = obj.getJSONObject("stations");
        //JSONArray list = (JSONArray) jsonArray.get(i);

        ArrayList<Double> cluster2 = new ArrayList<Double>();
        ArrayList<Double> cluster3 = new ArrayList<Double>();
        ArrayList<Double> cluster5 = new ArrayList<Double>();
        ArrayList<Double> cluster4 = new ArrayList<Double>();
        cluster2.add(0.0);
        cluster2.add(0.0);
        cluster3.add(0.0);
        cluster3.add(0.0);
        cluster4.add(0.0);
        cluster4.add(0.0);
        cluster5.add(0.0);
        cluster5.add(0.0);

        for (Map.Entry<String, Double> vertex : Cluster.entrySet()) {
            if (vertex.getValue()==2.0){
                //stat.getJSONObject((String)list.get(vertex.getKey()));
            }
        }
    }

    public static void ClusterRatp() throws IOException, JSONException {

        //create the Graph
        //weightedGraph g = new weightedGraph();

        //Map v = weightedGraph.HashmapArray.get("1621");


        //Graph.printGraph(g);

        //afficher tout le chemin BFS
        //System.out.println("le chemin complet que parcours l'algorithme");
        //BFS.bfs(g, 1, 200, Stations);
        //System.out.println(v);
        //g.printGraph();
        //BFS.findShortestPath(g, 1, 200);

        weightedGraph graph = new weightedGraph("/reseau.json");
        //Dijkstra.DiDi("1992", graph.HashmapArray);

        //Dijkstra.PrinShortestPath("1992", "1941");


        ArrayList<String> stat = graph.TableStat;
        ArrayList<String> Shortestpath = new ArrayList<String>();
        weightedGraph ClusterWeight = new weightedGraph(stat, "/reseau.json");
        Dijkstra dij;

        for (int i = 0; i < (stat.size() - 1); i++) {
            dij = new Dijkstra(stat.get(i), graph.HashmapArray);
            for (int j = i + 1; j < stat.size(); j++) {
                Shortestpath = dij.GetShortestPath(stat.get(i), stat.get(j));

                for (int k = 0; k < (Shortestpath.size() - 1); k++) {
                    ClusterWeight.addToEdge(Shortestpath.get(k), Shortestpath.get(k + 1), 1.0);
                }
            }
        }

        int somme = 0;
        int somme_sup = 0;

        int nbr_edge = 0;
        int nbr_edge_sup = 0;
        ArrayList<String> toremove = new ArrayList<String>();


        for (Map.Entry<String, HashMap<String, Double>> vertex : ClusterWeight.HashmapArray.entrySet()) {
            for (Map.Entry<String, Double> poids : vertex.getValue().entrySet()) {
                if (poids.getValue() > 5600) {
                    nbr_edge_sup += 1;
                    somme_sup += poids.getValue();
                    System.out.println(vertex.getKey() + " " + poids.getKey() + " " + poids.getValue());
                    toremove.add(poids.getKey());
                }
                somme += poids.getValue();
                nbr_edge += 1;

            }
            for (int k = 0; k < toremove.size(); k++) {
                ClusterWeight.remove(vertex.getKey(), toremove.get(k));
            }
            toremove.clear();
        }

        HashMap<String, Double> cluster = new HashMap();

        for (int i = 0; i < stat.size(); i++) {
            cluster.put(stat.get(i), 0.0);
        }


        dij = new Dijkstra("1621", ClusterWeight.HashmapArray);

        HashMap<String, Double> Table_cluster = new HashMap<String, Double>();

        for (int i = 0; i < stat.size(); i++) {
            Table_cluster.put(stat.get(i), 0.0);
        }

        System.out.println(Cluster(ClusterWeight, Table_cluster));


        double poids_moy = somme / (2 * (nbr_edge));
        double poids_moy_sup = somme_sup / (2 * (nbr_edge_sup));


        //ClusterWeight.printGraph();
        System.out.println("le nbr d'elements est :" + nbr_edge);
        System.out.println("le poids moyen est : " + poids_moy);
        System.out.println("le nombre de vertices est : " + stat.size());
        System.out.println("le nbr d'elements supérieur à 5000 est :" + nbr_edge_sup);
        System.out.println("le poids moyen est : " + poids_moy_sup);


    }


    public static void main(String[] args) throws JSONException, IOException {

        ClusterRatp();
		/*
        JSONObject obj = collection.getJSONObjectFromFile("/reseau.json");

        JSONObject listStations = obj.getJSONObject("stations");
        String[] StationNames = JSONObject.getNames(listStations);
        JSONObject StationI = new JSONObject();

        for (String str : StationNames) {
            StationI = listStations.getJSONObject(str);
            if (StationI.getString("type").matches("metro")) {
                String latitude = StationI.getString("lat");
                String longitude = StationI.getString("lng");
                System.out.println(latitude + " " + longitude);
                System.out.println(str);

            }
            System.out.println(str);
        }
        */
    }
}
