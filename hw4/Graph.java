package hw4;
import java.util.*;
public class Graph<T> implements GraphInterface<T> {
    private LinkedHashMap<T, List<T>> edges;
    private Map<Integer, T> vertices;//Get vertice by numbering
    private Hasher<T, T> hasher;
    private Set<T> seen;
    
    public Graph(Hasher<T, T> hasher) {
        this.edges = new LinkedHashMap<T, List<T>>();
        this.seen = new HashSet<>();
        this.hasher = hasher;
        this.vertices = new LinkedHashMap<>();
    }

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}
	
	@Override
	public int size() {
		return this.vertices.size();
	}
	
	/**
	 * Adds a vertex to the graph if it doesn't already exist
	 */
	@Override
	public void addVertex(T vertex) {
		if(!edges.containsKey(vertex)) {
			this.edges.put(vertex, new ArrayList<>());
			this.vertices.put(vertices.size() + 1, vertex);//Put into map so can retrieve based on key
		}
		
	}
	
	/**
	 * @return whether or not the map contains this specific value as a vertex.
	 */
	@Override
	public boolean hasVertex(T vertex) {
		return this.edges.containsKey(vertex);
	}
	
	/**
	 * Given a startvertex and an endvertex, @return the path between the two
	 */
	@Override
	public String getPath(int startVertexKey, int endVertexKey) {
		/*
		 * make sure the graph contains the keys
		 */
		if(vertices.containsKey(startVertexKey) && vertices.containsKey(endVertexKey)) {
			T startVertex = vertices.get(startVertexKey);
			T endVertex = vertices.get(endVertexKey);
			MapInterface<T, T> mapOfParents = getParents(startVertex, endVertex);
			if(mapOfParents == null) {
				System.out.println("No path was found, returning just that.");
				return "No path found";
			}
			System.out.println("Calling the method that parses the path from child vertex to parent"
					+ " and returns it as a string.");
			return parsePath(mapOfParents, startVertex, endVertex);
		}
		System.out.println("Graph doesn't contain these indices");
		return "Path not found";
	}
	
	/**
	 * put map from endVertex to startVertex into a stack, then into a 
	 * stringBuilder to it's in the right order
	 * @param mapOfParents
	 * @param startVertex
	 * @param endVertex
	 * @return string rep of path obtained from map
	 */
	private String parsePath(MapInterface<T, T> mapOfParents, T startVertex, T endVertex) {
		StringBuilder str = new StringBuilder();
		T curr = endVertex;
		int loops = 0;
		System.out.println("Creating a stack - we will push all the nodes from endVertex "
				+ "to startVertex, and then reverse it by going through the stack");
		Stack<T> stack = new Stack<>();
		/**
		 * put in some logic to loop until found the parent or we've gone through the graph
		 * and haven't found the correct path
		 */
		System.out.println("Starting loop - will push all vertices "
				+ "from startindex to endindex onto a stack, only ending when the "
				+ "original startindex has been found or we've exhausted the map of vertices");
		while(curr != startVertex && loops<this.edges.size()) {
			loops++;
			System.out.println("Pushing curr - [" + curr.toString() + "] onto the stack");
			stack.push(curr);
			curr = mapOfParents.get(curr);
			System.out.println("Curr is now [" + curr.toString() + "]");
		}
		System.out.println("Loop has ended. Now we'll append it to a stringbuilder and return.");
		str.append(startVertex.toString() + "->");
		while(!stack.isEmpty()) {
			curr = stack.pop();
			System.out.println("Current node - [" + curr.toString() + "]");
			str.append(curr.toString());
			if(!stack.isEmpty()) {
				str.append("->");
			}
		}
		return str.toString();
	}
	/**
	 * @return a visual rep of the list of people 
	 * and their connections
	 */
	@Override
	public String getAdjacencyTable() {
	    StringBuilder table = new StringBuilder();
	    /*
	     * Go through each node in the graph
	     */
	    for (int key : this.vertices.keySet()) {
	    	/*
	    	 * First append the person themselves
	    	 */
	    	T node = vertices.get(key);
	    	table.append(key + ". [" + node.toString() + "] :");
	    	/*
	    	 * Then append the children of the node
	    	 */
	    	for(T child:edges.get(node)) {
	    		table.append(" [" + child.toString() + "] ");
	    	}
	    	table.append("\n");
	    }
	    return table.toString();
	}
	
	/**
	 * 
	 * @param startVertex
	 * @param endVertex
	 * @return a map of children mapped to parent - 
	 * so we can retrace the path
	 */
	private MapInterface<T, T> getParents(T startVertex, T endVertex){
		System.out.println("Going through algorithm to map all children to parents using a bfs");
		MapInterface<T, T> parents = new MyHashMap((edges.size()*2), hasher); 
		Queue<T> queue = new LinkedList<>();
		System.out.println("A queue to hold all the nodes on the current level has been created, "
				+ "adding the startVertex to it as the first level, and marking startvertex as seen.");
		queue.add(startVertex);
		markVertex(startVertex);
		while(!queue.isEmpty()) {
			int size = queue.size();
			System.out.println("There are " + size + " nodes on this level; now we will go through them:");
			for(int i=0;i<size;i++) {
				T node = queue.remove();
				System.out.println("["  + node.toString() + "] removed from the queue to inspect.");
				List<T> children = edges.get(node);
				for(T child:children) {
					System.out.println("Current child: [" + child.toString() + "]");
					if(!isMarked(child)) {
						System.out.println("Child node not inspected, "
								+ "adding to queue to examine children on next level and marking as seen.");
						parents.put(child, node);
						markVertex(child);
						queue.add(child);
						if(child.equals(endVertex)) {
							System.out.println("Child vertex found! Returning map of children to parent.");
							return parents;
						}
					}else {
						System.out.println("Child node already examined, moving on the the next one.");
					}
				}
			}
		}
		clearAllMarks();
		/*
		 * We never found the endvertex, so return null
		 */
		System.out.println("[" + endVertex.toString() + "] not found, returning null.");
		return null;
	}
	/**
	 * Return all the vertices that connect to central vertex
	 * either directly or indirectly
	 */
	@Override
	public Queue<T> getToVertices(int vertexKey) {
		T vertex = vertices.get(vertexKey);
		System.out.println("Initializing a queue to hold all the vertices that connect"
				+ " to the given vertex, in a queue");
		Queue<T> finalLocations = new LinkedList<>();
		Queue<T> queue = new LinkedList<>();
		queue.add(vertex);
		markVertex(vertex);
		while(!queue.isEmpty()) {
			int size = queue.size();
			System.out.println("There are " + size + " nodes at this level.");
			for(int i=0;i<size;i++) {
				T node = queue.remove();
				System.out.println("Current Node: [" + node.toString() + "]");
				List<T> children = edges.get(node);
				for(T child:children) {
					System.out.println("Current Child: [" + node.toString() + "]");
					if(!isMarked(child)) {
						System.out.println("Child node not inspected, "
								+ "adding to queue to examine children on next level and marking as seen.");
						finalLocations.add(child);
						markVertex(child);
						queue.add(child);
					}else {
						System.out.println("Child node already examined, moving on the the next one.");
					}
				}
			}
		}
		clearAllMarks();
		System.out.println("Returning vertices");
		return finalLocations;
	}

	@Override
	public void markVertex(T vertex) {
		this.seen.add(vertex);
	}

	@Override
	public void clearAllMarks() {
		this.seen.clear();
	}

	@Override
	public void addEdge(int vertexKey1, int vertexKey2) {
		if(vertices.containsKey(vertexKey1) && vertices.containsKey(vertexKey2)) {
			T vertex1 = vertices.get(vertexKey1);
			T vertex2 = vertices.get(vertexKey2);
			/*
			 * make sure there isn't already a connection before you put in one
			 */
			List<T> connections = edges.get(vertex1);
			if(!connections.contains(vertex2)){
				connections.add(vertex2);
			}
			connections = edges.get(vertex2);
			if(!connections.contains(vertex1)){
				connections.add(vertex1);
			}
		}
	}

	@Override
	public boolean isMarked(T vertex) {
		return this.seen.contains(vertex);
	}
}
