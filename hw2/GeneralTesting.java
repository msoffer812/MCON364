package hw2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

class GeneralTesting {
	
	@Test
	void treeWithNoElementsIsSizeZero() {
		BST<Integer> tree = new BinarySearchTree<>();
		assertEquals(tree.size(), 0);
		
	}
	
	@Test
	void treeWithOneElementIsSizeOne() {
		BST<Integer> tree = new BinarySearchTree<>();
		tree.add(1);
		assertEquals(tree.size(), 1);
	}
	
	@Test
	void treeWithOneElementAddedThenRemovedIsSizeZero() {
		BST<Integer> tree = new BinarySearchTree<>();
		tree.add(1);
		tree.remove(1);
		assertEquals(tree.size(), 0);
	}
	
	@Test
	void treeWithSixElementsAndMin50Returns50AsMin() {
		BST<Integer> tree = new BinarySearchTree<>();
		tree.add(100);
		tree.add(72);
		tree.add(50);
		tree.add(999);
		tree.add(250);
		tree.add(50000);
		assertEquals(tree.min(), 50);
	}
	
	@Test
	void treeWithSixElementsAndMax50000Returns50000AsMax() {
		BST<Integer> tree = new BinarySearchTree<>();
		tree.add(100);
		tree.add(72);
		tree.add(50);
		tree.add(999);
		tree.add(250);
		tree.add(50000);
		assertEquals(tree.max(), 50000);
	}

	@Test
	void treeWithSixElementsAndContains72ReturnsTrueThatContains72() {
		BST<Integer> tree = new BinarySearchTree<>();
		tree.add(100);
		tree.add(72);
		tree.add(50);
		tree.add(999);
		tree.add(250);
		tree.add(50000);
		assertTrue(tree.contains(72));
	}
	
	@Test
	void treeWithSixElementsAndDoesNotContain30ReturnsFalseThatContains30() {
		BST<Integer> tree = new BinarySearchTree<>();
		tree.add(100);
		tree.add(72);
		tree.add(50);
		tree.add(999);
		tree.add(250);
		tree.add(50000);
		assertFalse(tree.contains(30));
	}
	
	@Test
	void treeWithSixElementsReturnsCorrectPreorderIterator() {
		int[] nums = {5, 3, 8, 1, 4, 7};
		int[] preorder = {5, 3, 1, 4, 8, 7};
		BST<Integer> tree = new BinarySearchTree<>();
		for(int num:nums) {
			tree.add(num);
		}
		Iterator<Integer> iter = tree.getIterator(Traversal.DFSPreorder);
		for(int num:preorder) {
			assertEquals(num, iter.next());
		}
	}
	
	@Test
	void treeWithSixElementsReturnsCorrectPostorderIterator() {
		int[] nums = {5, 3, 8, 1, 4, 7};
		int[] postorder = {1, 4, 3, 7, 8, 5};
		BST<Integer> tree = new BinarySearchTree<>();
		for(int num:nums) {
			tree.add(num);
		}
		Iterator<Integer> iter = tree.getIterator(Traversal.DFSPostorder);
		for(int num:postorder) {
			assertEquals(num, iter.next());
		}
	}
	
	@Test
	void treeWithSixElementsReturnsCorrectInorderIterator() {
		int[] nums = {5, 3, 8, 1, 4, 7};
		int[] inorder = {1, 3, 4, 5, 7, 8};
		BST<Integer> tree = new BinarySearchTree<>();
		for(int num:nums) {
			tree.add(num);
		}
		Iterator<Integer> iter = tree.getIterator(Traversal.DFSInorder);
		for(int num:inorder) {
			assertEquals(num, iter.next());
		}
	}
	
	@Test
	void treeWithSixElementsReturnsCorrectBFSIterator() {
		int[] nums = {5, 3, 8, 1, 4, 7};
		int[] bfs = {5, 3, 8, 1, 4, 7};
		BST<Integer> tree = new BinarySearchTree<>();
		for(int num:nums) {
			tree.add(num);
		}
		Iterator<Integer> iter = tree.getIterator(Traversal.BFS);
		for(int num:bfs) {
			assertEquals(num, iter.next());
		}
	}
}
