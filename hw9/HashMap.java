package hw9;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;

public class HashMap implements MapInterface, Iterable{
	private MapEntry[] table;
	private int size;
	private BiFunction<String, MapEntry[], Integer> hasher;
	
	/**
	 * constructor, makes the table and sets size to 0, 
	 * if capacity given is 0 or under throws an exception
	 * @param capacity
	 */
	public HashMap(int capacity, BiFunction<String, MapEntry[], Integer> hasher) {
		if(capacity<=0) {
			throw new InputMismatchException("Must be a positive integer capacity");
		}
		this.table = new MapEntry[capacity];
		for(int i=0;i<capacity;i++) {
			table[i] = null;
		}
		this.size = 0;
		this.hasher = hasher;
	}
	
	/**
	 * Call the method that checks for specific entry if it
	 * contains the key
	 * @return the boolean whether it's there or not
	 */
	@Override
	public boolean contains(String key) {
		int index = hasher.apply(key, table);
		System.out.println("Index where this can be found: " + index);
		MapEntry entry = table[index];
		if(entry == null) {
			System.out.println("This slot is empty, the table doesn't hold the value");
			return false;
		}
		System.out.println("Calling method to search for " + key + " in " + entry.toString());
		return searchForKeyInEntry(entry, key);
	}
	
	/**
	 * 
	 * @param entry
	 * @param key
	 * @return if specific entry contains the key
	 */
	private boolean searchForKeyInEntry(MapEntry entry, String key) {
		/*
		 * if entry is null, then there's nowhere else to search and return false
		 */
		if(entry == null) {
			System.out.println("Entry is null, table doesn't contain the key");
			return false;
		}
		/*
		 * If entry equals key, then it's been found and return true
		 */
		else if(entry.key().equals(key)) {
			System.out.println("Key found: " );
			System.out.println(entry.toString());
			return true;
		}
		/*
		 * recursively call the method with the next in it's place to 
		 * search down the list
		 */
		System.out.println("Searching for key now, calling the method recursively with it's next");
		return searchForKeyInEntry(entry.next(), key);
	}
	
	/**
	 * 
	 * @param key
	 * @return length of linked list at a particular table 
	 */
	public int getSlotLength(String key) {
		int index = hasher.apply(key, table);
		if(table[index] == null) {
			return 0;
		}
		return table[index].linkedListSize();
	}
	/**
	 * 
	 * @return string that's a report on stats of the book
	 */
	public String report() {
		StringBuilder str = new StringBuilder();
		int[] stats = getDiffStats();
		str.append("Total Words: " +  stats[0] + "\n");
		str.append("Size of the array: " + this.table.length + "\n");
		str.append(toString() + "\n");
		str.append("Total unused slots: " + stats[1] + "\n");
		str.append("Average Linked List size: " + stats[2] + "\n");
		str.append("Load Factor: " + ((double)(this.size)/table.length));
		return str.toString();
	}
	
	public int[] getDiffStats() {
		int totalWords = 0, totalUnusedSlots=0, avgLinkedListSize=0;
		for(MapEntry entry: this.table) {
			if(entry != null) {
				totalWords += entry.totalWordCount();
				avgLinkedListSize += entry.linkedListSize();
			}else {
				totalUnusedSlots++;
			}
		}
		avgLinkedListSize/=(table.length - totalUnusedSlots);
		return new int[] {totalWords, totalUnusedSlots, avgLinkedListSize};
	}
	/**
	 * @return is the map empty
	 */
	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}
	/**
	 * @return the amount of entries in the table
	 */
	@Override
	public int size() {
		return this.size;
	}
	/**
	 * @return the removed associated value
	 * find the entry that contains the key and remove it
	 * from the table
	 */
	@Override
	public int remove(String key) {
		int index = hasher.apply(key, table);
		System.out.println("Index where the key should be found: " + index);
		MapEntry entry = this.table[index];
		System.out.println("Entry at this index: " + entry.toString());
		int value = 0;
		System.out.println("Count set to -1, will return that if the map doesn't contain the key.");
		/*
		 * if entry isn't null - otherwise you return default value,
		 * 0
		 */
		if(entry != null) {
			/*
			 * It's different logic if the top link is the matching
			 * key,rather then ones later down, so that goes in a different method
			 */
			if(entry.key().equals(key)) {
				value = entry.count();
				System.out.println("Key was found in the map, "
						+ "return count of " + value);
				/*
				 * if all that's in that space is that one entry,
				 * then just set it to null and remove
				 */
				System.out.println("We've got the count, now we'll remove the key:");
				if(entry.next() == null) {
					System.out.println("This was the only entry in the list of the slot,"
							+ " so we'll just set the whole slot to null");
					this.table[index] = null;
				}else {
					/*
					 * Otherwise, just replace with that 
					 * entry's next
					 */
					System.out.println("Replacing the entry with the next entry from this key: ");
					this.table[index] = entry.next();
				}
			}else {
				System.out.println("Calling the method to search for the key:");
				value = findEntryValueAndRemove(entry, key);
			}
		}
		System.out.println("Slot is empty, returning 0");
		return value;
	}
	
	/**
	 * find the matching entry in the linked list
	 * @param entry
	 * @param key
	 * @return the value of the key or -1 if the key's not there
	 */
	private int findEntryValueAndRemove(MapEntry entry, String key) {
		if(entry.next() != null) {
			System.out.println(entry.toString());
			if(entry.next().key().equals(key)) {
				System.out.println("Key found in the current entry's next."
						+ " Now we'll just store the count, set the curren't entry's "
						+ "next to the key's next, and return! ");
				/*
				 * If the entry's next is the correct key,
				 * then just replace that entry's next with 
				 * the next's next, store the value, and return
				 */
				int value = entry.next().count();
				entry.setNext(entry.next().next());
				return value;	
			}else {
				System.out.println("Calling the method with entry.next() as the new entry to further search.");
				return findEntryValueAndRemove(entry.next(), key);
			}
		}
		System.out.println("Entry was null, returning 0 as the map"
				+ " doesn't contain the key. ");
		return 0;
	}
	/**
	 * Call the method that searches for an return the value associated with the 
	 * given key.
	 * Otherwise, return -1 if it doesn't exist
	 * @return value associated with key
	 */
	@Override
	public int get(String key) {
		int index = hasher.apply(key, table); /* Get the index where the key would be */
		System.out.println("Index where the key should be located: " + index);
		MapEntry entry = this.table[index];
		return searchForEntryAndReturnCount(key, entry);
	}
	
	/**
	 * Search for the key in an entry and return
	 * @param key
	 * @param entry
	 * @return count of the searched-for key, or 0 if not contained in the table
	 */
	private int searchForEntryAndReturnCount(String key, MapEntry entry) {
		System.out.println("Current Entry: ");
		if(entry == null) {
			System.out.println("Null, so just returning 0 since "
					+ "the map obviously doesn't contain the key.");
			return 0;
		}else if(entry.equals(key)) {
			System.out.println(entry.toString());
			System.out.println("Matching key found! Returning count.");
			return entry.count();
		}
		System.out.println(entry.toString());
		return searchForEntryAndReturnCount(key, entry.next());
	}
	/**
	 * Given a string, add to the table.
	 * Hash the value using the hasher, and place in that equivalent index
	 * if it's already full, call the method that traverses the entry until it
	 * finds the equivalent entry or attaches it to the end of the list
	 * @param word
	 */
	@Override
	public void put(String word) {
		/*
		 * get an int value based on the string, using the hasher
		 */
		 System.out.println("Hashing to calculate placement index:");
		 int index = hasher.apply(word, table);
		 System.out.println("index: " + index);
		 /*
		  * Get the entry in the table in that equivalent index
		  */
		 MapEntry entry = table[index];
		 /*
		  * if the entry location is null, then just insert the string
		  * as a new entry
		  */
		 if(entry == null) {
			 System.out.println("Slot is empty");
			 table[index] = new MapEntry(word, 1);
			 System.out.println("Putting " + word + " at " + index);
			 this.size++; /* Size only increments if the entry is new, not an incrementation */
		 }else {
			 /*
			  * Otherwise, call the method that will place the entry
			  */
			 traverseEntryAndAdd(entry, word);
		 }
		 System.out.println(toString());
	}
	
	/**
	 * recursively traverse the linked list at that entry until you've found a matching entry(increment count)
	 * or the end(attach to the end)
	 * @param entry
	 * @param key
	 */
	private void traverseEntryAndAdd(MapEntry entry, String key) {
		if(entry.equals(key)) {
			System.out.println("Matching key already inserted, will just increment the count");
			entry.incrementCount(1); /* No incrementing the size. since there's no new entry */
		}else if(entry.next() == null) {
			System.out.println("Entry's next is null, putting the new key in at the end and incrementing size to " + (this.size + 1));
			entry.setNext(new MapEntry(key, 1)); 
			this.size++; /* Size only increments if the entry is new, not an incrementation */
		}else {
			System.out.println("Calling the method recursively on entry.next() to find the correct placement");
			traverseEntryAndAdd(entry.next(), key);
		}
	}
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		int index = 0;
		for(MapEntry entry: table) {
			index++;
			str.append("Slot " + index + ":");
			str.append("\n");
			if(entry == null) {
				str.append("Empty Slot");
			}else {
				str.append("List size: " + entry.linkedListSize() + "\n");
				str.append(entry.toString());
			}
			str.append("\n");
		}
		return str.toString();
	}
	@Override
	public Iterator<MapEntry> iterator(){
		return new MapIterator();
	}
	/**
	 * iterator to iterate over map in order of 
	 * @author mbrso
	 *
	 */
	private class MapIterator implements Iterator<MapEntry>{
		private TreeMap<Integer, MapEntry> map;		
		public MapIterator() {
			this.map = new TreeMap<>(Comparator.reverseOrder());
			fillUpMap();
		}
		
		@Override
		public boolean hasNext() {
			return !map.isEmpty();
		}
		
		@Override
		public MapEntry next() {
			if(map.isEmpty()){
				return null;
			}
			MapEntry temp = map.pollFirstEntry().getValue();
			if(temp.next() != null) {
				map.put(temp.count(), temp.next());
			}
			return temp;
		}
		/**
		 * fill up the array with all the entries
		 */
		private void fillUpMap() {
			for(MapEntry entry:table) {
				while(entry != null) {
					if(!map.containsKey(entry.count())) {
						map.put(entry.count(), new MapEntry(entry.key(), entry.count()));
					}else {
						MapEntry curr = map.get(entry.count());
						while(curr.next()!= null) {
							curr = curr.next();
						}
						curr.setNext(new MapEntry(entry.key(), entry.count()));
					}
					entry = entry.next();
				}
			}
		}
	}
}
