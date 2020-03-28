package me.quigela.cis112.project.core;

/**
 * Object to represent connection between two vertices
 * @author quige
 */
public class Edge {
    
    private final String id;
    private final Vertex from;
    private final Vertex to;
    private final int weight;
    private boolean visited = false;
    
    public Edge(String id, Vertex f, Vertex t, int w) {
        this.id = id;
        this.from = f;
        this.to = t;
        this.weight = w;
    }
    
    @Override
    public String toString() {
        return "ID:" + id + "/FROM:" + from.getName() + "/TO:" + to.getName() + "/W:" + weight;
    }
    
    public Vertex getFrom() {
        return this.from;
    }
    
    public Vertex getTo(){ 
        return this.to;
    }
    
    public int getWeight() {
        return this.weight;
    }
    
    public void setVisited(boolean b) {
        this.visited = b;
    }
    
    public boolean isVisited() {
        return this.visited;
    }
}
