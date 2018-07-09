package com.grafos.pratica3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.alg.cycle.PatonCycleBase;
import org.jgrapht.alg.scoring.AlphaCentrality;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.alg.scoring.ClosenessCentrality;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.BreadthFirstIterator;

public class Principal {

	public static void main(String[] args) {

		String pathGraph = new File("").getAbsolutePath() + File.separator + "rede.gml";
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
		salvar (diameter,pathSalvamento+"diametro.txt");
		salvar(average,pathSalvamento+"Distancia.txt");
		

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
				int dist = (p.getPath(v1, v2)).getLength();
				if (v1.equals(v2) == false) {
					a.add(dist);
				}
			}
		}
		return a;
	}

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

	private static void gerarAlphaCentrality(Graph<Object, RelationshipEdge> grafo, String pathSalvamento) {
		AlphaCentrality<Object, RelationshipEdge> alpha = new AlphaCentrality<>(grafo);
		Map<Object, Double> mapAplha = alpha.getScores();
		salvar(pathSalvamento, mapAplha);
	}

	private static void gerarBetweenness(Graph<Object, RelationshipEdge> grafo, String pathSalvamento) {
		BetweennessCentrality<Object, RelationshipEdge> betweenness = new BetweennessCentrality<>(grafo);
		Map<Object, Double> mapCentrality = betweenness.getScores();
		salvar(pathSalvamento, mapCentrality);

	}

	private static void gerarCloseness(Graph<Object, RelationshipEdge> grafo, String pathSalvamento) {

		ClosenessCentrality<Object, RelationshipEdge> centrality = new ClosenessCentrality<>(grafo);

		Map<Object, Double> mapCentrality = centrality.getScores();

		salvar(pathSalvamento, mapCentrality);

	}

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