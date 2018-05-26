package CodeJam2008;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

/*
 * Implementaion of Kosaraju's algorithm to find strongly connected components in a Graph 
 * wiki :https://en.wikipedia.org/wiki/Kosaraju%27s_algorithm
 * A strongly connected component of a graph G=(V,E) is Gsc=(Vsc,Esc) such that for each pair of vertices in Gsc (u,v)
 * u~v and v~u  hat is both u and v are reachable from each other. 
 * The code uses MutableValueGraph from Google Guava Libraries .
 * 
 * @author: Debapriya Biswas
 * */
public class StronglyConnectedComponents {

	private static int time = 0;
	private Deque<GraphNode> stack = new ArrayDeque<GraphNode>();
	private List<HashSet<GraphNode>> components = new ArrayList<HashSet<GraphNode>>();

	enum COLOR {
		WHITE, GRAY, BLACK
	};

	// node structure
	static class GraphNode {
		int d;
		int f;
		String name;
		GraphNode pie = null;
		COLOR color;

		GraphNode(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}

	// Implementation of DFS algorithm from CLRS book . The nodes are pushed
	// into a stack based on fished time .
	private MutableValueGraph<GraphNode, Integer> dfs(MutableValueGraph<GraphNode, Integer> graph) {
		for (GraphNode node : graph.nodes())
			node.color = COLOR.WHITE;

		for (GraphNode node : graph.nodes()) {
			if (node.color == COLOR.WHITE)
				dfs_visit(graph, node, stack);
		}

		return graph;
	}

	private List<HashSet<GraphNode>> dfsOnTranspose(MutableValueGraph<GraphNode, Integer> graph) {
		for (GraphNode node : graph.nodes())
			node.color = COLOR.WHITE;

		while (!stack.isEmpty()) {
			GraphNode node = stack.pop();

			if (node.color == COLOR.WHITE) {
				HashSet<GraphNode> component = new HashSet<GraphNode>();
				components.add(dfs_visit_on_transpose(graph, node, component));
			}
		}

		return components;
	}

	private HashSet<GraphNode> dfs_visit_on_transpose(MutableValueGraph<GraphNode, Integer> graph, GraphNode node,
			HashSet<GraphNode> set) {
		node.color = COLOR.GRAY;
		set.add(node);
		node.d = ++time;
		for (GraphNode adj : graph.adjacentNodes(node)) {

			if (adj.color == COLOR.WHITE && graph.edgeValueOrDefault(node, adj, -1) != null) {
				adj.pie = node;
				dfs_visit_on_transpose(graph, adj, set);
			}

		}
		node.f = ++time;
		node.color = COLOR.BLACK;
		stack.push(node);
		return set;
	}

	private void dfs_visit(MutableValueGraph<GraphNode, Integer> graph, GraphNode node, Deque<GraphNode> stack) {
		node.color = COLOR.GRAY;
		node.d = ++time;
		for (GraphNode adj : graph.adjacentNodes(node)) {

			if (adj.color == COLOR.WHITE && graph.edgeValueOrDefault(node, adj, -1) != null) {
				adj.pie = node;
				adj.color = COLOR.GRAY;
				dfs_visit(graph, adj, stack);
			}

		}
		node.f = ++time;
		node.color = COLOR.BLACK;
		stack.push(node);
	}

	public static void main(String[] args) {

		GraphNode A = new GraphNode("A");
		GraphNode B = new GraphNode("B");
		GraphNode C = new GraphNode("C");
		GraphNode D = new GraphNode("D");
		GraphNode E = new GraphNode("E");
		GraphNode F = new GraphNode("F");
		GraphNode G = new GraphNode("G");
		GraphNode H = new GraphNode("H");
		GraphNode I = new GraphNode("I");
		GraphNode J = new GraphNode("J");
		GraphNode K = new GraphNode("K");
		MutableValueGraph<GraphNode, Integer> graph = ValueGraphBuilder.directed().build();
		graph.putEdgeValue(A, B, 1);
		graph.putEdgeValue(B, C, 1);
		graph.putEdgeValue(C, A, 1);
		graph.putEdgeValue(B, D, 1);
		graph.putEdgeValue(D, E, 1);
		graph.putEdgeValue(E, F, 1);
		graph.putEdgeValue(F, D, 1);
		graph.putEdgeValue(G, F, 1);
		graph.putEdgeValue(G, H, 1);
		graph.putEdgeValue(H, I, 1);
		graph.putEdgeValue(I, J, 1);
		graph.putEdgeValue(J, G, 1);
		graph.putEdgeValue(J, K, 1);
		// graph.putEdgeValue(G, F, 1);
		StronglyConnectedComponents scc = new StronglyConnectedComponents();
		// First pass DFS
		graph = scc.dfs(graph);
		// System.out.println(dfs.stack);
		MutableValueGraph<GraphNode, Integer> transposeGraph = Graphs.copyOf(Graphs.transpose(graph));
		// Second pass DFS on the transpose graph
		System.out.println(scc.dfsOnTranspose(transposeGraph));

	}

}
