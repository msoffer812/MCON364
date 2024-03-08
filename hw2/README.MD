# BinarySearchTree Class README

## Description
The **BinarySearchTree** class represents a basic implementation of a binary search tree data structure in Java.

## Public Methods

### `**BinarySearchTree()**`
Constructor to create an empty binary search tree.

### `**BinarySearchTree(T element)**`
Constructor to create a binary search tree with a single element.

### `**T remove(T value)**`
Removes a value from the binary search tree and returns it.

### `**String toString()**`
Returns a string representation of the binary search tree.

### `**T get(T value)**`
Searches for a given value in the tree and returns it.

### `**boolean contains(T value)**`
Checks if the tree contains a specific value.

### `**int size()**`
Returns the number of elements in the tree.

### `**boolean isEmpty()**`
Checks if the tree is empty.

### `**Iterator<T> getIterator(Traversal traversalType)**`
Returns an iterator to iterate over the values in the tree based on the specified traversal type.

### `**T min()**`
Finds the minimum value in the tree.

### `**T max()**`
Finds the maximum value in the tree.

### `**void add(T element)**`
Adds an element to the binary search tree.

## Inner Classes

### `**TreeNode<T>**`
Private inner class representing a node in the binary search tree.

### `**TreeIterator**`
