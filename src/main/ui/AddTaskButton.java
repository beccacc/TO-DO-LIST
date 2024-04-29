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
public class AddTaskButton extends JFrame implements ActionListener {
    private JLabel label1;
    private JTextField field1;
    private JLabel label2;
    private JTextField field2;
    private JLabel label3;
    private JTextField field3;
    private GraphicalUserInterface gui;
    private Planner planner;


    //MODIFIES: this
    //EFFECTS: instantiates Add Task Button
    public AddTaskButton(Planner planner, GraphicalUserInterface gui) {
        super("Add Task");
        this.gui = gui;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 90));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        JButton btn = new JButton("Add Task");
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
        label1 = new JLabel("name");
        field1 = new JTextField(5);
        add(label1);
        add(field1);
        label2 = new JLabel("time");
        field2 = new JTextField(5);
        add(label2);
        add(field2);
        label3 = new JLabel("number of day 1-7");
        field3 = new JTextField(5);
        add(label3);
        add(field3);
    }


    //MODIFIES: this, planner
    //EFFECTS: when button is clicked, performs action sets name, time and day as user input
    //         if user input is not in the appropriate format, asks the user to change it and does not add the task
    public void actionPerformed(ActionEvent e) {
        boolean b = true;
        String name = "";
        int time = 0;
        if (e.getActionCommand().equals("myButton")) {
            try {
                name = field1.getText();
                label1.setText("ADDED");
                time = Integer.parseInt(field2.getText());
                label2.setText("");
            } catch (NumberFormatException numberFormatException) {
                b = false;
                label1.setText("NOT ADDED");
                label2.setText("Please enter a number");
            }
            actionPerformed2(b, name, time);
        }
        gui.refreshPlanner(planner);
    }

    //MODIFIES: this, planner
    //EFFECTS: Sets day and calls addTask to add the task to the planner if appropriate
    public void actionPerformed2(boolean b, String name, int time) {
        Planner.Day day = Planner.Day.UNKNOWN;
        try {
            day = planner.findDay(Integer.parseInt(field3.getText()));
            label3.setText("");
        } catch (NumberFormatException numberFormatException) {
            b = false;
            label3.setText("Please enter your day as a number");
        }
        if (b) {
            System.err.println("Adding a task");
            planner.addTask(name, time, day);
        } else {
            System.err.print("Error: not adding;");
        }
    }
}