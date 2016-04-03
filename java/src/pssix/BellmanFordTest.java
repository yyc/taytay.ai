package pssix;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BellmanFordTest {

	@Test
	public void testIncremental() {

		AdjacencyMatrixGraph amg = new AdjacencyMatrixGraph("src/pssix/testFileIncremental.txt");
		BellmanFord bf = new BellmanFord(amg);

		bf.constructPath(0, 6);

		assertEquals("0 -> 1 -> 2 -> 3 -> 4 -> 5 -> 6", bf.getPath());
	}

	@Test
	public void testIncrementalCycles() {

		AdjacencyMatrixGraph amg = new AdjacencyMatrixGraph("src/pssix/testFileIncremental.txt");
		BellmanFord bf = new BellmanFord(amg);

		boolean actual = bf.hasNegativeCycle();

		assertEquals(false, actual);
	}

	@Test
	public void testNegativeCycles() {

		AdjacencyMatrixGraph amg = new AdjacencyMatrixGraph("src/pssix/testFileWithNegativeCycle.txt");
		BellmanFord bf = new BellmanFord(amg);

		boolean actual = bf.hasNegativeCycle();

		assertEquals(true, actual);
	}
}
