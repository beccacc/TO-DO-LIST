package ui;

import model.Planner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//creates a new button that prompts user to find out how many incomplete tasks they have on a given day
//modelled after example given LabelChanger.java:
//https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application
public class HowManyTasksButton extends JFrame implements ActionListener {
    private JLabel label1;
    private JTextField field1;
    private GraphicalUserInterface gui;
    private Planner planner;

    //EFFECTS: sets labels and fields
    public HowManyTasksButton(Planner planner, GraphicalUserInterface gui) {
        super("How many tasks do I have to do today?");
        this.gui = gui;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 90));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        JButton btn = new JButton("Find Tasks");
        btn.setActionCommand("myButton");
        btn.addActionListener(this);
        setLabels();
        add(btn);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        this.planner = planner;
    }

    //MODIFIES: this
    //EFFECTS: sets labels and fields
    public void setLabels() {
        label1 = new JLabel("day");
        field1 = new JTextField(5);
        add(label1);
        add(field1);
    }


    //MODIFIES: this
    //EFFECTS: when button is clicked, performs action to find how many tasks on a given day
    //         displays this number on the panel
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("myButton")) {
            try {
                Planner.Day day = planner.findDay(Integer.parseInt(field1.getText()));
                int i = planner.howManyTasksToDoOnDay(day);
                String toReturn = "You have " + i + " tasks to do on " + planner.dayToString(day);
                label1.setText(toReturn);
            } catch (NumberFormatException numberFormatException) {
                label1.setText("Please your day as a number");
            }
        }
        gui.refreshPlanner(planner);
    }
}