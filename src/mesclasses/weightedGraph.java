package mesclasses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class weightedGraph {



    public static HashMap<String,HashMap<String,Integer>> HashmapArray;


    // constructor
    weightedGraph() throws IOException, JSONException {
        int weight = 1;


        ArrayList<String> Stations = new ArrayList<>();
        JSONObject obj = collection.getJSONObjectFromFile("/reseau.json");

        /* Initialisation of Stations with the name of each stations    */
        JSONObject stat = obj.getJSONObject("stations");
        String[] names = JSONObject.getNames(stat);
        String type;
        for(String string : names) {
            //for all stations
            type = stat.getJSONObject(string).getString("type");
            //if the station is a metro
            if (type.matches("metro")){
                //add the station to the list
                Stations.add(string);
            }
        }
        //1850 is an exception for us, it's supposed to be a metro but in the JSON it's a rer
        //so we had to add it manually
        Stations.add("1850");


        /* initialization of adjListArray for each station */
        HashmapArray = new HashMap<>();
        for(int i = 0; i < Stations.size() ; i++){
            HashmapArray.put(Stations.get(i),new HashMap<>());
        }


        /* Adding the correspondance to the graph */
        JSONArray jsonArray = obj.getJSONArray("corresp");
        JSONArray list;
        //trick to remove all RER
        String corr;
        ArrayList<String> Correspondance = new ArrayList();
        for(int i = 0; i < jsonArray.length(); i++) {
            list = (JSONArray) jsonArray.get(i);
            for(int j = 0; j < list.length(); j++) {
                corr = (String) list.get(j);
                //here is the filter to keep only metro
                if (!corr.matches("A" + "(.*)") && !corr.matches("B" + "(.*)")) {
                    Correspondance.add(corr);	//convert to int
                }
            }
        }
        for(int a = 1; a < Correspondance.size(); a++) {
            //creating edge with this trick :
            //get 2 stations in Correspondance which must be bound
            //found their index in the arraylist station
            //Link them with their own index
            if (Stations.contains(Correspondance.get(a-1)) && (Stations.contains(Correspondance.get(a)))){
                addEdge(Correspondance.get(a-1), Correspondance.get(a),weight);
            }
        }

        JSONObject lignes = obj.getJSONObject("lignes");
        JSONObject metro = new JSONObject();
        JSONArray arrets = new JSONArray();
        JSONArray listArrets = new JSONArray();
        ArrayList<String> Lignes = new ArrayList();
        for(int i = 1; i < 15; i++) {
            metro = lignes.getJSONObject(Integer.toString(i));
            arrets = metro.getJSONArray("arrets");
            for(int k = 0; k < arrets.length(); k++) {
                //loop for "lignes"
                listArrets = (JSONArray) arrets.get(k);
                for(int j = 0; j < listArrets.length(); j++) {
                    //loop for stations metro
                    Lignes.add((String) listArrets.get(j));
                }
                for(int a = 1; a < Lignes.size(); a++) {
                    //create edge
                    addEdge(Lignes.get(a-1), Lignes.get(a),weight);
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
            Lignes.add((String) listArrets.get(j));
        }
        for(int a = 1; a < Lignes.size(); a++) {
            addEdge(Lignes.get(a-1), Lignes.get(a),weight);
        }
        metro = lignes.getJSONObject("7B");
        arrets = metro.getJSONArray("arrets");
        listArrets = (JSONArray) arrets.get(0);
        Lignes.clear();
        for(int j = 0; j < listArrets.length(); j++) {
            Lignes.add((String) listArrets.get(j));
        }
        for(int a = 1; a < Lignes.size(); a++) {
            addEdge(Lignes.get(a-1), Lignes.get(a),weight);
        }

    }


    // Adds an edge to an undirected Graph
    public void addEdge(String src, String dest,int weight)
    {
        // Add an edge from src to dest
        HashmapArray.get(src).put(dest,weight);

        // Since Graph is undirected, add an edge from dest to src also
        HashmapArray.get(dest).put(src,weight);
    }

    //print the Graph
    public void printGraph() throws IOException, JSONException {
/*        JSONObject obj = collection.getJSONObjectFromFile("/reseau.json");
        JSONObject stat = obj.getJSONObject("stations");

        for(Map.Entry<String,HashMap<>> vertex: HashmapArray.entrySet()) {
            System.out.println("Stations near : "+ stat.getJSONObject(vertex.getKey()).getString("nom"));
            for (String s : vertex.getValue()){
                System.out.print(stat.getJSONObject(s).getString("nom")+ " + ");
            }
            System.out.println("\n");
        }*/
    }
}
