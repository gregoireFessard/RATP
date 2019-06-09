package mesclasses;

import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main {


	static final Scanner scan = new Scanner(System.in);

	static ArrayList<String> stationList;



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
		for(int i =0; i<(stat.size()-1);i++){
			dij = new Dijkstra(stat.get(i), graph.HashmapArray);
			for (int j =i+1; j<stat.size();j++){
				Shortestpath = dij.GetShortestPath(stat.get(i), stat.get(j));

				for( int k = 0;k <(Shortestpath.size()-1);k++){
					ClusterWeight.addToEdge(Shortestpath.get(k), Shortestpath.get(k+1), 1.0);
				}
			}
		}

		ClusterWeight.printGraph();


	}

	public static void djikstraMenu(String choice) throws IOException, JSONException {
		weightedGraph graph = new weightedGraph("/reseau.json");
		Dijkstra dij;

		while(true) {
			System.out.println("\n --Djikstra-- \n Commands Options: ");
			System.out.println("Djikstra 'x' 'y' : Do a Djikstra from x to y (example : \"Djikstra 1889 1768\" to search the path between Corentin Celton and Notre-Dame des Champs");
			System.out.println("			  You can see the list of station available from the main menu");
			System.out.println("p : print the weighted graph");
			System.out.println("q : go back to the precious menu \n");

			choice = scan.nextLine();
			if (choice.matches("q")){
				break;
			}
			if (choice.matches("p")){
				graph.printGraph();
			}
			else {
				String[] c = choice.split(" ");
				if ((c.length == 3) && c[0].matches("Djikstra")) {
					if (stationList.contains(c[1]) && stationList.contains(c[2])) {
						dij = new Dijkstra(c[1], graph.HashmapArray);
						dij.PrintShortestPath(c[1],c[2]);
					} else {
						System.out.println("This station does not exist");
					}
				} else {
					System.out.println(" Unknown command");
				}
			}

		}

	}

	public static void bfsMenu(String choice) throws IOException, JSONException {
		Graph g = new Graph("/reseau.json");

		while(true) {
			System.out.println("\n --BFS-- \n Commands Options: ");
			System.out.println("BFS 'x' 'y' : Do a BFS from x to y (example : \"BFS 1889 1768\" to search the path between Corentin Celton and Notre-Dame des Champs");
			System.out.println("			  You can see the list of station available from the main menu");
			System.out.println("p : print the graph");
			System.out.println("q : go back to the precious menu \n");

			choice = scan.nextLine();
			if (choice.matches("q")){
				break;
			}
			if (choice.matches("p")){
				g.printGraph();
			}
			else {
				String[] c = choice.split(" ");
				if ((c.length == 3) && c[0].matches("BFS")) {
					if (stationList.contains(c[1]) && stationList.contains(c[2])) {
						BFS.findShortestPath(g, c[1], c[2]);
					} else {
						System.out.println("This station does not exist");
					}
				} else {
					System.out.println(" Unknown command");
				}
			}

		}

	}

	public static void printAllStation() throws IOException, JSONException {

		JSONObject obj = collection.getJSONObjectFromFile("/reseau.json");

		JSONObject stat = obj.getJSONObject("stations");
		String[] names = JSONObject.getNames(stat);

		System.out.println( stationList.size() + " stations : \n");
		for (String s: stationList){
			System.out.print(s + " : " + stat.getJSONObject(s).getString("nom") + " ");
			JSONObject lignes  = stat.getJSONObject(s).getJSONObject("lignes");
			names = JSONObject.getNames(lignes);
			for (String ligneName : names)
			{
				JSONArray metrer =  lignes.getJSONArray(ligneName);
				for (int i=0;i<metrer.length();i++)
				{
					System.out.print( "(" +ligneName + " " +  metrer.get(i) + ")");
				}

			}
			System.out.println();
		}
	}

	public static void menu() throws IOException, JSONException {

		stationList = new ArrayList<String>();
		JSONObject obj = collection.getJSONObjectFromFile("/reseau.json");

		JSONObject stat = obj.getJSONObject("stations");
		String[] names = JSONObject.getNames(stat);
		String type;
		for (String string : names) {
			//for all stations
			type = stat.getJSONObject(string).getString("type");
			//if the station is a metro
			if (type.matches("metro") || type.matches("rer")) {
				//add the station to the list
				stationList.add(string);
			}
		}
		String choice = null;
		do {
			System.out.println("\n Commands Options: ");
			System.out.println("s : See the list of stations available");
			System.out.println("b : Graph with BFS");
			System.out.println("d : Weighted graph with djikstra");
			System.out.println("q : Quit the application \n");

			choice = scan.nextLine();

			switch (choice) {
				case "s":
					printAllStation();
					break;
				case "b":
					bfsMenu(choice);
					break;
				case "d":
					djikstraMenu(choice);
					break;
			}
		} while (!choice.equals("q"));
	}


	public static void main(String[] args) throws JSONException,IOException {

		menu();

	}
}
