import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.io.CSVFormat;
import org.jgrapht.io.CSVImporter;
import org.jgrapht.io.EdgeProvider;
import org.jgrapht.io.ImportException;
import org.jgrapht.io.VertexProvider;

public class ImportSimpleGraphCSV {
	// Importa Grafo Simples no formato CSV sem rótulos nas aresta e
	// Grafo Simples no formato GML com rótulo nos vértices e nas arestas

	public static void main(String[] args) {
	    //Import CSV
	    VertexProvider <Object> vp = (label,attributes) -> label;
	    EdgeProvider <Object,DefaultEdge> ep = (from,to,label,attributes) -> new DefaultEdge();
  
	    CSVImporter <Object,DefaultEdge> csvImporter = new CSVImporter <> (vp,ep);
		csvImporter.setFormat(CSVFormat.EDGE_LIST);
	    Graph<Object, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
   	    try {
	        csvImporter.importGraph(graph, ImportGraph.readFile("C:\\Users\\patricia\\Dropbox\\workspace\\Graph Theory\\src\\csv-example.txt"));
	      } catch (ImportException e) {
	        throw new RuntimeException(e);
	      }	 
   	    System.out.println("Grafo importado do arquivo CSV: ");
	    System.out.println("Arestas: "+ graph.edgeSet());
	    System.out.println("Vértices: " + graph.vertexSet());
	}
	    
}

