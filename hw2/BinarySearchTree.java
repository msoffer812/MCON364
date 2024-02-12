package hw2;
import java.util.*;
public class BinarySearchTree<T extends Comparable<T>> implements BST<T>{
	private TreeNode root;
	private int size;
	
	public BinarySearchTree() {
		this.root = null;
		size=0;
	}
	
	public BinarySearchTree(T element) {
		this.root = new TreeNode(element);
		size = 1;
	}
	
	/**
	 * remove a value and then return it
	 * @return removed value
	 */
	public T remove(T value) {
		if(!isEmpty()) {
			System.out.println(toString(this.root));
			System.out.println("Tree isn't empty, so now searching for the item to remove");
			if(this.root.value.equals(value)) {
				System.out.println("The root of the tree, " + this.root.value + " is the value we're looking for. "
						+ "Decrementing size and removing");
				size--;
				T temp = (T)this.root.value;
				if(this.root.left == null && this.root.right==null) {
					System.out.println("The root has no children, we'll just set the whole tree to null");
					this.root = null;
				}else if(this.root.left == null && this.root.right != null) {
					System.out.println("The tree only has one child on the right, " 
							+ this.root.right.value + " that is the new head of the tree.");
					this.root = this.root.right;
				}else if(this.root.left != null && this.root.right == null) {
					System.out.println("The tree only has one child on the left, " 
							+ this.root.left.value + " that is the new head of the tree.");
					this.root = this.root.left;
				} else {
					System.out.println("The root has two children. We will go down to the rightmost value "
							+ "or the left child to get the value closest to the removed node, which it will then be swapped with to remove easily");
					return findNextNodeInOrderAndSwap(this.root, this.root, this.root.left);
				}
				return temp;
			}else {
				T val = null;
				if(this.root.value.compareTo(value) >= 0) {
					System.out.println("The root value, " + this.root.value + " is greater than the value we're searching for to remove - "
							+ value + "so we'll go call findRemoveAndReturnValue() to search down starting at the left child of the root");
					val = (T) findRemoveAndReturnValue(value, this.root, this.root.left);
				}else {
					System.out.println("The root value, " + this.root.value + " is less than the value we're searching for to remove - "
							+ value + "so we'll go call findRemoveAndReturnValue() to search down starting at the right child of the root");
					val = (T) findRemoveAndReturnValue(value, this.root, this.root.right);
				}
				if(val != null) {
					System.out.println("The value was found and removed, so we're going to decrement the size of the array to " + size + " and return it.");
					this.size--;
				}else {
					System.out.println("The value searched for doesn't exist in the tree, returning null");
				}
				System.out.println(toString(this.root));
				return val;
			}
		}
		return null;
	}
	
	/**
	 * For debugging purposes, prints out tree
	 * as bfs, with each line a level of the tree
	 */
	private String toString(TreeNode<T> rt) {
		if(rt == null) {
			return "{}";
		}
		Queue<TreeNode<T>> queue = new LinkedList<>();
		StringBuilder str = new StringBuilder();
		queue.add(rt);
		while(!queue.isEmpty()) {
			int size = queue.size();
			for(int i=0;i<size;i++) {
				TreeNode<T> node = queue.remove();
				str.append(node.value);
				str.append(" ");
				if(node.left!= null) {
					queue.add(node.left);
				}
				if(node.right!= null) {
					queue.add(node.right);
				}
			}
			str.append('\n');
		}
		return str.toString();
	}
	/**
	 * @return string representation of the tree
	 */
	@Override
	public String toString() {
		if(root == null) {
			return "{}";
		}
		Queue<TreeNode<T>> queue = new LinkedList<>();
		StringBuilder str = new StringBuilder();
		queue.add(root);
		while(!queue.isEmpty()) {
			int size = queue.size();
			for(int i=0;i<size;i++) {
				TreeNode<T> node = queue.remove();
				str.append(node.value);
				str.append(" ");
				if(node.left!= null) {
					queue.add(node.left);
				}
				if(node.right!= null) {
					queue.add(node.right);
				}
			}
			str.append('\n');
		}
		return str.toString();
	}

	/**
	 * Searches for given value in the tree, removes it, and returns it.
	 * If the found value has two children, it calls the method that deals with that, findNextNodeInOrderAndSwap().
	 * @param valueToRemove
	 * @param parent
	 * @param node
	 * @return removed values
	 */
	private T findRemoveAndReturnValue(T valueToRemove, TreeNode<T> parent, TreeNode<T> node) {
		System.out.println("Recursively searching for the value to remove. First inspecting the node itself");
		if(node.value.equals(valueToRemove)) {
			System.out.println("Yay! We have found the correct node. Now we will save the value in temp, to return later");
			T temp = (T)node.value;
			if(node.left == null && node.right==null) {
				System.out.println("The node we've found is a leaf - no children - so we'll just set it to null");
				if(parent.left == node) {
					parent.left = null;
				}else {
					parent.right = null;
				}
			}else if(node.left == null && node.right != null) {
				System.out.println("The node has a right child, but no left.");
				if(parent.left == node) {
					System.out.println("Now replacing parent node's (" + parent.value + 
							") left (" + node.value + ") with the matching node's right child (" + node.right + ").");
					parent.left = node.right;
				}else {
					System.out.println("Now replacing parent node's (" + parent.value + 
							") right (" + node.value + ") with the matching node's right child (" + node.right + ").");
					parent.right = node.right;
				}
			}else if(node.left != null && node.right == null) {
				System.out.println("The node has a left child, but no right.");
				if(parent.left == node) {
					System.out.println("Now replacing parent node's (" + parent.value + 
							") left (" + node.value + ") with the matching node's left child (" + node.left + ").");
					parent.left = node.left;
				}else {
					System.out.println("Now replacing parent node's (" + parent.value + 
							") right (" + node.value + ") with the matching node's left child (" + node.left + ").");
					parent.right = node.left;
				}
			} else {
				System.out.println("The correct node has both a left and right child (" + node.left + " and " + node.right + ")");
				System.out.println("We need to go down to the rightmost value of the node's left child, which will be the closest value to it,"
						+ "and swap that node with this, then remove it.");
				findNextNodeInOrderAndSwap(node, node, node.left);
			}
			return temp;
		}else {
			if(node.value.compareTo(valueToRemove) >= 0) {
				System.out.println("Current node( " + node.value + ") is larger than that value we're searching for(" + valueToRemove + ").");
				if(node.left != null) {
					System.out.println("Now we'll call this method recursively with the current node's left child(where the lesser values are):");
					return (T) findRemoveAndReturnValue(valueToRemove, node, node.left);
				}else {
					System.out.println("The current node has no left child, which would be where the smaller values are. "
							+ "Therefore, the tree doesn't contain " + valueToRemove);
				}
			}else {
				System.out.println("Current node( " + node.value + ") is less than that value we're searching for(" + valueToRemove + ").");
				if(node.right != null) {
					System.out.println("Now we'll call this method recursively with the current node's right child(where the bigger values are):");
					return (T) findRemoveAndReturnValue(valueToRemove, node, node.right);
				}else {
					System.out.println("The current node has no right child, which would be where the bigger values are. "
							+ "Therefore, the tree doesn't contain " + valueToRemove);
				}
			}
		}
		return null;
	}
	
	/**
	 * given the node to remove has two children, this method
	 * finds the rightmost node of its left child, which will be closest to it in value,
	 * swap values, and then remove the now childless node on the bottom.
	 * The reason we don't go down the right child is because, given adding a node that's the same value as the root,
	 * it gets placed on the left.
	 * @param nodeToRemove
	 * @param parent
	 * @param node
	 * @return value of node
	 */
	private T findNextNodeInOrderAndSwap(TreeNode nodeToRemove, TreeNode parent, TreeNode node) {
		System.out.println("Node: " + node.value);
		System.out.println(toString(node));
		if(node.right==null) {
			System.out.println("We've found the rightmost leaf node of the root, which should be closest to the root in value.");
			swap(nodeToRemove, node);
			System.out.println(toString(node));
			System.out.println("Now detaching the leaf node from it's parent, thereby removing it.");
			if(parent.right == node) {
				parent.right = null;
			}else {
				parent.left = null;
			}
			System.out.println(toString(node));
			return (T) nodeToRemove.value;
		}
		/*
		 * If the root has a right child, move down to it
		 * we'll call it recursively until we get to the rightmost child 
		 * of the left node of the root - this is where the value closest to it will be.
		 */
		else {
			System.out.println("This node isn't as far down as we can go, calling this method recursively "
					+ "on node.right to go further, with node now as the parent.");
			return findNextNodeInOrderAndSwap(nodeToRemove, node, node.right);
		}
	}
	
	/**
	 * Swap the two values at the given nodes
	 * @param a
	 * @param b
	 */
	private void swap(TreeNode<T> a, TreeNode<T> b) {
		System.out.println("Swapping the values of two TreeNodes, " + a.value + " and " + b.value);
		T temp = a.value;
		a.value = b.value;
		b.value = temp;
	}
	
	/**
	 * @return value in the tree, null if it isn't there
	 */
	@Override
	public T get(T value) {
		if(isEmpty()) {
			System.out.println("Tree is empty, returning null");
			return null;
		}
		System.out.println("Calling the method that searches for the value and returns it");
		return (T) dfsSearchForValueAndReturn(value, this.root);
	}
	/**
	 * @return if the tree contains a value
	 */
	@Override
	public boolean contains(T value) {
		if(isEmpty()) {
			System.out.println("The tree is empty, so obviously doesn't contain the value. Returning false");
			return false;
		}
		System.out.println("Calling the method that searches for and returns the value, "
				+ "and if it's not null then the value must exist, so only then will true be returned");
		return(dfsSearchForValueAndReturn(value, this.root) != null);
	}
	
	/**
	 * 
	 * @param value
	 * @param root
	 * @return desired value from the tree, null if it isn't there - 
	 * used by both get and contains to see if the specified value is there
	 */
	private T dfsSearchForValueAndReturn(T value, TreeNode<T> node) {
		System.out.println("Node: " + node.value);
		System.out.println(toString(node));
		if(node.value.equals(value)) {
			System.out.println("The current node matches the searched-for value");
			return node.value;
		}
		if(node.value.compareTo(value) < 0) {
			System.out.println("The current node is greater than " + value);
			if(root.right != null) {
				System.out.println("Calling the method recursively with the right child to search for the node: ");
				return (T) dfsSearchForValueAndReturn(value, node.right);
			}else {
				System.out.println("The right child is null, this tree doesn't contain the value, returning null");
			}
		}else {
			System.out.println("The current node is less than " + value);
			if(node.left != null) {
				System.out.println("Calling the method recursively with the left child to search for the node: ");
				return (T) dfsSearchForValueAndReturn(value, node.left);
			}else {
				System.out.println("The left child is null, this tree doesn't contain the value, returning null");
			}
		}
		return null;
	}
	
	/**
	 * @return the size of the tree
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * @return is the tree empty or not
	 */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}
	/**
	 * 
	 * @param traversalType
	 * @return an iterator to iterate over the values in the tree, 
	 * depending on the type of iteration they want
	 */
	@Override
	public Iterator<T> getIterator(Traversal traversalType){
		System.out.println("Making an array of size of the tree to put in the values, depending on traversal type. Also an array with "
				+ "one zero in it to keep track of the index while making recursive calls, so we put node values in the proper spaces and "
				+ "don't overwrite anything.");
		Comparable<T>[] values = new Comparable[this.size];
		int[] index = {0};
		switch(traversalType) {
			case DFSPreorder:
				System.out.println("Since a Preorder traversal was specified, we will call the method that performs a "
						+ "preorder traversal to fill up the array that will be used in the iterable.");
				preorderDFS(values, index, this.root);
				break;
			case DFSInorder:
				System.out.println("Since an Inorder traversal was specified, we will call the method that performs an "
						+ "Inorder traversal to fill up the array that will be used in the iterable.");
				inorderDFS(values, index,  this.root);
				break;
			case DFSPostorder:
				System.out.println("Since a Postorder traversal was specified, we will call the method that performs a "
						+ "Postorder traversal to fill up the array that will be used in the iterable.");
				postorderDFS(values, index, this.root);
				break;
			case BFS:
				System.out.println("Since a Breadth-first traversal was specified, we will call the method that performs a "
						+ "Breadth-first traversal to fill up the array that will be used in the iterable.");
				BFS(values);
				break;
		}
		System.out.println("Now we have an array, we return a new TreeIterator with it in the constructor. "
				+ "The Iterator will use hasNext() and next() to iterate over the array with the values in the correct order "
				+ "based on the traversal type");
		return new TreeIterator(values);
	}
	
	/**
	 * For debugging purposes, printing out array when needed.
	 * @param array
	 */
	private void printOutArray(Comparable<T>[] array) {
		for(Comparable<T> val:array) {
			System.out.print(val);
			System.out.print(" ");
		}
		System.out.println("");
	}
	/*
	 * These below return a queue of all the values in the tree,
	 * processed differently, for use in the iterator.
	 * They all use an int[] to hold the index value, since any other indexing
	 * method would result in an impoper order or overwriting since the variable value
	 * wouldn't pass over between recursive calls.
	 */
	
	/**
	 * Fills up the queue of all the values in the tree, processing root, 
	 * then left, then right children
	 * @param values
	 * @param node
	 */
	private void preorderDFS(Comparable<T>[] values, int[] index, TreeNode<T> node){
		System.out.println(toString(node));
		System.out.println("Setting the array at index " + index[0] + " to " + node.value);
		printOutArray(values);
		values[index[0]] = node.value;
		printOutArray(values);
		System.out.println("Incrementing index from " + index[0] + " to " + (index[0] + 1));
		index[0]++;
		if(node.left != null) {
			System.out.println("Calling the searching method recursively, now with the left child as the node:");
			preorderDFS(values, index, node.left);
		}
		if(node.right != null) {
			System.out.println("Calling the searching method recursively, now with the right child as the node:");
			preorderDFS(values, index, node.right);
		}
	}
	
	/**
	 * Fills up the queue of all the values in the tree, processing left, 
	 * then right children, then root
	 * @param values
	 * @param node
	 */
    private void postorderDFS(Comparable<T>[] values, int[] index, TreeNode<T> node){
		System.out.println(toString(node));
		if(node.left != null) {
			System.out.println("Calling the searching method recursively, now with the left child as the node:");
			postorderDFS(values, index, node.left);
		}
		if(node.right != null) {
			System.out.println("Calling the searching method recursively, now with the right child as the node:");
			postorderDFS(values, index, node.right);
		}
		System.out.println("Setting the array at index " + index[0] + " to " + node.value);
		printOutArray(values);
		values[index[0]] = node.value;
		printOutArray(values);
		System.out.println("Incrementing index from " + index[0] + " to " + (index[0] + 1));
		index[0]++;
	}
    
    /**
	 * Fills up the queue of all the values in the tree, processing left, 
	 * then root, then right children
	 * @param values
	 * @param node
	 */
    private void inorderDFS(Comparable<T>[] values, int[] index, TreeNode<T> node){
    	System.out.println(toString(node));
    	if(node.left != null) {
    		System.out.println("Calling the searching method recursively, now with the left child as the node:");
    		inorderDFS(values, index, node.left);
		}
    	System.out.println("Setting the array at index " + index[0] + " to " + node.value);
		printOutArray(values);
    	values[index[0]] = node.value;
    	printOutArray(values);
		System.out.println("Incrementing index from " + index[0] + " to " + (index[0] + 1));
		index[0]++;
		if(node.right != null) {
			System.out.println("Calling the searching method recursively, now with the right child as the node:");
			inorderDFS(values, index, node.right);
		}
	}
    
    /**
	 * Fills up the queue of all the values in the tree, processing level by level
	 * @param values
	 * @param node
	 */
    private void BFS(Comparable<T>[] values){
    	/*
    	 * Holds the nodes at each level while processing
    	 */
		Queue<TreeNode<T>> nodes = new LinkedList<>();
		int index = 0;
		nodes.add(this.root);
		System.out.println("We have a single queue of TreeNodes with the root of the tree in it:");
		/*
		 * loops once for each level of the tree
		 */
		System.out.println("Starting the loop that will loop until the whole tree has been processed "
				+ "and the queue is empty of nodes");
		while(!nodes.isEmpty()) {
			/*
			 * The size of the level by definition is the size of the queue
			 * that holds all the nodes at the level
			 */
			int levelSize = nodes.size();
			System.out.println("This level has " + levelSize + " nodes in it, so we will loop " + levelSize + 
					"times to process each node.");
			/*
			 * loops for each node on that level of the tree
			 */
			for(int i=0;i<levelSize;i++) {
				TreeNode<T> node = nodes.remove();
				System.out.println("Node: " + node.value);
				System.out.println(toString(node));
				printOutArray(values);
				values[index] = node.value;
				printOutArray(values);
				System.out.println("Incrementing index of " + index + " to index of " + (index+1));
				index++;
				/*
				 * Adds the children nodes to end of the queue
				 */
				if(node.left != null) {
					System.out.println("Current node has a left child, " + node.left.value + " "
							+ "so putting it into the queue to be processed in the next level.");
					nodes.add(node.left);
				}
				if(node.right != null) {
					System.out.println("Current node has a right child, [" + node.right.value + "] "
							+ "so putting it into the queue to be processed in the next level.");
					nodes.add(node.right);
				}
			}
		}
	}
	
	/**
	 * find the minimum value in the tree
	 * @return min value
	 */
    @Override
	public T min() {
		/*
		 * If there are no values in the tree, then return null
		 */
		if(isEmpty()) {
			System.out.println("Tree is empty, returning null");
			return null;
		}
		System.out.println("Calling method to search for the min and returning found value");
		return (T) getMinLogic(this.root);
	}
	
	/**
	 * find the maximum value in the tree
	 * @return max value
	 */
    @Override
	@SuppressWarnings("unchecked")
	public T max() {
		/*
		 * If there are no values in the tree, then return null
		 */
		if(this.root == null) {
			System.out.println("Tree is empty, returning null");
			return null;
		}
		System.out.println("Calling method to search for the max and returning found value");
		return (T) getMaxLogic(this.root);
	}
	
	/**
	 * Recursively get the max value in the tree
	 * @param node
	 * @return that value
	 */
	private T getMaxLogic(TreeNode<T> node) {
		System.out.println(toString(node));
		/*
		 * finding out if this is the leaf or has right children - 
		 * if it does there are larger values. Otherwise we return that value
		 */
		if(node.right == null) {
			System.out.println("Found rightmost leaf, which would be the max, returning " + node.value);
			return node.value;
		}
		/*
		 * If the right does have a child, then recursively call getMax
		 * to get to the bottom of the tree
		 */
		System.out.println("Node still has right child, calling method recursively with the right child to find rightmost child.");
		return (T) getMaxLogic(node.right);
	}
	
	/**
	 * Recursively get the min value in the tree
	 * @param node
	 * @return that value
	 */
	private T getMinLogic(TreeNode<T> node) {
		System.out.println(toString(node));
		/*
		 * finding out if this is the leaf or has left children - 
		 * if it does there are smaller values. Otherwise we return that value
		 */
		if(node.left == null) {
			System.out.println("Found leftmost leaf, which would be the min, returning " + node.value);
			return node.value;
		}
		/*
		 * If the left does have a child, then recursively call getMax
		 * to get to the bottom of the tree
		 */
		System.out.println("Node still has ;eft child, calling method recursively with the left child to find rightmost child.");
		return (T) getMinLogic(node.left);
	}
	
	/**
	 * add an element to its proper place in a binary tree
	 * @param element
	 */
	@Override
	public void add(T element) {
		System.out.println(toString(this.root));
		System.out.println("Size: " + this.size);
		if(this.root == null) {
			System.out.println("Tree is empty, so just making a new treeNode with the value as the tree");
			this.root = new TreeNode<>(element);
		}else {
			dfsAdd(element, root);
		}
		System.out.println(toString(this.root));
		this.size++;
		System.out.println("Size: " + this.size);
	}
	
	/**
	 * Actually does the adding, can't do it in the original method because 
	 * need to pass the current node in recursively
	 * @param element
	 * @param node
	 */
	private void dfsAdd(T element, TreeNode<T> node) {
		System.out.println(toString(node));
		if(node.value.compareTo(element) >= 0) {
			System.out.println("The root, " + node.value + " is bigger than or equal to " + element + ", so the new element should go on its left.");
			if(node.left == null) {
				System.out.println("Node's left is null, so we can just attach " + element + " here:");
				node.left = new TreeNode<>(element);
			}else {
				System.out.println("Node's left isn't null, and as that's where the smaller elements go, "
						+ "we'll call the method recursively on it so to see where to put " + element);
				dfsAdd(element, node.left);
			}
		}else {
			System.out.println("The root, " + node.value + " is less than " + element + ", so the new element should go on its right.");
			if(node.right == null) {
				System.out.println("Node's right is null, so we can just attach " + element + " here:");
				node.right = new TreeNode<>(element);
			}else {
				System.out.println("Node's right isn't null, and as that's where the smaller elements go, "
						+ "we'll call the method recursively on it so to see where to put " + element);
				dfsAdd(element, node.right);
			}
		}
	}
	/**
	 * 
	 * @author mbrso
	 * private inner class that represents a node in the tree 
	 * @param <Comparable>
	 */
	private class TreeNode<T extends Comparable<T>>{
		T value;			/* The element the current node holds*/
		TreeNode left;	/* The left child of the node */
		TreeNode right;  /* The right child of the node */
		
		public TreeNode() {
			this.value = null;
			this.left = null;
			this.right = null;
		}
		
		public TreeNode(T element) {
			this.value = element;
			this.left = null;
			this.right = null;
		}
		
		public TreeNode(TreeNode<T> node) {
			this.value = node.value;
			this.left = node.left;
			this.right = node.right;
		}
		public TreeNode(T element, TreeNode left, TreeNode right) {
			this.value = element;
			this.left = left;
			this.right = right;
		}
	}
	
	/**
	 * 
	 * @author mbrso
	 * Iterator to be returned that traversed the tree using Preorder DFS
	 */
	private class TreeIterator implements Iterator<T>{
		private Comparable<T>[] values;
		private int index;
		
		public TreeIterator(Comparable<T>[] vals) {
			this.values = vals;
			index=0;
		}
		
		@Override
		public boolean hasNext() {
			return index < values.length;
		}
		
		@Override
		public T next() {
			if(hasNext()) {
				index++;
				return (T)values[index-1];
			}
			return null;
		}
	}
}
