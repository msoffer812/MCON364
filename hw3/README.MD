https://www.geeksforgeeks.org/double-hashing/

# HashMap Class README

## Description
The `HashMap` class represents a basic implementation of a hash map data structure in Java.

## Public Methods

### `HashMap(int capacity, Hasher hasher)`
Constructor to create a HashMap object. Initializes the table and sets the size to 0. Throws an exception if the capacity provided is 0 or negative.

### `boolean contains(String key)`
Checks if the HashMap contains a specific key.

### `int getSlotLength(String key)`
Gets the length of the linked list at a specific table slot.

### `String report()`
Generates a report on the statistics of the HashMap.

### `int[] getDiffStats()`
Returns an array containing different statistics about the HashMap.

### `boolean isEmpty()`
Checks if the HashMap is empty.

### `int size()`
Returns the number of entries in the HashMap.

### `int remove(String key)`
Removes the value associated with the specified key from the HashMap.

### `int get(String key)`
Gets the value associated with the specified key from the HashMap.

### `void put(String word)`
Adds a new entry to the HashMap with the specified word.

### `Iterator<MapEntry> iterator()`
Provides an iterator to iterate over the HashMap.

## Iterator
The `MapIterator` class provides an iterator to iterate over the HashMap in order of count.

