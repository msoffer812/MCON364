package hw9;

public interface MapInterface{
	public void put(String key);			/* Inserts the key, a string, either putting in a place or incrementing the int count */
	public int get(String key);				/* Returns the count for how many times the string was inserted */
	public int remove(String key);			/* Removes a word from the map, returning its int value ( count of insertion) */
	public boolean contains(String key);	/* Searches for the string, return true if the map contains it */
	public boolean isEmpty();				/* Return if the map is empty - no isFull because it can hold more and more */
	public int size();						/* Return the size of the map - the amount of string entries */
}
