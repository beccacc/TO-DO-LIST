package ui;

import model.Planner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//creates a new button that prompts user to add a new task
//modelled after example given LabelChanger.java:
//https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application
public class SavePlannerButton extends JFrame implements ActionListener {
    private Planner planner;
    private JLabel label;
    private GraphicalUserInterface gui;


    //MODIFIES: this
    //EFFECTS: instantiates Save Planner Button
    public SavePlannerButton(Planner planner, GraphicalUserInterface gui) {
        super("Save Planner");
        this.gui = gui;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 90));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        JButton btn = new JButton("Save Planner");
        btn.setActionCommand("myButton");
        btn.addActionListener(this);
        label = new JLabel("Save Planner");
        add(label);
        add(btn);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        this.planner = planner;
    }

    //MODIFIES: this
    //EFFECTS: when button is clicked, performs action
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("myButton")) {
            planner.savePlanner();
        }
        label.setText("Planner Saved");
        gui.refreshPlanner(planner);
    }
}
