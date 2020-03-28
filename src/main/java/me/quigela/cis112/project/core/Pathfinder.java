package me.quigela.cis112.project.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Path and distance finding
 * @author quige
 */
public class Pathfinder {
    
    private List<Vertex> verts;
    private List<Edge> edges;
    
    private Set<Vertex> settled;
    private Set<Vertex> unsettled;
    
    private HashMap<Vertex, Integer> dist;
    private HashMap<Vertex, Vertex> prev;
    
    /**
     * Initialize the pathfinder to a graph
     * @param g 
     */
    public Pathfinder(Graph g) {
        this.verts = g.getVertices();
        this.edges = g.getEdges();
        this.settled = new HashSet<>();
        this.unsettled = new HashSet<>();
        this.dist = new HashMap<>();
        this.prev = new HashMap<>();
    }
    
    /**
     * Get the distances map
     * @return 
     */
    public HashMap<Vertex, Integer> getDistances() {
        return this.dist;
    }
    
    /**
     * Use Dijkstra
     * Calculate distances of vertices to from
     * First unsettled is the destination at 0
     * @param from 
     */
    public void calculateDistance(Vertex from) {
        dist.put(from, 0);
        unsettled.add(from);
        while (unsettled.size() > 0) {
            Vertex v = getMin();
            unsettled.remove(v);
            settled.add(v);
            calcMin(v);
        }
    }
    
    /**
     * Calculate the lowest weight path to adjacent vertices
     * Add the Vertex to the pathing map for later
     * Add the vertex to unsettled to be further processed
     * @param a 
     */
    private void calcMin(Vertex a) {
        List<Vertex> adj = getAdj(a);
        for (Vertex v : adj) {
            
            if (getDistance(v) > getDistance(a) + getWeight(a, v)) {
                dist.put(v, getDistance(a) + getWeight(a, v));
                prev.put(v, a);
                unsettled.add(v);
            }
        }
    }
    
    /**
     * Gets the minimum weight Vertex from unsettled
     * @return 
     */
    private Vertex getMin() {
        Vertex min = null;
        for (Vertex v : unsettled) {
            if (min == null) {
                min = v;
                continue;
            }
            if (getDistance(v) < getDistance(min)) {
                min =  v;
            }
        }
        return min;
    }
    
    /**
     * Get the current distance for the current path to a given vertex
     * @param v
     * @return 
     */
    private Integer getDistance(Vertex v) {
        if (dist.containsKey(v)) {
            Integer d = dist.get(v);
            if (d == null){
                return Integer.MAX_VALUE;
            }
            return d;
        }
        return Integer.MAX_VALUE;
    }
    
    /**
     * Get the weight of an edge
     * Iterates through all edges for a matching to and from
     * @param f - from
     * @param t - to
     * @return 
     */
    private int getWeight(Vertex f, Vertex t) {
        for (Edge e : this.edges) {
            if (e.getTo().equals(t) && e.getFrom().equals(f)) {
                return e.getWeight();
            }
            if (e.getTo().equals(f) && e.getFrom().equals(t)) {
                return e.getWeight();
            }
        }
        return Integer.MAX_VALUE;
    }
    
    /**
     * Iterates through all edges for any pertaining to the given vertex
     * Should be using the Vertex's edge listings
     * @param v
     * @return 
     */
    private List<Vertex> getAdj(Vertex v) {
        List<Vertex> data = new ArrayList<Vertex>();
        for(Edge e : this.edges) {
            if (e.getFrom().equals(v) && !settled.contains(e.getTo())) {
                data.add(e.getTo());
            }
            if (e.getTo().equals(v) && !settled.contains(e.getFrom())) {
                data.add(e.getFrom());
            }
        }
        return data;
    }
    
    /**
     * Get the path to the given Vertex. Calculated from the starting Vertex given
     * at calculateDistance
     * @param a
     * @return 
     */
    public LinkedList<Vertex> getPath(Vertex a) {
        LinkedList<Vertex> path = new LinkedList<>();
        Vertex v = a;
        if (!prev.containsKey(v)) {
            return null;
        }
        path.add(v);
        while (prev.containsKey(v)) {
            v = prev.get(v);
            path.add(v);
        }
        return path;
    }
}
