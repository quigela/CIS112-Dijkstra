package me.quigela.cis112.project.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import me.quigela.cis112.project.core.Edge;
import me.quigela.cis112.project.core.Graph;
import me.quigela.cis112.project.core.Vertex;

/**
 * This class uses the Graphics of a JPanel to draw the city grid
 * Addresses are represented as ovals. Drivers at addresses are filled ovals
 * Lines represent edges / roads
 * @author quige
 */
public class GraphDraw extends JPanel{
    private Graph graph;
    
    private final int distBetween = 50;
    private final int vertRadius = 5;
    private final int padding = 25;
    private int WIDTH= 0;
    private int HEIGHT = 0;
    private List<Vertex> drivers = new ArrayList<>();
    
    public GraphDraw() {
        graph = null;
    }
    
    /**
     * Set driver list to given list
     * @param list of drivers at vertices
     */
    public void setDrivers(List<Vertex> list) {
        this.drivers = list;
    }
    /**
     * Draw a given Graph to the screen
     * Calculates the pixels needed and adjusts the JPanels size accordingly
     * @param g 
     */
    public void drawThisGraph(Graph g) {
        this.graph = g;
        
        HEIGHT = 0;
        WIDTH = 0;
        
        HEIGHT += g.getPositions().length * vertRadius;
        HEIGHT += g.getPositions().length * distBetween;
        HEIGHT += padding;
        
        WIDTH += g.getPositions()[0].length * vertRadius;
        WIDTH += g.getPositions()[0].length * distBetween;
        WIDTH += padding;
        
        setSize(new Dimension(WIDTH, HEIGHT));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        repaint();
    }
    
    /**
     * Override preferred size to be our calculated width and height
     * @return size
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }
    
    /**
     * Draws the Vertices and Edges as a grid / city streets
     * @param g Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.graph == null) {
            return;
        }
        Vertex[][] items = graph.getPositions();
        for (int i = 0;  i < items.length; i++) {
            for (int j = 0; j < items[i].length; j++) {
                if (this.drivers.contains(items[i][j])) {
                    g.fillOval((j * distBetween) + padding, 
                           (i * distBetween) + padding, 
                        vertRadius, vertRadius);
                } else {
                    g.drawOval((j * distBetween) + padding, 
                           (i * distBetween) + padding, 
                        vertRadius, vertRadius);
                }
            }
        }
        for (int i = 0; i < items.length - 1; i++) {
            for (int j = 0; j < items[i].length - 1; j++) {
                Vertex current = items[i][j];
                
                Vertex horiz = items[i][j+1];
                Vertex vertical = items[i+1][j];
                
                boolean hasHoriz = false;
                boolean hasVertical = false;
                
                for (Edge e : current.getEdges()) {
                    if (e.getFrom().equals(current)) {
                        if (e.getTo().equals(horiz)) {
                            hasHoriz = true;
                        }
                        if (e.getTo().equals(vertical)) {
                            hasVertical = true;
                        }
                    }
                    if (e.getTo().equals(current)) { 
                        if (e.getFrom().equals(horiz)) {
                            hasHoriz = true;
                        }
                        if (e.getFrom().equals(vertical)) {
                            hasVertical = true;
                        }
                    }
                }
                if (hasHoriz) {
                    g.drawLine((j * distBetween) + padding,
                               (i * distBetween) + padding,
                               ((j + 1) * distBetween) + padding,
                               (i * distBetween) + padding);
                }
                if (hasVertical) {
                    g.drawLine((j * distBetween) + padding,
                               (i * distBetween) + padding,
                               (j * distBetween) + padding,
                               ((i + 1) * distBetween) + padding);
                }
            }
        }
    }
}
