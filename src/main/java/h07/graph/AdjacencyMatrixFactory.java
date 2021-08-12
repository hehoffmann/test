package h07.graph;

public class AdjacencyMatrixFactory<V, A> implements DirectedGraphFactory<V, A> {
	private final V[] nodes;
	private final A[][] adjacencyMatrix;

	public AdjacencyMatrixFactory(V[] nodes, A[][] adjacencyMatrix) {
		if (nodes == null || adjacencyMatrix == null)
			throw new NullPointerException("Nodes and adjacencyMatrix must not be null.");
		for (V node : nodes)
			if (node == null)
				throw new NullPointerException("Each node within nodes must not be null.");
		if (nodes.length != adjacencyMatrix.length || adjacencyMatrix.length == 0
				|| nodes.length != adjacencyMatrix[0].length)
			throw new IllegalArgumentException(
					"Nodes must have the same length as both dimensions of adjacencyMatrix and they must be at least one.");
		this.nodes = nodes;
		this.adjacencyMatrix = adjacencyMatrix;
	}

	@Override
	public DirectedGraph<V, A> createDirectedGraph() {
		DirectedGraph<V, A> graph = new DirectedGraphImpl<>();

		for (V node : nodes)
			graph.addNode(node);

		for (int i = 0; i < nodes.length; i++)
			for (int j = 0; j < nodes.length; j++)
				if (adjacencyMatrix[i][j] != null)
					graph.connectNodes(nodes[i], adjacencyMatrix[i][j], nodes[j]);


		return graph;
	}

}
