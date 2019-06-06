package mesclasses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class weightedGraph {


/*
    public static HashMap<Integer,Integer> HashmapArray[];

    public weightedGraph(String json) throws JSONException, IOException {
        ArrayList<Integer> Stations = new ArrayList<>();
        JSONObject obj = collection.getJSONObjectFromFile(json);
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


        // number of vertices
        HashmapArray = new HashMap[Stations.size()];

        // Create a new list for each vertex
        for(int i = 0; i < Stations.size() ; i++){
            HashmapArray[i] = new HashMap<>();
        }

        JSONArray jsonArray = obj.getJSONArray("corresp");
        JSONArray list;
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
                addEdge(Stations.indexOf(Correspondance.get(a-1)), Stations.indexOf(Correspondance.get(a)));
            }
        }

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
                    addEdge(Stations.indexOf(Lignes.get(a-1)), Stations.indexOf(Lignes.get(a)));
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
            addEdge(Stations.indexOf(Lignes.get(a-1)), Stations.indexOf(Lignes.get(a)));
        }
        metro = lignes.getJSONObject("7B");
        arrets = metro.getJSONArray("arrets");
        listArrets = (JSONArray) arrets.get(0);
        Lignes.clear();
        for(int j = 0; j < listArrets.length(); j++) {
            Lignes.add(Integer.valueOf((String) listArrets.get(j)));
        }
        for(int a = 1; a < Lignes.size(); a++) {
            addEdge(Stations.indexOf(Lignes.get(a-1)), Stations.indexOf(Lignes.get(a)));
        }
    }

    // Adds an edge to an undirected Graph
    public void addEdge(int src, int dest,int weight)
    {
        // Add an edge from src to dest
        HashmapArray[src].put(dest,weight);

        // Since Graph is undirected, add an edge from dest to src also
        HashmapArray[dest].put(src,weight);
    }

    //print the Graph
    public void printGraph(Graph graph)
    {
        for(int v = 0; v < graph.V; v++)
        {
            System.out.println("Adjacency list of vertex "+ v);
            for(Map.Entry<Integer,Integer> vert: HashmapArray[v].entrySet()){
                System.out.print(" -> "+ vert.getKey() + " + " + vert.getValue());
            }
            System.out.println("\n");
        }
    }
*/

}
