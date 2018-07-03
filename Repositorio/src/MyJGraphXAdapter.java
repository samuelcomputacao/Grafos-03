import java.awt.*;
import javax.swing.*;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;
import com.mxgraph.layout.*;
import com.mxgraph.swing.*;

public class MyJGraphXAdapter
    extends JFrame {
	// Cria um grafo e representa em um frame gráfico
	
    private static final long serialVersionUID = 2202072534703043194L;

    private static final Dimension DEFAULT_SIZE = new Dimension(350, 350);

    private JGraphXAdapter<Object, DefaultEdge> jgxAdapter;
    
    MyJGraphXAdapter () {
    	super ("My JGraphX Demo");    
		
		// create a JGraphT graph
        ListenableGraph<Object, DefaultEdge> lgraph =
            new DefaultListenableGraph <> (createGraph());

        // create a visualization using JGraph, via an adapter
        jgxAdapter = new JGraphXAdapter<>(lgraph);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        getContentPane().add(component);

        // positioning via jgraphx layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);
        // center the circle
        int radius = 100;
        layout.setX0((DEFAULT_SIZE.width / 2.5) - radius);
        layout.setY0((DEFAULT_SIZE.height / 2.5) - radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);

        layout.execute(jgxAdapter.getDefaultParent());
    }
    
    public static void main(String[] args)
    
    {
        MyJGraphXAdapter m = new MyJGraphXAdapter();
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m.setSize(DEFAULT_SIZE);
		m.setVisible(true);
    }
    
    public Graph <Object,DefaultEdge> createGraph () {
 
    	Graph<Object, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);
		
        String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";
        String v4 = "v4";

        // add some sample data (graph manipulated via JGraphX)
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);

        g.addEdge(v1, v2);
        g.addEdge(v2, v3);
        g.addEdge(v3, v1);
        g.addEdge(v4, v3);
        
        return g;
    }

}
// End JGraphXAdapterDemo.java
