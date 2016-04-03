package pssix;

public class BellmanFord implements IBellmanFord {

	// Stores the Adjacency Matrix of the current Graph to run Bellman-Ford with
	private AdjacencyMatrixGraph m_amg;

	// Tracks who is the current parent vertex, for each vertex in the graph
	private int[] predecessors;

	// Store the total distance of each vertex from the source vertex
	private int[] distanceEstimates;

	// Stores the source and destination vertices chosen for this iteration
	private int m_source;
	private int m_destination;

	private int numVertices;

	public BellmanFord(AdjacencyMatrixGraph amg) {
		m_amg = amg;
		numVertices = amg.getNumberOfVertices();
		predecessors = new int[numVertices];
		distanceEstimates = new int[numVertices];
		for (int i = 0; i < numVertices; i++) {
			predecessors[i] = -1;
			distanceEstimates[i] = Integer.MAX_VALUE;
		}
	}

	// Construct the shortest path from source to destination
	@Override
	public void constructPath(int source, int destination) {
		int i = 0;
		m_source = source;
		m_destination = destination;
		distanceEstimates[source] = 0;
		// Shortest path should be n - 1 in length, so we relax n - 1 times;
		while (RelaxAll() && i++ < numVertices - 1) {
		}
	}

	// Relaxes every edge in the graph
	private boolean RelaxAll() {
		boolean relaxed = false;
		for (int i = 0; i < numVertices; i++) {
			for (int j = 0; j < numVertices; j++) {
				relaxed = Relax(i, j) || relaxed;
				relaxed = Relax(j, i) || relaxed;
			}
		}
		return relaxed;
	}

	// Relaxes an edge
	private boolean Relax(int source, int destination) {
		int weight = m_amg.getEdgeWeight(source, destination);
		if (distanceEstimates[source] != Integer.MAX_VALUE
				&& distanceEstimates[destination] > (distanceEstimates[source] + weight)) {
			distanceEstimates[destination] = (distanceEstimates[source] + weight);
			predecessors[destination] = source;
			return true; // The edge was successfully relaxed
		} else {
			return false; // The edge wasn't relaxed!
		}
	}

	// Checks if at least 1 negative cycle exists in the Graph
	@Override
	public boolean hasNegativeCycle() {
		if (this.numVertices < 2) {
			return false;
		} else {
			this.constructPath(m_source, m_destination);
			return RelaxAll();
		}
	}

	// Produces the String for printing the shortest path
	@Override
	public String getPath() {

		int currentVertex = this.m_destination;
		StringBuilder prepender = new StringBuilder();
		StringBuilder resultHolder = new StringBuilder("" + this.m_destination);

		while (currentVertex != this.m_source) {

			currentVertex = this.predecessors[currentVertex];

			prepender.append(currentVertex);
			prepender.append(" -> ");
			prepender.append(resultHolder.toString());

			resultHolder.replace(0, resultHolder.length(), prepender.toString());
			prepender.delete(0, prepender.length());
		}

		return resultHolder.toString();
	}

	public static void main(String[] args) {

		// Example use
		AdjacencyMatrixGraph amg = new AdjacencyMatrixGraph("src/pssix/testFileIncremental.txt");
		BellmanFord bf = new BellmanFord(amg);
		System.out.println("Running Bellman-Ford on testFileIncremental...");
		bf.constructPath(0, 6);
		System.out.println("Has negative cycle(s): " + bf.hasNegativeCycle());
		System.out.println("Shortest path: " + bf.getPath());
	}

}
