import org.jgrapht.*;
import org.jgrapht.alg.cycle.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class MySimpleGraph {
    // Grafo simples e algumas propriedades

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
		
		g.addVertex("MA");  	g.addVertex("PI");  	g.addVertex("CE");  
		g.addVertex("RN");		g.addVertex("PB");  	g.addVertex("PE");  	
		g.addVertex("AL");  	g.addVertex("SE");		g.addVertex("BA");
		
		g.addEdge("MA","PI");  	g.addEdge("PI","CE");  	g.addEdge("PI","PE");
		g.addEdge("PI","BA");	g.addEdge("CE","PE");	g.addEdge("CE","PB");
		g.addEdge("CE","RN");	g.addEdge("PE","PB");	g.addEdge("RN","PB");
		g.addEdge("PE","BA");	g.addEdge("PE","AL");	g.addEdge("BA","AL");
		g.addEdge("BA","SE");	g.addEdge("AL","SE");
		
		System.out.println("Vértices: " + g.vertexSet());
		System.out.println("Arestas: " + g.edgeSet());

		
		// Vizinhos NG(v)
		System.out.println("\nArestas que possuem o vértices PE como Terminal: " + g.edgesOf("PE"));
		
		// Ciclos Simples Básicos
		PatonCycleBase <String,DefaultEdge> c = new PatonCycleBase <> (g);
		System.out.println("\nCiclos Básicos do grafo: " + c.findCycleBase());
		
		
		// Comparando Arestas
	    DefaultEdge e1 = g.getEdge("CE","PI");
	    DefaultEdge e2 = g.getEdge("PI","CE");
	    System.out.println("\nA ordem de vértices na aresta não importa: " + e1.equals(e2));
	    

		

	}

}
