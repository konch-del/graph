import org.jgrapht.Graph;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.alg.scoring.ClosenessCentrality;
import org.jgrapht.alg.scoring.EigenvectorCentrality;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.csv.CSVExporter;
import org.jgrapht.nio.csv.CSVFormat;
import org.jgrapht.nio.csv.VisioExporter;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.*;
import java.util.Collections;
import java.util.Map;

public class GraphUtils {

    //Центральность по близости
    public static Integer closenessCentrality(Graph<Integer, DefaultEdge> graph){
        ClosenessCentrality<Integer, DefaultEdge> closenessCentrality = new ClosenessCentrality<>(graph);
        Map<Integer, Double> scores = closenessCentrality.getScores();
        return Collections.max(scores.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
    //Центральность по посреднечеству
    public static Integer betweennessCentrality(Graph<Integer, DefaultEdge> graph){
        BetweennessCentrality<Integer, DefaultEdge> betweennessCentrality = new BetweennessCentrality<>(graph);
        Map<Integer, Double> scores = betweennessCentrality.getScores();
        return Collections.max(scores.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
    //Центральность по собственному вектору
    public static Integer eigenvectorCentrality(Graph<Integer, DefaultEdge> graph){
        EigenvectorCentrality<Integer, DefaultEdge> eigenvectorCentrality = new EigenvectorCentrality<>(graph);
        Map<Integer, Double> scores = eigenvectorCentrality.getScores();
        return Collections.max(scores.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public static void saveGraph(Graph<Integer, DefaultEdge> graph, String file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(graph);
    }

    public static void printGraphInVZ(Graph<Integer, DefaultEdge> graph, String file) throws FileNotFoundException {
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        DOTExporter exporter = new DOTExporter(v -> v.toString());
        exporter.exportGraph(graph, fileOutputStream);
    }

    public static void printGraphInSVG(Graph<Integer, DefaultEdge> graph, String file) throws FileNotFoundException {
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        VisioExporter exporter = new VisioExporter(v -> v.toString());
        exporter.exportGraph(graph, fileOutputStream);
    }

    public static void printGraphInCSV(Graph<Integer, DefaultEdge> graph, String file) throws FileNotFoundException {
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        CSVExporter exporter = new CSVExporter((v -> v.toString()), CSVFormat.MATRIX, ',');
        exporter.exportGraph(graph, fileOutputStream);
    }

    public static Graph<Integer, DefaultEdge> readGraph(String file) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        return (Graph<Integer, DefaultEdge>) objectInputStream.readObject();
    }
    public static void printStats(Graph<Integer, DefaultEdge> graph, String file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write("closenessCentrality: " + closenessCentrality(graph));
        writer.write("eigenvectorCentrality: " + eigenvectorCentrality(graph));
        writer.write("betweennessCentrality: " + betweennessCentrality(graph));
        writer.close();
    }
}
