import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import org.jgrapht.alg.shortestpath.*;

public class ShortestPath {
    // Algoritmo de Djikstra
	
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
	    
	    DijkstraShortestPath <String,DefaultWeightedEdge>  p = 
	    		new DijkstraShortestPath <> (g);
 
	    System.out.println(p.getPath("Fra","Mun"));
        
        System.out.println((p.getPaths("Fra")).getPath("Stu"));
        System.out.println((p.getPaths("Fra")).getPath("Aug"));
        System.out.println((p.getPaths("Fra")).getPath("Kas"));
        System.out.println((p.getPaths("Fra")).getPath("Erf"));
        
        System.out.println("Fra-Man: " +p.getPathWeight("Fra","Man"));
        System.out.println("Fra-Wur: " +p.getPathWeight("Fra","Wur"));
        System.out.println("Fra-Kas: " +p.getPathWeight("Fra","Kas"));
        System.out.println("Fra-Kar: " +p.getPathWeight("Fra","Kar"));
        System.out.println("Fra-Erf: " +p.getPathWeight("Fra","Erf"));
        System.out.println("Fra-Num: " +p.getPathWeight("Fra","Num"));
        System.out.println("Fra-Stu: " +p.getPathWeight("Fra","Stu"));
        System.out.println("Fra-Mun: " +p.getPathWeight("Fra","Mun"));
        System.out.println("Fra-Aug: " +p.getPathWeight("Fra","Aug"));	    
	}
}
