package mesclasses;

import java.io.IOException;
import java.util.*;

import mesclasses.Graph;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BFS {

	public static ArrayList<String> doBFSShortestPath(Graph graph, String source, String dest)
	{
		if (source == dest)
			return null;

		Queue<String> queue = new LinkedList<String>();
		HashMap<String,String> parentNodes = new HashMap<>();
        HashSet visitedVertices = new HashSet();

		queue.add(source);
        visitedVertices.add(source);

		loop:
		while(!queue.isEmpty())
        {
            String u = queue.poll();
			LinkedList<String> adjList = graph.adjListArray.get(u);
			for(String v : adjList)
			{
				if(!visitedVertices.contains(v))
				{
					queue.add(v);
                    visitedVertices.add(v);
                    parentNodes.put(v,u);
					if(v == dest)
						break loop;
				}
			}
		}

		//To find the path, we backtrack

        ArrayList<String> shortestPathList = new ArrayList<String>();
		shortestPathList.add(dest);

        String currentSrc = dest;
		while (!source.matches(currentSrc)) {
            currentSrc = parentNodes.get(currentSrc);
            shortestPathList.add(0,currentSrc);
        }

		return shortestPathList;
	}


	public static void findShortestPath(Graph graph, String source, String dest) throws IOException, JSONException {
        JSONObject obj = collection.getJSONObjectFromFile("/reseau.json");
        JSONObject stat = obj.getJSONObject("stations");

		ArrayList<String> shortestPathList =  doBFSShortestPath(graph, source, dest);
		System.out.print("[");
		for(String node : shortestPathList)
		{
            System.out.print(stat.getJSONObject(node).getString("nom"));
            JSONObject lignes  = stat.getJSONObject(node).getJSONObject("lignes");
            String[] names = JSONObject.getNames(lignes);
            for (String ligneName : names)
            {
                System.out.print( ":" +ligneName);
                JSONArray metrer =  lignes.getJSONArray(ligneName);
                for (int i=0;i<metrer.length();i++)
                {
                    System.out.print( "/" +metrer.get(i));
                }

            }
            System.out.print("->");
		}
		System.out.print("]");
	}
}
