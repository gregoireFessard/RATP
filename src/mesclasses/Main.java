package mesclasses;

import java.util.ArrayList;
import java.io.*;

import org.json.JSONException;
import org.json.JSONObject;

public class Main {



	public static void RATP() throws IOException, JSONException {

		//create the Graph
		Graph g = new Graph("/reseau.json");

		//Graph.printGraph(g);

		//afficher tout le chemin BFS
		//System.out.println("le chemin complet que parcours l'algorithme");
		//BFS.bfs(g, 1, 200, Stations);
		//System.out.println("Le chemin le plus court :");
		//g.printGraph();
		BFS.findShortestPath(g, "1992", "1941");
		//System.out.print(Math.sqrt(Math.abs(48.8946326891969 - 48.8972870603971)*Math.abs(2.34709106533484 - 2.34477887737988) + Math.abs(2.34709106533484 - 2.34477887737988)*Math.abs(48.8946326891969 - 48.8972870603971)));
	}

	public static void main(String[] args) throws JSONException,IOException {

		RATP();
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
