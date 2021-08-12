package h07.graph;

import java.util.Iterator;
import java.util.NoSuchElementException;

class PathImpl<V, A> extends AbstractPath<V, A> {
	private PathElement pathHead;

	private class PathElement {
		private final V node;
		private A distance = null;
		private PathElement next = null;

		/**
		 * Creates a new element for a linked list which represents a path.
		 * 
		 * @param node to be added
		 */
		private PathElement(V node) {
			this.node = node;
		}

		/**
		 * Adds new element at the end of the linked list which represents a path.
		 * 
		 * @param distance to the next node (relative to the current)
		 * @param nextNode which follows the current with a specified distance
		 */
		private void addElement(A distance, V nextNode) {
			if (next != null)
				next.addElement(distance, nextNode);
			else {
				this.distance = distance;
				next = new PathElement(nextNode);
			}
		}

		/**
		 * Copies the path represented by a linked list. Values are the same and in the
		 * same order but the {@link PathElement} objects will differ:<br>
		 * {@code path != path.copy()}
		 * 
		 * @return a copy of the current instance
		 */
		private PathElement copy() {
			PathElement copy = new PathElement(node);
			if (next != null) {
				copy.distance = distance;
				copy.next = next.copy();
			}
			return copy;
		}
	}

	private class PathTraverser implements Traverser<V, A> {
		PathElement currentElement;

		private PathTraverser() {
			currentElement = pathHead.copy();
		}

		@Override
		public V getCurrentNode() {
			return currentElement.node;
		}

		@Override
		public A getDistanceToNextNode() {
			if (currentElement == null || currentElement.next == null)
				throw new IllegalStateException("There is no element left in the path.");
			return currentElement.distance;
		}

		@Override
		public void walkToNextNode() {
			if (currentElement == null || currentElement.next == null)
				throw new IllegalStateException("There is no element left in the path.");
			currentElement = currentElement.next;
		}

		@Override
		public boolean hasNextNode() {
			return currentElement.next != null;
		}
	}

	private class PathItarator implements Iterator<V> {
		private PathElement nextElement;

		private PathItarator() {
			nextElement = pathHead;
		}

		@Override
		public boolean hasNext() {
			return nextElement != null;
		}

		@Override
		public V next() {
			if (nextElement == null)
				throw new NoSuchElementException("There is no element left in the path.");
			V temp = nextElement.node;
			nextElement = nextElement.next;
			return temp;
		}

	}

	public PathImpl(V v1) {
		pathHead = new PathElement(v1);
	}

	private PathImpl(PathElement pathHead) {
		this.pathHead = pathHead;
	}

	@Override
	public Traverser<V, A> traverser() {
		return new PathTraverser();
	}

	@Override
	public Path<V, A> concat(V node, A distance) {
		if (node == null || distance == null)
			throw new NullPointerException("Node and distance must not be null.");
		PathElement headCopy = pathHead.copy();
		headCopy.addElement(distance, node);
		return new PathImpl<>(headCopy);
	}

	@Override
	public Iterator<V> iterator() {
		return new PathItarator();
	}

}
