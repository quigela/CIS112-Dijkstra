package me.quigela.cis112.project.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds collections of Vertex and Edges
 * @author quige
 */
public class Graph {
    
    private List<Edge> edges;
    private List<Vertex> verts;
    private Vertex[][] positions;
    
    public Graph() {
        edges = new ArrayList<>();
        verts = new ArrayList<>();
        positions = new Vertex[1][1];
    }
    
    public Graph(List<Vertex> verts, List<Edge> edges, Vertex[][] positions) {
        this.verts = verts;
        this.edges = edges;
        this.positions = positions;
        
        for(Edge e : this.edges) { //maintan Edge list among vertices
            e.getTo().addEdge(e);
            e.getFrom().addEdge(e);
        }
    }
    
    /**
     * Get all edges
     * @return 
     */
    public List<Edge> getEdges() {
        return this.edges;
    }
    
    /**
     * Get all vertices (collection)
     * @return 
     */
    public List<Vertex> getVertices() {
        return this.verts;
    }
    
    /**
     * Get all vertices as they are in the original Vertex matrix
     * @return 
     */
    public Vertex[][] getPositions() {
        return this.positions;
    }
}
