package spath;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import heaps.Decreaser;
import heaps.MinHeap;
import spath.graphs.DirectedGraph;
import spath.graphs.Edge;
import spath.graphs.Vertex;
import spath.VertexAndDist;


// SHORTESTPATHS.JAVA
// Compute shortest paths in a graph.
//
// Your constructor should compute the actual shortest paths and
// maintain all the information needed to reconstruct them.  The
// returnPath() function should use this information to return the
// appropriate path of edge ID's from the start to the given end.
//
// Note that the start and end ID's should be mapped to vertices using
// the graph's get() function.
//
// You can ignore the input and startTime arguments to the constructor
// unless you are doing the extra credit.
//
public class ShortestPaths {
	private final static Integer inf = Integer.MAX_VALUE;
	private HashMap<Vertex, Decreaser<VertexAndDist>> map;
	private HashMap<Vertex, Edge> toEdge;
	private Map<Edge, Integer> weights;
	private Vertex startVertex;
	private final DirectedGraph g;


	//
	// constructor
	//
	public ShortestPaths(DirectedGraph g, Vertex startVertex, Map<Edge,Integer> weights) {

		this.map         = new HashMap<Vertex, Decreaser<VertexAndDist>>();
		this.toEdge      = new HashMap<Vertex, Edge>();    //edges that were taken to get to the vertex shortest path
		this.weights     = weights;
		this.startVertex = startVertex;
		this.g           = g;
	}

	//
	// this method does all the real work
	//
	public void run() {
		MinHeap<VertexAndDist> pq = new MinHeap<VertexAndDist>(30000);

		//
		// Initially all vertices are placed in the heap
		//   infinitely far away from the start vertex
		//

		for (Vertex v : g.vertices()) {
			toEdge.put(v, null);
			VertexAndDist a = new VertexAndDist(v, inf);
			Decreaser<VertexAndDist> d = pq.insert(a);
			map.put(v, d);
		}


		//
		// Now we decrease the start node's distance from
		//   itself to 0.
		// Note how we look up the decreaser using the map...
		// 
		Decreaser<VertexAndDist> startVertDist = map.get(startVertex);
		//
		// and then decrease it using the Decreaser handle
		//
		startVertDist.decrease(startVertDist.getValue().sameVertexNewDistance(0));





		while(!pq.isEmpty()){
			VertexAndDist a = pq.extractMin();

			int stDist= a.getDistance() ; 

			for (Edge e : a.getVertex().edgesFrom()) {

				Vertex b = e.to;

				int weight =weights.get(e);
				Decreaser<VertexAndDist> bDist = map.get(b);
				if (bDist.getValue().getDistance() == inf) ; 
				if ((stDist+weight)<bDist.getValue().getDistance())    {

					bDist.decrease(bDist.getValue().sameVertexNewDistance(stDist+weight));
					toEdge.put(b, e);


				}

			}

		}

	}


	/**
	 * Return a List of Edges forming a shortest path from the
	 *    startVertex to the specified endVertex.  Do this by tracing
	 *    backwards from the endVertex, using the map you maintain
	 *    during the shortest path algorithm that indicates which
	 *    Edge is used to reach a Vertex on a shortest path from the
	 *    startVertex.
	 * @param endVertex 
	 * @return
	 */
	public LinkedList<Edge> returnPath(Vertex endVertex) {
		LinkedList<Edge> path = new LinkedList<Edge>();
		Vertex crtVertex= endVertex;


		while((map.get(crtVertex).getValue().getDistance()) > 0){

			path.addFirst(toEdge.get(crtVertex));

			crtVertex=toEdge.get(crtVertex).from;

		}

		return path;
	}

	/**
	 * Return the length of all shortest paths.  This method
	 *    is completed for you, using your solution to returnPath.
	 * @param endVertex
	 * @return
	 */
	public int returnValue(Vertex endVertex) {
		LinkedList<Edge> path = returnPath(endVertex);
		int pathValue = 0;
		for(Edge e : path) {
			pathValue += weights.get(e);
		}

		return pathValue;

	}
}
