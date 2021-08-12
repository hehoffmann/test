package h07.algorithm;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Set;

import h07.algebra.Monoid;
import h07.graph.DirectedGraph;
import h07.graph.Path;

public class Dijkstra<V, A> implements ShortestPathsAlgorithm<V, A> {

	public Dijkstra() {
	}

	@Override
	public Map<V, Path<V, A>> shortestPaths(DirectedGraph<V, A> graph, V startNode, Monoid<A> monoid,
			Comparator<? super A> comparator) {
		if (graph == null || startNode == null || monoid == null || comparator == null)
			throw new NullPointerException("All parameters must not be null.");

		Set<V> leftNodes = new HashSet<>();
		leftNodes.addAll(graph.getAllNodes());
		if (!leftNodes.remove(startNode))
			throw new NoSuchElementException("The specified startNode is not part of the graph.");

		Comparator<Path<V, A>> pathComparator = (Path<V, A> p1, Path<V, A> p2) -> comparator
				.compare(pathLength(p1, monoid), pathLength(p2, monoid));
		PriorityQueue<Path<V, A>> queue = new PriorityQueue<>(pathComparator);
		for (V child : graph.getChildrenForNode(startNode))
			queue.add(Path.of(startNode, graph.getArcWeightBetween(startNode, child), child));

		Map<V, Path<V, A>> resultMap = new HashMap<>();
		resultMap.put(startNode, Path.of(startNode));

		while (!queue.isEmpty()) {
			Path<V, A> currPath = queue.poll();

			// find current node (last one in currPath)
			V currNode;
			Iterator<V> iterator = currPath.iterator();
			do {
				currNode = iterator.next();
			} while (iterator.hasNext());


			if (!leftNodes.contains(currNode))
				continue; // another path has been found previously which must be shorter

			// found shortest path for currNode
			resultMap.put(currNode, currPath);
			leftNodes.remove(currNode);

			for (V child : graph.getChildrenForNode(currNode))
				if (leftNodes.contains(child))
					queue.add(currPath.concat(child, graph.getArcWeightBetween(currNode, child)));
		}

		return resultMap;
	}

	private A pathLength(Path<V, A> path, Monoid<A> monoid) {
		Path.Traverser<V, A> traverser = path.traverser();
		A length = monoid.zero();
		while (traverser.hasNextNode()) {
			length = monoid.add(length, traverser.getDistanceToNextNode());
			traverser.walkToNextNode();
		}
		return length;
	}

}
