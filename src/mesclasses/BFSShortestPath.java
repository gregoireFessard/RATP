package mesclasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import java.util.Scanner;

import mesclasses.graph.Graph;

public class BFSShortestPath {
	
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
