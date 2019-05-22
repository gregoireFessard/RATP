package mesclasses;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

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
    	Data.GraphCorrespondance(g, S);
    	//Collect all the stations in a nice order in several arraylists and create edge between each stations
    	Data.GraphLignes(g, S);
    }
}