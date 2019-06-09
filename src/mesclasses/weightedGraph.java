package mesclasses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import java.lang.*;
public class weightedGraph {



    public HashMap<String,HashMap<String,Double>> HashmapArray;
    public String path;
    public ArrayList<String> TableStat;

    weightedGraph(ArrayList<String> vertex,String _path){
        this.path = _path;
        HashmapArray = new HashMap<>();
        for(int i = 0; i < vertex.size() ; i++){
            HashmapArray.put(vertex.get(i),new HashMap<>());
        }
    }

    // constructor
    weightedGraph(String _path) throws IOException, JSONException {

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
        TableStat = Stations;

        /* initialization of adjListArray for each station */
        HashmapArray = new HashMap<>();
        for(int i = 0; i < Stations.size() ; i++){
            HashmapArray.put(Stations.get(i),new HashMap<>());
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
                        addEdge((String) list.get(j), (String) list.get(k),getDistance( stat.getJSONObject((String)list.get(j)), stat.getJSONObject((String)list.get(k))));
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
                    addEdge((String) listArrets.get(j), (String) listArrets.get(j+1),getDistance( stat.getJSONObject((String)listArrets.get(j)), stat.getJSONObject((String)listArrets.get(j+1))));
                }
            }
        }

    }



    // Adds an edge to an undirected Graph
    public void addEdge(String src, String dest,Double weight){
        HashmapArray.get(src).put(dest,weight);
        HashmapArray.get(dest).put(src,weight);
    }


    //print the Graph
    public void printGraph() throws IOException, JSONException {
        JSONObject obj = collection.getJSONObjectFromFile("/reseau.json");
        JSONObject stat = obj.getJSONObject("stations");
        Boolean b;

        for(Map.Entry<String,HashMap<String,Double>> vertex: HashmapArray.entrySet()) {
            System.out.print(stat.getJSONObject(vertex.getKey()).getString("nom") + " : [ ");
            b = false;

            for (Map.Entry<String,Double> s : vertex.getValue().entrySet()){
                if (b)
                    System.out.print(" | ");
                System.out.print(stat.getJSONObject(s.getKey()).getString("nom")+ "("+s.getValue().intValue()+"m)");
                b= true;
            }
            System.out.println(" ]");
        }
    }

    public double getDistance2(JSONObject StationSrc, JSONObject StationDest) throws JSONException {
        Double lat1 = Double.parseDouble(StationSrc.getString("lat"));
        Double long1 = Double.parseDouble(StationSrc.getString("lng"));
        Double lat2 = Double.parseDouble(StationDest.getString("lat"));
        Double long2 = Double.parseDouble(StationDest.getString("lng"));

        return Math.sqrt((Math.abs(lat1 - lat2)*Math.abs(long1 - long2)) + (Math.abs(long1 - long2)*Math.abs(lat1 - lat2)));
    }
    double getDistance(JSONObject StationSrc, JSONObject StationDest) throws JSONException {
        Double lat1 = Double.parseDouble(StationSrc.getString("lat"));
        Double lon1 = Double.parseDouble(StationSrc.getString("lng"));
        Double lat2 = Double.parseDouble(StationDest.getString("lat"));
        Double lon2 = Double.parseDouble(StationDest.getString("lng"));

        // Convert degrees to radians
        lat1 = lat1 * Math.PI / 180.0;
        lon1 = lon1 * Math.PI / 180.0;

        lat2 = lat2 * Math.PI / 180.0;
        lon2 = lon2 * Math.PI / 180.0;

        // radius of earth in metres
        double r = 6378100;

        // P
        double rho1 = r * Math.cos(lat1);
        double z1 = r * Math.sin(lat1);
        double x1 = rho1 * Math.cos(lon1);
        double y1 = rho1 * Math.sin(lon1);

        // Q
        double rho2 = r * Math.cos(lat2);
        double z2 = r * Math.sin(lat2);
        double x2 = rho2 * Math.cos(lon2);
        double y2 = rho2 * Math.sin(lon2);

        // Dot product
        double dot = (x1 * x2 + y1 * y2 + z1 * z2);
        double cos_theta = dot / (r * r);

        double theta = Math.acos(cos_theta);

        // Distance in Metres
        return r * theta;
    }

    public void addToEdge(String src, String dest,Double weight){
        Double w = 0.0;
        if (HashmapArray.get(src).get(dest) != null)
            w = HashmapArray.get(src).get(dest);

        HashmapArray.get(src).put(dest,weight+w);
        HashmapArray.get(dest).put(src,weight+w);
    }
}
