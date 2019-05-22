package mesclasses;
import java.io.IOException;
import java.util.ArrayList;
import org.json.*;
import mesclasses.graph.Graph;
public class Data {
	
	//here we create an arrayList with all the correspondance
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
