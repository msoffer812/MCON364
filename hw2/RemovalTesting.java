package hw2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

class RemovalTesting {

	@Test
	void treeWithSixElementsReturnsCorrectInorderIteratorAfterRemovingAnElement() {
		int[] nums = {5, 3, 8, 1, 4, 7};
		int[] inorder = {1, 3, 4, 7, 8};
		BST<Integer> tree = new BinarySearchTree<>();
		for(int num:nums) {
			tree.add(num);
		}
		tree.remove(5);
		testInorderIterator(inorder, tree.getIterator(Traversal.DFSInorder));
	}
	
	@Test
	void treeWithFiveElementsReturnsCorrectInorderIteratorAfterRemovingAnElement() {
		int[] nums = {3, 8, 1, 4, 7};
		int[] inorder = {1, 4, 7, 8};
		BST<Integer> tree = new BinarySearchTree<>();
		for(int num:nums) {
			tree.add(num);
		}
		tree.remove(3);
		testInorderIterator(inorder, tree.getIterator(Traversal.DFSInorder));
	}
	
	@Test
	void treeWithFourElementsReturnsCorrectInorderIteratorAfterRemovingAnElement() {
		int[] nums = {3, 8, 1, 4, 7};
		int[] inorder = {1, 4, 7, 8};
		BST<Integer> tree = new BinarySearchTree<>();
		for(int num:nums) {
			tree.add(num);
		}
		tree.remove(3);
		testInorderIterator(inorder, tree.getIterator(Traversal.DFSInorder));
	}
	
	@Test
	void treeWithThreeElementsReturnsCorrectInorderIteratorAfterRemovingAnElement() {
		int[] nums = {8, 1, 4, 7};
		int[] inorder = {4, 7, 8};
		BST<Integer> tree = new BinarySearchTree<>();
		for(int num:nums) {
			tree.add(num);
		}
		tree.remove(1);
		testInorderIterator(inorder, tree.getIterator(Traversal.DFSInorder));
	}
	
	@Test
	void treeWithFourElementsReturnsCorrectNullMinAndMaxAfterRemovingAllElements() {
		int[] nums = {8, 1, 4, 7};
		int[] inorder = {};
		BST<Integer> tree = new BinarySearchTree<>();
		for(int num:nums) {
			tree.add(num);
		}
		tree.remove(1);
		tree.remove(8);
		tree.remove(7);
		tree.remove(4);
		assertNull(tree.min());
		assertNull(tree.max());
	}
	
	private void testInorderIterator(int[] inorderElements, Iterator<Integer> iter) {
		for(int num:inorderElements) {
			assertEquals(num, iter.next());
		}
	}

}
