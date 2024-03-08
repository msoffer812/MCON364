package hw4;
import java.util.*;
public interface GraphInterface<T> {
	boolean isEmpty();
	void addVertex(T vertex);
	boolean hasVertex(T vertexString);
	Queue<T> getToVertices(int vertexKey);
	void markVertex(T vertex);
	void clearAllMarks();
	void addEdge(int vertexKey1, int vertexKey2);
	boolean isMarked(T vertex);
	String getPath(int startVertex, int endVertex);
	int size();
	String getAdjacencyTable();
}
