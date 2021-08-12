package h07.graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

class DirectedGraphImpl<V, A> implements DirectedGraph<V, A> {

	private final Set<Node> nodes;

	private class Node {
		final V vertex;
		final Set<Edge> arcs;

		Node(V vertex) {
			this.vertex = vertex;
			arcs = new HashSet<>();
		}

	}

	private class Edge {
		final Node end;
		A weight;

		Edge(Node end, A weight) {
			this.end = end;
			this.weight = weight;
		}
	}

	DirectedGraphImpl() {
		nodes = new HashSet<>();
	}

	@Override
	public Collection<V> getAllNodes() {
		return nodes.stream().map(node -> node.vertex).collect(Collectors.toUnmodifiableSet());
//				.collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
	}

	@Override
	public Collection<V> getChildrenForNode(V node) {
		if (node == null)
			throw new NullPointerException("The node must not be null.");
		return nodes.stream().filter(n -> n.vertex.equals(node)).findFirst()
				.orElseThrow(() -> new NoSuchElementException("The node is not part of the graph.")).arcs.stream()
						.map(edge -> edge.end.vertex).collect(Collectors.toUnmodifiableSet());
//						.collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
	}

	@Override
	public A getArcWeightBetween(V from, V to) {
		if (from == null || to == null)
			throw new NullPointerException("Source and destination node must not be null.");
		return nodes.stream().filter(node -> node.vertex.equals(from)).findFirst()
				.orElseThrow(() -> new NoSuchElementException("The source node is not part of the graph.")).arcs
						.stream().filter(edge -> edge.end.vertex.equals(to)).findFirst()
						.orElseThrow(() -> new NoSuchElementException(
								"The destination node is no child of the source node.")).weight;
	}

	@Override
	public void addNode(V node) {
		if (node == null)
			throw new NullPointerException("The node must not be null.");
		if (nodes.stream().filter(n -> n.vertex.equals(node)).findAny().isPresent())
			throw new IllegalArgumentException("The node is already part of the graph.");
		nodes.add(new Node(node));
	}

	@Override
	public void removeNode(V node) {
		if (node == null)
			throw new NullPointerException("The node must not be null.");
		if (nodes.stream().filter(n -> n.vertex.equals(node)).findAny().isEmpty())
			throw new NoSuchElementException("The node is not part of the graph.");
		// remove all edges with 'node' as destination
		nodes.stream().forEach(n -> n.arcs.removeIf(edge -> edge.end.vertex.equals(node)));
		// remove 'node' itself
		nodes.removeIf(n -> n.vertex.equals(node));
	}

	@Override
	public void connectNodes(V from, A weight, V to) {
		if (from == null || weight == null || to == null)
			throw new NullPointerException("Source node, destination node and the weight must not be null.");
		Node source = nodes.stream().filter(node -> node.vertex.equals(from)).findFirst()
				.orElseThrow(() -> new NoSuchElementException("The source node is not part of the graph."));
		Node dest = nodes.stream().filter(node -> node.vertex.equals(to)).findFirst()
				.orElseThrow(() -> new NoSuchElementException("The destination node is not part of the graph."));
		if (source.arcs.stream().filter(edge -> edge.end.equals(dest)).findAny().isPresent())
			throw new IllegalArgumentException("There already exists a edge between source and destination.");
		source.arcs.add(new Edge(dest, weight));
	}

	@Override
	public void disconnectNodes(V from, V to) {
		if (from == null || to == null)
			throw new NullPointerException("Source and destination node must not be null.");
		Node source = nodes.stream().filter(node -> node.vertex.equals(from)).findFirst()
				.orElseThrow(() -> new NoSuchElementException("The source node is not part of the graph."));
		Node dest = nodes.stream().filter(node -> node.vertex.equals(to)).findFirst()
				.orElseThrow(() -> new NoSuchElementException("The destination node is not part of the graph."));
		if (!source.arcs.removeIf(edge -> edge.end.equals(dest)))
			throw new NoSuchElementException("There does not exist a edge between source and destination.");
	}

}
