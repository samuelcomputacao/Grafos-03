import org.jgrapht.alg.cycle.HierholzerEulerianCycle;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.ClosestFirstIterator;


public class TourSearch {
	
	public static void main(String[] args) {
		
		SimpleWeightedGraph<String,DefaultWeightedEdge> g = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		g.addVertex("Fra"); 
		g.addVertex("Man");
		g.addVertex("Wur");
		g.addVertex("Stu");
		g.addVertex("Kas");
		g.addVertex("Num");
		g.addVertex("Mun");
		g.addVertex("Aug");
		g.addVertex("Erf");
		g.addVertex("Kar");
		g.setEdgeWeight(g.addEdge("Fra","Man"),85);
		g.setEdgeWeight(g.addEdge("Fra","Wur"),217);
		g.setEdgeWeight(g.addEdge("Fra","Kas"),173);
		g.setEdgeWeight(g.addEdge("Man","Kar"),80);
		g.setEdgeWeight(g.addEdge("Wur","Erf"),186);
		g.setEdgeWeight(g.addEdge("Wur","Num"),103);
		g.setEdgeWeight(g.addEdge("Num","Stu"),183);
		g.setEdgeWeight(g.addEdge("Kas","Mun"),502);
		g.setEdgeWeight(g.addEdge("Num","Mun"),167);
		g.setEdgeWeight(g.addEdge("Kar","Aug"),250);
		g.setEdgeWeight(g.addEdge("Aug","Mun"),84);

		ClosestFirstIterator <String,DefaultWeightedEdge> bfs = new ClosestFirstIterator <> (g);
		while (bfs.hasNext()) {
			System.out.println(bfs.next());
			
		HierholzerEulerianCycle <String,DefaultWeightedEdge> h = 
				new HierholzerEulerianCycle <> ();
		h.getEulerianCycle(g);
		
		}
		
	}
}
