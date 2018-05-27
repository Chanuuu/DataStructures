package CodeJam2008;
import java.util.ArrayDeque;
import java.util.Deque;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

public class BFS {
	
	enum COLOR { WHITE,GRAY,BLACK};
	
	static class GraphNode{
		int d;
		String name;
		GraphNode pie=null;
		COLOR color;
		GraphNode(String name,COLOR c)
		{
			this.name=name;
			this.color=c;
		}
	
		public String toString()
		{
			return name + "[distance from source :"+d+"]";
		}
	}
	public MutableValueGraph<GraphNode,Integer> bfs(MutableValueGraph<GraphNode,Integer> graph,GraphNode s)
	{
		Deque<GraphNode>deque=new ArrayDeque<GraphNode>();
		s.color=COLOR.GRAY;
		s.d=0;
		deque.add(s);
		while(!deque.isEmpty())
		{
			GraphNode node=deque.poll();
			for(GraphNode adj:graph.adjacentNodes(node))
			{
				if(adj.color==COLOR.WHITE)
				{
					adj.color=COLOR.GRAY;
					adj.d=node.d+1;
					adj.pie=node;
					deque.offer(adj);
				}
				
			}
			node.color=COLOR.BLACK;
		}
		return graph;
	}

	public static void main(String[] args)
	{
		MutableValueGraph<GraphNode,Integer> graph=ValueGraphBuilder.directed().allowsSelfLoops(true).build();
		GraphNode A= new GraphNode("A",COLOR.WHITE);
		GraphNode B= new GraphNode("B",COLOR.WHITE);
		GraphNode C= new GraphNode("C",COLOR.WHITE);
		GraphNode D= new GraphNode("D",COLOR.WHITE);
		GraphNode E= new GraphNode("E",COLOR.WHITE);
		graph.putEdgeValue(A,B,1);
		graph.putEdgeValue(A,C,1);
		graph.putEdgeValue(B,C,1);
		graph.putEdgeValue(C,B,1);
		graph.putEdgeValue(C,D,1);
		graph.putEdgeValue(B,D,1);
		graph.putEdgeValue(C,E,1);
		graph.putEdgeValue(D,E,1);
		graph.putEdgeValue(E,D,1);
		BFS bfs= new BFS();
		graph=bfs.bfs(graph,A);
		System.out.println(graph.nodes());

	}
}
	

