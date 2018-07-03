import org.jgrapht.alg.*;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.*;
import org.jgrapht.io.*;

import java.util.*;

public class ConnectivityDirectedGraph {
	// Conectividade de Grafos Directionados

	public static void main(String[] args) {

	    VertexProvider <DefaultVertex> vp1 = 
	    		(label,attributes) -> new DefaultVertex (label,attributes);
	    EdgeProvider <DefaultVertex,RelationshipDirectedEdge> ep1 = 
	    		(from,to,label,attributes) -> new RelationshipDirectedEdge(from,to,attributes);
		GmlImporter <DefaultVertex,RelationshipDirectedEdge> gmlImporter = new GmlImporter <> (vp1,ep1);
	    DefaultDirectedGraph<DefaultVertex,RelationshipDirectedEdge> graphgml = new DefaultDirectedGraph<>(RelationshipDirectedEdge.class);
   	    try {
   	    	// Testar com arquivos strongly1.gml ... strongly4.gml
	        gmlImporter.importGraph(graphgml, ImportGraph.readFile("C:\\Users\\patricia\\Dropbox\\workspace\\Graph Theory\\src\\strongly4.gml"));
	      } catch (ImportException e) {
	        throw new RuntimeException(e);
	      }	    		
 	    
	    Set <DefaultVertex> V = new HashSet <DefaultVertex>(graphgml.vertexSet());
	    Set <DefaultEdge> E = new HashSet <DefaultEdge>(graphgml.edgeSet());
	    
 	    System.out.println("Ordem do Grafo G: " + V.size());
 	    System.out.println("Tamanho do Grafo G: " + E.size());
	    System.out.println("V(G): " + V.toString());
	    System.out.println("E(G): " + E.toString());

	    ConnectivityInspector <DefaultVertex,RelationshipDirectedEdge> k = 
	    		new ConnectivityInspector <> (graphgml);
	    System.out.println("Is connected? " + k.isGraphConnected());
	    
	    System.out.println("Connected Sets: " + k.connectedSets());
	    
        DefaultVertex source = DefaultVertexSet.getVertexfromLabel(V,"a");
        DefaultVertex target = DefaultVertexSet.getVertexfromLabel(V,"e");
	    
	    AllDirectedPaths <DefaultVertex,RelationshipDirectedEdge> p = new AllDirectedPaths <> (graphgml);
	    System.out.println("Paths from " + source + " to " + target + "? " + 
	                           p.getAllPaths(source,target,true,null));
	    
	}

}

