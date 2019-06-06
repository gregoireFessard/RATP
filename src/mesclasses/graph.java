package mesclasses;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
  
public class graph
{
    // Size of array will be V (number of vertices in graph)
    static class Graph
    { 
        static int V;
        public static LinkedList<Integer> adjListArray[];
          
        // constructor  
        Graph(int V) 
        { 
            this.V = V; 
            // number of vertices 
            adjListArray = new LinkedList[V]; 
              
            // Create a new list for each vertex
            for(int i = 0; i < V ; i++){
                adjListArray[i] = new LinkedList<>();
            } 
        } 
    }

    // Adds an edge to an undirected graph
    public static void addEdge(Graph graph, int src, int dest) 
    {
        // Add an edge from src to dest
        graph.adjListArray[src].add(dest); 
          
        // Since graph is undirected, add an edge from dest to src also
        graph.adjListArray[dest].add(src);
    }
    
    //print the graph
    static void printGraph(Graph graph) 
    {       
        for(int v = 0; v < graph.V; v++) 
        { 
            System.out.println("Adjacency list of vertex "+ v);
            for(Integer vert: graph.adjListArray[v]){ 
                System.out.print(" -> "+ vert);
            } 
            System.out.println("\n"); 
        }
    }
    
    //Collect all correspondance in an Arraylist and create edge for correspondance
    public static void graphConstruction(Graph g, ArrayList<Integer> S) throws JSONException, IOException {
    	graph.GraphCorrespondance(g, S);
    	//Collect all the stations in a nice order in several arraylists and create edge between each stations
        graph.GraphLignes(g, S);
    }

    public static void GraphCorrespondance(Graph g, ArrayList<Integer> Stations) throws JSONException, IOException {
        //get the JSON
        JSONObject obj = collection.getJSONObjectFromFile("/reseau.json");
        JSONArray jsonArray = obj.getJSONArray("corresp");
        JSONArray list = new JSONArray();
        //trick to remove all RER
        String corr = new String();
        ArrayList<Integer> Correspondance = new ArrayList();
        for(int i = 0; i < jsonArray.length(); i++) {
            list = (JSONArray) jsonArray.get(i);
            for(int j = 1; j < list.length(); j++) {
                corr = (String) list.get(j);
                //here is the filter to keep only metro
                if (!corr.matches("A" + "(.*)") && !corr.matches("B" + "(.*)")) {
                    Correspondance.add(Integer.valueOf(corr));	//convert to int
                }
            }
        }
        for(int a = 1; a < Correspondance.size(); a++) {
            //creating edge with this trick :
            //get 2 stations in Correspondance which must be bound
            //found their index in the arraylist station
            //Link them with their own index
            if (Stations.contains(Correspondance.get(a-1)) && (Stations.contains(Correspondance.get(a)))){
                graph.addEdge(g, Stations.indexOf(Correspondance.get(a-1)), Stations.indexOf(Correspondance.get(a)));
            }
        }
    }

    //repete the processus with all metros
    public static void GraphLignes(Graph g, ArrayList<Integer> Stations) throws JSONException, IOException {
        JSONObject obj = collection.getJSONObjectFromFile("/reseau.json");
        JSONObject lignes = obj.getJSONObject("lignes");
        JSONObject metro = new JSONObject();
        JSONArray arrets = new JSONArray();
        JSONArray listArrets = new JSONArray();
        ArrayList<Integer> Lignes = new ArrayList();
        for(int i = 1; i < 15; i++) {
            metro = lignes.getJSONObject(Integer.toString(i));
            arrets = metro.getJSONArray("arrets");
            for(int k = 0; k < arrets.length(); k++) {
                //loop for "lignes"
                listArrets = (JSONArray) arrets.get(k);
                for(int j = 0; j < listArrets.length(); j++) {
                    //loop for stations metro
                    Lignes.add(Integer.valueOf((String) listArrets.get(j)));
                }
                for(int a = 1; a < Lignes.size(); a++) {
                    //create edge
                    graph.addEdge(g, Stations.indexOf(Lignes.get(a-1)), Stations.indexOf(Lignes.get(a)));
                }
                Lignes.clear();
            }
        }
        //add an exception for 3B and 7B metro which can't be in the precedent loop
        metro = lignes.getJSONObject("3B");
        arrets = metro.getJSONArray("arrets");
        listArrets = (JSONArray) arrets.get(0);
        Lignes.clear();
        for(int j = 0; j < listArrets.length(); j++) {
            Lignes.add(Integer.valueOf((String) listArrets.get(j)));
        }
        for(int a = 1; a < Lignes.size(); a++) {
            graph.addEdge(g, Stations.indexOf(Lignes.get(a-1)), Stations.indexOf(Lignes.get(a)));
        }
        metro = lignes.getJSONObject("7B");
        arrets = metro.getJSONArray("arrets");
        listArrets = (JSONArray) arrets.get(0);
        Lignes.clear();
        for(int j = 0; j < listArrets.length(); j++) {
            Lignes.add(Integer.valueOf((String) listArrets.get(j)));
        }
        for(int a = 1; a < Lignes.size(); a++) {
            graph.addEdge(g, Stations.indexOf(Lignes.get(a-1)), Stations.indexOf(Lignes.get(a)));
        }
    }
}