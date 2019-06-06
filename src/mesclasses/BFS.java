package mesclasses;

import java.util.*;

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

	public static boolean isNeighbor(int u, int v)
	{
		if(Graph.adjListArray[u]==null)
			return false;
		return Graph.adjListArray[u].contains(v);

	}

	public static LinkedList<Integer> getOutEdges(int u)
	{
		return Graph.adjListArray[u];
	}

	public static ArrayList<Integer> doBFSShortestPath(Graph graph, int source, int dest)
	{
		ArrayList<Integer> shortestPathList = new ArrayList<Integer>();
		HashMap<Integer, Boolean> visited = new HashMap<Integer, Boolean>();

		if (source == dest)
			return null;
		Queue<Integer> queue = new LinkedList<Integer>();
		Stack<Integer> pathStack = new Stack<Integer>();

		queue.add(source);
		pathStack.add(source);
		visited.put(source, true);

		while(!queue.isEmpty())
		{
			int u = queue.poll();
			LinkedList<Integer> adjList = getOutEdges(u);

			for(int v : adjList)
			{
				if(!visited.containsKey(v))
				{
					queue.add(v);
					visited.put(v, true);
					pathStack.add(v);
					if(u == dest)
						break;
				}
			}
		}


		//To find the path
		int node, currentSrc=dest;
		shortestPathList.add(dest);
		while(!pathStack.isEmpty())
		{
			node = pathStack.pop();
			if(isNeighbor(currentSrc, node))
			{
				shortestPathList.add(node);
				currentSrc = node;
				if(node == source)
					break;
			}
		}

		return shortestPathList;
	}
	public static void findShortestPath(Graph graph, int source, int dest)
	{

		ArrayList<Integer> shortestPathList =  doBFSShortestPath(graph, source, dest);

		System.out.print("[");
		for(int node : shortestPathList)
		{
			System.out.print(node+" ");
		}
		System.out.print("]");
	}
}
