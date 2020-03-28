package me.quigela.cis112.project;

import me.quigela.cis112.project.core.Controller;
import me.quigela.cis112.project.gui.MainFrame;

/**
 * Starts the program
 * Init main holding frame for JPanel components and Controller
 * Project Overview:
 * A city-like grid is generated with a given number of horizontal and vertical nodes
 * Edges are randomly generated between these nodes to represent roads
 * There can be two edges between two vertices for the purpose of 
 * representing different sides of traffic
 * 
 * Each edge has a random weight assigned to it to represent road conditions, traffic, etc.
 * 
 * Dijkstra's shortest path algorithm is used to find the most suitable driver from a random
 * bank of drivers to pick up someone at a given location (node)
 * 
 * @author Anthony Quigel
 */
public class Main {
    public static void main(String[] args) {
        MainFrame main = new MainFrame();
        main.setVisible(true);
        Controller c = new Controller(main);
    }
}
