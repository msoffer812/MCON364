package hw4;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Queue;

import org.junit.jupiter.api.Test;

class Testing {

	 @Test
	    public void testAddVertex() {
	        Graph<SocialMediaUser> graph = new Graph<>(new PersonHasher<>());
	        SocialMediaUser user = new SocialMediaUser("test@example.com");
	        graph.addVertex(user);
	        assertTrue(graph.hasVertex(user));
	    }


	    @Test
	    public void testGetPath() {
	        Graph<SocialMediaUser> graph = new Graph<>(new PersonHasher<>());
	        SocialMediaUser user1 = new SocialMediaUser("user1@example.com");
	        SocialMediaUser user2 = new SocialMediaUser("user2@example.com");
	        graph.addVertex(user1);
	        graph.addVertex(user2);
	        graph.addEdge(1, 2);
	        String path = graph.getPath(1, 2);
	        assertEquals(user1.toString() + "->" + user2.toString(), path);
	    }

	    @Test
	    public void testGetAdjacencyTable() {
	        Graph<SocialMediaUser> graph = new Graph<>(new PersonHasher<>());
	        SocialMediaUser user1 = new SocialMediaUser("user1@example.com");
	        SocialMediaUser user2 = new SocialMediaUser("user2@example.com");
	        graph.addVertex(user1);
	        graph.addVertex(user2);
	        graph.addEdge(1, 2);
	        String table = graph.getAdjacencyTable();
	        assertTrue(table.contains(user1.toString()));
	        assertTrue(table.contains(user2.toString()));
	    }

	    @Test
	    public void testGetToVertices() {
	        Graph<SocialMediaUser> graph = new Graph<>(new PersonHasher<>());
	        SocialMediaUser user1 = new SocialMediaUser("user1@example.com");
	        SocialMediaUser user2 = new SocialMediaUser("user2@example.com");
	        graph.addVertex(user1);
	        graph.addVertex(user2);
	        graph.addEdge(1, 2);
	        Queue<SocialMediaUser> vertices = graph.getToVertices(1);
	        assertTrue(vertices.contains(user2));
	    }

	    @Test
	    public void testIsEmpty() {
	        Graph<SocialMediaUser> graph = new Graph<>(new PersonHasher<>());
	        assertTrue(graph.isEmpty());
	        SocialMediaUser user = new SocialMediaUser("test@example.com");
	        graph.addVertex(user);
	        assertFalse(graph.isEmpty());
	    }

	    @Test
	    public void testSize() {
	        Graph<SocialMediaUser> graph = new Graph<>(new PersonHasher<>());
	        assertEquals(0, graph.size());
	        SocialMediaUser user = new SocialMediaUser("test@example.com");
	        graph.addVertex(user);
	        assertEquals(1, graph.size());
	    }

	    @Test
	    public void testMarkVertex() {
	        Graph<SocialMediaUser> graph = new Graph<>(new PersonHasher<>());
	        SocialMediaUser user = new SocialMediaUser("test@example.com");
	        graph.addVertex(user);
	        graph.markVertex(user);
	        assertTrue(graph.isMarked(user));
	    }

	    @Test
	    public void testClearAllMarks() {
	        Graph<SocialMediaUser> graph = new Graph<>(new PersonHasher<>());
	        SocialMediaUser user = new SocialMediaUser("test@example.com");
	        graph.addVertex(user);
	        graph.markVertex(user);
	        graph.clearAllMarks();
	        assertFalse(graph.isMarked(user));
	    }


}
