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
		System.out.println("Le chemin le plus court");
		BFS.findShortestPath(g, 1, 200);
	}

	public static void main(String[] args) throws JSONException,IOException {

		RATP();
	}
}
