package com.grafos.pratica3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.alg.clique.DegeneracyBronKerboschCliqueFinder;
import org.jgrapht.alg.clique.PivotBronKerboschCliqueFinder;
import org.jgrapht.alg.cycle.PatonCycleBase;
import org.jgrapht.alg.scoring.AlphaCentrality;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.alg.scoring.ClosenessCentrality;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.traverse.BreadthFirstIterator;

/**
 * Classe principal responsavel por realizar as funcionalidades
 * exigidas pela questao 2 
 *
 */
public class Principal {

	/**
	 * Metodo principal responsavel por iniccializar as atividades
	 */
	public static void main(String[] args) {

		String pathGraph = new File("").getAbsolutePath() + File.separator + "TEIA.gml";
		String pathSalvamento = new File("").getAbsolutePath() + File.separator;

		Graph<Object, RelationshipEdge> grafo = ImportSimpleGraphGML.importar(pathGraph);

		gerarCloseness(grafo, pathSalvamento + "closeness.txt");
		gerarBetweenness(grafo, pathSalvamento + "betweenness.txt");
		gerarAlphaCentrality(grafo, pathSalvamento + "alpha.txt");

		double triplets = get_NTriplets(grafo);
		double triangles = get_NTriangles(grafo);

		double coefCluster = 3 * triangles / triplets;
		salvar(coefCluster, pathSalvamento + "coefCluster.txt");

		int diameter = 0;
		ArrayList<Integer> a = get_allpathLenghts(grafo);
		int sum = 0;
		for (int i = 0; i < a.size(); i++) {
			sum = sum + a.get(i);
			if (diameter < a.get(i)) {
				diameter = a.get(i);
			}
		}
		double average = sum / a.size();
		salvar(diameter, pathSalvamento + "diametro.txt");
		salvar(average, pathSalvamento + "Distancia.txt");
		
		gerarCliques(grafo,pathSalvamento + "Cliques.txt");
		

	}

	/**
	 * Metodo responsavel peladeracao dos cliques do grafo
	 * @param grafo : um grafo analizado para geracao dos cliques
	 * @param path : Local onde sera salvoi os cliques obtidos
	 */
	private static void gerarCliques(Graph<Object, RelationshipEdge> grafo, String path) {
		DegeneracyBronKerboschCliqueFinder <Object,RelationshipEdge> cf2 = 
	    		new DegeneracyBronKerboschCliqueFinder <> (grafo); 
	    Iterator  <Set <Object>> it2 = cf2.iterator();
	    String saida = "DegenearyBronKerboschCliqueFinder cliques: \n";
	    while (it2.hasNext()) {
	    	saida += it2.next().toString() + " ";
	    }
	    
	    PivotBronKerboschCliqueFinder <Object,RelationshipEdge> cf3 = 
	    		new PivotBronKerboschCliqueFinder <> (grafo); 
	    Iterator  <Set <Object>> it3 = cf3.iterator();
	    saida += "\n\nPivotBronKerboschCliqueFinder cliques: \n";
	    while (it3.hasNext()) {
	    	saida += it3.next().toString() + " ";	    	
	    }
	    
	    salvar(saida, path);
	}

	/**
	 * Metodo responsavel por salvar um dado no formato de string em um arquivo de saida 
	 * @param saida : Dado que sera salvo
	 * @param path : O caminho onde o arquivo de saida se encontra
	 */
	private static void salvar(String saida, String path) {
		try {
			File file = new File(path);
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(saida);
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	private static ArrayList<Integer> get_allpathLenghts(Graph<Object, RelationshipEdge> g) {
		DijkstraShortestPath<Object, RelationshipEdge> p = new DijkstraShortestPath<>(g);
		ArrayList<Integer> a = new ArrayList<Integer>();
		BreadthFirstIterator<Object, RelationshipEdge> pf = new BreadthFirstIterator<>(g);
		while (pf.hasNext()) {
			Object v1 = pf.next();
			Iterator<Object> vs = g.vertexSet().iterator();
			while (vs.hasNext()) {
				Object v2 = vs.next();
				if (p.getPath(v1, v2) != null) {
					int dist = (p.getPath(v1, v2)).getLength();
					if (v1.equals(v2) == false) {
						a.add(dist);
					}
				}
			}
		}
		return a;
	}

	/**
	 * Metodo responsavel pelo salvamento de um valor de cluster
	 * @param coefCluster : Um double representao o coeficiente de cluter
	 * @param path : caminho onde será salvo o dado
	 */
	private static void salvar(double coefCluster, String path) {
		try {
			File file = new File(path);
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			String representacaoTextual = String.valueOf(coefCluster);
			bufferedWriter.write(representacaoTextual);
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo responsavel por buscar todos a quantidades de triangulos que ha no grafo
	 * @param g : Um grafo que sera analizado
	 * @return Um inteiro indicando a quantidade de triangulos que o grafo possui
	 */
	private static double get_NTriangles(Graph<Object, RelationshipEdge> g) {
		double triangles = 0;
		PatonCycleBase<Object, RelationshipEdge> pc = new PatonCycleBase<>(g);
		Iterator<List<RelationshipEdge>> it2 = ((pc.getCycleBasis()).getCycles()).iterator();
		while (it2.hasNext()) {
			List<RelationshipEdge> s = it2.next();
			if ((s).size() == 3) {
				// System.out.println(s);
				triangles++;
			}
		}
		return triangles;
	}

	/**
	 * Metodo responsavel por buscar o numero de triplets que o grafo possui
	 * @param g : Um grafo que sera analizado
	 * @return : Um inteiro indicando a quantidade de triplets que o grafo possui
	 */
	private static double get_NTriplets(Graph<Object, RelationshipEdge> g) {
		double triplets = 0;
		BreadthFirstIterator<Object, RelationshipEdge> cfi = new BreadthFirstIterator<>(g);
		while (cfi.hasNext()) {
			Object v = cfi.next();
			int n = (g.edgesOf(v)).size();
			if (n >= 2) {
				triplets = triplets + fact(n) / (2 * fact(n - 2));
			}
		}
		return triplets;
	}

	private static int fact(int n) {
		if (n == 1 || n == 0) {
			return 1;
		} else {
			return n * fact(n - 1);
		}
	}

	/**
	 * Metodo responsavel p0ela geracao do alpha centrality
	 * @param grafo : Um grafo utilizado para a geracao do alpha centrality
	 * @param pathSalvamento: O local onde sera salvo o resultado obtido
	 */
	private static void gerarAlphaCentrality(Graph<Object, RelationshipEdge> grafo, String pathSalvamento) {
		AlphaCentrality<Object, RelationshipEdge> alpha = new AlphaCentrality<>(grafo,0.1);
		Map<Object, Double> mapAplha = alpha.getScores();
		salvar(pathSalvamento, mapAplha);
	}

	/**
	 * Metodo responsavel pela geracao do betweeness 
	 * @param grafo : Grafo utulizado para a geracao do betweeness
	 * @param pathSalvamento : Local onde sera salvo o resultyado obtido
	 */
	private static void gerarBetweenness(Graph<Object, RelationshipEdge> grafo, String pathSalvamento) {
		BetweennessCentrality<Object, RelationshipEdge> betweenness = new BetweennessCentrality<>(grafo);
		Map<Object, Double> mapCentrality = betweenness.getScores();
		salvar(pathSalvamento, mapCentrality);

	}

	/**
	 * Metodo responsavel pela geracao do closeness 
	 * @param grafo : Grafo utulizado para a geracao do closeness
	 * @param pathSalvamento : Local onde sera salvo o resultyado obtido
	 */
	private static void gerarCloseness(Graph<Object, RelationshipEdge> grafo, String pathSalvamento) {

		ClosenessCentrality<Object, RelationshipEdge> centrality = new ClosenessCentrality<>(grafo);

		Map<Object, Double> mapCentrality = centrality.getScores();

		salvar(pathSalvamento, mapCentrality);

	}

	/**
	 * Metoto responsavel por salvar um mapa de objetos
	 * @param path : Local onde sera salvo
	 * @param mapa : Mapa que sera analizado para salvamento
	 */
	private static void salvar(String path, Map<Object, Double> mapa) {
		try {
			File file = new File(path);
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			String representacaoTextual = gerarString(mapa);
			bufferedWriter.write(representacaoTextual);
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo responsavelpor gerar uma representacao textual para um mapa
	 * @param mapa : Um mapa que sera analizado pra geracao da string
	 * @return : A string gerada
	 */
	private static String gerarString(Map<Object, Double> mapa) {
		String retorno = "{";
		for (Object key : mapa.keySet()) {
			if (retorno.length() == 1) {
				retorno += "[" + key.toString() + " : " + mapa.get(key) + "]";
			} else {
				retorno += ", [" + key.toString() + " : " + mapa.get(key) + "]";
			}
		}
		return retorno;
	}
}
