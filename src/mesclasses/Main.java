package mesclasses;

import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class Main {



	public static void RATP() throws IOException, JSONException {

		//create the Graph
        weightedGraph g = new weightedGraph();

		//Graph.printGraph(g);

		//afficher tout le chemin BFS
		//System.out.println("le chemin complet que parcours l'algorithme");
		//BFS.bfs(g, 1, 200, Stations);
		//System.out.println("Le chemin le plus court :");
		//g.printGraph();
		//BFS.findShortestPath(g, 1, 200);
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

		int vertices = 6;
		weightedGraph graph = new weightedGraph();
		Dijkstra("1768", graph.HashmapArray);
	}


	public static void main(String[] args) throws JSONException,IOException {

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
