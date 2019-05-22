package mesclasses;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import mesclasses.graph.Graph;

public class BFS {
	
	public static ArrayList<Integer> bfsUtil(Graph G, int start, int[] prev, int[] dist, boolean[] mark) {

	// prints BFS traversal from a given source s
	
		// Create a queue for BFS 
		LinkedList<Integer> queue = new LinkedList<Integer>(); 
		ArrayList<Integer> ListResult = new ArrayList<Integer>();
		// Mark the current node as visited and enqueue it 
		mark[start]=true;
		queue.add(start);

		while (queue.size() != 0) { 
			// Dequeue a vertex from queue and print it 
			start = queue.poll();
			ListResult.add(start);
	
			// Get all adjacent vertices of the dequeued vertex s 
			// If a adjacent has not been visited, then mark it 
			// visited and enqueue it 
			Iterator<Integer> i = Graph.adjListArray[start].listIterator();
			while (i.hasNext()) {	
				int cptVertex = 1;	//number of vertex before i
    			int n = i.next();
    			if (!mark[n]) { 
    				mark[n] = true;	//if the vertex is visited
    				dist[n] = n-cptVertex;	//distance in number of vertex
    				cptVertex = cptVertex + 1;
    				queue.add(n); 
    			}
			}
		}
		return ListResult;
	}

	static void bfs(Graph G, int start, int end, ArrayList<Integer> St) {
		boolean marked[] = new boolean[St.size()]; //set to true if v has been visited
	    int previous[] = new int[St.size()-1]; //indicates the preceding vertex
	    int distance[] = new int[St.size()]; //represents the distance between s and v
		ArrayList<Integer> ListResult = bfsUtil(G, start, previous, distance, marked);
		ListResult.add(end);
		System.out.println(ListResult);
		int Cpt = 1;
		previous[Cpt] = start;
		while (Cpt != end) {
			Cpt += 1;
			previous[Cpt] = ListResult.get(Cpt-1);
		}
	}
}
