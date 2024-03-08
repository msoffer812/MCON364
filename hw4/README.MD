# Graph Class README

## Description
The Graph class is a generic implementation of a graph data structure. It allows for the storage and manipulation of vertices and edges, and provides methods for performing various operations on the graph.

## Public Methods

### `isEmpty()`
Returns true if the graph is empty (contains no vertices), false otherwise.

### `size()`
Returns the number of vertices in the graph.

### `addVertex(T vertex)`
Adds a vertex to the graph if it does not already exist.

### `hasVertex(T vertex)`
Checks if the graph contains a specific vertex.

### `getPath(int startVertexKey, int endVertexKey)`
Given start and end vertex keys, returns the path between them as a string.

### `getAdjacencyTable()`
Returns a visual representation of the graph, showing vertices and their connections.

### `getToVertices(int vertexKey)`
Returns a queue of vertices connected to the given vertex, either directly or indirectly.

### `markVertex(T vertex)`
Marks a vertex as seen.

### `clearAllMarks()`
Clears all marks from vertices.

### `addEdge(int vertexKey1, int vertexKey2)`
Adds an edge between two vertices specified by their keys.

### `isMarked(T vertex)`
Checks if a vertex is marked as seen.

## Constructor
### `Graph(Hasher<T, T> hasher)`
Initializes a new graph with a custom Hasher object for hashing vertices.

## Private Methods

### `parsePath(MapInterface<T, T> mapOfParents, T startVertex, T endVertex)`
Parses the path from end vertex to start vertex and returns it as a string.

### `getParents(T startVertex, T endVertex)`
Returns a map of children mapped to their parent vertices for retracing paths.

## Fields

### `private LinkedHashMap<T, List<T>> edges`
Stores the edges of the graph.

### `private Map<Integer, T> vertices`
Stores the vertices of the graph with numeric keys for retrieval.

### `private Hasher<T, T> hasher`
Custom Hasher object for hashing vertices.

### `private Set<T> seen`
Set of vertices that have been marked as seen during traversal.
