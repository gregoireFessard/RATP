package mesclasses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class Dijkstra {

    public HashMap<String,String> parentNodes;
    public HashMap<String, Double> distance;

    public Dijkstra(String sourceVertex, HashMap<String,HashMap<String,Double>> HashmapArray) {

        parentNodes = new HashMap<>();

        // Creates a new empty HashSet object to store the unvisited vertices
        //
        HashSet unvisitedVertices = new HashSet();

        // Initializes the unvisited set with all the vertices
        for (Map.Entry<String, HashMap<String, Double>> vertex : HashmapArray.entrySet()) {
            unvisitedVertices.add(vertex.getKey());
        }

        // Creates a new empty HashMap object to store the distance to each vertex
        // (key=vertex, value=distance)
        //
        distance = new HashMap();

        // Initializes all the distances to "infinity" (use the value
        // Integer.MAX_VALUE for "infinity")
        for (Map.Entry<String, HashMap<String, Double>> vertex : HashmapArray.entrySet()) {
            distance.put(vertex.getKey(), Double.MAX_VALUE);
        }

        // Initializes tree set
        // override the comparator to do the sorting based keys

        TreeSet treeSet = new TreeSet(new PairComparator());

        // Initialises for the sourceVertex
        // Sets the distance for the sourceVertex (in distance) to 0
        distance.put(sourceVertex, 0.0);

        // Creates a new pair (class SimpleEntry) of (distance, vertex) for the sourceVertex
        //
        AbstractMap.SimpleEntry p0 = new AbstractMap.SimpleEntry(distance.get(sourceVertex), sourceVertex);

        // Adds the pair to the treeSet
        treeSet.add(p0);

        // while tree set is not empty
        while (!treeSet.isEmpty()) {
            // Finds and removes the (distance, vertex) pair (class SimpleEntry) with the minimum distance
            // in the treeSet
            //
            AbstractMap.SimpleEntry extractedPair = (AbstractMap.SimpleEntry) treeSet.pollFirst();

            // Get the vertex from the (distance, vertex) pair (it is in the pair value)
            //
            String extractedVertex = (String) extractedPair.getValue();

            // Only if the extracted vertex is in the unvisited set do the rest ...
            if (unvisitedVertices.contains(extractedVertex)) {
                // Removes the extracted vertex from the unvisited set (i.e. mark it as
                // visited)
                unvisitedVertices.remove(extractedVertex);

                // Takes all the adjacent vertices
                //
                HashMap<String, Double> list = HashmapArray.get(extractedVertex);

                // iterate over every neighbor/adjacent vertex
                for (Map.Entry<String, Double> vertex : list.entrySet()) {

                    // Gets the corresponding Edge object
                    //

                    // Gets the Edge destination vertex
                    String destination = vertex.getKey();

                    // Only if the destination vertex is in the unvisited set, do the rest
                    if (unvisitedVertices.contains(destination)) {
                        // Gets the current distance of the destination vertex
                        //
                        Double currentDistance = (Double) distance.get(destination);

                        // Calculates the new distance via extractedVertex and the edge.weight
                        //
                        Double newDistance = (Double) distance.get(extractedVertex) + vertex.getValue();

                        // If the newDistance is less than the currentDistance, update
                        if (newDistance < currentDistance) {
                            // Creates a new pair (SimpleEntry object) for (newDistance, destination)
                            //
                            parentNodes.put(destination,extractedVertex);

                            AbstractMap.SimpleEntry p = new AbstractMap.SimpleEntry(newDistance, destination);

                            // Adds the pair object to the treeSet
                            treeSet.add(p);

                            // Updates the distance HashMap for the destination vertex to the
                            // newDistance
                            distance.put(destination, newDistance);
                        }
                    }
                }
            }
        }
        //printDijkstra(distance, sourceVertex);
    }

    class PairComparator implements Comparator {

        // Implements the compare method. o1 and o2 are really instances of
        // java.util.AbstractMap.SimpleEntry where the key and the value are both of type int. Returns the
        // equivalent of o1.key - o2.key to implement an ascending value ordering.
        //
        @Override
        public int compare(Object o1, Object o2) {
            // sort using distance values

            Double key1 = (Double) ((AbstractMap.SimpleEntry) o1).getKey();
            Double key2 = (Double) ((AbstractMap.SimpleEntry) o2).getKey();
            if (key1 < key2){
                return -1;
            }
            else{
                return 1;
            }
            return 0;
        }
    }

    public void printDijkstra(HashMap<String, Double> distance, String sourceVertex) {
        System.out.println("Dijkstra Algorithm: (Adjacency List + TreeSet)");
        for (Map.Entry<String, Double> vertex : distance.entrySet()) {
            System.out.println(
                    "Source Vertex: " + sourceVertex + " to vertex " + vertex.getKey() + " distance: " + distance.get(vertex.getKey()));
        }

    }

    public void PrintShortestPath(String sourceVertex, String destVertex) throws IOException, JSONException {
        JSONObject obj = collection.getJSONObjectFromFile("/reseau.json");
        JSONObject stat = obj.getJSONObject("stations");


        ArrayList<String> shortestPathList = GetShortestPath(sourceVertex, destVertex);

        System.out.print("\n \n");
        System.out.println("Djikstra : Fastest path from " + stat.getJSONObject(sourceVertex).getString("nom") + " to " + stat.getJSONObject(destVertex).getString("nom") + " - Total distance : " + distance.get(destVertex).intValue() + "m");

        Double dist =0.0;
        for(String node : shortestPathList)
        {
            System.out.print(" ->");
            System.out.print(stat.getJSONObject(node).getString("nom"));
            JSONObject lignes  = stat.getJSONObject(node).getJSONObject("lignes");
            String[] names = JSONObject.getNames(lignes);
            for (String ligneName : names)
            {
                JSONArray metrer =  lignes.getJSONArray(ligneName);
                for (int i=0;i<metrer.length();i++)
                {
                    System.out.print( "(" +ligneName + " " +  metrer.get(i) + ")");
                }
            }
            System.out.print(" distance :" +  (distance.get(node).intValue() - dist.intValue()) + "m");
            dist = distance.get(node);
            System.out.print("\n");
        }
        System.out.print("\n \n");
    }
    public ArrayList<String> GetShortestPath(String sourceVertex, String destVertex) throws IOException, JSONException {
        ArrayList<String> shortestPathList = new ArrayList<String>();
        shortestPathList.add(destVertex);

        String currentSrc = destVertex;
        while (!sourceVertex.matches(currentSrc)) {
            currentSrc = parentNodes.get(currentSrc);
            shortestPathList.add(0,currentSrc);
        }
        return shortestPathList;
    }
}
