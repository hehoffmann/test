package h07.experiment;

import java.util.Comparator;
import java.util.Map;

import h07.algebra.IntegerAddition;
import h07.algorithm.Dijkstra;
import h07.graph.Path;

public class ChickenNuggets {
	private final static Map<Integer, Path<Integer, Integer>> chickenMap;
	static {
		chickenMap = new Dijkstra<Integer, Integer>().shortestPaths(
				new ChickenNuggetGraphFactory().createDirectedGraph(), 0, new IntegerAddition(),
				Comparator.naturalOrder());
	}

	public static int[] computePackageNumbers(int numberOfNuggets) {
//		Map<Integer, Path<Integer, Integer>> chickenMap = new Dijkstra<Integer, Integer>().shortestPaths(
//				new ChickenNuggetGraphFactory().createDirectedGraph(), 0, new IntegerAddition(),
//				Comparator.naturalOrder());

		int[] packageNumbers = { 0, 0, 0 };
		Path<Integer, Integer> path = chickenMap.get(numberOfNuggets % 6);
		Path.Traverser<Integer, Integer> traverser = path.traverser();
		while (traverser.hasNextNode()) {
			if (traverser.getDistanceToNextNode() == 9)
				packageNumbers[1]++;
			else
				packageNumbers[2]++;
			traverser.walkToNextNode();
		}
		double temp = (numberOfNuggets - 9.0 * packageNumbers[1] - 20.0 * packageNumbers[2]) / 6.0;
		if (temp < 0 || temp != (int) temp)
			packageNumbers = null;
		else
			packageNumbers[0] = (int) temp;

		return packageNumbers;
	}
}
