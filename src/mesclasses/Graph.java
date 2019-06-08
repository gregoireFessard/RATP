package mesclasses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Graph {
    // Size of array will be V (number of vertices in Graph)

    public HashMap<String, LinkedList<String>> adjListArray;
    public String path;

    // constructor
    Graph(String _path) throws IOException, JSONException {

        this.path = _path;
        JSONObject obj = collection.getJSONObjectFromFile("/reseau.json");



        /* Initialisation of Stations with the name of each stations    */
        ArrayList<String> Stations = new ArrayList<String>();
        JSONObject stat = obj.getJSONObject("stations");
        String[] names = JSONObject.getNames(stat);
        String type;
        for (String string : names) {
            //for all stations
            type = stat.getJSONObject(string).getString("type");
            //if the station is a metro
            if (type.matches("metro") || type.matches("rer")) {
                //add the station to the list
                Stations.add(string);
            }
        }

        /* initialization of adjListArray for each station */

        adjListArray = new HashMap<>();
        for (int i = 0; i < Stations.size(); i++) {
            adjListArray.put(Stations.get(i), new LinkedList<>());
        }


        /* Adding the correspondance to the graph */
        JSONArray jsonArray = obj.getJSONArray("corresp");
        JSONArray list;
        //trick to remove all RER
        for (int i = 0; i < jsonArray.length(); i++) {
            list = (JSONArray) jsonArray.get(i);
            for (int j = 0; j < list.length()-1; j++) {
                for (int k = j + 1; k < list.length(); k++) {
                    if (Stations.contains(list.get(j)) &&  Stations.contains(list.get(k)))
                        addEdge((String) list.get(j), (String) list.get(k));
                }
            }
        }

        /* Selecting the lignes needed */
        ArrayList<String> ligne = new ArrayList<>();
        for (int i = 1; i < 15; i++) {
            ligne.add(Integer.toString(i));
        }
        ligne.add("3B");
        ligne.add("7B");
        ligne.add("A");
        ligne.add("B");

        /* Adding the lignes to the graph */
        JSONObject lignesJson = obj.getJSONObject("lignes");
        JSONArray listArrets;
        JSONArray arrets;

        for (String s : ligne) {
            arrets = lignesJson.getJSONObject(s).getJSONArray("arrets");
            for (int k = 0; k < arrets.length(); k++) {
                listArrets = (JSONArray) arrets.get(k);
                for (int j = 0; j < listArrets.length()-1; j++) {
                    addEdge((String)listArrets.get(j), (String) listArrets.get(j+1));
                }
            }
        }
    }


    // Adds an edge to an undirected Graph
    public void addEdge(String src, String dest) throws IOException, JSONException {
        // Add an edge from src to dest
            adjListArray.get(src).add(dest);
            // Since Graph is undirected, add an edge from dest to src also
            adjListArray.get(dest).add(src);
    }

    //print the Graph
    public void printGraph() throws IOException, JSONException {
        JSONObject obj = collection.getJSONObjectFromFile("/reseau.json");
        JSONObject stat = obj.getJSONObject("stations");

        for (Map.Entry<String, LinkedList<String>> vertex : adjListArray.entrySet()) {
            System.out.println("Stations near : " + stat.getJSONObject(vertex.getKey()).getString("nom"));
            for (String s : vertex.getValue()) {
                System.out.print(stat.getJSONObject(s).getString("nom") + " + ");
            }
            System.out.println("\n");
        }
    }
}