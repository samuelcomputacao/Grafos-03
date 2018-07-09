package com.grafos.pratica3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.alg.scoring.ClosenessCentrality;
import org.jgrapht.graph.DefaultEdge;


public class Principal {

	public static void main(String[] args) {
		
		String pathGraph = new File("").getAbsolutePath()+File.separator+"rede.gml";
		String pathSalvamento =  new File("").getAbsolutePath()+File.separator;
		
		Graph<String, DefaultEdge> grafo = ImportGraph.importar(pathGraph);
		
		gerarCloseness(grafo, pathSalvamento+"closeness.txt");
		gerarBetweenness(grafo, pathSalvamento+"betweenness.txt");
		
		
	}

	private static void gerarBetweenness(Graph<String, DefaultEdge> grafo, String pathSalvamento) {
		BetweennessCentrality<String, DefaultEdge> betweenness = new BetweennessCentrality<>(grafo);
		Map<String,Double> mapCentrality = betweenness.getScores();
		salvar(pathSalvamento,mapCentrality);
		
	}

	private static void gerarCloseness(Graph<String, DefaultEdge> grafo, String pathSalvamento) {
		
		ClosenessCentrality< String , DefaultEdge> centrality = new ClosenessCentrality<>(grafo);
		
		Map<String,Double> mapCentrality = centrality.getScores();

		salvar(pathSalvamento, mapCentrality);
		
	}
	private static void salvar(String path, Map<String,Double> mapa){
		try{
		File file = new File(path);
		FileWriter fileWriter = new FileWriter(file);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		String representacaoTextual = gerarString(mapa);
		bufferedWriter.write(representacaoTextual);
		bufferedWriter.close();
		fileWriter.close();
		}catch (IOException e) {
			e.printStackTrace();
		}

	
	}
	
	private static String gerarString(Map<String, Double> mapa) {
		String  retorno= "{";
		for(String key : mapa.keySet()){
			if(retorno.length()==1){
				retorno += "["+key+" : "+mapa.get(key)+"]";
			}else{
				retorno += ", ["+key+" : "+mapa.get(key)+"]";
			}
		}
		return retorno;
	}
}
