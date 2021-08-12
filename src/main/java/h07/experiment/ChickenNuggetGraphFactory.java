package h07.experiment;

import java.util.Collection;

import h07.graph.AdjacencyMatrixFactory;
import h07.graph.DirectedGraph;
import h07.graph.DirectedGraphFactory;

public class ChickenNuggetGraphFactory implements DirectedGraphFactory<Integer, Integer> {

	private class DirectedChickenNuggetGraph implements DirectedGraph<Integer, Integer> {
		private final DirectedGraph<Integer, Integer> graph;

		private DirectedChickenNuggetGraph(DirectedGraph<Integer, Integer> graph) {
			this.graph = graph;
		}

		@Override
		public Collection<Integer> getAllNodes() {
			return graph.getAllNodes();
		}

		@Override
		public Collection<Integer> getChildrenForNode(Integer node) {
			return graph.getChildrenForNode(node);
		}

		@Override
		public Integer getArcWeightBetween(Integer from, Integer to) {
			return graph.getArcWeightBetween(from, to);
		}

		@Override
		public void addNode(Integer node) {
			throw new UnsupportedOperationException("This method is not supported for the specified instance.");
		}

		@Override
		public void removeNode(Integer node) {
			throw new UnsupportedOperationException("This method is not supported for the specified instance.");
		}

		@Override
		public void connectNodes(Integer from, Integer weight, Integer to) {
			throw new UnsupportedOperationException("This method is not supported for the specified instance.");
		}

		@Override
		public void disconnectNodes(Integer from, Integer to) {
			throw new UnsupportedOperationException("This method is not supported for the specified instance.");
		}
	}

	public ChickenNuggetGraphFactory() {
	}

	@Override
	public DirectedGraph<Integer, Integer> createDirectedGraph() {
		Integer[] nodes = { 0, 1, 2, 3, 4, 5 };

		Integer[][] adjacencyMatrix = {
				{ null, null, 20, 	9,	  null, null },
				{ null, null, null, 20,   9,	null },
				{ null, null, null, null, 20,	9 	 },
				{ 9,	null, null, null, null, 20	 },
				{ 20,	9,	  null, null, null, null },
				{ null, 20,	  9,	null, null, null } };

		return new DirectedChickenNuggetGraph(
				new AdjacencyMatrixFactory<>(nodes, adjacencyMatrix).createDirectedGraph());
	}

}
