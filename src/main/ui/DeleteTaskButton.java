package ui;

import model.Planner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//creates a new button that prompts user to delete an existing task
//modelled after example given LabelChanger.java:
//https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application
public class DeleteTaskButton extends JFrame implements ActionListener {
    private JLabel label1;
    private JTextField field1;
    private JLabel label2;
    private JTextField field2;
    private GraphicalUserInterface gui;
    private Planner planner;

    // MODIFIES: this
    // EFFECTS: sets labels and fields
    public DeleteTaskButton(Planner planner, GraphicalUserInterface gui) {
        super("Delete Task");
        this.gui = gui;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 90));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        JButton btn = new JButton("Delete Task");
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
        label2 = new JLabel("time");
        field2 = new JTextField(5);
        add(label2);
        add(field2);
    }


    //MODIFIES: this, planner
    //EFFECTS: when button is clicked, performs actions to delete task
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("myButton")) {
            boolean b = true;
            try {
                Planner.Day day = planner.findDay(Integer.parseInt(field1.getText()));
                label1.setText("DELETED");
            } catch (NumberFormatException numberFormatException) {
                b = false;
                label1.setText("Please enter your day as a number");
            }
            try {
                int time = Integer.parseInt(field2.getText());
                label2.setText("");
            } catch (NumberFormatException numberFormatException) {
                b = false;
                label2.setText("Please enter a number");
            }
            if (b) {
                planner.deleteTask(planner.findDay(Integer.parseInt(field1.getText())),
                        Integer.parseInt(field2.getText()));
            }
        }
        gui.refreshPlanner(planner);
    }
}