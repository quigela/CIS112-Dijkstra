package me.quigela.cis112.project.core;
import java.util.HashSet;
import java.util.Set;

/**
 * Holds information about a particular Node in the graph
 * @author quige
 */
public class Vertex {
    
    private final String id;
    private final String name;
    private Set<Edge> edges;
    private boolean isVisited = false;
    
    /**
     * initialize
     * @param id - id
     * @param name - name
     */
    public Vertex(String id, String name) {
        this.id = id;
        this.name = name;
        this.edges = new HashSet<Edge>();
    }
    
    /**
     * Override equals method so that two vertices with equal id and name will 
     * be equal regardless
     * of location in memory
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if(getClass() != obj.getClass()) {
            return false;
        }
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        Vertex v = (Vertex) obj;
        if (v.getId().equals(this.getId()) && v.getName().equals(this.getName())) {
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    /**
     * Add edge to list of edges to maintain a list of all connected edges to this vertex
     * @param e 
     */
    public void addEdge(Edge e) {
        if (e.getTo().equals(this) || e.getFrom().equals(this)) {
            this.edges.add(e);
        }
    }
    
    /**
     * Get the list of connected edges
     * @return 
     */
    public Set<Edge> getEdges() {
        return this.edges;
    }
    
    public void setVisited(boolean b) {
        this.isVisited = b;
    }
    
    public boolean isVisited() {
        return this.isVisited;
    }
}
