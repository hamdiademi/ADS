package Lab10;

import java.util.*;
import java.util.Map.Entry;

class AdjacencyListGraph<T> {
    private Map<T, Set<T>> adjacencyList;
    public AdjacencyListGraph() {
        this.adjacencyList = new HashMap<>();
    }

    // Add a vertex to the graph
    public void addVertex(T vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            adjacencyList.put(vertex, new HashSet<>());
        }
    }

    // Add an edge to the graph
    public void addEdge(T source, T destination) {
        addVertex(source);
        addVertex(destination);

        adjacencyList.get(source).add(destination);
        adjacencyList.get(destination).add(source); // for undirected graph
    }

    // Remove an edge from the graph
    public void removeEdge(T source, T destination) {
        if (adjacencyList.containsKey(source)) {
            adjacencyList.get(source).remove(destination);
        }
        if (adjacencyList.containsKey(destination)) {
            adjacencyList.get(destination).remove(source); // for undirected graph
        }
    }

    // Get all neighbors of a vertex
    public Set<T> getNeighbors(T vertex) {
        return adjacencyList.getOrDefault(vertex, new HashSet<>());
    }


    @Override
    public String toString() {
        String ret = new String();
        for (Entry<T, Set<T>> vertex : adjacencyList.entrySet())
            ret += vertex.getKey() + ": " + vertex.getValue() + "\n";
        return ret;
    }

}

public class UnorientedGraph {
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        int n=scanner.nextInt();
        AdjacencyListGraph<Integer> graph=null;
        for (int i = 0; i < n+1; i++) {
        String line= scanner.nextLine();
        String []parts=line.split(" ");

            if (parts[0].equals("CREATE")){
                graph=new AdjacencyListGraph<>();
                continue;
            }

            if (parts[0].equals("ADDEDGE")){
                graph.addEdge(Integer.parseInt(parts[1]),Integer.parseInt(parts[2]));
                continue;
            }
            if (parts[0].equals("DELETEEDGE")){
                graph.removeEdge(Integer.parseInt(parts[1]),Integer.parseInt(parts[2]));
                continue;
            }
            if (parts[0].equals("ADJACENT")){
                System.out.println(graph.getNeighbors(Integer.parseInt(parts[1])).contains(Integer.parseInt(parts[2])));
                continue;
            }
            if (parts[0].equals("PRINTGRAPH")){
                System.out.println(graph);
            }
        }
        scanner.close();

    }
}