package me.quigela.cis112.project.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Handles random graph generation
 * @author quige
 */
public class GraphGen {

    /**
     * Get the width (number of columns)
     * @return 
     */
    public int getWIDTH() {
        return WIDTH;
    }

    /**
     * Set the width (number of columns)
     * @param WIDTH 
     */
    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    /**
     * Get the height (number of rows)
     * @return 
     */
    public int getHEIGHT() {
        return HEIGHT;
    }

    /**
     * Set the height (number of rows)
     * @param HEIGHT 
     */
    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }
    private int WIDTH;
    private int HEIGHT;
    
    public GraphGen(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
    }
    
    /**
     * Generates a new graph with Vertex matrix equaling [HEIGHT][WIDTH]
     * Edges are randomly added between vertices
     * 80% change for a horizontal connection
     * 50% change for a vertical connection
     * @return 
     */
    public Graph generate() {
        Random r = new Random();
        Vertex[][] verts = new Vertex[HEIGHT][WIDTH];
        List<Vertex> verts_l = new ArrayList<Vertex>();
        
        List<Edge> edges_l = new ArrayList<Edge>();
        
        for (int i = 0; i < verts.length; i++) {
            for (int j = 0; j < verts[i].length; j++) {
                Vertex v = new Vertex(i + "x" + j, i + "x" + j + "-Address");
                verts[i][j] = v;
                verts_l.add(v);
            }
        }
        for (int i = 0; i < verts.length - 1; i++) {
            for (int j = 0; j < verts[i].length - 1; j++) {
                System.out.println(i + ":" + j);
                if (r.nextDouble() < 0.80) {
                    Edge e = new Edge(i + "x" + j + "-Street", verts[i][j], verts[i][j+1], r.nextInt(15));
                    edges_l.add(e);
                    System.out.println(e);
                }
                if (r.nextDouble() < 0.50) {
                    Edge e1 = new Edge(i + "x" + j + "-Street", verts[i][j], verts[i+1][j], r.nextInt(15));
                    edges_l.add(e1);
                    System.out.println(e1);
                }
            }
        }
        
        Graph graph = new Graph(verts_l, edges_l, verts);
        return graph;
    }
}
