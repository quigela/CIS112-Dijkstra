package me.quigela.cis112.project.gui;

import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import me.quigela.cis112.project.core.Controller;
import me.quigela.cis112.project.core.Vertex;
/**
 * This class will populate a JComboBox or JList with a given List on a separate
 * thread
 * @author quige
 */
public class ListViewPopulate implements Runnable{
    private Object list;
    private List<Vertex> items;
    private int start;
    private int inc;
    
    public ListViewPopulate(Object list, List<Vertex> items, int start, int inc) {
        this.list = list;
        this.items = items;
        this.start = start;
        this.inc = inc;
    }

    ListViewPopulate(JList<String> jList2, Controller CONTROL, int i, int i0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        if (this.list instanceof JList) {
            DefaultListModel<String> model = new DefaultListModel();
            for (int i = this.start; i < items.size(); i += this.inc) {
                model.insertElementAt(items.get(i).getName(), model.size());
            }
            ((JList) list).setModel(model);
            ((JList) list).setSelectedIndex(0);
        }
        else if (this.list instanceof JComboBox) {
            DefaultComboBoxModel model = new DefaultComboBoxModel();
            for (int i = this.start; i < items.size(); i += this.inc) {
                model.addElement(items.get(i));
            }
            ((JComboBox) list).setModel(model);
            ((JComboBox) list).setSelectedIndex(0);
        }
    }
}
