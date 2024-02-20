package hw3;

public class MapEntry implements Comparable<MapEntry>{
	private String key;		/* The key, aka the word being counted */
	private int count;		/* The value, aka the count of how many times the word was inserted */
	private MapEntry next;	/* The next entry, for linking the list */
	private int totalCount;
	private int lengthOfLinkedList;
	
	public MapEntry(String key, int count, MapEntry next) {
		this.key = key.toLowerCase(); /* Converts the key to lower case so all value comparisons don't take into account case */
		this.count = count;
		this.next = next;
		this.totalCount = count;
		this.lengthOfLinkedList = 1;
		countTotalWords(next);
	}
	/**
	 * count the amount of words in the linkedList
	 */
	private void countTotalWords(MapEntry entry) {
		while(entry != null) {
			this.totalCount += entry.count();
			this.lengthOfLinkedList++;
			entry = entry.next();
		}
	}
	
	/*
	 * Overloaded constructor for making a new entry without a 
	 * next
	 */
	public MapEntry(String key, int count) {
		this.key = key.toLowerCase();
		this.count = count;
		this.next = null;
		this.lengthOfLinkedList = 1;
		this.totalCount = count;
	}
	
	public void incrementCount(int num) {
		count += num;
		totalCount+=num;
	}
	/**
	 * Setter for setting the next
	 * @param next
	 */
	public void setNext(MapEntry next) {
		this.next = next;
		this.totalCount = count;
		this.lengthOfLinkedList = 1;
		countTotalWords(next);
	}
	/**
	 * The getters
	 * @return the various values
	 */
	public String key() {
		return this.key;
	}
	
	public int count() {
		return this.count;
	}
	
	/**
	 * 
	 * @param word
	 * @return if anywhere down the line the entry contains the word
	 */
	public boolean contains(String word) {
		MapEntry curr = this;
		while(curr != null) {
			if(curr.equals(word)) {
				return true;
			}
			curr = curr.next();
		}
		return false;
	}
	public int linkedListSize() {
		return this.lengthOfLinkedList;
	}
	
	public int totalWordCount() {
		return this.totalCount;
	}
	
	public MapEntry next() {
		return this.next;
	}
	
	public boolean hasNext() {
		return next() != null;
	}
	@Override
	public int compareTo(MapEntry entry) {
		return this.count - entry.count();
	}
	/**
	 * 
	 * @param str
	 * @return if a given string equals this key
	 */
	public boolean equals(String str) {
		return str.toLowerCase().equals(this.key);
	}
	@Override
	public String toString() {
		MapEntry curr = this;
		StringBuilder str = new StringBuilder();
		while(curr != null) {
			str.append(curr.key() + ": " + curr.count() + "\n");
			curr = curr.next();
		}
		return str.toString();
	}
}
