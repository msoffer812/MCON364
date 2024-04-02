package hw9;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AddingTesting {

	@Test
	void MapWithNothingReturnsSize0NaiveHasher() {
		HashMap map = new HashMap(30, new NaiveHasher());
		assertEquals(map.size(), 0);
	}
	@Test
	void MapWithNothingReturnsDoesNotContainWordsBabyOrStringNaiveHasher() {
		HashMap map = new HashMap(30, new NaiveHasher());
		assertFalse(map.contains("Baby"));
		assertFalse(map.contains("String"));
	}
	
	@Test
	void MapWithBabyAndStringInsertedReturnsContainsWordsBabyAndStringNaiveHasher() {
		HashMap map = new HashMap(30, new NaiveHasher());
		map.put("Baby");
		map.put("String");
		assertTrue(map.contains("Baby"));
		assertTrue(map.contains("String"));
	}
	@Test
	void AddingThirtyWordsMeansItContainsAllThoseWordsNaiveHasher() {
		HashMap map = new HashMap(30, new NaiveHasher());
		String[] words = {
	            "apple", "banana", "cherry", "date", "elderberry",
	            "fig", "grape", "honeydew", "kiwi", "lemon",
	            "mango", "nectarine", "orange", "pear", "quince",
	            "raspberry", "strawberry", "tangerine", "uva", "vanilla",
	            "watermelon", "ximenia", "yellow", "zucchini", "apricot",
	            "blueberry", "cranberry", "dragonfruit", "guava", "lime"
	        };
		int i =0;
		assertEquals(map.size(), i);
		for(String word: words) {
			map.put(word);
			i++;
			assertEquals(map.size(), i);
		}
		for(String word: words) {
			assertTrue(map.contains(word));
		}
	}
	
	@Test
	void AddingThirtyOfSameWordMeansItsSize1AndCount30NaiveHasher() {
		HashMap map = new HashMap(30, new NaiveHasher());
		assertEquals(map.size(), 0);
		for(int i=0;i<30;i++) {
			map.put("hi");
			assertEquals(map.size(), 1);
		}
		assertTrue(map.contains("hi"));
		assertEquals(map.get("hi"), 30);
	}
	
	@Test
	void MapWithNothingReturnsSize0SophisticatedHasher() {
		HashMap map = new HashMap(30, new SophisticatedHasher());
		assertEquals(map.size(), 0);
	}
	@Test
	void MapWithNothingReturnsDoesNotContainWordsBabyOrStringSophisticatedHasher() {
		HashMap map = new HashMap(30, new SophisticatedHasher());
		assertFalse(map.contains("Baby"));
		assertFalse(map.contains("String"));
	}
	
	@Test
	void MapWithBabyAndStringInsertedReturnsContainsWordsBabyAndStringSophisticatedHasher() {
		HashMap map = new HashMap(30, new SophisticatedHasher());
		map.put("Baby");
		map.put("String");
		assertTrue(map.contains("Baby"));
		assertTrue(map.contains("String"));
	}
	@Test
	void AddingThirtyWordsMeansItContainsAllThoseWordsSophisticatedHasher() {
		HashMap map = new HashMap(30, new SophisticatedHasher());
		String[] words = {
	            "apple", "banana", "cherry", "date", "elderberry",
	            "fig", "grape", "honeydew", "kiwi", "lemon",
	            "mango", "nectarine", "orange", "pear", "quince",
	            "raspberry", "strawberry", "tangerine", "uva", "vanilla",
	            "watermelon", "ximenia", "yellow", "zucchini", "apricot",
	            "blueberry", "cranberry", "dragonfruit", "guava", "lime"
	        };
		int i =0;
		assertEquals(map.size(), i);
		for(String word: words) {
			map.put(word);
			i++;
			assertEquals(map.size(), i);
		}
		for(String word: words) {
			assertTrue(map.contains(word));
		}
	}
	
	@Test
	void AddingThirtyOfSameWordMeansItsSize1AndCount30SophisticatedHasher() {
		HashMap map = new HashMap(30, new SophisticatedHasher());
		assertEquals(map.size(), 0);
		for(int i=0;i<30;i++) {
			map.put("hi");
			assertEquals(map.size(), 1);
		}
		assertTrue(map.contains("hi"));
		assertEquals(map.get("hi"), 30);
	}
}
