package me.quigela.cis112.project.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import me.quigela.cis112.project.gui.MainFrame;

/**
 * Controls functions of program
 * @author quige
 */
public class Controller {
    
    private GraphGen generator;
    private Graph g;
    private List<Vertex> drivers;
    private Random r;
    
    private MainFrame mainFrame;
    
    /**
     * Initialize all variables. Pick drivers from random vertices (15% max of total vertices)
     * Attach event handlers to buttons
     * Generate the graph
     * Draw the graph
     * @param mainFrame 
     */
    public Controller(MainFrame mainFrame) {
        this.generator = new GraphGen((int) mainFrame.spin_width.getValue(), 
                (int) mainFrame.spin_height.getValue());
        this.g = generator.generate();
        this.r = new Random();
        this.drivers = new ArrayList<>();
        this.mainFrame = mainFrame;
        
        pickDrivers();
        
        mainFrame.btn_reset.addActionListener((ActionEvent e) -> {
            reset();
        });
        
        mainFrame.requestDriverForm1.btnRequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vertex v = (Vertex) mainFrame.requestDriverForm1.jComboBox1.getSelectedItem();
                Vertex driver = requestDriver(v);
                
                mainFrame.driverListView1.populateList(getDrivers());
                mainFrame.requestDriverForm1.populateAddresses(getPatronAddresses());
            }
        });
        
        mainFrame.driverListView1.populateList(getDrivers());
        mainFrame.requestDriverForm1.populateAddresses(getPatronAddresses());
        mainFrame.jTextArea1.append("Hello!\n");
        mainFrame.graphDraw1.drawThisGraph(g);
    }
    
    /**
     * Reset all the variables
     * Regenerate the graph
     * Pick a bank of new drivers
     * Draw the new graph
     */
    public void reset() {
        mainFrame.jTextArea1.append("Resetting...\n");
        generator.setWIDTH((int) mainFrame.spin_width.getValue());
        generator.setHEIGHT((int) mainFrame.spin_height.getValue());
        this.g = generator.generate();
        this.drivers = new ArrayList<>();
        pickDrivers();
        
        mainFrame.driverListView1.populateList(getDrivers());
        mainFrame.requestDriverForm1.populateAddresses(getPatronAddresses());
        mainFrame.graphDraw1.drawThisGraph(g);
    }
    
    /**
     * Get the current driver bank
     * @return 
     */
    public List<Vertex> getDrivers() {
        return this.drivers;
    }
    
    /**
     * Start a driver request. Use Dijkstra to get distances for all nodes to v
     * Iterate over drivers and their distances to pick the driver with the least
     * steps to take to get to the Requestor
     * @param v - Patron/Requestor Location
     * @return Driver
     */
    public Vertex requestDriver(Vertex v) {
        mainFrame.jTextArea1.append("Requesting driver for: " + v + " \n");
        Pathfinder pf = new Pathfinder(this.g);
        pf.calculateDistance(v);
        Integer minDistance = Integer.MAX_VALUE;
        Vertex minDriver = null;
        for (Vertex driver : getDrivers()) {
            LinkedList<Vertex> path = pf.getPath(driver);
            if (path == null) {
                continue;
            }
            if (minDriver == null) {
                minDriver = driver;
                minDistance = path.size();
            } else {
                if (path.size() < minDistance) {
                    minDriver = driver;
                    minDistance = path.size();
                }
            }
        }
        if (minDriver == null) {
            mainFrame.jTextArea1.append("Driver Not Found!!\n");
        } else {
            mainFrame.jTextArea1.append("Driver Found: " + minDriver + "\n");
            String s = "Taking ";
            LinkedList<Vertex> a = pf.getPath(minDriver);
            for (Vertex w : a) {
                if (!s.equals("Taking ")) {
                    s += " to ";
                }
                s += w.getName();
            }
            mainFrame.jTextArea1.append(s + "\n");
        }
        return minDriver;
    }
    
    /**
     * Get all valid addresses/vertices
     * @return 
     */
    public List<Vertex> getAddresses() {
        return this.g.getVertices();
    }
    
    /**
     * Get all valid requestor addresses
     * getAddresses() excluding getDrivers()
     * @return 
     */
    private List<Vertex> getPatronAddresses() {
        List<Vertex> addresses = getAddresses();
        addresses.removeAll(getDrivers());
        return addresses;
    }
    
    /**
     * Pick drivers randomly from Vertex matrix
     * Max amount of drivers is 15% of total vertices
     */
    private void pickDrivers() {
        drivers.clear();
        List<Vertex> verts = this.g.getVertices();
        List<Vertex> choices = new ArrayList<Vertex>();
        int drivers = (int) ((this.r.nextDouble() * 0.15) * verts.size());
        while (drivers < 1) {
            drivers = (int) ((this.r.nextDouble() * 0.15) * verts.size());
        }
        for (int i = 1; i <= drivers; i++) {
            Vertex v = null;
            while (v == null || choices.contains(v)) {
                int index = this.r.nextInt(verts.size());
                v = verts.get(index);
            }
            choices.add(v);
        }
        this.drivers = choices;
        this.mainFrame.graphDraw1.setDrivers(this.drivers);
    }
}
