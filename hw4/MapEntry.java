package hw4;

public class MapEntry<K, V>{
	private K key;		/* The key, aka the word being counted */
	private V value;		/* The value, aka the count of how many times the word was inserted */
	private MapEntry next;	/* The next entry, for linking the list */
	private int lengthOfLinkedList;
	
	public MapEntry(K key, V value, MapEntry<K, V> next) {
		this.key = key; /* Converts the key to lower case so all value comparisons don't take into account case */
		this.value = value;
		this.next = next;
		this.lengthOfLinkedList = 1;
		countTotalLength(next);
	}
	/**
	 * count the amount of words in the linkedList
	 */
	private void countTotalLength(MapEntry<K, V> entry) {
		while(entry != null) {
			this.lengthOfLinkedList++;
			entry = entry.next();
		}
	}
	
	/*
	 * Overloaded constructor for making a new entry without a 
	 * next
	 */
	public MapEntry(K key, V value) {
		this.key = key;
		this.value = value;
		this.next = null;
		this.lengthOfLinkedList = 1;
	}
	
	/**
	 * Setter for setting the next
	 * @param next
	 */
	public void setNext(MapEntry<K, V> next) {
		this.next = next;
		this.lengthOfLinkedList = 1;
		countTotalLength(next);
	}
	
	/**
	 * Setter for setting the next
	 * @param next
	 */
	public void setValue(V value) {
		this.value = value;
	}
	/**
	 * The getters
	 * @return the various values
	 */
	public K key() {
		return this.key;
	}
	
	public V value() {
		return this.value;
	}
	
	/**
	 * 
	 * @param word
	 * @return if anywhere down the line the entry contains the word
	 */
	public boolean contains(K key) {
		System.out.println("Searching for key in this chain:");
		MapEntry<K, V> curr = this;
		while(curr != null) {
			System.out.println("Curr:" + curr.toString());
			if(curr.key().equals(key)) {
				System.out.println("Key found");
				return true;
			}
			curr = curr.next();
		}
		return false;
	}
	public int linkedListSize() {
		return this.lengthOfLinkedList;
	}
	
	public MapEntry<K, V> next() {
		return this.next;
	}
	
	public boolean hasNext() {
		return next() != null;
	}
	
	
	@Override
	public String toString() {
		MapEntry<K, V> curr = this;
		StringBuilder str = new StringBuilder();
		while(curr != null) {
			str.append(curr.key().toString() + ": " + curr.value().toString() + "\n");
			curr = curr.next();
		}
		return str.toString();
	}
}
