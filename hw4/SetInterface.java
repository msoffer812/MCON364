package hw4;

public interface SetInterface<K> {
	public void put(K key);			/* Inserts the key, a string, either putting in a place or incrementing the int count */
	public K get(K key);				/* Returns the count for how many times the string was inserted */
	public K remove(K key);			/* Removes a word from the map, returning its int value ( count of insertion) */
	boolean contains(K key);	/* Searches for the string, return true if the map contains it */
	boolean isEmpty();				/* Return if the map is empty - no isFull because it can hold more and more */
	int size();
}
