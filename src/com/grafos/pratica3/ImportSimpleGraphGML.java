package com.grafos.pratica3;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.io.GmlImporter;
import org.jgrapht.io.EdgeProvider;
import org.jgrapht.io.ImportException;
import org.jgrapht.io.VertexProvider;

public class ImportSimpleGraphGML {
	/**
	 * Metodo responsavel por importar um grafo gml
	 * @param path : Loval onde se encontra o arquivo gml
	 * @return O grafo importado
	 */
	public static  Graph<Object, RelationshipEdge> importar (String path) {
	    VertexProvider <Object> vp1 = 
	    		(label,attributes) -> new DefaultVertex (label,attributes);
	    EdgeProvider <Object,RelationshipEdge> ep1 = 
	    		(from,to,label,attributes) -> new RelationshipEdge(from,to,attributes);
		GmlImporter <Object,RelationshipEdge> gmlImporter = new GmlImporter <> (vp1,ep1);
	    Graph<Object, RelationshipEdge> graphgml = new SimpleGraph<>(RelationshipEdge.class);
  	    try {
	        gmlImporter.importGraph(graphgml, ImportGraph.readFile(path));
	      } catch (ImportException e) {
	        throw new RuntimeException(e);
	      }	   
  	    return graphgml;
   	 
	}
	    
}

