package mesclasses;

import java.util.*;

public class Dijkstra {

    public void Dijkstra(String sourceVertex, HashMap<String, HashMap<String, Double>> HashmapArray) {
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
        HashMap<String, Double> distance = new HashMap();

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
        AbstractMap.SimpleEntry p0 = new AbstractMap.SimpleEntry(distance.get(sourceVertex), 0);

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
        printDijkstra(distance, sourceVertex);
    }

    static class PairComparator implements Comparator {

        // Implements the compare method. o1 and o2 are really instances of
        // java.util.AbstractMap.SimpleEntry where the key and the value are both of type int. Returns the
        // equivalent of o1.key - o2.key to implement an ascending value ordering.
        //
        @Override
        public int compare(Object o1, Object o2) {
            // sort using distance values

            int key1 = (int) ((AbstractMap.SimpleEntry) o1).getKey();
            int key2 = (int) ((AbstractMap.SimpleEntry) o2).getKey();
            return key1 - key2;
        }
    }

    public void printDijkstra(HashMap<String, Double> distance, String sourceVertex) {
        System.out.println("Dijkstra Algorithm: (Adjacency List + TreeSet)");
        for (Map.Entry<String, Double> vertex : distance.entrySet()) {
            System.out.println(
                    "Source Vertex: " + sourceVertex + " to vertex " + vertex.getKey() + " distance: " + distance.get(vertex.getKey()));
        }
    }

    public void toVertex(HashMap distance, String sourceVertex, String destVertex) {
        System.out.println("The shortest distance form " + sourceVertex + " to " + destVertex + " is : " + distance.get(destVertex));
    }

}
