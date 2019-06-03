package mesclasses;

import java.util.ArrayList;
import java.io.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mesclasses.graph.Graph;

public class Main {

	public static void main(String[] args) throws JSONException,IOException {
		
		// here we create an arraylist with all the stations
	    ArrayList<Integer> Stations = new ArrayList<>();
		JSONObject obj = collection.getJSONObjectFromFile("/reseau.json");
		JSONObject stat = obj.getJSONObject("stations");
		String[] names = JSONObject.getNames(stat);
		for(String string : names) {
			//for all stations
			JSONObject metro = stat.getJSONObject(string);
			String type = metro.getString("type");
			//if the station is a metro
			if (type.matches("metro")){
				//add the station to the list
				Stations.add(Integer.valueOf(string));
			}
		}
			
		//1850 is an exception for us, it's supposed to be a metro but in the JSON it's a rer
		//so we had to add it manually
		Stations.add(Integer.valueOf("1850"));
			
		//create the size of the graph
	    int V = Stations.size();
	    //create the graph
	    
	    Graph g = new Graph(V);
        graph.graphConstruction(g, Stations);
        //graph.printGraph(g);
        
        //afficher tout le chemin BFS
        //System.out.println("le chemin complet que parcours l'algorithme");
        //BFS.bfs(g, 1, 200, Stations);
        System.out.println("Le chemin le plus court");
        BFSShortestPath.findShortestPath(g, 1, 200);
	}
}
