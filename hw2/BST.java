package hw2;
import java.util.*;
public interface BST<T extends Comparable<T>> {
	T min();
	T max();
	int size();
	boolean isEmpty();
	Iterator<T> getIterator(Traversal t);
	void add(T value);
	T remove(T value);
	T get(T value);
	boolean contains(T value);
}
